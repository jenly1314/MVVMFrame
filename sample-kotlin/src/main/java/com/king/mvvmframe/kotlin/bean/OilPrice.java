package com.king.mvvmframe.kotlin.bean;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class OilPrice {
    /**
     *  "city"  :   "北京",
     *  "92h"   :   "5.95",
     *  "95h"   :   "6.33",
     *  "98h"   :   "7.31",
     *  "0h"    :   "5.59"
     */

    private String city;

    @SerializedName("92h")
    private String oil92;

    @SerializedName("95h")
    private String oil95;

    @SerializedName("98h")
    private String oil98;

    @SerializedName("0h")
    private String oil0;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOil92() {
        return oil92;
    }

    public void setOil92(String oil92) {
        this.oil92 = oil92;
    }

    public String getOil95() {
        return oil95;
    }

    public void setOil95(String oil95) {
        this.oil95 = oil95;
    }

    public String getOil98() {
        return oil98;
    }

    public void setOil98(String oil98) {
        this.oil98 = oil98;
    }

    public String getOil0() {
        return oil0;
    }

    public void setOil0(String oil0) {
        this.oil0 = oil0;
    }


    public String getOil92Str() {
        return "¥ " + oil92;
    }


    public String getOil95Str() {
        return "¥ " +oil95;
    }


    public String getOil98Str() {
        return "¥ " +oil98;
    }


    public String getOil0Str() {
        return "¥ " +oil0;
    }



    @Override
    public String toString() {
        return "OilPrice{" +
                "city='" + city + '\'' +
                ", oil92='" + oil92 + '\'' +
                ", oil95='" + oil95 + '\'' +
                ", oil98='" + oil98 + '\'' +
                ", oil0='" + oil0 + '\'' +
                '}';
    }
}
