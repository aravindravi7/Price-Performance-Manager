public class CustomerWorkArea extends WorkAreaPanel {
    private Customer customer;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton placeOrderButton;
    
    public CustomerWorkArea(PricingModelFrame frame, Business business) {
        super(frame, business);
    }
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        
        // Create table model
        String[] columns = {"Product ID", "Name", "Price", "Supplier"};
        tableModel = new DefaultTableModel(columns, 0);
        productTable = new JTable(tableModel);
        
        placeOrderButton = new JButton("Place Order");
        placeOrderButton.addActionListener(e -> placeOrder());
        
        // Add components
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(placeOrderButton, BorderLayout.SOUTH);
        
        refreshProductTable();
    }
    
    private void refreshProductTable() {
        tableModel.setRowCount(0);
        for (Supplier supplier : business.getSupplierDirectory().getSupplierList()) {
            ProductCatalog catalog = supplier.getProductCatalog();
            for (Product product : catalog.getProductList()) {
                Object[] row = {
                    product.getProductId(),
                    product.getProductName(),
                    product.getPriceProfile().getActualPrice(),
                    supplier.getName()
                };
                tableModel.addRow(row);
            }
        }
    }
    
    private void placeOrder() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Create new order using Order class
            Order order = new Order();
            // Set order details
            
            // Add to customer's order list
            customer.getOrderList().add(order);
            
            JOptionPane.showMessageDialog(this, "Order placed successfully!");
        }
    }
} 