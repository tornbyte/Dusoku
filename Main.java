package obTry;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
	
	static Random randu = new Random();

	public static void main(String[] args) {

		Cell[][] grid = new Cell[9][9];
		
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				grid[row][col] = new Cell();
			}
		}
		//int[][] puzzle = new int[9][9];
				int runs = 0;
				long time = 0;
				
				int[][] puzzle = { {9,6,0,0,0,0,8,0,0},
						   		   {0,0,0,0,3,0,6,0,5},
						   		   {0,0,0,0,0,6,0,0,9},
						   		   {8,0,0,0,0,4,0,0,0},
						   		   {0,2,0,7,6,1,0,9,0},
						   		   {0,0,0,8,0,0,0,0,1},
						   		   {7,0,0,2,0,0,0,0,0},
						   		   {4,0,5,0,1,0,0,0,0},
						   		   {0,0,9,0,0,0,0,7,4}};
				
				for(int row=0; row<9; row++) {
					for(int col=0; col<9; col++) {
						grid[row][col].setValue(puzzle[row][col]);
					}
				}
				
				System.out.println("Before: ");
				prtPuzzle( grid );
				long startTime = System.currentTimeMillis();
				solTricky( grid );
				retPossible( grid );
				while(!checkSock( grid )) {
					solGuess( grid );
					runs++;
				}
				long endTime = System.currentTimeMillis();
				time = (endTime - startTime);
				System.out.println("---------------------------------------------");
				System.out.println("After: ");
				prtPuzzle( grid );
				System.out.println("\n\n");
				System.out.println("The run took: "+ TimeUnit.MILLISECONDS.toMillis(time) + " Milliseconds. For " + runs + " runs");
				System.out.println((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())/1000);
	}
	
	public static void retPossible( Cell[][] grid ) {
		
		
		 //Takes puzzle input
		 //Generates 3D array of values available to be in that position
		 //Returns that array		
		
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				if(grid[row][col].getValue() == 0) {
					//Column Add
					for(int tcol=0; tcol<9; tcol++) {	
						if( grid[row][tcol].getValue() != 0) {
							grid[row][col].setPossible(grid[row][tcol].getValue());
						}
					}
					//Row Add
					for(int trow=0; trow<9; trow++) {	
						if( grid[trow][col].getValue() != 0) {
							grid[row][col].setPossible(grid[trow][col].getValue()); 
						}
					}
				}
			}
		}
		//Region Add
		int trow =0;
		int tcol =0;
		
		for(int rreg=0; rreg<3; rreg++) {
			for(int creg=0; creg<3; creg++) {
				trow = rreg*3;
				tcol = creg*3;
				for(int row=trow; row<trow+3; row++) {
					for(int col=tcol; col<tcol+3; col++) {
						if( grid[row][col].getValue() != 0) {
							grid[row][col].setPossible(grid[row][col].getValue()); 
						}
					}
				}
			}
		}
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				
			}
		}
	}

	public static void prtPuzzle( Cell[][] grid ) {
		
		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				System.out.printf("| %d |",  grid[row][col].getValue());
			}
			System.out.println();
		}		 
	}

	public static void solTricky( Cell[][] grid ) {
		
		int gridr = 0;
		int gridc = 0;
		int testr = 0;
		int testc = 0;
		
		for(int row=0; row<9; row++) { //Defaults Boolean array to TRUE
			for(int col=0; col<9; col++) {
				grid[row][col].setAble(true);	
			}
		}
		
		for(int number=1; number<10; number++) {
			
			for(int row=0; row<9; row++) { //Starting boolean set
				for(int col=0; col<9; col++) {
					if( grid[row][col].getValue() == number ) {
						for(int srow=0; srow<9; srow++)
							grid[srow][col].setAble(false);
						for(int scol=0; scol<9; scol++)
							grid[row][scol].setAble(false);
													
						gridr = row/3 * 3; //Gets region number was found.
						gridc = col/3 * 3; //Sets region to FALSE
													
						for(int grow=gridr; grow<gridr+3; grow++) {
							for(int gcol=gridc; gcol<gridc+3; gcol++) {
								grid[grow][gcol].setAble(false);
							}
						}
					}
					else {
						if( grid[row][col].getValue() != 0) { //If still a number. FALSE
							grid[row][col].setAble(false);
						}
					}
				}
			} //Finished boolean set
			
			int test =0;
			int trow =0;
			int tcol =0;
			
			for(int rreg=0; rreg<3; rreg++) {
				for(int creg=0; creg<3; creg++) {
					trow = rreg*3;
					tcol = creg*3;
					for(int row=trow; row<trow+3; row++) {
						for(int col=tcol; col<tcol+3; col++) {
							if( grid[row][col].isAble() ) {
								test++;
								testr = row;
								testc = col;
							}
						}
					}
					if( test == 1) {
						grid[testr][testc].setValue(number);
					}
					//Test Change
					//testr = 0;
					//testc = 0;
					test = 0;
				}
			}
			for(int row=0; row<9; row++) {
				for(int col=0; col<9; col++) {
					grid[row][col].setAble(true);	
				}
			}
			
		}
	}

	public static boolean checkSock( Cell[][] grid ) {
		
		int[] check = new int[10];

		
		//Goes row by row checking for duplicates.
			for(int row=0; row<9; row++) {
				for(int col=0; col<9; col++) {
					check[grid[row][col].getValue()] = grid[row][col].getValue();
				}
				for(int i=1; i<check.length; i++) {
					if(check[i] == 0) {
						return false;
					}
					check[i] = 0; //resets the value after the test. Saves the loop to reset the whole thing.
				}
			}
		return true;
	}

	public static void solGuess( Cell[][] grid ) {
		
		int plank;
		int[] val = new int[10];
		int test = 0;
		
		for(int row=0; row<9; row++) { //Backup of the current values.
			for(int col=0; col<9; col++) {
				grid[row][col].setTest(grid[row][col].getValue());
			}
		}

		for(int row=0; row<9; row++) {
			for(int col=0; col<9; col++) {
				
				for(int i=0; i<10; i++) {
					if( grid[row][col].getPossible(i) == 0 ) {
						val[test] = i;
						test++;
					}
				}
		
				if( (test <= 4) && (test > 0) ) {
					
					if(randu.nextBoolean()) {
						
						if( test == 1)
							plank = 0;
						else
							plank = randu.nextInt(test);	
						
						grid[row][col].setValue(val[plank]);
					}
				}
				test = 0;
			}
		}
		for(int p=0; p<10; p++) {
			solTricky( grid );
		}
		if(!checkSock( grid )) {
			for(int row=0; row<9; row++) {
				for(int col=0; col<9; col++) {
					grid[row][col].setValue(grid[row][col].getTest());
				}
			}
		}
	}
	
}