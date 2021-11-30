package com.app;
import com.app.Algorithms.BreadthFirst;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFunction{
    private static DrawGrid grid;
    private static JFrame frame;
    //number of elements in X and Y axis
    private static final int gridWid = Settings.GRID_WID;
    private static final int gridHei = Settings.GRID_HEI;

    public static void main(String args[]){
        frame = new JFrame("Pathfinding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Settings.WINDOW_WID, Settings.WINDOW_HEI);

        grid = new DrawGrid();
        grid.createAndShowGui(gridWid, gridHei);
        grid.setLayout(null);
        grid.setBounds(Settings.GRID_OFFSET_X, Settings.GRID_OFFSET_Y, gridWid * grid.getRectWid() + 1, gridHei * grid.getRectHei() + 1);
        frame.getContentPane().add(grid);

        JPanel p = CreateButtons();

        frame.getContentPane().add(p);
        frame.setVisible(true);
    }

    private static JPanel CreateButtons() {
        JPanel p = new JPanel();
        JButton button = new JButton("Visualize");
        button.setActionCommand("Visualize");
        p.setLayout(null);
        button.setBounds((gridWid * grid.getRectWid() + 1) + 80,100,120,40);
        p.add(button);
        button.addActionListener(new ButtonListener());

        button = new JButton("Clear Board");
        button.setActionCommand("ClearBoard");
        p.setLayout(null);
        button.setBounds((gridWid * grid.getRectWid() + 1) + 80,100 + 60,120,40);
        p.add(button);
        button.addActionListener(new ButtonListener());

        button = new JButton("Clear Path");
        button.setActionCommand("ClearPath");
        p.setLayout(null);
        button.setBounds((gridWid * grid.getRectWid() + 1) + 80,100 + 60 + 60,120,40);
        p.add(button);
        button.addActionListener(new ButtonListener());

        return p;
    }

    static class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()){
                case "Visualize":
                    BreadthFirst.Start(grid.startPiece, grid.gridPieces, grid, Settings.VISUALIZE_SPEED);
                    System.out.println("Visualize baby");
                    break;
                case "ClearBoard":
                    grid.ClearBoard();
                    break;
                case "ClearPath":
                    grid.ClearPath();
                    break;
                default:
                    System.out.println("[ERROR] the current button has not been defined, its action command is: " + e.getActionCommand());
            }
            /*
            if (e.getActionCommand().equals("Visualize"))
            {
                BreadthFirst.Start(grid.startPiece, grid.gridPieces, grid);
                System.out.println("Visualize baby");
            } else
                System.out.println("the current button has not been defined, its action command is: " + e.getActionCommand());

             */
        }
    }
}