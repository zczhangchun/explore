package com.monkey.akka.入门;

import akka.actor.*;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;


public class SelectionDemo {

    static class TargetActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny((message) -> System.out.println("target receive" + message)).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef targetActor2 = system.actorOf(Props.create(TargetActor.class), "targetActor2");
        ActorRef targetActor1 = system.actorOf(Props.create(TargetActor.class), "targetActor1");

        ActorSelection actorSelection = system.actorSelection("user/targetActor*");
//        actorSelection.tell("hello", ActorRef.noSender());

        Future<ActorRef> future = actorSelection.resolveOne(new Timeout(Duration.create(3, TimeUnit.SECONDS)));
        future.onSuccess(new OnSuccess<ActorRef>() {
            @Override
            public void onSuccess(ActorRef result) throws Throwable {
                System.out.println(result);
            }
        }, system.getDispatcher());
    }
}
