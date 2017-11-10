package ua.domino.entity;

/**
 * Created by Ник on 28.10.2017.
 */
public class ChainLink implements Cloneable{

    private Long id;
    private String chainNumber;
    private Long linkNumber;
    private Domino  domino;
    private Long parentId = 0L;

    @Override
    protected ChainLink clone() throws CloneNotSupportedException {
        ChainLink clone = (ChainLink)super.clone();
        clone.id = id;
        clone.chainNumber = chainNumber;
        clone.linkNumber = linkNumber;
        clone.domino = (Domino)domino.clone();
        clone.parentId = parentId;
        return clone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChainNumber() {
        return chainNumber;
    }

    public void setChainNumber(String chainNumber) {
        this.chainNumber = chainNumber;
    }

    public Domino getDomino() {
        return domino;
    }

    public void setDomino(Domino domino) {
        this.domino = domino;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getLinkNumber() {
        return linkNumber;
    }

    public void setLinkNumber(Long linkNumber) {
        this.linkNumber = linkNumber;
    }

}
