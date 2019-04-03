
public class Puzzle {
	
	//declares and instantiates the current state of the board
	State boardState = new State();
	
	//the number of columns and rows
	public static final int N = 4;
	
	//The chars have their own variables to avoid confusion
	public static char A = 'A';
	public static char B = 'B';
	public static char C = 'C';
	public static char P = 'P';
	
	//use this to generate a NxN start state
	private char[] startState = generateStartState();
	
	
	//use this to make a custom 4x4 start state
	/*private char[] startState = {
			'X','X','X','X',
			'X','P','X','X',
			'X','A','B','X',
			'X','C','X','X'};//*/
	
	//remembers the goal state
	private char[] goalState = generateGoalState();
	
	/*
	 * the number values of the direction
	 * are the amount of moves the agent actually makes
	 * as even though it's made to look like a NxN grid
	 * the game is actually an array with two possible directions
	 */
	public static int LEFT = -1;
	public static int RIGHT = 1;
	public static int UP = -Puzzle.N;
	public static int DOWN = Puzzle.N;
	
	/*
	 * constructor initiates the game by creating
	 * the start state and parsing it as the current
	 * state and the storing arrays
	 */
	public Puzzle() {
		boardState.setCurrentState(this.startState);
		boardState.setLvl(0);
	}
	
	/*
	 * generates the start state of the NxN board implemented as an array
	 * of characters where 'X' is a blank spot 'A', 'B' and 'C' are the blocks
	 * and P is the player. It's a flexible method that works for any N
	 */
	private char[] generateStartState() {
		char[] tempState = {};
		char[] x = {'X'};
		char[] abc = {Puzzle.A, Puzzle.B, Puzzle.C};
		char[] p = {Puzzle.P};
		for(int i = 0; i < Puzzle.N*Puzzle.N - Puzzle.N; i++) { //create the first n - 1 rows of the puzzle
			tempState = Concatinator.concat(tempState, x);
		}
		tempState = Concatinator.concat(tempState, abc);
		while(tempState.length < Puzzle.N*Puzzle.N) {
			if(tempState.length == Puzzle.N*Puzzle.N - 1) {
				tempState = Concatinator.concat(tempState, p);
			}
			else {
				tempState = Concatinator.concat(tempState, x);
			}
		}
		return tempState;
	}
	
	/*
	 * creates a goal state that only has the position
	 * of the tower and no agent
	 */
	private char[] generateGoalState() {
		char[] tempState = {};
		char[] x = {'X'};
		char[] a = {'X', Puzzle.A};
		char[] b = {'X', Puzzle.B};
		char[] c = {'X', Puzzle.C};
		for(int i = 0; i < Puzzle.N*Puzzle.N - 3*Puzzle.N; i++) { //create the first n - 3 rows of the puzzle
			tempState = Concatinator.concat(tempState, x);
		}
		tempState = Concatinator.concat(tempState, a);
		while(tempState.length < Puzzle.N*Puzzle.N - 2*Puzzle.N) {
			tempState = Concatinator.concat(tempState, x);
		}
		tempState = Concatinator.concat(tempState, b);
		while(tempState.length < Puzzle.N*Puzzle.N - Puzzle.N) {
			tempState = Concatinator.concat(tempState, x);
		}
		tempState = Concatinator.concat(tempState, c);
		while(tempState.length < Puzzle.N*Puzzle.N) {
			tempState = Concatinator.concat(tempState, x);
		}
		return tempState;
	}
	
	/*
	 * moves the agent if the move is legal
	 * according to the value of the direction
	 */
	public void moveAgent(int i){

		if(boardState.isMoveLegal(i) == true){
			boardState.move(i);
			
		}
	}
	
	//returns the current board state
	public State getState(){
		return boardState;
	}
	

	//returns the start state
	public char[] getStartState() {
		return this.startState;
	}
	
	//returns the goal state
	public char[] getGoalState() {
		return this.goalState;
	}
}
