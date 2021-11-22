package com.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DrawGrid extends JPanel {
    Graphics test;
    boolean drawWorld;

    ArrayList<Piece> pieces = new ArrayList<>();

    public static int width;
    private static int height;

    private static final int rectWid = 50;
    private static final int rectHei = 50;
    private static final int rectX = rectWid + 10;
    private static final int rectY = rectHei + 10;

    private static final int gridOffsetX = 25;
    private static final int gridOffsetY = 25;


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.setColor(Color.RED);
        //g.fillRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        //g.setColor(Color.BLACK);
        //g.drawRect(RECT_X, RECT_Y, RECT_WIDTH, RECT_HEIGHT);
        test = g;

        DrawGrid(g);

        if (drawWorld)
            g.drawString("Hello", 50, 50);

        if (!drawWorld)
            g.drawString("World", 10, 10);
    }

    private ArrayList<Piece> DrawGrid(Graphics g){
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Graphics2D g2d = (Graphics2D) g;
                Rectangle2D rect = new Rectangle2D.Double(x * rectX + gridOffsetX, y * rectY + gridOffsetY, rectWid, rectHei);

                g2d.setColor(Color.WHITE);
                g2d.fill(rect);
                g2d.setColor(Color.black);
                g2d.draw(rect);

                pieces.add(new Piece(rect, x, y));
            }
        }
        return pieces;
    }

    public void actionPerformed(ActionEvent e) {
        if (drawWorld)
            drawWorld = false;
        else
            drawWorld = true;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        // so that our GUI is big enough
        return new Dimension(750, 500);
    }

    // create the GUI explicitly on the Swing event thread
    public static void createAndShowGui(DrawGrid panel, JFrame frame, int width_, int height_) {


        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                width = width_;
                height = height_;

                //JFrame frame = new JFrame("DrawRect");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

}

