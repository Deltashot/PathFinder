package com.app.algorithms;

import java.util.ArrayList;

public class QueueNode {
    private int x = 0;
    private int y = 0;
    private ArrayList<QueueNode> visitedPieces = new ArrayList<>();

    QueueNode(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void AddParent(QueueNode visited){
        visitedPieces.add(visited);
    }

    public ArrayList<QueueNode> getPath(){
        return visitedPieces;
    }
}
