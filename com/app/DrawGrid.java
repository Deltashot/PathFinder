package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawGrid extends JPanel {
    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<>();

    private int xAxisPieces;
    private int yAxisPieces;

    public final int rectWid = 50;
    public final int rectHei = rectWid;
    private final int rectX = rectWid;
    private final int rectY = rectHei;

    public Piece pieceForRepainting = null;
    private boolean gridDrawn = false;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (pieceForRepainting != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(pieceForRepainting.getColor());
            g2d.fill(pieceForRepainting.getRect());
            g2d.setColor(Color.black);
            g2d.draw(pieceForRepainting.getRect());
            pieceForRepainting = null;
        } else if (!gridDrawn) {
            DrawGrid(g);
            gridDrawn = true;
        } else
            System.out.println("[DEBUG] um trying to repaint something but there is nothing to repaint?");
        /**
         * @NOTE repainting the grid using DrawGrid is a bad idea because it will paint another grid on top of the first
         * one and add the elements to
         * @param pieces which is a really bad idea and will cause a memory leak
         */
    }

    private ArrayList<ArrayList<Piece>> DrawGrid(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if (!pieces.isEmpty())
            System.out.println("[ERROR] there are already pieces when creating the grid wtf?");

        for (int y = 0; y < yAxisPieces; y++) {
            ArrayList<Piece> tempArr = new ArrayList<>();
            for (int x = 0; x < xAxisPieces; x++) {
                Rectangle2D rect = new Rectangle2D.Double(x * rectX, y * rectY, rectWid, rectHei);
                g2d.setColor(Color.WHITE);
                g2d.fill(rect);
                g2d.setColor(Color.black);
                g2d.draw(rect);
                tempArr.add(new Piece(rect, x, y));
            }
            pieces.add(tempArr);
        }
        new GridListeners(pieces, this);
        return pieces;
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


            for (int y = 0; y < grid.size(); y++){
                for (int x = 0; x < grid.get(y).size(); x++){
                    rect = grid.get(y).get(x).getRect();
                    if (rect.contains(xPos, yPos)){
                        piece = grid.get(y).get(x);
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
                gridObj.pieceForRepainting = pressed;
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

