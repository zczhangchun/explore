package com.monkey.cache.config;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Configuration
public class ApplicationConfig {

    @Bean
    public CaffeineCache featureCache(){
        return new CaffeineCache("feature", Caffeine.newBuilder()
                .maximumSize(2)
                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<Object, Object>() {
                    @Nullable
                    @Override
                    public Object load(@NonNull Object key) throws Exception {
                        return "1";
                    }


                    //@Nullable
                    //@Override
                    //public Object reload(@NonNull Object key, @NonNull Object oldValue) throws Exception {
                    //    return "feature";
                    //}

                    @Override
                    public @NonNull CompletableFuture<Object> asyncReload(@NonNull Object key, @NonNull Object oldValue, @NonNull Executor executor) {

                        return CompletableFuture.supplyAsync(() -> "2");
                    }
                }));

    }

    @Bean
    public CaffeineCache eventCache(){

        return cache("event", key -> "3" );
        //return new CaffeineCache("event", Caffeine.newBuilder()
        //        .maximumSize(100)
        //        .refreshAfterWrite(5, TimeUnit.SECONDS)
        //        .build(new CacheLoader<Object, Object>() {
        //            @Nullable
        //            @Override
        //            public Object load(@NonNull Object key) throws Exception {
        //                return "3";
        //            }
        //
        //            //@Override
        //            //public @NonNull CompletableFuture<Object> asyncReload(@NonNull Object key, @NonNull Object oldValue, @NonNull Executor executor) {
        //            //    return CompletableFuture.supplyAsync(() -> "4");
        //            //}
        //
        //            @Nullable
        //            @Override
        //            public Object reload(@NonNull Object key, @NonNull Object oldValue) throws Exception {
        //                return "event";
        //            }
        //        }));
    }


    public CaffeineCache cache(String name, Function<Object, Object> function){
        return new CaffeineCache(name, Caffeine.newBuilder()
                .maximumSize(100)
                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<Object, Object>() {
                    @Nullable
                    @Override
                    public Object load(@NonNull Object key) throws Exception {
                        System.out.println("helll");
                        return function.apply(key);
                    }

                    //@Override
                    //public @NonNull CompletableFuture<Object> asyncReload(@NonNull Object key, @NonNull Object oldValue, @NonNull Executor executor) {
                    //    return CompletableFuture.supplyAsync(() -> "4");
                    //}

                    @Nullable
                    @Override
                    public Object reload(@NonNull Object key, @NonNull Object oldValue) throws Exception {
                        return function.apply(key);
                    }
                }));
    }
}
