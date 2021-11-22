package com.app;

import com.app.DrawGrid;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PSurface;

import javax.swing.*;
import java.awt.*;

public class MainFunction {
    private JButton button1;

    public static void main(String args[]){
        JFrame frame = new JFrame("My First GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        JButton button1 = new JButton("Button 1");
        //JButton button2 = new JButton("Button 2");
        //frame.getContentPane().add(button1);
        //frame.getContentPane().add(button2);

        //getRootPane().setDefaultButton(buttonOK);
        DrawGrid panel = new DrawGrid();
        DrawGrid.createAndShowGui(panel, frame, 5, 5);


        frame.getContentPane().add(panel);

        frame.setVisible(true);
    }

}