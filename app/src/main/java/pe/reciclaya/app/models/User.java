package pe.reciclaya.app.models;

public class User {
    private int id;
    private String full_name;
    private String email;
    private String password;
    private String role;

    public User(int id, String full_name, String email, String password, String role) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public int getId() {return id;}
    public String getFull_name() {return full_name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getRole() {return role;}

    public void setId(int id) {this.id = id;}
    public void setFull_name(String full_name) {this.full_name = full_name;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) {this.role = role;}
}
