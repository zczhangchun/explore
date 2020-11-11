package com.monkey.spring.annotation.importt;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author zhangchun
 */
@Configuration
//使用import的bean的名称是全限定类名，用@Bean和@ComponentScan的bean的名称是首字母小写类名
//@Import(value = {Car.class, Person.class})

//使用自己写的ImportBeanDefinitionRegistrar实现类
//@Import({MyImportBeanDefinitionRegistrar.class})

//使用自己写的ImportSelector实现类
@Import({MyImportBeanDefinitionRegistrar.class})
public class MainConfig {


}
