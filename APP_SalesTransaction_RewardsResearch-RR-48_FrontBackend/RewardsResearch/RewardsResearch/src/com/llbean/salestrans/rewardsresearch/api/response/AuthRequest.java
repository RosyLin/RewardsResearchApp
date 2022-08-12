package com.llbean.salestrans.rewardsresearch.api.response;
import java.util.*;


public class AuthRequest {
    int AUTH_TOKEN_ID;
    Date AUTH_RQST_TS;
    int ORD_ID;
    int LL_STORE_ID;
    int RGST_ID;
    String AUTH_FUNC_CD;
    float AUTH_AMT;
    float TRN_TOT_AMT;
    String AUTH_SYS_IND;

    public int getAUTH_TOKEN_ID() {
        return AUTH_TOKEN_ID;
    }

    public void setAUTH_TOKEN_ID(int AUTH_TOKEN_ID) {
        this.AUTH_TOKEN_ID = AUTH_TOKEN_ID;
    }

    public Date getAUTH_RQST_TS() {
        return AUTH_RQST_TS;
    }

    public void setAUTH_RQST_TS(Date AUTH_RQST_TS) {
        this.AUTH_RQST_TS = AUTH_RQST_TS;
    }

    public int getORD_ID() {
        return ORD_ID;
    }

    public void setORD_ID(int ORD_ID) {
        this.ORD_ID = ORD_ID;
    }

    public int getLL_STORE_ID() {
        return LL_STORE_ID;
    }

    public void setLL_STORE_ID(int LL_STORE_ID) {
        this.LL_STORE_ID = LL_STORE_ID;
    }

    public int getRGST_ID() {
        return RGST_ID;
    }

    public void setRGST_ID(int RGST_ID) {
        this.RGST_ID = RGST_ID;
    }

    public String getAUTH_FUNC_CD() {
        return AUTH_FUNC_CD;
    }

    public void setAUTH_FUNC_CD(String AUTH_FUNC_CD) {
        this.AUTH_FUNC_CD = AUTH_FUNC_CD;
    }

    public float getAUTH_AMT() {
        return AUTH_AMT;
    }

    public void setAUTH_AMT(float AUTH_AMT) {
        this.AUTH_AMT = AUTH_AMT;
    }

    public float getTRN_TOT_AMT() {
        return TRN_TOT_AMT;
    }

    public void setTRN_TOT_AMT(float TRN_TOT_AMT) {
        this.TRN_TOT_AMT = TRN_TOT_AMT;
    }

    public String getAUTH_SYS_IND() {
        return AUTH_SYS_IND;
    }

    public void setAUTH_SYS_IND(String AUTH_SYS_IND) {
        this.AUTH_SYS_IND = AUTH_SYS_IND;
    }

}

