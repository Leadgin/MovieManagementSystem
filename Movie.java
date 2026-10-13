package moviesys;

import java.util.Objects;

public class Movie {

    private String movieID;
    private String movieTitle;
    private String ageRating;
    private int duration;

    //-----------------------------constructor-----------------------------
    public Movie() {

    }

    public Movie(String movieID, String movieTitle, String ageRating, int duration) {
        this.movieID = movieID;
        this.movieTitle = movieTitle;
        this.ageRating = ageRating;
        this.duration = duration;
    }

    //-----------------------------getters-----------------------------
    public String getMovieID() {
        return movieID;
    }

    public String getMovieName() {
        return movieTitle;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public int getDuration() {
        return duration;
    }

    //-----------------------------setters-----------------------------
    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }

    public void setMovieName(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    //-----------------------------methods-----------------------------
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Movie)) {
            return false;
        }
        Movie m = (Movie) o;
        return Objects.equals(movieID, m.movieID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieID);
    }

    @Override
    public String toString() {
        return "Movie Title: " + movieTitle;
    }

}
