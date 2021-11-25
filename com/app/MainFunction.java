package com.app;
import javax.swing.*;

public class MainFunction{
    private static JFrame frame;
    //number of elements in X and Y axis
    private static final int gridWid = 10;
    private static final int gridHei = 10;

    public static void main(String args[]){
        frame = new JFrame("Pathfinding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(750,600);

        DrawGrid grid = new DrawGrid();
        grid.createAndShowGui(gridWid, gridHei);
        grid.setLayout(null);
        grid.setBounds(25, 25, gridWid * grid.rectWid + 1, gridHei * grid.rectHei + 1);
        frame.getContentPane().add(grid);

        JPanel p = new JPanel();
        JButton button = new JButton("Visualize");
        p.setLayout(null);
        button.setBounds((gridWid * grid.rectWid + 1) + 80,100,100,40);
        p.add(button);

        frame.getContentPane().add(p);

        frame.setVisible(true);
    }
}