package ua.domino.service.interfaces;

import ua.domino.entity.SetDominoes;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public interface SetDominoService {

    List<SetDominoes> getAllSetDominoes();
    SetDominoes getSetDominoesById(Long id);
    SetDominoes create(Integer amount);
}
