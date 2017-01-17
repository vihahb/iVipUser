package com.xtel.ivipuser.model.entity;

import java.io.Serializable;

/**
 * Created by vihahb on 1/16/2017.
 */

public class TestRecycle implements Serializable {

    private String shopName;
    private String shopMenber;
    private String shopLocation;
    private String shopComment;

    public TestRecycle(String shopName, String shopMenber, String shopLocation, String shopComment) {
        this.shopName = shopName;
        this.shopMenber = shopMenber;
        this.shopLocation = shopLocation;
        this.shopComment = shopComment;
    }

    public TestRecycle() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopMenber() {
        return shopMenber;
    }

    public void setShopMenber(String shopMenber) {
        this.shopMenber = shopMenber;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopComment() {
        return shopComment;
    }

    public void setShopComment(String shopComment) {
        this.shopComment = shopComment;
    }

    @Override
    public String toString() {
        return "TestRecycle{" +
                "shopName='" + shopName + '\'' +
                ", shopMenber='" + shopMenber + '\'' +
                ", shopLocation='" + shopLocation + '\'' +
                ", shopComment='" + shopComment + '\'' +
                '}';
    }
}
