package com.dzablotny.legacy.drivermanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DriverManagerConfiguration {
    private final String url;
    private final String username;
    private final String password;
    private final List<Statement> statementPool;
    private List<Statement> usedStatements = new ArrayList<>();
    private static final int INITIAL_POOL_SIZE = 5;

    public DriverManagerConfiguration(String url, String username, String password,
                                      List<Statement> statementPool) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.statementPool = statementPool;
    }

    public static DriverManagerConfiguration create(String url, String username, String password)
            throws SQLException {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        List<Statement> statements = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection(url, username, password));
            statements.add(pool.get(i).createStatement());
        }
        return new DriverManagerConfiguration(url, username, password, statements);
    }

    public Statement getStatement()  {
        Statement statement = statementPool.remove(statementPool.size() - 1);
        usedStatements.add(statement);
        return statement;
    }

    private static Connection createConnection(String url, String username, String password)
            throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public boolean releaseStatement(Statement statement) {
        statementPool.add(statement);
        return usedStatements.remove(statement);
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
