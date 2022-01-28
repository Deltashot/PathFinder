package com.app.CustomStuff;

import com.app.Objects.QueuePiece;

import java.util.Comparator;

public class CustomComparator implements Comparator<QueuePiece> {
    @Override
    public int compare(QueuePiece o1, QueuePiece o2) {
        return 3;
    }
}