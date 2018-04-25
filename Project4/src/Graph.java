import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph{


	LinkedList<Vertex>[] edges = new LinkedList[0];


	public class Vertex{

		int value;
		boolean visited = false;
		Vertex previous;
		public Vertex(int vertex){
			this.value = vertex;
		}

		public String toString() {
			return "" + value + " " + visited + " " + previous;
		}
	}

	public Graph(int N){
		edges = new LinkedList[N];
		for(int i = 0; i < N; i ++){
			edges[i] = new LinkedList();
			edges[i].add(new Vertex(i));
		}
	}

	// Add an undirected edge to the graph connecting Vertex i and Vertex j
	public void addEdge(int i, int j){

		edges[i].add(edges[j].get(0));
		edges[j].add(edges[i].get(0));
	}

	// Return a List of vertices which provides the shortest path between i and j.
	// Include both i and j if there is a valid path.
	// If no such path exists, return an empty List.
	// if i==j, return a list containing only i or j (but not both)
	public List<Integer> getShortestPath(int i, int j){
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add(edges[i].get(0));
		edges[i].get(0).visited = true;
		ArrayList<Vertex> shortestPath = new ArrayList<>();
		Vertex temp = null;
		do{ 
			temp = q.poll();
			//			System.out.println(temp);
			for(int p = 0; p < edges[temp.value].size(); p++){
				if(edges[temp.value].get(p).visited == false){
					q.add((edges[temp.value]).get(p));
					edges[temp.value].get(p).visited = true;
					edges[temp.value].get(p).previous = temp;
				}
			}
			shortestPath.add(temp);
			//			Vertex temp2 = edges[j].get(0);
			//			while(temp2.previous!=null){
			//
			//				PATH.add(temp2.value);
			//				temp = temp2.previous;
			//			}
		}while(temp.value != j);

		ArrayList<Integer> path = new ArrayList<>();

		//		System.out.println(Arrays.asList(shortestPath));
		for(int x = 0; x < shortestPath.size(); x++) {

			if(shortestPath.get(x).value == j) {
				temp = shortestPath.get(x);
			}
		}
		while(temp != null) {
			path.add(temp.value);
			temp = temp.previous;
		}
		for(int x = 0; x < path.size()/2; x++) {
			int t = path.get(x);
			path.set(x, path.get(path.size() - x - 1));
			path.set((path.size() - x - 1), t);
		}

		return path;
	}
	
	public static void testPath(String filename, int vertex1, int vertex2) {
		BufferedReader br = null;
		Graph graph1 = null;
		try {
			br = new BufferedReader(new FileReader(filename));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			int test = Integer.parseInt(br.readLine());
			//			System.out.println(test);
			graph1 = new Graph(test);
			while(br.ready()){
				String str = br.readLine();
				//				System.out.println(str.charAt(0) + " " + str.charAt(2));
				String[] edge = str.split(" ");
				graph1.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		try {
			List<Integer> path = graph1.getShortestPath(vertex1, vertex2);
			String pathString = "";
			for(Integer i : path) {
				pathString += i + " ";
			}
			System.out.println("Path Length: " + (path.size() - 1));
			System.out.println("Path: " + pathString);
		}catch(NullPointerException e) {
			System.out.println("Path Length: -1");
			System.out.println("Path: ");
		}
	}


	public static void main(String[] args){
		testPath("File1.txt", 0, 7);
		testPath("File2.txt", 3, 23);
		testPath("File3.txt", 4, 8);
	}
}


