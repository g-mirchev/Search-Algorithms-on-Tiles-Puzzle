
public class Run {
	
	static Puzzle puzzle;
	
	public static void main(String args[]){
		puzzle = new Puzzle();
		Search bfs = new BreadthFirstSearch(puzzle);
		//bfs.search();
		Search dfs = new DepthFirstSearch(puzzle);
		//dfs.search();
		/*int i = 0;
		while(i < 5) {
			dfs.search();
			i++;
		}//*/
		
		Search ids = new IterativeDeepeningSearch(puzzle);
		//ids.search();
		Search astar = new Astar(puzzle);
		astar.search();
	}
}
