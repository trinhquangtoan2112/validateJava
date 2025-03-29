package edu.java.helloworld.configuration;




import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.IOException;



/*@Configurable
public class AppConfig implements WebMvcConfigurer  {
*//*  cach 1
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowCredentials(true);
    }
*//*
}*/
//@Configurable
//public class AppConfig  {
//
//
//   /* @Bean   cach2
//    public WebMvcConfigurer corsFilter(){
//      return  new WebMvcConfigurer() {
//          @Override
//          public void addCorsMappings(CorsRegistry registry) {
//              registry.addMapping("/**").allowCredentials(true);
//          }
//      };
//    }*/
//      @Bean   cach3
//    public FilterRegistrationBean<CorsFilter> corsFilter(){
//          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//          CorsConfiguration config =new CorsConfiguration();
//          config.setAllowCredentials(true);
//          source.registerCorsConfiguration("/**",config);
//          FilterRegistrationBean bean = new FilterRegistrationBean<>(new CorsFilter(source));
//          bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//          return bean;
//      }
//}


@Component
public class AppConfig extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin","truyen vao domain");
        filterChain.doFilter(request,response);
    }
}