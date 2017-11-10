package ua.domino.dao.interfaces;

import ua.domino.entity.Domino;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public interface DominoDAO {

   List<Domino> findAllDomino();
}
