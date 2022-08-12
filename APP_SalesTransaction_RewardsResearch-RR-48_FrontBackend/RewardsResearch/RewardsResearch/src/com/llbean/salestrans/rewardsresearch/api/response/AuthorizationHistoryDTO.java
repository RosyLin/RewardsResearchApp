package com.llbean.salestrans.rewardsresearch.api.response;
import java.util.*;


public class AuthorizationHistoryDTO {
    
    AuthRequest[] authRequest;
    AuthResponse[] authResponse;

    public AuthRequest[] getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(AuthRequest[] authRequest) {
        this.authRequest = authRequest;
    }

    public AuthResponse[] getAuthResponse() {
        return authResponse;
    }

    public void setAuthResponse(AuthResponse[] authResponse) {
        this.authResponse = authResponse;
    }


}