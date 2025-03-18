import model.*;
import java.util.*;

public class App {
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Laporan> laporanList = new ArrayList<>();
    static ArrayList<Komentar> komentarList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static User currentUser = null;

    public static void main(String[] args) {
        seedUsers();
        mainMenu();
    }

    static void seedUsers() {
        users.add(new User(1, "warga", "123", "warga"));
        users.add(new User(2, "instansi", "123", "instansi"));
        users.add(new User(3, "admin", "123", "admin"));
    }

    static void mainMenu() {
        while (true) {
            System.out.println("\nMenu Utama:");
            System.out.println("1. Login");
            System.out.println("2. Daftar");
            System.out.println("0. Keluar");
            System.out.print("Pilih menu: ");
            int pilihan = getIntInput();
            switch (pilihan) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid!");
            }
        }
    }

    static void register() {
        System.out.print("Masukkan Username: ");
        String username = scanner.nextLine();
        System.out.print("Masukkan Password: ");
        String password = scanner.nextLine();
        System.out.print("Masukkan Role (warga/instansi/admin): ");
        String role = scanner.nextLine();

        int id = users.size() + 1;
        users.add(new User(id, username, password, role));
        System.out.println("Registrasi berhasil!");
    }

    static void login() {
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

    static void menuUtama() {
        while (true) {
            System.out.println("\nMenu Utama:");
            if (currentUser.getRole().equals("warga")) {
                System.out.println("1. Tambah Laporan");
                System.out.println("2. Tampilkan Laporan");
                System.out.println("3. Tambah Komentar");
                System.out.println("4. Tampilkan Laporan Sendiri");
                System.out.println("5. Hapus Laporan Sendiri");
            } else if (currentUser.getRole().equals("instansi")) {
                System.out.println("1. Lihat Laporan Berdasarkan Kategori");
                System.out.println("2. Ubah Status Laporan");
                System.out.println("3. Tambah Komentar");
            } else if (currentUser.getRole().equals("admin")) {
                System.out.println("1. Kelola Pengguna");
                System.out.println("2. Kelola Laporan");
                System.out.println("3. Kelola Komentar");
            }
            System.out.println("0. Logout");
            System.out.print("Pilih menu: ");
            int pilihan = getIntInput();
            if (pilihan == 0) {
                currentUser = null;
                mainMenu();
                return;
            }
            handleMenu(pilihan);
        }
    }

    static void handleMenu(int pilihan) {
        switch (currentUser.getRole()) {
            case "warga":
                handleWargaMenu(pilihan);
                break;
            case "instansi":
                handleInstansiMenu(pilihan);
                break;
            case "admin":
                handleAdminMenu(pilihan);
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    static void handleWargaMenu(int pilihan) {
        switch (pilihan) {
            case 1:
                tambahLaporan();
                break;
            case 2:
                tampilkanLaporan();
                break;
            case 3:
                tambahKomentar();
                break;
            case 4:
                tampilkanLaporanSendiri();
                break;
            case 5:
                hapusLaporanSendiri();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    static void handleInstansiMenu(int pilihan) {
        switch (pilihan) {
            case 1:
                tampilkanLaporanByKategori();
                break;
            case 2:
                updateLaporanStatus();
                break;
            case 3:
                tambahKomentar();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    static void handleAdminMenu(int pilihan) {
        switch (pilihan) {
            case 1:
                kelolaPengguna();
                break;
            case 2:
                kelolaLaporan();
                break;
            case 3:
                kelolaKomentar();
                break;
            default:
                System.out.println("Pilihan tidak valid!");
        }
    }

    static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Input tidak valid, masukkan angka: ");
            }
        }
    }

    static void tambahLaporan() {
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

    static void tampilkanLaporan() {
        for (Laporan laporan : laporanList) {
            laporan.display();
            for (Komentar komentar : komentarList) {
                if (komentar.getIdLaporan() == laporan.getId()) {
                    komentar.display();
                }
            }
        }
    }

    static void tampilkanLaporanSendiri() {
        for (Laporan laporan : laporanList) {
            if (laporan.getUserId() == currentUser.getId()) {
                laporan.display();
            }
        }
    }

    static void hapusLaporanSendiri() {
        System.out.print("Masukkan ID Laporan yang akan dihapus: ");
        int id = getIntInput();
        laporanList.removeIf(laporan -> laporan.getId() == id && laporan.getUserId() == currentUser.getId());
        System.out.println("Laporan berhasil dihapus!");
    }

    static void tampilkanLaporanByKategori() {
        System.out.print("Masukkan Kategori: ");
        String kategori = scanner.nextLine();

        for (Laporan laporan : laporanList) {
            if (laporan.getKategori().equalsIgnoreCase(kategori)) {
                laporan.display();
            }
        }
    }

    static void updateLaporanStatus() {
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

    static void tambahKomentar() {
        System.out.print("Masukkan ID Laporan yang dikomentari: ");
        int idLaporan = getIntInput();
        System.out.print("Masukkan Komentar: ");
        String isiKomentar = scanner.nextLine();

        komentarList.add(new Komentar(idLaporan, currentUser.getId(), isiKomentar));
        System.out.println("Komentar berhasil ditambahkan!");
    }

    static void kelolaPengguna() {
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

    static void deleteUser() {
        System.out.print("Masukkan ID Pengguna yang akan dihapus: ");
        int id = getIntInput();
        users.removeIf(user -> user.getId() == id);
        System.out.println("Pengguna berhasil dihapus!");
    }

    static void kelolaLaporan() {
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

    static void deleteReport() {
        System.out.print("Masukkan ID Laporan yang akan dihapus: ");
        int id = getIntInput();
        laporanList.removeIf(laporan -> laporan.getId() == id);
        System.out.println("Laporan berhasil dihapus!");
    }

    static void kelolaKomentar() {
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

    static void deleteComment() {
        System.out.print("Masukkan ID Komentar yang akan dihapus: ");
        int id = getIntInput();
        komentarList.removeIf(komentar -> komentar.getIdLaporan() == id);
        System.out.println("Komentar berhasil dihapus!");
    }
}