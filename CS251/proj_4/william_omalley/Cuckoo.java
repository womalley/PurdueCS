/*
*
*	Cuckoo
*	Project 4
*	Last modified: 11/05/2017
*
*/

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.Arrays;

public class Cuckoo <K, V> {

	//global variables
	public static int r = 256;
	int size = 0;
	int loopCount = 0;
	int dCount = 0;
	boolean flag = false;        
	int i = 0;	
	int loops = 0;
	boolean dislodgeFlag = false; 
	boolean skipFlag = false;
	int cmdIndex = 0;
	int N = 0;
	boolean dislodged = false;

	public static long a1 = 0; //first prime number read
        public static long a2 = 0; //second prime number read
	public static Cuckoo<Integer, Integer> ht = new Cuckoo<>();

	public K[] hashKey = (K[]) new Object[r];
        public V[] hashVal = (V[]) new Object[r];

	private static final double epsilon = 0.05;

	/*
	*
	*	setVerbose
	*	when true, print statements are shown. When 
	*	false, print statements are not shown.
	*
	*/
	public boolean setVerbose(boolean v) {
		if (v == true) {
			return(true);
		}
		else {
			return(false);
		}
	}
	public boolean verbose = setVerbose(true); //false doesn't print out, true prints out trace
        
	/*
        *
        *       Main
        *       Reads in coordinate values into DLL and
        *       query values
        *
        */
        public static void main(String[] args) {
		//verbose = setVerbose(true);
		ht.start();
	}

	int stop = 0;
	public void start() {		
		String cmd = null;
		a1 = StdIn.readLong();
		a2 = StdIn.readLong(); //read second prime
	        N = StdIn.readInt(); //number of commands to execute
		if (verbose == true) {
			StdOut.println("(hash " + a1 + " " + a2 + " " + r + ")");
		}

                //read in record list
                for (cmdIndex = 0; cmdIndex < N; cmdIndex++) {
                        
			cmd = StdIn.readString();
			if (verbose == true) {
				//StdOut.println("\ncmd: " + cmd + " " + cmdIndex);
			}

			if (cmd.equals("size")) {
				int size = ht.size();
				if (verbose == true) {
					StdOut.println(size);
				}
			}
			else if (cmd.equals("put")) {
        			int key = StdIn.readInt();
				int val = StdIn.readInt();
				relocateDislodged = 0;	
				skipNext = 0;
				loopCount = 0;
				flag = true;
				ht.put(key, val); 
               		}
			else if (cmd.equals("contains")) {
                                int key = StdIn.readInt();
				boolean result = ht.contains(key); 
				if (verbose == true) {				
					if (result == true) {
						StdOut.println("yes");
					}
					else if (result == false) {
						StdOut.println("no");
					}
				}
			}
			else if (cmd.equals("get")) {
                                int key = StdIn.readInt();
				V getVal = (V)ht.get(key);
				if (verbose == true) {
					if (getVal == null) {
						StdOut.println("none");
					}
					else {
						StdOut.println(getVal);
					}
				}
			}
			else if (cmd.equals("delete")) {
                                int key = StdIn.readInt();
				ht.delete(key);
			}
		 }
	}


	/*
	*
	*	put
	*	places the read in key and value based on the h_a(x) equation
	*
	*/
	K newKey = null;
	int relocateDislodged = 0;
	int oldH1 = -1;
	int oldH2 = -1;
	int oldH = -1;
	public int storeH1 = -1;
	public int storeH2 = -1;
	int skipNext = 0;

        K oldKey = null;
        V oldVal = null;

	K storeKey = null;
	V storeVal = null;

