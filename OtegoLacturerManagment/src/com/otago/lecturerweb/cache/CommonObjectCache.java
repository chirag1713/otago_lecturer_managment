package com.otago.lecturerweb.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.otago.lecturercommon.entity.Status;

public class CommonObjectCache {

    protected Map<Integer, Map<Object, Object>> commonCache = null;

    protected EntityManager em;

    public static final int CACHE_STATUS = 1;
    public static final int CACHE_TAG = 2;
    public static final int CACHE_APPLICATIONCONSTANT = 3;

    public CommonObjectCache(EntityManager em) {
        this.em = em;
        commonCache = new HashMap<Integer, Map<Object, Object>>();
    }

    public void refreshCache(int cacheId) {
        Map<Object, Object> cacheMap = commonCache.remove(cacheId);
        cacheMap.clear();
    }

    public void refreshAllCache() {
        if (commonCache.size() > 0) {
            Map<Object, Object> dataMap;
            for (int cacheId : commonCache.keySet()) {
                dataMap = commonCache.get(cacheId);
                dataMap.clear();
            }
            this.commonCache.clear();
        }
    }

    public Object getObject(Integer cacheId, Object entityObject) {
        Map<Object, Object> cacheMap = commonCache.get(cacheId);
        if (cacheMap == null) {
            initCache(cacheId);
            cacheMap = commonCache.get(cacheId);
        }
        return cacheMap.get(entityObject);
    }

    /**
     * This method should only used for the cache should required to be reloaded
     * if entityObject is not present in the cache. Please do not use it blindly
     * 
     * @param cacheId
     * @param entityObject
     * @return
     */
    public Object getObjectV2(Integer cacheId, Object entityObject) {
        Map<Object, Object> cacheMap = commonCache.get(cacheId);
        Object value = null;
        if (cacheMap == null || (value = cacheMap.get(entityObject)) == null) {
            initCache(cacheId);
            value = commonCache.get(cacheId).get(entityObject);
        }
        return value;
    }

    // /**
    // * This method should only used to cache the individual objects ondemand,
    // * this will not load the complete data from the database but will load
    // only
    // * required object in the cache
    // *
    // * @param cacheId
    // * @param entityObject
    // * @return
    // */
    // public Object getObjectV3(Integer cacheId, Object entityObject) {
    // Map<Object, Object> cacheMap = commonCache.get(cacheId);
    // Object value = null;
    // if (cacheMap == null || (value = cacheMap.get(entityObject)) == null) {
    // initCacheByObjId(cacheId, entityObject);
    // value = commonCache.get(cacheId).get(entityObject);
    // }
    // return value;
    // }

    protected boolean initCache(Integer cacheId) {
        switch (cacheId) {
            case (CACHE_STATUS): {
                Query q = em.createQuery("select s.id, s from Status s");
                return addData(q, cacheId);
            }
            case (CACHE_TAG): {
                Query q = em.createQuery("select t.id, t from Tag t");
                return addData(q, cacheId);
            }
            case (CACHE_APPLICATIONCONSTANT): {
                Query q = em.createQuery("select ac.id, ac.value from ApplicationConstant ac");
                return addData(q, cacheId);
            }
        }
        return false;
    }

    protected boolean addData(Query q, int cacheId) {
        List<Object[]> entities = q.getResultList();
        Map<Object, Object> dataMap = new HashMap<Object, Object>(entities.size());
        for (Object[] data : entities) {
            dataMap.put(data[0], data[1]);
        }
        commonCache.put(cacheId, dataMap);
        return true;
    }

    public Status getStatus(Integer statusId) {
        return (Status) getObject(CACHE_STATUS, statusId);
    }

    /*
     * public Tag getTag(Integer id) { return (Tag) getObject(CACHE_TAG, id); }
     */

    public String getApplicationConstant(Integer id) {
        return (String) getObject(CACHE_APPLICATIONCONSTANT, id);
    }
}
