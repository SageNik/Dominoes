package ua.domino.dao.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.DBConnection;
import ua.domino.dao.interfaces.SetDominoesDAO;
import ua.domino.entity.Domino;
import ua.domino.entity.SetDominoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public class MySqlSetDominoesDAO implements SetDominoesDAO {
    private static final Logger LOGGER = Logger.getLogger(MySqlSetDominoesDAO.class.getName());
    private FinalyBlock finalyBlock = new FinalyBlock();

    @Override
    public Long getCount() {
        LOGGER.info("Try get count from 'set_domino'");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Long count = null;
        try {
            con = DBConnection.getInstance().getConnection();
            statement = con.prepareStatement("SELECT COUNT(id) FROM set_domino");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            LOGGER.error("Could not get count from 'set_domino'", e);
            e.printStackTrace();
        } finally {
            finalyBlock.withRS(resultSet, statement, con);
        }
        return count;
    }

    @Override
    public List<SetDominoes> findAllSetDominoes() {
        LOGGER.info("Try get all SetDominoes from 'set_dominoes'");
        Connection con = null;
        PreparedStatement statement = null;
        PreparedStatement secondStatement = null;
        ResultSet secondResultSet = null;
        ResultSet resultSet = null;
        List<SetDominoes> setDominoesList = new ArrayList<>();
        try{
            con = DBConnection.getInstance().getConnection();
            statement = con.prepareStatement("SELECT * FROM set_domino");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                SetDominoes newSetDominoes = new SetDominoes();
                newSetDominoes.setDominoBones(new HashSet<Domino>());
                newSetDominoes.setSetNumber(resultSet.getLong("set_number"));
                newSetDominoes.setId(resultSet.getLong("id"));


                secondStatement = con.prepareStatement("SELECT * FROM domino WHERE id IN (SELECT domino_id FROM cell_of_set_domino WHERE set_domino_id =?)");
                secondStatement.setLong(1,newSetDominoes.getId());
                secondResultSet = secondStatement.executeQuery();
                newDominoFromResultSet(secondResultSet, newSetDominoes);
                setDominoesList.add(newSetDominoes);
                secondResultSet.close();
                secondStatement.close();
            }

        }catch(SQLException e){
            LOGGER.error("Could not get all SetDominoes from 'set_dominoes'", e);
            e.printStackTrace();
        }finally {
            if (secondResultSet != null) {
                try {
                    secondResultSet.close();
                } catch (SQLException e) {
                    LOGGER.error("Can not close ResultSet", e);
                    e.printStackTrace();
                }
            }
            if (secondStatement != null) {
                try {
                    secondStatement.close();
                } catch (SQLException e) {
                    LOGGER.error("Can not close PrepearedStatement", e);
                    e.printStackTrace();
                }
            }

            finalyBlock.withRS(resultSet,statement,con);
        }

        return setDominoesList;
    }

    private void newDominoFromResultSet(ResultSet resultSet, SetDominoes newSetDominoes) throws SQLException {
        while (resultSet.next()){
            Domino newDomino = new Domino();
            newDomino.setId(resultSet.getLong("id"));
            newDomino.setSideOne(resultSet.getInt("side_one"));
            newDomino.setSideTwo(resultSet.getInt("side_two"));
            newDomino.setDouble(resultSet.getBoolean("is_double"));
            newSetDominoes.getDominoBones().add(newDomino);
        }
    }

    @Override
    public SetDominoes findOneById(Long setDominoesId) {

        LOGGER.info("Try to find setDominoes by id="+setDominoesId);
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        SetDominoes setDominoes = new SetDominoes();
        try{
            con = DBConnection.getInstance().getConnection();
            statement = con.prepareStatement("SELECT * FROM set_domino WHERE id=?");
            statement.setLong(1,setDominoesId);
            resultSet = statement.executeQuery();
            while (resultSet.next()){

                setDominoes.setId(resultSet.getLong("id"));
                setDominoes.setSetNumber(resultSet.getLong("set_number"));
                setDominoes.setDominoBones(new HashSet<Domino>());
                resultSet.close();
                statement.close();

                statement = con.prepareStatement("SELECT * FROM domino WHERE id IN (SELECT domino_id FROM cell_of_set_domino WHERE set_domino_id =?)");
                statement.setLong(1,setDominoes.getId());
                resultSet = statement.executeQuery();
                newDominoFromResultSet(resultSet, setDominoes);
            }

        }catch (SQLException e) {
            LOGGER.error("Could not find count setDominoes by id="+setDominoesId, e);
            e.printStackTrace();
        } finally {
            finalyBlock.withRS(resultSet, statement, con);
        }
        return setDominoes;
    }

    @Override
    public SetDominoes firstStepSave(SetDominoes setDominoes) {

        LOGGER.info("Try save setDominoes to 'set_domino'");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            con = DBConnection.getInstance().getConnection();
            statement = con.prepareStatement("INSERT INTO set_domino (id,set_number) VALUES (?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, 0);
            statement.setLong(2, setDominoes.getSetNumber());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while (resultSet.next()) {
                setDominoes.setId(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            LOGGER.error("Could not save setDominoes to 'set_domino'", e);
            e.printStackTrace();
        } finally {
            finalyBlock.withRS(resultSet, statement, con);
        }
        return setDominoes;
    }

    @Override
    public void secondStepSave(Domino domino, Long setDominoesId) {

        LOGGER.info("Try save data to 'cell_of_set_domino'");
        Connection con = null;
        PreparedStatement statement = null;

        try {
            con = DBConnection.getInstance().getConnection();
            statement = con.prepareStatement("INSERT INTO cell_of_set_domino (id,set_domino_id,domino_id) VALUES (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1, 0);
            statement.setLong(2, setDominoesId);
            statement.setLong(3, domino.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Could not save data to 'cell_of_set_domino'", e);
            e.printStackTrace();
        } finally {
            finalyBlock.withOutRS(statement, con);
        }
    }
}
