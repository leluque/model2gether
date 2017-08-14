package br.com.models;

import java.util.ArrayList;

public class User extends Entity {

    private String name;
    private String email;
    private String login;
    private String password;
    private int preferentialDiagramMode;
    private ArrayList<Diagram> myDiagrams;
    private ArrayList<Diagram> thirdDiagrams;

    private VisualImpairment visualImpairment;
    private HasStudied hasStudied;

    public VisualImpairment getVisualImpairment() {
        return this.visualImpairment;
    }

    public void setVisualImpairment(VisualImpairment visualImpairment) {
        this.visualImpairment = visualImpairment;
    }

    public HasStudied getHasStudied() {
        return this.hasStudied;
    }

    public void setHasStudied(HasStudied hasStudied) {
        this.hasStudied = hasStudied;
    }

    public ArrayList<Diagram> getMyDiagrams() {
        return this.myDiagrams;
    }

    public void setMyDiagrams(ArrayList<Diagram> myDiagrams) {
        this.myDiagrams = myDiagrams;
    }

    public ArrayList<Diagram> getThirdDiagrams() {
        return this.thirdDiagrams;
    }

    public void setThirdDiagrams(ArrayList<Diagram> thirdDiagrams) {
        this.thirdDiagrams = thirdDiagrams;
    }

    public int getPreferentialDiagramMode() {
        return this.preferentialDiagramMode;
    }

    public void setPreferentialDiagramMode(int preferentialDiagramMode) {
        this.preferentialDiagramMode = preferentialDiagramMode;
    }

    public User() {
    }

    public User(String username, String pass) {
        this.login = username;
        this.password = pass;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return this.login;
    }
    
    

}
