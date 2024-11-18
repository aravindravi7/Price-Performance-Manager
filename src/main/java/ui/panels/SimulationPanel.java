public class SimulationPanel extends JPanel {
    private Business business;
    private JTable resultsTable;
    private JFreeChart revenueChart;
    private JSpinner priceAdjustmentSpinner;
    private JSpinner iterationsSpinner;
    private JProgressBar simulationProgress;
    
    public SimulationPanel(Business business) {
        this.business = business;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Parameters Panel
        JPanel paramsPanel = new JPanel(new MigLayout("fillx, insets 20", "[][grow]"));
        paramsPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Add simulation parameters
        priceAdjustmentSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 50, 1));
        iterationsSpinner = new JSpinner(new SpinnerNumberModel(1000, 100, 10000, 100));
        
        paramsPanel.add(new JLabel("Price Adjustment Range (%):"), "right");
        paramsPanel.add(priceAdjustmentSpinner, "growx, wrap");
        paramsPanel.add(new JLabel("Simulation Iterations:"), "right");
        paramsPanel.add(iterationsSpinner, "growx, wrap");
        
        // Results Panel
        JPanel resultsPanel = new JPanel(new BorderLayout(10, 10));
        resultsPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Table Model
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Product", "Current Price", "Suggested Price", "Revenue Impact", "Risk Level"},
            0
        );
        resultsTable = new JTable(model);
        UIUtils.styleTable(resultsTable);
        
        // Chart Panel
        ChartPanel chartPanel = createChartPanel();
        
        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            new JScrollPane(resultsTable),
            chartPanel
        );
        splitPane.setResizeWeight(0.5);
        
        // Progress Bar
        simulationProgress = new JProgressBar(0, 100);
        simulationProgress.setStringPainted(true);
        
        // Control Panel
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        controlPanel.setBackground(UIUtils.BACKGROUND_COLOR);
        
        JButton runButton = UIUtils.createCustomButton("Run Simulation", UIUtils.PRIMARY_COLOR);
        JButton exportButton = UIUtils.createCustomButton("Export Results", UIUtils.SECONDARY_COLOR);
        
        runButton.addActionListener(e -> runSimulation());
        exportButton.addActionListener(e -> exportResults());
        
        controlPanel.add(runButton);
        controlPanel.add(exportButton);
        
        // Layout
        add(paramsPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(simulationProgress, BorderLayout.SOUTH);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    private void runSimulation() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                int iterations = (Integer) iterationsSpinner.getValue();
                Simulation simulation = new Simulation(business);
                
                for (int i = 0; i < iterations; i++) {
                    simulation.runIteration();
                    publish((i * 100) / iterations);
                }
                return null;
            }
            
            @Override
            protected void process(List<Integer> chunks) {
                simulationProgress.setValue(chunks.get(chunks.size() - 1));
            }
            
            @Override
            protected void done() {
                updateResults();
                JOptionPane.showMessageDialog(SimulationPanel.this, 
                    "Simulation completed successfully!");
            }
        };
        worker.execute();
    }
} 