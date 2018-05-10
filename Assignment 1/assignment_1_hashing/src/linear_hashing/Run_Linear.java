package linear_hashing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

import extendible_hashing.ExtendibleHashing;

public class Run_Linear {
	
	static PrintWriter searchCostWrite;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub	
		
		LinearHashing.writer = new PrintWriter("LinearStorageUtilization_10.csv","UTF-8");
		searchCostWrite = new PrintWriter("LinearSearchCost_10.csv","UTF-8");
		LinearHashing.splitCostWrite = new PrintWriter("LinearSplitCost_10.csv","UTF-8");
		running(10);
		LinearHashing.splitCostWrite.close();
		searchCostWrite.close();
		LinearHashing.writer.close();
		
		LinearHashing.writer = new PrintWriter("LinearStorageUtilization_40.csv","UTF-8");
		searchCostWrite = new PrintWriter("LinearSearchCost_40.csv","UTF-8");
		LinearHashing.splitCostWrite = new PrintWriter("LinearSplitCost_40.csv","UTF-8");
		running(40);
		LinearHashing.splitCostWrite.close();
		searchCostWrite.close();
		LinearHashing.writer.close();
	}
	
	public static void running (int bucketSize)  throws IOException {
		Random rand = new Random();
		int noOfValues = 5000;
		int[] values = new int[noOfValues];
		
		LinearHashing.splitPointer = 0;
		LinearHashing.m = 2;
		LinearHashing.currentnoofbuckets = 0;
		LinearHashing.currentnoofrecords = 2;
		LinearHashing.success = 0;
		LinearHashing.unsuccess = 0;
		LinearHashing.currentBucket = 2;
		LinearHashing.splitCost = 0;
		
		File file = new File("LinearData.txt");
		file.createNewFile();
		FileWriter writer = new FileWriter(file);
		LinearHashing linearHashing = new LinearHashing(noOfValues, bucketSize);
		int[] searchSet = new int[50];
		for(int i=0;i<12;i++) {
			for(int x=0;x<noOfValues;x++) {
				int getValue = 700000+rand.nextInt(100000);
				values[x] = getValue;
				writer.write(getValue+"\n");
			}
			writer.flush();
			linearHashing.insert(values);
			Bucket.bs = 0;
			for(int j=0;j<50;j++) {
				int lineNumber = (i*5000)+rand.nextInt(5000);
				Scanner reader = new Scanner(file);
				 for(int k = 0 ;k < lineNumber;k++)
			     {
			    	 reader.nextLine();
			     }
				 searchSet[j] = Integer.valueOf(reader.nextLine());
				 reader.close();
			}
			linearHashing.search(searchSet);
			searchCostWrite.println(i+1+","+(float)Bucket.bs/50);
			System.out.println("Completed "+i);
		}
		for(int i=0;i<8;i++) {
			for(int x=0;x<noOfValues;x++) {
				int getValue = 1+rand.nextInt(700000);
				values[x] = getValue;
				writer.write(getValue+"\n");
			}
			writer.flush();
			linearHashing.insert(values);
			Bucket.bs = 0;
			for(int j=0;j<50;j++) {
				int lineNumber = (i*5000)+rand.nextInt(5000);
				Scanner reader = new Scanner(file);
				 for(int k = 0 ;k < lineNumber;k++)
			     {
			    	 reader.nextLine();
			     }
				 searchSet[j] = Integer.valueOf(reader.nextLine());
				 reader.close();
			}
			linearHashing.search(searchSet);
			searchCostWrite.println(i+1+","+(float)Bucket.bs/50);
			System.out.println("Completed "+i);
		}		
		writer.close();
	}
}