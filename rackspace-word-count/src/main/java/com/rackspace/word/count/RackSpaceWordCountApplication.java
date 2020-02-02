package com.rackspace.word.count;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.rackspace.word.count.service.FileService;
import com.rackspace.word.count.util.MapUtil;
import com.rackspace.word.count.util.PartWordCountUtil;
import com.rackspace.word.count.util.WordCountUtil;

@SpringBootApplication
public class RackSpaceWordCountApplication {

	public static void main(String[] args) throws Exception {
		ApplicationContext applicationContext = SpringApplication.run(RackSpaceWordCountApplication.class, args);
		
		MapUtil mapUtil =  new MapUtil();
		FileService fileService = applicationContext.getBean(FileService.class);
		fileService = applicationContext.getBean(FileService.class);;
		
		//Read file to be analyzed from resources
		File masterFile = fileService.readFile("test_file_rackspace.txt");
		
		//return the count of files in which master file is splitted
		System.out.println("Started bifurcating file into chunks");
		Long fileCount = fileService.splitFile(masterFile);
		System.out.println("Ended bifurcating file into "+ fileCount +" chunks.");
		
		int totalCount=0;
		
		ArrayList<HashMap<String, Integer>> listOfAllWords = new ArrayList<HashMap<String, Integer>>();
		HashMap<String, Integer> finalMap = new HashMap<String, Integer>();
		for(int i = 1 ; i<=fileCount; i++) {
			
			File fileTemp = new File("../rackspace-word-count/target/classes/test_file_rackspace.txt" + i);
			
			//String contents = new String(Files.readAllBytes(Paths.get("../RackSpace/target/classes/sample_file.txt"+i)));

			System.out.println("Chuck "+i+"  analysis");
			WordCountUtil calc = new WordCountUtil(fileTemp);
			ExecutorService pool = Executors.newFixedThreadPool(10);
			Future<Integer> wordCount=pool.submit(calc);

			totalCount = totalCount + wordCount.get();
			listOfAllWords.add(new PartWordCountUtil().repeatedCount(fileTemp));
			
		}
		List<Entry<String, Integer>> flattenList = listOfAllWords.stream().flatMap(x -> x.entrySet().stream())
				.collect(Collectors.toList());

		for (int i = 0; i < flattenList.size(); i++) {
			String key = flattenList.get(i).getKey();
			if(finalMap.get(key) == null) {
				finalMap.put(key, flattenList.get(i).getValue());
			}
			else {
				finalMap.replace(key,finalMap.get(key) + flattenList.get(i).getValue() );
			}
		}
		System.out.println("-----------------------------------");
		System.out.println("------------Summary----------------");
		System.out.println("------------Total word count------->"+totalCount);
		System.out.println("Top 5 words and count");
		Map<String, Integer> hm1 = mapUtil.sortedCountWordMap(finalMap);
		for (Map.Entry<String, Integer> en : hm1.entrySet()) {
			System.out.println("word = " + en.getKey() + ", count = " + en.getValue());
		}		
	}

}
