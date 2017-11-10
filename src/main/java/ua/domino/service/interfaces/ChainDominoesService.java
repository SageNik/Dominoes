package ua.domino.service.interfaces;

import ua.domino.model.HistoryChainDominoesViewModel;
import ua.domino.model.ResultChainDominoesViewModel;

import java.util.List;

/**
 * Created by Ник on 28.10.2017.
 */
public interface ChainDominoesService {
    List<HistoryChainDominoesViewModel> getAllChainDominoes();
    List<ResultChainDominoesViewModel> buildChains(String mode, Long setDominoesId);

}
