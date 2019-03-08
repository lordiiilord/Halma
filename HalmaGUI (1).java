import java.util.ArrayList;

import javax.swing.JOptionPane;




import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
public class HalmaGUI extends Application{
	public GridPane gp =new GridPane();
	public Circle c;
	public int oldRow; int newRow; int oldCol; int newCol;
	static LogicArray l1 = new LogicArray();
	static boolean playerOne = true;
	ArrayList<Circle> circles = new ArrayList<Circle>();
	
    public static void main(String [] args) {
    	
    	l1.print();
    	launch();
      
     
}
@Override
public void start(Stage primaryStage) throws Exception {
	primaryStage.setTitle("Halma");
	primaryStage.centerOnScreen();
	 Text title = new Text("My Halma Project");
     title.setFont(Font.font ("Calibri", 25));
    
	GridPane gp = new GridPane();
    //Creating the buttons
	HBox buttons = new HBox();
	Button btnExit = new Button("Exit");
	btnExit.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
			System.exit(0);
		}
	});
	Button btnReset = new Button("Reset");
	btnReset.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
              resetAll();
		}
	});
	Button btnInstr = new Button("Instructions");
	btnInstr.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
			String  Instructions = "The aim of the game is to be the first player to move all pieces across the board "
									+ "\nand into the opposing corner. Players take turns to move a single piece of their own colour."
									+ "\nThe piece may either be simply moved into an adjacent square OR it may make one or more"
									+ "\nhops over other pieces. Where a hopping move is made, each hop must be over "
									+ "\nan adjacent piece into the vacant square directly beyond it. A hop may be over "
									+ "\nany coloured piece including the player's own and can proceed in any one of the eight "
									+ "\ndirections. After each hop, the player may either finish or, if possible and desired,"
									+ "\ncontinue by hopping over another piece."
									+ "\n\nMore at http://www.mastersgames.com/rules/halma-rules.htm  ";
		JOptionPane.showMessageDialog(null, Instructions);
		}
	});
	Button btnTurn = new Button("Player 1 End Turn");
	btnTurn.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent event){
			if (playerOne) {
				btnTurn.setText("Player 2 End Turn");
				playerOne = false;
			}
			else {
				btnTurn.setText("Player 1 End Turn");
				playerOne = true;
			}
			
		}
	});
	buttons.setSpacing(5);
	buttons.getChildren().addAll(btnTurn, btnInstr, btnReset, btnExit);
	
	BorderPane bp = new BorderPane();
	bp.setCenter(gp);
	bp.setBottom(buttons);
    bp.setTop(title);
    BorderPane.setAlignment(title, Pos.TOP_CENTER);
    
    
    //Making the board
	for(int row = 0; row < 8; row++){
		for(int col = 0; col < 8; col++){
			Rectangle space = new Rectangle(35, 35);
			space.setOnMouseClicked(e->{
				Rectangle rec = (Rectangle)e.getSource();
				int rows = gp.getRowIndex(rec);
				int cols = gp.getColumnIndex(rec);
			
				
				int cRow = gp.getRowIndex(c);
				int cCol = gp.getColumnIndex(c);
				c.setStroke(null);
				
				if(moveCheck(cRow, cCol, rows, cols, l1) == false){
				return;
			}else {
			
				if(l1.setPositions(cRow, cCol, rows, cols, playerOne) != true){
					return;
				}
				btnTurn.fire();
			}
				
				System.out.println();
				l1.print();
				System.out.println("rows: " + rows + ", cols: " + cols + ", cRow: " + cRow + ", cCol: " + cCol);
				
				gp.getChildren().remove(c);
				gp.add(c, cols, rows);
				
				 
		
			});
			if((row + col) % 2 == 1 ){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
				space.setFill(Color.DIMGREY);
			}else {
			space.setFill(Color.ALICEBLUE);
			}
			space.setStroke(Color.CORAL);
			gp.add(space, col, row);
			}
			}
