import java.awt.*;	
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/nmims";
    static final String USER = "root";
    static final String PASS = "rick@astley";

    // UI components
    JLabel emailLabel, passwordLabel, titleLabel;
    JTextField emailField;
    JPasswordField passwordField;
    JButton loginButton, signupButton;

    public LoginPage() {
        // Set up UI components
        titleLabel = new JLabel("Login Page");
        emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        signupButton = new JButton("Sign Up");

        // Set up layout manager
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Add components to layout
        add(titleLabel, gbc);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
      
        add(emailLabel, gbc);
        add(emailField, gbc);
        add(passwordLabel, gbc);
        add(passwordField, gbc);
        add(loginButton, gbc);
        add(signupButton, gbc);

        emailField.setPreferredSize(new Dimension(emailField.getPreferredSize().width, 30));
        passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, 30));
        // Add action listeners to buttons
        loginButton.addActionListener(this);
        signupButton.addActionListener(this);

        // Set window properties
        setTitle("Login Page");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        signupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	SignupPage signupPage = new SignupPage();
            }
        });
    }

    public static void main(String[] args) {
        new LoginPage();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            

            if (email.equals("") || password.equals("")) {
                  JOptionPane.showMessageDialog(null, "Please fill in all fields.");
              } 
            else {
            if (authenticateUser(email, password)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                this.dispose();
                TipCalculatorGUI tipCalculator = new TipCalculatorGUI((getUserName(email, password)));
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect email or password.");
            }
            }
        } else if (e.getSource() == signupButton) {
            emailField.setText("");
            passwordField.setText("");
        }
    }

    private boolean authenticateUser(String email, String password) {
        try {
            // Connect to database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare SQL statement
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Execute query
            ResultSet rs = stmt.executeQuery();
            
            // Check if user exists in database
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private String getUserName(String email, String password) {
        try {
            // Connect to database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare SQL statement
            String sql = "SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);

            // Execute query
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String userName = rs.getString("first_name");
            return userName;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return "";
        }
    }
}
