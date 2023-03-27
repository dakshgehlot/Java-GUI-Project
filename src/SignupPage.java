import javax.swing.*;    	
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SignupPage extends JFrame implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// GUI components
    JLabel titleLabel, nameLabel, lastNameLabel, phoneLabel, emailLabel, passwordLabel;
    JTextField nameField, lastNameField, phoneField, emailField;
    JPasswordField passwordField;
    JButton submitButton, resetButton;
    JPanel mainPanel;

    // Database connection
    Connection con;

    public SignupPage() {

        // Create database connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/nmims";
            String user = "root";
            String password = "rick@astley";
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e);
        }

        // Set the layout of the main panel
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        // Add the title label to the main panel
        titleLabel = new JLabel("Sign Up Form");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        mainPanel.add(titleLabel, c);

        // Add the name label and text field to the main panel
        nameLabel = new JLabel("First Name:");
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        mainPanel.add(nameLabel, c);
        nameField = new JTextField();
        c.gridx = 1;
        c.gridy = 1;
        nameField.setColumns(15);
        nameField.setPreferredSize(new Dimension(nameField.getPreferredSize().width, 30));
        mainPanel.add(nameField, c);

        // Add the last name label and text field to the main panel
        lastNameLabel = new JLabel("Last Name:");
        c.gridx = 0;
        c.gridy = 2;
        mainPanel.add(lastNameLabel, c);
        lastNameField = new JTextField();
        c.gridx = 1;
        c.gridy = 2;
        lastNameField.setPreferredSize(new Dimension(lastNameField.getPreferredSize().width, 30));
        mainPanel.add(lastNameField, c);

        // Add the phone label and text field to the main panel
        phoneLabel = new JLabel("Phone Number:");
        c.gridx = 0;
        c.gridy = 3;
        mainPanel.add(phoneLabel, c);
        phoneField = new JTextField();
        c.gridx = 1;
        c.gridy = 3;
        phoneField.setPreferredSize(new Dimension(phoneField.getPreferredSize().width, 30));
        mainPanel.add(phoneField, c);

        // Add the email label and text field to the main panel
        emailLabel = new JLabel("Email:");
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(emailLabel, c);
        emailField = new JTextField();
        c.gridx = 1;
        c.gridy = 4;
        emailField.setPreferredSize(new Dimension(emailField.getPreferredSize().width, 30));
        mainPanel.add(emailField, c);

        // Add the password label and text field to the main panel
        passwordLabel = new JLabel("Password:");
        c.gridx = 0;
        c.gridy = 5;
        mainPanel.add(passwordLabel, c);
        passwordField = new JPasswordField();
        c.gridx = 1;
        c.gridy = 5;
        passwordField.setPreferredSize(new Dimension(passwordField.getPreferredSize().width, 30));
        mainPanel.add(passwordField, c);

        // Add the submit button and reset button to the main panel
        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 6;
        mainPanel.add(submitButton, c);
        Dimension buttonSize = submitButton.getPreferredSize();
        buttonSize.width = 150; // Set the desired width
        submitButton.setPreferredSize(buttonSize);
        resetButton = new JButton("Reset");
        resetButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 6;
        Dimension buttonSize1 = resetButton.getPreferredSize();
        buttonSize1.width = 100; // Set the desired width
        resetButton.setPreferredSize(buttonSize);
        mainPanel.add(resetButton, c);
        
      resetButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
          // Clear all text fields
          nameField.setText("");
          lastNameField.setText("");
          phoneField.setText("");
          emailField.setText("");
          passwordField.setText("");
      }
  });
      
      // Add main panel to content pane
      setContentPane(mainPanel);

      // Set applet size and make visible
      setSize(500, 500);
      setTitle("Sign Up Page");
      setLocationRelativeTo(null);
      setVisible(true);
        
        
    }

  public void actionPerformed(ActionEvent e) {
  if (e.getSource() == submitButton) {
      String firstName = nameField.getText();
      String lastName = lastNameField.getText();
      String phoneNumber = phoneField.getText();
      String email = emailField.getText();
      char[] passwordChars = passwordField.getPassword();
      String password = new String(passwordChars);

      if (firstName.equals("") || lastName.equals("") || phoneNumber.equals("") || email.equals("") || password.equals("")) {
          JOptionPane.showMessageDialog(null, "Please fill in all fields.");
      } else {
          try {
              // Establish database connection
              Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nmims", "root", "rick@astley");
              Statement stmt = conn.createStatement();
              
              // Execute SQL query to insert user data into the database
              String insertQuery = "INSERT INTO users (first_name, last_name, phone_number, email, password) VALUES ('" + firstName + "', '" + lastName + "', '" + phoneNumber + "', '" + email + "', '" + password + "')";
              stmt.executeUpdate(insertQuery);
              
              // Close database connection and show success message
              conn.close();
              JOptionPane.showMessageDialog(null, "Sign Up successful!");
              
              this.dispose();
              TipCalculatorGUI tipCalculator = new TipCalculatorGUI(firstName);
              
          } catch (SQLException ex) {
              JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
          }
      }
  }
}

//public static void main(String[] args) {
//	SignupPage signupPage = new SignupPage();
//}
  
}