//player 1 pieces
		int a = 4;
	for(int i = 0; i < 4; i++){
		for(int j = 0; j < a; j++ ){
			Circle c = new Circle(16.5);
			c.setFill(Color.BLACK);
			c.setOnMouseClicked(new Move());
		gp.setHalignment(c, HPos.CENTER);
			gp.setValignment(c, VPos.CENTER);
			gp.add(c, i, j);
			circles.add(c);
				
		}
		a--;
		}
	//player 2 pieces
	 int b = 0;
	for(int i = 7; i >= 4; i--){
	for(int j = 7; j >= (4 + b); j-- ){
		Circle c = new Circle(16.5);
		c.setFill(Color.PURPLE);
		c.setStroke(Color.GREY);
		c.setOnMouseClicked(new Move());
	gp.setHalignment(c, HPos.CENTER);
		gp.setValignment(c, VPos.CENTER);
		gp.add(c, i, j);
		circles.add(c);
	}
	b++;
	}

	Scene window = new Scene(bp, 288, 350);
	primaryStage.setScene(window);
	primaryStage.show();
}

// reset the board
void resetAll(){
	 Text title = new Text("My Halma Project");
     title.setFont(Font.font ("Calibri", 25));
    
	GridPane gp = new GridPane();
	BorderPane bp = new BorderPane();
	bp.setCenter(gp);
    bp.setTop(title);
    BorderPane.setAlignment(title, Pos.TOP_CENTER);
    //Making the board
	for(int row = 0; row < 8; row++){
		for(int col = 0; col < 8; col++){
			Rectangle space = new Rectangle(35, 35);
			space.setOnMouseClicked(e->{
				Rectangle rec = (Rectangle)e.getSource();
				int rows = gp.getRowIndex(rec);
				int cols = gp.getColumnIndex(rec);
			
				
				int cRow = gp.getRowIndex(c);
				int cCol = gp.getColumnIndex(c);
				c.setStroke(null);
				
				
				System.out.println();
				l1.print();
				System.out.println("rows: " + rows + ", cols: " + cols + ", cRow: " + cRow + ", cCol: " + cCol);
				
				gp.getChildren().remove(c);
				gp.add(c, cols, rows);
				//note*
				 
		
			});
			if((row + col) % 2 == 1 ){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
				space.setFill(Color.DIMGREY);
			}else {
			space.setFill(Color.ALICEBLUE);
			}
			space.setStroke(Color.CORAL);
			gp.add(space, col, row);
			}
			}
//player 1 pieces
		int a = 4;
	for(int i = 0; i < 4; i++){
		for(int j = 0; j < a; j++ ){
			Circle c = new Circle(16.5);
			c.setFill(Color.BLACK);
			c.setOnMouseClicked(new Move());
		gp.setHalignment(c, HPos.CENTER);
			gp.setValignment(c, VPos.CENTER);
			gp.add(c, i, j);
			circles.add(c);
				
		}
		a--;
		}
	//player 2 pieces
	 int b = 0;
	for(int i = 7; i >= 4; i--){
	for(int j = 7; j >= (4 + b); j-- ){
		Circle c = new Circle(16.5);
		c.setFill(Color.PURPLE);
		c.setStroke(Color.GREY);
		c.setOnMouseClicked(new Move());
	gp.setHalignment(c, HPos.CENTER);
		gp.setValignment(c, VPos.CENTER);
		gp.add(c, i, j);
		circles.add(c);
	}
	b++;
	}
}
//This portion of code is for jumping
public boolean checkJumpedPiece(int oldRow, int newRow, int oldCol, int newCol) {
	 int aRow= Math.abs( (oldRow + newRow)/2);
	 int aCol = Math.abs( (oldCol + newCol)/2 );
	 if( !((l1.getPosition(aRow, aCol)) == 0) ) {
	 return true;
	 } 
	 return false; 
	 }

//creating a logic array which helps in movement of pieces in every direction in the movecheck class
static class LogicArray {
	int[][] logicArray = new int[8][8];
	
