import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Astar extends Search {

	private Queue<State> que;
	private Stack<State> path;
	private int[] goalAbc = this.getAbc(puzzle.getGoalState());
	int depth = 0;
	
	/*
	 * creates a new priority queue to store the nodes and passes
	 * the root as the first element.
	 */
	public Astar(Puzzle p) {
		super(p);
		//implement comparator sort the queue so the lowest value get's taken first
		que = new PriorityQueue<State>(new Comparator<State>() {
			@Override
			public int compare(State a, State b) {
				int aH = a.getHeuristic();
				int bH = b.getHeuristic();
				return aH - bH;
	        }
		});
		path = new Stack<State>();
		this.getRoot().setHeuristic(this.manhattanDistance(this.getRoot()));
		que.add(this.getRoot());
	}

	/*
	 * Implements A* heuristic search. Takes the value with the lowest
	 * distance from the goal state from the queue and checks whether it's
	 * a goal state or not. Will end the loop when goalstate is found
	 */
	@Override
	protected void search() {
		State node;
		while(!que.isEmpty()) {
			node = que.poll();
			path.push(node);
			if(node.isCorrectState()) {
				System.out.println("A* search: correct state found after " + count + " nodes expanded.");
				System.out.println("A* search: goal reached in " + this.calculateMoves() + " moves.");
				node.printCurrentState();
				break;
			}
			depth++;
			this.addNextStates(node);
		}
	}
	
	/*
	 * adds the next states to the queue while not adding
	 * the current state's parent (otherwise goes in an infinite loop)
	 */
	private void addNextStates(State s) {
		State[] ts = s.createPossibleNextStates();
		for(int i = 0; i < ts.length; i++) {
			if(s.getPreviousState() == null) {
				ts[i].setHeuristic(depth + this.manhattanDistance(ts[i]));
				que.add(ts[i]);
				count++;
			}
			else if(!Arrays.equals(ts[i].getCurrentState(), s.getPreviousState().getCurrentState())){
				ts[i].setHeuristic(depth + this.manhattanDistance(ts[i]));
				que.add(ts[i]);
				count++;
			}
		}
	}
		
	//calculates the Manhattan distance from the state passed to the goal state
	public int manhattanDistance(State s) {
		int[] stateAbc = this.getAbc(s.getCurrentState());
		int distance = 0;
		for(int i = 0; i < stateAbc.length; i++) {
			for(int j = 0; j < goalAbc.length; j++) {
				if(s.getCurrentState()[stateAbc[i]] == puzzle.getGoalState()[goalAbc[j]])
					distance += Math.abs(stateAbc[i] - goalAbc[j]);
			}
		}
		return distance;
	}
	
	//returns the position of A, B and C blocks in the given array of chars (state)
	private int[] getAbc(char[] state) {
		char[] s = state;
		int a = 0;
		int b = 0;
		int c = 0;
		for(int i = 0; i < s.length; i++) {
			if(s[i] == Puzzle.A) 
				a = i;
			else if(s[i] == Puzzle.B) 
				b = i;
			else if(s[i] == Puzzle.C) 
				c = i;
		}
		int[] abc = {a, b, c};
		return abc;
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
