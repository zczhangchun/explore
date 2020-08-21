package com.zhangchun.akka.高级.Http;

import akka.actor.ActorSystem;
import akka.http.javadsl.Http;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Sink;

import java.util.concurrent.CompletionStage;

public class HttpClientDemo {

    public static void main(String[] args) {
        final ActorSystem system = ActorSystem.create();

        final CompletionStage<HttpResponse> responseFuture = Http.get(system).singleRequest(HttpRequest.create("https://akka.io"));

        final Materializer materializer = ActorMaterializer.create(system);
        responseFuture.thenAccept(response->{
            response.entity().getDataBytes().runWith(Sink.foreach(content->{
                System.out.println(content.utf8String());
            }), materializer);
        });

    }
}

