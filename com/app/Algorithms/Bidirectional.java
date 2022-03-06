package com.app.Algorithms;

import com.app.DrawGrid;
import com.app.Objects.Piece;
import com.app.Objects.QueuePiece;

import java.io.Console;
import java.util.*;

public class Bidirectional{
    static int[] dx={1,-1,0,0};//right, left, NA, NA
    static int[] dy={0,0,1,-1};//NA, NA, bottom, top

    public static void start(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed){
        gridObj.visualize_speed = visualizeSpeed;

        Queue<QueuePiece> q = new LinkedList<>();
        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY(), 2, false);
        QueuePiece end = new QueuePiece(endPiece.getX(), endPiece.getY(), 3, false);
        start.addParent(new ArrayList<>(), start);
        end.addParent(new ArrayList<>(), end);

        q.add(end);
        q.add(start);

        while (q.peek() != null) {
            List<QueuePiece> previous = Collections.unmodifiableList(new ArrayList<>(q.peek().getPath())); //immutable and NO YOU CAN'T MAKE THIS MUTABLE IT WILL BREAK ANYTHING

            QueuePiece curr = q.poll();//poll or remove. Same thing
            int curX = curr.getX();
            int curY = curr.getY();
            for (int i = 0; i < 4; i++)//for each direction
            {
                if ((curX + dx[i] >= 0 && curX + dx[i] < grid.size()) &&
                        (curY + dy[i] >= 0 && curY + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curX + dx[i];//Setting current x coordinate
                    int yc = curY + dy[i];//Setting current y coordinate
                    int type = grid.get(xc).get(yc).getType();//type of current field
                    Piece tempPiece = grid.get(xc).get(yc);

                    if (type == 0)//Movable. Can't return here again so setting it to 'Blocked' now
                    {
                        tempPiece.setStartType(curr.getStartType());
                        tempPiece.setType(4);//now BLOCKED
                        QueuePiece temp = new QueuePiece(xc, yc);
                        temp.addParent(new ArrayList<>(previous), temp);
                        temp.setStartType(curr.getStartType());
                        q.add(temp);//Adding current coordinates to the queue

                        //paint the piece
                        gridObj.pieceForRepainting.add(grid.get(xc).get(yc));
                        gridObj.paintImmediately(temp.getX() * gridObj.getRectWid(),
                                temp.getY() * gridObj.getRectHei(), gridObj.getRectWid(),
                                gridObj.getRectHei());
                    } else if (((curr.getStartType() == 2 && tempPiece.getStartType() == 3) || (curr.getStartType() == 3 &
                            tempPiece.getStartType() == 2)) && type == 4) { //Destination found and top tier italiano spaghetti here
                        gridObj.DrawShortestPath(new ArrayList<>(curr.getPath()));
                        boolean success = false;
                        for (QueuePiece qe : q){
                            if (qe.getX() == xc && qe.getY() == yc){
                                ArrayList<QueuePiece> path = new ArrayList<>(qe.getPath());
                                Collections.reverse(path);
                                path.add(0, new QueuePiece(-1, -1)); //my italian spaghetti forced me to do this
                                path.remove(path.size() - 1);
                                gridObj.DrawShortestPath(path);
                                success = true;
                                break;
                            }
                        }
                        if (success = false){
                            System.out.println("can't find the tempiece and only half the shortest path will be drawn");
                        }
                        gridObj.visualize_speed = 0;
                        return;
                    }
                }
            }
        }
        System.out.println("no route possible");
        gridObj.visualize_speed = 0;
    }
}