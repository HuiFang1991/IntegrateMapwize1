package com.deveritec;

import android.location.Location;

import com.decawave.Position;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.Iterator;

import io.indoorlocation.core.IndoorLocation;
import io.indoorlocation.core.IndoorLocationProvider;
import io.mapwize.mapwizeformapbox.model.LatLngFloor;

public class DeveritecIndoorLocationProvider extends IndoorLocationProvider {

    public static boolean onDidLoadFinished = false;
    private boolean isStarted = false;

    public DeveritecIndoorLocationProvider(){
        super();
    }

    public void displayingLocationChangeTestDemo(){
        ArrayList<Position> positionList = PositionMap.fakeDataGeneration();
        while(isStarted){
            Iterator iterator = positionList.iterator();
            while(iterator.hasNext()){
                Object pos = iterator.next();
                LatLng latlng = CoordinateCalculator.position2CoordinateDemo((Position) pos);
                LatLngFloor latlngfloor = new LatLngFloor(latlng.getLongitude(), latlng.getLatitude(), 3.0);
                IndoorLocation indoorLocation = convertLatLngFloor2IndoorLocation(latlngfloor);
                dispatchIndoorLocationChange(indoorLocation);
            }
        }
    }



    public void setIndoorLocation(IndoorLocation indoorLocation) {
        dispatchIndoorLocationChange(indoorLocation);
    }

    public IndoorLocation convertLatLngFloor2IndoorLocation(LatLngFloor latLngFloor){
        Location location = new Location(getName());
        location.setLatitude(latLngFloor.getLatitude());
        location.setLongitude(latLngFloor.getLongitude());
        location.setTime(System.currentTimeMillis());
        IndoorLocation indoorLocation = new IndoorLocation(location, latLngFloor.getFloor());
        return indoorLocation;
    }


    @Override
    public boolean supportsFloor() {
        return false;
    }

    @Override
    public void start() {
         isStarted = true;
        displayingLocationChangeTestDemo();
    }

    @Override
    public void stop() {
        this.isStarted = false;

    }

    @Override
    public boolean isStarted() {
        return false;
    }


}
