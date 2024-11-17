package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.UserAccountManagement.Role;
import model.Supplier.Supplier;
import model.Business.Business;

public class LoginScreen extends JFrame {
    private JComboBox<Role> roleComboBox;
    private JComboBox<Supplier> supplierComboBox;
    private JPanel supplierPanel;
    private Business business;
    
    public LoginScreen(Business business) {
        this.business = business;
        setTitle("System Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
        
        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Role selection
        gbc.gridx = 0; gbc.gridy = 0;
        mainPanel.add(new JLabel("Select Role:"), gbc);
        
        gbc.gridx = 1;
        roleComboBox = new JComboBox<>(new Role[]{Role.ADMIN, Role.SUPPLIER});
        roleComboBox.addActionListener(e -> handleRoleSelection());
        mainPanel.add(roleComboBox, gbc);
        
        // Supplier selection panel
        supplierPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        
        supplierComboBox = new JComboBox<>();
        supplierPanel.add(new JLabel("Select Supplier:"));
        supplierPanel.add(supplierComboBox);
        supplierPanel.setVisible(false);
        
        mainPanel.add(supplierPanel, gbc);
        
        // Login button
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton, gbc);
        
        add(mainPanel);
        pack();
    }
    
    private void handleRoleSelection() {
        Role selectedRole = (Role) roleComboBox.getSelectedItem();
        if (selectedRole == Role.SUPPLIER) {
            // Clear and repopulate supplier dropdown
            supplierComboBox.removeAllItems();
            for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
                supplierComboBox.addItem(supplier);
            }
            supplierPanel.setVisible(true);
        } else {
            supplierPanel.setVisible(false);
        }
        pack();
    }
    
    private void handleLogin() {
        Role selectedRole = (Role) roleComboBox.getSelectedItem();
        
        try {
            switch (selectedRole) {
                case ADMIN:
                    this.dispose();
                    new AdminInterface(business).setVisible(true);
                    break;
                    
                case SUPPLIER:
                    if (supplierComboBox.getSelectedItem() != null) {
                        Supplier selectedSupplier = (Supplier) supplierComboBox.getSelectedItem();
                        System.out.println("Selected supplier: " + selectedSupplier.getName());
                        System.out.println("Number of products: " + 
                            selectedSupplier.getProductCatalog().getProductList().size());
                        
                        this.dispose();
                        SwingUtilities.invokeLater(() -> {
                            SupplierInterface supplierInterface = new SupplierInterface(business, selectedSupplier);
                            supplierInterface.setVisible(true);
                        });
                    } else {
                        JOptionPane.showMessageDialog(this,
                            "Please select a supplier",
                            "Selection Required",
                            JOptionPane.WARNING_MESSAGE);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error opening interface: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
} 