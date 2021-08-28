package example.part2.ex02;

import java.util.function.Consumer;

import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

/*
 * Reactor 예제
 * 
 * Mono
 * 
 * 
 */
public class Ex01App {

	public static void main(String[] args) {
		ex01();
		//ex02();
	}
	
	public static void ex01() {
		Mono<Integer> m = Mono.create(new Consumer<MonoSink<Integer>>() {
			@Override
			public void accept(MonoSink<Integer> emitter) {
				emitter.success(1);
			}
		}).log();
		
		m.subscribe(new Consumer<Integer>() {
			@Override
			public void accept(Integer i) {
				System.out.println(i);
			}
		});
	}

	public static void ex02() {
		Mono<Integer> m = Mono.create( emitter -> emitter.success(1) );
		m.subscribe(System.out::println);
	}
}