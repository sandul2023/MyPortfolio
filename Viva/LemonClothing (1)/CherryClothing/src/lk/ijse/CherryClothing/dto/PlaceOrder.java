package lk.ijse.CherryClothing.dto;

import java.util.ArrayList;

public class PlaceOrder {
    private String customerId;
    private String orderId;
    private ArrayList<OrderDetailDTO> orderDetails = new ArrayList<>();

    public PlaceOrder() {
    }

    public PlaceOrder(String customerId, String orderId, ArrayList<OrderDetailDTO> orderDetails) {
        this.customerId = customerId;
        this.orderId = orderId;
        this.orderDetails = orderDetails;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public ArrayList<OrderDetailDTO> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetailDTO> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



    @Override
    public String toString() {
        return "PlaceOrder{" +
                "customerId='" + customerId + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderDetails=" + orderDetails +
                '}';
    }
}
