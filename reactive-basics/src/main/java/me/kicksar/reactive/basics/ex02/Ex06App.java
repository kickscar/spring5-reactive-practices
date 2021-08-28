package me.kicksar.reactive.basics.ex02;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/*
 * < Example of Operator IV > 
 * 
 * 
 * Ex05의 sum operation -> reduce 연산으로 일반화
 * Operator(Reduce)의 Subscriber확장 (delegation)
 *            
 */

/*
 * Map & reduce operator
 *          
 * mapPub(op1):
 * reducePub(op2):
 * 
 * Publisher -> [data1]
 *                 |------> op1 ------> [data2 = data1 * data1]
 *           							   |--------> op2 --------> [data3 = data1 + "," + data2 + "," + data3 + ....datan] -> Subscriber
 */
public class Ex06App {
	public static void main(String[] args) throws Exception {
		Publisher<Integer> p = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
		
		Publisher<Integer> op1 = mapPub(p, s -> s * s);
		Publisher<StringBuilder> op2 = reducePub(op1, new StringBuilder(), (a, b) -> a.append(b + ",") );

		op2.subscribe(logSub());
	}


	public static <T, R> Publisher<R> reducePub(Publisher<T> publisher, R init, BiFunction<R, T, R> f){
		return subscriber -> publisher.subscribe(new DelegateSubscriber<T, R>(subscriber) {
			R sum = init;
					
			@Override
			public void onNext(T t) {
				sum = f.apply(sum, t);
			}

			@Override
			public void onComplete() {
				subscriber.onNext(sum);
				subscriber.onComplete();
			}
		});
	}
	
	public static <T, R> Publisher<R> mapPub(Publisher<T> publisher, Function<T, R> f){
		return subscriber -> publisher.subscribe(new DelegateSubscriber<T, R>(subscriber) {
			@Override
			public void onNext(T t) {
				subscriber.onNext(f.apply(t));
			}
		});
	}

	private static class DelegateSubscriber<T, R> implements Subscriber<T> {
		private final Subscriber subscriber;

		private DelegateSubscriber(Subscriber<? super R> subscriber) {
			this.subscriber = subscriber;
		}

		@Override
		public void onSubscribe(Subscription subscription) {
			subscriber.onSubscribe(subscription);
		}

		@Override
		public void onNext(T t) {
			subscriber.onNext(t);
		}

		@Override
		public void onError(Throwable t) {
			subscriber.onError(t);
		}

		@Override
		public void onComplete() {
			subscriber.onComplete();
		}
	}
	
	public static <T> Subscriber<T> logSub() {
		return new Subscriber<T>() {
			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscription");
				s.request(Long.MAX_VALUE);
			}

			@Override
			public void onNext(T t) {
				System.out.println("onNext: " + t);
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
	
	public static <T> Publisher<T> iterPub(Iterable<T> iter) {
		return subscriber -> subscriber.onSubscribe(new Subscription() {
			@Override
			public void request(long n) {
				try {
					iter.forEach(i -> subscriber.onNext(i));
					subscriber.onComplete();
				} catch (Exception e) {
					subscriber.onError(e);
				}
			}

			@Override
			public void cancel() {
			}
		});
	}
}