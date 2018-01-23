package gd.app.graphgenerator;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (String bean : beanFactory.getBeanDefinitionNames()) {
            // do not lazy initialize the DIService
            if (bean.equals("DIService")) {
                continue;
            }
            // set bean to lazy initialize
            beanFactory.getBeanDefinition(bean).setLazyInit(true);
        }
    }

}
