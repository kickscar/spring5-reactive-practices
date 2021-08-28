package example.part2.ex03;

import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;


/* 
 * Spring WebFlux(Reacative Web)를 이용한 개발
 * 
 * 2. @MVC Webflux(기존의 @MVC 방식과 유사, 비동기 논블로킹 리액티브 스타일)
 * 
 *
 **/

/**
 * 
 * Reactive Web + @MVC : Mono 다양한 사용 예제
 * 
 * 
 * 
 */

@SpringBootApplication
public class Ex01SpringBootApp {
	
	@RestController
	public class MyController {
		
		@GetMapping("/test01")
		public Mono<String> test01() {
			return Mono.just("Hello World1").log(); // publisher[Mono] -> publisher[Log] -> Subscriber(스링이 알아서 걸어줌)
		}
		
		@GetMapping("/test02")
		public Mono<String> test02() {
			System.out.println("position1");
			
			 // doOnNext: onNext 이전에...
			Mono<String> m = Mono.just("Hello World2").doOnNext(c -> System.out.println(c)).log();
			
			System.out.println("position2");
			
			return m;
		}
		
		@GetMapping("/test03")
		public Mono<String> test03() {
			System.out.println("position1");
			
			// just: 이미 만들어진 콘텐츠, 만들고 가지고 들어감 
			Mono<String> m = Mono.just(generateHello(3)).log();
			
			System.out.println("position2");
			
			return m;
		}

		@GetMapping("/test04")
		public Mono<String> test04() {
			System.out.println("position1");
			
			// fromSupplier: subscribe가 일어나면 만들어짐(실행이 지연됨) -> 잘 기억해야 함!
			// Mono<String> m = Mono.fromSupplier(() -> generateHello(4) ).log();
			Mono<String> m = Mono.fromSupplier(new Supplier<String>() {

				@Override
				public String get() {
					return  generateHello(4);
				}} ).log();
			
			System.out.println("position2");
			
			return m;
		}
		
		@GetMapping("/test05")
		public Mono<String> test05() {
			System.out.println("position1");
			Mono<String> m = Mono.fromSupplier(() -> generateHello(5) ).log();
			
			//
			// 미리 subscribe를 해보쟝
			// Mono나 Flux는 1개 이상의 subscriber 가 있을 수 있다.
			//
			// Publisher의 소스 타입은 Cold / Hot Source 가 있다.
			// 동일한 결과가 전달되는 소스 : Cold -> Replay
			// subscribe 요청 시점부터 전달된다 : Hot  (실시간 이벤트들, 유저 인터페이스, 외부시스템에서 오는 데이터)
			//
			m.subscribe();
			
			System.out.println("position2");
			
			return m;
		}		
		
		
		//오류!!!
		@GetMapping("/test06")
		public Mono<String> test06() {
			System.out.println("position1");
			
			String msg1 = generateHello(6);
			Mono<String> m = Mono.just(msg1).log();
			
			//
			// block()
			// Mono로 감싼 가공된 데이터를 빼서 다시 쓰고 싶은 경우.
			String msg2 = m.block();
			System.out.println("position2: " + msg2);
			
			return m;
		}
		
		private String generateHello(Integer number) {
			System.out.println("method: generateHello");
			return "Hello World" + number;
		}
		
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Ex01SpringBootApp.class, args);
	}
}