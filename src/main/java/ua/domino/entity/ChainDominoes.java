package ua.domino.entity;

import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ник on 28.10.2017.
 */
public class ChainDominoes implements Cloneable{

    private Long id;
    private Long setNumber;
    private String chainNumber;
    private LinkedList<ChainLink> chainLinks;
    private CopyOnWriteArrayList<Domino> currentHeapDomino;
    private CopyOnWriteArrayList<Tail> tails;
    private Integer elementAmount;

   public ChainDominoes(){}

    public ChainDominoes clone() throws CloneNotSupportedException {
       ChainDominoes obj = (ChainDominoes)super.clone();
       obj.id = id;
       obj.setNumber = getSetNumber();
       obj.chainNumber = getChainNumber();
       obj.tails = new CopyOnWriteArrayList<Tail>();
        for (Tail tail:tails) {
            obj.tails.add((Tail)tail.clone());
        }
       obj.currentHeapDomino = new CopyOnWriteArrayList<Domino>();
        for (Domino heapDom:currentHeapDomino) {
            obj.currentHeapDomino.add((Domino)heapDom.clone());
        }
       obj.chainLinks = new LinkedList<ChainLink>();
        for (ChainLink link:chainLinks) {
            obj.chainLinks.add((ChainLink)link.clone());
        }
        return obj;
    }

    public CopyOnWriteArrayList<Tail> getTails() {
        return tails;
    }

    public void setTails(CopyOnWriteArrayList<Tail> tails) {
        this.tails = tails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSetNumber() {
        return setNumber;
    }

    public void setSetNumber(Long setNumber) {
        this.setNumber = setNumber;
    }

    public String getChainNumber() {
        return chainNumber;
    }

    public void setChainNumber(String chainNumber) {
        this.chainNumber = chainNumber;
    }

    public LinkedList<ChainLink> getChainLinks() {
        return chainLinks;
    }

    public void setChainLinks(LinkedList<ChainLink> chainLinks) {
        this.chainLinks = chainLinks;
    }


    public CopyOnWriteArrayList<Domino> getCurrentHeapDomino() {
        return currentHeapDomino;
    }

    public void setCurrentHeapDomino(CopyOnWriteArrayList<Domino> currentHeapDomino) {
        this.currentHeapDomino = currentHeapDomino;
    }

    public Integer getElementAmount() {
        return elementAmount;
    }

    public void setElementAmount(Integer elementAmount) {
        this.elementAmount = elementAmount;
    }
}
