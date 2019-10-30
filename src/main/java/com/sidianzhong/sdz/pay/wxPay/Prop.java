package com.sidianzhong.sdz.pay.wxPay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Data
@Component
public class Prop {
    @Value("${prop.coin_server_path}")
    private String coin_server_path;

    @Value("${prop.base_file_path}")
    private String base_file_path;

    @Value("${prop.token_time_out}")
    private int token_time_out;

    @Value("${prop.authorization}")
    private String authorization;

    @Value("${prop.current_user_id}")
    private String current_user_id;

    @Value("${prop.user_icon}")
    private String user_icon;

    @Value("${prop.id_card_0}")
    private String id_card_0;

    @Value("${prop.id_card_1}")
    private String id_card_1;

    @Value("${prop.id_card_2}")
    private String id_card_2;

    @Value("${prop.id_card_3}")
    private String id_card_3;

    @Value("${prop.poster_img}")
    private String poster_img;

    @Value("${prop.banner}")
    private String banner;

    @Value("${prop.activity_img}")
    private String activity_img;

    @Value("${prop.service_rate}")
    private Double service_rate;


    @Value("${prop.notify_url}")
    private String notify_url;

    @Value("${prop.refund_notify_url}")
    private String refund_notify_url;

    public String getCoin_server_path() {
        return coin_server_path;
    }

    public void setCoin_server_path(String coin_server_path) {
        this.coin_server_path = coin_server_path;
    }

    public String getBase_file_path() {
        return base_file_path;
    }

    public void setBase_file_path(String base_file_path) {
        this.base_file_path = base_file_path;
    }

    public int getToken_time_out() {
        return token_time_out;
    }

    public void setToken_time_out(int token_time_out) {
        this.token_time_out = token_time_out;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getCurrent_user_id() {
        return current_user_id;
    }

    public void setCurrent_user_id(String current_user_id) {
        this.current_user_id = current_user_id;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getId_card_0() {
        return id_card_0;
    }

    public void setId_card_0(String id_card_0) {
        this.id_card_0 = id_card_0;
    }

    public String getId_card_1() {
        return id_card_1;
    }

    public void setId_card_1(String id_card_1) {
        this.id_card_1 = id_card_1;
    }

    public String getId_card_2() {
        return id_card_2;
    }

    public void setId_card_2(String id_card_2) {
        this.id_card_2 = id_card_2;
    }

    public String getId_card_3() {
        return id_card_3;
    }

    public void setId_card_3(String id_card_3) {
        this.id_card_3 = id_card_3;
    }

    public String getPoster_img() {
        return poster_img;
    }

    public void setPoster_img(String poster_img) {
        this.poster_img = poster_img;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getActivity_img() {
        return activity_img;
    }

    public void setActivity_img(String activity_img) {
        this.activity_img = activity_img;
    }

    public Double getService_rate() {
        return service_rate;
    }

    public void setService_rate(Double service_rate) {
        this.service_rate = service_rate;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRefund_notify_url() {
        return refund_notify_url;
    }

    public void setRefund_notify_url(String refund_notify_url) {
        this.refund_notify_url = refund_notify_url;
    }
}
