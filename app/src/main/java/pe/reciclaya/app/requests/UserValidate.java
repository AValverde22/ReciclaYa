package pe.reciclaya.app.requests;

public class UserValidate {
    private String email;
    private String password;

    public UserValidate() {}

    public UserValidate(String email, String password){
        this.email = email;
        this.password = password;
    }

    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setEmail(String email) {this.email = email;}
    public void setPassword(String password) {this.password = password;}
}
