public class AdminWorkArea extends WorkAreaPanel {
    private JTabbedPane tabbedPane;
    private JPanel simulationPanel;
    private JTable simulationResultsTable;
    private ChartPanel revenueChartPanel;
    
    public AdminWorkArea(PricingModelFrame frame, Business business) {
        super(frame, business);
    }
    
    @Override
    protected void initComponents() {
        setLayout(new BorderLayout());
        setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Create custom tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabbedPane.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Add styled tabs
        tabbedPane.addTab("Dashboard", createDashboardPanel());
        tabbedPane.addTab("Simulation", createSimulationPanel());
        tabbedPane.addTab("Supplier Management", createSupplierPanel());
        tabbedPane.addTab("Performance Analysis", createPerformancePanel());
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private JPanel createSupplierPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // Implementation using SupplierDirectory class
        return panel;
    }
    
    private JPanel createPerformancePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // Implementation using PriceProfile and Performance classes
        return panel;
    }
    
    private JPanel createSimulationPanel() {
        simulationPanel = new JPanel(new BorderLayout(10, 10));
        simulationPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        simulationPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Control Panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Simulation parameters
        JPanel paramsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        paramsPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Add parameter inputs
        addParameterField(paramsPanel, "Price Adjustment Range (%)", "±10");
        addParameterField(paramsPanel, "Simulation Iterations", "1000");
        addParameterField(paramsPanel, "Target Profit Margin (%)", "25");
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        JButton runSimulationBtn = UIUtils.createCustomButton("Run Simulation", UIUtils.PRIMARY_COLOR);
        JButton resetBtn = UIUtils.createCustomButton("Reset", UIUtils.SECONDARY_COLOR);
        
        buttonPanel.add(runSimulationBtn);
        buttonPanel.add(resetBtn);
        
        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout(10, 10));
        resultsPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Create results table
        String[] columns = {"Product", "Current Price", "Optimal Price", "Revenue Impact", "Confidence"};
        simulationResultsTable = new JTable(new DefaultTableModel(columns, 0));
        UIUtils.styleTable(simulationResultsTable);
        
        // Create chart
        revenueChartPanel = createRevenueChart();
        
        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(simulationResultsTable),
            revenueChartPanel);
        splitPane.setResizeWeight(0.5);
        
        // Add components
        resultsPanel.add(splitPane, BorderLayout.CENTER);
        
        simulationPanel.add(paramsPanel, BorderLayout.NORTH);
        simulationPanel.add(resultsPanel, BorderLayout.CENTER);
        simulationPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add action listeners
        runSimulationBtn.addActionListener(e -> runSimulation());
        resetBtn.addActionListener(e -> resetSimulation());
        
        return simulationPanel;
    }
    
    private void addParameterField(JPanel panel, String label, String defaultValue) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JTextField field = new JTextField(defaultValue);
        field.setPreferredSize(new Dimension(150, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        panel.add(jLabel);
        panel.add(field);
    }
    
    private ChartPanel createRevenueChart() {
        XYSeries series = new XYSeries("Revenue Projection");
        // Add sample data
        series.add(1.0, 1.0);
        series.add(2.0, 4.0);
        series.add(3.0, 3.0);
        
        XYSeriesCollection dataset = new XYSeriesCollection(series);
        JFreeChart chart = ChartFactory.createXYLineChart(
            "Revenue Impact Simulation",
            "Price Adjustment (%)",
            "Revenue Change (%)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        // Customize chart appearance
        chart.setBackgroundPaint(UIUtils.BACKGROUND_COLOR);
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);
        
        return new ChartPanel(chart);
    }
    
    private void runSimulation() {
        // Create and run simulation using existing Simulation class
        Simulation simulation = new Simulation(business);
        simulation.runSimulation();
        
        // Update results table and chart with simulation results
        updateSimulationResults(simulation);
    }
    
    private void updateSimulationResults(Simulation simulation) {
        DefaultTableModel model = (DefaultTableModel) simulationResultsTable.getModel();
        model.setRowCount(0);
        
        // Add simulation results to table
        for (Product product : business.getProductList()) {
            PriceProfile profile = product.getPriceProfile();
            Object[] row = {
                product.getProductName(),
                profile.getCurrentPrice(),
                profile.getOptimalPrice(),
                profile.getRevenueImpact(),
                profile.getConfidenceLevel()
            };
            model.addRow(row);
        }
        
        // Update chart with new data
        updateRevenueChart(simulation);
    }
} 