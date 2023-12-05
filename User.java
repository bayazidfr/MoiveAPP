import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
  private static final String DATABASE_PATH = "user_database.txt";
  private String username;
  private String password;
  private List<Movie> watchlist;

  public User(String username, String password) {
    this.username = username;
    setPassword(password);
    this.watchlist = new ArrayList<>();
  }

  public void addToWatchlist(Movie movie) {
    if (movie != null) {
      watchlist.add(movie);
      updateWatchlistInDatabase();
      System.out.println("Movie added to the watchlist");
    } else {
      System.out.println("Invalid movie. Unable to add to watchlist.");
    }
  }

  private void updateWatchlistInDatabase() {
    String watchlistInfo = username + "'s Watchlist:\n";
    for (Movie movie : watchlist) {
      watchlistInfo += movie.getTitle() + "\n";
    }

    try {
      Files.write(Paths.get(DATABASE_PATH), (watchlistInfo + "\n").getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (!isValidString(password)) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (!isValidString(username)) {
      throw new IllegalArgumentException("Username cannot be null or empty");
    }

    if (!isUsernameAvailable(username)) {
      throw new IllegalArgumentException("Username already exists");
    }

    this.username = username;
  }

  public static void register(String username, String password) {
    if (isUsernameAvailable(username)) {
      User user = new User(username, password);
      user.writeToDatabase();
      System.out.println("Registration successful");
    } else {
      System.out.println("Username already exists. Registration failed");
    }
  }

  public static User login(String username, String password) {
    User foundUser = isValidLogin(username, password);
    if (foundUser != null) {
      System.out.println("Login successful");
      return foundUser;
    } else {
      System.out.println("Login failed");
      return null;
    }
  }

  private static User isValidLogin(String username, String password) {
    Map<String, String> users = readUsersFromDatabase();
    if (users.containsKey(username) && users.get(username).equals(password)) {
      return new User(username, password);
    }
    return null;
  }

  private static Map<String, String> readUsersFromDatabase() {
    Map<String, String> users = new HashMap<>();
    try {
      List<String> lines = Files.readAllLines(Paths.get(DATABASE_PATH));
      for (String line : lines) {
        String[] userEntry = line.split(":");
        if (userEntry.length == 2) {
          users.put(userEntry[0], userEntry[1]);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return users;
  }

  public List<Movie> getWatchlist() {
    return watchlist;
  }

  private void writeToDatabase() {
    String info = username + ":" + password;
    try {
      Files.write(Paths.get(DATABASE_PATH), (info + "\n").getBytes(), StandardOpenOption.APPEND);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static boolean isUsernameAvailable(String username) {
    createDatabaseIfNotExists();
    Map<String, String> users = readUsersFromDatabase();
    return !users.containsKey(username);
  }

  private static void createDatabaseIfNotExists() {
    if (!Files.exists(Paths.get(DATABASE_PATH))) {
      try {
        Files.createFile(Paths.get(DATABASE_PATH));
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private boolean isValidString(String str) {
    return str != null && !str.isEmpty();
  }
}
