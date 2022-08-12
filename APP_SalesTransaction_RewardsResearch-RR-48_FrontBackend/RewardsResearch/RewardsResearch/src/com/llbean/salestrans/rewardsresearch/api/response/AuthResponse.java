package com.llbean.salestrans.rewardsresearch.api.response;
import java.util.*;


public class AuthResponse {
    Date PMD_AUTH_RSP_TS;
    float PMD_AUTH_AMT;
    String PMD_AUTH_REAS_CD;
    String AUTH_RSP_TYP_CD;
    String ISD_RSP_CD;
    float REMAIN_BAL_AMT;
    int PMT_MTHD_ID;
    String RSP_TXT_DSPL_DESC;
    String RSP_DESC;
    
    public Date getPMD_AUTH_RSP_TS() {
        return PMD_AUTH_RSP_TS;
    }

    public void setPMD_AUTH_RSP_TS(Date PMD_AUTH_RSP_TS) {
        this.PMD_AUTH_RSP_TS = PMD_AUTH_RSP_TS;
    }

    public float getPMD_AUTH_AMT() {
        return PMD_AUTH_AMT;
    }

    public void setPMD_AUTH_AMT(float PMD_AUTH_AMT) {
        this.PMD_AUTH_AMT = PMD_AUTH_AMT;
    }

    public String getPMD_AUTH_REAS_CD() {
        return PMD_AUTH_REAS_CD;
    }

    public void setPMD_AUTH_REAS_CD(String PMD_AUTH_REAS_CD) {
        this.PMD_AUTH_REAS_CD = PMD_AUTH_REAS_CD;
    }

    public String getAUTH_RSP_TYP_CD() {
        return AUTH_RSP_TYP_CD;
    }

    public void setAUTH_RSP_TYP_CD(String AUTH_RSP_TYP_CD) {
        this.AUTH_RSP_TYP_CD = AUTH_RSP_TYP_CD;
    }

    public String getISD_RSP_CD() {
        return ISD_RSP_CD;
    }

    public void setISD_RSP_CD(String ISD_RSP_CD) {
        this.ISD_RSP_CD = ISD_RSP_CD;
    }

    public float getREMAIN_BAL_AMT() {
        return REMAIN_BAL_AMT;
    }

    public void setREMAIN_BAL_AMT(float REMAIN_BAL_AMT) {
        this.REMAIN_BAL_AMT = REMAIN_BAL_AMT;
    }

    public int getPMT_MTHD_ID() {
        return PMT_MTHD_ID;
    }

    public void setPMT_MTHD_ID(int PMT_MTHD_ID) {
        this.PMT_MTHD_ID = PMT_MTHD_ID;
    }

    public String getRSP_TXT_DSPL_DESC() {
        return RSP_TXT_DSPL_DESC;
    }

    public void setRSP_TXT_DSPL_DESC(String RSP_TXT_DSPL_DESC) {
        this.RSP_TXT_DSPL_DESC = RSP_TXT_DSPL_DESC;
    }

    public String getRSP_DESC() {
        return RSP_DESC;
    }

    public void setRSP_DESC(String RSP_DESC) {
        this.RSP_DESC = RSP_DESC;
    }
    
    
}