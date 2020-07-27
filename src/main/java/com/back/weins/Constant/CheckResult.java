package com.back.weins.Constant;

import io.jsonwebtoken.Claims;

public class CheckResult {
    int errCode;
    boolean success;
    Claims claims;
    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode){
        this.errCode = errCode;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Claims getClaims() {
        return claims;
    }

    public void setClaims(Claims claims) {
        this.claims = claims;
    }
}
