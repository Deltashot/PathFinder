package com.app;

import java.awt.geom.Rectangle2D;

public class Piece {
    public Rectangle2D rect;
    public int x = 0;
    public int y = 0;
    public int type = 0;

    public Piece(Rectangle2D rect, int x, int y){
        this.rect = rect;
        this.x = x;
        this.y = y;
    }

    public Piece(Rectangle2D rect, int x, int y, int type){
        this.rect = rect;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public Piece(Rectangle2D rect){
        this.rect = rect;
    }

    //TODO add the different types here and a function that returns the color of each and something else if needed
}
