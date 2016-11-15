package com.emarket.emarketcommon.pojo;

public class EMarketResponse {
    public static int STATUS_SUCCESS = 1;
    public static int STATUS_ERROR = 2;
    public static int STATUS_WARNING = 3;
    public static int STATUS_PENDING = 4;

    private Integer STATUS;
    private String MESSAGE;
    private Object DATA;

    public Integer getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(Integer sTATUS) {
        STATUS = sTATUS;
    }

    public String getMESSAGE() {
        return MESSAGE;
    }

    public void setMESSAGE(String mESSAGE) {
        MESSAGE = mESSAGE;
    }

    public Object getDATA() {
        return DATA;
    }

    public void setDATA(Object dATA) {
        DATA = dATA;
    }

}
