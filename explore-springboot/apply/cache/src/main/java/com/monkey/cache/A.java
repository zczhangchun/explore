package com.monkey.cache;

import org.springframework.stereotype.Component;

@Component
public class A extends C{
    public A(B b) {
        super(b);
    }


}