	LogicArray() {
		for(int i = 0; i < logicArray.length; i++){
			for(int j = 0; j < logicArray.length; j++){
				if(i + j < 4){
				logicArray[i][j]= 1;
				}
				else if(i + j > 10){
				logicArray[i][j] = 2;
				}
				else {
				logicArray[i][j]= 0;
				}
				}
		}
	}
	
	int getPosition(int row, int col){
		return logicArray[row][col];
	}
	//setting each players pieces on the board with the logic array
	boolean setPositions(int oldRowPos, int oldColPos, int newRowPos, int newColPos, boolean playerOne) {
		System.out.println("Postion is: "+logicArray[oldRowPos][oldColPos]+" and playerturn is "+playerOne);
		if(logicArray[oldRowPos][oldColPos] == 1 && playerOne != true){
			return false;
		}
		if(logicArray[oldRowPos][oldColPos] == 2 && playerOne == true){
			return false;
		}
		logicArray[oldRowPos][oldColPos] = 0;
		
		if (playerOne) {
			logicArray[newRowPos][newColPos] = 1;
		}
		else {
			logicArray[newRowPos][newColPos] = 2;
		}
		return true;
	}
	void print() {
		for(int i = 0; i < logicArray.length; i++){
			for(int j = 0; j < logicArray.length; j++){
				System.out.print(logicArray[i][j] + " ");
			}
			System.out.println();
		}
	}
}
//This class moves the pieces
class Move implements EventHandler<MouseEvent>{
public void handle(MouseEvent event){
	for(Circle c2: circles){
		c2.setStroke(null);
	}
 c = (Circle)event.getSource();
 c.setStroke(Color.RED);
 c.setStrokeWidth(1);
}

}
public boolean moveCheck(int oldRow, int oldCol, int newRow, int newCol, LogicArray l1){
	//check if piece is moving up
	if(newRow == oldRow - 1 && newCol == oldCol){
		//check if a player piece is already there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//if no player piece is there
		else if(l1.getPosition(newRow, newCol) == 0){
			return true;
		}
	}
	//moving down
	else if(newRow == oldRow + 1 && newCol == oldCol){
		//check if a player piece is already there
				if(l1.getPosition(newRow, newCol) != 0){
					return false;
					//}
				}
				//if no player piece is there
				else if(l1.getPosition(newRow, newCol) == 0){
					return true;
				}
	}
	//moving left
	else if(newRow == oldRow && newCol == oldCol - 1){
		//check if a player piece is already there
				if(l1.getPosition(newRow, newCol) != 0){
						return false;
					
				}
				//if no player piece is there
				else if(l1.getPosition(newRow, newCol) == 0){
					return true;
				}
	}
	//moving right
	else if(newRow == oldRow && newCol == oldCol + 1){
		//if no player piece is there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//check if a player piece is already there
		else if(l1.getPosition(newRow, newCol) == 0){
						return true;
				}
	}
	
      //This portion of code is for Diagonal movement
	
	else if(newRow == oldRow + 1 && newCol == oldCol + 1){
		//if no player piece is there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//check if a player piece is already there
		else if(l1.getPosition(newRow, newCol) == 0){
						return true;
					
				}
	}
	else if(newRow == oldRow - 1 && newCol == oldCol + 1){
		//if no player piece is there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//check if a player piece is already there
		else if(l1.getPosition(newRow, newCol) == 0){
						return true;
				}
	}
	else if(newRow == oldRow + 1 && newCol == oldCol - 1){
		//if no player piece is there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//check if a player piece is already there
		else if(l1.getPosition(newRow, newCol) == 0){
						return true;
					
				}
	}
	else if(newRow == oldRow - 1 && newCol == oldCol - 1){
		//if no player piece is there
		if(l1.getPosition(newRow, newCol) != 0){
			return false;
		}
		//check if a player piece is already there
		else if(l1.getPosition(newRow, newCol) == 0){
						return true;
					
				}
	}
	return false;
}

}