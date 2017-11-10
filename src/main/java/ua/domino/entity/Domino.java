package ua.domino.entity;

/**
 * Created by Ник on 28.10.2017.
 */
public class Domino implements Cloneable{

    private Long id;
    private Integer sideOne;
    private Integer sideTwo;
    private boolean isDouble;

    @Override
    protected Domino clone() throws CloneNotSupportedException {
        Domino clone = (Domino)super.clone();
        clone.id = id;
        clone.sideOne = sideOne;
        clone.sideTwo = sideTwo;
        clone.isDouble = isDouble;
        return clone;
    }

    public Boolean getDouble() {
        return isDouble;
    }

    public void setDouble(Boolean aDouble) {
        isDouble = aDouble;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSideOne() {
        return sideOne;
    }

    public void setSideOne(Integer sideOne) {
        this.sideOne = sideOne;
    }

    public Integer getSideTwo() {
        return sideTwo;
    }

    public void setSideTwo(Integer sideTwo) {
        this.sideTwo = sideTwo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domino domino = (Domino) o;

        if (!id.equals(domino.id)) return false;
        if (!sideOne.equals(domino.sideOne)) return false;
        return sideTwo.equals(domino.sideTwo);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + sideOne.hashCode();
        result = 31 * result + sideTwo.hashCode();
        return result;
    }
}
