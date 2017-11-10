package ua.domino.dao.impl;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinalyBlock {
    private static final Logger LOGGER = Logger.getLogger(FinalyBlock.class.getName());

    public void withRS(ResultSet rs, PreparedStatement statement, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Can not close ResultSet", e);
                e.printStackTrace();
            }
        }
        withOutRS(statement, conn);

    }

    public void withOutRS (PreparedStatement statement, Connection conn){

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                LOGGER.error("Can not close PrepearedStatement", e);
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("Can not close Connection", e);
                e.printStackTrace();
            }
        }
    }
}
