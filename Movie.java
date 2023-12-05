public class Movie {
  private String title;
  private String director;
  private int releaseYear;
  private int runningTime;

  public Movie(String title, String director, int releaseYear, int runningTime) {
    setTitle(title);
    setDirector(director);
    setReleaseYear(releaseYear);
    setRunningTime(runningTime);
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    if (isValidString(director)) {
      this.director = director;
    } else {
      throw new IllegalArgumentException("Director cannot be null or empty");
    }
  }

  public int getReleaseYear() {
    return releaseYear;
  }

  public void setReleaseYear(int releaseYear) {
    if (releaseYear >= 1866 && releaseYear <= 2023) {
      this.releaseYear = releaseYear;
    } else {
      throw new IllegalArgumentException("Invalid release year. Must be between 1866 and 2023");
    }
  }

  public int getRunningTime() {
    return runningTime;
  }

  public void setRunningTime(int runningTime) {
    if (runningTime > 0) {
      this.runningTime = runningTime;
    } else {
      throw new IllegalArgumentException("Running time must be greater than 0");
    }
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    if (isValidString(title)) {
      this.title = title;
    } else {
      throw new IllegalArgumentException("Title cannot be null or empty");
    }
  }

  @Override
  public String toString() {
    return "Movie{" +
        "title='" + title + '\'' +
        ", director='" + director + '\'' +
        ", releaseYear=" + releaseYear +
        ", runningTime=" + runningTime +
        '}';
  }

  private boolean isValidString(String str) {
    return str != null && !str.isEmpty();
  }

}