	public void put(K key, V val) {
		int counter = 0;		
                double L_maxD = Math.ceil((3.0 * (Math.log(r) / Math.log(1.0 + epsilon)) + 1.0));
		int L_max = (int) L_maxD;

		dCount++;

		//indices of key
		int h1 = hashIndex(key, 1);
		int h2 = hashIndex(key, 2);
	
		//error catch if null
                if (key == null) {
                        throw new IllegalArgumentException("key is null!");
                }
		
                if (loopCount >= L_max) {
                        hashResize();
                }


		Integer tempKeyH1 = 0;
		Integer tempKeyH2 = 0;
		Integer intKey = (Integer)key;
		String stringH1 = null;
		String stringH2 = null;
		String stringKey = Integer.toString(intKey);

		Integer keyIndexH1 = hashIndex(key, 1);
		Integer keyIndexH2 = hashIndex(key, 2);

		String keyH1 = Integer.toString(keyIndexH1);
		String keyH2 = Integer.toString(keyIndexH2);

		if (hashKey[h1] != null) {
			tempKeyH1 = (Integer)hashKey[h1];
			stringH1 = Integer.toString(tempKeyH1);
		}
		if (hashKey[h2] != null) {
			tempKeyH2 = (Integer)hashKey[h2];
			stringH2 = Integer.toString(tempKeyH2);
		}

		if (skipNext == 0) { 		

			double sizeLimit = (2 * (1 + epsilon) * (size + 1));
			//new key the same as h1 key
			if (stringH1 != null && stringH1.equals(stringKey)) {
				hashVal[h1] = val;
				if (verbose == true) {
                	        	StdOut.println("(" + h1 + " " + key + " " + val + ")");
				}
			}
			//new key the same as h2 key
			else if (stringH2 != null && stringH2.equals(stringKey)) {
				hashVal[h2] = val;
				if (verbose == true) {
	                        	StdOut.println("(" + h2 + " " + key + " " + val + ")");
				}
			}
			else if ((double)r <= sizeLimit) {
        	                hashResize();
				put(key, val);
	                }
			//insert in first spot (empty)
			else if (hashKey[h1] == null) {
                                hashKey[h1] = key;
                                hashVal[h1] = val;
                                size++;
				if (verbose == true) {
                                	StdOut.println("(" + h1 + " " + key + " " + val + ")");
                                }

				if (dislodgeFlag == true) {
                                        hashResize();
                                }
			}
			//insert in second spot (first spot taken, second spot free)
			else if (hashKey[h1] != null && hashKey[h2] == null) {
				hashKey[h2] = key;
				hashVal[h2] = val;
				size++;
				if (verbose == true) {
	                        	StdOut.println("(" + h2 + " " + key + " " + val + ")");
				}
				if (dislodgeFlag == true) {
					hashResize();
				}
			}
			//both spots full, replace first spot and recursively call replaced value
			else if (hashKey[h1] != null && hashKey[h2] != null) {
				loopCount++;
				dislodged = true;

				//reinsertion of dislodged
				if (loopCount > 1) { 	

					if (oldH == h1) {
						//place in h2 spot
						relocateDislodged = 2;
					}
					else if (oldH == h2) {
						relocateDislodged = 1;
					}
					else {
					}
				}
				
				//new put command call resets relocateDislodged to zero				
			 	if (flag == true) {
					relocateDislodged = 0;
					flag = false;
				}

				if (relocateDislodged == 0 || relocateDislodged == 1) {
                        		if (verbose == true) {
						StdOut.println("(" + h1 + " " + key + " " + val + ")");
					}
					
					oldKey = hashKey[h1];
					oldVal = hashVal[h1];
					oldH = h1;
					hashKey[h1] = key;
					hashVal[h1] = val;
					relocateDislodged = 0;
				}
				else if (relocateDislodged == 2) { 
	        	                if (verbose == true) {
						StdOut.println("(" + h2 + " " + key + " " + val + ")");
        	                        }

					oldKey = hashKey[h2];
	                                oldVal = hashVal[h2];
					oldH = h2;
                        	        hashKey[h2] = key;
                	                hashVal[h2] = val;
					relocateDislodged = 0;
				}

				storeKey = oldKey;
				storeVal = oldVal;
				

				//recursive call to replace dislodged key and value
				put (oldKey, oldVal);

			}
			else {
				skipNext = 0;
			}
		
		}

		//skip hash (gets rid of dangling hash)	
		if (skipFlag == true) {
			skipNext = 1;
			skipFlag = false;
		}
	}


	/*
	*
	*	get
	*	obtains the value of the given key
	*
	*/	
	public V get(K key) {

		//error catch if null
                if (key == null) {
                        throw new IllegalArgumentException("key is null!");
                }

		int index1 = hashIndex(key, 1);
		int index2 = hashIndex(key, 2);
		boolean contain = contains(key);

		if (contain == false) {
			return null;
		}
		else if (hashKey[index1] == null && hashKey[index2] == null) {
			return null;
		}
		else if (hashVal[index1] != null && hashVal[index2] == null) {
			Integer intKey = (Integer)key;
			Integer tempKey1 = (Integer)hashKey[index1];
			if (intKey == tempKey1) {
				return (hashVal[index1]);
			}
		}
		else if (hashVal[index1] == null && hashVal[index2] != null) {
			Integer intKey = (Integer)key;
                        Integer tempKey2 = (Integer)hashKey[index2];
                        if (intKey == tempKey2) {
                                return (hashVal[index2]);
                        }

		}
		else if (hashVal[index1] != null && hashVal[index2] != null) {
                        Integer intKey = (Integer)key;
                        Integer tempKey1 = (Integer)hashKey[index1];
                        Integer tempKey2 = (Integer)hashKey[index2];
			String stringKey = Integer.toString(intKey);
			String stringTempKey1 = Integer.toString(tempKey1);
			String stringTempKey2 = Integer.toString(tempKey2);
                        if (intKey.equals(tempKey2)) {
                                return (hashVal[index2]);
                        }
			else {
				return (hashVal[index1]);
			}
		}
		else {
			if (verbose == true) {
				StdOut.println("Something went wrong");
			}
		}
		return (hashVal[index1]);
	}


