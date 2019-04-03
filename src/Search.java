
public abstract class Search {
	
	protected Puzzle puzzle;
	protected State root;
	protected int count;
	
	/*
	 * Abstract class to represent a tree search. It takes the
	 * Starting state of the puzzle as a root of the tree and
	 * has a counter that will be used to count the number of nodes expanded
	 */
	protected Search(Puzzle p) {
		this.puzzle = p;
		root = new State();
		this.createRoot();
		count = 0;
	}
	
	/*
	 * child classes will be able to implement
	 * this method depending on the type of search
	 */
	protected abstract void search();
	

 
	//sets the root to be the startState of the board
	protected void createRoot() {
		this.root.setCurrentState(puzzle.getStartState());
	}
	
	//returns the root of the tree
	protected State getRoot() {
		return this.root;
	}
}
