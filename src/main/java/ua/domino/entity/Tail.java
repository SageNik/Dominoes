package ua.domino.entity;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Ник on 02.11.2017.
 */
public class Tail implements Cloneable{

    private Integer tailNumber;
    private ChainLink chainLink;
    private Integer tailValue;
    private CopyOnWriteArrayList<Domino> variants ;

    public Tail(ChainLink chainLink, Integer tailValue, Integer tailNumber) {
        this.chainLink = chainLink;
        this.tailValue = tailValue;
        this.variants = new CopyOnWriteArrayList<>();
        this.tailNumber = tailNumber;

    }

    @Override
    protected Tail clone() throws CloneNotSupportedException {
       Tail clone = (Tail)super.clone();
       clone.tailNumber = tailNumber;
       clone.chainLink = chainLink;
       clone.tailValue = tailValue;
       clone.variants = new CopyOnWriteArrayList<Domino>();
        for (Domino variant:variants) {
            clone.variants.add((Domino)variant.clone());
        }
        return clone;
    }

    public Integer getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(Integer tailNumber) {
        this.tailNumber = tailNumber;
    }

    public CopyOnWriteArrayList<Domino> getVariants() {
        return variants;
    }

    public void setVariants(CopyOnWriteArrayList<Domino> variants) {
        this.variants = variants;
    }

    public ChainLink getChainLink() {
        return chainLink;
    }

    public void setChainLink(ChainLink chainLink) {
        this.chainLink = chainLink;
    }

    public Integer getTailValue() {
        return tailValue;
    }

    public void setTailValue(Integer tailValue) {
        this.tailValue = tailValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tail tail = (Tail) o;

        if (tailNumber != null ? !tailNumber.equals(tail.tailNumber) : tail.tailNumber != null) return false;
        if (chainLink != null ? !chainLink.equals(tail.chainLink) : tail.chainLink != null) return false;
        if (tailValue != null ? !tailValue.equals(tail.tailValue) : tail.tailValue != null) return false;
        return variants != null ? variants.equals(tail.variants) : tail.variants == null;
    }

    @Override
    public int hashCode() {
        int result = tailNumber != null ? tailNumber.hashCode() : 0;
        result = 31 * result + (chainLink != null ? chainLink.hashCode() : 0);
        result = 31 * result + (tailValue != null ? tailValue.hashCode() : 0);
        result = 31 * result + (variants != null ? variants.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tail{" +
                "chainLink=" + chainLink +
                ", tailValue=" + tailValue +
                ", variants=" + variants +
                '}';
    }
}
