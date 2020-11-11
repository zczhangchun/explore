package com.monkey.akka.高级.事件总线;

import akka.actor.*;


public class EventStreamDemo {


    // 订阅自定义消息
    interface SuperMessage {

    }

    static class MessageA implements SuperMessage {

    }

    static class MessageB implements SuperMessage {

    }

    // 消费死信消息
    static class SimpleActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny(msg -> {
                if (msg instanceof DeadLetter) {
                    DeadLetter deadLetter = (DeadLetter) msg;
                    System.out.println("deadLetter:" + deadLetter.message() + " " + deadLetter.sender() + " " + deadLetter.recipient());
                } else {
                    System.out.println("otherMessage" + msg);
                }
            }).build();
        }
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef actorRef = system.actorOf(Props.create(SimpleActor.class));
//        system.eventStream().subscribe(actorRef, DeadLetter.class);
//
//        ActorSelection delayActor = system.actorSelection("test");
//        delayActor.tell("test", ActorRef.noSender());

        system.eventStream().subscribe(actorRef, SuperMessage.class);
        system.eventStream().publish(new MessageA());
        system.eventStream().publish(new MessageB());

    }
}
