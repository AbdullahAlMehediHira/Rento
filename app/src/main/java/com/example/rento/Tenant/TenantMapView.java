package com.example.rento.Tenant;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.rento.Constants;
import com.example.rento.Landlord.RentPost;
import com.example.rento.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;


public class TenantMapView extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    GoogleMap mGoogelMap;
    TenantLocation mTenantPosition;
    private LatLngBounds mMapBoundary;
    String TAG="Tenant Map view";
    FirebaseFirestore mFireStore;
    FirebaseFirestore mDb;
//    CustomInfoWIndow mCustomInfo;


    private static ArrayList<RentPost> mRentPosts = new ArrayList<>();
//    private ClusterManager mClusterManager;
    private ClusterManager<ClusterMarker> mClusterManager;
    private MyClusterManagerRenderer mClusterManagerRenderer;
    private ArrayList<ClusterMarker> mClusterMarker= new ArrayList<>();
    private Location mRentPossition;

//    private void setCameraView(){
//        //overall map view window: .2+.2=.4
//        double bottomBound = mTenantPosition.get_geopoint().getLatitude()-.1;
//        double leftBound= mTenantPosition.get_geopoint().getLongitude()-.1;
//        double topBound = mTenantPosition.get_geopoint().getLatitude()+.1;
//        double rightBound = mTenantPosition.get_geopoint().getLongitude()+.1;
//
//        mMapBoundary = new LatLngBounds(
//                new LatLng(bottomBound,leftBound),
//                new LatLng(topBound,rightBound)
//        );
//        mGoogelMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary,15));
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = FirebaseFirestore.getInstance();
        retriveRentPosts();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.tenant_map_view, container, false);
        mMapView = (MapView)view.findViewById(R.id.map_view);

        initGoogleMap(savedInstanceState);

        //setRentPostPostion();
        return view;


    }
    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(Constants.MAPVIEW_BUNDLE_KEY);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(Constants.MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(Constants.MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        map.setMyLocationEnabled(true);
        mGoogelMap = map;
//        addMapMarkers();
        setUpClusterer();
    }


    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    // Declare a variable for the cluster manager.
//    private ClusterManager<MyItem> mClusterManager;

    private void setUpClusterer() {
        // Position the map.
//        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 10));
        mGoogelMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(23.814919, 90.426012), 15));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogelMap);

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        mGoogelMap.setOnCameraIdleListener(mClusterManager);
        mGoogelMap.setOnMarkerClickListener(mClusterManager);
        mGoogelMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(getActivity().getApplicationContext());
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(getActivity().getApplicationContext());
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(getActivity().getApplicationContext());
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });
//        mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<ClusterMarker>() {
//            @Override
//            public boolean onClusterItemClick(ClusterMarker item) {
//                Log.d("cluster item","clicked");
//                return true;
//            }
//        });

        // Add cluster items (markers) to the cluster manager.
        addMapMarkers();
    }

    private void addMapMarkers(){


        if(mGoogelMap!=null) {
            if (mClusterManager == null) {
                mClusterManager = new ClusterManager<ClusterMarker>(getActivity().getApplicationContext(), mGoogelMap);
                Log.d("guu", "habijabi");
            }
            if (mClusterManagerRenderer == null) {
                mClusterManagerRenderer = new MyClusterManagerRenderer(
                        getActivity(),
                        mGoogelMap,
                        mClusterManager

                );

                mClusterManager.setRenderer(mClusterManagerRenderer);
            }
        }

//
        mDb.collection("RentPosts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RentPost rentPost = document.toObject(RentPost.class);

                                int avater = R.drawable.rent_logo;//set default avater

                                double lat = rentPost.getgeopoint().getLatitude();
                                double lng = rentPost.getgeopoint().getLongitude();
                                Log.d("loopp", "lat: "+rentPost.getnumberofrooms());
                                String title = "Rent: "+rentPost.getcost()+ " TK";
                                String snippet = "Number of rooms: "+rentPost.getnumberofrooms()+
                                        "\nSquare Feet: "+rentPost.getsquarefeet()+
                                        "\nLandLord Email: "+rentPost.getlandlordData().getemail();
//                                Log.d("loopp", "lat: "+snippet);
//                                ClusterMarker offsetItem = new ClusterMarker(lat, lng);
                                ClusterMarker rentItem = new ClusterMarker(new LatLng(rentPost.getgeopoint().getLatitude(), rentPost.getgeopoint().getLongitude()),
                                    title,
                                    snippet,
                                    avater,
                                    rentPost.getlandlordData());
                                mClusterManager.addItem(rentItem);

                                Log.d(TAG, document.getId() + " => " + document.getData().get("geopoint"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

//                try {
//                    String snippet;
//                    snippet = "Number of rooms: "+rentPost.getnumberofrooms()+
//                            "\nSquare Feet: "+rentPost.getsquarefeet()+
//                            "\nLandLord Email: "+rentPost.getlandlordData().getemail();
//                    Log.d("Rent post","Latitude: "+rentPost.getgeopoint().getLatitude());
//
//
//                    }
//                    int avater = R.drawable.house;//set default avater
//                    try {
//                    } catch (NumberFormatException e) {
//                        //Log.d(TAG, "addMapMarkers: no avater for" + mRentPostlocation.getLandlordData().getEmail() + ", setting default");
//                    }
//                    ClusterMarker newClasterMarker;
//                    newClasterMarker = new ClusterMarker(
//                            new LatLng(rentPost.getgeopoint().getLatitude(), rentPost.getgeopoint().getLongitude()),
//                            rentPost.getcost(),
//                            snippet,
//                            avater,
//                            rentPost.getlandlordData()
//                    );
//                    mClusterMarker.add(newClasterMarker);
//                    mClusterManager.addItem(newClasterMarker);
//
//                } catch (NullPointerException e) {
//                    Log.e(TAG, "addMapMarkers: NullPointerException: " +e.getMessage());
//                }
//        setCameraView();
    }
//            mClusterManager.cluster();



//        }
//    }
    private void retriveRentPosts(){
//        mDb = FirebaseFirestore.getInstance();

        mDb.collection("RentPosts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    List<RentPost> rentPost = task.getResult().toObjects(RentPost.class);
//                    List<RentPost> rentPost = documentSnapshots.toObjects(Type.class);
                    mRentPosts.addAll(rentPost);
                    Log.d("Rentposts", mRentPosts.get(0).getgeopoint().toString());
                }

            }
        });

    }
    //Change it to tenant
//    private void setRentPostPostion(){
//        for(RentPost rentPost: mRentPosts){
//            mRentPostlocation =new Location(rentPost.getgeopoint().getLatitude(),
//                    rentPost.getgeopoint().getLongitude());
//        }
//    }

}
