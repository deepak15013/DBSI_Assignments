package extendible_hashing;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class ExtendibleHashing {
	
	public static PrintWriter print;
	static int success = 0;
	static int unsuccess = 0;
	static int numberOfBuckets = 0;
	static int bs = 0;
	int[] values;
	int globalDepth;
	int currentRecord = 0;
	int currentBucket = 0;
	int bucketAccess = 0;
	int bucketSize;
	String binaryValue = null;
	String check = null;
	Random rand = new Random();
	Vector<ExtendibleBucket> directory = new Vector<ExtendibleBucket>();
	int directorySize = 1;
	int index = 0;
	int val=0;
	boolean overflow = false;
	boolean firstInsert = false;
	public static PrintWriter pr;
	ArrayList<Integer> temp = new ArrayList<Integer>();
	
	public ExtendibleHashing(int bucketSize) throws FileNotFoundException, UnsupportedEncodingException {		
		globalDepth= 0;
		this.bucketSize = bucketSize;
		ExtendibleBucket bucket = new ExtendibleBucket(bucketSize);
		numberOfBuckets++;
		directory.add(bucket);
		currentBucket++;
	}
	
	public void insert(int[] dataSet) throws FileNotFoundException, UnsupportedEncodingException {
		values = dataSet;
		for(int x: values) {
			bucketAccess = 0;
			binaryValue = String.format("%20s", Integer.toBinaryString(x)).replace(' ', '0');
			if(firstInsert == false) {
				overflow = directory.elementAt(index).insertElement(x);
			} else {
				check = binaryValue.substring(0, globalDepth);
				val = Integer.parseInt(check,2);
				overflow = directory.elementAt(val).insertElement(x);
				index = val;
			}
			checkOverflow(overflow);
			currentRecord++;
			float storageUtilization = (float)currentRecord/(currentBucket*bucketSize);
			pr.println(currentRecord+","+storageUtilization);
			print.println(currentRecord+","+bucketAccess);
		}
	}
	
	public void checkOverflow(boolean overflow) {
		if(overflow == true) {			
			if(globalDepth == directory.elementAt(index).localDepth) {
				globalDepth++;
				int ld = ++directory.elementAt(index).localDepth;
				Vector<ExtendibleBucket> newDirectory = new Vector<ExtendibleBucket>(directorySize*2);
				bucketAccess++;
				for(int m=0;m<directorySize;m++) {
					newDirectory.insertElementAt(directory.elementAt(m), m*2);
					newDirectory.insertElementAt(directory.elementAt(m), (m*2)+1);
				}
				directorySize = directorySize*2;
				directory.clear();
				directory = (Vector<ExtendibleBucket>) newDirectory.clone();
				//create Bucket
				ExtendibleBucket bucket = new ExtendibleBucket(bucketSize);
				index = index*2;
				directory.setElementAt(bucket, index+1);
				currentBucket++;
				numberOfBuckets++;
				directory.elementAt(index+1).localDepth = ld;
				temp = (ArrayList<Integer>) directory.elementAt(index).record.clone();
				bucketAccess++;
				directory.elementAt(index).record.clear();
				directory.elementAt(index).ptr = 0;
				for(int x: temp) {
					binaryValue = String.format("%20s", Integer.toBinaryString(x)).replace(' ', '0');
					check = binaryValue.substring(0, globalDepth);
					val = Integer.parseInt(check,2);
					directory.elementAt(val).insertElement(x);
				}
				firstInsert = true;
			} else if(directory.elementAt(index).localDepth < globalDepth){
				int ld = directory.elementAt(index).localDepth;
				int diff = globalDepth-ld;
				int number = (int)((Math.pow(2, diff))/2);
				int start = index;
					while(start >=0 && directory.elementAt(start) == directory.elementAt(index) ) {
						--start;
						bucketAccess++;
					}
					++start;
					start = start + number;
				ExtendibleBucket bucket = new ExtendibleBucket(bucketSize);
				++ld;
				for(int i =0 ;i<number;i++) {
					directory.setElementAt(bucket, start);
					numberOfBuckets++;
					currentBucket++;
					directory.elementAt(start).localDepth = ld;
					++start;	
				}
				directory.elementAt(index).localDepth=ld;
				temp = (ArrayList<Integer>) directory.elementAt(index).record.clone();
				directory.elementAt(index).record.clear();
				directory.elementAt(index).ptr = 0;
				bucketAccess++;
				for(int x: temp) {
					binaryValue = String.format("%20s", Integer.toBinaryString(x)).replace(' ', '0');
					check = binaryValue.substring(0, globalDepth);
					val = Integer.parseInt(check,2);
					directory.elementAt(val).insertElement(x);
					
				}	
			} else {
				System.out.println("Somethings Wrong");
				System.exit(1);
			}
		}
	}
	
	public void print() {
		int bucketNumber=0;
		System.out.println("directory size: "+directory.size());
		for(ExtendibleBucket x: directory) {
			System.out.print("Bucket "+bucketNumber+" : ");
			bucketNumber++;
			x.print();
		}
	}
	
	public void search(int[] searchSet) {
		for(int i: searchSet) {
			binaryValue = String.format("%20s", Integer.toBinaryString(i)).replace(' ', '0');
			check = binaryValue.substring(0, globalDepth);
			val = Integer.parseInt(check,2);
			if(val > 1024) {
				bs += 2;
			}
			else {
				bs += 1;
			}
			boolean check = directory.elementAt(val).searchElement(i);
			if(check == true) {
				++success;
			} else {
				++unsuccess;
			}
		}	
	}
}
