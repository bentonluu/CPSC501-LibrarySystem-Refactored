public class Movie extends RentableItem {

    private int movieID;
    private String directorName;

    public Movie(String movieTitle, int movieID, String directorName, Boolean availability) {
        super(movieTitle, availability);
        this.movieID = movieID;
        this.directorName = directorName;
    }

    // Retrieves the availability of the movie.
    public Boolean getAvailability() {
        return availability;
    }

    // Retrieves the unique ID of the movie.
    public int getMovieID() {
        return movieID;
    }

    // Sets the availability of the movie to unavailable.
    public void movieUnavailable() {
        availability = false;
    }

    // Sets the availability of the movie to available.
    public void movieAvailable() {
        availability = true;
    }

    // Returns information about the movie's name and related details.
    public String itemNameAndDetails() {
        return "Movie Title: " + getTitle() + " | " + "Director Name: " + directorName + " | "
                + "Movie ID: " + movieID + " | " + "Availability: " + availability;
    }





}
