package com.back.weins.Config;

import com.back.weins.Interceptor.SessionValidateInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
//
    @Bean
    public SessionValidateInterceptor sessionValidateInterceptor(){
        return new SessionValidateInterceptor();
    }
//    @Resource
//    SessionValidateInterceptor sessionValidateInterceptor;

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(sessionValidateInterceptor()).addPathPatterns("/**");
//
//    }
//
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(sessionValidateInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/register")
                .excludePathPatterns("/blog/getPublicBlogs")
                .excludePathPatterns("/blog/page/getPublicBlogs")
                .excludePathPatterns("/blog/getBlogsLogined")
                .excludePathPatterns("/user/getAll")
                .excludePathPatterns("/user/getPlainOne")
                .excludePathPatterns("/blog/getBlogsById")
                .excludePathPatterns("/blog/page/recommend")
                .excludePathPatterns("/blog/getLabels")
        ;
    }
//
//    private CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//        return corsConfiguration;
//    }
//
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig());
//        return new CorsFilter(source);
//    }
}
