package mazeRunnerIterative;
/**
 * <p>
 * Title: MazeRunner
 * </p>
 * 
 * <p>
 * Description: The mainline application controller class for displaying the maze using JavaFX
 * </p>
 * 
 * <p>
 * Copyright: Copyright © 2018 Lynn Robert Carter
 * </p>
 * 
 * @author Lynn Robert Carter and Neaz Morshed
 * @version 3.02	Updated application 
 * 
 */

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**********
 * This class extends the JavaFX class Application and drives the display of the maze and it's 
 * solution.
 *
 */
public class MazeRunner extends Application {

	// Constants for the width and height of the screen
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int BUTTON_HEIGHT = 50;

	// Constants for the top and left boarder
	private static final int LEFT = 50;
	private static final int TOP = 50;

	// Constants for the width and height of each graphic element
	private static final int CELL_WIDTH = 25;
	private static final int CELL_HEIGHT = 25;
	private static final int MARGIN_WIDTH = 20;
	
	// The following images are used to draw the maze and the path from the start to the goal
	private static Image wall00;
	private static Image wall01;
	private static Image wall02;
	private static Image wall03;
	private static Image wall04;
	private static Image wall05;
	private static Image wall06;
	private static Image wall07;
	private static Image wall08;
	private static Image wall09;
	private static Image wall10;
	private static Image wall11;
	private static Image wall12;
	private static Image wall13;
	private static Image wall14;
	private static Image wallStart;
	private static Image wallGoal;
	private static Image iconX;
	
	private static boolean atLeastOneImageNotFound = false;


	// The button for solving the maze
	Button buttonSolveTheMaze = new Button("Solve the maze!");

	Pane theRoot;

	Maze theMaze;	// A reference to the maze
	
	/**********
	 * loadAnImage loads an image into memory from a file and returns it to the caller.
	 * 
	 * @param fileName
	 * @return
	 */
	private Image loadAnImage(String fileName) {

		// The intern should replace this line and the following as required by the assignment
		return new Image(fileName);
	}

	/**********
	 * initializeTheGUIElementsmis used to load and set up the attributes for the GUI elements
	 */
	private void initializeTheGUIElements() {
		// Load the images for drawing the maze
		wall00 = loadAnImage("wall00.jpg");
		wall01 = loadAnImage("wall01.jpg");
		wall02 = loadAnImage("wall02.jpg");
		wall03 = loadAnImage("wall03.jpg");
		wall04 = loadAnImage("wall04.jpg");
		wall05 = loadAnImage("wall05.jpg");
		wall06 = loadAnImage("wall06.jpg");
		wall07 = loadAnImage("wall07.jpg");
		wall08 = loadAnImage("wall08.jpg");
		wall09 = loadAnImage("wall09.jpg");
		wall10 = loadAnImage("wall10.jpg");
		wall11 = loadAnImage("wall11.jpg");
		wall12 = loadAnImage("wall12.jpg");
		wall13 = loadAnImage("wall13.jpg");
		wall14 = loadAnImage("wall14.jpg");
		wallStart = loadAnImage("wallStart.jpg");
		wallGoal = loadAnImage("wallGoal.jpg");
		iconX = loadAnImage("iconX.jpg");

		// If any of the images can't be loaded a runtime error is displayed on the console
		if (atLeastOneImageNotFound) {
			System.out.println("\nAt least one file could not be loaded. The application stops!");
			System.exit(0);
		}
		
		// Create the maze and set up the canvas
		theMaze = new Maze(WIDTH, HEIGHT);

		// There's only one button, so we will directly initialize each of the key attribute
		buttonSolveTheMaze.setFont(Font.font("Arial", 18));
		buttonSolveTheMaze.setMinWidth(170);
		buttonSolveTheMaze.setAlignment(Pos.BASELINE_CENTER);
		buttonSolveTheMaze.setLayoutX(200);
		buttonSolveTheMaze.setLayoutY(HEIGHT + 10);
		buttonSolveTheMaze.setOnAction((event) -> { solveTheMaze(); });		
		buttonSolveTheMaze.setDisable(false);
	}

