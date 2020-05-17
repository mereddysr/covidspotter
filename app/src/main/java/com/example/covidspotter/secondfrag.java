package com.example.covidspotter;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class secondfrag extends Fragment
{

    View view;
    FloatingActionButton floatingActionButton;
    private static secondfrag INSTANCE=null;
    CardView cssf_red,cssf_orange,cssf_green,tesb,cssf_lab,cssf_food,cssf_hos,cssf_pol,cssf_help;
String Redbtn="Red",Orbtn="Orange",Greenbtn="Green",lab="CoVID-19 Testing Lab",food="Free Food",hos="Hospitals and Centers",pol="Police"
    ,help="Government Helpline";


    public secondfrag(){

    }

    public static secondfrag getINSTANCE() {
        if(INSTANCE==null)
            INSTANCE=new secondfrag();
        return INSTANCE;}


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.secondfrag,container,false);
        floatingActionButton=view.findViewById(R.id.fab);
        cssf_red=view.findViewById(R.id.cs_sf_red);
        cssf_orange=view.findViewById(R.id.cs_sf_or);
        cssf_green=view.findViewById(R.id.cs_sf_gre);
        tesb=view.findViewById(R.id.testbtn);
        cssf_food=view.findViewById(R.id.cs_sf_food);

        cssf_help=view.findViewById(R.id.cs_sf_helpline);

        cssf_hos=view.findViewById(R.id.cs_sf_hos);
        cssf_lab=view.findViewById(R.id.cs_sf_lab);
        cssf_pol=view.findViewById(R.id.cs_sf_police);



        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), MapsActivity.class);
        startActivity(intent);

    }
});
cssf_red.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
Intent intent=new Intent(getContext(),redzone.class);
intent.putExtra("Zone",Redbtn);
startActivity(intent);
    }
});

        cssf_orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),redzone.class);
                intent.putExtra("Zone",Orbtn);
                startActivity(intent);
            }
        });

        cssf_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),redzone.class);
                intent.putExtra("Zone",Greenbtn);
                startActivity(intent);
            }
        });
        tesb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),testdet.class);
                startActivity(intent);
            }
        });

        cssf_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),resdet.class);
                intent.putExtra("reso",food);
                startActivity(intent);
            }
        });
        cssf_pol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),resdet.class);
                intent.putExtra("reso",pol);
                startActivity(intent);
            }
        });
        cssf_lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),resdet.class);
                intent.putExtra("reso",lab);
                startActivity(intent);
            }
        });
        cssf_hos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),resdet.class);
                intent.putExtra("reso",hos);
                startActivity(intent);
            }
        });
        cssf_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),resdet.class);
                intent.putExtra("reso",help);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);



    }




}
