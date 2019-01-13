/*
*
*	Cuckoo
*	Project 4
*	Last modified: 10/27/2017
*
*/

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.AbstractMap;
import java.util.Arrays;

public class CuckooOld <K, V> {

	//global variables
	public static int r = 256;
	int size = 0;
	int loopCount = 0;
	boolean flag = false;        
	public static long a1 = 0; //first prime number read
        public static long a2 = 0; //second prime number read
	public static CuckooOld<Integer, Integer> ht = new CuckooOld<>();

//	public Cuckoo<K, V> hash[][] = new Cuckoo[2][r];
	public K[] hashKey = (K[]) new Object[r];
        public V[] hashVal = (V[]) new Object[r];

/*	public Entry[] hash = (Entry[]) new Object[r];

	class Entry {
		K key;
		V val;
		Entry next;

		Entry (K k, V v) {
			val = v;
			key = k;
		}
	}
*/

	private static final double epsilon = 0.05;


        /*
        *
        *       Main
        *       Reads in coordinate values into DLL and
        *       query values
        *
        */
        public static void main(String[] args) {
//	        Cuckoo<Integer, Integer> ht = new Cuckoo<>();
		ht.start();
	}

	int stop = 0;
	public void start() {		
                int N = 0; //number of commands to be executed/read
                int index = 0; //index for looping input in
		String cmd = null;

		a1 = StdIn.readLong();
		a2 = StdIn.readLong(); //read second prime
	        N = StdIn.readInt(); //number of commands to execute
	
		StdOut.println("(hash " + a1 + " " + a2 + " " + r + ")");
	
                //read in record list
                for (index = 0; index < N; index++) {
                        
			cmd = StdIn.readString();
			StdOut.println("cmd: " + cmd);
			
			if (cmd.equals("size")) {
				int size = ht.size();
				StdOut.println(size);
			}
			else if (cmd.equals("put")) {
        			int key = StdIn.readInt();
				int val = StdIn.readInt();
	
				//stop++;

				ht.put(key, val); //maybe ht. before put?
               		}
			else if (cmd.equals("contains")) {
                                int key = StdIn.readInt();
				ht.contains(key); //maybe ht. before contains?
			}
			else if (cmd.equals("get")) {
                                int key = StdIn.readInt();
			}
			else if (cmd.equals("delete")) {
                                int key = StdIn.readInt();
			}
		 }
                for (int i = 0; i < r-1; i++) {
                        StdOut.print(hashKey[i] + " ");
                }

                StdOut.println();

                for (int i = 0; i < r-1; i++) {
                       StdOut.print(hashVal[i] + " ");
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
	K oldKey = null;
	V oldVal = null;
	public void put(K key, V val) {
		int counter = 0;		
                double L_maxD = Math.ceil((3.0 * (Math.log(r) / Math.log(1.0 + epsilon)) + 1.0));
		int L_max = (int) L_maxD;
//		StdOut.println("L_max: " + L_max);
	
		//indices of key
		int h1 = hashIndex(key, 1);
		int h2 = hashIndex(key, 2);
	
//		StdOut.println("hashcode" + key.hashCode());	
//		StdOut.println("key: " + key + " h1: " + h1 + " h2: " + h2);
		//error catch if null
                if (key == null) {
                        throw new IllegalArgumentException("key is null!");
                }


//		StdOut.println("key: " + key + " val: " + val);
//		StdOut.println("hash index: " + hashKey[h1]);

                //infinite loop catch
                if (loopCount >= L_max) {

                        //clone hashKey and hashVal
                        K[] tempKey = (K[]) new Object[r];
                        System.arraycopy(hashKey, 0, tempKey, 0, r);

                        V[] tempVal = (V[]) new Object[r];
                        System.arraycopy(hashVal, 0, tempVal, 0, r);


/*                        StdOut.println("\n\nhashKey Old: ");
                        for (int i = 0; i < r-1; i++) {
                                StdOut.print(hashKey[i] + " ");
                        }

                        StdOut.println();

                        StdOut.println("\n\ntempKey: ");
                        for (int i = 0; i < r-1; i++) {
                               StdOut.print(tempKey[i] + " ");
                        }
*/

                        //empty hashKey and hashVal
                        //Arrays.fill(hashKey, null);
                        //Arrays.fill(hashVal, null);

                        //double hash size (r)
                        r = (r * 2);

                        //increase hashkey and hashVal size (r)
                        K[] doubleKey = (K[]) new Object[r];
                        V[] doubleVal = (V[]) new Object[r];
                        hashKey = doubleKey;
                        hashVal = doubleVal;
			
/*			StdOut.println("\n\nhashKey New: ");
                        for (int i = 0; i < r-1; i++) {
                                StdOut.print(hashKey[i] + " ");
                        }
*/
                        //rehash into new array


                        //reset cycling counter
                        loopCount = 0;

			reInsert(tempKey, tempVal);

                        //StdOut.println("Good-Bye");
                        //System.exit(0);
                        //start();
                }

		
		//insert in first spot (empty)
		if (hashKey[h1] == null) {
//			StdOut.println("FIRST SPOT NULL");
			hashKey[h1] = key;
			hashVal[h1] = val;
			StdOut.println("(" + h1 + " " + key + " " + val + ")");
		}
		//new key the same as h1 key
		else if (hashKey[h1] == key) {
//			StdOut.println("NEW KEY THE SAME AS H1");
			hashVal[h1] = val;
                        StdOut.println("(" + h1 + " " + key + " " + val + ")");
		}
		//new key the same as h2 key
		else if (hashKey[h2] == key) {
//			StdOut.println("NEW KEY THE SAME AS H2");
			hashVal[h2] = val;
                        StdOut.println("(" + h2 + " " + key + " " + val + ")");
		}
		//insert in second spot (first spot taken, second spot free)
		else if (hashKey[h1] != null && hashKey[h2] == null) {
//			StdOut.println("SECOND SPOT FREE");
			hashKey[h2] = key;
			hashVal[h2] = val;
                        StdOut.println("(" + h2 + " " + key + " " + val + ")");
		}
		//for recursive rehash
/*		else if (hashKey[h1] != null && hashKey[h2] != null && flag == true) {
			StdOut.println("OH SHIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIT");			
			//stored newKey indices			
/*			int storedH1 = hashIndex(newKey, 1);
			int storedH2 = hashIndex(newKey, 2);

			if (storedH1 == h1) {
				hashKey[h2] = key;
				hashVal[h2] = val;
			}
			else if (storedH2 == h2) {
				hashKey[h1] = key;
				hashVal[h1] = val;
			}
*/

			
/*			if (relocateDislodged == 1) {
				hashKey[h1] = key;
				hashVal[h1] = val;
			}
			else if (relocateDislodged == 2) {
				hashKey[h2] = key;
                                hashVal[h2] = val;
			}

			relocateDislodged = 0;
			flag = false;
		}
*/		//both spots full, replace first spot and recursively call replaced value
		else if (hashKey[h1] != null && hashKey[h2] != null) {
//			StdOut.println("BUMP THAT BITCH AND LOOP HER AROUND BIG BOIII");			
		
			loopCount++;
	
//			K oldKey = hashKey[h1];
//			V oldVal = hashVal[h1];
			
			//reinsertion of dislodged
			if (oldH1 != -1 && oldH2 != -1) {
				
				int storedH1 = hashIndex(newKey, 1);
				int storedH2 = hashIndex(newKey, 2);

				if (relocateDislodged == 1) {
                                	hashKey[h1] = key;
                                	hashVal[h1] = val;
                        	}
                        	else if (relocateDislodged == 2) {
                                	hashKey[h2] = key;
                                	hashVal[h2] = val;
                        	}
				relocateDislodged = 0;
			}

			
			StdOut.println("LOOP: " + loopCount + "--------------------------------------------------------");
			//dislodged key and value
			//K oldKey = hashKey[h1];
			//V oldVal = hashVal[h1];
		
                        //first loop base case
                        if (loopCount == 1) {
                                oldKey = hashKey[h1];
                                oldVal = hashVal[h1];
                        }

	
			//set new key and value
			hashKey[h1] = key;
			hashVal[h1] = val;
			
			
			//save new key
//			newKey = key;

 /*                       if (relocateDislodged == 0 || relocateDislodged == 1) {
                                StdOut.println("(" + h1 + " " + key + " " + val + ")");
                        }
                        else if (relocateDislodged == 2) {
                                StdOut.println("(" + h2 + " " + key + " " + val + ")");
                        }
*/			
			
                        StdOut.println("\nNew Hashes: " + key + " " + val);
                        StdOut.println("Old Hashes: " + oldKey + " " + oldVal + "\n\n");


			if (loopCount > 1 || loopCount == L_max) {	
				oldH1 = hashIndex(oldKey, 1);
				oldH2 = hashIndex(oldKey, 2);
				
				StdOut.println("oldH1: " + oldH1);
                                StdOut.println("H1: " + h1);
                                StdOut.println("oldH2: " + oldH2);
                                StdOut.println("H2: " + h2);
				

				if (oldH1 == h1) {
					//set old to h2 location
					relocateDislodged = 2;
					StdOut.println("FIRST H1____________________________________________________________________________");
					StdOut.println("oldH2: " + oldH2 + " h2: " + h2);
				}
				else if (oldH2 == h2) {
					//set old to h1 location
					relocateDislodged = 1;
                                        StdOut.println("FIRST H2____________________________________________________________________________");
                                        StdOut.println("oldH1: " + oldH1 + " h1: " + h1);
				}
				else if (oldH1 == h2) {
					//set old to h2 location
					relocateDislodged = 1;
                                        StdOut.println("Second H1____________________________________________________________________________");
                                        StdOut.println("oldH1: " + oldH1 + " h2: " + h2);
				}
				else if (oldH2 == h1) {
					//set old to h1 location
					relocateDislodged = 2;
                                        StdOut.println("Second H2____________________________________________________________________________");
                                        StdOut.println("oldH2: " + oldH2 + " h1: " + h1);
				}
				else {
					StdOut.println("498QRPTHFV;NODT4GRAFE8-9[0UOAL;GHAPLGFQ4AKLFADLLJFAD;FJDKF;AUT89OFKDLTFKJQ4TR8FAFJIADO");
				}

				flag = true;
//				StdOut.println("FLAAAAAAAAAAAAAAG VALUE: " + flag);
			}
			
			if (relocateDislodged == 0 || relocateDislodged == 1) {
                        	StdOut.println("(" + h1 + " " + key + " " + val + ")");
			}
			else if (relocateDislodged == 2) { 
	                        StdOut.println("(" + h2 + " " + key + " " + val + ")");
			}

                        oldKey = hashKey[h1];
                        oldVal = hashVal[h1];
			
			//recursive call to replace dislodged key and value
			put (oldKey, oldVal);
		}
		
	}


	/*
	*
	*	get
	*	obtains the value of the given key
	*
	*/	
	public V get(K key) {
		StdOut.println("GET FUNC");

		//error catch if null
                if (key == null) {
                        throw new IllegalArgumentException("key is null!");
                }

		int index1 = hashIndex(key, 1);
		int index2 = hashIndex(key, 2);

		if (key == hashKey[index1]) {
			return (hashVal[index1]);
			//StdOut.println(hash[index1].key);
		}
		else if (key == hashKey[index2]) {
			return (hashVal[index2]);
			//StdOut.println(hash[index2].key);
		}
		else {
			return null;
			//StdOut.println("none");
		}

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

		StdOut.println("DELETE FUNC");
	}


	/*
	*
	*	contains
	*	returns "yes" if the key is in the table
	*	
	*/
	public boolean contains(K key) {
		StdOut.println("CONTAINS FUNC");

		//error catch if null
		if (key == null) {
			throw new IllegalArgumentException("key is null!");
		}

		if (get(key) != null) {
			StdOut.println("TRUE");
			return (true);
		}
		else {
			StdOut.println("FALSE");
			return (false);
		}
	}

	
	/*
	*
	*	size
	*	returns the current size of the table
	*
	*/
	public int size() {
		StdOut.println("SIZE FUNC");
		return (size);
	}


	/*
	*
	*	reInsert	
	*	reInserts the keys entered in hashKey/hashVal when rehashing
	*
	*/
	public void reInsert(K[] tempKey, V[] tempVal) {
		
		StdOut.println("(hash " + a1 + " " + a2 + " " + r + ")");
		
		int index = 0;
		for (index = 0; index < (r / 2); index++) {
			if (tempKey[index] != null) {
				K key = tempKey[index];
				V val = tempVal[index];
				put (key, val);
			}
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

}