	/**********
	 * This method draws a frame around the canvas
	 * 
	 * @param gc
	 */
	private void drawFrame(GraphicsContext gc) {
		gc.setStroke(Color.BLACK);
		gc.setLineWidth(1);
		gc.setFill(Color.LIGHTGRAY);
		
		// Draw a round rectangle with the fill color
		gc.fillRoundRect(MARGIN_WIDTH/2-2, MARGIN_WIDTH/2-2, 
				WIDTH - MARGIN_WIDTH+6, 
				WIDTH - MARGIN_WIDTH + 6, 8, 8);
		
		// Draw an edge on the round rectangle to boarder it
		gc.strokeRoundRect(MARGIN_WIDTH/2-2, MARGIN_WIDTH/2-2, 
				WIDTH - MARGIN_WIDTH+6, 
				WIDTH - MARGIN_WIDTH+6, 8, 8);

		// Draw an interior round rectangle with white so we can just draw the walls and have a 
		// white background
		gc.setLineWidth(2);
		gc.setFill(Color.WHITE);
		gc.fillRoundRect(MARGIN_WIDTH, MARGIN_WIDTH, WIDTH-2*MARGIN_WIDTH, 
				WIDTH-2*MARGIN_WIDTH, 4, 4);
		gc.strokeRoundRect(MARGIN_WIDTH, MARGIN_WIDTH, WIDTH-2*MARGIN_WIDTH, 
				WIDTH-2*MARGIN_WIDTH, 4, 4);
	}

	/**********
	 * solveTheMaze computes the solution of the maze and if there is a solution, displays and it
	 */
	private void solveTheMaze() {
		// This creates and solves the maze
		Maze solution = theMaze.solveMaze();	// Solve the maze

		// This draws the path onto the existing maze
		if (solution != null) {
			GraphicsContext gc = theMaze.getGraphicsContext2D();
			drawPath(gc, solution);	
		}
		
		// Change the button to a Quit button
		buttonSolveTheMaze.setText("Quit");
		buttonSolveTheMaze.setOnAction((event) -> { System.exit(0); });	
	}

	/**********
	 * Establish the maze window and initialize it to white
	 */
	public void start(Stage theStage) throws Exception {
		theStage.setTitle("Maze Runner");

		// Define all the GUI elements
		initializeTheGUIElements();				

		// Create pane within the window
		theRoot = new Pane();					

		// Draw the frame for the game board
		GraphicsContext gc = theMaze.getGraphicsContext2D();
		drawFrame(gc);

		// Place all of the GUI elements on to the window
		theRoot.getChildren().addAll(theMaze, buttonSolveTheMaze);

		// Display the maze
		drawMaze(gc, theMaze);			

		// Create the scene with the required width and height
		Scene theScene = new Scene(theRoot, WIDTH, HEIGHT + BUTTON_HEIGHT);	

		// Set the scene on the stage
		theStage.setScene(theScene);								

		// Show the stage to the user
		theStage.show();											
	}

	/**********
	 * Determine whether or not a cell is a boarder cell (has a connecting line in it)
	 * 
	 * @param m		The maze
	 * @param r		The row index of interest
	 * @param c		The col index of interest
	 * @return	true if the cell contains one of the boarder elements
	 */
	private boolean boarder(Maze m, int r, int c){
		// If the row or column indexes are outside of the maze, treat it as if there
		// was a blank at that location
		if (r < 0 || c < 0 || r >= m.getMaxRow() || c >= m.getMaxCol()) return false;

		// If the indexes are indeed within the maze, fetch the actual character
		char ch = m.getCell(r,c);

		// Use that character to determine if it is indeed a boarder character
		return ch == '+' || ch == '-' || ch == '|' || ch == 'S' || ch == 'G';
	}

	/**********
	 * drawPath places the "X" image onto the board each place where the path shows that the maze
	 * runner had visited that place
	 * 
	 * @param gc	This is the graphics context
	 * @param m		This is the maze
	 */
	private void drawPath(GraphicsContext gc, Maze m) {
		// For every row and column in the maze, determine the character that is there
		// and figure out the right image to use to display it
		for (int row = 0; row < m.getMaxRow(); row++)
			for (int col = 0; col < m.getMaxCol(); col++) {
				if (m.getCell(row,col) == 'X') {
					ImageView iv = new ImageView();
					iv.setImage(iconX);
					iv.setFitWidth(CELL_WIDTH);
					iv.setFitHeight(CELL_HEIGHT);
					iv.setLayoutX(LEFT + col*CELL_WIDTH);
					iv.setLayoutY(TOP + row*CELL_HEIGHT);				        
					iv.setSmooth(true);
					theRoot.getChildren().add(iv);
				}
			} 
	}

