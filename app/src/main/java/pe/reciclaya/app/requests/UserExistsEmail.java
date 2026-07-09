package pe.reciclaya.app.requests;

public class UserExistsEmail {
    private String email;

    public UserExistsEmail() {}

    public UserExistsEmail(String email) {this.email = email;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
}
