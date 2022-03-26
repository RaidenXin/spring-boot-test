package com.raiden.croe;

import com.raiden.annotation.TestService;
import com.raiden.factory.ProxyBeanFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

public class CustomPathScanHandle extends ClassPathBeanDefinitionScanner {

    public CustomPathScanHandle(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
         super(registry, useDefaultFilters);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        //添加过滤条件，这里是只添加了@TestService的注解才会被扫描到
        addIncludeFilter(new AnnotationTypeFilter(TestService.class, false, true));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        return beanDefinitionHolders;
    }

    @Override
    protected void registerBeanDefinition(BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry) {
        GenericBeanDefinition definition = (GenericBeanDefinition) definitionHolder.getBeanDefinition();
        definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
        definition.setBeanClass(ProxyBeanFactory.class);
        definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return (metadata.isIndependent() && (metadata.isConcrete() || (metadata.isAbstract())) && metadata.hasAnnotation(TestService.class.getName()));
    }
}
