import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstSearch extends Search {

	private Queue<State> que;
	private boolean solutionFound = false;
	private Stack<State> path;
	
	/*
	 * Creates the queue needed to store the
	 * nodes to be checked and adds the root
	 */
	public BreadthFirstSearch(Puzzle p) {
		super(p);
		que = new LinkedList<State>();
		que.add(this.getRoot());
		path = new Stack<State>();
	}

	/*
	 * Implements Breadth First Search algorithm
	 * for the 8 puzzle while counting the nodes
	 * expanded. Will stop traversing once it reaches the
	 * goal state (or runs out of memory)
	 */
	@Override
	protected void search() {
		State node;
		while(!que.isEmpty() && solutionFound == false) {
			node = que.poll();
			this.path.push(node);
			if(node.isCorrectState()) {
				this.solutionFound = true;
			}
			this.addNextStates(node);
		}
	}
	
	/*
	 * add's the next states to the queue and checks whether they
	 * are the goal state. will return a boolean to break the loop if so
	 */
	private void addNextStates(State s) {
		State[] ts = s.createPossibleNextStates();
		for(int i = 0; i < ts.length; i++) {
			this.path.push(ts[i]);
			count++;
			if(ts[i].isCorrectState()) {
				System.out.println("Bredth first search: correct state found after " + count + " nodes expanded.");
				System.out.println("Bredth search: goal reached in " + this.calculateMoves() + " moves.");
				this.solutionFound = true;
				ts[i].printCurrentState();
			}
			this.que.add(ts[i]);
		}
	}

	//backtracks to find the number of moves used to get to the goal
	private int calculateMoves() {
		int moves = 0;
		State ts = path.pop();
		while(!Arrays.equals(ts.getCurrentState(), puzzle.getStartState())) {
			ts = ts.getPreviousState();
			moves++;
		}
		return moves;
	}
}