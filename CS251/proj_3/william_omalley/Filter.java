/*
*
*	Filter
*	Last modified: 10-15-2017
*	Uses a Doubly LinkedList (DLL) to store coordinates (x, y).
*	The coordinates are first sorted by the x-value and then 
*	filtered out if the current nodes (x, y) are both less than
*	the compared coordinates. The filtered list is then printed
*	to StdOut.
*
*/

public class Filter<x, y> {

	static Node first = null;
	static Node last = null;
	
        public static class Node<x, y> {
                public int x;
                public int y;
                public Node next;
                public Node prev;

                public Node(int x, int y) {
                        this.x = x;
                        this.y = y;
                }
        }


	/*
	*
	*	addLink
	*	adds the coordinates to a node in the DLL
	*
	*/
        public void addLink(int x, int y) {
                Node newNode = new Node(x, y);

                if (first == null) {
                        first = newNode;
                        last = newNode;
                }
                else {
                        first.prev = newNode;
                        newNode.next = first;
                        first = newNode;
                }
        }

	
	/*
	*
	*	removeNode
	*	Filters out any set of coordinates that are both
	*	inferior to the compared coordinates
	*
	*/
	Node removeNode(Node first) {
		
		Node headClone = first;
		Node step = headClone.next;
		
		while (step != null) {

			if (headClone.y < step.y) {
				
				//delete headClone node
				filterNodeOut(headClone);
				first = first.next;
				headClone = first;
				step = headClone.next;

			}
			else if (step.next == null) {
				if (headClone.next == null) {
					StdOut.println("Break Loop!");
					break; //breaks while loop
				}
				//check next node against list
				else {
					headClone = headClone.next;
					step = headClone.next;
				}
			}
			else {
				step = step.next;
			}
		}
		return (first);
	}

	
	/*
	*
	*
	*	filterNodeOut
	*	removes node
	*
	*/
	public void filterNodeOut(Node killClone) {		

		if (killClone == null) {
			return;
		}
		
		if (first == null) {
			return;
		}
		
		//middle
		if (killClone.next != null && killClone.prev != null) {
                         killClone.prev.next = killClone.next;
                         killClone.next.prev = killClone.prev;
                }     
		//tail  
		else if (killClone.prev != null) {
			killClone.prev.next = killClone.next;
		}
		//head
		else {
			first = first.next;
			first.prev = null;
		}

		return;
	}


	/*
	*
	*	QuickSort
	*
	*/
	Node partition(Node node, Node head) {

		int pivot = head.x;
		Node nodePrev = node.prev;

		for (Node index = node; index != head; index = index.next) {
		if (index.x <= pivot) {
                                nodePrev = (nodePrev == null) ? node : nodePrev.next;
                                int tempX = nodePrev.x;
                                int tempY = nodePrev.y;
                                nodePrev.x = index.x;
                                nodePrev.y = index.y;
                                index.x = tempX;
                                index.y = tempY;
                        }
                }
                nodePrev = (nodePrev == null) ? node : nodePrev.next;
                int tempX = nodePrev.x;
                int tempY = nodePrev.y;
                nodePrev.x = head.x;
                nodePrev.y = head.y;
                head.x = tempX;
                head.y = tempY;
                return nodePrev;
	}

	void quickSort(Node node, Node head) {
                if (head != null && node != head && node!= head.next) {
                        Node temp = partition(node, head);
                        quickSort(node, temp.prev);
                        quickSort(temp.next, head);
                }
        }
	
	Node tailNode(Node node) {
		while(node.next != null) {
			node = node.next;
		}
		return (node);
	}

	public void sort(Node node) {
		Node head = tailNode(node);
		quickSort(node, head);
	}

	public void printer (Node first) {
		while (first != null) {
			StdOut.print(first.x);
			StdOut.println(" " + first.y);
			first = first.next;
		}
	}

	/*
	*
	*	Main
	*	Reads values into DLL from text file and makes needed calls
	*
	*/
        public static void main(String[] args) {

                int N = 0; //number of coordinate sets (x, y)
                int colSize = 2; //predifined size of columns in the 2D array (only two values per line)
                int x = 0;
                int y = 0;

                N = StdIn.readInt();
                Filter list = new Filter();

                while (!StdIn.isEmpty()) {
                        x = StdIn.readInt();
                        y = StdIn.readInt();
                        list.addLink(x,y);
                }

		Node node = null;
		
		//sort list
		list.sort(list.first);		
	
		//filter list
		list.removeNode(list.first);
		

		//print final list
                list.printer(list.first);

        }
}

