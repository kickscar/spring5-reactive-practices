package example.part2.ex03;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * Reactive Web + @MVC : Flux 다양한 사용 예제
 * 
 * 데이터가 여러개인거...
 * 
 * Mono<Collection<Event>> == Flux<Event>
 * 
 */


@SpringBootApplication

public class Ex02SpringBootApp {

	@RestController
	public class MyController {
		
		@GetMapping("/event1/{id}")
		public Mono<Event> event1(@PathVariable long id) {
			return Mono.just(new Event(id, "event" + id)); 
		}

		@GetMapping("/events1")
		public Flux<Event> events1() {
			return Flux.just(new Event(1L, "event1"), new Event(2L, "event2"), new Event(3L, "event3"));
		}
	
		@GetMapping("/event2")
		public Mono<List<Event>> event2() {
			List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"), new Event(3L, "event3"));
			return Mono.just(list);
		}
		
		//
		// 플럭스 안의 요소 하나 하나를 스트림 단위로 쪼개서 보내기
		//
		// produces
		// 1. accept header 내용 확인
		// 2. response Content-Type 지정
		//
		@GetMapping(value="/events2", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		public Flux<Event> events2() {
			List<Event> list = Arrays.asList(new Event(1L, "event1"), new Event(2L, "event2"), new Event(3L, "event3"));
			return Flux.fromIterable(list);
		}
		
		// 데이터 제너레이션(Stream API 이용)
		// 플럭스 안의 요소 하나 하나를 스트림 단위로 쪼개서 보내기: 푸시 예제
		@GetMapping(value="/events3", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		public Flux<Event> events3() {
			return Flux
					.fromStream(Stream.generate(() -> new Event(System.currentTimeMillis(), "none")))
					.delayElements(Duration.ofSeconds(1))  // onNext전에 1초 연기
					.take(10);
		}
		
		//
		// generate와 sink 사용하기
		// 
		@GetMapping(value="/events4", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		public Flux<Event> events4() {
			return Flux
					.<Event>generate(sink -> sink.next(new Event(System.currentTimeMillis(), "none")))
					.delayElements(Duration.ofSeconds(1)) 
					.take(10);
		}
		
		// 일련번호 생성 해보기 
		@GetMapping(value="/events5", produces=MediaType.TEXT_EVENT_STREAM_VALUE)
		public Flux<Event> events5() {
			return Flux
					.<Event, Long>generate(() -> 1L, (id, sink) -> {
						sink.next(new Event(id, "value" + id));
						return id+1;
					})
					.delayElements(Duration.ofSeconds(1)) 
					.log()
					.take(10);
		}		
		
	}
	
	private static class Event {
		private long id;
		private String value;
		
		public Event(long id, String value) {
			this.id = id;
			this.value = value;
		}
		
		public long getId() {
			return id;
		}
		
		public void setId(long id) {
			this.id = id;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Ex01SpringBootApp.class, args);
	}
}
