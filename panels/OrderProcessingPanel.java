public class OrderProcessingPanel extends JPanel {
    private Business business;
    private JTable orderTable;
    private JPanel orderDetailsPanel;
    private Order selectedOrder;
    
    public OrderProcessingPanel(Business business) {
        this.business = business;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Order Status Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{
            "All Orders", "Pending", "Processing", "Completed", "Cancelled"
        });
        
        filterPanel.add(new JLabel("Filter by Status:"));
        filterPanel.add(statusFilter);
        
        // Orders Table
        String[] columns = {
            "Order ID", "Customer", "Date", "Items", "Total", "Status"
        };
        orderTable = new JTable(new DefaultTableModel(columns, 0));
        UIUtils.styleTable(orderTable);
        
        // Order Details Panel
        orderDetailsPanel = new JPanel(new MigLayout("fillx, insets 20"));
        orderDetailsPanel.setBackground(Color.WHITE);
        orderDetailsPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        
        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(orderTable),
            orderDetailsPanel
        );
        splitPane.setResizeWeight(0.6);
        
        // Layout
        add(filterPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        
        // Add listeners
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = orderTable.getSelectedRow();
                if (row >= 0) {
                    String orderId = (String) orderTable.getValueAt(row, 0);
                    loadOrderDetails(orderId);
                }
            }
        });
        
        statusFilter.addActionListener(e -> filterOrders((String) statusFilter.getSelectedItem()));
        
        // Initial load
        loadOrders();
    }
    
    private void loadOrderDetails(String orderId) {
        // Find order in business
        selectedOrder = business.findOrder(orderId);
        if (selectedOrder == null) return;
        
        orderDetailsPanel.removeAll();
        
        // Order Info
        orderDetailsPanel.add(new JLabel("Order #" + orderId), "span, center, wrap");
        orderDetailsPanel.add(new JSeparator(), "span, growx, wrap");
        
        // Customer Info
        Customer customer = selectedOrder.getCustomer();
        orderDetailsPanel.add(new JLabel("Customer: " + customer.getName()), "wrap");
        orderDetailsPanel.add(new JLabel("Contact: " + customer.getContact()), "wrap");
        
        // Order Items
        JPanel itemsPanel = new JPanel(new MigLayout("fillx"));
        for (OrderItem item : selectedOrder.getOrderItems()) {
            itemsPanel.add(new JLabel(item.getProduct().getName()), "split 2");
            itemsPanel.add(new JLabel("$" + item.getPrice()), "wrap");
        }
        
        orderDetailsPanel.add(itemsPanel, "span, growx, wrap");
        
        // Actions
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton processBtn = UIUtils.createCustomButton("Process Order", UIUtils.PRIMARY_COLOR);
        JButton cancelBtn = UIUtils.createCustomButton("Cancel Order", UIUtils.ERROR_COLOR);
        
        actionPanel.add(processBtn);
        actionPanel.add(cancelBtn);
        
        orderDetailsPanel.add(actionPanel, "span, center");
        
        orderDetailsPanel.revalidate();
        orderDetailsPanel.repaint();
    }
    
    private void loadOrders() {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.setRowCount(0);
        
        for (Order order : business.getOrderList()) {
            model.addRow(new Object[]{
                order.getOrderId(),
                order.getCustomer().getName(),
                order.getOrderDate(),
                order.getOrderItems().size(),
                String.format("$%.2f", order.getTotalAmount()),
                order.getStatus()
            });
        }
    }
    
    private void filterOrders(String status) {
        // Implement order filtering logic
    }
} 