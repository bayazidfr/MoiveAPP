import java.util.ArrayList;
import java.util.List;

public class MovieDatabase {
  private List<Movie> movies;

  public MovieDatabase() {
    this.movies = new ArrayList<>();
  }

  public void addMovie(Movie movie) {
    if (!movies.contains(movie)) {
      movies.add(movie);
      System.out.println("Movie added to the database: " + movie.getTitle());
    } else {
      throw new IllegalArgumentException("Movie already exists in the database.");
    }
  }

  public void removeMovie(String title) {
    movies.removeIf(movie -> movie.getTitle().equals(title));
    System.out.println("Movie removed from the database: " + title);
  }

  public Movie getMovieByTitle(String title) {
    for (Movie movie : movies) {
      if (movie.getTitle().equals(title)) {
        return movie;
      }
    }
    throw new IllegalArgumentException("Movie not found in the database.");
  }

  public void displayMovies() {
    for (Movie movie : movies) {
      System.out.println(movie);
    }
  }

  public List<Movie> getMovies() {
    return movies;
  }
}
