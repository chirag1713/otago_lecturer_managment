package com.otago.lecturercommon.pojo;

public class Constants {
	public static final String SPRING_PACKAGE_SCAN = "com.otago";

	public static final int SESSION_EXPIRATION_SECONDS = 3600;
	public static final String CACHE_PRIFIX = "OTAGO";

	/*
	 * public static final String ENVIRONMENT_DEV = "DEV"; public static final
	 * String ENVIRONMENT_TEST = "TEST"; public static final String
	 * ENVIRONMENT_PROD = "PROD"; public static final String ENVIRONMENT_MODE =
	 * "EnvironmentMode";
	 */
	public static final String SYSTEM_DOMAIN = "otagolecturermanagment.com";

	public static final String NODENAME = "NodeName";
	public static final int DB_MAX_OPEN_PREPAREDSTATEMENT = 200;
	public static final int DB_CONNECTION_TIMEOUT = 60000;
	public static final String DB_MYSQL_DRIVER = "com.mysql.jdbc.Driver";

	public static final String APPLICATION_KEY_COMMONUTIL = "commonUtil";

	public static final String APPLICATION_KEY_JSPUTIL = "jspUtil";

	public static int JS_INDEX = 30;

	public static final int HOME_PAGE = 1;
	public static final int PRODUCT_PAGE = 2;
	public static final int PRODUCT_DETAILS_PAGE = 3;
	public static final int ABOUTUS_PAGE = 4;
	public static final int CONTACTUS_PAGE = 5;
	public static final int FIND_A_STORE_PAGE = 6;
	public static final int FIND_A_BROKER_PAGE = 7;
	public static final int PROFILE_PAGE = 8;

	public static final int PRODUCT_PER_PAGE = 25;

	public static final String COOKIE_SESSION_ID = "OtagoSessionId";
	public static final String PARAM_PREFIXSJVT = "_OTAGO";
	public static final String PARAM_SJVTSESSIONID = PARAM_PREFIXSJVT
			+ "SessionId";

	public static final String COOKIE_DEVICE_ID = "CookieDeviceId";
	public static final String COOKIE_FLUSH_USER_DATA = "FlushUserData";
	public static final String COOKIE_DATA = "OtagoData";

	public static final String HEADER_X_PROXY_SCHEME = "x_proxy_scheme";

}
