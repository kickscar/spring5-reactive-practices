package example.part2.ex02;

import java.util.function.Consumer;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/*
 * Reactor 예제
 * 
 * Flux
 * 
 * 
 */
public class Ex02App {

	public static void main(String[] args) {
		//ex01();
		//ex02();
		ex03();
	}
	
	public static void ex01() {
		Flux<Integer> f = Flux.create(new Consumer<FluxSink<Integer>>() {
			@Override
			public void accept(FluxSink<Integer> emitter) {
				emitter.next(1);
				emitter.next(2);
				emitter.next(3);
				emitter.next(4);
				emitter.complete();
			}
		})
		.map(new Function<Integer, Integer>(){
			@Override
			public Integer apply(Integer i) {
				return i * i;
			}
		}).log();
		
		f.subscribe(new Consumer<Integer>(){
			@Override
			public void accept(Integer i) {
				System.out.println(i);
			}
		});
	}
	
	public static void ex02() {
		Flux<Integer> f = Flux.<Integer>create(emitter -> {
			emitter.next(1);
			emitter.next(2);
			emitter.next(3);
			emitter.next(4);
			emitter.complete();
		})
		.map(i -> i*i);
		
		f.subscribe(i -> System.out.println(i));
	}

	
	public static void ex03() {
		Flux<Integer> f = Flux.<Integer>create(emitter -> {
			emitter.next(1);
			emitter.next(2);
			emitter.next(3);
			emitter.next(4);
			emitter.complete();
		})
		.map(i -> i*i)
		.log();
	
		f.subscribe(System.out::println);
	}
	
	
	
}