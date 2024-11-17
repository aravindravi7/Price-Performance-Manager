public class LoginPanel extends javax.swing.JPanel {
    private PricingModelFrame parentFrame;
    private JComboBox<String> userTypeCombo;
    
    public LoginPanel(PricingModelFrame frame) {
        this.parentFrame = frame;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        userTypeCombo = new JComboBox<>(new String[]{"Customer", "Supplier", "Admin"});
        JButton loginButton = new JButton("Login");
        
        loginButton.addActionListener(e -> {
            String selectedRole = (String) userTypeCombo.getSelectedItem();
            switch(selectedRole) {
                case "Customer":
                    parentFrame.switchPanel("customer");
                    break;
                case "Supplier":
                    parentFrame.switchPanel("supplier");
                    break;
                case "Admin":
                    parentFrame.switchPanel("admin");
                    break;
            }
        });
        
        // Add components to panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.add(new JLabel("Select User Type:"));
        centerPanel.add(userTypeCombo);
        centerPanel.add(loginButton);
        
        add(centerPanel, BorderLayout.CENTER);
    }
} 