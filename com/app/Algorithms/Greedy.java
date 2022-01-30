package com.app.Algorithms;

import com.app.DrawGrid;
import com.app.Objects.Piece;
import com.app.Objects.QueuePiece;


import java.util.*;

public class Greedy {
    /**
     * so the way that this algorithm work is:
     * the algorithm looks at all the neighbors, and it calculates the distance from itself to the finish, it does this
     * by taking his x and y pos and subtracting it from the end's x and y pos, for example if the start is located at
     * 5,10 and the end is located at 2,15 it's going do the following equation ((5-2)+(10-15))=3+5=8 distance away from
     * the end, NOTE it shouldn't support negative numbers so any negative number like in the case with 10-15 will be
     * converted to positive in this case 5, and then the algorithm does that for each of the neighbors and takes the one
     * which is the closest to the end (NOTE it stores the data of the previous neighbors) after it moves it checks if it
     * has any new neighbors calculates how far away they are from the end and takes the next closest and so on till it
     * reaches the end
     */

    static int[] dx = {1, -1, 0, 0};//right, left, NA, NA
    static int[] dy = {0, 0, 1, -1};//NA, NA, bottom, top

    private static int endX;
    private static int endY;


    public static void start(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed){
        gridObj.visualize_speed = visualizeSpeed;
        endX = endPiece.getX();
        endY = endPiece.getY();;

        Queue<QueuePiece> q = new PriorityQueue<>(new Comparator<QueuePiece>() {
            @Override
            public int compare(QueuePiece o1, QueuePiece o2) {
                Integer o1d = o1.getDistance();
                Integer o2d = o2.getDistance();
                return o1d.compareTo(o2d);
            }
        });
        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY(), calDis(startPiece.getX(), startPiece.getY())); //Start piece
        start.addParent(new ArrayList<>(), start);

        q.add(start);//Adding start to the queue since we're already visiting it
        int test = 0;
        while (!q.isEmpty()){
            test++;
            if (test == 500)
            {
                test = test;
            }
            List<QueuePiece> previous_ = Collections.unmodifiableList(q.peek().getPath());

            QueuePiece curr = q.peek();
            q.remove(curr);

            int curX = curr.getX();
            int curY = curr.getY();

            for (int i = 0; i < 4; i++) { //for each direction
                if ((curX + dx[i] >= 0 && curX + dx[i] < grid.size()) &&
                        (curY + dy[i] >= 0 && curY + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curX + dx[i];//Setting current x coordinate
                    int yc = curY + dy[i];//Setting current y coordinate
                    int type = grid.get(xc).get(yc).getType(); //the type of field that the

                    if (type == 0)//add the piece to the list for it to be processed later
                    {
                        QueuePiece temp = new QueuePiece(xc, yc, calDis(xc, yc));
                        temp.addParent(new ArrayList<>(previous_), temp);

                        if (q.stream().noneMatch(o -> o.getX() == xc && o.getY() == yc))
                            q.add(temp);//Adding current coordinates to the queue

                    } else if (type == 3) { //Destination found
                        //System.out.println("[DEBUG] printing shortest route" + curr.getPathString());
                        gridObj.DrawShortestPath(new ArrayList<>(curr.getPath()));
                        gridObj.visualize_speed = 0;
                        return;
                    }
                }
            }

            //move to the closest piece
            QueuePiece cPiece = q.peek(); //closest piece to the end
            int cPieceX = cPiece.getX();
            int cPieceY = cPiece.getY();
            grid.get(cPieceX).get(cPieceY).setType(4);//now BLOCKED

            //paint the piece
            gridObj.pieceForRepainting.add(grid.get(cPieceX).get(cPieceY));
            gridObj.paintImmediately(cPieceX * gridObj.getRectWid(),
                    cPieceY * gridObj.getRectHei(), gridObj.getRectWid(),
                    gridObj.getRectHei());

        }

        System.out.println("no route possible");
        gridObj.visualize_speed = 0;
    }




