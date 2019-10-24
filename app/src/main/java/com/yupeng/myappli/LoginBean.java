package com.yupeng.myappli;

public class LoginBean {
    public ResultBean result;
    public String message;
    public String status;
    public static class ResultBean {
        public String headPic;
        public String nickName;
        public String phone;
        public String sessionId;
        public int sex;
        public int userId;
    }
}
