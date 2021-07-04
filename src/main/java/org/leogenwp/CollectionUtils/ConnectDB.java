package org.leogenwp.CollectionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import resources.application.properties;

public class ConnectDB {
    private  String url = "jdbc:mysql://localhost:3306/writer?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC";
    private  String username = "root";
    private  String password = "admin";
    private static ConnectDB instance;
    private Connection connection;

    private ConnectDB()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url,"root","admin");
        }

        catch(SQLException e)
        {
            System.err.println(e.getMessage());
        }

        catch(ClassNotFoundException e)
        {
            System.err.println(e.getMessage());
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public static ConnectDB getInstance() throws SQLException
    {
        if (instance == null) {
            instance = new ConnectDB();
        } else if (instance.getConnection().isClosed()) {
            instance = new ConnectDB();
        }

        return instance;
    }
}

