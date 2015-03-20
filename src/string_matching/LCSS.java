package string_matching;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

public class LCSS{

	public static int lines = 0;
	public static int nextline = 0;
	public static int parasPlagiarised = 0;
	public static int paraCount = 0;
	public static double threshold = 0.60;
	public static ArrayList<Integer> lcstimes = new ArrayList<Integer>();

public static String[] lcs(String[] a, String[] b) {
	
	Date start = new Date(System.currentTimeMillis());
    int[][] lengths = new int[a.length+1][b.length+1];
    String returnMatched[] = null;
    
    // row 0 and column 0 are initialized to 0 already
    for (int i = 0; i < a.length; i++)
        for (int j = 0; j < b.length; j++)
            if (a[i].equalsIgnoreCase(b[j]))
                lengths[i+1][j+1] = lengths[i][j] + 1;
            else
                lengths[i+1][j+1] =
                    Math.max(lengths[i+1][j], lengths[i][j+1]);
 
    // read the substring out from the matrix
    StringBuffer sb = new StringBuffer();
    String reverse[];
    for (int x = a.length, y = b.length;
         x != 0 && y != 0; ) {
        if (lengths[x][y] == lengths[x-1][y])
            x--;
        else if (lengths[x][y] == lengths[x][y-1])
            y--;
        else {
            assert a[x-1].equalsIgnoreCase(b[y-1]);
            sb.append(a[x-1]+" ");
            x--;
            y--;
        }
    }
    
    reverse = sb.toString().split(" ");
    List<String> matched = Arrays.asList(reverse);
    Collections.reverse(matched);
  
    returnMatched = matched.toArray(new String[matched.size()]);
    Date stop = new Date(System.currentTimeMillis());
	Integer time = (int)(long)(stop.getTime() - start.getTime());
	lcstimes.add(time);
    return returnMatched;
}

public static boolean checkLineBreak(String[] pattern){
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

public static int countLines(File filename) throws IOException {
    LineNumberReader reader  = new LineNumberReader(new FileReader(filename));
    int cnt = 0;
    String lineRead = "";
    while ((lineRead = reader.readLine()) != null) {}
    	cnt = reader.getLineNumber(); 
    	reader.close();
    	return cnt;
	}

public static void validateParagraph(File inputFile, String folderPath) throws IOException{
	
	File allFiles = new File(folderPath);  // Set the location for the corpus of existing documents
	File[] listOfFiles = allFiles.listFiles();
	String lineRead = "";
	String pattern[], text[], matched[];
	BufferedReader textFile = null;
	String textLine;
	int linesPlagiarized = 0;
	int paraLineCount = 0;
	
	
	for(int lineCount = nextline ; lineCount<lines; lineCount++){
		lineRead = FileUtils.readLines(inputFile).get(lineCount);
		pattern = lineRead.split("\\s+");

		if(!checkLineBreak(pattern)){
			paraCount++;
			System.out.println("\nChecking paragraph : "+paraCount);
			System.out.println("Total matches found: "+linesPlagiarized);
			System.out.println("Total number of lines in the para: "+paraLineCount);
			System.out.println("Percentage of Plagiarism: "+(((float)linesPlagiarized/(float)paraLineCount)*100)+"%");
			
			paraLineCount = 0; // Reset the line count in a paragraph once a line break is encountered
			nextline = lineCount+1;
			validateParagraph(inputFile, folderPath);
			break;
		}
		else{
			paraLineCount++;
			for (int f = 0; f < listOfFiles.length; f++) {
				if (listOfFiles[f].isFile()) {
					textFile = new BufferedReader(new FileReader(folderPath+"\\" + listOfFiles[f].getName()));

					while ((textLine = textFile.readLine()) != null) {
						text = textLine.split("\\s+");
						if(checkLineBreak(text)){
						matched = lcs(pattern, text);
						if(!matched[0].isEmpty()){
							if(Arrays.equals(pattern, matched)){
								linesPlagiarized++;
							}
						}
						}
					}
				}
			}	
		}
	}
}


public static void main(String[] args) throws IOException{
	
	// format : C:\\file\\files
	System.out.println("Enter the path of the directory where the corpus of existing files is stored : ");
	Scanner sc1 = new Scanner(System.in);
	String folderPath = sc1.next();
	
	// format : C:\\file\\inputFile.txt
	System.out.println("Enter the path of the potentially plagiarized document : ");
	Scanner sc2 = new Scanner(System.in);
	String inputFilePath = sc2.next();
	
	File inputFile = new File(inputFilePath);  // Set the location for the corpus of existing documents
	lines = countLines(inputFile);
	validateParagraph(inputFile, folderPath);
	long totalTime = 0;
	for (Integer i : lcstimes){
		totalTime += i;
	}
	System.out.println("\nRunning time of LCSS algorithm: "+totalTime+" milliseconds");
	}
}
