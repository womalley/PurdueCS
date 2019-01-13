/*
*
*	Filter
*	Last modified: 10-08-2017
*	Uses a Doubly LinkedList (DLL) to store coordinates (x, y).
*	The coordinates are first sorted by the x-value and then 
*	filtered out if the current nodes (x, y) are both less than
*	the compared coordinates. The filtered list is then printed
*	to StdOut.
*
*/

public class FilterNode<x, y> {

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
	Node partition(Node l, Node h) {

		int pivot = h.x;
		Node i = l.prev;

		for (Node j=l; j != h; j = j.next) {
			if (j.x <= pivot) {
				i = (i == null) ? l : i.next;
				int temp = i.x;
				int tempY = i.y;
				i.x = j.x;
				i.y = j.y;
				j.x = temp;
				j.y = tempY;
			}
		}
		i = (i == null) ? l : i.next;
		int temp = i.x;
		int tempY = i.y;
		i.x = h.x;
		i.y = h.y;
		h.x = temp;
		h.y = tempY;
		return i;
	}

	void quickSort(Node l, Node h) {
		
		if (h != null && l != h && l!= h.next) {
			Node temp = partition(l, h);
			quickSort(l, temp.prev);
			quickSort(temp.next, h);
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
                FilterNode list = new FilterNode();

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

