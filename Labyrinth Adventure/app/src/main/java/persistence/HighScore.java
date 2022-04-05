package persistence;

import java.util.Objects;

public class HighScore {
    public final String user;
    public final int score;
    
    public HighScore(String user, int score){
        this.user = user;
        this.score = score;
    }

    @Override
    public int hashCode() {
       return Objects.hash(this.user,this.score);
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
        if (this.user != other.user) {
            return false;
        }
        return true;
    }   

    @Override
    public String toString() {
            return "user:" + user + " score: "+ score;
    }
    
    
}
