package com.example.rento;

import android.app.Application;

import com.example.rento.Tenant.TenantData;

public class TenantDataClient extends Application {
    private TenantData tenantData=null;

    public TenantData getTenantData(){
        return tenantData;
    }
    public void setTenantData(TenantData tenantData){
        this.tenantData=tenantData;
    }
}
