package user.hibernate.anno.ext;

import java.io.Serializable;

public class Food implements Serializable {

    private static final long serialVersionUID = 1L;

    public int rank;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

}
