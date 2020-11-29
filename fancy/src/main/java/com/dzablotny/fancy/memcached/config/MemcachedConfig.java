package com.dzablotny.fancy.memcached.config;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.BinaryConnectionFactory;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MemcachedConfig {
    private MemcachedClient[] connectionPool = new MemcachedClient[] {};
    private int connectionPoolSize = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedConfig.class);

    public static MemcachedClient generateMemcachedClient() throws IOException {
        LOGGER.info("Successfully connected to memcached.");
        return new MemcachedClient(new InetSocketAddress("memcached", 11211));
    }

    public MemcachedConfig(int connectionPoolSize, String host) {
        try {
            connectionPool = new MemcachedClient[connectionPoolSize];
            for (int i = 0; i < connectionPoolSize; i++) {
                connectionPool[i] = new MemcachedClient(new BinaryConnectionFactory(),
                        AddrUtil.getAddresses(host));
            }
        } catch (IOException e) {
            LOGGER.error(String.format(
                    "Unable to create connection pool, %s",
                    e
            ));
        }
        this.connectionPoolSize = connectionPoolSize;
    }

    public MemcachedClient getCache() {
        return connectionPool[(int) (Math.random() * connectionPoolSize)];
    }
}
