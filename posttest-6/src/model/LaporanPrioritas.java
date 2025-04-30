package model;

public class LaporanPrioritas extends Laporan implements intLaporanPrioritas{
    private String prioritas;

    public LaporanPrioritas(int id, String deskripsi, String status, String kategori, int userId, String prioritas) {
        super(id, deskripsi, status, kategori, userId);
        this.prioritas = prioritas;
    }

    public String getPrioritas() {
        return prioritas;
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Prioritas: " + prioritas);
    }
}