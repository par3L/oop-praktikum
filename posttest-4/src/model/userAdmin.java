package model;

public class userAdmin extends User {
    public userAdmin(int id, String username, String password) {
        super(id, username, password, "u");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: u - Mengelola sistem");
    }
}