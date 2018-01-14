import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class Grid {

	private int rowCount, colCount;
	private int startRow, startCol;
	private int[][] grid;
	private boolean gamePlay;
	private Shape[] next7;

	private Shape shape;
	private HashMap<Shape, int[][][]> shapesLocation;
	private static final String[] DIRECTIONS = new String[3];
	private int next7Index;

public Grid() {
		gamePlay = false;
		next7 = new Shape[7];
		shape = generateShape();
		startRow = 1;
		startCol = 3;

		rowCount = 24;
		colCount = 10;

		grid = new int[rowCount][colCount];
		//next7Index = 0;

		shapesLocation = new HashMap<>();
		
		DIRECTIONS[0] = "down";
		DIRECTIONS[1] = "left";
		DIRECTIONS[2] = "right";
	}

	public void initiate() {
		gamePlay = true;
		
		// Initial hashmap input
		updateLocation(startRow, startCol);
		placeShape(shape);
		
		next7Index = 0;
	}
	
	public boolean getGamePlay() {
		return gamePlay;
	}

	public Shape generateShape() {
		Random rand = new Random();
		Shape newShape;
		Shape temp;
		
		if (next7Index == 0) {
			next7[0] = new TShape();
			next7[1] = new SShape();
			next7[2] = new ZShape();
			next7[3] = new LShape();
			next7[4] = new JShape();
			next7[5] = new IShape();
			next7[6] = new SqShape();
			
			for (int i = next7.length-1; i >= 0; i--) {
				int index = rand.nextInt(i+1);
				temp = next7[index];
				next7[index] = next7[i];
				next7[i] = temp;
			}
		}
		newShape = next7[next7Index];
		next7[next7Index] = null;
		
		//increase index;
		next7Index = (next7Index+=1)%6;
		
		return newShape;
	}
	
	public boolean isFilled(int row, int col) {
		return (grid[row][col] != 0);
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColCount() {
		return colCount;
	}

	
	public int getNumber(int row, int col) {
		return grid[row][col];
	}

	public int getLocationRow() {
		return shapesLocation.get(shape)[0][0][0];
	}

	public int getLocationCol() {
		return shapesLocation.get(shape)[0][0][1];
	}

	public int[][][] getLocation(){
		return shapesLocation.get(shape);
	}
	
	public HashMap<Shape, int[][][]> getListOfShapes() {
			return shapesLocation;
	}
	
	public void updateLocation(int row, int col) {
		shapesLocation.put(shape, shape.getCoordinates(row, col));
	}
	
	public void placeShape(Shape newShape) {
		for (int i = 0; i < shapesLocation.get(newShape).length; i++) {
			int row = shapesLocation.get(newShape)[i][0][0];
			int col = shapesLocation.get(newShape)[i][0][1];

			grid[row][col] = newShape.getNumber();
		}
	}

	public void removeShape(Shape newShape) {
		for (int i = 0; i < shapesLocation.get(newShape).length; i++) {
			int row = shapesLocation.get(shape)[i][0][0];
			int col = shapesLocation.get(shape)[i][0][1];

			grid[row][col] = 0;
		}
	}
	
	public void down() {

		if (canMove(DIRECTIONS[0])) {
			removeShape(shape);
			for(int i = 0; i < 4; i++) {
				shapesLocation.get(shape)[i][0][0]+=1;
			}
			placeShape(shape);
		}
		
	}

	public void left() {
		if (canMove(DIRECTIONS[1])) {
			removeShape(shape);
			for(int i = 0; i < 4; i++) {
				shapesLocation.get(shape)[i][0][1]-=1;
			}
			placeShape(shape);
		}
	}

	public void right() {
		if (canMove(DIRECTIONS[2])) {
			removeShape(shape);
			for(int i = 0; i < 4; i++) {
				shapesLocation.get(shape)[i][0][1]+=1;
			}
		}
		placeShape(shape);
	}

	public void rotate() {

		int row = shapesLocation.get(shape)[0][0][0];
		int col = shapesLocation.get(shape)[0][0][1];
		
		removeShape(shape);
		shape.cwRotate();
		updateLocation(row, col);
		placeShape(shape);
	}


	public void hardDrop() {
		while(canMove(DIRECTIONS[0])) {
			down();
		}
	}
	
	public boolean canMove(String dir) {

		//checks if it's on bottom row
		for (int j = 0; j < 4; j ++) {
			if (getLocation()[j][0][0] >= rowCount-1) {
				return false;
			}
		}
		removeShape(shape);
		if (dir == "down") {
			int row, col;
			for(int i = 0; i < 4; i ++) {
				row = getLocation()[i][0][0];
				col = getLocation()[i][0][1];
				if (isFilled(row + 1, col)) {
					placeShape(shape);
					return false;
				}
			}
		}else if (dir == "left") {
			for (int i = 0; i < 4; i ++) {
				if (getLocation()[i][0][1] <= 0) {
					placeShape(shape);
					return false;
				}
			}
			int row, col;
			for(int i = 0; i < 4; i ++) {
				row = getLocation()[i][0][0];
				col = getLocation()[i][0][1];
				if (isFilled(row, col-1)) {
					placeShape(shape);
					return false;
				}
			}
		}else if (dir == "right") {
			for (int i = 0; i < 4; i ++) {
				if (getLocation()[i][0][1] >= 9) {
					placeShape(shape);
					return false;
				}
			}
			int row, col;
			for(int i = 0; i < 4; i ++) {
				row = getLocation()[i][0][0];
				col = getLocation()[i][0][1];
				if (isFilled(row, col+1)) {
					placeShape(shape);
					return false;
				}
			}
		}
		
		placeShape(shape);
		return true;
	}
	
	
	public void nextShape() {
		lineRemoval();
		shapesLocation.clear();
		
		shape = generateShape();
		updateLocation(startRow, startCol);
		
		int row, col;
		for(int i = 0; i < 4; i ++) {
			row = getLocation()[i][0][0];
			
			col = getLocation()[i][0][1];
			if (isFilled(row, col)) {
				System.out.println("End Game!");
				gamePlay = false;
				return;
			}
		}
		placeShape(shape);
	}
	
	public void lineRemoval() {
		int topFullRow = -1;
		int rowsFilled = 0;
		
		//checks if line is full
		for (int i = 0; i < rowCount; i ++) {
			int count = 0;
			for (int j = 0; j < colCount; j++) {
				if (isFilled(i, j)) {
					count++;
				}
			}
			//removes line if full
			if (count == colCount) {
				if (topFullRow == -1) {
					topFullRow = i;
				}
				rowsFilled++;
				for (int col = 0; col < colCount; col ++) {
					grid[i][col] = 0;
				}
			}
		}
		
		//move rows
		for (int i = topFullRow-1; i >=0; i --) {
			for (int j = 0; j < colCount; j ++) {
				grid[i+rowsFilled][j] = grid[i][j];
				grid[i][j] = 0;
			}
		}
	}
	
	public void removeLineFromShape(int row) {
		Iterator<Shape> itr = shapesLocation.keySet().iterator();
		
		while(itr.hasNext()) {
			
			Shape nextShape = itr.next();
			int length = shapesLocation.get(nextShape).length;
			int[][][] newValue;
			int count = 0;
			
			for (int i = 0; i < length; i++) {
				if (shapesLocation.get(nextShape)[i][0][0] == row) {
					removeShape(shape);
					count++;
				}
			}
			
			if ((length - count) == 0) {
				itr.remove();
			} else {
				newValue = new int[length-count][1][2];
				int index = 0;
				for (int i = 0; i < length; i++) {
					if (shapesLocation.get(nextShape)[i][0][0] != row) {
						newValue[index][0][0] = shapesLocation.get(shape)[i][0][0];
						newValue[index][0][1] = shapesLocation.get(shape)[i][0][1];
						System.out.println(newValue[index][0][0] + ", " + newValue[index][0][1]);
						index++;
					}
				}
				shapesLocation.put(nextShape, newValue);
				placeShape(nextShape);
			}
			
			try {
				itr.next();
			}catch(Exception e) {
				
			}
		}
		
	}
	
	public Shape getShape() {
		return shape;
	}
	
	public String toString() {
		String retString = "";
		for (int i = 0; i < rowCount; i++) {
			for (int j = 0; j < colCount; j++) {
				if (isFilled(i, j)) {
					retString += " 0";
				} else {
					retString += " " + getNumber(i, j);
				}
			}
			retString += "\n";
		}
		return retString;
	}
	
	public static void main(String[] args) {
		Grid grid = new Grid();
		grid.initiate();
		System.out.println(grid.toString());
		
		grid.hardDrop();
		System.out.println(grid.toString());
		
		grid.lineRemoval();
		System.out.println(grid.toString());
	}
}
