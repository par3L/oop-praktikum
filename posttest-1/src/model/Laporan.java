package model;

public class Laporan {
    private int id;
    private String deskripsi;
    private String status;
    private String kategori;
    private int userId;

    public Laporan(int id, String deskripsi, String status, String kategori, int userId) {
        this.id = id;
        this.deskripsi = deskripsi;
        this.status = status;
        this.kategori = kategori;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKategori() {
        return kategori;
    }

    public int getUserId() {
        return userId;
    }

    public void display() {
        System.out.println(
                "ID: " + id + " | Deskripsi: " + deskripsi + " | Status: " + status + " | Kategori: " + kategori);
    }
}
