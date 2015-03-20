package string_matching;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;


public class RabinKarp {

	public static int linesPlagiarized = 0;
	public static ArrayList<Integer> rktimes = new ArrayList<Integer>();
	
	public static void rabinKarpSearch(String inputLine, int n, String existingFile, int m, String filename){
		Date start = new Date(System.currentTimeMillis());
		int inputLine_hashcode= inputLine.hashCode();
		
		for(int i=0; i<m; i++){
			if(i+n+1<=m){
				String existingFile_substr=existingFile.substring(i,i+n);
				int existingFile_substr_hashcode=existingFile_substr.hashCode();
				if(inputLine_hashcode==existingFile_substr_hashcode){
					int count=0;
				    boolean flag=true;
				    
				    //check if every character matches
				     for(int j=i;j<i+n;j++){ 
				    	 if(existingFile.charAt(j)==inputLine.charAt(count)){ 
				    		 count++;      
				    	 }
				    	 else{
				    		 flag=false;
				    		 break; 
				    	 }      
				     }
				     if(flag){
				    	 //System.out.println("\nInput line: "+inputLine);
				    	 System.out.println("\nMatch found: "+existingFile_substr); //print the pattern every time a match found
				    	 System.out.println("From file: "+ filename);
				    	 linesPlagiarized++;					    	 
				     }
				}
			}
		}
		Date stop = new Date(System.currentTimeMillis());
    	Integer time = (int)(long)(stop.getTime() - start.getTime());
    	rktimes.add(time);
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
	
	public static void main(String[] args) throws IOException{
	
		String pattern[];
		String inputLine;
		BufferedReader inputFile = null;
		double inputFileLineCount = 0;
		
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
			inputFileLineCount++;
			int n = inputLine.length();
			pattern = inputLine.split("\\s+");
			if (validatePattern(pattern)) {
				for (int f = 0; f < listOfFiles.length; f++) {
					if (listOfFiles[f].isFile()) {
						String filename = listOfFiles[f].getName();
		      	      	BufferedReader existingFileReader = new BufferedReader(new FileReader(folderPath+"\\"+ listOfFiles[f].getName()));
		      	      	StringBuilder existingFileBuilder = new StringBuilder();
				        String existingFileLine = existingFileReader.readLine();

				        while (existingFileLine != null) {
				            existingFileBuilder.append(existingFileLine);
				            existingFileLine = existingFileReader.readLine();
				        }
				        String existingFile = existingFileBuilder.toString();
				        int m = existingFile.length();
				        rabinKarpSearch(inputLine, n, existingFile, m, filename);
				        existingFileReader.close();
					}
				}
			}
		}
		inputFile.close();
		System.out.println("\nLines plagiarized: "+linesPlagiarized);
		System.out.println("Total number of lines in input file: "+inputFileLineCount);
		System.out.println("Percentage of plagiarism: "+(((double)linesPlagiarized/inputFileLineCount)*100)+"%");
		long totalTime = 0;
		for (Integer i : rktimes){
			totalTime += i;
		}
		System.out.println("\nRunning time of Rabin Karp algorithm: "+totalTime+" milliseconds");
		
	}
		
		
}		
	
