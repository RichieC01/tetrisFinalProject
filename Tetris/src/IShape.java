import javafx.scene.paint.Color;

public class IShape extends Shape {
	public IShape() {
		super();
		color = Color.INDIANRED;
		num = 6;
	}
	
	public int[][][] getCoordinates(int startRow, int startCol){
		int[][][] locations = new int[4][1][2];
		switch (orientNum) {
			case 0:
				locations[0][0][0] = startRow;
					locations[0][0][1] = startCol;
				locations[1][0][0] = startRow;
					locations[1][0][1] = startCol-1;
				locations[2][0][0] = startRow;
					locations[2][0][1] = startCol+1;
				locations[3][0][0] = startRow;
					locations[3][0][1] = startCol+2;
					break;
			case 1:
				locations[0][0][0] = startRow;
					locations[0][0][1] = startCol+1;
				locations[1][0][0] = startRow-1;
					locations[1][0][1] = startCol+1;
				locations[2][0][0] = startRow+1;
					locations[2][0][1] = startCol+1;
				locations[3][0][0] = startRow+2;
					locations[3][0][1] = startCol+1;
					break;
			case 2:
				locations[0][0][0] = startRow+1;
					locations[0][0][1] = startCol;
				locations[1][0][0] = startRow+1;
					locations[1][0][1] = startCol-1;
				locations[2][0][0] = startRow+1;
					locations[2][0][1] = startCol+1;
				locations[3][0][0] = startRow+1;
					locations[3][0][1] = startCol+2;
					break;
			case 3:
				locations[0][0][0] = startRow;
					locations[0][0][1] = startCol;
				locations[1][0][0] = startRow-1;
					locations[1][0][1] = startCol;
				locations[2][0][0] = startRow+1;
					locations[2][0][1] = startCol;
				locations[3][0][0] = startRow+2;
					locations[3][0][1] = startCol;
					break;
		}
		return locations;
		
	}

	public int getNumber() {
		return num;
	}
}
