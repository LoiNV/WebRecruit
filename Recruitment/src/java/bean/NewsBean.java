/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DataAccess;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import model.News;
import org.primefaces.component.datatable.DataTable;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class NewsBean {

    private int id;
    private String name;
    private String local;
    private String department;
    private Date timeout;
    private int quantity;
    private boolean status;
    private String descript;

    private List<News> listNews;
    private List<News> listFiltered;
    private News selectedNews;
    private List<String> listLocal;
    private List<String> listDepartments;
    private String keySearch;
    private List<News> listSearch;

    DataAccess da = new DataAccess();
    private DataTable newsTable;

    public NewsBean() {
    }

    public String addNews() {
        News news = new News(name, local, department, new java.sql.Date(timeout.getTime()), quantity, true, descript);
        boolean rs = da.addNews(news);
        if (rs) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Publish Successful"));
            return "allNews";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Publish Failed"));
            return "";
        }
    }

    public String remove() {
        News n = (News) newsTable.getRowData();
        DataAccess da = new DataAccess();
        boolean rs = da.removeNews(n.getId());
        if (rs) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "News was Deleted"));
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Delete Failed"));
        }
        
        return "";
    }
    
    public String searchStringValueChanged(ValueChangeEvent vce) {

        keySearch = (String) vce.getNewValue();
        listSearch = new LinkedList<>();
        for (News n : da.getAllNews()) {
            if ((n.getName().toLowerCase()).contains(keySearch.toLowerCase())) {
                listSearch.add(n);
            } else {
            }
        }
        return "search";
    }

    public String detailsPage() {
        return "detailsPage";
    }

    public List<News> getListNews() {
        this.listNews = da.getAllNews();
        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews = listNews;
    }

    public List<News> getListFiltered() {
        return listFiltered;
    }

    public void setListFiltered(List<News> listFiltered) {
        this.listFiltered = listFiltered;
    }

    public News getSelectedNews() {
        return selectedNews;
    }

    public void setSelectedNews(News selectedNews) {
        this.selectedNews = selectedNews;
    }

    public List<String> getListLocal() {
        this.listLocal = new LinkedList<>();
        listLocal.add("Hà Nội");
        listLocal.add("TP.HCM");
        listLocal.add("Đà Nẵng");
        return listLocal;
    }

    public void setListLocal(List<String> listLocal) {
        this.listLocal = listLocal;
    }

    public List<String> getListDepartments() {
        this.listDepartments = new LinkedList<>();
        listDepartments.add("Developer");
        listDepartments.add("Leader");
        listDepartments.add("Project Manager");
        listDepartments.add("Tester");
        return listDepartments;
    }

    public void setListDepartments(List<String> listDepartments) {
        this.listDepartments = listDepartments;
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

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Date getTimeout() {
        return timeout;
    }

    public void setTimeout(Date timeout) {
        this.timeout = timeout;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public List<News> getListSearch() {
        return listSearch;
    }

    public void setListSearch(List<News> listSearch) {
        this.listSearch = listSearch;
    }

    public DataTable getNewsTable() {
        return newsTable;
    }

    public void setNewsTable(DataTable newsTable) {
        this.newsTable = newsTable;
    }
    
}
