/*
*
*       Query
*       Last modified: 10-16-2017
*
*/
import java.util.LinkedList;

public class Query<x, y> {

	//for x-sorted record list
	static Node head = null;
	static Node tail = null;
	
	//for query list
	static Node firstQ = null;
	static Node lastQ = null;
	
	//for T Binary Search Tree (DLL)
	static Node headT = null;
	static Node tailT = null;

	//for filter query results (L[v]) to be printed
	static Node headFinal = null;
	static Node tailFinal = null;

	//for temporary DLL for storing results of each query
	static Node headTemp = null;
	static Node tailTemp = null;

        static Node headTempX = null;
        static Node tailTempX = null;


	//Node for DLL
	public static class Node<x, y> {
		public int x;
		public int y;
		public String none = "none";
		public Node next;
		public Node prev;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
			this.none = none;
		}
	}

	
	//DLL sorted by x initialization
	static Query list = new Query();

	//DLL tree T initialization
	static Query listTree = new Query();	

	//DLL for list of queries
        static Query listQuery = new Query(); //DLL made for query list
	
	//DLL for final list of query results
	static Query listFinal = new Query();

	//DLL for temp holding of results of query (to be sorted and added to final)
	static Query listTemp = new Query();

        static Query listTempX = new Query();


	/*
	*
	*	sortCompare
	*	takes the input sorted by x values, adds nodes with greater x-values
	*	than the query to a new DLL. Sorts the x filtered DLL by the y-values
	*	adds nodes with greater y-values to new temporary DLL and sorts it by
	*	the x-values again. Results added to a final DLL. Steps are repeated
	*	until all queries are added to the final DLL. Then the DLL is printed.
	*
	*/
	int flagIterator = 0;
	int sortFlag = 0;
	int lengthX = 0;
	int lengthIterator = 0;
	int added = 0;
	int added2 = 0;

	public void sortCompare(Node headCopy, Node query, Node tempX, Node result) {
		int x = 0;
		int y = 0;

	
		//filter y into new DLL
		if ((headCopy != null) && (flagIterator == 0)) {
			if (query.y < headCopy.y) {
				//add to new DLL that will be sorted by x
				x = headCopy.x;
				y = headCopy.y;
				listTemp.addLink(x,y,3);
				headCopy = headCopy.next;
				added = 1;
                                sortCompare(headCopy, query, tempX, result);
			}
			else {
				headCopy = headCopy.next;
				sortCompare(headCopy, query, tempX, result);
			}
		}
		else if ((headCopy == null) && (flagIterator == 0)) {
			flagIterator = 1; //done with filtering y values
			headCopy = head;
			sortFlag = 1;
		}

		//sort y filtered temp DLL
		if (sortFlag == 1) {
			if (added > 0) {
				listTemp.sort(listTemp.headTemp);
			}
			sortFlag = 2;
			tempX = headTemp;
		}

		//filter sorted y temp DLL for x values
		if ((tempX != null) && (flagIterator == 1)) {
			if (query.x < tempX.x) {
                                //add to new DLL that will be sorted by x
				if (added > 0) {
					x = tempX.x;
	                                y = tempX.y;
                	                listTempX.addLink(x,y,4);
					added2 = 1;
				}
				
                                tempX = tempX.next;
                                sortCompare(headCopy, query, tempX, result);
                        }
                        else {

                                tempX = tempX.next;
				sortCompare(headCopy, query, tempX, result);
                        }
                }
                else if ((tempX == null) && (flagIterator == 1)) {
	                if (added == 0 || added2 == 0) {
                                listFinal.addNull(-1, -1, 1);
                        }

                        flagIterator = 2; //done with filtering x values
                        tempX = headTemp;
		}

		if (flagIterator == 2) {
			lengthX = listTempX.getLength(3);
			flagIterator = 3;
			result = headTempX;
		}

		//fill in final DLL
		if ((flagIterator == 3) && (lengthIterator < lengthX)) {
			x = result.x;
			y = result.y;
			listFinal.addLink(x,y,2);

			lengthIterator++;
			result = result.next;
                        sortCompare(headCopy, query, tempX, result);
		}
		else if ((flagIterator == 3) && (lengthIterator <= lengthX)) {
			sortFlag = 0;
			flagIterator = 4;	
			lengthIterator = 0;
			lengthX = 0;
			result = headTempX;
		}
		
		//reset all temps
		if ((flagIterator == 4) && (headTemp != null)) {
			headTemp = headTemp.next;
			sortCompare(headCopy, query, tempX, result);
		}
	
		if ((flagIterator == 4) && (headTempX != null)) {
			headTempX = headTempX.next;
			sortCompare(headCopy, query, tempX, result);
		}

		if ((flagIterator == 4) && (headTemp == null) && (headTempX == null)) {
			added = 0;
			added2 = 0;
			flagIterator = 0;
			query = query.next;
			if (query == null) {
				//end
			}
			else {
                        	sortCompare(headCopy, query, tempX, result);			
			}
		}
	}




	/*
	*
	*	addLink
	*	adds the coordinates (x, y) into a DLL
	*
	*/
	public void addLink(int x, int y, int val) {
		Node newNode = new Node(x, y);
		
		if (val == 0) {
			if (head == null) {
				head = newNode;
				tail = newNode;
			}
			else {
				tail.next = newNode;
				newNode.prev = tail;
				tail = newNode;			
			}
			newNode.none = "occupied";
		}
		else if (val == 1) {
			if (headT == null) {
                                headT = newNode;
                                tailT = newNode;
                        }
                        else {
                                tailT.next = newNode;
                                newNode.prev = tailT;
                                tailT = newNode;
                        }
			newNode.none = "occupied";
		}
		else if (val == 2) {
                        if (headFinal == null) {
                                headFinal = newNode;
                                tailFinal = newNode;
                        }
                        else {
                                tailFinal.next = newNode;
                                newNode.prev = tailFinal;
                                tailFinal = newNode;
                        }
                        newNode.none = "occupied";
                }
                else if (val == 3) {
                        if (headTemp == null) {
                                headTemp = newNode;
                                tailTemp = newNode;
                        }
                        else {
                                tailTemp.next = newNode;
                                newNode.prev = tailTemp;
                                tailTemp = newNode;
                        }
                        newNode.none = "occupied";
                }
                else if (val == 4) {
                        if (headTempX == null) {
                                headTempX = newNode;
                                tailTempX = newNode;
                        }
                        else {
                                tailTempX.next = newNode;
                                newNode.prev = tailTempX;
                                tailTempX = newNode;
                        }
                        newNode.none = "occupied";
                }

	}

	
	/*
	*
	*	addNull
	*	adds a null ("none") to the children of
	*	the just set parent in the DLL of T
	*
	*/
        public void addNull(int x, int y, int val) {
        	Node newNode = new Node(x, y);
		if (val == 0) {
	                if (headT == null) {
		                headT = newNode;
                	        tailT = newNode;
	                }
        	        else {
                	        tailT.next = newNode;
                        	newNode.prev = tailT;
	                        tailT = newNode;
        	        }
                	newNode.none = "none";
		}
		else if (val == 1) {
                        if (headFinal == null) {
                                headFinal = newNode;
                                tailFinal = newNode;
                        }
                        else {
                                tailFinal.next = newNode;
                                newNode.prev = tailFinal;
                                tailFinal = newNode;
                        }
                        newNode.none = "none";	
		}
        }	


	/*
	*
	*	addQuery
	*	adds coordinates (x, y) of query to DLL for queries
	*
	*/	
        public void addQuery(int x, int y) {
                Node newNode = new Node(x, y);

                if (firstQ == null) {
                        firstQ = newNode;
                        lastQ = newNode;
                }
		else {
			lastQ.next = newNode;
			newNode.prev = lastQ;
			lastQ = newNode;
		}
        }


	/*
	*
	*	Quick Sort functions
	*	(Same as Filter.java)
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


	/*
	*
	*	Quick Sort function for y-value sorting
	*
	*/
        Node partitionY(Node node, Node head) {

                int pivot = head.y;
                Node nodePrev = node.prev;

                for (Node index = node; index != head; index = index.next) {
                        if (index.y <= pivot) {
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

        void quickSortY(Node node, Node head) {

                if (head != null && node != head && node!= head.next) {
                        Node temp = partitionY(node, head);
                        quickSortY(node, temp.prev);
                        quickSortY(temp.next, head);
                }
        }

        Node tailNodeY(Node node) {
                while(node.next != null) {
                        node = node.next;
                }
                return (node);
        }

        public void sortY(Node node) {
                Node head = tailNodeY(node);
                quickSortY(node, head);
        }


	/*
	*
	*	printer
	*	prints the result of the queries
	*
	*/
	public void printer(Node headCopy) {
		Node copy = headCopy;
		while (copy != null) {
		
			if (copy.none == "none") {
				StdOut.println(copy.none);
			}
			else {
				StdOut.print(copy.x);
				StdOut.println(" " + copy.y);
			}	
			copy = copy.next;
		}
	}


	/*
	*
	*	linkTree
	*	uses sorted DLL to create a DLL sorted as
	*	a Binary Search Tree
	*
	*/
/*	public void linkTree(Node headCopy, Node headCopyT, int size) {
		Node iterator = headCopy;		
			
		int addVal = 1; //for addLink function

		int index = 0;
		int headIndex = ((size) / 2);
		int leftIndex = ((headIndex - 1) / 2);
		int rightIndex = ((3 * headIndex + 1) / 2);
		int xLeft = 0;
		int yLeft = 0;		
		int xRight = 0;
		int yRight = 0;
		int xHead = 0;
		int yHead = 0;
		int obtain = 0; //counts total node values obtained
		int nullVal = -1; //the value for each null child

		while (iterator != null) {

			//obtain T head (root)
			if (index == headIndex) {
				xHead = iterator.x;
				yHead = iterator.y;
				obtain++;	
			}
			//obtain T left child
			if (index == leftIndex) {
				xLeft = iterator.x;
                               	yLeft = iterator.y;
                      		obtain++;
			}	
			//obtain T right child
			if (index == rightIndex) {
				xRight = iterator.x;
                                yRight = iterator.y;
                               	obtain++;
			}
			//add values to DLL T
			if (obtain == 3) {
				listTree.addLink(xHead,yHead,addVal);
				listTree.addLink(xLeft,yLeft,addVal);
				listTree.addLink(xRight,yRight,addVal);
				listTree.addNull(nullVal, nullVal, 0);
				listTree.addNull(nullVal, nullVal, 0);
				listTree.addNull(nullVal, nullVal, 0);
				listTree.addNull(nullVal, nullVal, 0);

				break;
			}

			iterator = iterator.next;	
			index++;
		}

		Node cmp = headCopy;
		Node loopT = headT;
	
		buildTree(headIndex, leftIndex, rightIndex, size, cmp, loopT);		
	}
*/
	
	/*
	*
	*	buildTree
	*	adds nodes in BST order in the DLL 
	*	given the root and roots children
	*
	*/
/*	int indexT = 1;
	int cmpIndex = 0;

	public void buildTree(int headIndex, int leftIndex, int rightIndex, int size, Node cmp, Node loopT) {
		
		int x = 0;
		int y = 0;
		String none = "empty";
		int length = 0;
		int diffLen = 0; //difference in length of sorted DLL and tree DLL T	
		int indexCopyT = 0;		
	
		while (cmp != null) {
			
			//skip root and root's children (already set in linkTree)
			if (cmpIndex == headIndex || cmpIndex == leftIndex || cmpIndex == rightIndex) {
				cmp = cmp.next;
				cmpIndex++;
			}

			//if spot none, add
			if (loopT.none == "none") {

				loopT.x = cmp.x;
				loopT.y = cmp.y;
				loopT.none = "occupied";				
	
				cmp = cmp.next; //place next DLL in tree
				cmpIndex++;

				length = listTree.getLength(0);
				diffLen = length - ((2* indexT) + 1);	

				while (diffLen < 0) {
					
					listTree.addNull(-1, -1, 0);
					diffLen++;
				}

				loopT = headT;	
				indexT = 1;
			}
		
			//DLL cmp less than tree node value
			else if (cmp.x < loopT.x) {

				indexCopyT = indexT;
				for (; indexCopyT > 0; indexCopyT--) {
					loopT = loopT.next;
					indexT++;
				}
				//buildTree(headIndex, leftIndex, rightIndex, size, cmp, loopT);
			}
			
			//DLL cmp greater than tree node value
			else if (cmp.x > loopT.x) {

				indexCopyT = indexT;
				for (; indexCopyT > 0; indexCopyT--) {
					loopT = loopT.next;
					indexT++;
				}
				loopT = loopT.next;
				indexT++;

				//buildTree(headIndex, leftIndex, rightIndex, size, cmp, loopT);
			}
		}
	}
*/

	/*
	*
	*	getLength
	*	obtains the length of a linked list
	*
	*/
	public int getLength(int val) {

		if (val == 0) {
			Node tmp = headT;
			int length = 0;
		
			while (tmp != null) {
				length++;
				tmp = tmp.next;
			}
			return (length);
		}
		else if (val == 1) {
                        Node tmp = headTemp;
                        int length = 0;

                        while (tmp != null) {
                                length++;
                                tmp = tmp.next;
                        }
                        return (length);
		}
                else if (val == 2) {
                        Node tmp = firstQ;
                        int length = 0;

                        while (tmp != null) {
                                length++;
                                tmp = tmp.next;
                        }
                        return (length);
                }
                else if (val == 3) {
                        Node tmp = headTempX;
                        int length = 0;

                        while (tmp != null) {
                                length++;
                                tmp = tmp.next;
                        }
                        return (length);
                }


		return(-1);
	}


	/*
	*
	*	searchTree
	*	searches the tree comparing the query
	*	to each node. Adds node to Final DLL
	*
	*/
/*	int indexTree = 1; //reset for use in searchTree (original initialization in buildTree)
	int queryIndex = 0;
	int flag = 0; //0 if no nodes added for query, 1 if nodes added
	
	public void searchTree(Node tree, Node cmpQ, Node query) {		
		int x = 0;
		int y = 0;
                int indexCopyT = 0;
		Node root = headT;
		int tempLength = 0;		
		int queryLength = listQuery.getLength(2);

                if (queryIndex < queryLength) {

				
			//add to temporary DLL for sorting
			if ((query.x < tree.x) && (query.y < tree.y) && tree.none != "none") {
				x = tree.x;
				y = tree.y;
				listTemp.addLink(x,y,3);
				tree = tree.next;
				flag = 1;	
			}
			else {
				tree = tree.next;
			}
		
                        //end of tree
                        if (tree == null) {

                        	int a = 0;
                                int b = 0;
                                tempLength = listTemp.getLength(1);

                                //SORT THE DLL AND ADD TO FINAL DLL
                                if (flag == 1) {
	                                listTemp.sort(listTemp.headTemp);
					flag = 0;	
                                        while (tempLength > 0) {
						a = headTemp.x;
                                                b = headTemp.y;
                                                listFinal.addLink(a,b,2);
                                                headTemp = headTemp.next;
                                                tempLength--;
                                        }
                                }
                                else {
                	                listFinal.addNull(-1, -1, 1);
                                }

                                //reset temp DLL
                                while (headTemp != null) {
                        	        headTemp = null;
                                        headTemp = headTemp.prev;
                                }

                                tree = headT;
                                query = query.next; //steps the query DLL
                                queryIndex++;
			}

			if (tree != null) {	
				searchTree(tree, cmpQ, query);
			}
		}
	}
*/

	/*
	*
	*	Main
	*	Reads in coordinate values into DLL and
	*	query values
	*
	*/
	public static void main(String[] args) {
		
		int N = 0; //number of records
		int Q = 0; //number of queries
		int x = 0; //x-coordinate value
		int y = 0; //y-coordinate value
		int index = 0; //index for looping input in
		int addVal = 0; //for addLink

		N = StdIn.readInt(); //number of records read in

		//read in record list
		for (index = 0; index < N; index++) {
			x = StdIn.readInt();
			y = StdIn.readInt();
			list.addLink(x,y,addVal);
		}

		Q = StdIn.readInt(); //number of queries read in
		Query listQuery = new Query();

		//read in query list
		for (index = 0; index < Q; index++) {
			x = StdIn.readInt();
			y = StdIn.readInt();
			listQuery.addQuery(x,y);
		}

		//sort records list		
		list.sortY(list.head);

                Node headCopy = head;
                Node query = firstQ;
                Node tempX = headTemp;
                Node result = headTempX;

		list.sortCompare(headCopy, query, tempX, result);

		list.printer(listFinal.headFinal);


/*		//create DLL tree list
		listTree.linkTree(list.head, listTree.headT, N);	

                Node tree = headT; //tree DLL
                Node cmpQ = headFinal; //query result DLL
                Node query = firstQ; //query list
		
		//filter tree
		listFinal.searchTree(listTree.headT, listFinal.headFinal, listQuery.firstQ);
		
		list.printer(listFinal.headFinal);
*/
	}
}
