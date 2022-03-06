package com.app.Objects;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Piece {
    private Rectangle2D rect;
    private int x = 0;
    private int y = 0;
    private int type = 0;
    private int startType;

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

    public Rectangle2D getRect() {
        return rect;
    }

    public Color getColor(int type){
        return Colors(type);
    }

    public Color getColor(){
        return Colors(this.type);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStartType(int startType){
        this.startType = startType;
    }

    public Integer getStartType(){
        return startType;
    }

    public Integer getType() {
        return type;
    }



    private Color Colors(int type){
        /** type used for differentiating the type of the current cell
         *             type 0: empty cell (default value), white
         *             type 1: wall, darkGray
         *             type 2: start, red
         *             type 3: end, darkGreen
         *             type 4: already checked, lightBlue
         *             type 5: in queue for checking, darkBlue
         *             type 6: for displaying shortest path, yellow
         */

        Color tempCol = new Color(0,0,0);
        switch (type) {
            case 0:
                tempCol = Color.white;
                break;
            case 1:
                tempCol = new Color(64, 64, 64);
                break;
            case 2:
                tempCol = Color.RED;
                break;
            case 3:
                tempCol = new Color(0,128,0);
                break;
            case 4:
                tempCol = Color.BLUE;
                break;
            case 5:
                tempCol = new Color(0, 0, 128);
                break;
            case 6:
                tempCol = Color.yellow;
                break;
            default:
                System.out.println("[ERROR] the current type of cell " + type + " is not defined in code");
                type = -1;
                break;
        }
        return tempCol;
    }


    //TODO add the different types here and a function that returns the color of each and something else if needed
}
