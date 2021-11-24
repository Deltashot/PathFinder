package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawGrid extends JPanel {
    boolean drawWorld;

    private ArrayList<ArrayList<Piece>> pieces = new ArrayList<>();

    private int xAxisPieces;
    private int yAxisPieces;

    public final int rectWid = 50;
    public final int rectHei = rectWid;
    private final int rectX = rectWid;
    private final int rectY = rectHei;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.RED);
        //g.fillRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        //g.setColor(Color.BLACK);
        //g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        DrawGrid(g);

        if (drawWorld)
            g.drawString("Hello", 50, 50);

        if (!drawWorld)
            g.drawString("World", 10, 10);
    }

    private ArrayList<ArrayList<Piece>> DrawGrid(Graphics g){
        for (int y = 0; y < yAxisPieces; y++) {
            ArrayList<Piece> tempArr = new ArrayList<>();
            for (int x = 0; x < xAxisPieces; x++) {
                Graphics2D g2d = (Graphics2D) g;
                Rectangle2D rect = new Rectangle2D.Double(x * rectX, y * rectY, rectWid, rectHei);

                g2d.setColor(Color.WHITE);
                g2d.fill(rect);
                g2d.setColor(Color.black);
                g2d.draw(rect);

                tempArr.add(new Piece(rect, x, y));
            }
            pieces.add(tempArr);
        }

        new Listeners(pieces);

        return pieces;
    }


    /*
    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(750, 500);
    }
     */

    // create the GUI explicitly on the Swing event thread
    public void createAndShowGui(int xAxisPieces_, int yAxisPieces_) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                xAxisPieces = xAxisPieces_;
                yAxisPieces = yAxisPieces_;
            }
        });
    }

    class Listeners implements MouseListener{
        private ArrayList<ArrayList<Piece>> grid;

        Listeners(ArrayList<ArrayList<Piece>> grid){
            this.grid = grid;
            System.out.println("[DEBUG] the listener doesn't register for some reason");

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            /**
             * @TODO: 25-Nov-21 the listener doesn't seem to be working properly, fix it, it doesn't print anything in
             *      console
             *
             */

            System.out.println("[DEBUG] mouse pressed at " + e.getX() + " X pos and " + e.getY() + " Y pos");

            /*
            if ((e.getButton() == 1) && oval.contains(e.getX(), e.getY()) ) {
                repaint();
                // JOptionPane.showMessageDialog(null,e.getX()+ "\n" + e.getY());
            }

             */
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}

