/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DbContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class AccountDAO {

    Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    private Account checkLogin(String user, String pass) {
        Account tk = null;
        conn = DbContext.getConnection();
        try {
            ps = conn.prepareStatement("select * from Account where user=? and pass =?");
            ps.setString(1, user);
            ps.setString(2, pass);
            rs = ps.executeQuery();
            if(rs.next()){
                tk = new Account(rs.getString(1), rs.getString(2));
            }
        } catch (Exception e) {
            System.out.println("Loi: "+e.toString());
        }
        return tk;
    }
}
