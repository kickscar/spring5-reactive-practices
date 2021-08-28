package me.kickscar.java.functional.ch3.ex09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 동작 파라미터화 기법 중 람다 표현식을 사용한 기법 */
public class App {
	
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<Apple>();
		
		for (Apple apple : inventory) {
			if(p.test(apple)) { // 프레디케이트 객체로 사과 검사 조건을 캡슐화 했다.
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

		List<Apple> redApples = filterApples(inventory, (Apple apple) -> "red".equals(apple.getColor()));
		List<Apple> heavyApples = filterApples(inventory, (Apple apple) -> apple.getWeight() > 150);
		List<Apple> greenHeavyApples = filterApples(inventory, (Apple apple) -> "green".equals(apple.getColor()) && apple.getWeight() > 150);
		
		System.out.println(redApples);
		System.out.println(heavyApples);
		System.out.println(greenHeavyApples);
	}
}