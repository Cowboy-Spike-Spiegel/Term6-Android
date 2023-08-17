package com.Spike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OrderActivity extends AppCompatActivity {

    private Button currentBtn;
    private Button historyBtn;
    ArrayAdapter<Order> adapter;
    ListView listView;

    private OkHttpClient okHttpClient;
    private RequestBody requestBody;
    private Request request;
    private Call call;
    private ResponseBody responseBody;
    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initEvent();

        // 创建listview
        listView = (ListView) findViewById(R.id.orderListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        okHttpClient = new OkHttpClient();
    }

    private void initView() {
        currentBtn = findViewById(R.id.current_btn);
        historyBtn = findViewById(R.id.history_btn);
    }

    private void initEvent() {
        currentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // authentic
                // 执行提交
                new NetworkTaskGet().execute("CURRENT");
            }
        });
        historyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // authentic
                // 执行提交
                new NetworkTaskGet().execute("HISTORY");
            }
        });
    }

    private class NetworkTaskGet extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            String token = getTokenFromSharedPreferences(getApplicationContext());
            String mode = params[0];
            String API_URL = "http://121.43.119.64:8848/client/index/order?mode="+mode;
            request = new Request.Builder()
                    .url(API_URL)
                    .get()
                    .addHeader("token", token)
                    .build();
            call = okHttpClient.newCall(request);
            try {
                response = call.execute();
                if (response.isSuccessful()) {
                    responseBody = response.body();
                    if (responseBody != null) {
                        return new JSONObject(responseBody.string());
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            } finally {
                if (responseBody != null) {
                    responseBody.close();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result != null) {
                int code = -1;
                try {
                    code = result.getInt("code");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (code == 0) {
                    Toast.makeText(OrderActivity.this, "查询成功", Toast.LENGTH_SHORT).show();
                    JSONArray data = new JSONArray();
                    try {
                        data = result.getJSONArray("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // 进行数据分析和控件写入数据
                    try {
                        // 遍历JSON数组并将每个项添加到适配器中
                        List<Order> orders = new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject order = data.getJSONObject(i);
                            // 创建order
                            Order item = new Order(
                                    order.getDouble("apply_kwh"),
                                    order.getString("car_id"),
                                    order.getString("charge_id"),
                                    order.getDouble("charge_kwh"),
                                    order.getDouble("charge_price"),
                                    order.getString("create_time"),
                                    order.getString("dispatch_time"),
                                    order.getDouble("fee"),
                                    order.getString("finish_time"),
                                    order.getInt("front_cars"),
                                    order.getString("id"),
                                    order.getString("mode"),
                                    order.getDouble("service_price"),
                                    order.getString("start_time"),
                                    order.getString("state"),
                                    order.getString("user_id")
                                    );
                            System.out.println(item);
                            orders.add(item);
                        }
                        adapter = new OrderAdapter(OrderActivity.this, orders);
                        listView.setAdapter(adapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "查询失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(OrderActivity.this, "害，服务器跑路啦", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 放入token
    public static String getTokenFromSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
}


