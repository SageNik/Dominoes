package ua.domino.dao.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.DBConnection;
import ua.domino.dao.interfaces.DominoDAO;
import ua.domino.entity.Domino;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public class MySqlDominoDAO implements DominoDAO {

    private static final Logger LOGGER = Logger.getLogger(MySqlSetDominoesDAO.class.getName());
    private FinalyBlock finalyBlock = new FinalyBlock();

    @Override
    public List<Domino> findAllDomino() {

        LOGGER.info("Try get all domino from 'domino'");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Domino> dominoList = new ArrayList<>();
        try {
            con = DBConnection.getConnection();
            statement = con.prepareStatement("SELECT * FROM domino");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Domino currentDomino = new Domino();
                currentDomino.setId(resultSet.getLong("id"));
                currentDomino.setSideOne(resultSet.getInt("side_one"));
                currentDomino.setSideTwo(resultSet.getInt("side_two"));
                currentDomino.setDouble(resultSet.getBoolean("is_double"));
                dominoList.add(currentDomino);
            }
        }catch(SQLException e){
            LOGGER.error("Could not get all domino from 'domino'", e);
            e.printStackTrace();
        }finally {
            finalyBlock.withRS(resultSet,statement,con);
        }
        return dominoList;
    }
}
