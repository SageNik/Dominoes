package ua.domino.service.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.impl.MySqlDominoDAO;
import ua.domino.entity.Domino;
import ua.domino.service.interfaces.DominoService;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public class DominoServiceImpl implements DominoService {

    private static final Logger LOGGER = Logger.getLogger(DominoServiceImpl.class.getName());
    MySqlDominoDAO mySqlDominoDAO = new MySqlDominoDAO();

    @Override
    public List<Domino> getAllDomino() {
        LOGGER.info("Finding all Domino");
        return mySqlDominoDAO.findAllDomino();
    }
}
