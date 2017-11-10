package ua.domino.model;

import ua.domino.entity.ChainLink;

/**
 * Created by Ник on 08.11.2017.
 */
public class ResultChainDominoesViewModel {

    private String chainNumber;
    private String dominoSideOne;
    private String dominoSideTwo;
    private String isFinishChain = "false";

    public String getChainNumber() {
        return chainNumber;
    }

    public void setChainNumber(String chainNumber) {
        this.chainNumber = chainNumber;
    }

    public String getDominoSideOne() {
        return dominoSideOne;
    }

    public void setDominoSideOne(String dominoSideOne) {
        this.dominoSideOne = dominoSideOne;
    }

    public String getDominoSideTwo() {
        return dominoSideTwo;
    }

    public void setDominoSideTwo(String dominoSideTwo) {
        this.dominoSideTwo = dominoSideTwo;
    }

    public String getIsFinishChain() {
        return isFinishChain;
    }

    public void setIsFinishChain(String isFinishChain) {
        this.isFinishChain = isFinishChain;
    }

    public static ResultChainDominoesViewModel buildByChainLink(ChainLink chainLink){
         ResultChainDominoesViewModel result = new ResultChainDominoesViewModel();
         result.chainNumber = chainLink.getChainNumber();
         result.dominoSideOne = chainLink.getDomino().getSideOne().toString();
         result.dominoSideTwo = chainLink.getDomino().getSideTwo().toString();
        return result;
    }
}
