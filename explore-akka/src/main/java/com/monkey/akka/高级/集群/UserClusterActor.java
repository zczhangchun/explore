package com.monkey.akka.高级.集群;

import akka.actor.*;
import akka.cluster.Cluster;
import akka.cluster.client.ClusterClientReceptionist;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class UserClusterActor extends AbstractActor {
    private Cluster cluster = Cluster.get(getContext().system());

    @Override
    public Receive createReceive() {
        return receiveBuilder().matchAny(msg -> {
            getSender().tell(cluster.selfAddress() + "=====获取用户信息" + msg + getSelf(), getSelf());
        }).build();
    }

    public static void main(String[] args) {
        String port = args[0];
        Config config = ConfigFactory
                .parseString("akka.remote.netty.tcp.port=" + port)
                .withFallback(ConfigFactory.load("cluster.conf"));

        ActorSystem system = ActorSystem.create("sys", ConfigFactory.load(config));


        Cluster cluster = Cluster.get(system);
        Address address = new Address("akka.tcp", "sys", "127.0.0.1", 2551);
        cluster.join(address);


//        ActorRef userActor = system.actorOf(Props.create(UserClusterActor.class), "userActor");
        ActorRef userActor = system.actorOf(Props.create(ClusterRouterActor.class), "userActor");

        System.out.println(userActor);
        ClusterClientReceptionist.get(system).registerService(userActor);


    }


}
