public class SupplierWorkArea extends WorkAreaPanel {
    private JTable productTable;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        
        // Main content
        cardPanel = new JPanel(cardLayout = new CardLayout());
        cardPanel.add(createProductListPanel(), "productList");
        cardPanel.add(createProductDetailsPanel(), "productDetails");
        
        add(headerPanel, BorderLayout.NORTH);
        add(cardPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Title
        JLabel title = new JLabel("Product Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(UIUtils.SECONDARY_COLOR);
        
        // Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        JButton addButton = UIUtils.createCustomButton("Add Product", UIUtils.ACCENT_COLOR);
        JButton refreshButton = UIUtils.createCustomButton("Refresh", UIUtils.PRIMARY_COLOR);
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        header.add(title, BorderLayout.WEST);
        header.add(buttonPanel, BorderLayout.EAST);
        
        return header;
    }
} 