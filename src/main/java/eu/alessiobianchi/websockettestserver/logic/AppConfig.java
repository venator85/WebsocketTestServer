package eu.alessiobianchi.websockettestserver.logic;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ServletRegistrationBean socketServlet() {
        return new ServletRegistrationBean(new WsServiceServlet(), "/ws/service");
    }

    @Bean
    public BeanUtil beanUtil() {
        return new BeanUtil();
    }
}
