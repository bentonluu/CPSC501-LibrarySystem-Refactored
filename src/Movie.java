public class Movie extends RentableItem {

    private int movieID;
    private String directorName;

    public Movie(String movieTitle, int movieID, String directorName, Boolean availability) {
        super(movieTitle, availability);
        this.movieID = movieID;
        this.directorName = directorName;
    }

    // Retrieves the unique ID of the movie.
    public int getMovieID() {
        return movieID;
    }

    // Returns information about the movie's name and related details.
    public String itemNameAndDetails() {
        return "Movie Title: " + getTitle() + " | " + "Director Name: " + directorName + " | "
                + "Movie ID: " + movieID + " | " + "Availability: " + availability;
    }





}
