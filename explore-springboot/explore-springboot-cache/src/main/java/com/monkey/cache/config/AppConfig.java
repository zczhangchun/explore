//package com.monkey.cache.config;
//
//import com.github.benmanes.caffeine.cache.CacheLoader;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import com.github.benmanes.caffeine.cache.LoadingCache;
//import org.checkerframework.checker.nullness.qual.NonNull;
//import org.springframework.cache.CacheManager;
//import org.springframework.cache.caffeine.CaffeineCacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.Executor;
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class AppConfig {
//
//    /**
//     * 配置缓存管理器，如果使用javaConfig配置方式，可以不引用cache-starter
//     *
//     * @return 缓存管理器
//     */
//    @Bean("caffeineCacheManager")
//    public CacheManager cacheManager() {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(Caffeine.newBuilder()
//                // 设置最后一次写入或访问后经过固定时间过期
//                .expireAfterAccess(60, TimeUnit.SECONDS)
//                // 初始的缓存空间大小
//                .initialCapacity(1)
//                // 缓存的最大条数
//                .maximumSize(300));
//        return cacheManager;
//    }
//
//
//
//    @Bean
//    public LoadingCache<String, Integer> caffeineCache(){
//        return Caffeine.newBuilder()
//                .refreshAfterWrite(1, TimeUnit.MINUTES)//一分钟异步重新加载
//                .expireAfterWrite(1, TimeUnit.HOURS)//一个小时没用移除
//                .build(new CacheLoader<String, Integer>() {
//                    @Override
//                    public Integer load(@NonNull String key) throws Exception {
//                        String[] ss = key.split("_");
//                        return loadData(key);
//                    }
//
//
//                    @Override
//                    public @NonNull CompletableFuture<Integer> asyncReload(@NonNull String key, @NonNull Integer oldValue, @NonNull Executor executor) {
//                        String[] ss = key.split("_");
//                        return CompletableFuture.supplyAsync(() -> loadData(key));
//                    }
//
//                });
//    }
//
//    public Integer loadData(String key){
//        return Integer.valueOf(key);
//    }
//
//
//
//}
