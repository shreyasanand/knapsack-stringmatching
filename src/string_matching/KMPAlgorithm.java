package string_matching;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;


public class KMPAlgorithm {

	public static double threshold = 0.60;
	public static double matches = 0;
	public static double totallines = 0;
	public static ArrayList<Integer> kmptimes = new ArrayList<Integer>();
	
	
	// Calculates the longest prefix/suffix of each sub-pattern
	public static int[] computeLPSArray(String[] pattern, int length)
	{
		int[] lps = new int[length + 1];
	    int i = 0;  
	    int j = -1; // length of the previous longest prefix suffix
	 
	    lps[0] = j;
	    i = 1;
	 
	    // the loop calculates lps[i] for i = 1 to length-1
	    while(i < length)
	    {
	       // if there is mismatch consider the next longest prefix
	       while(j > -1 && !pattern[i].equalsIgnoreCase(pattern[j])){
	    	   j = lps[j];
	       }
	       i++;
	       j++;
	       lps[i] = j;
	    }
	    return lps;
	}

	
	public static boolean validatePattern(String[] pattern){
		boolean flag = true;
		for (int i = 0; i < pattern.length; i++) {
			if (pattern[i].equalsIgnoreCase("")) {
				flag = false;
			} else {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	
	public static void KMPSearch(String[] pattern, String[] text, String filename) throws IOException{
		int m = pattern.length;
		int[] lps = new int[m+1];
		int n = text.length;
		int j = 0; // index for pattern
		lps = computeLPSArray(pattern, m);
		int k = 0; // index for text
		StringBuffer matched = new StringBuffer();
		while(k < n){
			while (j >= 0 && !text[k].contains(pattern[j])) {
				j = lps[j];
				matched = new StringBuffer();
			}
			if ( !(j<0)&& (j != pattern.length)) {
				matched.append(" "+pattern[j]); //store the matched string in buffer
			}
			k++;
			j++;
								
			if (j == pattern.length) {
				System.out.println("Match found in file: "+ filename);
				matches++;
				System.out.println("Matched line: "+matched.toString());
				j = lps[j];
			}
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		
		//Date start = new Date(System.currentTimeMillis()); // start the timer
		
		String text[], pattern[];
		String inputLine, textLine;
		BufferedReader inputFile = null;
		BufferedReader textFile = null;
		
		// format : C:\\file\\files
		System.out.println("Enter the path of the directory where the corpus of existing files is stored : ");
		Scanner sc1 = new Scanner(System.in);
		String folderPath = sc1.next();
		
		// format : C:\\file\\inputFile.txt
		System.out.println("Enter the path of the potentially plagiarized document : ");
		Scanner sc2 = new Scanner(System.in);
		String inputFilePath = sc2.next();
		
		File folder = new File(folderPath);  // Set the location for the corpus of existing documents
		File[] listOfFiles = folder.listFiles();
		
		inputFile = new BufferedReader(new FileReader(inputFilePath)); // Read the potentially plagiarized document
		while ((inputLine = inputFile.readLine()) != null) {
			pattern = inputLine.split("\\s+");
			totallines++;
			
			if (validatePattern(pattern)) {
				
				for (int f = 0; f < listOfFiles.length; f++) {
					if (listOfFiles[f].isFile()) {
						textFile = new BufferedReader(new FileReader(folderPath+"\\" + listOfFiles[f].getName()));

						while ((textLine = textFile.readLine()) != null) {
							text = textLine.split("\\s+");
							String filename = listOfFiles[f].getName();
							Date start = new Date(System.currentTimeMillis());
							if(validatePattern(text)){
							KMPSearch(pattern, text, filename);
							}
							Date stop = new Date(System.currentTimeMillis());
							Integer time = (int)(long)(stop.getTime() - start.getTime());
							kmptimes.add(time);
						}
					}
				}
		    }
	    }
		inputFile.close();
		//Date stop = new Date(System.currentTimeMillis());
		//long time = (stop.getTime() - start.getTime());
		long totalTime = 0;
		for (Integer i : kmptimes){
			totalTime += i;
		}
		
		System.out.println("Total matches found: "+matches);
		System.out.println("Total number of lines in the input file: "+totallines);
		System.out.println("Percentage of Plagiarism: "+(((float)matches/(float)totallines)*100)+"%");
		System.out.println("\nRunning time of KMP algorithm: "+totalTime+" milliseconds");
	}
}
