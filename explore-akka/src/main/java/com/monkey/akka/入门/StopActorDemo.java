package com.monkey.akka.入门;

import akka.actor.*;

public class StopActorDemo {

    static class WorkerActor extends AbstractActor {

        @Override
        public Receive createReceive() {
            return receiveBuilder().matchAny((msg) -> {
                System.out.println("正在做事情...");
                Thread.sleep(3000);
                System.out.println("做完了...");
            }).build();
        }

        @Override
        public void postStop() throws Exception {
            System.out.println("Worker postStop");
        }
    }


    static class WatchActor extends AbstractActor {
        ActorRef child = null;

        @Override
        public void preStart() throws Exception {
            child = getContext().actorOf(Props.create(WorkerActor.class), "workerActor");
            //监控child, unwatch可以解除监控
            getContext().watch(child);
        }

        @Override
        public void postStop() throws Exception {
            System.out.println("WatchActor postStop");
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .matchEquals("stopChild", s -> {
                        getContext().stop(child);
                    })
                    .match(String.class, s -> {
                        child.forward(s, getContext());
                    })
                    .match(Terminated.class, (t) -> {
                        System.out.println("监控到" + t.getActor() + "停止了");
                    })
                    .build();
        }
    }


    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("sys");
        ActorRef watchActor = system.actorOf(Props.create(WatchActor.class), "watchActor");
        watchActor.tell("work", ActorRef.noSender());

//        system.stop(watchActor);
        watchActor.tell(PoisonPill.getInstance(),ActorRef.noSender());
//        watchActor.tell(Kill.getInstance(), ActorRef.noSender());
    }
}
