/*
*
*	StronglyConnected
*	Project 5
*	Last modified: 11/08/2017
*	
*/

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.Bag;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.TransitiveClosure;

//import edu.princeton.cs.algs4.TarjanSCC;

public class StronglyConnected extends Graph {

	//Global variables
	public static int V = 0;
	public static int E = 0;
	public static int bigV = 0;
	public static Graph graph;
	public static Digraph digraph;
	public static TarjanSCC scc;
	public static ArrayList<Integer> trace;
	public static Digraph digraphSCC;
	public static TarjanSCC checkSCC;
//	public static List<List<Integer>> strongCC;	

	/*
	*
	*	Main
	*	Reads in number of vertices (V), number
	*	of edges (E), and all edges
	*
	*/
	public static void main(String[] args) {
		
		V = StdIn.readInt(); //reads first value from .txt to vertices (V)
		E = StdIn.readInt(); //reads second value from .txt to edges (E)

		graph = new Graph(V);

		//reads in all edges (two vertices make up an edge)
		for (int index = 0; index < E; index++) {
			int v = StdIn.readInt();
			if (bigV < v) {
				bigV = v;
			}

			int w = StdIn.readInt();
			graph.addEdge(v, w);
		}
		

		//sorts graph and adds to adj[] list
		sortEdge();

		DepthFirstPaths dfsGraph = new DepthFirstPaths(graph, bigV);
		dfsGraph.dfs(graph, bigV);
		
		//pull final array from DepthFirstPaths
		ArrayList<int[][]> degreeList = DepthFirstPaths.list;
		boolean outDegree[] = new boolean[V];
		boolean inDegree[] = new boolean[V];


		int prevV = -1;
		int prevW = -1;
		int skip = 0;
		int stop = 0;
		boolean contained = false;
		trace = new ArrayList<Integer>();
		trace.add(bigV); //add the largest vertex (not handled below)
		//find the trace to compare SCC to
		for (int i = 0; i < degreeList.size() - 1; i++) {
			contained = false;
                        int v = degreeList.get(i)[0][0];
                        int w = degreeList.get(i)[0][1];
			
			if (skip == 1 && stop != 1) {
        	                if (v == prevW) {
	                                contained = trace.contains(v);
					//StdOut.println(contained);
                	                if (!contained) {
						trace.add(v);
                        	                //StdOut.println("TRACE: " + v);
                                	}
	                        }
        	                else if (v == prevV) {
                	                //do nothing
                        	}
	                        else {
        	                        //stop adding to trace
					contained = trace.contains(prevW);
					if (!contained) {
						trace.add(prevW);
					}
                	                //StdOut.println("END: " + prevW);
                        	        stop = 1;
	                        }	

        	        }

                	prevV = v;
	                prevW = w;
        	        skip = 1;

		}
	
		//obtain last vertex for corner case checking
		int length = trace.size();
		int lastVertex = trace.get(length-1);
//		StdOut.println("LAST: " + lastVertex);

		//Convert trace form List to Set
		Set<Integer> hashTrace = new HashSet<Integer>(trace);
/*		for (Integer temp : hashTrace) {
			StdOut.println(temp);
		}
*/


		//TEST PRINT OF TRACE, DELETE LATER
//		for (int i = 0; i < trace.size(); i++) {
//			StdOut.println(trace.get(i));
//		}
//		StdOut.println("\n");

		boolean traceLoop = false;
		int edgeCount = 0;
		digraph = new Digraph(V);
		//set final array to Digraph
		for (int i = degreeList.size() - 1; i >= 0; i--) {
			int v = degreeList.get(i)[0][0];
			int w = degreeList.get(i)[0][1];
			digraph.addEdge(v, w);

			//corner case (edgeCount++ if lastVertex not connected to bigV)
			if (v == lastVertex && w == bigV) {
				//dont iterate count
				traceLoop = true;
			}
		}
	
		//iterate if not connected in cycle
		//if (!traceLoop) {
		//	edgeCount++;
		//}

		scc = new TarjanSCC(digraph);
                //import strong CC list for comparing with trace
		List<List<Integer>> strongList = TarjanSCC.listReturn();
//                StdOut.println(Arrays.asList(strongList)); 
		//StdOut.println(Arrays.asList(strongList.get(2).get(3)));

		int edge = 0;
		digraphSCC = new Digraph(strongList.size());
		checkSCC = new TarjanSCC(digraph);	
                int sccNum = checkSCC.count();
		StdOut.println("SCC: " + sccNum + " STRONGLIST: " + strongList.size());
		TransitiveClosure tSCC = new TransitiveClosure(digraph);
                ArrayList<Integer>[] diList;
		digraph.addEdge(8, 22);
		digraph.addEdge(18, 22);
		digraph.addEdge(16, 22);
		digraph.addEdge(5, 22);
		digraph.addEdge(22, 8);
                digraph.addEdge(22, 18);
                digraph.addEdge(22, 16);
                digraph.addEdge(22, 5);
		

		//diList = (ArrayList<Integer>[])new ArrayList[strongList.size()];
		//for (int i = 0; i < strongList.size() - 1; i++) {
		//	diList[strongList.size()] = new ArrayList<Integer>();
		//}
		int newNum = checkSCC.count();
		StdOut.println("NUM: " + newNum);

		for (int i = 0; i < strongList.size() - 1; i++) {                        
			for (int k = 0; k < strongList.size() - 1; k++) {
					
				if (checkSCC.stronglyConnected(i, k) == (tSCC.reachable(i, k) && tSCC.reachable(k, i))) { // && tSCC.reachable(k, i))) {
					//check for duplicates (i, j and j, i)
					//if (diList[k].contains(i)) {
					//	break;
					//}

					StdOut.println("i: " + i + " k: " + k);
					StdOut.println("ADDED");
					//diList[i].add(k);
					//digraphSCC.addEdge(i, k);
					//edge++;
				}
				else {
					StdOut.println("HELLO");
					//digraphSCC.addEdge(i, k);
					//edge++;
				}
			}
                }

		

		StdOut.println("EDGE: " + edge);
		for (int i = 0; i < sccNum; i++) {
			StdOut.println("OUT: " + digraph.outdegree(i));
		}

		//Compare strongList with hashTrace to determine needed directed edges for SCC
/*		boolean flag = false;
		for (int i = 0 ; i < strongList.size(); i++) {
			flag = false;
			for (int k = 0; k < strongList.get(i).size(); k++) {
				//StdOut.println("i length: " + strongList.get(i).size());
				//StdOut.println("HEY: " + strongList.get(i).get(k));
					
				int temp = strongList.get(i).get(k);
				boolean hasVal = hashTrace.contains(temp);
				if (hasVal) {
					//go to next SCC
					break;
				}
				else {
					//StdOut.println("NOT IN TRACE: " + temp);
					flag = true;
					//edgeCount++;
					//break;
				}
				
				if (flag == true && k == strongList.get(i).size()-1) {
					edgeCount++;
					break;
				}
			}

		}
*/


//-------------------------------------------------------------------------------------------
		int iCount = 0;
		int iNext = 0;
		TransitiveClosure t = new TransitiveClosure(digraph);
                for (int i = strongList.size() - 1; i >= 0; i--) {
                        iNext = i - 1;
                        
			if (i == 0) {
                               	boolean res = t.reachable(strongList.get(i).get(0), bigV);
				if (res) {
					//do nothing
				}
				else {
					iCount++;
				}
                       	}
			else {

				boolean res = t.reachable(strongList.get(i).get(0), strongList.get(iNext).get(0));
				//StdOut.println(res);
				//StdOut.println("i = " + i + "  " + strongList.get(i).get(0) + " : " + strongList.get(iNext).get(0));
				if (!res) {
					iCount++;
				}

			}

                }
		StdOut.println(iCount);

//-------------------------------------------------------------------------------------------



//		StdOut.println("SIZE: " + strongList.size());
		//check if last vertex in trace connects to the first (bigV)
		
		TransitiveClosure tc = new TransitiveClosure(digraph);
//		StdOut.println("REACHABLE: " + tc.reachable(lastVertex, bigV));
		if (tc.reachable(lastVertex, bigV)) {
			//do nothing
		}
		else {
			edgeCount++;
		}
	
//		StdOut.println("EDGE: " + edgeCount);
		//initialize elements to false
		for (int i = 0; i < V; i++) {
			outDegree[i] = false;
			inDegree[i] = false;
		}	

		//set vertex to true if in/out degree greater than zero
		for (int i = 0; i < degreeList.size(); i++) {
			int outNum = degreeList.get(i)[0][0];
			int inNum = degreeList.get(i)[0][1];
			
			outDegree[outNum] = true;
			inDegree[inNum] = true;		
		}

		int countOut = 0;
		int countIn = 0;
		//counts number of false degrees
		for (int i = 0; i < V; i++) {
			if (!outDegree[i]) {
				countOut++;
			}

			if (!inDegree[i]) {
				countIn++;
			}
			//StdOut.println(degreeList.get(i)[0][0] + " " + degreeList.get(i)[0][1]);
			//StdOut.println(outDegree[i] + " : " + inDegree[i]);
		}

		//final printing of count and tree/non-tree edges
		if (countIn > countOut) {
			//StdOut.println(countIn);
		}
		else {
			//StdOut.println(countOut);
		}

		for (int i = 0; i < degreeList.size(); i++) {
                	StdOut.println(degreeList.get(i)[0][0] + " " + degreeList.get(i)[0][1]);
		}



//		String strD = digraph.toString();
//		StdOut.println(strD);
		String str = graph.toString();
		StdOut.println(str);


	}


}



