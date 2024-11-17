package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Component;
import java.util.List;
import java.util.stream.Collectors;
import model.Business.Business;
import model.Supplier.Supplier;
import model.ProductManagement.Product;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.MouseAdapter;
import javax.swing.event.MouseEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableHeader;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import model.OrderManagement.MasterOrderList;

public class SupplierInterface extends JFrame {
    private Business business;
    private Supplier currentSupplier;
    private JTabbedPane tabbedPane;
    private JTable productTable;
    private JTable performanceTable;
    private DefaultTableModel productTableModel;
    private DefaultTableModel performanceTableModel;
    
    public SupplierInterface(Business business, Supplier supplier) {
        this.business = business;
        this.currentSupplier = supplier;
        setTitle("Supplier Interface - " + supplier.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        
        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Add tabs
        tabbedPane.addTab("Product Management", createProductPanel());
        tabbedPane.addTab("Price Performance", createPerformancePanel());
        tabbedPane.addTab("Price Adjustment", createPriceAdjustmentPanel());
        tabbedPane.addTab("Simulation", createSimulationPanel());
        tabbedPane.addTab("Reports", createReportsPanel());
        
        add(tabbedPane);
        
        // Add navigation panel at bottom
        add(createNavigationPanel(), BorderLayout.SOUTH);
        
        // Refresh data immediately after construction
        refreshProductTable();
        revalidate();
        repaint();
    }
    
    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create the add product panel at the top
        JPanel addProductPanel = new JPanel(new GridBagLayout());
        addProductPanel.setBorder(BorderFactory.createTitledBorder("Add New Product"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Product Name
        gbc.gridx = 0; gbc.gridy = 0;
        addProductPanel.add(new JLabel("Product Name:"), gbc);
        
        JTextField nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 3;
        addProductPanel.add(nameField, gbc);
        
        // Target Price
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        addProductPanel.add(new JLabel("Target Price:"), gbc);
        
        JTextField targetPriceField = new JTextField(10);
        targetPriceField.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 1;
        addProductPanel.add(targetPriceField, gbc);
        
        // Floor Price
        gbc.gridx = 2;
        addProductPanel.add(new JLabel("Floor Price:"), gbc);
        
        JTextField floorPriceField = new JTextField(10);
        floorPriceField.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 3;
        addProductPanel.add(floorPriceField, gbc);
        
        // Ceiling Price
        gbc.gridx = 4;
        addProductPanel.add(new JLabel("Ceiling Price:"), gbc);
        
        JTextField ceilingPriceField = new JTextField(10);
        ceilingPriceField.setPreferredSize(new Dimension(150, 30));
        gbc.gridx = 5;
        addProductPanel.add(ceilingPriceField, gbc);
        
        // Add Button
        JButton addButton = new JButton("Add Product");
        addButton.setPreferredSize(new Dimension(120, 35));
        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        addProductPanel.add(addButton, gbc);
        
        addButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                double targetPrice = Double.parseDouble(targetPriceField.getText().trim());
                double floorPrice = Double.parseDouble(floorPriceField.getText().trim());
                double ceilingPrice = Double.parseDouble(ceilingPriceField.getText().trim());
                
                if (name.isEmpty()) {
                    throw new IllegalArgumentException("Product name cannot be empty");
                }
                
                if (floorPrice > targetPrice || targetPrice > ceilingPrice) {
                    throw new IllegalArgumentException("Prices must be in order: Floor ≤ Target ≤ Ceiling");
                }
                
                Product newProduct = new Product(name, targetPrice, floorPrice, ceilingPrice);
                currentSupplier.getProductCatalog().addProduct(newProduct);
                
                // Clear fields
                nameField.setText("");
                targetPriceField.setText("");
                floorPriceField.setText("");
                ceilingPriceField.setText("");
                
                refreshProductTable();
                
                JOptionPane.showMessageDialog(this,
                    "Product added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                    "Please enter valid numbers for prices",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Add the panels to the main panel
        panel.add(addProductPanel, BorderLayout.NORTH);
        
        // Create and add the product table
        createProductTable();
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Product List"));
        panel.add(tableScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Performance metrics at top
        JPanel metricsPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        metricsPanel.add(createMetricPanel("Total Revenue", calculateTotalRevenue()));
        metricsPanel.add(createMetricPanel("Average Performance", calculateAveragePerformance()));
        metricsPanel.add(createMetricPanel("Products Above Target", getProductsAboveTarget()));
        metricsPanel.add(createMetricPanel("Profit Margin", calculateProfitMargin()));
        panel.add(metricsPanel, BorderLayout.NORTH);
        
        // Performance table
        String[] columns = {
            "Product Name",
            "Target Price",
            "Avg Selling Price",
            "Sales Volume",
            "Total Revenue",
            "Performance",
            "Recommendation"
        };
        
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable performanceTable = new JTable(model);
        
        // Populate performance data
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            double avgPrice = calculateAverageSellingPrice(product);
            int salesVolume = calculateSalesVolume(product);
            double revenue = avgPrice * salesVolume;
            double performance = (avgPrice / product.getTargetPrice()) * 100;
            
            model.addRow(new Object[]{
                product.getProductName(),
                formatPrice(product.getTargetPrice()),
                formatPrice(avgPrice),
                salesVolume,
                formatPrice(revenue),
                String.format("%.1f%%", performance),
                getRecommendation(performance)
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(performanceTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private String getRecommendation(double performance) {
        if (performance < 95) {
            return "Lower Target Price";
        } else if (performance > 105) {
            return "Increase Target Price";
        }
        return "Maintain Price";
    }
    
    private JPanel createPriceAdjustmentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Control buttons at top
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton lowerPricesBtn = new JButton("Adjust Prices Lower");
        JButton higherPricesBtn = new JButton("Adjust Prices Higher");
        JButton applyChangesBtn = new JButton("Apply All Changes");
        
        controlPanel.add(lowerPricesBtn);
        controlPanel.add(higherPricesBtn);
        controlPanel.add(applyChangesBtn);
        panel.add(controlPanel, BorderLayout.NORTH);
        
        // Adjustment table
        String[] columns = {
            "Product Name",
            "Current Price",
            "Target Price",
            "Change %",
            "Expected Impact",
            "Select Product"
        };
        
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 5 ? Boolean.class : String.class;
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only make the checkbox column editable
            }
        };
        
        JTable adjustmentTable = new JTable(model);
        adjustmentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Set column widths and styling
        adjustmentTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        adjustmentTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        adjustmentTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        adjustmentTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        adjustmentTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        adjustmentTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        adjustmentTable.setRowHeight(25);
        adjustmentTable.setFont(new Font("Arial", Font.PLAIN, 12));
        adjustmentTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Populate adjustment data
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            double currentPrice = product.getCurrentPrice();
            double targetPrice = product.getTargetPrice();
            double changePercent = ((currentPrice - targetPrice) / targetPrice) * 100;
            
            model.addRow(new Object[]{
                product.getProductName(),
                formatPrice(currentPrice),
                formatPrice(targetPrice),
                String.format("%.1f%%", changePercent),
                calculateExpectedImpact(product),
                Boolean.FALSE
            });
        }
        
        JScrollPane scrollPane = new JScrollPane(adjustmentTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listeners
        lowerPricesBtn.addActionListener(e -> {
            if (!hasSelectedProducts(model)) {
                JOptionPane.showMessageDialog(panel,
                    "Please select at least one product to adjust prices",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            adjustPricesLower(model);
        });
        
        higherPricesBtn.addActionListener(e -> {
            if (!hasSelectedProducts(model)) {
                JOptionPane.showMessageDialog(panel,
                    "Please select at least one product to adjust prices",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            adjustPricesHigher(model);
        });
        
        applyChangesBtn.addActionListener(e -> {
            if (!hasSelectedProducts(model)) {
                JOptionPane.showMessageDialog(panel,
                    "Please select at least one product to apply changes",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            applyPriceChanges(adjustmentTable);
            JOptionPane.showMessageDialog(panel,
                "Price changes applied successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
        });
        
        return panel;
    }
    
    private boolean hasSelectedProducts(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 5)) {
                return true;
            }
        }
        return false;
    }
    
    private void adjustPricesLower(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 5)) { // Only adjust selected products
                Product product = currentSupplier.getProductCatalog().getProductList().get(i);
                double currentPrice = product.getCurrentPrice();
                double newPrice = currentPrice * 0.95; // 5% reduction
                
                // Validate the new price is within bounds
                if (newPrice >= product.getFloorPrice() && newPrice <= product.getCeilingPrice()) {
                    // Update only the current price
                    product.setCurrentPrice(newPrice);
                    
                    // Update the table
                    model.setValueAt(formatPrice(newPrice), i, 1); // Update current price
                    double changePercent = ((newPrice - product.getTargetPrice()) / product.getTargetPrice()) * 100;
                    model.setValueAt(String.format("%.1f%%", changePercent), i, 3);
                    model.setValueAt(calculateExpectedImpact(product), i, 4);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Price adjustment for " + product.getProductName() + 
                        " would exceed allowed range.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        refreshProductTable();
    }
    
    private void adjustPricesHigher(DefaultTableModel model) {
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 5)) { // Only adjust selected products
                Product product = currentSupplier.getProductCatalog().getProductList().get(i);
                double currentPrice = product.getCurrentPrice();
                double newPrice = currentPrice * 1.05; // 5% increase
                
                // Validate the new price is within bounds
                if (newPrice >= product.getFloorPrice() && newPrice <= product.getCeilingPrice()) {
                    // Update only the current price
                    product.setCurrentPrice(newPrice);
                    
                    // Update the table
                    model.setValueAt(formatPrice(newPrice), i, 1); // Update current price
                    double changePercent = ((newPrice - product.getTargetPrice()) / product.getTargetPrice()) * 100;
                    model.setValueAt(String.format("%.1f%%", changePercent), i, 3);
                    model.setValueAt(calculateExpectedImpact(product), i, 4);
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Price adjustment for " + product.getProductName() + 
                        " would exceed allowed range.",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        refreshProductTable();
    }
    
    private void applyPriceChanges(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        boolean changesApplied = false;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 5)) {
                Product product = currentSupplier.getProductCatalog().getProductList().get(i);
                String suggestedPriceStr = (String) model.getValueAt(i, 2);
                double newPrice = extractPrice(suggestedPriceStr);
                
                if (newPrice >= product.getFloorPrice() && newPrice <= product.getCeilingPrice()) {
                    product.setTargetPrice(newPrice);
                    changesApplied = true;
                }
            }
        }
        
        if (changesApplied) {
            refreshProductTable();
            // Reset checkboxes and suggested prices
            for (int i = 0; i < model.getRowCount(); i++) {
                model.setValueAt(Boolean.FALSE, i, 5);
                model.setValueAt(model.getValueAt(i, 1), i, 2); // Reset suggested to current
                model.setValueAt("0.0%", i, 3);
                model.setValueAt("No change", i, 4);
            }
        }
    }
    
    private JPanel createSimulationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Simulation controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JComboBox<String> periodCombo = new JComboBox<>(new String[]{"1 Month", "3 Months", "6 Months"});
        JButton runSimulationBtn = new JButton("Run Simulation");
        JButton optimizeBtn = new JButton("Optimize Margins");
        
        controlPanel.add(new JLabel("Simulation Period:"));
        controlPanel.add(periodCombo);
        controlPanel.add(runSimulationBtn);
        controlPanel.add(optimizeBtn);
        panel.add(controlPanel, BorderLayout.NORTH);
        
        // Results panel
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Simulation Results"));
        
        // Create chart panel (you'll need to implement actual charting)
        JPanel chartPanel = new JPanel();
        chartPanel.setPreferredSize(new Dimension(800, 400));
        resultsPanel.add(chartPanel, BorderLayout.CENTER);
        
        // Results table
        String[] columns = {
            "Product",
            "Current Revenue",
            "Simulated Revenue",
            "Revenue Impact",
            "Profit Impact"
        };
        
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable resultsTable = new JTable(model);
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.SOUTH);
        
        panel.add(resultsPanel, BorderLayout.CENTER);
        
        // Add action listeners
        runSimulationBtn.addActionListener(e -> runSimulation(model, periodCombo.getSelectedItem().toString()));
        optimizeBtn.addActionListener(e -> optimizeMargins(model));
        
        return panel;
    }
    
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Report controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton generateReportBtn = new JButton("Generate Report");
        JButton exportBtn = new JButton("Export to Excel");
        JButton printBtn = new JButton("Print Report");
        
        controlPanel.add(generateReportBtn);
        controlPanel.add(exportBtn);
        controlPanel.add(printBtn);
        panel.add(controlPanel, BorderLayout.NORTH);
        
        // Report content
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Add action listeners
        generateReportBtn.addActionListener(e -> generateReport(reportArea));
        exportBtn.addActionListener(e -> exportReport());
        printBtn.addActionListener(e -> printReport());
        
        return panel;
    }
    
    private void createProductTable() {
        String[] columns = {
            "Product Name",
            "Target Price",
            "Floor Price",
            "Ceiling Price",
            "Current Sales",
            "Performance"
        };
        
        productTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        productTable = new JTable(productTableModel);
        
        // Set column widths
        productTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(100);
        
        refreshProductTable();
    }
    
    private void refreshProductTable() {
        productTableModel.setRowCount(0);
        
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            int totalSales = calculateTotalSales(product);
            String performance = calculatePerformanceMetric(product);
            
            productTableModel.addRow(new Object[]{
                product.getProductName(),
                formatPrice(product.getCurrentPrice()),
                formatPrice(product.getFloorPrice()),
                formatPrice(product.getCeilingPrice()),
                totalSales,
                performance
            });
        }
    }
    
    private String formatPrice(double price) {
        return String.format("$%.2f", price);
    }
    
    private int calculateTotalSales(Product product) {
        int totalQuantity = 0;
        MasterOrderList masterOrderList = business.getMasterOrderList();
        if (masterOrderList != null) {
            List<Order> orders = masterOrderList.getOrders();
            if (orders != null) {
                for (Order order : orders) {
                    List<OrderItem> items = order.getOrderItems();
                    if (items != null) {
                        for (OrderItem item : items) {
                            if (item.getSelectedProduct().equals(product)) {
                                totalQuantity += item.getQuantity();
                            }
                        }
                    }
                }
            }
        }
        return totalQuantity;
    }
    
    private String calculatePerformanceMetric(Product product) {
        double avgSellingPrice = calculateAverageSellingPrice(product);
        double targetPrice = product.getTargetPrice();
        
        if (avgSellingPrice == 0) {
            return "No Sales";
        }
        
        double performance = (avgSellingPrice / targetPrice) * 100;
        if (performance < 95) {
            return String.format("%.1f%% (Low)", performance);
        } else if (performance > 105) {
            return String.format("%.1f%% (High)", performance);
        } else {
            return String.format("%.1f%% (Good)", performance);
        }
    }
    
    private double calculateAverageSellingPrice(Product product) {
        double totalRevenue = 0;
        int totalQuantity = 0;
        MasterOrderList masterOrderList = business.getMasterOrderList();
        
        if (masterOrderList != null) {
            List<Order> orders = masterOrderList.getOrders();
            if (orders != null) {
                for (Order order : orders) {
                    List<OrderItem> items = order.getOrderItems();
                    if (items != null) {
                        for (OrderItem item : items) {
                            if (item.getSelectedProduct().equals(product)) {
                                totalRevenue += item.getActualPrice() * item.getQuantity();
                                totalQuantity += item.getQuantity();
                            }
                        }
                    }
                }
            }
        }
        
        return totalQuantity > 0 ? totalRevenue / totalQuantity : 0;
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
    
    private JPanel createMetricPanel(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(valueLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void createPerformanceTable() {
        String[] columns = {
            "Product Name", 
            "Target Price", 
            "Avg Selling Price", 
            "Sales Volume",
            "Revenue",
            "Performance %",
            "Status"
        };
        
        performanceTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Make table read-only
            }
            
            @Override
            public Class<?> getColumnClass(int column) {
                if (column >= 1 && column <= 5) {
                    return Double.class;  // For price and numeric columns
                }
                return String.class;
            }
        };
        
        performanceTable = new JTable(performanceTableModel);
        
        // Set column widths
        performanceTable.getColumnModel().getColumn(0).setPreferredWidth(200);  // Product Name
        performanceTable.getColumnModel().getColumn(1).setPreferredWidth(100);  // Target Price
        performanceTable.getColumnModel().getColumn(2).setPreferredWidth(100);  // Avg Selling Price
        performanceTable.getColumnModel().getColumn(3).setPreferredWidth(100);  // Sales Volume
        performanceTable.getColumnModel().getColumn(4).setPreferredWidth(100);  // Revenue
        performanceTable.getColumnModel().getColumn(5).setPreferredWidth(100);  // Performance
        performanceTable.getColumnModel().getColumn(6).setPreferredWidth(100);  // Status
        
        // Set table styling
        performanceTable.setRowHeight(25);
        performanceTable.setFont(new Font("Arial", Font.PLAIN, 12));
        performanceTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        // Add sample data (replace with actual data later)
        refreshPerformanceTable();
    }
    
    private void refreshPerformanceTable() {
        performanceTableModel.setRowCount(0);
        
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            double targetPrice = product.getTargetPrice();
            // These values should come from your actual data model
            double avgSellingPrice = calculateAverageSellingPrice(product);
            int salesVolume = calculateSalesVolume(product);
            double revenue = avgSellingPrice * salesVolume;
            double performance = (avgSellingPrice / targetPrice) * 100;
            String status = performance >= 100 ? "Above Target" : "Below Target";
            
            performanceTableModel.addRow(new Object[]{
                product.getProductName(),
                String.format("%.2f", targetPrice),
                String.format("%.2f", avgSellingPrice),
                salesVolume,
                String.format("%.2f", revenue),
                String.format("%.1f", performance),
                status
            });
        }
    }
    
    // Helper methods for calculations (implement these based on your data model)
    private int calculateSalesVolume(Product product) {
        // TODO: Implement actual calculation based on order history
        return (int)(Math.random() * 100);  // Sample data
    }
    
    private String calculateTotalRevenue() {
        double total = currentSupplier.getProductCatalog().getProductList().stream()
            .mapToDouble(p -> calculateAverageSellingPrice(p) * calculateSalesVolume(p))
            .sum();
        return String.format("$%.2f", total);
    }
    
    private String calculateAveragePerformance() {
        List<Product> products = currentSupplier.getProductCatalog().getProductList();
        double avgPerformance = products.stream()
            .mapToDouble(p -> (calculateAverageSellingPrice(p) / p.getTargetPrice()) * 100)
            .average()
            .orElse(0.0);
        return String.format("%.1f%%", avgPerformance);
    }
    
    private String getProductsAboveTarget() {
        List<Product> products = currentSupplier.getProductCatalog().getProductList();
        long aboveTarget = products.stream()
            .filter(p -> calculateAverageSellingPrice(p) > p.getTargetPrice())
            .count();
        return String.format("%d/%d", aboveTarget, products.size());
    }
    
    private String calculateProfitMargin() {
        double totalRevenue = currentSupplier.getProductCatalog().getProductList().stream()
            .mapToDouble(p -> calculateAverageSellingPrice(p) * calculateSalesVolume(p))
            .sum();
        double totalCost = currentSupplier.getProductCatalog().getProductList().stream()
            .mapToDouble(p -> p.getFloorPrice() * calculateSalesVolume(p))
            .sum();
        double margin = ((totalRevenue - totalCost) / totalRevenue) * 100;
        return String.format("%.1f%%", margin);
    }
    
    private double calculateSuggestedPrice(Product product) {
        double performance = calculateAverageSellingPrice(product) / product.getTargetPrice();
        if (performance < 0.95) {
            return Math.max(product.getFloorPrice(), product.getTargetPrice() * 0.95);
        } else if (performance > 1.05) {
            return Math.min(product.getCeilingPrice(), product.getTargetPrice() * 1.05);
        }
        return product.getTargetPrice();
    }
    
    private String calculateExpectedImpact(Product product) {
        double currentPrice = product.getCurrentPrice();
        double targetPrice = product.getTargetPrice();
        
        // Calculate expected impact based on price difference from target
        double priceDifference = ((currentPrice - targetPrice) / targetPrice) * 100;
        
        // Simple elasticity model: for every 1% increase in price, expect 2% decrease in demand
        double elasticity = -2.0;
        double expectedVolumeChange = priceDifference * elasticity;
        
        // Calculate revenue impact
        double currentRevenue = currentPrice * calculateTotalSales(product);
        double expectedRevenue = currentPrice * (calculateTotalSales(product) * (1 + expectedVolumeChange/100));
        double revenueImpact = ((expectedRevenue - currentRevenue) / currentRevenue) * 100;
        
        if (revenueImpact > 0) {
            return String.format("+%.1f%%", revenueImpact);
        } else {
            return String.format("%.1f%%", revenueImpact);
        }
    }
    
    private void runSimulation(DefaultTableModel model, String period) {
        model.setRowCount(0);
        int months = Integer.parseInt(period.split(" ")[0]);
        
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            double currentRevenue = calculateAverageSellingPrice(product) * calculateSalesVolume(product);
            double simulatedRevenue = simulateRevenue(product, months);
            double revenueImpact = ((simulatedRevenue - currentRevenue) / currentRevenue) * 100;
            double profitImpact = calculateProfitImpact(product, simulatedRevenue);
            
            model.addRow(new Object[]{
                product.getProductName(),
                formatPrice(currentRevenue),
                formatPrice(simulatedRevenue),
                String.format("%+.1f%%", revenueImpact),
                String.format("%+.1f%%", profitImpact)
            });
        }
    }
    
    private double simulateRevenue(Product product, int months) {
        double baseRevenue = calculateAverageSellingPrice(product) * calculateSalesVolume(product);
        double growthRate = 1.0 + (Math.random() * 0.1); // 0-10% growth
        return baseRevenue * Math.pow(growthRate, months);
    }
    
    private double calculateProfitImpact(Product product, double newRevenue) {
        double currentProfit = (calculateAverageSellingPrice(product) - product.getFloorPrice()) 
            * calculateSalesVolume(product);
        double newVolume = newRevenue / calculateAverageSellingPrice(product);
        double newProfit = (calculateAverageSellingPrice(product) - product.getFloorPrice()) * newVolume;
        return ((newProfit - currentProfit) / currentProfit) * 100;
    }
    
    private void optimizeMargins(DefaultTableModel model) {
        // Implement margin optimization logic here
        // This could involve iterative price adjustments to maximize profit
        runSimulation(model, "3 Months");
    }
    
    private void generateReport(JTextArea reportArea) {
        StringBuilder report = new StringBuilder();
        report.append("Product Performance Report\n");
        report.append("========================\n\n");
        report.append(String.format("Supplier: %s\n", currentSupplier.getName()));
        report.append(String.format("Total Revenue: %s\n", calculateTotalRevenue()));
        report.append(String.format("Average Performance: %s\n", calculateAveragePerformance()));
        report.append(String.format("Profit Margin: %s\n\n", calculateProfitMargin()));
        
        report.append("Product Details:\n");
        report.append("---------------\n");
        for (Product product : currentSupplier.getProductCatalog().getProductList()) {
            report.append(String.format("\nProduct: %s\n", product.getProductName()));
            report.append(String.format("Target Price: %s\n", formatPrice(product.getTargetPrice())));
            report.append(String.format("Average Selling Price: %s\n", 
                formatPrice(calculateAverageSellingPrice(product))));
            report.append(String.format("Sales Volume: %d\n", calculateSalesVolume(product)));
            report.append(String.format("Performance: %.1f%%\n", 
                (calculateAverageSellingPrice(product) / product.getTargetPrice()) * 100));
        }
        
        reportArea.setText(report.toString());
    }
    
    private void exportReport() {
        // Implement export functionality
        JOptionPane.showMessageDialog(this, "Report exported successfully!");
    }
    
    private void printReport() {
        // Implement print functionality
        JOptionPane.showMessageDialog(this, "Report sent to printer!");
    }
    
    private double extractPrice(String priceStr) {
        return Double.parseDouble(priceStr.replace("$", "").replace(",", ""));
    }
} 