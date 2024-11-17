/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UserAccountManagement;

import model.Personnel.Profile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author kal bugrara
 */
public class UserAccount {
    
    private String username;
    private String password;
    private Role role;
    private Profile profile;
    private List<String> permissions;
    private ActivityLog activityLog;
    
    public UserAccount(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.permissions = new ArrayList<>();
        this.activityLog = new ActivityLog();
        updatePermissionsBasedOnProfile();
    }
    
    private void updatePermissionsBasedOnProfile() {
        // Set permissions based on role
        switch(role) {
            case ADMIN:
                // Admin permissions
                break;
            case SALES:
                // Sales permissions
                break;
            case MARKETING:
                // Marketing permissions
                break;
            case SUPPLIER:
                // Supplier permissions
                break;
            case CUSTOMER:
                // Customer permissions
                break;
        }
    }
    
    public boolean isMatch(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
    
    public Role getRole() {
        return role;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void logActivity(String action, String details) {
        activityLog.addEntry(action, details);
    }
    
    public void assignProfile(Profile profile) {
        this.profile = profile;
        updatePermissionsBasedOnProfile();
    }
    
    private void initializePermissions() {
        switch (role) {
            case ADMIN:
                permissions.addAll(Arrays.asList("READ", "WRITE", "DELETE", "MANAGE"));
                break;
            case SUPPLIER:
                permissions.addAll(Arrays.asList("READ", "WRITE"));
                break;
            case CUSTOMER:
                permissions.add("READ");
                break;
        }
    }

    public String getPersonId(){
        return profile.getPerson().getPersonId();
    }

        public boolean isMatch(String id){
        if(getPersonId().equals(id)) return true;
        return false;
    }
        public boolean IsValidUser(String un, String pw){
        
            if (username.equalsIgnoreCase(un) && password.equals(pw)) return true;
            else return false;
        
        }
        public String getRole(){
            return profile.getRole();
        }
        
        public Profile getAssociatedPersonProfile(){
            return profile;
        }    
        
}
