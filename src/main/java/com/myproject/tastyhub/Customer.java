
package com.myproject.tastyhub;

public class Customer {

    private String name;
    private String phoneNo;
    private String deliveryArea;
    private String address;

    public Customer(String name, String phoneNo,
                    String deliveryArea, String address) {

        this.name = name;
        this.phoneNo = phoneNo;
        this.deliveryArea = deliveryArea;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
