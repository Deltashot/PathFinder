package com.app.Objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class QueuePiece {
    private int x = 0;
    private int y = 0;
    private List<QueuePiece> visitedPieces = new ArrayList<>();
    private int distance; //distance from the finish
    //the piece type that the piece starts from, if the algorithm is bidirectional it will
    // have 2 start types (start piece and end piece)
    private int startType;

    public QueuePiece(int x, int y){
        this.x = x;
        this.y = y;
    }

    public QueuePiece(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    public QueuePiece(int x, int y, List<QueuePiece> visitedPieces, int distance, int startType) {
        this.x = x;
        this.y = y;
        this.visitedPieces = visitedPieces;
        this.distance = distance;
        this.startType = startType;
    }

    public QueuePiece(int x, int y, List<QueuePiece> visitedPieces, int startType) {
        this.x = x;
        this.y = y;
        this.visitedPieces = visitedPieces;
        this.startType = startType;
    }

    public QueuePiece(int x, int y, int startType, Boolean uselessthing) {
        this.x = x;
        this.y = y;
        this.startType = startType;
    }

    public void addParent(List<QueuePiece> previousPath, QueuePiece visited, int startType){
        //I am not sure how or why this works but hey it prevents a memory leak and crisis prevented
        visitedPieces = previousPath;
        visitedPieces.add(new QueuePiece(visited.getX(), visited.getY(), startType, false));
    }

    public void addParent(List<QueuePiece> previousPath, QueuePiece visited){
        //I am not sure how or why this works but hey it prevents a memory leak and crisis prevented
        visitedPieces = previousPath;
        visitedPieces.add(new QueuePiece(visited.getX(), visited.getY()));
    }

    public List<QueuePiece> getPath(){
        return visitedPieces;
    }

    public String getPathString(){
        StringBuilder builder = new StringBuilder();

        for (QueuePiece piece : visitedPieces){
            builder.append(" Piece ").append(piece.getX()).append(" ").append(piece.getY());
        }

        return builder.toString();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getStartType() {
        return startType;
    }

    public void setStartType(int startType) {
        this.startType = startType;
    }
}
