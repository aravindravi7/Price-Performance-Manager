package model.UserAccountManagement;

import java.util.ArrayList;
import java.util.Date;

public class ActivityLog {
    private ArrayList<LogEntry> entries;
    
    public ActivityLog() {
        entries = new ArrayList<>();
    }
    
    public void addEntry(String action, String details) {
        entries.add(new LogEntry(action, details));
    }
    
    private class LogEntry {
        private Date timestamp;
        private String action;
        private String details;
        
        public LogEntry(String action, String details) {
            this.timestamp = new Date();
            this.action = action;
            this.details = details;
        }
    }
} 