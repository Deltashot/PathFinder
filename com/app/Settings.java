package com.app;

public class Settings {
    /**
     * GRID_OFFSET_N: offset in the x and y axis from the window, how many units away from the top left corner of the window
     * GRID_WID: number of pieces on the X axis of the grid
     * GRID_HEI: number of pieces on the Y axis of the grid
     * RECT_WID: width of a single piece inside the grid, in pixels?
     * VISUALIZE_SPEED: speed between repainted pieces when repainting grid in millisecond
     * SHORTEST_VISUALIZE_SPEED: speed between repainted pieces when visualizing shortest path in milliseconds
     *
     * NOTE using ridiculous values in GRID_WID/GRID_HEI and MOSTLY RECT_WID seems to result in visual glitches
     */

    protected static final int WINDOW_WID = 800;
    protected static final int WINDOW_HEI = 600;
    protected static final int GRID_OFFSET_X = 25;
    protected static final int GRID_OFFSET_Y = 25;
    protected static final int GRID_WID = 25;
    protected static final int GRID_HEI = 25;
    protected static final int RECT_WID = 20;
    protected static final int VISUALIZE_SPEED = 3;
    protected static final int SHORTEST_VISUALIZE_SPEED = 5;
}
