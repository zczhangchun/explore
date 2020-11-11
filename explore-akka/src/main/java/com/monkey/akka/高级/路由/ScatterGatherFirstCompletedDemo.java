package com.monkey.akka.高级.路由;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.routing.FromConfig;
import akka.util.Timeout;
import com.typesafe.config.ConfigFactory;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;


public class ScatterGatherFirstCompletedDemo {

    static class Worker1 extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println(getSelf() + " " + msg);
                Thread.sleep(5000);
                getSender().tell("Worker1", getSelf());
            }).build();
        }
    }

    static class Worker2 extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println(getSelf() + " " + msg);
                Thread.sleep(5000);
                getSender().tell("Worker2", getSelf());
            }).build();
        }

    }

    static class RouterActor extends AbstractActor {

        private ActorRef router;

        @Override

        public void preStart() throws Exception {
            getContext().actorOf(Props.create(Worker1.class), "wk1");
            getContext().actorOf(Props.create(Worker2.class), "wk2");
            router = getContext().actorOf(FromConfig.getInstance().props(), "routerActor");
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                router.tell(msg, getSender());
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("router.conf"));
        ActorRef master = system.actorOf(Props.create(RouterActor.class), "scatterGatherGroupActor");
//        ActorRef master = system.actorOf(Props.create(RouterActor.class), "tailGhoppingGroupActor");

        Timeout timeout = new Timeout(Duration.create(10, "seconds"));
        Future<Object> fu = Patterns.ask(master, "helloA", timeout);
        fu.onComplete(new OnComplete<Object>() {
            @Override
            public void onComplete(Throwable err, Object result) throws Throwable {
                System.out.println("result: " + result);
            }
        }, system.dispatcher());
    }
}
