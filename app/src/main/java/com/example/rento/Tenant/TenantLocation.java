package com.example.rento.Tenant;

import android.os.Parcel;

import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class TenantLocation {
    private GeoPoint geoPoint;
    private @ServerTimestamp Date timestamp;
    private TenantData tenantData;

    public TenantLocation(){

    }
    public TenantLocation(GeoPoint geoPoint, Date timestamp, TenantData tenantData){
        this.geoPoint=geoPoint;
        this.timestamp = timestamp;
        this.tenantData = tenantData;
    }
    protected TenantLocation(Parcel in) {
        tenantData= in.readParcelable(TenantData.class.getClassLoader());
    }

    public GeoPoint get_geopoint(){
        return geoPoint;
    }
    public void set_geopoint(GeoPoint geopoint){
        this.geoPoint=geopoint;
    }
    public Date get_time_stamp(){
        return timestamp;
    }
    public void set_time_stamp(Date timestamp){
        this.timestamp=timestamp;
    }
    public TenantData get_tenant_data(){
        return tenantData;
    }
    public void set_tenant_data(TenantData tenantData){
        this.tenantData=tenantData;
    }
    @Override
    public String toString(){
        return "UserLocation{" +
                "geopoint=" + geoPoint +
                ", timestamp= '"+ timestamp+'\''+
                ", TenantData=" + tenantData+'}';
    }

}
