package ua.domino.model;

import ua.domino.entity.ChainDominoes;

/**
 * Created by Ник on 08.11.2017.
 */
public class HistoryChainDominoesViewModel {

    private String setNumber;
    private String chainNumber;
    private String elementsAmount;

    public static HistoryChainDominoesViewModel buildByChainDominoes(ChainDominoes chainDominoes){
        HistoryChainDominoesViewModel viewModel = new HistoryChainDominoesViewModel();
        viewModel.setNumber = chainDominoes.getSetNumber().toString();
        viewModel.chainNumber = chainDominoes.getChainNumber();
        viewModel.elementsAmount = String.valueOf(chainDominoes.getElementAmount());
        return viewModel;
    }

    public String getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(String setNumber) {
        this.setNumber = setNumber;
    }

    public String getChainNumber() {
        return chainNumber;
    }

    public void setChainNumber(String chainNumber) {
        this.chainNumber = chainNumber;
    }

    public String getElementsAmount() {
        return elementsAmount;
    }

    public void setElementsAmount(String elementsAmount) {
        this.elementsAmount = elementsAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HistoryChainDominoesViewModel viewModel = (HistoryChainDominoesViewModel) o;

        if (setNumber != null ? !setNumber.equals(viewModel.setNumber) : viewModel.setNumber != null) return false;
        if (chainNumber != null ? !chainNumber.equals(viewModel.chainNumber) : viewModel.chainNumber != null)
            return false;
        return elementsAmount != null ? elementsAmount.equals(viewModel.elementsAmount) : viewModel.elementsAmount == null;
    }

    @Override
    public int hashCode() {
        int result = setNumber != null ? setNumber.hashCode() : 0;
        result = 31 * result + (chainNumber != null ? chainNumber.hashCode() : 0);
        result = 31 * result + (elementsAmount != null ? elementsAmount.hashCode() : 0);
        return result;
    }
}
