/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otago.lecturerweb.cache;

import java.util.Map;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.otago.lecturercommon.entity.User;
import com.otago.lecturercommon.pojo.Constants;
import com.otago.lecturercommon.utill.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 *
 * @author omm
 */
public class ApplicationCache {

    private String KEY_PREFIX;

    private JedisPool jedisPool;
    private JsonUtil jsonUtil;

    private String CACHE_SERVER_IP;
    private int CACHE_SERVER_PORT;

    public static final String KEY_THREADEXECUTOR = "THREADEXECUTOR";

    public static final String KEY_PREFIX_SESSION_ORDERID = "ORDERID_";
    public static final String KEY_SESSION = "SESSION";
    public static final String SESSION_PREFIX = "SESSION_";
    public static final String KEY_SESSION_USER_IP = "IP";
    public static final String KEY_SESSION_UPDATED_ON = "UPDATED_ON";
    public static final String KEY_SESSION_CREATED_ON = "CREATED_ON";
    public static final String KEY_SESSION_USER = "USER";
    public static final String KEY_USER_IS_VERIFIED = "IS_VERIFIED";
    public static final String KEY_USER_IS_SOCIAL = "IS_SOCIAL";
    public static final String KEY_USER_FIRST_TIME_LOGIN = "FIRST_TIME_LOGIN";
    public static final String KEY_SESSION_USER_RESETPASSWORD = "USER_RESETPASSWORD";
    public static final String KEY_AUTO_INCREMENTED_ID = "AUTO_INCREMENTED_ID";
    public static final String KEY_PURCHASE_ORDER = "PURCHASE_ORDER";

    public ApplicationCache() {
        System.out.println("*******CacheProvider");
    }

    public ApplicationCache(String keyPrefid, String serverIp, int serverPort, int serverMinCon, int serverMaxCon, int serverConTimeout, JsonUtil jsonUtil) {
        this.KEY_PREFIX = keyPrefid;
        this.CACHE_SERVER_IP = serverIp;
        this.CACHE_SERVER_PORT = serverPort;
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setBlockWhenExhausted(true);
        config.setTestOnBorrow(true);
        config.setMaxIdle(serverMinCon);
        config.setMaxTotal(serverMaxCon);
        config.setMaxWaitMillis(serverConTimeout);
        this.jedisPool = new JedisPool(config, CACHE_SERVER_IP, CACHE_SERVER_PORT);
        this.jsonUtil = jsonUtil;
    }

    public <T> T getValue(String key, Class<T> cls, int expirationSeconds) {
        String valueJson = getValue(key, expirationSeconds);
        if (valueJson != null) {
            return jsonUtil.fromJson(valueJson, cls);
        }
        return null;
    }

