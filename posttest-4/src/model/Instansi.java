package model;

public class Instansi extends User {
    public Instansi(int id, String username, String password) {
        super(id, username, password, "instansi");
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Instansi - Menangani laporan");
    }
}