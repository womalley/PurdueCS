/*
*
*       Filter
*       Proj_3
*       last modified: 10-04-2017
*       Filter determines if the previous i and j values in the 2D array 
*	are both less than the current. If so, the previous values are deleted. 
*
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;

/*
class Object {
	int x;
        int y;

        Object (int x, int y) {
        	this.x = x;
                this.y = y;
        }
} 
*/

/*
public class DoublyLinkedList<x, y> {

	private static class Node<x, y> {
        	private int x;
		private int y;
        	private Node next;
        	private Node prev;

	        public Node(int x, int y) {
        		this.x = x;
			this.y = y;	
	        }
	}
	public Node last = null;
        public Node first = null;

        public void addLink(int x, int y) {
        	Node newNode = new Node(x, y);

	        if (newNode.isEmpty()) {
        		newNode.prev = null;
            		newNode.next = null;
            		last = newNode;
            		first = newNode;
  	        } 
		else {
            		first.prev = newNode;
            		newNode.next = first;
            		newNode.prev = null;
            		first = newNode;
        	}
	}
}
*/
public class Filter {

	public DoublyLinkedList head;
	
	//filters list of coordinates then calls printer()
	public void filter() {

			
			

		//calls printer to print the sorted coordinates
		printer();
	}



	//prints filtered list
//	public void printer(Comparable[][] Sfilter) {
	public void printer() {
		//Node tempNode = new Node(x, y);
                Node tempNode = head;
                while (tempNode.next != null && tempNode != null) {
                        StdOut.printf(tempNode.x + " ");
                        StdOut.println(tempNode.y);
                        tempNode = tempNode.next;
                }
/*
		int x = 0;
		int y = 0;
		int index = 0;
		//prints coordinates in proper format
		for (index = 0; index <= totalCoord - 1; index++) {
			StdOut.println(Sfilter[x][y] + " " + Sfilter[x][y+1]);
			x++;
		}
*/
	}

	//reads values into 2D array from text file and calls Filter
	public static void main(String[] args) {
		
		int N = 0; //number of coordinate sets (x, y)
		int colSize = 2; //predifined size of columns in the 2D array (only two values per line)
		int x = 0;
		int y = 0;
		

		N = StdIn.readInt();
		//int coordCount = N; //holds current count of coordinate sets (will be decremented in filter() as coordinates deleted)
		//Comparable[][] Object = new Comparable[N][colSize]; //custom object to hold values	
		//ArrayList<Object> list = new ArrayList<Object>();		
		DoublyLinkedList list = new DoublyLinkedList();

		while (!StdIn.isEmpty()) {
			x = StdIn.readInt();
			y = StdIn.readInt();			
			list.addLink(x,y);
		}


/*		Node tmpNode = head;
	        while (tmpNode.next != null && tmpNode != null) {
			StdOut.printf(tmpNode.x + " ");
			StdOut.println(tmpNode.y);
	        	tmpNode = tmpNode.next;
       		}		
*/
/*		//ArrayList of object
                while (!StdIn.isEmpty()) {

			x = StdIn.readInt();
			y = StdIn.readInt();
			Object coord = new Object(x, y);
			list.add(coord);
                }
*/

/*		for (int i = 0; i < (N); i++) {	
			System.out.printf(list.get(i).x + " ");
			System.out.println(list.get(i).y);
		}
*/
		//filters out (x, y) coordinates and then prints filtered list
		//filter(list, coordCount);
		
		//calls filter
		filter();	
	}
}

