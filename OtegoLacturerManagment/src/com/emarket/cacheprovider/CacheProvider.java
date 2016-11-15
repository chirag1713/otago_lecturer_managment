package com.emarket.cacheprovider;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.emarket.emarketcommon.entity.UserRegistration;
import com.google.gson.Gson;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CacheProvider {
    private String KEY_PREFIX;
    private String CACHE_SERVER_IP;
    private int CACHE_SERVER_PORT;
    private JedisPool jedisPool;

    public static final String KEY_PREFIX_LOCK = "LOCK_";

    // cache key
    public static final String KEY_SESSION = "SESSION";
    public static final String KEY_SESSION_UPDATED_ON = "SESSION_UPDATED_ON";
    public static final String KEY_PLATFORM = "PLATFORM";
    public static final String KEY_SESSION_USER_CITY = "USER_CITY";
    public static final String KEY_SESSION_USER_COMMUNICATION = "USER_COMM";
    public static final String KEY_USER_AUTH_AUDITID = "userAuthAuditId";
    public static final String KEY_SESSION_CREATED_ON = "SESSION_CREATED_ON";
    public static final String KEY_SESSION_USER_IP = "SESSION_USER_IP";
    public static final String KEY_SESSION_DEVICE = "DEVICE";
    public static final String KEY_SESSION_CURR_URL = "CUR_URL";
    public static final String KEY_SESSION_PREV_URL = "PREV_URL";
    public static final String KEY_SESSION_AUTH_KEY = "AUTH_KEY";
    public static final String KEY_USER_PROFILE_IMAGE = "USER_PROFILE_IMAGE";
    public static final String KEY_PREFIX_SESSION_ORDERID = "ORDERID_";
    public static final String KEY_PREFIX_SERVICESTATUS = "SERVICESTATUS_";
    public static final String KEY_AUTO_INCREMENTED_ID = "AUTO_INCREMENTED_ID";
    public static final String KEY_USER_EMAIL = "EMAIL";
    public static final String KEY_USER_MOBILE = "MOBILE";
    public static final String KEY_USER_PASSWORD = "PASSWORD";

    public static final String KEY_USER_IS_VERIFIED = "IS_VERIFIED";
    public static final String KEY_USER_IS_SOCIAL = "IS_SOCIAL";
    public static final String KEY_USER_FIRST_TIME_LOGIN = "FIRST_TIME_LOGIN";

    public static final String KEY_SESSION_SESSION_DATA_UPDATE_TIME = "UPDATE_SESSION_DATA";

    public CacheProvider() {
        System.out.println("*******CacheProvider");
    }

    public CacheProvider(GenericObjectPoolConfig config, String serverIp, int serverPort, int serverMinCon, int serverMaxCon, int serverConTimeout, String keyPrefix) {
        this.KEY_PREFIX = keyPrefix;
        this.CACHE_SERVER_IP = serverIp;
        this.CACHE_SERVER_PORT = serverPort;
        jedisPool = new JedisPool(config, CACHE_SERVER_IP, CACHE_SERVER_PORT);
    }

    public boolean isSessionExist(String sessionId) {
        sessionId = KEY_PREFIX + "_" + KEY_SESSION + "_" + sessionId;
        Jedis jedis = jedisPool.getResource();
        try {
            if (jedis.exists(sessionId)) {
                if (jedis.hexists(sessionId, KEY_SESSION_CREATED_ON)) {
                    return true;
                } else {
                    jedis.del(sessionId);
                }
            }
            return false;
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void createSession(String sessionId, String ip, UserRegistration user) {
        Gson gson = new Gson();
        sessionId = KEY_PREFIX + "_" + KEY_SESSION + "_" + sessionId;
        Jedis jedis = jedisPool.getResource();
        String nowMillies = String.valueOf(System.currentTimeMillis());
        jedis.hset(sessionId, KEY_SESSION_UPDATED_ON, nowMillies);
        jedis.hset(sessionId, KEY_SESSION_CREATED_ON, nowMillies);
        jedis.hset(sessionId, KEY_SESSION_USER_IP, ip);
        if (user != null) {
            jedis.hset(sessionId, KEY_SESSION_USER_COMMUNICATION, gson.toJson(user));
        }

    }
}
