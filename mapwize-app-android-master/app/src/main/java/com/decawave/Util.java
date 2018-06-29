package com.decawave;

import android.support.annotation.NonNull;

public class Util {

    @NonNull
    public static String shortenNodeId(long number, boolean prepend0x) {
        return (prepend0x ? "0x…" : "") + String.format("%04X", (short) number);
    }

    public static int distance(int x, int y) {
        return (int) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) + 0.5);
    }

    public static int nodeDistance(Position p1, Position p2) {
        return distance(p2.x - p1.x, p2.y - p1.y);
    }
}