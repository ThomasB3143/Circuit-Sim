import java.util.ArrayList;
import java.util.Arrays;
public class Main {
	public static ArrayList<Node> nodeList = new ArrayList<Node>();
	public static ArrayList<Cell> cellList = new ArrayList<Cell>();
	public static ArrayList< Resistor> resList = new ArrayList<Resistor>();
	private static ArrayList<Node> ungrounded = new ArrayList<Node>(); //list used for equations (updated during depth first search)
	private static ArrayList<Node> unvisited = new ArrayList<Node>(); //list used to exhaust depth first search

	public static void main(String[] args) {
		Builder builder = new Builder();
	}
	public static ArrayList<Node> getNodeList(){
		return nodeList;
	}
	public static ArrayList<Cell> getCellList(){
		return cellList;
	}
	public static ArrayList<Resistor> getResList(){
		return resList;
	}
	public static void depthFirstSearch(Node node) { //recursive depth first search before MNA determines if the graph is connected and ground a vertex from each sector
		unvisited.remove(node);
		for (int i = 0 ; i < node.connected.size() ; i ++) {
			if (unvisited.contains(node.connected.get(i).port1) && !(node.connected.get(i).isSwitch && node.connected.get(i).isRes)) {
				System.out.println("Passing through " + node.connected.get(i).name);
				depthFirstSearch(node.connected.get(i).port1);
			}
			else if (unvisited.contains(node.connected.get(i).port2)  && !(node.connected.get(i).isSwitch && node.connected.get(i).isRes)) {
				System.out.println("Passing through " + node.connected.get(i).name);
				depthFirstSearch(node.connected.get(i).port2);
			}
		}
	}
	public static void MNA() {
		unvisited.clear();
		ungrounded.clear();
		unvisited.addAll(nodeList);
		ungrounded.addAll(nodeList);
		for (Node node : nodeList) {
			node.voltage = 0;
		}
		while(!unvisited.isEmpty()) {
			ungrounded.remove(unvisited.get(0)); //grounds one node per circuit
			depthFirstSearch(unvisited.get(0));
		}
		System.out.println("NodeList has " + nodeList.size());
		System.out.println("ungrounded has " + ungrounded.size());
		double[][] A = new double[ungrounded.size() + cellList.size()][ungrounded.size() + cellList.size() + 1]; //-1 because one node is ignored as a ground source
		for (int i = 0 ; i < ungrounded.size() ; i ++) { //populating G
			Node current = ungrounded.get(i);
			A[i][i] = current.getConSum();
			for (int j = i + 1 ; j < ungrounded.size() ; j ++) {
				double con = current.getConBetween(ungrounded.get(j));
				A[i][j] = con; //halves calculations by using A's symmetric nature
				A[j][i] = con;
			}
		}
		for (int i = 0 ; i < ungrounded.size() ; i ++) { //populating B and C
			Node current = ungrounded.get(i);
			for (int j = ungrounded.size() ; j < ungrounded.size() + cellList.size() ; j ++) {
				double terminal = current.getTerminal(cellList.get(j - ungrounded.size()));
				A[i][j] = terminal; //halves calculations again since C is B but transposed by 90'
				A[j][i] = terminal;
			}
		}
		for (int i = ungrounded.size() ; i < ungrounded.size() + cellList.size() ; i ++) { //fills rightmost column with emf's from cells
			A[i][ungrounded.size() + cellList.size()] = cellList.get(i - ungrounded.size()).pd;
		}
		for (int i = 0 ; i < ungrounded.size() + cellList.size() ; i++) { //print loop
			for (int j = 0 ; j < ungrounded.size() + cellList.size() + 1 ; j++) {
				System.out.print(A[i][j] + " , ");
			}
			System.out.println("");
		}
		System.out.println("");
		//row elimination process
		int[] copy = new int[ungrounded.size() + cellList.size()]; //-1 indicates no copy, any number represents that the row is a copy of the row of said number
		int tempCopy;
		Arrays.fill(copy, -1);
		for (int i = ungrounded.size() ; i < ungrounded.size() + cellList.size() - 1; i ++) { //for every row below G matrix
			for (int j = i + 1 ; j < ungrounded.size() + cellList.size() ; j ++) { //for every row below row i
				System.out.println("Assuming " + j + " is a copy of " + i);
				tempCopy = copy[j];
				copy[j] = i; //assume j is a copy of i, then attempt to disprove
				for (int k = 0 + 1 ; k < ungrounded.size() + cellList.size() ; k ++) { //for every column in row j
					if (A[j][k] != A[i][k]) {
						System.out.println("It is not a copy :(");
						copy[j] = tempCopy; //j is no longer a copy of i (reverted back to its previous value)
						break; //prevents unnecessary iteration
					}
				}
				if (copy[j] == i && copy[i] == -1) { //j is a copy of i, adding j to i
					System.out.println("Addring rows");
					for (int k = 0 ; k < ungrounded.size() + cellList.size() - 1 ; k ++) { //for every column in row j
						A[k][i] += A[k][j];
					}
				}
			}
		}
		//print loop for Copy
		for (int i = 0 ; i < copy.length ; i ++) {
			System.out.print("( " + copy[i] + " ) , ");
		}
		System.out.println("\n");
		//converting A into upper triangular form
		for (int i = 0 ; i < ungrounded.size() + cellList.size() ; i ++) { //for every diagonal element in A
			if (copy[i] == -1) { //checks if row is a copy
				if (A[i][i] == 0) { //non zero element on diagonal must be swapped
					System.out.println("Swapping row at i = " + i);
					for (int j = i + 1 ; j < ungrounded.size() + cellList.size() ; j ++) { //for every row below row i
						if (A[j][i] != 0 && copy[j] == -1) { //non-zero element found in a non-copy row, swapping rows i and j
							for (int k = 0 ; k < ungrounded.size() + cellList.size() + 1; k ++) {//for every column in j and i
								double temp = A[i][k]; //temporary swapping variable
								A[i][k] = A[j][k];
								A[j][k] = temp;
							}
						}
					}
				}
				for (int j = i + 1 ; j < ungrounded.size() + cellList.size() ; j ++) {//for every row below i
					if (A[j][i] != 0 && copy[j] == -1) { // ignores multiplication and addition if the element is already 0, preventing error when dividing
						double factor = -A[i][i]/A[j][i];
						System.out.println("i = " + i);
						System.out.println("factor = " + factor);
						for (int k = 0 ; k < ungrounded.size() + cellList.size() + 1; k ++) { //carries out row multiplication and addition with row 0
							A[j][k] *= factor;
							A[j][k] += A[i][k];
						}
					}
					for (int a = 0 ; a < ungrounded.size() + cellList.size() ; a++) { //print loop
						for (int b = 0 ; b < ungrounded.size() + cellList.size() + 1 ; b++) {
							System.out.print(A[a][b] + " , ");
						}
						System.out.println("");
					}
					System.out.println("");
				}
			}
		}
		double[] solutions = new double[ungrounded.size() + cellList.size()]; //solves row echleon form of A
		for (int i = ungrounded.size() + cellList.size() - 1 ; i >= 0 ; i --) { //for every row in A
			if (copy[i] == -1) {
				solutions[i] = A[i][ungrounded.size() + cellList.size()];
				for (int j = ungrounded.size() + cellList.size() - 1 ; j > i ; j --) { //for every upper triangle element in row i
					solutions[i] -= A[i][j] * solutions[j];
				}
				solutions[i] /= A[i][i];
			}
		}
		//copying solutions for copied variables
		for (int i = 0 ; i < copy.length ; i ++) { //for every element in solutions
			if (copy[i] != -1) {
				solutions[i] = solutions[copy[i]];
			}
		}
		for (int i = 0 ; i < cellList.size() ; i ++) { //assigns solutions to cells
			System.out.println(solutions[ungrounded.size() + i]);
			if (solutions[ungrounded.size() + i] == Double.POSITIVE_INFINITY || solutions[ungrounded.size() + i] == Double.NEGATIVE_INFINITY) {
				System.out.println("Hello");
				throw new IllegalArgumentException("Invalid circuit");			}
			else {
				if (solutions[ungrounded.size() + i] == 0) {
					cellList.get(i).current = 0;
				}
				else {
					cellList.get(i).current = Math.round((solutions[ungrounded.size() + i]) * Math.pow(10, 6 + Math.floor(Math.log10( Math.abs((solutions[ungrounded.size() + i])))))) / Math.pow(10, 6 + Math.floor(Math.log10(Math.abs((solutions[ungrounded.size() + i])))));
				}
			}
		}
		for (int i = 0 ; i < ungrounded.size() ; i ++) { //assigns solutions to nodes
			ungrounded.get(i).voltage = solutions[i];
			for(int j = 0 ; j < nodeList.size() ; j ++) {
				if(ungrounded.get(i).equals(nodeList.get(j))) {
					nodeList.get(j).voltage = ungrounded.get(i).voltage;
				}
			}
		}
		for (int i = 0 ; i < resList.size() ; i ++) { //calculates potential difference across components using the voltage on either side
			Resistor a = resList.get(i);
			if (a.port1.voltage - a.port2.voltage == 0) {
				a.pd = 0;
			}
			else {
				a.pd = Math.round((a.port1.voltage - a.port2.voltage) * Math.pow(10, 6 + Math.floor(Math.log10( Math.abs((a.port1.voltage - a.port2.voltage)))))) / Math.pow(10, 6 + Math.floor(Math.log10(Math.abs((a.port1.voltage - a.port2.voltage)))));
			}
			
			if (a.getResCurrent() == 0) {
				a.current = 0;
			}
			else {
				a.current = Math.round((a.getResCurrent()) * Math.pow(10, 6 + Math.floor(Math.log10( Math.abs((a.getResCurrent())))))) / Math.pow(10, 6 + Math.floor(Math.log10(Math.abs((a.getResCurrent())))));
			}
			System.out.println("R1: v = " + a.pd + " , I = " + a.current);
		}
		System.out.println("");
		
	}
}








