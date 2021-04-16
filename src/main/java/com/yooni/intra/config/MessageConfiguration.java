package com.yooni.intra.config;

import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@Configuration
public class MessageConfiguration implements WebMvcConfigurer {

    @Bean //지역설정 default ko
    public LocaleResolver localeResolver(){
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.KOREA);
        return sessionLocaleResolver;
    }

    @Bean // 지역설정 변경
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

//    @Override // 인터셉트 레지스트리 등록
//    public void addInterCeptor(InterceptorRegistry registry){
//        registry.addInterceptor(localeChangeInterceptor());
//    }

    @Bean //yml 파일 참조
    public MessageSource messageSource(
            @Value("${spring.messages.basename}") String basename,
            @Value("${spring.messages.encoding}") String encoding
    ){
        YamlMessageSource ms = new YamlMessageSource();
        return ms;
    }
    private static class YamlMessageSource extends ResourceBundleMessageSource{

        @Override
        protected ResourceBundle doGetBundle(String basename,Locale locale) throws MissingResourceException {
            return ResourceBundle.getBundle(basename,locale, YamlResourceBundle.Control.INSTANCE);
        }
    }
}
