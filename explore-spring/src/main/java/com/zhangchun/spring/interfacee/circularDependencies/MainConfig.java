package com.zhangchun.spring.interfacee.circularDependencies;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smlz on 2019/5/29.
 */
@Configuration
//@ImportResource(locations = "classpath:Beans.xml")
//@ComponentScan(basePackages = {"com.zhangchun.spring.interfacee.circularDependencies"})
public class MainConfig {

   @Bean
    public InstanceA instanceA(InstanceB instanceB){
        return new InstanceA(instanceB);
    }

    @Bean
    public InstanceB instanceB(InstanceA instanceA) {
        return new InstanceB(instanceA);
    }
}
