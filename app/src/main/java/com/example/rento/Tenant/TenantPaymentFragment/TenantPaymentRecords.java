package com.example.rento.Tenant.TenantPaymentFragment;

import com.example.rento.Tenant.TenantData;

public class TenantPaymentRecords {
    private TenantData tenantData;
    String mounth;
    boolean isPayed;

    public TenantPaymentRecords(TenantData tenantData, String mounth, boolean isPayed) {
        this.tenantData = tenantData;
        this.mounth = mounth;
        this.isPayed = isPayed;
    }

    public TenantData gettenantData() {
        return tenantData;
    }

    public void setTenantData(TenantData tenantData) {
        this.tenantData = tenantData;
    }
    public boolean getPayed(){
        return isPayed;
    }
    public void setPayed(boolean isPayed){
        this.isPayed=isPayed;
    }

    public void setMounth(String mounth) {
        this.mounth = mounth;
    }

    public String getMounth() {
        return mounth;
    }
}
