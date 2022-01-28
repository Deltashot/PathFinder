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

    static int[] dx={1,-1,0,0};//right, left, NA, NA
    static int[] dy={0,0,1,-1};//NA, NA, bottom, top

    private static QueuePiece start;
    private static Piece end;

    public static void start(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed){
        gridObj.visualize_speed = visualizeSpeed;

        ArrayList<QueuePiece> q = new ArrayList<>();
        end = endPiece;
        start = new QueuePiece(startPiece.getX(), startPiece.getY(), calDis(startPiece.getX(), startPiece.getY())); //Start piece
        start.AddParent(new ArrayList<>(), start);

        q.add(start);//Adding start to the queue since we're already visiting it


        int count = 0;

        while (!q.isEmpty()){
            count++;
            ArrayList<QueuePiece> previous = q.get(q.size() - 1).getPath();
            List<QueuePiece> previous_ = Collections.unmodifiableList(new ArrayList<>(previous));

            QueuePiece curr = q.get(q.size() - 1);
            if (count >= 200) { //TODO remove this after the bug
                System.out.println("[ERROR] there is a bug the greedy algorithm, it loops indefinitely, fix it dumbass");
                break;
            }
            q.remove(curr);

            for (int i = 0; i < 4; i++){ //for each direction
                if ((curr.getX() + dx[i] >= 0 && curr.getX() + dx[i] < grid.size()) &&
                        (curr.getY() + dy[i] >= 0 && curr.getY() + dy[i] < grid.get(0).size())){
                    //Checked if x and y are correct. ALL IN 1 GO
                    int xc = curr.getX() + dx[i];//Setting current x coordinate
                    int yc = curr.getY() + dy[i];//Setting current y coordinate

                    if (grid.get(xc).get(yc).getType() == 0)//add the piece to the list for it to be processed later
                    {
                        QueuePiece temp = new QueuePiece(xc, yc, calDis(xc, yc));
                        temp.AddParent(new ArrayList<QueuePiece>(previous_), temp);
                        q.add(temp);//Adding current coordinates to the queue
                    }

                    else if (grid.get(xc).get(yc).getType() == 3)//Destination found
                    {
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
            grid.get(cPiece.getX()).get(cPiece.getY()).setType(4);//now BLOCKED

            //paint the piece
            gridObj.pieceForRepainting.add(grid.get(cPiece.getX()).get(cPiece.getY()));
            gridObj.paintImmediately(cPiece.getX() * gridObj.getRectWid(),
                    cPiece.getY() * gridObj.getRectHei(), gridObj.getRectWid(),
                    gridObj.getRectHei());

        }

        System.out.println("no route possible");
        gridObj.visualize_speed = 0;
    }

    private static int calDis(QueuePiece piece){ //calculate distance from current piece to end
        return calDis(piece.getX(), piece.getY());
    }


    private static int calDis(int x, int y){ //calculate distance from current piece to end
        return Math.abs(x - end.getX()) + Math.abs(y - end.getY());
    }

    public static void startOld(Piece startPiece, Piece endPiece, ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj, int visualizeSpeed) {
        gridObj.visualize_speed = visualizeSpeed;

        Queue<QueuePiece> q = new LinkedList<>();
        QueuePiece start = new QueuePiece(startPiece.getX(), startPiece.getY()); //Start piece
        QueuePiece end = new QueuePiece(endPiece.getX(), startPiece.getY()); //End piece
        start.AddParent(new ArrayList<>(), start);

        q.add(start);//Adding start to the queue since we're already visiting it

        while (q.peek() != null) {

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

                    if (grid.get(xc).get(yc).getType() == 0)//add to the movable list
                    {
                        grid.get(xc).get(yc).setType(4);//now BLOCKED
                        QueuePiece temp = new QueuePiece(xc, yc);
                        temp.AddParent(new ArrayList<QueuePiece>(previous_), temp);
                        q.add(temp);//Adding current coordinates to the queue

                        //paint the piece
                        gridObj.pieceForRepainting.add(grid.get(xc).get(yc));
                        gridObj.paintImmediately(temp.getX() * gridObj.getRectWid(),
                                temp.getY() * gridObj.getRectHei(), gridObj.getRectWid(),
                                gridObj.getRectHei());
                    }
                    else if (grid.get(xc).get(yc).getType() == 3)//Destination found
                    {
                        //System.out.println("[DEBUG] printing shortest route" + curr.getPathString());
                        gridObj.DrawShortestPath(curr.getPath());
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
