public class SupplierManagementPanel extends JPanel {
    private Business business;
    private JTable supplierTable;
    private JTable productCatalogTable;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    public SupplierManagementPanel(Business business) {
        this.business = business;
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(UIUtils.BACKGROUND_COLOR);
        
        // Toolbar
        JToolBar toolbar = createToolbar();
        
        // Main content with card layout
        mainPanel = new JPanel(cardLayout = new CardLayout());
        
        // Supplier list view
        JPanel supplierListPanel = createSupplierListPanel();
        
        // Supplier detail view
        JPanel supplierDetailPanel = createSupplierDetailPanel();
        
        mainPanel.add(supplierListPanel, "LIST");
        mainPanel.add(supplierDetailPanel, "DETAIL");
        
        add(toolbar, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBackground(UIUtils.BACKGROUND_COLOR);
        
        JButton addSupplierBtn = UIUtils.createCustomButton("Add Supplier", UIUtils.ACCENT_COLOR);
        JButton importBtn = UIUtils.createCustomButton("Import", UIUtils.SECONDARY_COLOR);
        JButton exportBtn = UIUtils.createCustomButton("Export", UIUtils.SECONDARY_COLOR);
        
        toolbar.add(addSupplierBtn);
        toolbar.add(Box.createHorizontalStrut(10));
        toolbar.add(importBtn);
        toolbar.add(exportBtn);
        
        addSupplierBtn.addActionListener(e -> showAddSupplierDialog());
        
        return toolbar;
    }
    
    private void showAddSupplierDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), 
            "Add New Supplier", true);
        
        JPanel panel = new JPanel(new MigLayout("fillx, insets 20"));
        
        JTextField nameField = new JTextField(20);
        JTextField contactField = new JTextField(20);
        JTextArea addressArea = new JTextArea(3, 20);
        
        panel.add(new JLabel("Name:"), "right");
        panel.add(nameField, "growx, wrap");
        panel.add(new JLabel("Contact:"), "right");
        panel.add(contactField, "growx, wrap");
        panel.add(new JLabel("Address:"), "right");
        panel.add(new JScrollPane(addressArea), "growx, wrap");
        
        JButton saveButton = UIUtils.createCustomButton("Save", UIUtils.PRIMARY_COLOR);
        saveButton.addActionListener(e -> {
            Supplier supplier = new Supplier();
            supplier.setName(nameField.getText());
            // Set other properties
            business.getSupplierDirectory().addSupplier(supplier);
            dialog.dispose();
            refreshSupplierList();
        });
        
        panel.add(saveButton, "span, center");
        
        dialog.add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
} 