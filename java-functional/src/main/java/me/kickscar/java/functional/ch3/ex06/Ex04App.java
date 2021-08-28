package me.kickscar.java.functional.ch3.ex06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex04App {
	/* 값 파라미터화 */
	public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if (
					(flag && apple.getColor().equals(color))    ||
					(!flag && apple.getWeight() > weight)
							
			   ) { // 다양한 조건으로 검색한다.
				result.add(apple);
			}
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		List<Apple> inventory = Arrays.asList(new Apple[] { 
			new Apple(100, "red"),
			new Apple(300, "green"),
			new Apple(200, "red"),
			new Apple(500, "green")
		});

		List<Apple> redApples = filterApples(inventory, "red", 0, true);
		List<Apple> heavyApples = filterApples(inventory, "", 150, false);
		
		System.out.println(redApples);
		System.out.println(heavyApples);
	}
}
