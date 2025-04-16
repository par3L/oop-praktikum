package model;

public class userInstansi extends Instansi {
    private String departemen;

    public userInstansi(int id, String username, String password, String departemen) {
        super(id, username, password);
        this.departemen = departemen;
    }

    public String getDepartemen() {
        return departemen;
    }

    @Override
    public void displayRole() {
        System.out.println("Role: Instansi Pemerintah - Departemen: " + departemen);
    }
}