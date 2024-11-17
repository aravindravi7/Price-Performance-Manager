public abstract class WorkAreaPanel extends javax.swing.JPanel {
    protected Business business;
    protected PricingModelFrame parentFrame;
    
    public WorkAreaPanel(PricingModelFrame frame, Business business) {
        this.parentFrame = frame;
        this.business = business;
        initComponents();
    }
    
    protected abstract void initComponents();
} 