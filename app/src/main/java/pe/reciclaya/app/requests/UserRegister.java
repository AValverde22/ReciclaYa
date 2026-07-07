package pe.reciclaya.app.requests;

public class UserRegister {
    private String fullName;
    private String email;
    private String password;
    private String rol;

    public UserRegister() {}

    public UserRegister(String fullName, String email, String password, String rol) {
        fullName = fullName;
        email = email;
        password = password;
        rol = rol;
    }

    public String getFullName() {return fullName;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public String getRol() {return rol;}

    public void setFullName(String fullName) {this.fullName = fullName;}
    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
    public void setRol(String rol) {this.rol = rol;}
}
