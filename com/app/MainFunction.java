package com.app;
import com.app.Algorithms.BreadthFirst;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class MainFunction{
    private static DrawGrid grid;
    private static JFrame frame;
    //number of elements in X and Y axis
    private static final int gridWid = Settings.GRID_WID;
    private static final int gridHei = Settings.GRID_HEI;
    private static final int centerX = Settings.WINDOW_WID/2 - 12;
    private static final int btnWid = Settings.BUTTON_WID;
    private static final int btnHei = Settings.BUTTON_HEI;

    private static JButton algorithmsButton;

    public static void main(String args[]){
        frame = new JFrame("Pathfinding");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Settings.WINDOW_WID, Settings.WINDOW_HEI);

        grid = new DrawGrid();
        grid.createAndShowGui(gridWid, gridHei);
        grid.setLayout(null);
        grid.setBounds(Settings.GRID_OFFSET_X, Settings.GRID_OFFSET_Y, gridWid * grid.getRectWid() + 1, gridHei * grid.getRectHei() + 1);
        frame.add(grid);

        frame.add(createButtons());
        frame.setVisible(true);
    }


    private static JPanel createButtons() {
        /*
        JPanel p = new JPanel();
        JButton button = new JButton("Visualize");
        button.setActionCommand("Visualize");
        p.setLayout(null);
        button.setBounds((gridWid * grid.getRectWid() + 1) + 80,100,120,40);
        p.add(button);
        button.addActionListener(new ButtonListener());
         */


        JPanel p = new JPanel();
        p.setLayout(null);
        JButton button;

        button = new JButton("Mazes & Patterns");
        button.setActionCommand("Mazes & Patterns");
        button.setBounds((int) (centerX - btnWid * 2.5) - Settings.BUTTON_MARGIN * 2, 15, btnWid, btnHei);
        button.addActionListener(new ButtonListener());
        p.add(button);

        button = new JButton("Algorithms");
        button.setActionCommand("Algorithms");
        button.setBounds((int) (centerX - btnWid * 1.5) - Settings.BUTTON_MARGIN, 15, btnWid, btnHei);
        button.addActionListener(new ButtonListener());
        algorithmsButton = button;
        p.add(button);

        button = new JButton("Visualize");
        button.setActionCommand("Visualize");
        button.setBounds(centerX - (btnWid/2), 15, btnWid, btnHei);
        button.addActionListener(new ButtonListener());
        p.add(button);

        button = new JButton("Clear Board");
        button.setActionCommand("ClearBoard");
        button.setBounds(centerX + (btnWid/2) + Settings.BUTTON_MARGIN,15, btnWid, btnHei);
        button.addActionListener(new ButtonListener());
        p.add(button);

        button = new JButton("Clear Path");
        button.setActionCommand("ClearPath");
        button.setBounds((int) (centerX + btnWid * 1.5 + Settings.BUTTON_MARGIN * 2),15, btnWid, btnHei);
        button.addActionListener(new ButtonListener());
        p.add(button);

        return p;
    }

    static class ButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Algorithms":
                    ArrayList<Button> algorithmsList = new ArrayList<>();
                    algorithmsList.add(new Button("option 1"));
                    algorithmsList.add(new Button("option 2"));
                    algorithmsList.add(new Button("option 3"));
                    new MenuConstructor(algorithmsButton, algorithmsList, null);

                    break;

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