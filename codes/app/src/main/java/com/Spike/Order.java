package com.Spike;

public class Order {
    private double ApplyKwh; // 申请度数，可能不是最后充电的度数！
    private String CarID;   // 车辆ID
    private String ChargeID;    // 调度到的充电桩id，无为空串
    private double ChargeKwh;    // 实际充电度数，确切的充电度数，由时间算出
    private double ChargePrice;  // 充电计价，平均充电计价
    private String CreateTime;  // 创建时间
    private String DispatchTime;    // 调度时间，无为xx-xx-xx xx:xx:xx
    private double Fee;  // 订单最终花费
    private String FinishTime;  // 终止时间，无为xx-xx-xx xx:xx:xx
    private int FrontCars;  // 查看前面有多少车，排号号码-当前已进入充电区号码-1
    private String ID;  // 订单ID
    private String Mode;    // 充电模式，F快充，T慢充
    private double ServicePrice;// 服务计价，服务计价
    private String StartTime;   // 开始时间，无为xx-xx-xx xx:xx:xx
    private String State;   // 订单状态，WAITING - 在等待区等待; DISPATCHED - 在充电桩内等待; CHARGING - 充电中; FINISHED - 结束
    private String UserID;  // 用户ID

    public Order(double applyKwh, String carID, String chargeID, double chargeKwh, double chargePrice, String createTime, String dispatchTime, double fee, String finishTime, int frontCars, String ID, String mode, double servicePrice, String startTime, String state, String userID) {
        ApplyKwh = applyKwh;
        CarID = carID;
        ChargeID = chargeID;
        ChargeKwh = chargeKwh;
        ChargePrice = chargePrice;
        CreateTime = createTime;
        DispatchTime = dispatchTime;
        Fee = fee;
        FinishTime = finishTime;
        FrontCars = frontCars;
        this.ID = ID;
        Mode = mode;
        ServicePrice = servicePrice;
        StartTime = startTime;
        State = state;
        UserID = userID;
    }

    public double getApplyKwh() {
        return ApplyKwh;
    }

    public String getCarID() {
        return CarID;
    }

    public String getChargeID() {
        return ChargeID;
    }

    public double getChargeKwh() {
        return ChargeKwh;
    }

    public double getChargePrice() {
        return ChargePrice;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getDispatchTime() {
        return DispatchTime;
    }

    public double getFee() {
        return Fee;
    }

    public String getFinishTime() {
        return FinishTime;
    }

    public int getFrontCars() {
        return FrontCars;
    }

    public String getID() {
        return ID;
    }

    public String getMode() {
        return Mode;
    }

    public double getServicePrice() {
        return ServicePrice;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getState() {
        return State;
    }

    public String getUserID() {
        return UserID;
    }
}
