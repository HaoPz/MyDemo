package com.mydemo.StickyHeadActivity;

import java.util.List;

/**
 * Created by HaoPz on 2017/11/28.
 */

public class CityBean {

    private List<CitiesBean> cities;

    public List<CitiesBean> getCities() {
        return cities;
    }

    public void setCities(List<CitiesBean> cities) {
        this.cities = cities;
    }

    public static class CitiesBean {
        /**
         * counties : [{"areaName":"南明区","areaId":"520102"},{"areaName":"云岩区","areaId":"520103"},{"areaName":"花溪区","areaId":"520111"},{"areaName":"乌当区","areaId":"520112"},{"areaName":"白云区","areaId":"520113"},{"areaName":"观山湖区","areaId":"520115"},{"areaName":"开阳县","areaId":"520121"},{"areaName":"息烽县","areaId":"520122"},{"areaName":"修文县","areaId":"520123"},{"areaName":"清镇市","areaId":"520181"}]
         * areaName : 贵阳市
         * areaId : 520100
         */

        private String areaName;
        private String areaId;
        private List<CountiesBean> counties;

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public List<CountiesBean> getCounties() {
            return counties;
        }

        public void setCounties(List<CountiesBean> counties) {
            this.counties = counties;
        }

        public static class CountiesBean {
            /**
             * areaName : 南明区
             * areaId : 520102
             */

            private String areaName;
            private String areaId;

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }
        }
    }
}