	/**********
	 * Method to draw the Maze (boarders, walls, start, goal, and any visited cells
	 * 
	 * @param gc	This is the graphics context
	 * @param m		This is the maze
	 */
	private void drawMaze(GraphicsContext gc, Maze m) {

		// For every row and column in the maze, determine the character that is there
		// and figure out the right image to use to display it
		for (int row = 0; row < m.getMaxRow(); row++)
			for (int col = 0; col < m.getMaxCol(); col++) {
				ImageView iv = new ImageView();

				// This switch statement directs the execution flow to the appropriate display code
				switch (m.getCell(row, col)){

				// The empty cell... we just allow the background to show
				case ' ':
					break;

					// An '*' character is used to show the solution trail
				case '*':
					iv.setImage(iconX);
					break;

					// The 'S' is the start cell
				case 'S':
					iv.setImage(wallStart);
					break;

					// The 'G' is the goal cell
				case 'G':
					iv.setImage(wallGoal);
					break;

					// The '|' is a vertical wall
				case '|':
					iv.setImage(wall08);
					break;

					// The '-' is a horizontal wall
				case '-':
					iv.setImage(wall09);
					break;

					// The '+' is a corner or a joint.  This code is complex because we what the 
					// output to look good, so we select the right corner image for all of the 
					// different cases. We use a decision tree to minimize the number of if
					// statement we use
				case '+':
					if (boarder(m,row-1,col))
						// Upper is there
						if (boarder(m,row,col-1))
							// Upper and left are there
							if (boarder(m,row,col+1))
								// Upper and left and right are there
								if (boarder(m,row+1,col)) {
									// Upper and left and right and lower are there
									iv.setImage(wall14);
								}
								else {
									// Upper and left and right are there, lower is not there
									iv.setImage(wall13);
								}
							else
								// Upper and left are there, right is not there
								if (boarder(m,row+1,col)) {
									// Upper and left and lower are there, right is not there
									iv.setImage(wall12);
								}
								else {
									// Upper and left are there, right and lower are not there
									iv.setImage(wall03);
								}
						else
							// Upper is there left is not there
							if (boarder(m,row,col+1))
								// Upper and right are there, left is not there
								if (boarder(m,row+1,col)) 
									// Upper and right and lower are there, left is not there
									iv.setImage(wall10);
								else
									// Upper and right are there, left and lower are not there
									iv.setImage(wall01);
							else
								// Upper is there, left and right are not there
								if (boarder(m,row+1,col))
									// Upper and lower are there, left and right are not there
									iv.setImage(wall08);
								else
									// Upper is there, lower and left and right are not there
									iv.setImage(wall04);
					else
						// Upper is not there
						if (boarder(m,row,col-1))
							// Upper is not there, left is there
							if (boarder(m,row,col+1))
								// Upper is not there, left and right are there
								if (boarder(m,row+1,col))
									// Upper is not there, left and right and lower are there
									iv.setImage(wall11);
								else
									// Upper and lower are not there, left and right are there
									iv.setImage(wall09);
							else
								// Upper and right are not there, left is there
								if (boarder(m,row+1,col))
									// Upper and right are not there, left and lower are there
									iv.setImage(wall02);
								else
									// Upper and right and lower are not there, left is there
									iv.setImage(wall07);
						else
							// Upper and left are not there
							if (boarder(m,row,col+1))
								// Upper and left are not there, right is there
								if (boarder(m,row+1,col))
									// Upper and left are not there, right and lower are there
									iv.setImage(wall00);
								else
									// Upper and left and lower are not there, right is there
									iv.setImage(wall05);
							else
								// Upper and left and right are not there
								if (boarder(m,row+1,col))
									// Upper and left and right are not there, lower is there
									iv.setImage(wall06);
								else
									// Upper and left and right and lower are not there... not valid
									iv.setImage(wall04);
					break;
				
				// This case should never occur, but in case we made a mistake, it is here	
				default:
					System.out.println("Warning! The decision tree was unable to find an image " +
										"for the cell [" + row + "][" + col + "]!");
					break;
				}
				
				// Place the item onto the board at the right place
				iv.setFitWidth(CELL_WIDTH);
				iv.setFitHeight(CELL_HEIGHT);
				iv.setLayoutX(LEFT + col*CELL_WIDTH);
				iv.setLayoutY(TOP + row*CELL_HEIGHT);				        
				iv.setSmooth(true);
				
				// Add the item to what is being displayed... it is now visible
				theRoot.getChildren().add(iv);	
			}
	}			


	/**********
	 * This is the main method that starts the JavaFX application
	 * 
	 * @param args	The program arguments are not used in this program
	 */
	public static void main(String[] args) {						// This method may not be required
		System.out.println("Author: Neaz Morshed and Lynn Carter");      
		launch(args);		
		                                                       // for all JavaFX applications.
	}
}

