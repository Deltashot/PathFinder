package com.app.algorithms;

import com.app.DrawGrid;
import com.app.Piece;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class BreadthFirst {
    static int[] dx={1,-1,0,0};//right, left, NA, NA
    static int[] dy={0,0,1,-1};//NA, NA, bottom, top

    public static void Start(Piece startPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj) {
        // static int r,c,s1,s2,f1,f2;//Rows, Columns, Start Coordinates, Finish Coordinates
        DrawGrid drawGrid = new DrawGrid();

        Queue<int[]> q = new LinkedList<int[]>();
        int[] start = {startPiece.getX(), startPiece.getY()};//Start Coordinates
        q.add(start);//Adding start to the queue since we're already visiting it
        //startPiece.setType(4);

        gridObj.pieceForRepainting.add(startPiece);
        gridObj.paintImmediately(startPiece.getX() * gridObj.rectWid, startPiece.getY() * gridObj.rectHei, gridObj.rectWid,
                gridObj.rectHei);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (q.peek() != null) {
            int[] curr = q.poll();//poll or remove. Same thing

            for (int i = 0; i < 4; i++)//for each direction
            {
                //checking up right down left
                if ((curr[0] + dx[i] >= 0 && curr[0] + dx[i] < grid.size()) && (curr[1] + dy[i] >= 0 && curr[1] + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curr[0] + dx[i];//Setting current x coordinate
                    int yc = curr[1] + dy[i];//Setting current y coordinate
                    if (grid.get(yc).get(xc).getType() == 3)//Destination found
                    {
                        //System.out.println(xc+" "+yc);
                        System.out.println("Shortest route found");
                        return;
                    } else if (grid.get(yc).get(xc).getType() == 0)//Movable. Can't return here again so setting it to 'B' now
                    {
                        //System.out.println(xc+" "+yc);
                        grid.get(yc).get(xc).setType(4);//now BLOCKED
                        int[] temp = {xc, yc};
                        q.add(temp);//Adding current coordinates to the queue

                        gridObj.pieceForRepainting.add(grid.get(yc).get(xc));
                        gridObj.paintImmediately(grid.get(yc).get(xc).getX() * gridObj.rectWid, grid.get(yc).get(xc).getY() * gridObj.rectHei, gridObj.rectWid,
                                gridObj.rectHei);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        System.out.println("no route possible");

    }
}
