package com.app.algorithms;

import com.app.DrawGrid;
import com.app.Piece;

import java.util.*;

public class BreadthFirst{
    static int[] dx={1,-1,0,0};//right, left, NA, NA
    static int[] dy={0,0,1,-1};//NA, NA, bottom, top

    public static void Start(Piece startPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj) {
        // static int r,c,s1,s2,f1,f2;//Rows, Columns, Start Coordinates, Finish Coordinates
        Queue<QueuePiece> q = new LinkedList<>();
        //int[] start = {startPiece.getX(), startPiece.getY()};//Start Coordinates
        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY());
        start.AddParent(new ArrayList<>(), start);

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
            /**
             * creating an unmodifiable instance of the last list selected, so it doesn't modify when for example if
             * there is a grid with this layout, where S is start, E is empty and F is finish:
             *
             *                                  S E
             *                                  E F
             *
             * when the list starts at S it moves to right and E is added to the list so if we move down we get the list
             * with S and E but we should be getting only S instead, so this is what this code does, only getting S
             * instead of the all passed elements in the lists
             */

            ArrayList<QueuePiece> previous = q.peek().getPath();
            List<QueuePiece> previous_ = Collections.unmodifiableList(new ArrayList<>(previous));

            QueuePiece curr = q.poll();//poll or remove. Same thing


            for (int i = 0; i < 4; i++)//for each direction
            {
                if ((curr.getX() + dx[i] >= 0 && curr.getX() + dx[i] < grid.size()) &&
                        (curr.getY() + dy[i] >= 0 && curr.getY() + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curr.getX() + dx[i];//Setting current x coordinate
                    int yc = curr.getY() + dy[i];//Setting current y coordinate
                    if (grid.get(xc).get(yc).getType() == 3)//Destination found
                    {
                        //System.out.println("[DEBUG] printing shortest route" + curr.getPathString());
                        gridObj.DrawShortestPath(grid, curr.getPath());
                        return;
                    } else if (grid.get(xc).get(yc).getType() == 0)//Movable. Can't return here again so setting it to 'B' now
                    {
                        grid.get(xc).get(yc).setType(4);//now BLOCKED
                        QueuePiece temp = new QueuePiece(xc, yc);
                        temp.AddParent(new ArrayList<QueuePiece>(previous_), temp);
                        q.add(temp);//Adding current coordinates to the queue

                        //paint the piece
                        gridObj.pieceForRepainting.add(grid.get(xc).get(yc));
                        gridObj.paintImmediately(temp.getX() * gridObj.rectWid,
                                temp.getY() * gridObj.rectHei, gridObj.rectWid,
                                gridObj.rectHei);

                        //wait some time so it doesn't go tooo fast
                        try {
                            Thread.sleep(25);
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
