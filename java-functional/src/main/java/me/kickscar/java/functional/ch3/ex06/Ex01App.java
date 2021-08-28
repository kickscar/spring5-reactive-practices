package me.kickscar.java.functional.ch3.ex06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex01App {

	public static List<Apple> filterGreenApples(List<Apple> inventory) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if ("green".equals(apple.getColor())) { // 녹색 사과만 선택한다.
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

		List<Apple> greenApples = filterGreenApples(inventory);
		System.out.println(greenApples);
	}
}