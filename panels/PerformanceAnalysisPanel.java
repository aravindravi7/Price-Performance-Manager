public class PerformanceAnalysisPanel extends JPanel {
    private Business business;
    private JTable performanceTable;
    private JComboBox<String> timeRangeCombo;
    private JFreeChart performanceChart;
    
    public PerformanceAnalysisPanel(Business business) {
        this.business = business;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Filters Panel
        JPanel filtersPanel = new JPanel(new MigLayout("fillx, insets 20"));
        filtersPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        timeRangeCombo = new JComboBox<>(new String[]{
            "Last 7 Days", "Last 30 Days", "Last 90 Days", "Year to Date"
        });
        
        JComboBox<String> metricCombo = new JComboBox<>(new String[]{
            "Revenue", "Profit Margin", "Sales Volume", "Price Performance"
        });
        
        filtersPanel.add(new JLabel("Time Range:"), "right");
        filtersPanel.add(timeRangeCombo, "growx");
        filtersPanel.add(new JLabel("Metric:"), "right");
        filtersPanel.add(metricCombo, "growx");
        
        // Performance Metrics Cards
        JPanel metricsPanel = createMetricsPanel();
        
        // Main Content
        JPanel mainContent = new JPanel(new BorderLayout(10, 10));
        mainContent.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Table
        performanceTable = new JTable(new PerformanceTableModel());
        UIUtils.styleTable(performanceTable);
        
        // Charts
        JTabbedPane chartTabs = new JTabbedPane();
        chartTabs.addTab("Trend Analysis", createTrendChart());
        chartTabs.addTab("Distribution", createDistributionChart());
        chartTabs.addTab("Comparison", createComparisonChart());
        
        // Layout
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
            new JScrollPane(performanceTable),
            chartTabs
        );
        splitPane.setResizeWeight(0.5);
        
        mainContent.add(metricsPanel, BorderLayout.NORTH);
        mainContent.add(splitPane, BorderLayout.CENTER);
        
        add(filtersPanel, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);
        
        // Add refresh button
        JButton refreshButton = UIUtils.createCustomButton("Refresh", UIUtils.PRIMARY_COLOR);
        refreshButton.addActionListener(e -> refreshData());
        add(refreshButton, BorderLayout.SOUTH);
    }
    
    private JPanel createMetricsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 10, 0));
        panel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Create metric cards
        panel.add(createMetricCard("Revenue", "$125,000", "+12.5%"));
        panel.add(createMetricCard("Profit Margin", "24.8%", "+2.3%"));
        panel.add(createMetricCard("Orders", "1,234", "+15.7%"));
        panel.add(createMetricCard("Avg. Order Value", "$102", "-3.2%"));
        
        return panel;
    }
    
    private JPanel createMetricCard(String title, String value, String change) {
        JPanel card = new JPanel(new MigLayout("fillx, insets 15"));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(UIUtils.BACKGROUND_COLOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        
        JLabel changeLabel = new JLabel(change);
        changeLabel.setForeground(change.startsWith("+") ? 
            UIUtils.ACCENT_COLOR : UIUtils.ERROR_COLOR);
        
        card.add(titleLabel, "wrap");
        card.add(valueLabel, "wrap");
        card.add(changeLabel, "wrap");
        
        return card;
    }
} 