package ua.domino.dao.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.DBConnection;
import ua.domino.dao.interfaces.ChainDominoesDAO;
import ua.domino.entity.ChainDominoes;
import ua.domino.entity.ChainLink;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public class MySqlChainDominoesDAO implements ChainDominoesDAO {

    private static final Logger LOGGER = Logger.getLogger(MySqlSetDominoesDAO.class.getName());
    private FinalyBlock finalyBlock = new FinalyBlock();

    @Override
    public List<ChainDominoes> findAllChainDominoes() {

        LOGGER.info("Try get all chainDominoes from 'chain'");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ChainDominoes> chainDominoesList = new ArrayList<>();
        try{
            con = DBConnection.getConnection();
            statement = con.prepareStatement("SELECT * FROM chain");
            resultSet = statement.executeQuery();
            while (resultSet.next()){
                ChainDominoes newChainDominoes = new ChainDominoes();
                newChainDominoes.setId(resultSet.getLong("id"));
                newChainDominoes.setChainNumber(resultSet.getString("chain_number"));
                newChainDominoes.setSetNumber(resultSet.getLong("set_number"));
                newChainDominoes.setElementAmount(resultSet.getInt("element_amount"));
                chainDominoesList.add(newChainDominoes);
            }
        }catch(SQLException e){
            LOGGER.error("Could not get all chainDominoes from 'chain'", e);
            e.printStackTrace();
        }finally {
            finalyBlock.withRS(resultSet,statement,con);
        }

        return chainDominoesList;
    }

    @Override
    public ChainDominoes firstStepSave(ChainDominoes chainDominoes) {

        LOGGER.info("Try save chainDominoes to 'chain'");
        Connection con = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            con = DBConnection.getConnection();
            statement = con.prepareStatement("INSERT INTO chain (id,chain_number,set_number,element_amount) VALUES (?,?,?,?)",PreparedStatement.RETURN_GENERATED_KEYS);
            statement.setLong(1,0);
            statement.setString(2,chainDominoes.getChainNumber());
            statement.setLong(3,chainDominoes.getSetNumber());
            statement.setInt(4,chainDominoes.getChainLinks().size());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            while(resultSet.next()){
                chainDominoes.setId(resultSet.getLong(1));
            }
        }catch(SQLException e){
            LOGGER.error("Could not save chainDominoes to 'chain'", e);
            e.printStackTrace();
        }finally {
            finalyBlock.withRS(resultSet,statement,con);
        }
        return chainDominoes;
    }

    @Override
    public void secondStepSave(ChainLink chainLink, Long chainDominoesId) {

        LOGGER.info("Try save data to 'chain_link'");
        Connection con = null;
        PreparedStatement statement = null;
        try{
            con = DBConnection.getConnection();
            statement = con.prepareStatement("INSERT INTO chain_link (id,domino_id, chain_id,parent_id,link_number) VALUES (?,?,?,?,?)");
            statement.setLong(1,0);
            statement.setLong(2,chainLink.getDomino().getId());
            statement.setLong(3,chainDominoesId);
            statement.setLong(4,chainLink.getParentId());
            statement.setLong(5,chainLink.getLinkNumber());
            statement.executeUpdate();
        }catch (SQLException e){
            LOGGER.error("Could not save chainDominoes to 'chain'", e);
            e.printStackTrace();
        }finally {
            finalyBlock.withOutRS(statement,con);
        }
    }
}
