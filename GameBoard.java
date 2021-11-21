import java.util.Arrays;

public class GameBoard {
	private int[][] grid; // the grid that stores the pieces
	public int lRow;
	public int lCol;

	public GameBoard(int width, int height) {
		grid = new int[height][width];

		// Initialize starting positions
		grid[0][0] = 2; //startpos
		grid[width - 1][height - 1] = 3; //endpos
	}

	// Make the requested move at (row, col) by changing the grid.
	// returns false if no move was made, true if the move was successful.
	public boolean FieldPressed(int row, int col) {
		System.out.println("[DEBUGGING INFO] You clicked in row " + row + " and column " + col);

		if (!isInGrid(row,col))
			return false;

		//to stop the grid from going nuts if you hold the button (when you hold it is like you press the
		// mouse button repeatetly so its activating and deactivating the cell 100+ times a second
		if (!(lRow != row || lCol != col))
			return false;

		System.out.println("[DEBUGGING INFO] You clicked in row " + row + " and column " + col);

		if (grid[row][col] == 1) {
			grid[row][col] = 0;
		}
		else {
			grid[row][col] = 1;
		}
		lCol = col;
		lRow = row;
		return true;
	}

	public int[][] getGrid() {
		return grid;
	}

	// Return true if the row and column in location loc are in bounds for the grid
	public boolean isInGrid(int row, int col) {
		return  row >= 0 && col>= 0 && grid.length - 1 >= row && grid[row].length - 1 >= col;
	}
}