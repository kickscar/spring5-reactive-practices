package me.kickscar.java.functional.ch3.ex05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
	
	public static List<List<Integer>> subsets(List<Integer> list) {
		if( list.isEmpty() ) {
			List<List<Integer>> result = new ArrayList<List<Integer>>();
			result.add(Collections.emptyList());
			
			return result;
		}
		
		Integer firstItem = list.get(0);
		List<Integer> subList = list.subList(1, list.size());
		
		List<List<Integer>> subLists1 = subsets(subList);
		List<List<Integer>> subLists2 = insertAll(firstItem, subLists1);
		
		return concat(subLists1, subLists2);
	}
	
	public static List<List<Integer>> insertAll(Integer item, List<List<Integer>> lists){
		List<List<Integer>> result = new ArrayList<List<Integer>>();
		
		for(List<Integer> list : lists) { // 리스트를 복사한 다음 리스트에 요소를 추가한다. Integer가 불변이기 때문에 요소까지 복사하지 않는다.
			List<Integer> listCopy = new ArrayList<Integer>();
			listCopy.add(item);
			listCopy.addAll(list);
			
			result.add(listCopy);
		}
		
		return result;
	}
	
	public static List<List<Integer>> concat(List<List<Integer>> list1, List<List<Integer>> list2){
		// 다음 코드는 부작용을 포함한 좋지 못한 코드로 이상동작을 한다.
		// list1.addAll(list2);
		// return list1;
		
		List<List<Integer>> result = new ArrayList<List<Integer>>(list1);
		result.addAll(list2);
		
		return result;
	}
	
	public static void main(String[] args) {
		List<List<Integer>> list = subsets(Arrays.asList(new Integer[] { 1, 4, 9}));
		System.out.println(list);
	}
}