import processing.core.*;

public class RunGraphicalGame extends PApplet {
	GameBoard game;
	Display display;

	public void settings() {
		size(750, 500);
	}

	public void setup() {
		// Create a game object
		game = new GameBoard(5, 5);

		// Create the display
		// parameters: (10,10) is upper left of display
		// (400, 400) is the width and height
		display = new Display(this, 10, 10, 400, 400);

		//all types of pieces
		PieceTypes();

		// You can use images instead if you'd like.
		//display.setImage(1, "assets/on.png");
		//display.setImage(2, "assets/off.png");

		display.initializeWithGame(game);
	}

	private void PieceTypes() {
		//EMPTY PIECE COLOR IS IN DISPLAY

		//Wall piece color
		display.setColor(1, color(100, 100, 100));

		//Start piece color
		display.setColor(2, color(200, 0, 0));

		//End piece color
		display.setColor(3, color(0, 200, 0));

		//Traveling piece color
		display.setColor(4, color(0, 0, 200));
	}

	@Override
	public void draw() {


		background(200);

		display.drawGrid(game.getGrid()); //display the game
		display.DrawButtons(game.getGrid());
	}

	public void mouseClicked(){
		UpdateField();
	}

	//to make pressing the same button possible
	public void mouseReleased(){
		game.lCol = -1;
		game.lRow = -1;
	}

	public void mouseDragged() {
		UpdateField();
	}

	private void UpdateField(){
		Location loc = display.gridLocationAt(mouseX, mouseY);
		int row = loc.getRow();
		int col = loc.getCol();

		game.FieldPressed(row, col);

		ButtonPressed(row, col);
	}

	private void ButtonPressed(int row, int col) {

	}

	// main method to launch this Processing sketch from computer
	public static void main(String[] args) {
		PApplet.main(new String[] { "RunGraphicalGame" });
	}
}