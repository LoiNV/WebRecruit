/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import db.DBUtil;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.News;
import model.User;

/**
 *
 * @author Admin
 */
public class DataAccess {

    public List<News> getAllNews() {
        List<News> list = new LinkedList<>();
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pre = conn.prepareStatement("select * from tbl_news");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String local = rs.getString("local");
                String department = rs.getString("department");
                Date timeout = rs.getDate("timeout");
                int quantity = rs.getInt("quantity");
                boolean status = rs.getBoolean("status");
                String descript = rs.getString("descript");
                News news = new News(name, local, department, timeout, quantity, status, descript);
                news.setId(id);
                list.add(news);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public boolean addNews(News n) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pre = conn.prepareStatement("insert into tbl_news values (?,?,?,?,?,?,?)");
            pre.setString(1, n.getName());
            pre.setString(2, n.getLocal());
            pre.setString(3, n.getDepartment());
            pre.setDate(4, n.getTimeout());
            pre.setInt(5, n.getQuantity());
            pre.setBoolean(6, n.isStatus());
            pre.setString(7, n.getDescript());
            int rows = pre.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean removeNews(int id) {
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pre = conn.prepareStatement("delete tbl_news where id=?");
            pre.setInt(1, id);
            int rows = pre.executeUpdate();
            conn.close();
            return rows >0;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<User> getAllUser() {
        List<User> list = new LinkedList<>();
        try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pre = conn.prepareStatement("select * from tbl_user");
            ResultSet rs = pre.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String department = rs.getString("department");
                String linkCV = rs.getString("linkCV");
                User user = new User(name, email, phone, department, linkCV);
                user.setId(id);
                list.add(user);
            }
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    
    public boolean addUser(User user){
         try {
            Connection conn = DBUtil.getConnection();
            PreparedStatement pre = conn.prepareStatement("insert into tbl_user values (?,?,?,?,?)");
            pre.setString(1, user.getName());
            pre.setString(2, user.getEmail());
            pre.setString(3, user.getPhone());
            pre.setString(4, user.getDepartment());
            pre.setString(5, user.getLinkCV());
            int rows = pre.executeUpdate();
            conn.close();
            return rows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(DataAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
