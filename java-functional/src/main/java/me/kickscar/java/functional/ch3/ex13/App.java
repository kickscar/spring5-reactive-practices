package me.kickscar.java.functional.ch3.ex13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class App {
	
	public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> p) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if(p.test(apple)) {
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

		List<Apple> redApples = filterApples(inventory, (apple) -> "red".equals(apple.getColor()));
		List<Apple> heavyApples = filterApples(inventory, (apple) -> apple.getWeight() > 150);
		List<Apple> greenHeavyApples = filterApples(inventory, (apple) -> "green".equals(apple.getColor()) && apple.getWeight() > 150);
		
		System.out.println(redApples);
		System.out.println(heavyApples);
		System.out.println(greenHeavyApples);
	}
}