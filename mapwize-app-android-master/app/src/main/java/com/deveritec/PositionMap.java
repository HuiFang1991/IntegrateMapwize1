package com.deveritec;

import com.decawave.Position;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PositionMap {

    static Map<Long, LatLng> array = new HashMap<>();
    public static LatLng Origin = new LatLng(51.034712, 13.711961);
    static private LatLng fakeAnchorCoordinate1 = new LatLng(51.034789, 13.712084);
    static private LatLng fakeAnchorCoordinate2 = new LatLng(51.034814, 13.712112);
    static private LatLng fakeAnchorCoordinate3 = new LatLng(51.034764, 13.712221);
    static private LatLng fakeAnchorCoordinate4 = new LatLng(51.034745, 13.712192);

    static private Long ID1 = Long.valueOf(1);
    static private Long ID2 = Long.valueOf(2);
    static private Long ID3 = Long.valueOf(3);
    static private Long ID4 = Long.valueOf(4);
    public static  NodeInfo fakeAnchor1 = new NodeInfo(ID1, fakeAnchorCoordinate1);
    public static  NodeInfo fakeAnchor2 = new NodeInfo(ID2, fakeAnchorCoordinate2);
    public static  NodeInfo fakeAnchor3 = new NodeInfo(ID3, fakeAnchorCoordinate3);
    public static  NodeInfo fakeAnchor4 = new NodeInfo(ID4, fakeAnchorCoordinate4);

    static public Map<Long, LatLng> createFakeAnchorPoints(){

        array.put(ID1, fakeAnchorCoordinate1);
        array.put(ID2, fakeAnchorCoordinate2);
        array.put(ID3, fakeAnchorCoordinate3);
        array.put(ID4, fakeAnchorCoordinate4);
        return array;
    }

    static public ArrayList<Position> fakeDataGeneration(){
        ArrayList<Position> result = new ArrayList<>();
        Position pos = new Position(0,0,0,(byte) 100);
        for (int i = 0; i<20; i++){
            int x = randomNumberGeneration(10, 1); // range [1,10]
            int y = randomNumberGeneration(10, 1);
            pos.x = x;
            pos.y = y;
            result.add(pos);
        }

        return result;
    }

    static public int randomNumberGeneration(int scaleFactor, int minimum){

        int result = (int)(Math.random()*scaleFactor+minimum);
        return result;
    }
//    public LatLng getNodeCoordinate(Long Id){return array.get(Id);}

}

