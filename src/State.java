
public class State implements Cloneable{

	/*
	 * makes every state has a reference to it's parent state 
	 * (the state the board was before the agent moved to 
	 * create the current state
	 */
	private State parentState;
	private char[] currentState;
	private int heuristic;
	
	/*
	 * this shows on what level the current state is located in
	 * the tree of all states. Note: the start state should be
	 * the only state with lvl 0 as it is the root of the tree.
	 */
	private int lvl;
	
	/*
	 * correctState shows if this state is correct or not
	 * true means correct, and false means incorrect.
	 * checked shows if this state has been checked or not.
	 */
	private boolean correctState = false;
	private boolean checked = false;
	
	/*
	 * this method is the moving function of the agent. It
	 * takes the direction as a distance from the agent p
	 * to the block it needs to swap with and stores that block in s
	 * then the old state get's stored as the parent state for the new
	 * current state about to be created. It then creates the new array
	 * where p and s are swapped and everything else it's in the exact same position
	 * as before. The up and down take -N and N as a parameter which in a NxN grid
	 * will create the illusion that the agent is moving up and down where in reality
	 * it just moves only Left and Right
	 */
	public void move(int direction){
		
		try {
			this.parentState = (State) this.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setCurrentState(this.createMoveState(direction));
		if(direction == Puzzle.LEFT) {
			System.out.println("Moving left:");
		}
		if(direction == Puzzle.RIGHT) {
			System.out.println("Moving right:");
		}
		if(direction == Puzzle.UP) {
			System.out.println("Moving up:");
		}
		if(direction == Puzzle.DOWN) {
			System.out.println("Moving down:");
		}
		this.printCurrentState();
	}
	
	/*
	 * this method prints the array representing 
	 * the current state in an NxN grid format
	 * (basically a matrix) which creates the illusion
	 * of a 2d space when it' actually only 1d
	 */
	public void printCurrentState(){
		
		for (int i = 0; i < Puzzle.N; i++) {
			for (int j = i*Puzzle.N; j < (i + 1)*Puzzle.N; j++) {
				System.out.print(this.currentState[j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/*
	 * checks if the current state is the correct one and sets
	 * the boolean marker correctState to true if it is, otherwise
	 * it leaves it to false. It's made to work for any NxN grid and the
	 * correct state is a tower that starts from the bottom row and is always
	 * 1 space away from the left most wall. in 4x4 form it looks like this:
	 * X X X X
	 * X A X X 
	 * X B X X 
	 * X C X P 
	 * the location of the agent P is irrelevant!
	 */
	public boolean isCorrectState(){
		if (this.currentState[(Puzzle.N*Puzzle.N - 3*Puzzle.N) + 1] == Puzzle.A &&
				this.currentState[(Puzzle.N*Puzzle.N - 2*Puzzle.N) + 1] == Puzzle.B && 
				this.currentState[(Puzzle.N*Puzzle.N - Puzzle.N) + 1] == Puzzle.C){
			this.correctState = true;
		}
		else {
			this.correctState = false;
		}
		return correctState;
	}
	
	private char[] createMoveState(int direction) {
		int p = getAgentPosition();
		int s = p + direction;
		char[] tempState = {};
		State tempParent = this;
		
		//check if the move is legal before doing it and return the new state or empty array if move is illegal
		if(this.isMoveLegal(direction) == true) {
			for(int i = 0; i < Puzzle.N*Puzzle.N; i++){
				if(i == p){
					tempState = (new String(tempState) + tempParent.getCurrentState()[s]).toCharArray();
				}
				else if(i == s){
					tempState = (new String(tempState) + Puzzle.P).toCharArray();
				}
				else{
					tempState = (new String(tempState) + tempParent.getCurrentState()[i]).toCharArray();
				}
			}
		}
		return tempState;
	}
	
	//sets the parent state
	public void setPreviousState(State s) {
		this.parentState = s;
	}


	//returns the parent state
	public State getPreviousState() {
		return this.parentState;
	}

	//returns the current state
	public char[] getCurrentState() {
		return this.currentState;
	}

	
	//sets the current state
	public void setCurrentState(char[] cs) {
		this.currentState = cs;
	}
	
	/*
	 * checks if the move is legal i.e.
	 * if the agent can move in the direction
	 * of a wall he'scurrently facing (jump off the map)
	 */
	public boolean isMoveLegal(int direction){
		
		boolean legal = true;
		int p = getAgentPosition();
		
		if(direction == Puzzle.LEFT){
			int i = 0;
			while (i < Puzzle.N*Puzzle.N){
				if(p == i){
					legal = false;
				}
			i = i + Puzzle.N;
			}
		}
		if(direction == Puzzle.RIGHT){
			int i = Puzzle.N - 1;
			while (i < Puzzle.N*Puzzle.N){
				if(p == i){
					legal = false;
				}
			i = i + Puzzle.N;
			}
		}
		if(direction == Puzzle.UP && p < Puzzle.N){
			legal = false;
		}
		if(direction == Puzzle.DOWN && p >= ((Puzzle.N*Puzzle.N) - Puzzle.N)){
			legal = false;
		}
		return legal;
	}
	
	/*
	 * returns the current index of the agent 'P''s
	 * position in the array.
	 */
	public int getAgentPosition(){
		int agentPosition = 0;
		for(int i = 0; i <(Puzzle.N*Puzzle.N); i++){
			if(currentState[i] == Puzzle.P){
				agentPosition = i;
				break;
			}
		}
		return agentPosition;
	}

	/*
	 * returns the level on which this state
	 * is located in the tree of all states.
	 */
	public int getLvl() {
		return lvl;
	}

	/*
	 * sets the level on which the current state will be
	 * located in the tree of all states.
	 */
	public void setLvl(int lvl) {
		this.lvl = lvl;
	}
	
	/*
	 * Creates the next possible board states depending where
	 * the agent can move.
	 */
	public State[] createPossibleNextStates() {
		
		//create local temporary states and an array
		State[] tempState = {};
		State possibleLeft = new State();
		State[] pL = {possibleLeft};
		State possibleRight = new State();
		State[] pR = {possibleRight};
		State possibleUp = new State();
		State[] pU = {possibleUp};
		State possibleDown = new State();
		State[] pD = {possibleDown};
		
		/*
		 * if will set a current state if the move is legal
		 * also passes this state as the new state's parent
		 */
		possibleLeft.setCurrentState(this.createMoveState(Puzzle.LEFT));
		try {
			possibleLeft.setPreviousState((State) this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		possibleRight.setCurrentState(this.createMoveState(Puzzle.RIGHT));
		try {
			possibleRight.setPreviousState((State) this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		possibleUp.setCurrentState(this.createMoveState(Puzzle.UP));
		try {
			possibleUp.setPreviousState((State) this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		possibleDown.setCurrentState(this.createMoveState(Puzzle.DOWN));
		try {
			possibleDown.setPreviousState((State) this.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		//create array of all the created non null states and return it
		if(possibleUp.getCurrentState().length != 0) {
			tempState = Concatinator.concatenate(tempState, pU);
		}
		if(possibleRight.getCurrentState().length != 0) {
			tempState = Concatinator.concatenate(tempState, pR);
		}
		if(possibleLeft.getCurrentState().length != 0) {
			tempState = Concatinator.concatenate(tempState, pL);
		}
		if(possibleDown.getCurrentState().length != 0) {
			tempState = Concatinator.concatenate(tempState, pD);
		}
		return tempState;
	}

	//returns whether it's checked or not
	public boolean isChecked() {
		return checked;
	}

	//sets the checked state
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	//returns heuristic
	public int getHeuristic() {
		return heuristic;
	}

	//sets heuristic
	public void setHeuristic(int h) {
		this.heuristic = h;
	}
}