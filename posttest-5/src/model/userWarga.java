package model;

public class userWarga extends User {
    public userWarga(int id, String username, String password) {
        super(id, username, password, "warga");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Warga - Melaporkan masalah");
    }

    @Override
    public final void iniAbstract() {
        System.out.println("gatau mo ngapain abstarct");
    }
}
