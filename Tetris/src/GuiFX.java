import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;

public class GuiFX extends Application implements EventHandler <KeyEvent>{
	private Grid grid;
	
	private int tileSize;
	private int width, height;
	private Scene scene;
	private BorderPane pane;
	private GridPane gridpane;
	private FlowPane side;
	
	private int speed;
	private Timeline a;
	PauseTransition pause;
	
	public Color getColorByNum(int i) {
		Color color = null;
		switch (i) {
			case 1: color = TShape.getColor();
				break;
			case 2: color = SShape.getColor();
				break;
			case 3: color = ZShape.getColor();
				break;
			case 4: color = LShape.getColor();
				break;
			case 5: color = JShape.getColor();
				break;
			case 6: color = IShape.getColor();
				break;
			case 7: color = SqShape.getColor();
				break;
		}
		return color;
	}
	
	public void start(Stage stage) {
		grid = new Grid();

		tileSize = 35;
		width = 800;
		height = (grid.getRowCount()-4)*tileSize+20;
		
		//Set up grid pains
		pane = new BorderPane();
			gridpane = new GridPane();
			side = new FlowPane();
				side.setPrefHeight(height);
				side.setMinHeight(height);
				side.setMaxHeight(height);
				
				side.setPrefWidth((width-(tileSize*grid.getColCount()))/2);
				side.setMinWidth((width-(tileSize*grid.getColCount()))/2);
				side.setMaxWidth((width-(tileSize*grid.getColCount()))/2);
				
				Label startLabel = new Label("Press S to Start\n\nPress P to Pause");
				side.getChildren().add(startLabel);
		pane.setLeft(side);
		pane.setCenter(gridpane);
		scene = new Scene(pane, width, height);
		
		// need to attach KeyEvent caller to an invisible Box
        final Box keyboardNode = new Box();
        keyboardNode.setFocusTraversable(true);
        keyboardNode.requestFocus();
        keyboardNode.setOnKeyPressed(this);
        //keyboardNode.setOnKeyReleased();
        pane.setRight(keyboardNode);
        
        paint();
        
        // Show stage
		stage.setScene(scene);
			stage.setResizable(false);
			stage.show();
		
			
		speed = 250;
		//game loop
		a = new Timeline(new KeyFrame(Duration.millis(speed),
			e -> {
				
				gridpane.getChildren().clear();
				
				if (grid.getGamePlay() == true) {
					if (grid.canMove("down") == false) {
						grid.nextShape();
					}else {
						grid.down();
					}	
				} 
				paint();
			}
		));
		
		//Timeline settings
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();
		
		pause = new PauseTransition(Duration.seconds(0));
		pause.setOnFinished(event ->
			{if (a.getStatus() == Animation.Status.PAUSED) {
				a.play();
			}else {
				a.pause();
			}}
		);
	}

	public void pauseGame() {
		a.pause();
	}
	
	public void speedGame() {
		if (a != null) {
		       a.stop();
		   }
		a = new Timeline(new KeyFrame(Duration.millis(50),
				e -> {
					
					gridpane.getChildren().clear();
					
					if (grid.getGamePlay() == true) {
						if (grid.canMove("down") == false) {
							grid.nextShape();
						}else {
							grid.down();
						}	
					} 
					paint();
					
				}
			));
		a.setCycleCount(Timeline.INDEFINITE);
		a.play();	
	}
	
	public void resumeSpeed() {
		a.pause();
		a = new Timeline(new KeyFrame(Duration.millis(150),
				e -> {
					
					gridpane.getChildren().clear();
					
					if (grid.getGamePlay() == true) {
						if (grid.canMove("down") == false) {
							grid.nextShape();
						}else {
							grid.down();
						}	
					} 
					paint();
					
				}
			));
		a.play();	
	}
	
	public void paint() {
		
		//Grid without shapes
		int row = 10;
		int col = (width-(tileSize*grid.getColCount()))/2;
		for (int i = 4; i < grid.getRowCount(); i++) {
			for (int j = 0; j < grid.getColCount(); j++) {
				
				//Rectangle creation
				Rectangle rect = new Rectangle();
				rect.setWidth(tileSize);
				rect.setHeight(tileSize);
				rect.setStroke(Color.DARKGRAY);
				
				int gridNumber = grid.getNumber(i, j);
				switch (gridNumber) {
				case 0: rect.setFill(Color.LIGHTGRAY);
					break;
				case 1: rect.setFill(Color.PURPLE);
					break;
				case 2: rect.setFill(Color.LIME);
					break;
				case 3: rect.setFill(Color.RED);
					break;
				case 4: rect.setFill(Color.ORANGE);
					break;
				case 5: rect.setFill(Color.NAVY);
					break;
				case 6: rect.setFill(Color.AQUA);
					break;
				case 7: rect.setFill(Color.YELLOW);
					break;
			}
				gridpane.add(rect, col, row);
				col += tileSize;
			}
			row += tileSize;
			col = (width-(tileSize*grid.getColCount()))/2;
		}
	}
	
	public static void main(String[]args) {
		launch(args);
		
	}
	
	@Override
	public void handle(KeyEvent arg0) {
		if(arg0.getCode() == KeyCode.RIGHT) {
			grid.right();
		}
		if(arg0.getCode() == KeyCode.LEFT) {
			grid.left();
		}
		if(arg0.getCode() == KeyCode.S) {
			grid.initiate();
		}
		if(arg0.getCode() == KeyCode.UP) {
			grid.rotate();
		}
		if(arg0.getCode() == KeyCode.SPACE) {
			grid.hardDrop();
		}
//		if(arg0.getCode() == KeyCode.DOWN) {
//			speedGame();
//			//resumeSpeed();
//		}
		if (arg0.getCode() == KeyCode.P) {
			pause.play();
		}
	}
}
