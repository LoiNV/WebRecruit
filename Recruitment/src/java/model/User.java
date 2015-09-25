/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Admin
 */
public class User {
    
    private int id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String linkCV;

    public User(String name, String email, String phone, String department, String linkCV) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.linkCV = linkCV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLinkCV() {
        return linkCV;
    }

    public void setLinkCV(String linkCV) {
        this.linkCV = linkCV;
    }
    
    
}
