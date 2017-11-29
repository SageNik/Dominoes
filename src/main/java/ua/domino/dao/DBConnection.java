package ua.domino.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created by Ник on 27.10.2017.
 */
public class DBConnection {

    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private static DBConnection dataSource;
    private ComboPooledDataSource comboPooledDataSource;

    private DBConnection() {

        Properties props = new Properties();
        FileInputStream in = null;
        Connection con = null;

        try {
            in = new FileInputStream(new File("src/main/webapp/resources/properties/app.properties"));
            props.load(in);
            in.close();

            String driver = props.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }

            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");

            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource
                    .setDriverClass(driver);
            comboPooledDataSource
                    .setJdbcUrl(url);
            comboPooledDataSource.setUser(username);
            comboPooledDataSource.setPassword(password);

        } catch (IOException | ClassNotFoundException | PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (dataSource == null)
            dataSource = new DBConnection();
        return dataSource;
    }

    public Connection getConnection() {
        LOGGER.info("Try get connection");
        Connection con = null;

        try {
            con = comboPooledDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
