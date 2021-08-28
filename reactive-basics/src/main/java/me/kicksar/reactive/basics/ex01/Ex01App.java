package me.kicksar.reactive.basics.ex01;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Ex01App {

	public static void main(String[] args) {
		 ex01();
		 ex02();
		 ex03();
	}

	public static void ex01() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		for (Iterator<Integer> it = list.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}

	public static void ex02() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		for (Integer i : list) {
			System.out.println(i);
		}
	}

	public static void ex03() {
		Iterable<Integer> iter = () -> new Iterator<Integer>() {
			private final int MAX = 10;
			private int i = 0;
			
			@Override
			public boolean hasNext() {
				return i < MAX;
			}

			@Override
			public Integer next() {
				return ++i;
			}
		};

		for (Integer i : iter) {
			System.out.println(i);
		}
	}
}