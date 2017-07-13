package com.ktds.ramentimer.model;

/**
 * Created by xaiop on 2017-07-12.
 */

public class Ramen {
    private String prodName;
    private String cookMin;
    private String prodManufactor;
    private int prodImage;

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getCookMin() {
        return cookMin;
    }

    public void setCookMin(String cookMin) {
        this.cookMin = cookMin;
    }

    public String getProdManufactor() {
        return prodManufactor;
    }

    public void setProdManufactor(String prodManufactor) {
        this.prodManufactor = prodManufactor;
    }

    public int getProdImage() {
        return prodImage;
    }

    public void setProdImage(int prodImage) {
        this.prodImage = prodImage;
    }
}
