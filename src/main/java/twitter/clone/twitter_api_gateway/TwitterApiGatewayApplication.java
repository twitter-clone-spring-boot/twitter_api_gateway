package twitter.clone.twitter_api_gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import twitter.clone.twitter_api_gateway.filter.JWTAuthenticationFilter;

@EnableEurekaClient
@SpringBootApplication
public class TwitterApiGatewayApplication {
	@Autowired
	private JWTAuthenticationFilter filter;

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/tweet/**")
						.filters(f -> f.filter(filter))
//						.uri("lb://TWEET_SERVICE_APP")) // fix this
						.uri("http://localhost:8080"))
				.route(p -> p
						.path("/auth/**")
//						.uri("lb://AUTH_SERVICE_APP")) fix this
						.uri("http://localhost:8081"))
				.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterApiGatewayApplication.class, args);
	}

}
