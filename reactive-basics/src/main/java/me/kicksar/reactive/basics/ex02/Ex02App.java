package me.kicksar.reactive.basics.ex02;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/*
 * < Example of Operator I >
 *  
 * 1. Refactoring for Example of Operator
 * 
 * 2. Operator Concept:
 * 
 * Publisher -> [data1]
 *                 |------> op1 ------> [data2]
 *           							   |--------> op2 --------> [data3] -> Subscriber
 *                       
 */
public class Ex02App {
	public static void main(String[] args) throws Exception {
		Publisher<Integer> p = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));

		p.subscribe(logSub());
	}
	
	public static Subscriber<Integer> logSub() {
		return new Subscriber<Integer>() {
			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscription");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(Integer i) {
				System.out.println("onNext: " + i);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("onError: " + t);
			}

			@Override
			public void onComplete() {
				System.out.println("onComplete");
			}
		};
	}
	
	public static Publisher<Integer> iterPub(Iterable<Integer> iter) {
		return new Publisher<Integer>() {
			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				sub.onSubscribe(new Subscription() {
					@Override
					public void request(long n) {
						try {
							iter.forEach(i -> sub.onNext(i));
							sub.onComplete();
						} catch (Exception e) {
							sub.onError(e);
						}
					}

					@Override
					public void cancel() {
					}
				});
			}
		};
	}
}