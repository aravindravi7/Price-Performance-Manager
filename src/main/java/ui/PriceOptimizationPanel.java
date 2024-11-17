public class PriceOptimizationPanel extends JPanel {
    private Business business;
    private PriceOptimizationEngine optimizationEngine;
    private JTable resultsTable;
    private JButton optimizeButton;
    private JButton applyButton;
    private UserSession userSession;
    
    public PriceOptimizationPanel(Business business, UserSession session) {
        this.business = business;
        this.userSession = session;
        this.optimizationEngine = new PriceOptimizationEngine(business);
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        
        // Create table model
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Product", "Current Price", "Optimal Price", 
                        "Revenue Impact", "Confidence"}, 0);
        resultsTable = new JTable(model);
        
        // Create buttons
        optimizeButton = new JButton("Run Optimization");
        applyButton = new JButton("Apply Changes");
        
        optimizeButton.addActionListener(e -> {
            if (AccessControl.getInstance().hasPermission(
                userSession.getSessionId(), Permission.RUN_SIMULATION)) {
                runOptimization();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Insufficient permissions to run optimization");
            }
        });
        
        applyButton.addActionListener(e -> {
            if (AccessControl.getInstance().hasPermission(
                userSession.getSessionId(), Permission.ADJUST_PRICES)) {
                applyOptimization();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Insufficient permissions to adjust prices");
            }
        });
        
        // Layout
        add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(optimizeButton);
        buttonPanel.add(applyButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void runOptimization() {
        optimizationEngine.optimizeProductPrices();
        updateResultsTable();
    }
    
    private void updateResultsTable() {
        DefaultTableModel model = (DefaultTableModel) resultsTable.getModel();
        model.setRowCount(0);
        
        for (Map.Entry<Product, OptimizationResult> entry : 
            optimizationEngine.getOptimizationResults().entrySet()) {
            Product product = entry.getKey();
            OptimizationResult result = entry.getValue();
            
            model.addRow(new Object[]{
                product.getName(),
                product.getTargetPrice(),
                result.getOptimizedPrice(),
                String.format("%.2f%%", 
                    (result.getProjectedRevenue() - product.getCurrentRevenue()) 
                    / product.getCurrentRevenue() * 100),
                String.format("%.1f%%", result.getConfidenceScore() * 100)
            });
        }
    }
} 