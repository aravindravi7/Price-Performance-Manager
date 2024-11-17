public class PricingModelFrame extends javax.swing.JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private CustomerWorkArea customerPanel;
    private SupplierWorkArea supplierPanel;
    private AdminWorkArea adminPanel;
    
    public PricingModelFrame() {
        initComponents();
        setupLayout();
    }
    
    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Initialize panels
        loginPanel = new LoginPanel(this);
        customerPanel = new CustomerWorkArea(this);
        supplierPanel = new SupplierWorkArea(this);
        adminPanel = new AdminWorkArea(this);
        
        // Add panels to card layout
        mainPanel.add(loginPanel, "login");
        mainPanel.add(customerPanel, "customer");
        mainPanel.add(supplierPanel, "supplier");
        mainPanel.add(adminPanel, "admin");
        
        // Set initial panel
        cardLayout.show(mainPanel, "login");
        
        // Add to frame
        this.add(mainPanel);
    }
    
    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
} 