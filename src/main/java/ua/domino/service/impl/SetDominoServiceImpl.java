package ua.domino.service.impl;

import org.apache.log4j.Logger;
import ua.domino.dao.impl.MySqlSetDominoesDAO;
import ua.domino.entity.Domino;
import ua.domino.entity.SetDominoes;
import ua.domino.service.interfaces.SetDominoService;

import java.util.HashSet;
import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public class SetDominoServiceImpl implements SetDominoService {

    private static final Logger LOGGER = Logger.getLogger(SetDominoService.class.getName());
    private MySqlSetDominoesDAO mySqlSetDominoesDAO = new MySqlSetDominoesDAO();

    @Override
    public List<SetDominoes> getAllSetDominoes() {
        return mySqlSetDominoesDAO.findAllSetDominoes();
    }

    @Override
    public SetDominoes getSetDominoesById(Long id) {
        return mySqlSetDominoesDAO.findOneById(id);
    }

    @Override
    public SetDominoes create(Integer amount) {

        LOGGER.info("Start to create set Domino");
        SetDominoes setDominoes = new SetDominoes();
        DominoServiceImpl dominoService = new DominoServiceImpl();

        List<Domino> dominoesPool = dominoService.getAllDomino();
        if (amount == 28) {
            setDominoes.setDominoBones(new HashSet<Domino>(dominoesPool));
        } else {
            setDominoes.setDominoBones(new HashSet<Domino>());
            while (setDominoes.getDominoBones().size() < amount) {
                int currentIndex = (int) (Math.random() * 28);
                setDominoes.getDominoBones().add(dominoesPool.get(currentIndex));
            }
        }
        LOGGER.info("Try to get count set Domino");
        Long setNumber = mySqlSetDominoesDAO.getCount();
        setDominoes.setSetNumber(++setNumber);
        saveSetDominoes(setDominoes);
        return setDominoes;
    }

    private void saveSetDominoes(SetDominoes setDominoes) {

        LOGGER.info("Try to save set Domino");
        SetDominoes savedOne = mySqlSetDominoesDAO.firstStepSave(setDominoes);
        for (Domino domino : setDominoes.getDominoBones()) {
            mySqlSetDominoesDAO.secondStepSave(domino, savedOne.getId());
        }
        LOGGER.info("Set Domino saved");
    }
}
