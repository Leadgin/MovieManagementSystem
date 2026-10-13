package moviesys;

import java.util.Objects;

public class Actor {
    private String actorID;
    private String actorName;

    //-----------------------------constructor-----------------------------
    public Actor() {

    }

    public Actor(String actorID, String actorName) {
        this.actorID = actorID;
        this.actorName = actorName;
    }

    //-----------------------------getters-----------------------------
    public String getActorID() {
        return actorID;
    }

    public String getActorName() {
        return actorName;
    }

    //-----------------------------setters-----------------------------
    public void setActorID(String actorID) {
        this.actorID = actorID;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    //-----------------------------methods-----------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Actor)) {
            return false;
        }
        Actor a = (Actor) o;
        return Objects.equals(actorID, a.actorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(actorID);
    }

    @Override
    public String toString() {
        return (" ");
    }
}
