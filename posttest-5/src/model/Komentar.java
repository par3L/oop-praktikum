package model;

public final class Komentar {
    private int idLaporan;
    private final int userId;
    private String isiKomentar;

    public Komentar(int idLaporan, int userId, String isiKomentar) {
        this.idLaporan = idLaporan;
        this.userId = userId;
        this.isiKomentar = isiKomentar;
    }

    public int getIdLaporan() {
        return idLaporan;
    }

    protected int getUserId() {
        return userId;
    }

    public String getIsiKomentar() {
        return isiKomentar;
    }

    public void display() {
        System.out.println("Laporan ID: " + idLaporan + " | User ID: " + userId + " | Komentar: " + isiKomentar);
    }
}