	/*
	*
	*	delete
	*	removes the given key and value from the table
	*
	*/
	public void delete(K key) {

		//error catch if null
                if (key == null) {
                        throw new IllegalArgumentException("key is null!");
                }

		int tempH1 = hashIndex(key, 1);
		int tempH2 = hashIndex(key, 2);
	
		Integer intKey = (Integer)key;
		String stringKey = Integer.toString(intKey);
		Integer keyH1 = (Integer)hashKey[tempH1];
		String stringKeyH1 = "null";
                Integer keyH2 = (Integer)hashKey[tempH2];
                String stringKeyH2 = "null";
	
		if (hashKey[tempH1] != null) {
			stringKeyH1 = Integer.toString(keyH1);
		}
		
		if (hashKey[tempH2] != null) {
			stringKeyH2 = Integer.toString(keyH2);
		}
	
		if (stringKey.equals(stringKeyH1)) {
			size--;
			hashKey[tempH1] = null;
			hashVal[tempH1] = null;
		}
		else if (stringKey.equals(stringKeyH2)) {
			size--;
			hashKey[tempH2] = null;
			hashVal[tempH2] = null;
		}
		else {
			//doesn't exist (therefore, can't delete)
		}
	}


	/*
	*
	*	contains
	*	returns "yes" if the key is in the table
	*	
	*/
	public boolean contains(K key) {

		//error catch if null
		if (key == null) {
			throw new IllegalArgumentException("key is null!");
		}

		int index1 = hashIndex(key, 1);
		int index2 = hashIndex(key, 2);

                if (hashKey[index1] == null && hashKey[index2] == null) {
			return false;
                }
                else if (hashKey[index1] != null && hashKey[index2] == null) {
                        String intKey = Integer.toString((Integer)key);
                        String tempKey1 = Integer.toString((Integer)hashKey[index1]);
                        if (intKey.equals(tempKey1)) {
                                return (true);
                        }
                }
                else if (hashKey[index1] == null && hashKey[index2] != null) {
                        String intKey = Integer.toString((Integer)key);
                        String tempKey2 = Integer.toString((Integer)hashKey[index2]);
                        if (intKey.equals(tempKey2)) {
                                return (true);
                        }

                }
                else if (hashKey[index1] != null && hashKey[index2] != null) {
                        String intKey = Integer.toString((Integer)key);
                        String tempKey1 = Integer.toString((Integer)hashKey[index1]);
                        String tempKey2 = Integer.toString((Integer)hashKey[index2]);
                        if (intKey.equals(tempKey2)) {
                                return (true);
                        }
                        else if (intKey.equals(tempKey1)) {
                                return (true);
                        }
                }
                else {
                        //StdOut.println("SOMETHING WENT WRONG");
                }
                return (false);



	}

	
	/*
	*
	*	size
	*	returns the current size of the table
	*
	*/
	public int size() {
		return (size);
	}


	/*
	*
	*	reInsert	
	*	reInserts the keys entered in hashKey/hashVal when rehashing
	*
	*/
	public void reInsert(K[] tempKey, V[] tempVal) {
			
		if (verbose == true) {
			StdOut.println("(hash " + a1 + " " + a2 + " " + r + ")");
		}

		int index = 0;
		for (index = 0; index <= (r / 2) - 1; index++) {
			
			if (tempKey[index] != null) {
				K key = tempKey[index];
				V val = tempVal[index];
				put (key, val);
			}
		}
		skipFlag = true;

		//last reinsert (key last dislodged)
		if (cmdIndex == N - 1) {
			put (storeKey, storeVal);	
		}
		else {
			//do nothing
		}
	}

	
	/*
	*
	*	hashIndex
	*	obtains the index for the value of the key to be placed
	*
	*/
	public int hashIndex(K key, int hashNum) {
		if (hashNum == 1) {
			return (((int) ((a1 * (long) key.hashCode()) / 65536L)) % r);
		}
		else {
			return (((int) ((a2 * (long) key.hashCode()) / 65536L)) % r);
		}
	}

	/*
	*
	*	hashResize
	*
	*/
	public void hashResize() {
	        //clone hashKey and hashVal
                K[] tempKey = (K[]) new Object[r];
                System.arraycopy(hashKey, 0, tempKey, 0, r);

                V[] tempVal = (V[]) new Object[r];
                System.arraycopy(hashVal, 0, tempVal, 0, r);

                //double hash size (r)
                r = (r * 2);

                //increase hashkey and hashVal size (r)
                K[] doubleKey = (K[]) new Object[r];
                V[] doubleVal = (V[]) new Object[r];
                hashKey = doubleKey;
                hashVal = doubleVal;

                //reset cycling counter
                loopCount = 0;
                relocateDislodged = 0;
		size = 0;
                reInsert(tempKey, tempVal);
		dislodgeFlag = false;
	}

	
}


