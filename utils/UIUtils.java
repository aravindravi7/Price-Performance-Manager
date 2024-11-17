public class UIUtils {
    // Color scheme
    public static final Color PRIMARY_COLOR = new Color(41, 128, 185);    // Blue
    public static final Color SECONDARY_COLOR = new Color(44, 62, 80);    // Dark Blue
    public static final Color ACCENT_COLOR = new Color(46, 204, 113);     // Green
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241); // Light Gray
    public static final Color ERROR_COLOR = new Color(231, 76, 60);       // Red
    
    // Custom button style
    public static JButton createCustomButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    // Custom table style
    public static void styleTable(JTable table) {
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(30);
        table.getTableHeader().setBackground(SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setSelectionBackground(PRIMARY_COLOR.brighter());
        table.setSelectionForeground(Color.WHITE);
    }
} 