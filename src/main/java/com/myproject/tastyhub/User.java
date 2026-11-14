/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.myproject.tastyhub;

/**
 *
 * @author LENOVO
 */
public class User {
    private int userId;
    private String fullName;
    private String email;
    private String password;
    private String role;
    private String phoneNo;
    
    public User(int userId, String fullName, String email,String password, String role, String phoneNo){
        this.userId=userId;
        this.fullName=fullName;
        this.email=email;
        this.password=password;
        this.role=role;
        this.phoneNo=phoneNo;
    }
    public int getUserId(){
        return userId;
    }
    public String getfullName(){
        return fullName;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public String getRole(){
        return role;
    }
    public String getPhoneNo(){
        return phoneNo;
    }
    
}
