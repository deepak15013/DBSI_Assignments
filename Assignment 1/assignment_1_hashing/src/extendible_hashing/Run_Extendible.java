package extendible_hashing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class Run_Extendible {
	
	static PrintWriter write; 
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		ExtendibleHashing.pr = new PrintWriter("ExtendStorageUtil_10.csv","UTF-8");
		write = new PrintWriter("ExtendSearchCost_10.csv","UTF-8");
		ExtendibleHashing.print = new PrintWriter("ExtendSplitCost_10.csv","UTF-8");
		running(10);
		ExtendibleHashing.print.close();
		ExtendibleHashing.pr.close();
		write.close();
		ExtendibleHashing.pr = new PrintWriter("ExtendStorageUtil_40.csv","UTF-8");
		ExtendibleHashing.print = new PrintWriter("ExtendSplitCost_40.csv","UTF-8");
		write = new PrintWriter("ExtendSearchCost_40.csv","UTF-8");
		running(40);
		write.close();
		ExtendibleHashing.print.close();
		ExtendibleHashing.pr.close();
	}
	
	public static void running(int bucketSize) throws IOException{
		Random rand = new Random();
		File file = new File("ExtendData.txt");
		file.createNewFile();
		int noOfValues = 5000;
		int[] dataSet = new int[noOfValues];
		int[] searchSet = new int[50];
		FileWriter writer = new FileWriter(file);
		ExtendibleHashing extendibleHashing = new ExtendibleHashing(bucketSize);
		ExtendibleHashing.success = 0;
		ExtendibleHashing.unsuccess = 0;
		for(int run=0;run<12;run++) {
			for(int i=0;i<noOfValues;i++) {
				int getValue = 700000+rand.nextInt(100000);
				writer.write(getValue+"\n");
				dataSet[i] = getValue;
			}
			writer.flush();
			extendibleHashing.insert(dataSet);
			for(int i=0;i<50;i++) {
				ExtendibleHashing.bs = 0;
				ExtendibleHashing.numberOfBuckets = 0;
				int lineNumber = (run*5000)+rand.nextInt(5000);
				Scanner reader = new Scanner(file);
				 for(int j = 0 ;j < lineNumber;j++)
			     {
			    	 reader.nextLine();
			     }
				 searchSet[i] = Integer.valueOf(reader.nextLine());
				 reader.close();
			}
			extendibleHashing.search(searchSet);
			write.println((run+1)+","+(float)ExtendibleHashing.bs/50);
			System.out.println("Completed "+run);
		}
		for(int run=0;run<8;run++) {
			for(int i=0;i<noOfValues;i++) {
				int getValue = 1+rand.nextInt(700000);
				writer.write(getValue+"\n");
				dataSet[i] = getValue;		
			}
			writer.flush();
			extendibleHashing.insert(dataSet);
			for(int i=0;i<50;i++) {
				ExtendibleHashing.bs = 0;
				ExtendibleHashing.numberOfBuckets = 0;
				int lineNumber = (run*5000)+rand.nextInt(5000);
				Scanner reader = new Scanner(file);
				 for(int j = 0 ;j < lineNumber;j++)
			     {
			    	 reader.nextLine();
			     }
				 searchSet[i] = Integer.valueOf(reader.nextLine());
				 reader.close();
			}
			extendibleHashing.search(searchSet);
			write.println((run+1)+","+(float)ExtendibleHashing.bs/50);
			System.out.println("Completed "+run);
		}
		writer.close();
	}
}
