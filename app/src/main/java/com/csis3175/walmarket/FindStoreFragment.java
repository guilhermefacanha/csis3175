package com.csis3175.walmarket;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.csis3175.walmarket.database.StoreDbHelper;
import com.csis3175.walmarket.entity.Store;
import com.csis3175.walmarket.service.AppLocationService;
import com.csis3175.walmarket.util.MessageUtil;

import java.util.Collections;
import java.util.List;

public class FindStoreFragment extends Fragment {

    MainActivity mainActivity;
    Button btnContinue;
    EditText txtPostalCode;
    ImageView imgFindPc;
    AppLocationService locationService;
    Geocoder geocoder;

    Location location;
    Address address;

    StoreDbHelper storeDbHelper;
    List<Store> stores;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_find_store, container, false);
        location = null;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        locationService = new AppLocationService(mainActivity);
        geocoder = new Geocoder(mainActivity);
        storeDbHelper = new StoreDbHelper(mainActivity);

        txtPostalCode = (EditText) mainActivity.findViewById(R.id.txtFindPostalCode);
        btnContinue = (Button) mainActivity.findViewById(R.id.btnContinue);
        imgFindPc = (ImageView) mainActivity.findViewById(R.id.imgFindPc);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStoresFromPostalCode();
                if (address != null){
                    SelectStoreFragment selectStoreFragment = new SelectStoreFragment(stores, txtPostalCode.getText().toString(), address);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frameContent, selectStoreFragment);
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(selectStoreFragment.getClass().getSimpleName());
                    ft.commit();
                }
            }
        });

        imgFindPc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPostalCodeOnCurrentLocation();
            }
        });
    }

    private void findPostalCodeOnCurrentLocation() {
        location = locationService.getLocation(LocationManager.GPS_PROVIDER);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            String locationAddress = "";
            try {
                List<Address> fromLocation = geocoder.getFromLocation(latitude, longitude, 10);
                if (fromLocation != null && fromLocation.size() > 0) {
                    txtPostalCode.setText(fromLocation.get(0).getPostalCode());
                    address = fromLocation.get(0);
                    locationAddress = fromLocation.get(0).getFeatureName() + ", " + fromLocation.get(0).getThoroughfare() + ", " + fromLocation.get(0).getLocality();
                }
            } catch (Exception e) {
                MessageUtil.addMessage("Unable to access current location from device GPS!", mainActivity);
            }

            MessageUtil.addMessage("Location acquired at: " + locationAddress, mainActivity);
        } else {
            MessageUtil.addMessage("Unable to access current location from device GPS!", mainActivity);
        }
    }

    private void getStoresFromPostalCode() {

        try {
            if (txtPostalCode.getText().toString().isEmpty())
                throw new Exception("Please fill out a location to find the nearest store!");
            stores = storeDbHelper.getAll();
            if (location == null) {
                String postalCode = txtPostalCode.getText().toString();
                List<Address> fromLocationName = geocoder.getFromLocationName(postalCode, 10);
                if (fromLocationName != null && fromLocationName.size() > 0) {
                    address = fromLocationName.get(0);
                    location = new Location("from");
                    location.setLatitude(address.getLatitude());
                    location.setLongitude(address.getLongitude());
                }
            }

            if (address == null)
                throw new Exception("Unable to get a valid address from location: " + txtPostalCode.getText().toString());

            calculateDistance();

        } catch (Exception e) {
            MessageUtil.addMessage(e.getMessage(), mainActivity);
        }

    }

    private void calculateDistance() {
        for(Store store : stores){
            Location l = new Location("point B");
            l.setLatitude(store.getLatitude());
            l.setLongitude(store.getLongitude());

            float distance = l.distanceTo(location);
            store.setDistance(distance);
        }

        Collections.sort(stores);
    }

}
