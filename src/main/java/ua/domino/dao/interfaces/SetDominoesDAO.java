package ua.domino.dao.interfaces;

import ua.domino.entity.Domino;
import ua.domino.entity.SetDominoes;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public interface SetDominoesDAO {

    Long getCount();
    List<SetDominoes> findAllSetDominoes();
    SetDominoes findOneById(Long setDominoesId);
    SetDominoes firstStepSave(SetDominoes setDominoes);
    void secondStepSave(Domino domino, Long setNumberId);
}
