import java.util.ArrayList;
import java.util.Arrays;

public class IterativeDeepeningSearch extends Search {

	private boolean solutionFound = false;
	private ArrayList<State> path;
	
	//creates a stack to store states for path tracking
	public IterativeDeepeningSearch(Puzzle p) {
		super(p);
		path = new ArrayList<State>();
	}

	/*
	 * Implements Iterative Deepening Search algorithm.
	 * Will print the number of nodes extended when
	 * it reaches the goal state.
	 */
	@Override
	protected void search() {
		State found;
		int depth = 0;
		while(solutionFound == false) {
			found = dls(root, depth);
			if(found != null) {
				System.out.println("Iterative deepening search: correct state found after " + count + " nodes expanded.");
				System.out.println("Iterative deepening search: goal reached in " + this.calculateMoves() + " moves.");
				found.printCurrentState();
				this.solutionFound = true;
			}
			path.clear();
			count = 0;
			depth++;
		}	
	}
	
	/*
	 * Implement depth limited search needed to complete the
	 * iterative algorithm above. Counts every node added to the stack
	 */

	private State dls(State s, int d) {
		State st;
		if(d == 0 && s.isCorrectState()) {
			count ++;
			st = s;
			return st;
		}
		else if(d > 0) {
			State[] ts = s.createPossibleNextStates();
			for (int i = 0; i < ts.length; i++) {
				count++;
				path.add(ts[i]);
				st = dls(ts[i], d - 1);
				if(st != null)
					return st;
			}
		}
		return null;
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
	
	//another implementation that didn't work as well
	/*
	private State dls(State s, int d) {
		State st;
		int depth = 0;
		Stack<State> stack = new Stack<State>();
		s.setLvl(0);
		stack.push(s);
		while(!stack.isEmpty()) {
			st = stack.pop();
			this.path.push(st);
			if(st.isCorrectState()) {
				return st;
			}
			depth++;
			this.addNextStates(st, stack, d, depth);
		}
		return null;
	}
	
	private void addNextStates(State s, Stack<State> stak, int maxDep, int curDep) {
		State[] ts = s.createPossibleNextStates();
		for (int i = 0; i < ts.length; i++) {
			count++;
			ts[i].setLvl(curDep);
			if(ts[i].getLvl() < maxDep + 1);
				stak.push(ts[i]);
		}
	}
	*/
}
