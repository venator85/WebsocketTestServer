package eu.alessiobianchi.websockettestserver.logic;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class BeanUtil implements ApplicationContextAware {
    private static ApplicationContext appCxt;

    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        appCxt = applicationContext;
    }

    public static RelayService getRelayService() throws BeansException {
        return (RelayService) appCxt.getAutowireCapableBeanFactory().getBean("RelayService");
    }
}
