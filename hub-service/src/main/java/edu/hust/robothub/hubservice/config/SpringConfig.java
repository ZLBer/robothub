package edu.hust.robothub.hubservice.config;

import edu.hust.robothub.core.until.Config;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;




//用于读取spring cloud的config文件
@Component
public class SpringConfig extends Config implements BeanFactoryAware {


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        Environment environment = beanFactory.getBean(Environment.class);
        //TODO:



    }
}
