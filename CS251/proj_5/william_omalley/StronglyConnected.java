/*
*
*	StronglyConnected
*	Project 5
*	Last modified: 11/20/2017
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

public class StronglyConnected extends Graph {

	//Global variables
	public static int V = 0;
	public static int E = 0;
	public static int bigV = 0;
	public static Graph graph;
	public static Digraph digraph;
	public static TarjanSCC scc;
	public static ArrayList<Integer> trace;

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
                	                if (!contained) {
						trace.add(v);
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

		//Convert trace form List to Set
		Set<Integer> hashTrace = new HashSet<Integer>(trace);


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
	

		scc = new TarjanSCC(digraph);
                //import strong CC list for comparing with trace
		List<List<Integer>> strongList = TarjanSCC.listReturn();

		//Compare strongList with hashTrace to determine needed directed edges for SCC
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
				if (!res) {
					iCount++;
				}

			}

                }
		StdOut.println(iCount);

		//check if last vertex in trace connects to the first (bigV)
		TransitiveClosure tc = new TransitiveClosure(digraph);
		if (tc.reachable(lastVertex, bigV)) {
			//do nothing
		}
		else {
			edgeCount++;
		}
	
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
		}

		for (int i = 0; i < degreeList.size(); i++) {
                	StdOut.println(degreeList.get(i)[0][0] + " " + degreeList.get(i)[0][1]);
		}
	}
}

