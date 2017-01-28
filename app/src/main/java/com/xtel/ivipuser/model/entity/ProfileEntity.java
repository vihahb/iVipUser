package com.xtel.ivipuser.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 1/28/2017.
 */

public class ProfileEntity extends RESP_Basic {
    @Expose
    private String first_name;
    @Expose
    private String last_name;
    @Expose
    private int gender;
    @Expose
    private long birth_day;
    @Expose
    private String phone;
    @Expose
    private String address;
    @Expose
    private String avatar;
    @Expose
    private String email;

    public ProfileEntity(String first_name, String last_name, int gender, long birth_day, String phone, String address, String avatar, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.gender = gender;
        this.birth_day = birth_day;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public long getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(long birth_day) {
        this.birth_day = birth_day;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ProfileEntity{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", gender=" + gender +
                ", birth_day=" + birth_day +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
