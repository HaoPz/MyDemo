package com.mydemo.StickyHeadActivity;

/**
 * Created by HaoPz on 2017/11/28.
 */

public class AddressBean {
    private String type ;
    private String areaName;
    private String aredId;

    public AddressBean(String type, String areaName, String aredId) {
        this.type = type;
        this.areaName = areaName;
        this.aredId = aredId;
    }

    public String getType() {
        return type;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAredId() {
        return aredId;
    }
}
