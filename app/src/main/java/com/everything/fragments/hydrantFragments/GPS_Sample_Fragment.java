package com.everything.fragments.hydrantFragments;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.everything.R;
import com.everything.fragments.BaseFragment;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;

/**
 * Created by Mirek on 2016-03-18.
 */
public class GPS_Sample_Fragment extends BaseFragment implements LocationListener {

    private static final String TAG = "tag";
    private static final String LOCALIZATION = "localization";
    @Bind(R.id.latitude_tv)
    TextView latitudeTv;
    @Bind(R.id.longtitude_tv)
    TextView longtitudeTv;
    @Bind(R.id.country_tv)
    TextView countryTv;
    @Bind(R.id.city_tv)
    TextView cityTv;

    private LocationManager locationManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.fragmentLayout = R.layout.gps_sample;

        View view = inflater.inflate(fragmentLayout, container, false);
        ButterKnife.bind(this, view);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsEnabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 0.1f, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        double longit = location.getLongitude();
        double latitu = location.getLatitude();
        String longitude = "Longitude: " + longit;
        String latitude = "Latitude: " + latitu;

        longtitudeTv.setText(longitude);
        latitudeTv.setText(latitude);

        String cityName = "";
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitu, longit, 1);
            if (addresses.size() > 0) {
                String countryName = addresses.get(0).getCountryName();
                String city = addresses.get(0).getLocality();

                countryTv.setText(countryName);
                cityTv.setText(city);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "status changed");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "provider disabled");
    }
}
