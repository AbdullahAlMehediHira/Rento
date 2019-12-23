package com.example.rento.Tenant;
import com.example.rento.Landlord.LandlordData;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class ClusterMarker implements ClusterItem{
    private LatLng position;
    private String title;
    private String snippet;
    private int iconPicture;
    private LandlordData landlordData;



    public ClusterMarker(LatLng position, String title, String snippet, int iconPicture, LandlordData landlordData){
        this.position=position;
        this.title=title;
        this.snippet=snippet;
        this.iconPicture= iconPicture;
        this.landlordData =landlordData;
    }
    public ClusterMarker(double lat, double lng) {
        position = new LatLng(lat, lng);
    }
    public ClusterMarker(double lat, double lng, String title, String snippet) {
        position = new LatLng(lat, lng);
        this.title=title;
        this.snippet=snippet;
    }


    @Override
    public LatLng getPosition() {
        return position;
    }
    public void setPosition(LatLng position){
        this.position = position;
    }

    @Override
    public String getTitle() {
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }

    @Override
    public String getSnippet() {
        return snippet;
    }

    public LandlordData getLandlordData() {
        return landlordData;
    }

    public void setLandlordData(LandlordData landlordData) {
        this.landlordData = landlordData;
    }

    public void seticonPicture(int iconPicture){
        this.iconPicture=iconPicture;
    }
    public int geticonPicture(){
        return iconPicture;
    }
}
