package persistence;

import java.util.Objects;

public class HighScore {
    public final int labSize;
    public final int score;
    
    public HighScore(int labSize, int score){
        this.labSize = labSize;
        this.score = score;
    }

    @Override
    public int hashCode() {
       return Objects.hash(this.labSize,this.score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HighScore other = (HighScore) obj;
        if (this.score != other.score) {
            return false;
        }
        if (this.labSize != other.labSize) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
            return "labSize : " + labSize + " x " + labSize + " score: "+ score;
    }
    
    
}
