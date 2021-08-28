package me.kickscar.java.functional.ch3.ex07;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 동작 파라미터화 기법 중 클래스 구현을 사용한 기법 */
public class App {
	public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate p /* 인터페이스 인수를 통해 한개의 파라미터로 다양한 동작을 수행할 수 있다.*/ ) {
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

		List<Apple> redApples = filterApples(inventory, new RedApplePredicate());
		List<Apple> heavyApples = filterApples(inventory, new HeavyApplePredicate());
		List<Apple> greenHeavyApples = filterApples(inventory, new GreenHeavyApplePredicate());
		
		System.out.println(redApples);
		System.out.println(heavyApples);
		System.out.println(greenHeavyApples);
	}
}
