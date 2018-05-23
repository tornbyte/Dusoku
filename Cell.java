package obTry;

public class Cell {
	
	private int value;
	private int test;
	private int[] possible = new int[10];
	boolean able;

	public Cell() {
		//Constructor
	
	}
	
	public int getTest() {
		return test;
	}

	public void setTest(int test) {
		this.test = test;
	}
	
	public boolean isAble() {
		return able;
	}

	public void setAble(boolean able) {
		this.able = able;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getPossible(int position) {
		return possible[position];
	}

	public void setPossible(int posval) {
		this.possible[posval] = posval;
	}
	public void setPossible(int posval, int val) {
		this.possible[posval]+= val;
	}
}