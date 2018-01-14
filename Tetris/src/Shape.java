import java.util.Random;

import javafx.scene.paint.Color;

import java.util.HashMap;

public abstract class Shape {
	
	private Random rand;
	
	protected HashMap<Integer, boolean[][]> orientList;
	protected int orientNum;
	protected int[][][] locations;
	protected static Color color;
	protected int num;
	
	public Shape() {
		rand = new Random();
		orientNum = rand.nextInt(4);
	}

	public abstract int[][][] getCoordinates(int row, int col);
	
	public abstract int getNumber();
	
	public int getOrientNum() {
		return orientNum;
	}
	
	public void cwRotate() {
		orientNum = (orientNum+=1) %4;
	}
	
	public void ccwRotate() {
		if(orientNum == 0) {
			orientNum = 3;
		}else {
			orientNum = (orientNum-=1) %4;
		}
	}
	
	public static Color getColor() {
		return color;
	}
}