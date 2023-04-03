package com.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfigNewPath {
    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                // start this is make routing for any url have /get
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("bakryHeader","ahmed")
                                .addRequestParameter("Lovely","Jacky")
                        )
                        .uri("http://httpbin.org:80"))
                // end this is make routing for any url have /get
                .route(p -> p.path("/currency-exchange/**") // this is path
                        .uri("lb://currency-exchange/")) // we put before it this not showing in the url
                .route(p -> p.path("/getcurrency-exchange/**")
                        .uri("lb://currency-exchange/"))
                .route(p -> p.path("/currency-conversion/**")
                        .uri("lb://currency-conversion/"))
                .route(p -> p.path("/currency-conversion-feign/**")
                        .uri("lb://currency-conversion/"))
                // we here replace the path from /currency-conversion-feign/ to /currency-conversion-new/ and make route path for new
                .route(p ->p.path("/currency-conversion-new/**")
                        .filters(f -> f
                                .rewritePath("/currency-conversion-new/(?<segment>.*)" ,
                                        "/currency-conversion-feign/${segment}"))
                        .uri("lb://currency-conversion/"))
                .build();

    }
}
