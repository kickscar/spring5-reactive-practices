package me.kicksar.reactive.basics.ex02;

import java.util.Arrays;
import java.util.Iterator;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* 
 * < Implementation of Reactive Streams API's Interface > 
 * 
 * 
 * Reactive Streams -> java.util.concurrency.flow(JDK9)
 * by Neflix, Pivotal, 
 * 
 * - document: http://www.reactive-streams.org/
 * - Specification: https://github.com/reactive-streams/reactive-streams-jvm/blob/v1.0.2/README.md#specification
 * - Java API : http://www.reactive-streams.org/reactive-streams-1.0.2-javadoc/org/reactivestreams/package-summary.html 
 * 
 * cf)
 * ReactiveX: http://reactivex.io -> RxJava, RxJs, RxScala, RxSwift 
 *
 */

public class Ex01App {

	/*
	 * Protocol:
	 * 
	 * onSubscribe onNext* (onError | onComplete) ?
	 * 
	 * 
	 * Flow:
	 * 
	 * Publish --------> [Data] --------> Subscriber
	 * 
	 * <- subscribe(Subsc	riber)
	 * 
	 * -> Subsciber.onSubscribe(Subscription)
	 *
	 * <- Subscription.request(capacity)
	 *
	 * -> Subsciber.onNext(data)
	 * 
	 * -> Subsciber.onNext(ata)
	 * 
	 * -> .
	 * 
	 * -> .
	 * 
	 * -> Subscriber.onComplete()
	 * 
	 */

	public static void main(String[] args) throws Exception {
		Iterable<Integer> iter = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		Publisher<Integer> p = new Publisher<Integer>() {
			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				Iterator<Integer> it = iter.iterator();

				sub.onSubscribe(new Subscription() { // Subscription: Back Pressure
					@Override
					public void request(long n) {
						try {
							for(int i = 0; i < n; i++) {
								if (it.hasNext()) {
									sub.onNext(it.next());
								} else {
									sub.onComplete();
								}
							}
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

		Subscriber<Integer> s = new Subscriber<Integer>() {
			Subscription s = null;
			@Override
			public void onSubscribe(Subscription s) {
				System.out.println("onSubscribe ");
				this.s = s;
				this.s.request(4); // 4개만 줘!
			}
			@Override
			public void onNext(Integer t) {
				System.out.println("onNext " + t + ":" + Thread.currentThread().getId());
				this.s.request(1);
			}
			@Override
			public void onError(Throwable t) {
				System.out.println("onError " + t);
			}
			@Override
			public void onComplete() {
				System.out.println("onComplete ");
			}
		};
		p.subscribe(s);
	}
}
