package com.monkey.akka.高级.远程;

import akka.actor.*;
import com.typesafe.config.ConfigFactory;

public class RemoteActorSystemDemo {

    static class SimpleActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println(msg);
            }).build();
        }
    }

    public static void main(String[] args) {
        ActorSystem sys = ActorSystem.create("sys", ConfigFactory.load("remote.conf"));
        ActorRef ref = sys.actorOf(Props.create(SimpleActor.class), "simpleActor");

        ActorSelection actorSelection = sys.actorSelection("akka.tcp://sys@127.0.0.1:2552/user/simpleActor");
        actorSelection.tell("tttt", ActorRef.noSender());
    }
}
