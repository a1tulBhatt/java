package com.rackspace.word.count.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class MapUtil {

	public HashMap<String, Integer> sortedCountWordMap(HashMap<String, Integer> hm) {
		// Create a list from elements of HashMap
		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());

		// Sort the list
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// put data from sorted list to hashmap
		HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> aa : list) {
			temp.put(aa.getKey(), aa.getValue());
		}
		HashMap<String,Integer> highestInFile=new HashMap<>();
		List<Entry<String, Integer>> limit = temp.entrySet().stream().limit(5).collect(Collectors.toList());
		for (Map.Entry<String, Integer> aa : limit) {
			highestInFile.put(aa.getKey(), aa.getValue());
		}
		return highestInFile;
	}
}
