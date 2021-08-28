package me.kicksar.reactive.basics.test;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestApp {

	public static void main(String[] args) {
		Iterable<Integer> iter = Stream.iterate(1, a -> a+1).limit(10).collect(Collectors.toList());
		iter.forEach(new Consumer<Integer>() {

			@Override
			public void accept(Integer t) {
				System.out.println( t );
			}
			
		});

	}

}
