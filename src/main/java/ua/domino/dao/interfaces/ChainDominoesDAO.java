package ua.domino.dao.interfaces;

import ua.domino.entity.ChainDominoes;
import ua.domino.entity.ChainLink;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public interface ChainDominoesDAO {

    List<ChainDominoes> findAllChainDominoes();
    ChainDominoes firstStepSave(ChainDominoes chainDominoes);
    void secondStepSave(ChainLink chainLink, Long chainDominoesId);
}
