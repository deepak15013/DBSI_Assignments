package linear_hashing;
import java.util.ArrayList;
public class Bucket {
	static int bucketSize;
	ArrayList<Integer> record;
	ArrayList<Integer> overflowRecord;
	int ptr=0;
	int overflowaddr;	//overaddr for bucket if bucket overflow occurs
	
	public void createDataBucket(int size){
		bucketSize = size;
		record = new ArrayList<Integer>(bucketSize);
	}
	
	public void createOverflowBucket(){
		overflowRecord=new ArrayList<Integer>();
	}

	public boolean insertElement(int k,int hash) {
		if(ptr < bucketSize) {
			record.add(ptr,k);		//no overflow
			ptr++;
			return false;
		}
		else {
			LinearHashing.bucket.get(overflowaddr).overflowRecord.add(k); 	//overflow
			return true;
		}
	}
	
	public void print() {
		for(int x=0;x<record.size();x++) {
			System.out.print(" "+record.get(x));
		}
		System.out.println();
	}
	static int bs = 0;
	public boolean search(int element) {
		for(int i=0;i<record.size();i++) {
			if(element == record.get(i)) {
				if(record.size()>bucketSize) {
					bs++;
				}
				bs+=2;
				return true;
			}
		}
		return false;
	}
}