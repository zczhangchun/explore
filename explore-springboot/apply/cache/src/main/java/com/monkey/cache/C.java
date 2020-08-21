package com.monkey.cache;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public abstract class C {


    protected final B b;

    public void hello(){
        sys();
    }

    private void sys(){
        System.out.println("hello");
    }
}
