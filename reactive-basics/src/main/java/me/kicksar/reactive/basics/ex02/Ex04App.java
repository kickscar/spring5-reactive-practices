package me.kicksar.reactive.basics.ex02;

import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/*
 * < Example of Operator III > 
 * 
 * 
 * Refactoring:
 *         
 * Operator(Map)의 Subscriber확장 (delegation)
 * 
 * cf) Spring의 xxxAdapter
 *            
 */
public class Ex04App {
	public static void main(String[] args) throws Exception {
		Publisher<Integer> p = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
		
		Publisher<Integer> op1 = mapPub(p, s -> s * s);
		Publisher<Integer> op2 = mapPub(op1, s -> -s);
		
		op2.subscribe(logSub());
	}
	
	public static Publisher<Integer> mapPub(Publisher<Integer> publisher, Function<Integer, Integer> f){
		return new Publisher<Integer>() {
			@Override
			public void subscribe(Subscriber<? super Integer> subscriber) {
				publisher.subscribe(new DelegateSubscriber(subscriber) {
					@Override
					public void onNext(Integer i) {
						subscriber.onNext(f.apply(i));
					}
					
				});
			}
		};
	}

	private static class DelegateSubscriber implements Subscriber<Integer> {
		private final Subscriber<? super Integer> subscriber;

		private DelegateSubscriber(Subscriber<? super Integer> subscriber) {
			this.subscriber = subscriber;
		}

		@Override
		public void onSubscribe(Subscription subscription) {
			subscriber.onSubscribe(subscription);
		}

		@Override
		public void onNext(Integer i) {
			subscriber.onNext(i);
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