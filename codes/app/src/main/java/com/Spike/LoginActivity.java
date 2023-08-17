package com.Spike;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.IOException;
import java.util.Collections;

import okhttp3.Call;
import okhttp3.ConnectionSpec;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private EditText eEtAccount;
    private EditText eEtPassword;
    private Button eBtnLogin;
    private Button eBtnRegister;

    private OkHttpClient okHttpClient;
    private RequestBody requestBody;
    private Request request;
    private Call call;
    private ResponseBody responseBody;
    private Response response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
        okHttpClient = new OkHttpClient();
    }

    private void initView() {
        eEtAccount = findViewById(R.id.id_tv_account);
        eEtPassword = findViewById(R.id.id_tv_password);
        eBtnLogin = findViewById(R.id.id_btn_login);
        eBtnRegister = findViewById(R.id.id_btn_register);
    }

    private void initEvent() {
        eBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // authentic
                String account = eEtAccount.getText().toString().trim();
                String password = eEtPassword.getText().toString().trim();
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 执行提交
                new NetworkTask().execute(account, password);
            }
        });

        eBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toRegisterAcivity();
            }
        });
    }

    private class NetworkTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... params) {
            String account = params[0];
            String password = params[1];

            String API_URL = "http://121.43.119.64:8848/client/login";
            MediaType mediaType = MediaType.parse("application/json");
            JSONObject requestJson = new JSONObject();
            try {
                requestJson.put("account", account);
                requestJson.put("password", password);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            requestBody = RequestBody.create(requestJson.toString(), mediaType);
            request = new Request.Builder()
                    .url(API_URL)
                    .post(requestBody)
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
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    // memory token
                    JSONObject data = new JSONObject();
                    try {
                        data = result.getJSONObject("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String token = "";
                    try {
                        token = data.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();

                    toOrderAcivity();
                } else {
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "害，服务器跑路啦", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void toOrderAcivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    private void toRegisterAcivity() {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
        finish();
    }
}