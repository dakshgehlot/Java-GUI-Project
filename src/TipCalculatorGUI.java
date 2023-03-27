import java.awt.*;		
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipCalculatorGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel billLabel = new JLabel("Bill:");
    private JLabel tipPercentLabel = new JLabel("Tip:");
    private JTextField billField = new JTextField(10);

    private JRadioButton tip10Button = new JRadioButton("10%");
    private JRadioButton tip15Button = new JRadioButton("15%");
    private JRadioButton tip20Button = new JRadioButton("20%");

    private ButtonGroup tipButtonGroup = new ButtonGroup();

    private JLabel splitLabel = new JLabel("Split:");
    private JButton minusButton = new JButton("-");
    private JLabel splitValueLabel = new JLabel("1");
    private JButton addButton = new JButton("+");
    private JButton showResultsButton = new JButton("Retrieve Records");
    private JButton calculateButton = new JButton("Calculate");
    private JButton logoutButton = new JButton("Logout");
    private TextArea resultText = new TextArea(5, 20);

    public TipCalculatorGUI(String name) {
        JLabel userName = new JLabel("Hey, " + name + "!");
        // Set component fonts
        Font userNameFont = new Font("Tahoma", Font.BOLD, 18);
        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        Font resultFont = new Font("Arial", Font.BOLD, 24);

        userName.setFont(userNameFont);
        billLabel.setFont(labelFont);
        tipPercentLabel.setFont(labelFont);
        billField.setFont(fieldFont);
        logoutButton.setFont(buttonFont);
        tip10Button.setFont(buttonFont);
        tip15Button.setFont(buttonFont);
        tip20Button.setFont(buttonFont);
        splitLabel.setFont(labelFont);
        minusButton.setFont(buttonFont);
        splitValueLabel.setFont(labelFont);
        addButton.setFont(buttonFont);
        calculateButton.setFont(buttonFont);
        showResultsButton.setFont(buttonFont);
        resultText.setFont(resultFont);

        // Set component colors
        Color backgroundColor = new Color(240, 240, 240);
        Color foregroundColor = new Color(20, 20, 20);
        Color highlightColor = new Color(200, 200, 200);
        Color buttonColor = new Color(230, 230, 230);

        userName.setForeground(foregroundColor);
        billLabel.setForeground(foregroundColor);
        tipPercentLabel.setForeground(foregroundColor);
        billField.setBackground(backgroundColor);
        billField.setForeground(foregroundColor);
        logoutButton.setBackground(buttonColor);
        tip10Button.setBackground(buttonColor);
        tip15Button.setBackground(buttonColor);
        tip20Button.setBackground(buttonColor);
        logoutButton.setForeground(foregroundColor);
        tip10Button.setForeground(foregroundColor);
        tip15Button.setForeground(foregroundColor);
        tip20Button.setForeground(foregroundColor);
        tip10Button.setFocusPainted(false);
        tip15Button.setFocusPainted(false);
        tip20Button.setFocusPainted(false);
        tip10Button.setBorderPainted(false);
        tip15Button.setBorderPainted(false);
        tip20Button.setBorderPainted(false);
        splitLabel.setForeground(foregroundColor);
        minusButton.setBackground(buttonColor);
        splitValueLabel.setBackground(backgroundColor);
        splitValueLabel.setForeground(foregroundColor);
        addButton.setBackground(buttonColor);
        calculateButton.setBackground(highlightColor);
        showResultsButton.setBackground(highlightColor);
        resultText.setForeground(foregroundColor);

        // Add action listeners
        calculateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calculateTip();
            }
        });

        minusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int splitValue = Integer.parseInt(splitValueLabel.getText());
                if (splitValue > 1) {
                    splitValue--;
                    splitValueLabel.setText(Integer.toString(splitValue));
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int splitValue = Integer.parseInt(splitValueLabel.getText());
                splitValue++;
                splitValueLabel.setText(Integer.toString(splitValue));
            }
        });

        showResultsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                viewResultsActionPerformed(evt);
            }
        });

        // Set layout manager and add components
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(userName, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(billLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(billField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(tipPercentLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(tip10Button, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(tip15Button, gbc);

        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(tip20Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 10, 10, 10);
        mainPanel.add(splitLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        mainPanel.add(minusButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(splitValueLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(addButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(new JSeparator(), gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(calculateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(showResultsButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 4;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(resultText, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.LINE_END;
        mainPanel.add(logoutButton, gbc);

        // Set radio buttons in a group
        tipButtonGroup.add(tip10Button);
        tipButtonGroup.add(tip15Button);
        tipButtonGroup.add(tip20Button);

        // Add main panel to content pane
        setContentPane(mainPanel);

        // Set applet size and make visible
        setSize(600, 600);
        setTitle("Tip Calculator");
        setLocationRelativeTo(null);
        setVisible(true);
        
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
            	new LoginPage();
            }
         });
    }

    private void calculateTip() {
        try {
            double bill = Double.parseDouble(billField.getText());
            double tipPercentage = 0.0;
            if (tip10Button.isSelected()) {
                tipPercentage = 0.1;
            } else if (tip15Button.isSelected()) {
                tipPercentage = 0.15;
            } else if (tip20Button.isSelected()) {
                tipPercentage = 0.2;
            }
            int splitValue = Integer.parseInt(splitValueLabel.getText());
            double tipAmount = bill * tipPercentage;
            double totalAmount = bill + tipAmount;
            double perPersonAmount = totalAmount / splitValue;
            resultText.setText(
                    String.format("Total Bill: ₹%.2f\nTip Amount: ₹%.2f\nTotal Amount: ₹%.2f\nPer Person Amount: ₹%.2f",
                            bill, tipAmount, totalAmount, perPersonAmount));
            saveData(bill, tipPercentage, splitValue, tipAmount, totalAmount, perPersonAmount);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter a valid number for Bill amount and Split number.");
        }
    }

    private void saveData(
            double f_bill,
            double f_tipPercentage,
            int f_splitNumber,
            double f_tipAmount,
            double f_totalAmount,
            double f_perPersonAmount) {

        // Save results to database
        try {
            f_tipPercentage = 100 * f_tipPercentage;
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/nmims", "root", "rick@astley");
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO tip_calculator_results (bill, tip_percentage, split_count, tip_amount, total_amount, per_person_amount) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setDouble(1, f_bill);
            statement.setDouble(2, f_tipPercentage);
            statement.setInt(3, f_splitNumber);
            statement.setDouble(4, f_tipAmount);
            statement.setDouble(5, f_totalAmount);
            statement.setDouble(6, f_perPersonAmount);
            statement.executeUpdate();
            statement.close();
            conn.close();
            System.out.println("Result saved to database.");
        } catch (SQLException ex) {
            System.out.println("Failed to save result to database.");
            ex.printStackTrace();
        }
    }

    private void viewResultsActionPerformed(ActionEvent evt) {
        try {
            // create a connection to the database
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nmims", "root", "rick@astley");

            // create a prepared statement to select all records from the
            // tip_calculator_results table
            // order by id desc because we need to reverse the result set to get the 5 latest rows.
            // ...as new data is appended at the end
            String query = "SELECT * FROM tip_calculator_results ORDER BY id DESC";
            PreparedStatement ps = con.prepareStatement(query);

            // execute the query and get the result set
            ResultSet rs = ps.executeQuery();

            // create a string to hold the results
            String results = "";

            // loop through the result set and append latest 5 rows to the results string
            int num_of_records = 5;
            int count = 1;
            while (rs.next() && num_of_records > 0) {
                results +=  count + ") Bill amount: ₹" + rs.getDouble("bill") + ", Tip percentage: "
                        + rs.getDouble("tip_percentage") + "%, Number of people: " + rs.getInt("split_count")
                        + ", Total per person: ₹" + rs.getDouble("per_person_amount") + "\n";
                num_of_records--;
                count++;
            }

            // display the results in a message dialog
            if (results.equals("")) {
                JOptionPane.showMessageDialog(null, "No results found");
            } else {
                JOptionPane.showMessageDialog(null, results);
            }

            // close the database connection and prepared statement
            ps.close();
            con.close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error connecting to database");
            System.out.print(ex);
        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("Tip Calculator");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        JApplet applet = new TipCalculatorGUI();
//        applet.init();
//        frame.setContentPane(applet.getContentPane());
//        frame.pack();
//        frame.setVisible(true);
//    }
}