    public String getValue(String key, int expirationSeconds) {
        key = (KEY_PREFIX + key);
        String value;
        Jedis jedis = jedisPool.getResource();
        try {
            value = jedis.get(key);
            if (value != null && expirationSeconds > 0) {
                jedis.expire(key, expirationSeconds);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    public <T> T getMapValue(String key, String field, Class<T> cls, int expirationSeconds) {
        String valueJson = getMapValue(key, field, expirationSeconds);
        if (valueJson != null) {
            return jsonUtil.fromJson(valueJson, cls);
        }
        return null;
    }

    public String getMapValue(String key, String field, int expirationSeconds) {
        key = (KEY_PREFIX + key);
        String value;
        Jedis jedis = jedisPool.getResource();
        try {
            value = jedis.hget(key, field);
            if (value != null && expirationSeconds > 0) {
                jedis.expire(key, expirationSeconds);
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    public Map<String, String> getMap(String key) {
        key = (KEY_PREFIX + key);
        Map<String, String> value;
        Jedis jedis = jedisPool.getResource();
        try {
            value = jedis.hgetAll(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
        return value;
    }

    public void putValue(String key, Object object, int expirationSeconds) {
        if (object != null)
            putValue(key, jsonUtil.toJson(object), expirationSeconds);
    }

    public void putValue(String key, String value, int expirationSeconds) {
        key = (KEY_PREFIX + key);
        if (value != null) {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.set(key, value);
                if (expirationSeconds > 0) {
                    jedis.expire(key, expirationSeconds);
                }
            } finally {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public void putMapValue(String key, String field, Object object, int expirationSeconds) {
        if (object != null)
            putMapValue(key, field, jsonUtil.toJson(object), expirationSeconds);
    }

    public void putMapValue(String key, String field, String value, int expirationSeconds) {
        key = (KEY_PREFIX + key);
        if (value != null) {
            Jedis jedis = jedisPool.getResource();
            try {
                jedis.hset(key, field, value);
                if (expirationSeconds > 0) {
                    jedis.expire(key, expirationSeconds);
                }
            } finally {
                jedisPool.returnResource(jedis);
            }
        }
    }

    public void removeValue(String... key) {
        for (int i = 0; i < key.length; i++) {
            key[i] = (KEY_PREFIX + key[i]);
        }
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void removeMapValue(String key, String... field) {
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.hdel(key, field);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public <T> T getSessionValue(String sessionId, String field, Class<T> cls) {
        String key = SESSION_PREFIX + sessionId;
        return getMapValue(key, field, cls, 0);
    }

    public void putSessionValue(String sessionId, String field, Object obj) {
        putSessionValue(sessionId, field, jsonUtil.toJson(obj));
    }

    public void putSessionValue(String sessionId, String field, String value) {
        String key = SESSION_PREFIX + sessionId;
        putMapValue(key, field, value, 0);
    }

    public String getSessionValue(String sessionId, String field) {
        String key = SESSION_PREFIX + sessionId;
        return getMapValue(key, field, 0);
    }

    public void removeSessionValue(String sessionId, String field) {
        String key = SESSION_PREFIX + sessionId;
        removeMapValue(key, field);
    }

    public boolean isKeyExist(String key) {
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.exists(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public long incrementKey(String key) {
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incr(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void setExpiration(String key, int expitrationSeconds) {
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.expire(key, expitrationSeconds);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean isMapKeyExist(String key, String field) {
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hexists(key, field);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public boolean isUserExistInSession(String sessionId) {
        String key = SESSION_PREFIX + sessionId;
        return isMapKeyExist(key, KEY_SESSION_USER);
    }

    /**
     * Checks and keeps session live is it exists
     * 
     * @param sessionId
     * @return
     */
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

    public Map<String, String> getSessionObj(String sessionId) {
        String key = SESSION_PREFIX + sessionId;
        return getMap(key);
    }

    public void createSession(String sessionId, String ip) {
        String key = SESSION_PREFIX + sessionId;
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            String nowMillies = String.valueOf(System.currentTimeMillis());
            jedis.hset(key, KEY_SESSION_UPDATED_ON, nowMillies);
            jedis.hset(key, KEY_SESSION_CREATED_ON, nowMillies);
            jedis.hset(key, KEY_SESSION_USER_IP, ip);
            jedis.expire(key, Constants.SESSION_EXPIRATION_SECONDS);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void destroySession(String sessionId) {
        String key = SESSION_PREFIX + sessionId;
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            jedis.del(key);
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void resetSession(String sessionId) {
        String key = SESSION_PREFIX + sessionId;
        key = (KEY_PREFIX + key);
        Jedis jedis = jedisPool.getResource();
        try {
            Map<String, String> map = jedis.hgetAll(key);
            if (map != null) {
                for (String field : map.keySet()) {
                    if (!(field.equals(KEY_SESSION_UPDATED_ON) || field.equals(KEY_SESSION_CREATED_ON) || field.equals(KEY_SESSION_USER_IP))) {
                        jedis.hdel(sessionId, key);
                    }
                }
            }
        } finally {
            jedisPool.returnResource(jedis);
        }
    }

    public void close() {
        try {
            jedisPool.destroy();
        } catch (Exception e) {
            // Do nothing
        }
        try {
            jedisPool.close();
        } catch (Exception e) {
            // Do nothing
        }
    }

    public void createSession(String sessionId, String ip, User user) {
        sessionId = KEY_PREFIX + "_" + KEY_SESSION + "_" + sessionId;
        Jedis jedis = jedisPool.getResource();
        String nowMillies = String.valueOf(System.currentTimeMillis());
        jedis.hset(sessionId, KEY_SESSION_UPDATED_ON, nowMillies);
        jedis.hset(sessionId, KEY_SESSION_CREATED_ON, nowMillies);
        jedis.hset(sessionId, KEY_SESSION_USER_IP, ip);
        if (user != null) {
            jedis.hset(sessionId, KEY_SESSION_USER, jsonUtil.toJson(user));
        }
    }

    public int getAutoIncrementedJedisId() {
        int id;
        Jedis jedis = jedisPool.getResource();
        try {
            long value = jedis.incr(KEY_AUTO_INCREMENTED_ID);
            id = (int) value;
        } finally {
            jedisPool.returnResource(jedis);
        }
        return id;
    }

}
