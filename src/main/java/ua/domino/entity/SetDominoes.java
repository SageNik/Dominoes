package ua.domino.entity;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ник on 28.10.2017.
 */
public class SetDominoes {

    private Long id;
    private Long setNumber;
    private Set<Domino> dominoBones;
    private CopyOnWriteArrayList<ChainDominoes> chainDominoesList;


    public CopyOnWriteArrayList<ChainDominoes> getChainDominoesList() {
        return chainDominoesList;
    }

    public void setChainDominoesList(CopyOnWriteArrayList<ChainDominoes> chainDominoesList) {
        this.chainDominoesList = chainDominoesList;
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

    public Set<Domino> getDominoBones() {
        return dominoBones;
    }

    public void setDominoBones(Set<Domino> dominoBones) {
        this.dominoBones = dominoBones;
    }

}
