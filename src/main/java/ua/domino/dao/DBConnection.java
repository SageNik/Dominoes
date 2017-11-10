package ua.domino.dao;

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

    public static Connection getConnection() {
        LOGGER.info("Try get connection");
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

            con = DriverManager.getConnection(url, username, password);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    return con;
    }
}
