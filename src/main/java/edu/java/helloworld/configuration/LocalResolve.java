package edu.java.helloworld.configuration;

import java.util.List;
import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
public class LocalResolve extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    List<Locale> LOCALES = List.of(new Locale("en"), new Locale("fr"),new Locale("vn"),new Locale("de"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String languageHeader = request.getHeader("Accept-Language");
        return !StringUtils.hasLength(languageHeader)
                ? Locale.US
                : Locale.lookup(Locale.LanguageRange.parse(languageHeader), LOCALES);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        rs.setCacheSeconds(3600);
        return rs;
    }
}
