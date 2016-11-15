package com.otago.lecturercommon.utill;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.otago.lecturercommon.entity.Status;
import com.otago.lecturercommon.entity.User;
import com.otago.lecturercommon.pojo.Constants;
import com.otago.lecturercommon.pojo.OtagoResponse;
import com.otago.lecturerweb.cache.ApplicationCache;
import com.otago.lecturerweb.cache.CommonObjectCache;

@Service
public class CommonUtil {
    private static Logger logger = Logger.getLogger(CommonUtil.class);

    @Autowired
    private ApplicationCache applicationCache;

    @Autowired
    private CommonObjectCache objectCache;

    private Properties properties = null;

    public CommonUtil() {
    }

    public CommonUtil(Properties properties) {
        this.properties = properties;
    }

    /**
     * Method to ceil the digits upto 2 decimal points after truncating the
     * digits after 5 decimal points
     * 
     * @param amount
     *            double amount to be ceiled upto 2 digits
     * @return resultant amount
     */
    public double roundTo2Digit(double amount) {
        return Math.round((long) (amount * 100000.00) / 1000.00) / 100.00;
    }

    public String getCsv(Object[] array, String joinStr) {
        StringBuilder sb = new StringBuilder();
        if (array != null) {
            for (Object string : array) {
                sb.append(joinStr).append(string);
            }
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    public int[] getIntArr(String data, String joinStr) {
        if (data != null) {
            String[] elems = data.split(joinStr);
            if (elems != null && elems.length > 0) {
                int[] values = new int[elems.length];
                for (int i = 0; i < elems.length; i++) {
                    values[i] = Integer.parseInt(elems[i]);
                }
                return values;
            }
        }
        return null;
    }

    public void handleCommonException(Throwable throwable, OtagoResponse otagoResponse, Logger logger) {
        String message = null;
        if (throwable instanceof Exception) {
            message = throwable.getMessage();
            logger.error(message);
        } else {
            message = "We have encountered an error, please try again later";
            logger.error(throwable);
        }
        otagoResponse.setSTATUS(OtagoResponse.STATUS_ERROR);
        otagoResponse.setMESSAGE(message);
    }

    public String encodeUtf8(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private static String getDbNameFromJdbcUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    public int getJsIndex() {
        return Constants.JS_INDEX;
    }

    public String sha512(String input) throws NoSuchAlgorithmException {
        MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
        algorithm.reset();
        algorithm.update(input.getBytes());
        byte messageDigest[] = algorithm.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < messageDigest.length; i++) {
            String hex = Integer.toHexString(0xFF & messageDigest[i]);
            if (hex.length() == 1) {
                sb.append("0");
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public Connection getConnection() {
        try {
            Class.forName(this.properties.getProperty("DB_DRIVER"));
            Connection con = DriverManager.getConnection(this.properties.getProperty("DB_URL"), this.properties.getProperty("DB_USER"), this.properties.getProperty("DB_PASSWORD"));
            return con;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(Connection con) {
        try {
            con.close();
        } catch (SQLException e) {
        }
    }

    public String parserForSeoUrl(String content, Map<String, Object> map) {
        return parser(content, map, "", true, 0);
    }

    public String parser(String content, Map<String, Object> map, String prefix) {
        return parser(content, map, prefix, false, 0);
    }

    public String parser(String content, Map<String, Object> map, String prefix, boolean seoUrl, int alertType) {
        if (content != null && map != null && !content.trim().isEmpty()) {
            for (String key : map.keySet()) {
                if (map.get(key) instanceof Map) {
                    content = parser(content, (Map<String, Object>) map.get(key), prefix + key + ".", seoUrl, alertType);
                    continue;
                }
                String value = map.get(key) + "";
                if (map.get(key) == null) {
                    value = "";
                }
                if (seoUrl) {
                    value = replaceSpecialCharter(value);
                }
                content = content.replaceAll("\\{" + prefix + key + "\\}", value);
            }

        }
        return content;
    }

    public String replaceSpecialCharter(String Name) {
        return Name.replaceAll("\\/+", "").replaceAll("[^\\w]", " ").replaceAll(" +", " ").trim().replaceAll("[^\\w]", "-").toLowerCase();
    }

    public String getSessionId(HttpServletRequest request) {
        String sessionId = (String) request.getAttribute(Constants.COOKIE_SESSION_ID);
        if (sessionId == null) {
            sessionId = request.getParameter(Constants.PARAM_SJVTSESSIONID);
        }
        if (sessionId == null && request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(Constants.COOKIE_SESSION_ID) && cookie.getValue() != null) {
                    sessionId = cookie.getValue();
                    break;
                }
            }
        }
        return sessionId;
    }

    public boolean validateSessionId(HttpServletRequest request) {
        String sessionId = getSessionId(request);
        return validateSessionId(sessionId);
    }

    public boolean validateSessionId(String sessionId) {
        boolean flag = false;
        try {
            int index = sessionId.indexOf("_");
            if (index > 0) {
                UUID.fromString(sessionId.substring(0, index));
            } else {
                //   UUID.fromString(sessionId);
                flag = applicationCache.isSessionExist(sessionId);
            }
        } catch (Exception e) {
            //DO NOTHING FLAG WILL REMAIN FALSE
        }
        return flag;
    }

    public String generateSessionId() {
        return UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    }

    public void createSession(String sessionId, User user, int platformId, HttpServletRequest request, HttpServletResponse response) {
        createSession(sessionId, user, request, response);
    }

    public void createSession(String sessionId, User user, HttpServletRequest request, HttpServletResponse response) {
//	        if (platformId==Platform.TXN_VIA_WEB) {
        setCookie(Constants.COOKIE_SESSION_ID, sessionId, -1, request, response);
//	        }
        request.setAttribute(Constants.COOKIE_SESSION_ID, sessionId);
        applicationCache.createSession(sessionId, request.getRemoteAddr(), user);
    }

    public void setCookie(String name, String data, int maxage, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (name != null && !name.isEmpty() && data != null) {
                Cookie cookie = new Cookie(name, data);
                cookie.setMaxAge((maxage > 0) ? maxage : Integer.MAX_VALUE);
                cookie.setPath("/");
                cookie.setHttpOnly(false);
                cookie.setSecure(false);
                /*       if (!isDevEnv()) {
                           String host = request.getServerName();
                           int index = host.lastIndexOf(".");
                           index = host.lastIndexOf(".", index - 1);
                           if (index > 0) {
                               host = host.substring(index);
                           } else {
                               host = "." + host;
                           }
                           cookie.setDomain(host);
                       }*/
                response.addCookie(cookie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser(String sessionId) {
        return applicationCache.getSessionValue(sessionId, ApplicationCache.KEY_SESSION_USER, User.class);
    }

    public User getUser(HttpServletRequest request) {
        String sessionId = getSessionId(request);
        return getUser(sessionId);
    }

    public boolean processUserLogin(String sessionId, HttpServletRequest request) throws Exception {
        boolean flag = false;
        User user = applicationCache.getSessionValue(sessionId, ApplicationCache.KEY_SESSION_USER, User.class);
        if (user == null) {
            throw new Exception("Not Athorize user.");
        }

        applicationCache.putSessionValue(sessionId, ApplicationCache.KEY_SESSION_USER, user);
        //    updateUserDetail(sessionId, user);
        flag = true;
        return flag;
    }

    public static double ceilTo2Digit(double amount) {
        return Math.ceil((int) (amount * 10000.00) / 100.00) / 100.00;
    }

    public static DataSource getDataSource(AppDatabase appDatabase) {
        System.out.println("**********dataSource url: " + appDatabase.getJdbcUrl());
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Constants.DB_MYSQL_DRIVER);
        dataSource.setUrl(appDatabase.getJdbcUrl());
        dataSource.setUsername(appDatabase.getDbUser());
        dataSource.setPassword(appDatabase.getDbMagicword());
        dataSource.setInitialSize(appDatabase.getMinCon());
        dataSource.setMaxIdle(appDatabase.getMaxCon());
        dataSource.setMaxWait(Constants.DB_CONNECTION_TIMEOUT);
        dataSource.setMaxIdle(appDatabase.getMaxIdleCon());
        dataSource.setDefaultAutoCommit(true);
        dataSource.setPoolPreparedStatements(true);
        dataSource.setMaxOpenPreparedStatements(Constants.DB_MAX_OPEN_PREPAREDSTATEMENT);
        dataSource.setTestOnBorrow(true);
        dataSource.setTestWhileIdle(true);
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationQueryTimeout(60);
        return dataSource;
    }
}
