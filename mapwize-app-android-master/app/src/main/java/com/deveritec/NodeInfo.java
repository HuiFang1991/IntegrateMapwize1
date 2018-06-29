package com.deveritec;

import com.mapbox.mapboxsdk.geometry.LatLng;

public class NodeInfo {
    Long nodeID;
    LatLng nodeCoordinate;

    public NodeInfo(Long ID, LatLng GPS){
        this.nodeID = ID;
        this.nodeCoordinate = GPS;
    }


}

