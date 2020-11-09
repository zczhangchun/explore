package com.monkey.ehcache.config;

import org.ehcache.Cache;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.ResourcePools;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;

public class CacheConfig {

    public static void main(String[] args) throws Exception {
        ResourcePools resourcePools = ResourcePoolsBuilder.newResourcePoolsBuilder()
                .offheap(1, MemoryUnit.GB)
                .build();

        CacheConfiguration<Integer, byte[]> configuration = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class, byte[].class, resourcePools)
                .build();
        Cache<Integer, byte[]> offHeapCache = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("cacher", configuration)
                .build(true)
                .getCache("cacher", Integer.class, byte[].class);
        int index = 0;
        while (true){
            Thread.sleep(1);
            System.out.println("hello");
            offHeapCache.put(index++, new byte[1024]);
        }
    }
}
