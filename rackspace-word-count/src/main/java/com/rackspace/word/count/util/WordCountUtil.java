package com.rackspace.word.count.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class WordCountUtil implements Callable<Integer>{

	File file;
	int countWord = 0;

	public WordCountUtil(File file1) {
		this.file = file1;
	}

	@Override
	public Integer call() throws Exception {
		
		return calculateCount(file);
	}
	public int calculateCount(File file) {
		try (FileInputStream fileStream = new FileInputStream(file);
				InputStreamReader input = new InputStreamReader(fileStream);
				BufferedReader reader = new BufferedReader(input);) {
			String line;
			Map<String, Integer> countByWords = new HashMap<String, Integer>();
		
			String maxValue = null;
			
			while ((line = reader.readLine()) != null) {

				String[] wordList = line.split("\\s+");
				countWord += wordList.length;
				for (String word : wordList) {

					//remove special characters
					word = word.replaceAll("[^a-zA-Z0-9\\s+]", "");
					word = word.replaceAll("\\.", "");
					word = word.replaceAll("\\,", "");
					word = word.strip();
					word = word.toLowerCase();
					Integer count = countByWords.get(word);
					if (count == null) {
						countByWords.put(word, 1);
					} else {
						countByWords.replace(word, countByWords.get(word) + 1);
					}
				}
				countByWords.remove("");
			}
			System.out.println("words in each file "+countByWords);
			System.out.println("Total word count = " + countWord);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return countWord;
}


}		
	