/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.DataAccess;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import model.News;
import model.User;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class UserBean implements Serializable {

    private int id;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String linkCV;

    private String subject;
    private String content;
    private User selectUser;

    private List<User> listUser;
    private List<User> listFiltered;
    private DataTable tableUser;
    private UploadedFile fileUp;
    private StreamedContent fileDown;

    DataAccess da = new DataAccess();

    public UserBean() {

    }

    public void uploadCV() {

        if (fileUp != null) {
            try {
                String filePath = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("/");
                String fileName = fileUp.getFileName();
                InputStream is = fileUp.getInputstream();

                OutputStream os = new FileOutputStream(new File(filePath + fileName));

                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }

                is.close();
                os.flush();
                os.close();

                FacesMessage msg = new FacesMessage("Success! " + fileName + " is uploaded.");
                FacesContext.getCurrentInstance().addMessage(null, msg);

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public void downloadCV(User u) {

        InputStream stream = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream(u.getLinkCV());
        fileDown = new DefaultStreamedContent(stream, null, u.getLinkCV().replaceAll("/", ""));
    }

    public String acceptUser() {
        return "accept";
    }

    public String sendMail() {
        final String username = "loinv_c00615@fpt.aptech.ac.vn";
        final String password = "adukapro";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(selectUser.getEmail()));
            message.setSubject(this.subject);
            message.setText(this.content);

            Transport.send(message);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Send Mail Successful"));
            return "admin";

        } catch (MessagingException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Send Mail Successful"));
            return "";
        }

    }

    public String apply() {
        Map<String, String> param = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.department = param.get("department");
        return "apply";
    }

    public String addUser() {

        this.linkCV = "/" + fileUp.getFileName();
        User user = new User(name, email, phone, department, linkCV);
        boolean rs = da.addUser(user);
        if (rs) {
            uploadCV();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Submited Successful"));
            return "index";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Submited Failed"));
            return "";
        }
    }

    public List<User> getListUser() {
        this.listUser = da.getAllUser();
        Collections.sort(listUser, new Comparator<User>() {

            @Override
            public int compare(User t, User t1) {
                if (t.getId() > t1.getId()) {
                    return -1;
                }
                if (t.getId() < t1.getId()) {
                    return 1;
                }
                return 0;
            }
        });
        return listUser;
    }

    public void setListUser(List<User> listUser) {
        this.listUser = listUser;
    }

    public User getSelectUser() {
        return selectUser;
    }

    public void setSelectUser(User selectUser) {
        this.selectUser = selectUser;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public List<User> getListFiltered() {
        return listFiltered;
    }

    public void setListFiltered(List<User> listFiltered) {
        this.listFiltered = listFiltered;
    }

    public DataTable getTableUser() {
        return tableUser;
    }

    public void setTableUser(DataTable tableUser) {
        this.tableUser = tableUser;
    }

    public UploadedFile getFileUp() {
        return fileUp;
    }

    public void setFileUp(UploadedFile fileUp) {
        this.fileUp = fileUp;
    }

    public StreamedContent getFileDown() {
        return fileDown;
    }

    public void setFileDown(StreamedContent fileDown) {
        this.fileDown = fileDown;
    }

}
