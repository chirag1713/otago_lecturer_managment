package com.otago.lecturerweb.utill;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.XMLGregorianCalendar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class JsonUtil {
    private List<Gson> availablePool = null;
    private int size = 0;
    private volatile int currentIndex = 0;

    private final GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapter(java.sql.Timestamp.class, new DateGsonSerializer())
                    .registerTypeAdapter(java.sql.Date.class, new DateGsonSerializer()).registerTypeAdapter(java.sql.Time.class, new DateGsonSerializer())
                    .registerTypeAdapter(java.util.Date.class, new DateGsonSerializer())
                    .registerTypeAdapter(XMLGregorianCalendar.class, new XMLGregorianCalendarGsonSerializer()).disableHtmlEscaping();

    /**
     * pool requirement test runs -with new object every time gsonwrite: 236
     * gsonread: 77
     * 
     * -with single object gsonwrite: 80 gsonread: 28
     * 
     * -Our pool class LinkedList gsonwrite: 136 gsonread: 21
     * 
     * -Our pool class ArrayList gsonwrite: 142 gsonread: 20
     */
    public void init(int size) {
        init(size, null);
    }
    
    public void init(int size, Map<Type, Object> typeAdaptors) {
        
            if (typeAdaptors != null && typeAdaptors.size()>0) {
                for (Entry<Type, Object> entry: typeAdaptors.entrySet()) {
                    this.gsonBuilder.registerTypeAdapter(entry.getKey(), entry.getValue());
                }
            }

            this.size = size;

            this.availablePool = new ArrayList<Gson>();

            for (int i = 0; i < this.size; i++) {
                    this.availablePool.add(gsonBuilder.create());
            }
    }
    
    public String toJson(Object obj) {
            int localIndex = ++this.currentIndex;
            localIndex = localIndex%this.size;
            return this.availablePool.get(localIndex).toJson(obj);
    }

    public <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
            int localIndex = ++this.currentIndex;
            localIndex = localIndex%this.size;

            return this.availablePool.get(localIndex).fromJson(json, classOfT);
    }
    
    public <T> T fromJson(String json, Type typeOfT) throws JsonSyntaxException {
        int localIndex = ++this.currentIndex;
        localIndex = localIndex%this.size;
        
        return this.availablePool.get(localIndex).fromJson(json, typeOfT);
    }
}