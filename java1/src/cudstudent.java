import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Vector;

public class cudstudent extends JFrame {
    private JTextField idField, firstNameField, lastNameField, majorField, emailField;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton createButton, deleteButton, updateButton, clearButton;

    public cudstudent() {
        setTitle("CUD Student");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(240, 240, 245));

        // Left panel for input fields
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(50, 115, 220), 2),
                "Student Information",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                new Color(50, 115, 220)
        ));
        inputPanel.setBackground(new Color(250, 250, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        addLabelAndField(inputPanel, gbc, "ID:", idField = createStyledTextField());
        addLabelAndField(inputPanel, gbc, "First Name:", firstNameField = createStyledTextField());
        addLabelAndField(inputPanel, gbc, "Last Name:", lastNameField = createStyledTextField());
        addLabelAndField(inputPanel, gbc, "Major:", majorField = createStyledTextField());
        addLabelAndField(inputPanel, gbc, "Email:", emailField = createStyledTextField());

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        createButton = createStyledButton("Create", Color.blue);
        deleteButton = createStyledButton("Delete", Color.RED);
        updateButton = createStyledButton("Update", Color.ORANGE);
        clearButton = createStyledButton("Clear", Color.GRAY);

        buttonPanel.add(createButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        inputPanel.add(buttonPanel, gbc);

        add(inputPanel, BorderLayout.WEST);

        // Right panel for student table
        String[] columnNames = {"ID", "First Name", "Last Name", "Major", "Email"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        studentTable = new JTable(tableModel);
        styleTable();

        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // Add some sample data
        addSampleData();

        // Add action listeners
        addActionListeners();

        // Set up table selection listener
        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    populateFields(selectedRow);
                }
            }
        });
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                field.getBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setToolTipText("Enter " + field.getName());
        return field;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    private void addLabelAndField(JPanel panel, GridBagConstraints gbc, String labelText, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy++;
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label, gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void styleTable() {
        studentTable.setFont(new Font("Arial", Font.PLAIN, 14));
        studentTable.setRowHeight(25);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        studentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        studentTable.getTableHeader().setBackground(new Color(50, 115, 220));
        studentTable.getTableHeader().setForeground(Color.WHITE);
        studentTable.setShowGrid(true);
        studentTable.setGridColor(new Color(230, 230, 230));
        studentTable.setSelectionBackground(new Color(220, 240, 255));
    }

    private void addSampleData() {
        tableModel.addRow(new Object[]{"1", "Alice", "Johnson", "Engineering", "alice@example.com"});
        tableModel.addRow(new Object[]{"2", "Bob", "Smith", "Mathematics", "bob@example.com"});
    }

    private void populateFields(int row) {
        idField.setText(tableModel.getValueAt(row, 0).toString());
        firstNameField.setText(tableModel.getValueAt(row, 1).toString());
        lastNameField.setText(tableModel.getValueAt(row, 2).toString());
        majorField.setText(tableModel.getValueAt(row, 3).toString());
        emailField.setText(tableModel.getValueAt(row, 4).toString());
    }

    private void clearFields() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        majorField.setText("");
        emailField.setText("");
        studentTable.clearSelection();
    }

    private void addActionListeners() {
        createButton.addActionListener(e -> {
            if (validateInputs()) {
                Vector<Object> row = new Vector<>();
                row.add(idField.getText());
                row.add(firstNameField.getText());
                row.add(lastNameField.getText());
                row.add(majorField.getText());
                row.add(emailField.getText());
                tableModel.addRow(row);
                clearFields();
                JOptionPane.showMessageDialog(this, "Student created successfully.");
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                clearFields();
                JOptionPane.showMessageDialog(this, "Student deleted successfully.");
            }
        });

        updateButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow != -1 && validateInputs()) {
                tableModel.setValueAt(idField.getText(), selectedRow, 0);
                tableModel.setValueAt(firstNameField.getText(), selectedRow, 1);
                tableModel.setValueAt(lastNameField.getText(), selectedRow, 2);
                tableModel.setValueAt(majorField.getText(), selectedRow, 3);
                tableModel.setValueAt(emailField.getText(), selectedRow, 4);
                JOptionPane.showMessageDialog(this, "Student updated successfully.");
            }
        });

        clearButton.addActionListener(e -> clearFields());
    }

    private boolean validateInputs() {
        // Simple validation check for empty fields
        if (idField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() || majorField.getText().isEmpty() ||
                emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            cudstudent frame = new cudstudent();
            frame.setVisible(true);
        });
    }
}
