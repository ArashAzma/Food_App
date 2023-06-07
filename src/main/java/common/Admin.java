package common;

import java.io.Serializable;

public final class Admin implements Serializable {
    private String name;
    private String password;
    private String phoneNumber;
    private String address;
    private String email;
    public Admin(){};
    public Admin(String name, String password, String phoneNumber, String address, String email){
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String toString(){
        return name+" "+password+" "+phoneNumber+" "+address+" "+email;
    }
}

