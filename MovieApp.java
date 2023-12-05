import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieApp extends JFrame {
  private User currentUser;
  private MovieDatabase movieDatabase = new MovieDatabase(); // Initialize the MovieDatabase

  public MovieApp() {
    setTitle("Movie Application");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");

    loginButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showLoginFrame();
      }
    });

    registerButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showRegistrationFrame();
      }
    });

    panel.add(loginButton, BorderLayout.NORTH);
    panel.add(registerButton, BorderLayout.SOUTH);

    add(panel);
    setVisible(true);
  }

  private void showLoginFrame() {
    LoginFrame loginFrame = new LoginFrame();
    loginFrame.setVisible(true);
  }

  private void showRegistrationFrame() {
    RegistrationFrame registrationFrame = new RegistrationFrame();
    registrationFrame.setVisible(true);
  }

  private class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
      setTitle("Login");
      setSize(300, 200);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);

      JPanel panel = new JPanel();
      JLabel usernameLabel = new JLabel("Username:");
      JLabel passwordLabel = new JLabel("Password:");
      usernameField = new JTextField(20);
      passwordField = new JPasswordField(20);
      JButton loginButton = new JButton("Login");

      loginButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String username = usernameField.getText();
          char[] passwordChars = passwordField.getPassword();
          String password = new String(passwordChars);

          try {
            currentUser = User.login(username, password);
            showMovieManagementFrame(currentUser);
            dispose(); // Close the login frame after successful login
          } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      panel.add(usernameLabel);
      panel.add(usernameField);
      panel.add(passwordLabel);
      panel.add(passwordField);
      panel.add(loginButton);

      add(panel);
      setVisible(true);
    }
  }

  private class RegistrationFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public RegistrationFrame() {
      setTitle("User Registration");
      setSize(300, 200);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);

      JPanel panel = new JPanel();
      JLabel usernameLabel = new JLabel("Username:");
      JLabel passwordLabel = new JLabel("Password:");
      usernameField = new JTextField(20);
      passwordField = new JPasswordField(20);
      JButton registerButton = new JButton("Register");

      registerButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          String username = usernameField.getText();
          char[] passwordChars = passwordField.getPassword();
          String password = new String(passwordChars);

          try {
            User.register(username, password);
            JOptionPane.showMessageDialog(null, "Registration successful");
            dispose(); // Close the registration frame after successful registration
          } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
          }
        }
      });

      panel.add(usernameLabel);
      panel.add(usernameField);
      panel.add(passwordLabel);
      panel.add(passwordField);
      panel.add(registerButton);

      add(panel);
      setVisible(true);
    }
  }

  private void showMovieManagementFrame(User currentUser) {
    if (currentUser != null) {
      MovieManagementFrame managementFrame = new MovieManagementFrame(currentUser);
      managementFrame.setVisible(true);
    } else {
      JOptionPane.showMessageDialog(null, "User not logged in", "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public class MovieManagementFrame extends JFrame {
    private JTextField titleField;
    private JTextField directorField;
    private JTextField releaseYearField;
    private JTextField runningTimeField;
    private User currentUser;

    public MovieManagementFrame(User currentUser) {
      this.currentUser = currentUser;
      setTitle("Movie Management");
      setSize(400, 300);
      setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      setLocationRelativeTo(null);

      JPanel panel = new JPanel();
      panel.setLayout(new GridLayout(5, 2));

      JLabel titleLabel = new JLabel("Title:");
      titleField = new JTextField();
      JLabel directorLabel = new JLabel("Director:");
      directorField = new JTextField();
      JLabel releaseYearLabel = new JLabel("Release Year:");
      releaseYearField = new JTextField();
      JLabel runningTimeLabel = new JLabel("Running Time:");
      runningTimeField = new JTextField();

      JButton addButton = new JButton("Add Movie");
      JButton removeButton = new JButton("Remove Movie");
      JButton browseButton = new JButton("Browse Movies");
      JButton addToWatchlistButton = new JButton("Add to Watchlist");
      JButton watchlistButton = new JButton("Watchlist");

      addButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          addMovie();
        }
      });

      removeButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          removeMovie();
        }
      });

      browseButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          browseMovies();
        }
      });

      addToWatchlistButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          addToWatchlist();
        }
      });

      watchlistButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          showWatchlist();
        }
      });

      panel.add(titleLabel);
      panel.add(titleField);
      panel.add(directorLabel);
      panel.add(directorField);
      panel.add(releaseYearLabel);
      panel.add(releaseYearField);
      panel.add(runningTimeLabel);
      panel.add(runningTimeField);
      panel.add(addButton);
      panel.add(removeButton);

      add(panel, BorderLayout.CENTER);

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(browseButton);
      buttonPanel.add(addToWatchlistButton);
      buttonPanel.add(watchlistButton);
      add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addMovie() {
      if (currentUser != null && movieDatabase != null) {
        String title = titleField.getText();
        String director = directorField.getText();
        int releaseYear = Integer.parseInt(releaseYearField.getText());
        int runningTime = Integer.parseInt(runningTimeField.getText());

        try {
          Movie movie = new Movie(title, director, releaseYear, runningTime);
          movieDatabase.addMovie(movie);
          JOptionPane.showMessageDialog(null, "Movie added to the database");
        } catch (IllegalArgumentException ex) {
          JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(null, "User not logged in or MovieDatabase not initialized", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }

    private void removeMovie() {
      if (currentUser != null && movieDatabase != null) {
        String title = titleField.getText();
        movieDatabase.removeMovie(title);
        JOptionPane.showMessageDialog(null, "Movie removed from the database");
      } else {
        JOptionPane.showMessageDialog(null, "User not logged in or MovieDatabase not initialized", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }

    private void browseMovies() {
      if (movieDatabase != null) {
        StringBuilder movieList = new StringBuilder("Movies in Database:\n\n");

        for (Movie movie : movieDatabase.getMovies()) {
          movieList.append(movie.toString()).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, movieList.toString(), "Movie Database", JOptionPane.INFORMATION_MESSAGE);
      } else {
        JOptionPane.showMessageDialog(null, "MovieDatabase not initialized", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }

    private void addToWatchlist() {
      if (currentUser != null && movieDatabase != null) {
        String title = titleField.getText();
        Movie selectedMovie = movieDatabase.getMovieByTitle(title);

        if (selectedMovie != null) {
          currentUser.addToWatchlist(selectedMovie);
          JOptionPane.showMessageDialog(null, "Movie added to your watchlist");
        } else {
          JOptionPane.showMessageDialog(null, "Selected movie not found in the database", "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      } else {
        JOptionPane.showMessageDialog(null, "User not logged in or MovieDatabase not initialized", "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }

    private void showWatchlist() {
      if (currentUser != null && currentUser.getWatchlist().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Watchlist is empty", "Watchlist", JOptionPane.INFORMATION_MESSAGE);
        return;
      }

      StringBuilder watchlistInfo = new StringBuilder("Your Watchlist:\n\n");

      for (Movie movie : currentUser.getWatchlist()) {
        watchlistInfo.append(movie.toString()).append("\n\n");
      }

      JOptionPane.showMessageDialog(null, watchlistInfo.toString(), "Watchlist", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new MovieApp();
      }
    });
  }
}
