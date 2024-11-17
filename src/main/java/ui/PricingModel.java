package ui;

import java.awt.BorderLayout;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Business.Business;
import model.Supplier.Supplier;
import model.ProductManagement.Product;
import model.ProductManagement.ProductSummary;
import model.OrderManagement.OrderItem;
import java.util.ArrayList;
import model.Business.ConfigureABusiness;

public class PricingModel extends JFrame {
    private JTable productTable;
    private JButton adjustPriceButton;
    private JButton runSimulationButton;
    private Business business;  // Assuming business object to get data from
    private DefaultTableModel tableModel;

    public PricingModel(Business business) {
        this.business = business;  // Initialize the business object

        // Initialize GUI components
        productTable = new JTable();
        adjustPriceButton = new JButton("Adjust Prices");
        runSimulationButton = new JButton("Run Simulation");

        // Set up table model
        tableModel = new DefaultTableModel(new String[]{"Product Name", "Target Price", "Actual Sales", "Adjusted Price", "Simulated Sales"}, 0);
        productTable.setModel(tableModel);

        // Add components to the frame
        this.setLayout(new BorderLayout());
        this.add(new JScrollPane(productTable), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(adjustPriceButton);
        buttonPanel.add(runSimulationButton);
        this.add(buttonPanel, BorderLayout.SOUTH);

        // Add ActionListeners
        adjustPriceButton.addActionListener(e -> adjustPrices());
        runSimulationButton.addActionListener(e -> runSimulation());

        // Populate the table with data
        populateProductTable();
    }

    private void populateProductTable() {
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0);
        
        for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            for (Product product : supplier.getProductCatalog().getProductList()) {
                model.addRow(new Object[]{
                    product.getProductName(),
                    supplier.getName(),
                    product.getSalesVolume(),
                    // ... other columns ...
                });
            }
        }
    }

    private void adjustPrices() {
        // Loop through all rows in the table and adjust prices based on sales performance
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String productName = (String) tableModel.getValueAt(row, 0);
            int targetPrice = (int) tableModel.getValueAt(row, 1);
            int actualSales = (int) tableModel.getValueAt(row, 2);
            int adjustedPrice = targetPrice;

            // Logic to adjust the target price based on sales performance
            if (actualSales < targetPrice) {
                // Decrease the target price if actual sales are below target
                adjustedPrice = targetPrice - 500;  // Example: Decrease by 500
            } else if (actualSales > targetPrice) {
                // Increase the target price if actual sales exceed target
                adjustedPrice = targetPrice + 500;  // Example: Increase by 500
            }

            // Update the table with adjusted price
            tableModel.setValueAt(adjustedPrice, row, 3);
        }
    }

    private void runSimulation() {
        // Run a simulation to calculate the impact of adjusted prices on sales performance
        for (int row = 0; row < tableModel.getRowCount(); row++) {
            String productName = (String) tableModel.getValueAt(row, 0);
            int adjustedPrice = (int) tableModel.getValueAt(row, 3);

            // Get the product by name from the supplier catalog (for simplicity, assume it's unique)
            Product product = findProductByName(productName);

            if (product != null) {
                // Simulate new sales volume (for simplicity, let's assume a 10% increase in sales for every price adjustment)
                int simulatedSalesVolume = simulateSalesImpact(product.getSalesVolume(), adjustedPrice);

                // Update the table with simulated sales volume and adjusted price
                tableModel.setValueAt(simulatedSalesVolume, row, 4);
            }
        }
    }

    private int simulateSalesImpact(int currentSalesVolume, int adjustedPrice) {
        // Simulate sales impact based on price adjustment
        double priceSensitivityFactor = 0.05; // Example: 5% change in sales for each price unit change
        int simulatedSalesVolume = (int) (currentSalesVolume * (1 + priceSensitivityFactor * (adjustedPrice - currentSalesVolume) / 100.0));
        return simulatedSalesVolume;
    }

    private Product findProductByName(String productName) {
        // Find the product by name from the catalog (Assumes that the product name is unique)
        for (Supplier supplier : business.getSupplierDirectory().getSuplierList()) {
            for (Product product : supplier.getProductCatalog().getProductList()) {
                if (product.getProductName().equals(productName)) {
                    return product;
                }
            }
        }
        return null;  // Return null if product not found
    }

    public static void main(String args[]) {
        try {
            Business business = ConfigureABusiness.initialize();
            
            SwingUtilities.invokeLater(() -> {
                new LoginScreen(business).setVisible(true);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
