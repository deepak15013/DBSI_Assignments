package extendible_hashing;

import java.util.ArrayList;

public class ExtendibleBucket {
	int localDepth;
	int bucketSize;
	static int rs = 0;
	ArrayList<Integer> record;
	int ptr =0;
	
	public ExtendibleBucket(int size) {
		localDepth =0;
		bucketSize = size;
		record = new ArrayList<Integer>(bucketSize);
	}
	
	public boolean insertElement(int value) {
		if(ptr<bucketSize) {
			record.add(value);
			ptr++;
			return false;
		} else {
			record.add(value);
			return true;
		}
	}

	public void print() {
		// TODO Auto-generated method stub
		for(int x:record) {
			System.out.print(" "+x);
		}
		System.out.println();
	}
	
	public boolean searchElement(int element) {		
		for(int x: record) {
			rs++;
			if(x == element) {
				return true;
			}
		}
		return false;
	}
}
