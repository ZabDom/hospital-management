package com.dzablotny.fancy.requests.configuration;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import java.util.ArrayList;
import java.util.List;

public class SOAPClientConfiguration {
    private List<SOAPConnection> connectionPool;
    private List<SOAPConnection> usedConnections = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;

    public SOAPClientConfiguration(List<SOAPConnection> connectionPool) {
        this.connectionPool = connectionPool;
    }

    public static SOAPClientConfiguration create() throws SOAPException {
        List<SOAPConnection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(soapConnectionFactory.createConnection());
        }
        return new SOAPClientConfiguration(pool);
    }

    public SOAPConnection getConnection() {
        SOAPConnection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);
        return connection;
    }

    public boolean releaseConnection(SOAPConnection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    public List<SOAPConnection> getConnectionPool() {
        return connectionPool;
    }
}
