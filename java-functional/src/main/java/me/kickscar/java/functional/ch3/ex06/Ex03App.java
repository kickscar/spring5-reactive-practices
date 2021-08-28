package me.kickscar.java.functional.ch3.ex06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex03App {
	public static List<Apple> filterApples(List<Apple> inventory, String color) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if (apple.getColor().equals(color)) { // 다양한 색의 사과를 선택한다.
				result.add(apple);
			}
		}
		
		return result;
	}

	public static List<Apple> filterApples(List<Apple> inventory, int weight) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if (apple.getWeight() > weight) { // 인수로 받은 무거운 기준이 되는 무게를 기준으로 판단
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

		List<Apple> redApples = filterApples(inventory, "red");
		List<Apple> heavyApples = filterApples(inventory, 150);
		
		System.out.println(redApples);
		System.out.println(heavyApples);
		
	}
}
