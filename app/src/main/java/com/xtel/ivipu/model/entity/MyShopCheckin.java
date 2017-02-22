package com.xtel.ivipu.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 2/17/2017.
 */

public class MyShopCheckin {
    @Expose
    private int store_id;
    @Expose
    private String store_name;
    @Expose
    private String logo;
    @Expose
    private String banner;
    @Expose
    private int type;

    public MyShopCheckin() {
    }

    public MyShopCheckin(int store_id, String store_name, String logo, String banner, int type) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.logo = logo;
        this.banner = banner;
        this.type = type;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "MyShopCheckin{" +
                "store_id=" + store_id +
                ", store_name='" + store_name + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", type=" + type +
                '}';
    }
}
