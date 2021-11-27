package com.app;

import com.app.Objects.Piece;
import com.app.Objects.QueuePiece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawGrid extends JPanel {
    protected ArrayList<ArrayList<Piece>> gridPieces = new ArrayList<>();
    protected Piece startPiece;

    private int xAxisPieces;
    private int yAxisPieces;

    public final int rectWid = Settings.RECT_WID;
    public final int rectHei = rectWid;
    private final int rectX = rectWid;
    private final int rectY = rectHei;

    public ArrayList<Piece> pieceForRepainting = new ArrayList<>();
    private boolean gridDrawn = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!pieceForRepainting.isEmpty()) {
            Graphics2D g2d = (Graphics2D) g;
            for (Piece tempPiece : pieceForRepainting) {
                g2d.setColor(tempPiece.getColor());
                g2d.fill(tempPiece.getRect());
                g2d.setColor(Color.black);
                g2d.draw(tempPiece.getRect());
            }
            pieceForRepainting.clear();
            //wait some time so it doesn't go tooo fast
            try {
                Thread.sleep(Settings.VISUALIZE_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (!gridDrawn) {
            DrawGrid(g);
            gridDrawn = true;
            drawStartPositions(g);
        } else
            System.out.println("[DEBUG] um trying to repaint something but there is nothing to repaint?");

        /**
         * @NOTE repainting the grid using DrawGrid is a bad idea because it will paint another grid on top of the first
         * one and add the elements to
         * @param pieces which is a really bad idea and will cause a memory leak
         */
    }

    private void drawStartPositions(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Rectangle2D tempRect;

        Piece startPiece = gridPieces.get(0).get(0);
        startPiece.setType(2);
        tempRect = startPiece.getRect();
        g2d.setColor(startPiece.getColor());
        g2d.fill(tempRect);
        g2d.setColor(Color.black);
        g2d.draw(tempRect);
        this.startPiece = startPiece;

        Piece endPiece = gridPieces.get(xAxisPieces - 1).get(yAxisPieces - 1);
        endPiece.setType(3);
        tempRect = endPiece.getRect();
        g2d.setColor(endPiece.getColor());
        g2d.fill(tempRect);
        g2d.setColor(Color.black);
        g2d.draw(tempRect);
    }

    private ArrayList<ArrayList<Piece>> DrawGrid(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if (!gridPieces.isEmpty())
            System.out.println("[ERROR] there are already pieces when creating the grid wtf?");

        for (int x = 0; x < xAxisPieces; x++) {
            ArrayList<Piece> tempArr = new ArrayList<>();
            for (int y = 0; y < yAxisPieces; y++) {
                Rectangle2D rect = new Rectangle2D.Double(x * rectX, y * rectY, rectWid, rectHei);
                g2d.setColor(Color.WHITE);
                g2d.fill(rect);
                g2d.setColor(Color.black);
                g2d.draw(rect);
                tempArr.add(new Piece(rect, x, y));
            }
            gridPieces.add(tempArr);
        }
        new GridListeners(gridPieces, this);
        return gridPieces;
    }

    // create the GUI explicitly on the Swing event thread
    public void createAndShowGui(int xAxisPieces_, int yAxisPieces_) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                xAxisPieces = xAxisPieces_;
                yAxisPieces = yAxisPieces_;
            }
        });
    }

    public void DrawShortestPath(ArrayList<ArrayList<Piece>> grid, ArrayList<QueuePiece> path){
        for (int i = 1; i < path.size(); i++){
            QueuePiece curPiece = path.get(i);

            grid.get(curPiece.getX()).get(curPiece.getY()).setType(6);//display shortest path type

            pieceForRepainting.add(grid.get(curPiece.getX()).get(curPiece.getY()));
            paintImmediately(curPiece.getX() * rectWid, curPiece.getY() * rectHei, rectWid,
                    rectHei);
            try {
                Thread.sleep(Settings.SHORTEST_VISUALIZE_SPEED);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class GridListeners implements MouseListener, MouseMotionListener{
        private ArrayList<ArrayList<Piece>> grid;
        private DrawGrid gridObj;
        private Graphics2D g2d;
        private Piece lastPressed;
        private boolean pressedLeftClick;

        GridListeners(ArrayList<ArrayList<Piece>> grid, DrawGrid gridObj){
            this.grid = grid;
            this.gridObj = gridObj;
            gridObj.addMouseListener(this);
            gridObj.addMouseMotionListener(this);
        }


        private Piece PressedPiece(int xPos, int yPos){
            Rectangle2D rect;
            Piece piece = null;

            for (int x = 0; x < grid.size(); x++){
                for (int y = 0; y < grid.get(x).size(); y++){
                    rect = grid.get(x).get(y).getRect();
                    if (rect.contains(xPos, yPos)){
                        piece = grid.get(x).get(y);
                        return piece;
                    }
                }
            }
            return null;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            //System.out.println("Holding mouse");

            /**
             * @TODO seems to be getting the wrong mouse position or something
             *
             */
            PiecePressed(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            PiecePressed(e);
        }

        private void PiecePressed(MouseEvent e) {
            if (e.getButton() == 1 || pressedLeftClick) {
                pressedLeftClick = true;
                Piece pressed = PressedPiece(e.getX(), e.getY());

                if (PressedPiece(e.getX(), e.getY()) == lastPressed)
                    return;

                if (pressed.getType() == 0) {
                    pressed.setType(1);
                } else {
                    pressed.setType(0);
                }
                gridObj.pieceForRepainting.add(pressed);
                gridObj.repaint(pressed.getX() * gridObj.rectWid, pressed.getY() * gridObj.rectHei, gridObj.rectWid,
                        gridObj.rectHei);
                lastPressed = pressed;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            lastPressed = null;
            pressedLeftClick = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }
    }
}

