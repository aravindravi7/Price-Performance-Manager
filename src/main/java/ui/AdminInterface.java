package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import model.Business.Business;
import model.Supplier.Supplier;

public class AdminInterface extends JFrame {
    private Business business;
    private JTable supplierTable;
    private DefaultTableModel tableModel;
    
    public AdminInterface(Business business) {
        this.business = business;
        setTitle("Admin Interface - Supplier Management");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add Supplier Panel
        JPanel addSupplierPanel = createAddSupplierPanel();
        mainPanel.add(addSupplierPanel, BorderLayout.NORTH);
        
        // Supplier Table
        createSupplierTable();
        JScrollPane tableScrollPane = new JScrollPane(supplierTable);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        // Navigation Panel (now only with logout)
        JPanel navPanel = createNavigationPanel();
        mainPanel.add(navPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        refreshSupplierTable();
    }
    
    private JPanel createAddSupplierPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("Add New Supplier"));
        
        JTextField nameField = new JTextField(20);
        JButton addButton = new JButton("Add Supplier");
        
        addButton.addActionListener(e -> {
            String supplierName = nameField.getText().trim();
            if (!supplierName.isEmpty()) {
                business.getSupplierDirectory().newSupplier(supplierName);
                nameField.setText("");
                refreshSupplierTable();
                JOptionPane.showMessageDialog(this, 
                    "Supplier added successfully: " + supplierName,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Please enter a supplier name",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
            }
        });
        
        panel.add(new JLabel("Supplier Name:"));
        panel.add(nameField);
        panel.add(addButton);
        
        return panel;
    }
    
    private void createSupplierTable() {
        String[] columnNames = {"Supplier Name", "Number of Products"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        supplierTable = new JTable(tableModel);
    }
    
    private void refreshSupplierTable() {
        tableModel.setRowCount(0);
        for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            tableModel.addRow(new Object[]{
                supplier.getName(),
                supplier.getProductCatalog().getProductList().size()
            });
        }
    }
    
    private JPanel createNavigationPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            this.dispose();
            new LoginScreen(business).setVisible(true);
        });
        
        panel.add(logoutButton);
        
        return panel;
    }
} 