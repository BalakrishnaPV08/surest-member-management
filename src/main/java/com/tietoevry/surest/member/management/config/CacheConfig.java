package com.tietoevry.surest.member.management.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class CacheConfig {
    @Bean
    public CaffeineCacheManager cacheManager() {
        CaffeineCacheManager mgr = new CaffeineCacheManager("members");
        mgr.setCaffeine(Caffeine.newBuilder().maximumSize(1000).expireAfterAccess(Duration.ofMinutes(30)));
        return mgr;
    }
}

