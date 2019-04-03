import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

public class DepthFirstSearch extends Search {

	private Stack<State> stack;
	private ArrayList<State> path;
	/*
	 * creates a stack to store the States
	 * to be checked. pushes the root to the stack.
	 */
	public DepthFirstSearch(Puzzle p) {
		super(p);
		stack = new Stack<State>();
		stack.push(this.getRoot());
		path = new ArrayList<State>();
	}

	/*
	 * Implements Depth First Search algorithm.
	 * Will print the number of nodes extended when
	 * it reaches the goal state.
	 */
	@Override
	protected void search() {
		State node;
		root.printCurrentState();
		while(!stack.isEmpty()) {
			node = stack.pop();
			this.path.add(node);
			node.printCurrentState();
			if(node.isCorrectState()) {
				System.out.println("Depth first search: correct state found after " + count + " nodes expanded");
				System.out.println("Depth first search: goal reached in " + this.calculateMoves() + " moves.");
				node.printCurrentState();
				path.clear();
				count = 0;
				break;
			}
			this.addNextStates(node);
		}
	}
	
	/*
	 * pushes the next available states to the stack and
	 * counts them. Randomises the directions to avoid an infinite loop
	 */
	private void addNextStates(State s) {
		State[] ts = s.createPossibleNextStates();
		Random rn = new Random();
		for (int i = 0; i < ts.length; i++) {
			int j = rn.nextInt((ts.length - 1) - 0 + 1);
			State first = ts[i];
			State second = ts[j];
			ts[i] = second;
			ts[j] = first;
		}
		
		for (int i = 0; i < ts.length; i++) {
			count++;
			this.stack.push(ts[i]);
		}
	}
	
	//backtracks to find the number of moves used to get to the goal
	private int calculateMoves() {
		int moves = 0;
		State ts = path.get(path.size() - 1);
		while(!Arrays.equals(ts.getCurrentState(), puzzle.getStartState())) {
			ts = ts.getPreviousState();
			moves++;
		}
		return moves;
	}
}
