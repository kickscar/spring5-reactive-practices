package example.part2.test;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;
import reactor.ipc.netty.http.server.HttpServer;

public class TestServer {
	public static void main(String[] args) throws Exception {
		HandlerFunction<ServerResponse> helloHandler = (ServerRequest req) -> {
			String name = req.pathVariable("name");
			return ServerResponse.ok().syncBody("Hello " + name);
		};
		
		RouterFunction<ServerResponse> routerFunction = (ServerRequest request) -> RequestPredicates.path("/hello/{name}").test(request) ? Mono.just(helloHandler) : Mono.empty();
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routerFunction);
		
		ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(httpHandler);
		HttpServer server = HttpServer.create("localhost", 8088);
		server.newHandler(adapter).block();

		System.in.read();
	}
}
