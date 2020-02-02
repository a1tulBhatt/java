package com.rackspace.word.count.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
     
public class PartWordCountUtil {    
        
	  public HashMap<String,Integer>  repeatedCount(File f) throws Exception {    
	        String line, word = "";    
	        int count = 0, maxCount = 0;    
	        ArrayList<String> words = new ArrayList<String>();    
	        HashMap<String,Integer> mostRepeated = new HashMap<String,Integer>();  
	        //Opens file in read mode    
	        FileReader file = new FileReader(f);    
	        BufferedReader br = new BufferedReader(file);    
	            
	        //Reads each line    
	        while((line = br.readLine()) != null) {    
	            String string[] = line.toLowerCase().split("\\s");    
	            //Adding all words generated in previous step into words    
	            for(String s : string){ 
	            	s = s.replaceAll("[^a-zA-Z0-9\\s+]", "");
					s = s.replaceAll("\\.", "");
					s = s.replaceAll("\\,", "");
					s = s.strip();
					s = s.toLowerCase();
	                words.add(s);
	                Integer countTemp = mostRepeated.get(s);
					if (countTemp == null) {
						mostRepeated.put(s, 1);
					} else {
						mostRepeated.replace(s, mostRepeated.get(s) + 1);
					}
				}
	            mostRepeated.remove("");    
	        }    
	            
	        //Determine the most repeated word in a file    
	        for(int i = 0; i < words.size(); i++){    
	            count = 1;    
	            //Count each word in the file and store it in variable count    
	            for(int j = i+1; j < words.size(); j++){    
	                if(words.get(i).equals(words.get(j))){    
	                    count++;    
	                }     
	            }    
	            //If maxCount is less than count then store value of count in maxCount     
	            //and corresponding word to variable word    
	            if(count > maxCount){ 
	                maxCount = count;    
	                word = words.get(i);  
	              //  mostRepeated.put(word, maxCount);
	            }    
	        }    
	            
	       // System.out.println("Most repeated word: " + word);    
	      //  System.out.println("Most repeated word: " + mostRepeated);    
	        br.close();
	        System.out.println("Most repeated word->"+word+" count->"+maxCount);
			return mostRepeated;    
	    }    
} 