package me.kickscar.java.functional.ch3.ex06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ex02App {
	public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if (apple.getColor().equals(color)) { // 다양한 색의 사과를 선택한다.
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

		List<Apple> greenApples = filterApplesByColor(inventory, "green");
		System.out.println(greenApples);

		List<Apple> redApples = filterApplesByColor(inventory, "red");
		System.out.println(redApples);
		
	}
}