    /*
    public static void start(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed){
        gridObj.visualize_speed = visualizeSpeed;
        endX = endPiece.getX();
        endY = endPiece.getY();

        PriorityQueue<QueuePiece> q = new PriorityQueue<>();

        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY(), calDis(startPiece.getX(), startPiece.getY())); //Start piece
        start.AddParent(new ArrayList<>(), start);

        q.add(start);//Adding start to the queue since we're already visiting it
        int test = 0;
        while (q.peek() != null){
            test++;
            List<QueuePiece> previous_ = Collections.unmodifiableList(q.peek().getPath());

            QueuePiece curr = q.peek();
            q.remove(curr);

            int curX = curr.getX();
            int curY = curr.getY();

            for (int i = 0; i < 4; i++) { //for each direction
                if ((curX + dx[i] >= 0 && curX + dx[i] < grid.size()) &&
                        (curY + dy[i] >= 0 && curY + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curX + dx[i];//Setting current x coordinate
                    int yc = curY + dy[i];//Setting current y coordinate
                    int type = grid.get(xc).get(yc).getType(); //the type of field that the

                    if (type == 0)//add the piece to the list for it to be processed later
                    {
                        QueuePiece temp = new QueuePiece(xc, yc, calDis(xc, yc));
                        temp.AddParent(new ArrayList<>(previous_), temp);
                        q.add(temp);//Adding current coordinates to the queue
                        q.stream().sorted();

                    } else if (type == 3) { //Destination found
                        //System.out.println("[DEBUG] printing shortest route" + curr.getPathString());
                        gridObj.DrawShortestPath(new ArrayList<>(curr.getPath()));
                        gridObj.visualize_speed = 0;
                        return;
                    }
                }
            }

            //move to the closest piece
            QueuePiece cPiece = q.peek(); //closest piece to the end
            int cPieceX = cPiece.getX();
            int cPieceY = cPiece.getY();
            grid.get(cPieceX).get(cPieceY).setType(4);//now BLOCKED

            //paint the piece
            gridObj.pieceForRepainting.add(grid.get(cPieceX).get(cPieceY));
            gridObj.paintImmediately(cPieceX * gridObj.getRectWid(),
                    cPieceY * gridObj.getRectHei(), gridObj.getRectWid(),
                    gridObj.getRectHei());
        }
        System.out.println("no route possible");
        gridObj.visualize_speed = 0;
    }

     */

    private static int calDis(QueuePiece piece){ //calculate distance from current piece to end
        return calDis(piece.getX(), piece.getY());
    }

    private static int calDis(int x, int y){ //calculate distance from current piece to end
        return Math.abs(x - endX) + Math.abs(y - endY);
    }

    /*
    public static void startOld(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed){
        gridObj.visualize_speed = visualizeSpeed;
        endX = endPiece.getX();
        endY = endPiece.getY();;

        ArrayList<QueuePiece> q = new ArrayList<>();
        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY(), calDis(startPiece.getX(), startPiece.getY())); //Start piece
        start.addParent(new ArrayList<>(), start);

        q.add(start);//Adding start to the queue since we're already visiting it
        int test = 0;
        while (!q.isEmpty()){
            test++;
            LinkedList<QueuePiece> previous_ = Collections.unmodifiableList(q.get(0).getPath());

            QueuePiece curr = q.get(0);
            q.remove(curr);

            int curX = curr.getX();
            int curY = curr.getY();

            for (int i = 0; i < 4; i++) { //for each direction
                if ((curX + dx[i] >= 0 && curX + dx[i] < grid.size()) &&
                        (curY + dy[i] >= 0 && curY + dy[i] < grid.get(0).size())) {
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curX + dx[i];//Setting current x coordinate
                    int yc = curY + dy[i];//Setting current y coordinate
                    int type = grid.get(xc).get(yc).getType(); //the type of field that the

                    if (type == 0)//add the piece to the list for it to be processed later
                    {
                        QueuePiece temp = new QueuePiece(xc, yc, calDis(xc, yc));
                        temp.addParent(new ArrayList<>(previous_), temp);
                        q.add(temp);//Adding current coordinates to the queue

                    } else if (type == 3) { //Destination found
                        //System.out.println("[DEBUG] printing shortest route" + curr.getPathString());
                        gridObj.DrawShortestPath(curr.getPath());
                        gridObj.visualize_speed = 0;
                        return;
                    }
                }
            }

            q.sort((o1, o2) -> {
                Integer o1d = o1.getDistance();
                Integer o2d = o2.getDistance();
                return o1d.compareTo(o2d);
            });

            //move to the closest piece
            QueuePiece cPiece = q.get(0); //closest piece to the end
            int cPieceX = cPiece.getX();
            int cPieceY = cPiece.getY();
            grid.get(cPieceX).get(cPieceY).setType(4);//now BLOCKED

            //paint the piece
            gridObj.pieceForRepainting.add(grid.get(cPieceX).get(cPieceY));
            gridObj.paintImmediately(cPieceX * gridObj.getRectWid(),
                    cPieceY * gridObj.getRectHei(), gridObj.getRectWid(),
                    gridObj.getRectHei());

        }

        System.out.println("no route possible");
        gridObj.visualize_speed = 0;
    }



     */
}


