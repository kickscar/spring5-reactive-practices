package me.kickscar.java.functional.ch3.ex11;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import me.kickscar.java.functional.ch3.ex10.Apple;


public class App {
	
	public static void ex01() {
		Predicate<me.kickscar.java.functional.ch3.ex10.Apple> p = (me.kickscar.java.functional.ch3.ex10.Apple a) -> a.getWeight() > 150;
		System.out.println(p.test(new me.kickscar.java.functional.ch3.ex10.Apple(200, "green")));
		
		// 인터페이스 Predicate<T>의 함수 디스크립터
		// T -> boolean 
	}

	public static void ex02() {
		Function<String, Integer> f = (String s) -> s.length();
		System.out.println(f.apply("Hello World"));

		// 인터페이스 Function<T, R>의 함수 디스크립터
		// T -> R 
	}

	public static void ex03() {
		Consumer<me.kickscar.java.functional.ch3.ex10.Apple> c = (me.kickscar.java.functional.ch3.ex10.Apple a) -> System.out.println(a);
		c.accept(new me.kickscar.java.functional.ch3.ex10.Apple(200, "green"));

		// 인터페이스 Consumer<T>의 함수 디스크립터
		// T -> void 
	}

	public static void ex04() {
		Supplier<Integer> s = () -> 2018;
		System.out.println(s.get());

		// 인터페이스 Supplier<T>의 함수 디스크립터
		// () -> T 
	}

	public static void ex05() {
		BiPredicate<me.kickscar.java.functional.ch3.ex10.Apple, me.kickscar.java.functional.ch3.ex10.Apple> bp = (me.kickscar.java.functional.ch3.ex10.Apple a1, me.kickscar.java.functional.ch3.ex10.Apple a2) -> a1.getWeight() == a2.getWeight();
		System.out.println(bp.test(new me.kickscar.java.functional.ch3.ex10.Apple(300, "red"), new me.kickscar.java.functional.ch3.ex10.Apple(200, "red")));
	
		// 인터페이스 BiPredicate<L, R>의 함수 디스크립터
		// (L, R) -> boolean 
	}
	
	public static void ex06() {
		BiFunction<me.kickscar.java.functional.ch3.ex10.Apple, me.kickscar.java.functional.ch3.ex10.Apple, Integer> bf = (me.kickscar.java.functional.ch3.ex10.Apple a1, me.kickscar.java.functional.ch3.ex10.Apple a2) -> a1.getWeight().compareTo(a2.getWeight());
		System.out.println(bf.apply(new me.kickscar.java.functional.ch3.ex10.Apple(300, "red"), new Apple(200, "red")));

		// 인터페이스 BiFunction<T, U, R>의 함수 디스크립터
		// (T, U) -> R 
	}

	public static void ex07() {
		BiConsumer<Integer, Integer> bc = (Integer x, Integer y) -> System.out.println(x + " + " + y + " = " + (x + y));
		bc.accept(100, 200);

		// 인터페이스 BiConsumer<T, U>의 함수 디스크립터
		// (T, U) -> void
	}
	
	
	public static void ex08() {
		Consumer<Integer> c = (Integer year) -> {
			System.out.print("올해는 ");
			System.out.print(year + "년 입니다.");
			System.out.print("\n");
		};
		
		c.accept(2018);		
	}
	
	public static void main(String[] args) {
		ex01();
		ex02();
		ex03();
		ex04();
		ex05();
		ex06();
		ex07();
		ex08();
	}
}
