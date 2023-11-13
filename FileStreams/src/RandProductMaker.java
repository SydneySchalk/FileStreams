import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductMaker extends JFrame {

    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField idField;
    private JTextField costField;
    private JButton addButton;
    private JButton quitButton;

    private JLabel recordCountLabel; // New label to display "Record Count: " and the actual record count

    private RandomAccessFile randomAccessFile;

    private int recordCount;

    public RandProductMaker() {
        // Initialize GUI components and layout
        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField(40);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(6);

        JLabel costLabel = new JLabel("Cost:");
        costField = new JTextField(10);

        addButton = new JButton("Add Record");
        quitButton = new JButton("Quit");

        // Initialize the new label
        recordCountLabel = new JLabel("Record Count: 0");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });

        // Layout using GridBagLayout for better control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Name
        add(nameLabel, gbc);

        gbc.gridy = 1;
        add(nameField, gbc);

        // Description
        gbc.gridy = 2;
        add(descriptionLabel, gbc);

        gbc.gridy = 3;
        add(descriptionField, gbc);

        // ID
        gbc.gridy = 4;
        add(idLabel, gbc);

        gbc.gridy = 5;
        add(idField, gbc);

        // Cost
        gbc.gridy = 6;
        add(costLabel, gbc);

        gbc.gridy = 7;
        add(costField, gbc);

        // Record Count Label
        gbc.gridy = 8;
        gbc.gridx = 0;
        add(recordCountLabel, gbc);

        // Open the RandomAccessFile
        try {
            randomAccessFile = new RandomAccessFile("product.dat", "rw");
        } catch (IOException e) {
            // Handle file opening error
            JOptionPane.showMessageDialog(this, "Error opening the file.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Create a footer panel for buttons
        JPanel footerPanel = new JPanel();
        footerPanel.add(addButton);
        footerPanel.add(quitButton);

        // Add the footer panel to the frame
        gbc.gridy = 9;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Span two columns for the footer panel
        add(footerPanel, gbc);

        // Set up the frame
        setTitle("Random Product Maker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addProduct() {
        // Validate input fields
        if (!validateFields()) {
            // Display error message
            return;
        }

        try {
            // Read data from input fields
            String name = nameField.getText();
            String description = descriptionField.getText();
            String id = idField.getText();
            double cost = Double.parseDouble(costField.getText());

            // Create a Product object
            Product product = new Product(name, description, id, cost);

            // Write the product record to the random access file
            writeProductToRandomAccessFile(product);

            // Increment record count and update the display
            recordCount++;
            recordCountLabel.setText("Record Count: " + recordCount);

            // Clear input fields for the next record
            clearFields();

        } catch (NumberFormatException e) {
            // Handle parsing error for cost
            // Display error message
            JOptionPane.showMessageDialog(this, "Invalid cost value.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            // Handle file IO error
            // Display error message
            JOptionPane.showMessageDialog(this, "Error writing to the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void writeProductToRandomAccessFile(Product product) throws IOException {
        // Seek to the end of the file
        randomAccessFile.seek(randomAccessFile.length());

        // Write formatted product data to the random access file
        randomAccessFile.writeUTF(product.getFormattedID());
        randomAccessFile.writeUTF(product.getFormattedName());
        randomAccessFile.writeUTF(product.getFormattedDescription());
        randomAccessFile.writeDouble(product.getCost());
    }

    private boolean validateFields() {
        // Check if name is not empty
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if description is not empty
        String description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Description cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if ID is not empty and has the correct length
        String id = idField.getText().trim();
        if (id.isEmpty() || id.length() != 6) {
            JOptionPane.showMessageDialog(this, "ID must be 6 characters long.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if cost is a valid double
        try {
            Double.parseDouble(costField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid cost value.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // All fields are valid
        return true;
    }

    private void clearFields() {
        // Clear input fields
        nameField.setText("");
        descriptionField.setText("");
        idField.setText("");
        costField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductMaker());
    }
}
