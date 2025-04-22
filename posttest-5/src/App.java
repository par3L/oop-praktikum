import model.*;
import java.util.*;

public class App {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Laporan> laporanList = new ArrayList<>();
    private static ArrayList<Komentar> komentarList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static final void main(String[] args) {
        seedUsers();
        mainMenu();
    }

    private static void seedUsers() {
        users.add(new userAdmin(1, "admin", "123"));
        users.add(new Instansi(2, "instansi", "123"));
        users.add(new userInstansi(3, "pemerintah", "123", "Kesehatan"));
        users.add(new userWarga(4, "warga", "123")); 
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Login");
            System.out.println("2. Daftar");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = getIntInput();
            switch (pilihan) {
                case 1 -> login();
                case 2 -> register();
                case 0 -> {
                    System.out.println("Terima kasih telah menggunakan aplikasi!");
                    System.exit(0);
                }
                default -> System.out.println("Pilihan tidak valid!");
            }
        }
    }

    private static void register() {
        System.out.print("Masukkan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();
        System.out.print("Masukkan Role (warga/instansi/admin): ");
        String role = scanner.nextLine();

        int id = users.size() + 1;
        switch (role.toLowerCase()) {
            case "admin" -> users.add(new userAdmin(id, username, password));
            case "instansi" -> users.add(new Instansi(id, username, password));
            case "pemerintah" -> {
                System.out.print("Masukkan Departemen: ");
                String departemen = scanner.nextLine();
                users.add(new userInstansi(id, username, password, departemen));
            }
            default -> users.add(new userWarga(id, username, password)); 
        }
        System.out.println("Registrasi berhasil!");
    }

    private static void login() {
        System.out.print("Masukkan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login berhasil sebagai " + user.getRole());
                menuUtama();
                return;
            }
        }
        System.out.println("Login gagal! Username atau password salah.");
    }

    private static void menuUtama() {
        while (true) {
            System.out.println("\nMenu Utama:");
            switch (currentUser.getRole()) {
                case "warga" -> displayWargaMenu();
                case "instansi" -> displayInstansiMenu();
                case "admin" -> displayAdminMenu();
                default -> System.out.println("Role tidak dikenali!");
            }
        }
    }

    private static void displayWargaMenu() {
        System.out.println("1. Tambah Laporan");
        System.out.println("2. Tampilkan Laporan");
        System.out.println("3. Tambah Komentar");
        System.out.println("4. Tampilkan Laporan Sendiri");
        System.out.println("5. Hapus Laporan Sendiri");
        System.out.println("0. Logout");
        handleMenu(getIntInput());
    }

    private static void displayInstansiMenu() {
        System.out.println("1. Lihat Laporan Berdasarkan Kategori");
        System.out.println("2. Ubah Status Laporan");
        System.out.println("3. Tambah Komentar");
        System.out.println("0. Logout");
        handleMenu(getIntInput());
    }

    private static void displayAdminMenu() {
        System.out.println("1. Kelola Pengguna");
        System.out.println("2. Kelola Laporan");
        System.out.println("3. Kelola Komentar");
        System.out.println("0. Logout");
        handleMenu(getIntInput());
    }

    private static void handleMenu(int pilihan) {
        if (pilihan == 0) {
            currentUser = null;
            mainMenu();
            return;
        }

        switch (currentUser.getRole()) {
            case "warga" -> handleWargaMenu(pilihan);
            case "instansi" -> handleInstansiMenu(pilihan);
            case "admin" -> handleAdminMenu(pilihan);
            default -> System.out.println("Pilihan tidak valid!");
        }
    }

    private static void handleWargaMenu(int pilihan) {
        switch (pilihan) {
            case 1 -> tambahLaporan();
            case 2 -> tampilkanLaporan();
            case 3 -> tambahKomentar();
            case 4 -> tampilkanLaporanSendiri();
            case 5 -> hapusLaporanSendiri();
            default -> System.out.println("Pilihan tidak valid!");
        }
    }

    private static void handleInstansiMenu(int pilihan) {
        switch (pilihan) {
            case 1 -> tampilkanLaporanByKategori();
            case 2 -> updateLaporanStatus();
            case 3 -> tambahKomentar();
            default -> System.out.println("Pilihan tidak valid!");
        }
    }

    private static void handleAdminMenu(int pilihan) {
        switch (pilihan) {
            case 1 -> kelolaPengguna();
            case 2 -> kelolaLaporan();
            case 3 -> kelolaKomentar();
            default -> System.out.println("Pilihan tidak valid!");
        }
    }

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid, masukkan angka: ");
            }
        }
    }

    private static void tambahLaporan() {
        System.out.print("Masukkan ID Laporan: ");
        int id = getIntInput();
        System.out.print("Masukkan Deskripsi: ");
        String deskripsi = scanner.nextLine();
        System.out.print("Masukkan Status: ");
        String status = scanner.nextLine();
        System.out.print("Masukkan Kategori: ");
        String kategori = scanner.nextLine();

        laporanList.add(new Laporan(id, deskripsi, status, kategori, currentUser.getId()));
        System.out.println("Laporan berhasil ditambahkan!");
    }

    private static void tampilkanLaporan() {
        for (Laporan laporan : laporanList) {
            laporan.display();
            for (Komentar komentar : komentarList) {
                if (komentar.getIdLaporan() == laporan.getId()) {
                    komentar.display();
                }
            }
        }
    }

    private static void tampilkanLaporanSendiri() {
        for (Laporan laporan : laporanList) {
            if (laporan.getUserId() == currentUser.getId()) {
                laporan.display();
            }
        }
    }

    private static void hapusLaporanSendiri() {
        System.out.print("Masukkan ID Laporan yang akan dihapus: ");
        int id = getIntInput();
        laporanList.removeIf(laporan -> laporan.getId() == id && laporan.getUserId() == currentUser.getId());
        System.out.println("Laporan berhasil dihapus!");
    }

    private static void tampilkanLaporanByKategori() {
        System.out.print("Masukkan Kategori: ");
        String kategori = scanner.nextLine();

        for (Laporan laporan : laporanList) {
            if (laporan.getKategori().equalsIgnoreCase(kategori)) {
                laporan.display();
            }
        }
    }

    private static void updateLaporanStatus() {
        System.out.print("Masukkan ID Laporan yang akan diubah: ");
        int id = getIntInput();
        System.out.print("Masukkan Status baru: ");
        String status = scanner.nextLine();

        for (Laporan laporan : laporanList) {
            if (laporan.getId() == id) {
                laporan.setStatus(status);
                System.out.println("Status laporan berhasil diubah!");
                return;
            }
        }
        System.out.println("Laporan tidak ditemukan!");
    }

    private static void tambahKomentar() {
        System.out.print("Masukkan ID Laporan yang dikomentari: ");
        int idLaporan = getIntInput();
        System.out.print("Masukkan Komentar: ");
        String isiKomentar = scanner.nextLine();

        komentarList.add(new Komentar(idLaporan, currentUser.getId(), isiKomentar));
        System.out.println("Komentar berhasil ditambahkan!");
    }

    private static void kelolaPengguna() {
        System.out.println("Daftar Pengguna:");
        for (User user : users) {
            System.out.println(
                    "ID: " + user.getId() + " | Username: " + user.getUsername() + " | Role: " + user.getRole());
        }
        System.out.println("Kelola Pengguna:");
        System.out.println("1. Hapus Pengguna");
        System.out.print("Pilih menu: ");
        int pilihan = getIntInput();
        if (pilihan == 1) {
            deleteUser();
        } else {
            System.out.println("Pilihan tidak valid!");
        }
    }

    private static void deleteUser() {
        System.out.print("Masukkan ID Pengguna yang akan dihapus: ");
        int id = getIntInput();
        users.removeIf(user -> user.getId() == id);
        System.out.println("Pengguna berhasil dihapus!");
    }

    private static void kelolaLaporan() {
        System.out.println("Daftar Laporan:");
        for (Laporan laporan : laporanList) {
            laporan.display();
        }
        System.out.println("Kelola Laporan:");
        System.out.println("1. Hapus Laporan");
        System.out.print("Pilih menu: ");
        int pilihan = getIntInput();
        if (pilihan == 1) {
            deleteReport();
        } else {
            System.out.println("Pilihan tidak valid!");
        }
    }

    private static void deleteReport() {
        System.out.print("Masukkan ID Laporan yang akan dihapus: ");
        int id = getIntInput();
        laporanList.removeIf(laporan -> laporan.getId() == id);
        System.out.println("Laporan berhasil dihapus!");
    }

    private static void kelolaKomentar() {
        System.out.println("Daftar Komentar:");
        for (Komentar komentar : komentarList) {
            komentar.display();
        }
        System.out.println("Kelola Komentar:");
        System.out.println("1. Hapus Komentar");
        System.out.print("Pilih menu: ");
        int pilihan = getIntInput();
        if (pilihan == 1) {
            deleteComment();
        } else {
            System.out.println("Pilihan tidak valid!");
        }
    }

    private static void deleteComment() {
        System.out.print("Masukkan ID Komentar yang akan dihapus: ");
        int id = getIntInput();
        komentarList.removeIf(komentar -> komentar.getIdLaporan() == id);
        System.out.println("Komentar berhasil dihapus!");
    }
}