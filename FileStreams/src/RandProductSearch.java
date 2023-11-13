import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {

    private JTextField searchField;
    private JTextArea resultArea;

    public RandProductSearch() {
        // Initialize GUI components and layout
        JLabel searchLabel = new JLabel("Enter partial product name:");
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);

        // Layout using GridBagLayout for better control
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(searchLabel, gbc);

        gbc.gridy = 1;
        add(searchField, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(searchButton, gbc);

        // Make the JScrollPane span columns 0 and 1
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Set gridwidth to 2 to span two columns
        add(new JScrollPane(resultArea), gbc);

        // Add action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // Create a footer panel for buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Exit the application
            }
        });
        footerPanel.add(quitButton);

// Add the footer panel to the frame using BorderLayout
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(footerPanel, gbc);


        // Set up the frame
        setTitle("Random Product Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    // Inside RandProductSearch class
    private void performSearch() {
        String partialName = searchField.getText().trim().toLowerCase();

        if (partialName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a partial product name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (RandomAccessFile randomAccessFile = new RandomAccessFile("C:\\Users\\tadpo\\IdeaProjects\\FileStreams\\product.dat", "r")) {
            resultArea.setText(""); // Clear previous results

            long fileLength = randomAccessFile.length();
            long currentPosition = 0;

            while (currentPosition < fileLength) {
                // Read formatted data from the random access file
                String formattedID = randomAccessFile.readUTF();
                String formattedName = randomAccessFile.readUTF();
                String formattedDescription = randomAccessFile.readUTF();
                double cost = randomAccessFile.readDouble();

                // Create a temporary Product object to get the product name without formatting
                Product tempProduct = new Product("", "", "", 0.0);
                tempProduct.setName(formattedName);
                String productName = tempProduct.getProductNameWithoutFormatting();

                // Check if the product name contains the partial name (case-insensitive)
                if (productName.toLowerCase().contains(partialName)) {
                    resultArea.append(formattedName + "\n");
                }

                // Move the current position to the next record
                currentPosition = randomAccessFile.getFilePointer();
            }

            if (resultArea.getText().isEmpty()) {
                resultArea.setText("No matching products found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RandProductSearch());
    }
}
