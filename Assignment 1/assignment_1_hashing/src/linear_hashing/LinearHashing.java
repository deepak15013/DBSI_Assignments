package linear_hashing;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class LinearHashing {
	static Vector<Bucket> bucket = new Vector<Bucket>(); 
	static int splitPointer =0;
	static int m = 2;	//initial buckets
	static int i = 0;
	static int bucketSize;
	static boolean overflow;
	static int currentBucket = 2;
	static int splitCost=0;
	int ins=0;
	int hash;
	ArrayList<Integer> temp1 = new ArrayList<Integer>();	
	ArrayList<Integer> temp2 = new ArrayList<Integer>();	
	ArrayList<Integer> tempp = new ArrayList<Integer>();
	int[] values;
	static int nextoverflowaddr=100000;
	static int currentnoofrecords;
	static int currentnoofbuckets;
	static int success = 0;
	static int unsuccess = 0;
	public static PrintWriter writer;
	public static PrintWriter splitCostWrite;

	public LinearHashing(int noOfValues, int bucketSize) {
		values = new int[noOfValues];
		this.bucketSize = bucketSize;
		splitPointer = 0;
		for(int x=0;x<100000;x++) {
			Bucket bucketList = new Bucket();
			bucketList.createDataBucket(bucketSize);
			bucket.add(bucketList);	
			bucketList.overflowaddr=nextoverflowaddr;
			nextoverflowaddr++;
		}
		for(int i=0;i<100000;i++){
			Bucket overflowbucket = new Bucket();
			overflowbucket.createOverflowBucket();
			bucket.add(overflowbucket);
		}
	}
	
	public void insert(int[] values) {
		currentnoofbuckets++;
		this.values = values;
		for(int k: values) {
			splitCost = 0;
			ins++;
			currentnoofrecords++;
			hash = getHash(k,i);
			//insert
			overflow = bucket.elementAt(hash).insertElement(k,hash);
			splitCost++;
			if(overflow==true){
				currentBucket++;
				checkOverflow(hash);
			}
			float storageUtil = (float)(currentnoofrecords/(float)(currentBucket*bucketSize));
			writer.println(ins+","+storageUtil);
			splitCostWrite.println(ins+","+splitCost);
		}
	}
	
	public void checkOverflow(int hash) {
			//overflow occurs now split and re-hash
			currentnoofbuckets++;
			temp1 = (ArrayList<Integer>) bucket.elementAt(splitPointer).record.clone();
			if(bucket.elementAt(splitPointer).overflowaddr !=0){
				splitCost++;
				int overaddr=bucket.elementAt(splitPointer).overflowaddr;
				temp2 = (ArrayList<Integer>) bucket.elementAt(overaddr).overflowRecord.clone();
			}
			tempp.addAll(temp1);
			tempp.addAll(temp2);
			bucket.elementAt(splitPointer).record.clear();	//clear record
			bucket.elementAt(100000+splitPointer).overflowRecord.clear();	//clear record
			bucket.elementAt(splitPointer).ptr=0;
			for(int x: tempp) {	
				hash = getHash(x,i+1);
				overflow = bucket.elementAt(hash).insertElement(x,hash);
			}	
			tempp.clear();
			splitCost++;
			if(splitPointer<m-1) {
				splitPointer++;
			}
			else {
				splitPointer=0;
				m=m*2;
			}		
		}
	
	public int getHash(int k,int i) {
		int hash;
		hash = k % ((int)Math.pow(2, i)*m);
		return hash;
	}
	
	public void search(int[] searchSet) {
		for(int k: searchSet) {
			int hash = getHash(k,i);
			if(hash<splitPointer) {
				hash = getHash(k, i+1);
			}
			boolean searchVal = bucket.elementAt(hash).search(k);
			if(searchVal == true) {
				++success;
			} else {
				++unsuccess;
			}
		}
	}	
}
