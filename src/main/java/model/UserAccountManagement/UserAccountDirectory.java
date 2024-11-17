/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.UserAccountManagement;

import java.util.ArrayList;

import model.Personnel.*;

/**
 *
 * @author kal bugrara
 */
public class UserAccountDirectory {
    
      ArrayList<UserAccount> useraccountlist ;
    
      public UserAccountDirectory (){
          
       useraccountlist = new ArrayList();

    }

    public UserAccount newUserAccount(String username, String password, Role role) {

        UserAccount ua = new UserAccount (username,  password,  role);
        useraccountlist.add(ua);
        return ua;
    }

    public UserAccount findUserAccount(String username, String password) {

        for (UserAccount ua : useraccountlist) {

            if (ua.isMatch(username, password)) {
                return ua;
            }
        }
            return null; //not found after going through the whole list
         }
     public UserAccount AuthenticateUser(String un, String pw) {

        for (UserAccount ua : useraccountlist) {

            if (ua.IsValidUser(un, pw)) {
                return ua;
            }
        }
            return null; //not found after going through the whole list
         }   
}
