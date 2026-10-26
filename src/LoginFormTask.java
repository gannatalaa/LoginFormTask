import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
class User {
    // Todo: Add The Following Fields : username, password,role
    private String username;
    private String password;
    private String role;

    User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // TODO: add getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}
class WelcomePage extends Frame {
    WelcomePage(User user) {
        // TODO: build the page that says "Hello, username" and the role of the user
        setTitle("Welcome");
        setSize(300, 200);
        setLayout(new FlowLayout());

        Label welcomeLabel = new Label(
                "Hello, " + user.getUsername(), Label.CENTER);
        Label roleLabel = new Label(
                "Role: " + user.getRole(), Label.CENTER);

        add(welcomeLabel);
        add(roleLabel);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}
public class LoginFormTask extends Frame {
    //TODO: add list of users to seed
    private List<User> users = new ArrayList<>();
    private int failedAttempts = 0;

    public LoginFormTask() {
        super("Login");
        loadUsers();

        setLayout(new GridBagLayout());
        Panel content = new Panel(new BorderLayout(10, 15));

        Label title = new Label("Welcome back", Label.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        content.add(title, BorderLayout.NORTH);

        Panel form = new Panel(new GridLayout(3, 2, 8, 8));

        form.add(new Label("Username:", Label.RIGHT));
        TextField username = new TextField(18);
        form.add(username);

        form.add(new Label("Password:", Label.RIGHT));
        TextField password = new TextField(18);
        password.setEchoChar('*');
        form.add(password);

        form.add(new Label("Role:", Label.RIGHT));
        Choice role = new Choice();
        role.add("Student");
        role.add("TA");
        role.add("Professor");
        form.add(role);

        content.add(form, BorderLayout.CENTER);

        Panel buttons = new Panel(
                new FlowLayout(FlowLayout.CENTER, 15, 0));

        Button cancel = new Button("Cancel");
        Button login = new Button("Login");

        buttons.add(cancel);
        buttons.add(login);
        content.add(buttons, BorderLayout.SOUTH);

        add(content);

        login.addActionListener(e -> {
            // TODO: the login logic
            String inputUser = username.getText();
            String inputPass = password.getText();
            String inputRole = role.getSelectedItem();

            User u = findUser(inputUser);

            if (u != null
                    && u.getPassword().equals(inputPass)
                    && u.getRole().equalsIgnoreCase(inputRole)) {

                new WelcomePage(u);
                dispose();

            } else {
                failedAttempts++;

                if (failedAttempts >= 3) {
                    login.setEnabled(false);

                    JOptionPane.showMessageDialog(
                            this,
                            "You entered incorrect data 3 times. Login is disabled!",
                            "Login Locked",
                            JOptionPane.ERROR_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid username, password, or role!\n"
                                    + "Attempts remaining: "
                                    + (3 - failedAttempts),
                            "Login Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        cancel.addActionListener(e -> {
            // TODO: close the window
            dispose();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(420, 340);
        setLocationRelativeTo(null);
    }
    private void loadUsers() {
        // TODO: add at least 3 users to the list one for each role
        users.add(new User("admin", "1234", "Professor"));
        users.add(new User("ahmed", "abcd", "Student"));
        users.add(new User("ta_user", "ta12345", "TA"));
    }

    private User findUser(String name) {
        // TODO: search the list for the user
        for (User u : users) {
            if (u.getUsername().equals(name)) {
                return u;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        new LoginFormTask().setVisible(true);
    }
}