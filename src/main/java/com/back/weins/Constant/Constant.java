package com.back.weins.Constant;

public class Constant {
    public static final String USER_ID = "userId";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_TYPE = "userType";
    public static final String REMEMBER_ME = "remember";
    public static final String JSESSIONID = "JSESSIONID";

    public static final Integer NO_SUCH_USER = -1;
    public static final Integer MANAGER = 0;
    public static final Integer CUSTOMER = 1;

    public static final String JWT_ID = "weins-2020";
    public static final String JWT_SECRET = "Isi50b90fvJt+4IHoMJlHkS1ttg=";
    public static final int JWT_TTL = 60*60*1000;
    public static final int JWT_REFRESH_INTERVAL = 18 * 1000;
    public static final int JWT_REFRESH_TTL = 60 * 1000;
}
