package com.deveritec;

import com.decawave.ComputedPosition;
import com.decawave.Position;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;

public class CoordinateCalculator {


    static final double a = 6378137.0; //one of the axis of earth ellipsoid, unit: meter
    static final double b = 6356752.3142; //one of the axis of earth ellipsoid, unit: meter
    static final double e = (a*a-b*b)/(a*a);
    static double phiDegree = PositionMap.Origin.getLatitude();
    static double phi = Math.toRadians(phiDegree);
    // length of a degree
    static double deltaLon = Math.PI*a*Math.cos(phi)/(180*Math.sqrt(1-Math.pow(e*Math.sin(phi),2))); //according to https://en.wikipedia.org/wiki/Longitude#Length_of_a_degree_of_longitude
    static double deltaLat = (Math.PI*a*(1-e*e))/(180*Math.pow(1-Math.pow(e*Math.sin(phi),2), 1.5));

    // Method needs the result of Autopositioning algorithm
    public LatLng position2Coordinate (LatLng origin, Long nodeID, ComputedPosition cp){

        LatLng result = null;


        //acquire longitude and latitude of the first two nodes
        Long nodeId1 = cp.fromNodes[0];
        Long nodeId2 = cp.fromNodes[1];
        PositionMap.createFakeAnchorPoints();
        LatLng nodeCoordinate1 = PositionMap.array.get(nodeId1);
        LatLng nodeCoordinate2 = PositionMap.array.get(nodeId2);

        //calculate the rotation angle
        double node1x = nodeCoordinate1.getLongitude();
        double node1y = nodeCoordinate1.getLatitude();

        double node2x = nodeCoordinate2.getLongitude();
        double node2y = nodeCoordinate2.getLatitude();

        double alpha = calculateRotationAngle(node1x,node1y,node2x,node2y);
        //A0*T=A ---> A0 = A* Inverse(T)
        double sa = Math.sin(alpha);
        double ca = Math.cos(alpha);

        //acquire the computed position
        double x = cp.position.x;
        double y = cp.position.y;

        //rotate back -----> North: positive y, East: positive x
        double xNormal = x*ca+y*sa;
        double yNormal = x*(-sa)+y*ca;

        //calculate the coordinate offset
        double xOffset = (node1x - origin.getLongitude())*deltaLon;
        double yOffset = (node1y - origin.getLatitude())*deltaLat;

        //calculate the (x,y) in original coordinate system
        double xOrigin = xNormal + xOffset;
        double yOrigin = yNormal + yOffset;

        //convert the (x,y) to longitude and latitude
        double finallongitude = xOrigin/deltaLon;
        double finallatitude = yOrigin/deltaLat;

        result.setLongitude(finallongitude);
        result.setLatitude(finallatitude);
        return result;
    }

    // This method doesn't need autopositioning algorithm
    // Instead, This method below has prerequisites
    // coordinate origin is fixed on fakeAnchor1
    // coordinate x axis is determined by fakeAnchor2
    public static LatLng position2CoordinateDemo(Position position){

        LatLng result = new LatLng();
        double x1 = PositionMap.fakeAnchor1.nodeCoordinate.getLongitude();
        double y1 = PositionMap.fakeAnchor1.nodeCoordinate.getLatitude();

        double x2 = PositionMap.fakeAnchor2.nodeCoordinate.getLongitude();
        double y2 = PositionMap.fakeAnchor2.nodeCoordinate.getLatitude();

        double alpha = calculateRotationAngle(x1,y1,x2,y2);

        Position positionNormal = rotateBack(position,alpha);
        double LonDiff = positionNormal.x / deltaLon;
        double LatDiff = positionNormal.y / deltaLat;
        result.setLongitude(PositionMap.fakeAnchor1.nodeCoordinate.getLongitude()+LonDiff);
        result.setLatitude(PositionMap.fakeAnchor1.nodeCoordinate.getLatitude()+LatDiff);
        return result;

    }


    public static double calculateRotationAngle(double x1, double y1, double x2, double y2){
       double alpha = Math.atan((y2-y1)*deltaLon/((x2-x1)*deltaLon));
       return alpha;
    }

    //rotate angle must expressed in radian
    public static Position rotateBack(Position position,double rotateAngle){
        Position result = new Position();
        //A0*T=A ---> A0 = A* Inverse(T)
        double sa = Math.sin(rotateAngle);
        double ca = Math.cos(rotateAngle);

        //acquire the computed position
        double x = position.x;
        double y = position.y;

        //rotate back -----> North: positive y, East: positive x
        double xNormal = x*ca+y*sa;
        double yNormal = x*(-sa)+y*ca;

        result.x = Math.toIntExact(Math.round(xNormal));
        result.y = Math.toIntExact(Math.round(yNormal));

        return result;
    }
}
