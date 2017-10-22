package com.bawie.yangqingqing.mytaobao.bean;

import java.util.List;

/**
 * Created by Bare on 2017/10/21.
 */

public class AddressBean2 {

    /**
     * code : 200
     * datas : {"address_list":[{"address":"北京市海淀区","address_id":"10","area_id":"37","area_info":"物品","city_id":"36","dlyp_id":"0","is_default":"0","member_id":"6","mob_phone":"15101579809","true_name":"张静"},{"address":"北京","address_id":"9","area_id":"37","area_info":"66666","city_id":"36","dlyp_id":"0","is_default":"0","member_id":"6","mob_phone":"123456123","true_name":"rtrt"}]}
     */

    private int code;
    private DatasBean datas;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        private List<AddressListBean> address_list;

        public List<AddressListBean> getAddress_list() {
            return address_list;
        }

        public void setAddress_list(List<AddressListBean> address_list) {
            this.address_list = address_list;
        }

        public static class AddressListBean {
            /**
             * address : 北京市海淀区
             * address_id : 10
             * area_id : 37
             * area_info : 物品
             * city_id : 36
             * dlyp_id : 0
             * is_default : 0
             * member_id : 6
             * mob_phone : 15101579809
             * true_name : 张静
             */

            private String address;
            private String address_id;
            private String area_id;
            private String area_info;
            private String city_id;
            private String dlyp_id;
            private String is_default;
            private String member_id;
            private String mob_phone;
            private String true_name;
            private boolean isno;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getArea_info() {
                return area_info;
            }

            public void setArea_info(String area_info) {
                this.area_info = area_info;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getDlyp_id() {
                return dlyp_id;
            }

            public void setDlyp_id(String dlyp_id) {
                this.dlyp_id = dlyp_id;
            }

            public String getIs_default() {
                return is_default;
            }

            public void setIs_default(String is_default) {
                this.is_default = is_default;
            }

            public String getMember_id() {
                return member_id;
            }

            public void setMember_id(String member_id) {
                this.member_id = member_id;
            }

            public String getMob_phone() {
                return mob_phone;
            }

            public void setMob_phone(String mob_phone) {
                this.mob_phone = mob_phone;
            }

            public String getTrue_name() {
                return true_name;
            }

            public void setTrue_name(String true_name) {
                this.true_name = true_name;
            }


            public void setisno(boolean isno) {
                this.isno = isno;
            }

            public boolean getisno() {
                return isno;
            }
        }
    }
}
