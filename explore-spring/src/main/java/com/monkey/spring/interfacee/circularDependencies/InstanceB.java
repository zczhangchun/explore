package com.monkey.spring.interfacee.circularDependencies;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by smlz on 2019/5/29.
 */
@Component
public class InstanceB {

    @Autowired
    private InstanceA instanceA;

    public InstanceB(InstanceA instanceA) {
        this.instanceA = instanceA;
    }
}
