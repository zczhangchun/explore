package com.zhangchun.akka.高级.远程;

import akka.actor.*;
import akka.remote.RemoteScope;
import com.typesafe.config.ConfigFactory;

public class RemoteCreateActorDemo {

    static class RmtCreateActor extends AbstractActor {
        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                System.out.println("remote msg====" + msg);
            }).build();
        }
    }

    public static void main(String[] args) {
//        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remoteactor.conf"));
//        ActorRef ref = system.actorOf(Props.create(RmtCreateActor.class), "rmtCrtActor");
//        ref.tell("hello rmt", ActorRef.noSender());


        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load("remoteactor.conf"));
        Address address = new Address("akka.tcp", "sys", "127.0.0.1", 2552);
        ActorRef ref = system.actorOf(Props.create(RmtCreateActor.class).withDeploy(new Deploy(new RemoteScope(address))), "rmtCrtActor");
        ref.tell("hello rmt", ActorRef.noSender());
    }
}
