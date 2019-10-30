package com.sidianzhong.sdz.model.commond;

import java.util.Date;

public class PhoneCode {

    private String phone;
    private String code;
    private Long currentTime;

    public void setCurrentTime(Long currentTime) {
        this.currentTime = currentTime;
    }

    public Long getCurrentTime() {
        return currentTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
