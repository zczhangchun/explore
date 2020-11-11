package com.monkey.akka.高级.调度;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;


public class DispatcherDemo {

    static class SimpleDemo extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println(getSelf() + "--->" + msg + " " + Thread.currentThread().getName());
                Thread.sleep(5000);
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("dispacher.conf"));
        for(int i=0;i<50;i++) {
            // 每个Actor一个线程池（池中只有一个线程）
//            ActorRef ref = system.actorOf(Props.create(SimpleDemo.class).withDispatcher("my-pinned-dispatcher"),"actorDemo"+i);
            // 公用一个线程池
            ActorRef ref = system.actorOf(Props.create(SimpleDemo.class).withDispatcher("my-forkjoin-dispatcher"),"actorDemo"+i);
//            ActorRef ref = system.actorOf(Props.create(SimpleDemo.class).withDispatcher("my-threadpool-dispatcher"),"actorDemo"+i);
            ref.tell("hello",ActorRef.noSender());
        }
    }
}
