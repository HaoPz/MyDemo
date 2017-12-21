package com.mydemo.Retrofit;

import android.util.Log;

/**
 * Created by HaoPz on 2017/12/13.
 */

public class RetrofitTestBean {

    /**
     * lon : 117.12
     * level : 2
     * address :
     * cityName :
     * alevel : 4
     * lat : 36.65121
     */

    private double lon;
    private int level;
    private String address;
    private String cityName;
    private int alevel;
    private double lat;

    public void print(){
        Log.i("*****","lon:"+lon);
        Log.i("*****","level:"+level);
        Log.i("*****","address:"+address);
        Log.i("*****","cityName:"+cityName);
        Log.i("*****","alevel:"+alevel);
        Log.i("*****","lat:"+lat);
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getAlevel() {
        return alevel;
    }

    public void setAlevel(int alevel) {
        this.alevel = alevel;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
