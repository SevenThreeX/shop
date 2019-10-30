package com.sidianzhong.sdz.model.commond;

public class UserToken {

    private int userId;

    // 随机生成的uuid
    private String token;

//    public UserToken(int userId, String token) {
//        this.userId = userId;
//        this.token = token;
//    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenModel{" +
                "userId=" + userId +
                ", token='" + token + '\'' +
                '}';
    }
}
