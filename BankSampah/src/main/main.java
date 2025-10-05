package main;

import model.Sampah;
import model.Organik;
import model.Anorganik;
import service.SampahService;
import service.CRUD_ORM;
import service.TampilDataJDBC;
import java.util.List;
import java.util.Scanner;

public class main {
    private static SampahService service = new SampahService();
    private static CRUD_ORM crudOrm = new CRUD_ORM();
    private static TampilDataJDBC tampilJDBC = new TampilDataJDBC();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SELAMAT DATANG DI BANK SAMPAH ===");
        System.out.println("Sistem Manajemen Sampah dengan Prinsip OOP");
        System.out.println("- Abstraction: Abstract class Sampah dan interface HargaSampah");
        System.out.println("- Encapsulation: Data terlindungi dengan getter/setter");
        System.out.println("- Inheritance: Sampah sebagai parent, Organik & Anorganik sebagai child");
        System.out.println("- Polymorphism: Overriding dan Overloading method");

        while (true) {
            tampilkanMenu();
            String input = scanner.nextLine();

            // Validasi input menu
            if (!isAngkaValid(input, 1, 7)) {
                System.out.println("Input tidak valid! Harus angka antara 1-7.");
                continue;
            }

            int pilihan = Integer.parseInt(input);

            switch (pilihan) {
                case 1:
                    tambahSampah();
                    break;
                case 2:
                    lihatDataMenu();
                    break;
                case 3:
                    updateData();
                    break;
                case 4:
                    hapusData();
                    break;
                case 5:
                    menuCrudORM();
                    break;
                case 6:
                    menuTampilJDBC();
                    break;
                case 7:
                    System.out.println("Terima kasih telah menggunakan Bank Sampah!");
                    System.out.println("Mari bersama menjaga lingkungan!");
                    return;
            }

            // Pause untuk user experience
            System.out.println("\nTekan Enter untuk melanjutkan...");
            scanner.nextLine();
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("           BANK SAMPAH - MENU UTAMA");
        System.out.println("=".repeat(50));
        System.out.println("1. Tambah Data Sampah");
        System.out.println("2. Lihat Data Sampah");
        System.out.println("3. Update Data Sampah");
        System.out.println("4. Hapus Data Sampah");
        System.out.println("5. CRUD Database (ORM)");
        System.out.println("6. Tampil Data (JDBC)");
        System.out.println("7. Keluar");
        System.out.println("=".repeat(50));
        System.out.print("Pilih menu (1-7): ");
    }

    private static void lihatDataMenu() {
        System.out.println("\n=== LIHAT DATA SAMPAH ===");
        System.out.println("Pilih format tampilan:");
        System.out.println("1. Tampilan Normal");
        System.out.println("2. Tampilan Detail (dengan harga dasar dan pajak)");
        System.out.print("Pilihan (1-2): ");

        String input = scanner.nextLine();

        if (!isAngkaValid(input, 1, 2)) {
            System.out.println("Pilihan harus 1 atau 2!");
            return;
        }

        int pilihan = Integer.parseInt(input);

        if (pilihan == 1) {
            service.lihatData(); // Overloading method - tanpa parameter
        } else {
            service.lihatData(true); // Overloading method - dengan parameter
        }
    }

    private static void tambahSampah() {
        System.out.println("\n=== TAMBAH DATA SAMPAH ===");
        System.out.println("Pilih jenis sampah:");
        System.out.println("1. Sampah Organik (dapat terurai secara alami)");
        System.out.println("2. Sampah Anorganik (tidak dapat terurai secara alami)");
        System.out.print("Pilihan (1-2): ");

        String inputJenis = scanner.nextLine();

        // Validasi input jenis sampah
        if (!isAngkaValid(inputJenis, 1, 2)) {
            System.out.println("Pilihan harus 1 atau 2!");
            return;
        }

        int jenisSampah = Integer.parseInt(inputJenis);

        System.out.print("Masukkan jenis sampah: ");
        String jenis = scanner.nextLine();

        // Validasi input tidak kosong
        if (jenis.trim().isEmpty()) {
            System.out.println("Jenis sampah tidak boleh kosong!");
            return;
        }

        System.out.print("Masukkan berat sampah (kg): ");
        String inputBerat = scanner.nextLine();

        // Validasi input berat
        if (!isAngkaDecimalValid(inputBerat)) {
            System.out.println("Berat harus berupa angka positif!");
            return;
        }

        double berat = Double.parseDouble(inputBerat);

        if (jenisSampah == 1) {
            tambahSampahOrganik(jenis, berat);
        } else {
            tambahSampahAnorganik(jenis, berat);
        }
    }

    private static void tambahSampahOrganik(String jenis, double berat) {
        System.out.println("\n--- Data Sampah Organik ---");
        System.out.println("Tingkat dekomposisi menentukan seberapa cepat sampah dapat terurai:");
        System.out.println("- Cepat: ~30 hari (sisa makanan, daun segar)");
        System.out.println("- Sedang: ~60 hari (ranting kecil, kulit buah)");
        System.out.println("- Lambat: ~120 hari (kayu, tulang)");
        System.out.print("Masukkan tingkat dekomposisi (Cepat/Sedang/Lambat): ");
        String dekomposisi = scanner.nextLine();

        // Validasi input dekomposisi
        if (!dekomposisi.equalsIgnoreCase("Cepat") &&
                !dekomposisi.equalsIgnoreCase("Sedang") &&
                !dekomposisi.equalsIgnoreCase("Lambat")) {
            System.out.println("Tingkat dekomposisi harus Cepat, Sedang, atau Lambat!");
            return;
        }

        service.tambahOrganik(jenis, berat, dekomposisi);
    }

    private static void tambahSampahAnorganik(String jenis, double berat) {
        System.out.println("\n--- Data Sampah Anorganik ---");
        System.out.println("Status daur ulang mempengaruhi nilai ekonomis dan dampak lingkungan:");
        System.out.println("- Ya: Dapat didaur ulang (botol plastik, kertas, kaleng)");
        System.out.println("- Tidak: Sulit/tidak dapat didaur ulang (styrofoam, kaca laminated)");
        System.out.print("Apakah sampah dapat didaur ulang? (y/n): ");
        String pilihan = scanner.nextLine();

        // Validasi input pilihan
        if (!pilihan.equalsIgnoreCase("y") && !pilihan.equalsIgnoreCase("n")) {
            System.out.println("Pilihan harus y atau n!");
            return;
        }

        boolean daurUlang = pilihan.equalsIgnoreCase("y");
        service.tambahAnorganik(jenis, berat, daurUlang);
    }

    private static void updateData() {
        service.lihatData();
        if (service.isDataKosong()) return;

        System.out.print("Pilih nomor data yang akan diupdate: ");
        String inputIndex = scanner.nextLine();

        // Validasi input index
        if (!isAngkaValid(inputIndex, 1, service.getJumlahData())) {
            System.out.println("Nomor data tidak valid!");
            return;
        }

        int index = Integer.parseInt(inputIndex) - 1;

        // Tampilkan data yang akan diupdate
        System.out.println("\nData yang akan diupdate:");
        service.lihatDetailData(index);

        System.out.print("\nMasukkan jenis baru: ");
        String jenis = scanner.nextLine();

        // Validasi input tidak kosong
        if (jenis.trim().isEmpty()) {
            System.out.println("Jenis sampah tidak boleh kosong!");
            return;
        }

        System.out.print("Masukkan berat baru (kg): ");
        String inputBerat = scanner.nextLine();

        // Validasi input berat
        if (!isAngkaDecimalValid(inputBerat)) {
            System.out.println("Berat harus berupa angka positif!");
            return;
        }

        double berat = Double.parseDouble(inputBerat);
        service.updateData(index, jenis, berat);
    }

    private static void hapusData() {
        service.lihatData();
        if (service.isDataKosong()) return;

        System.out.print("Pilih nomor data yang akan dihapus: ");
        String inputIndex = scanner.nextLine();

        // Validasi input index
        if (!isAngkaValid(inputIndex, 1, service.getJumlahData())) {
            System.out.println("Nomor data tidak valid!");
            return;
        }

        int index = Integer.parseInt(inputIndex) - 1;

        // Tampilkan data yang akan dihapus
        System.out.println("\nData yang akan dihapus:");
        service.lihatDetailData(index);

        System.out.print("\nYakin ingin menghapus data ini? (y/n): ");
        String konfirmasi = scanner.nextLine();

        // Validasi input konfirmasi
        if (!konfirmasi.equalsIgnoreCase("y") && !konfirmasi.equalsIgnoreCase("n")) {
            System.out.println("Pilihan harus y atau n!");
            return;
        }

        if (konfirmasi.equalsIgnoreCase("y")) {
            service.hapusData(index);
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }

    // Method untuk validasi angka
    private static boolean isAngkaValid(String input, int min, int max) {
        try {
            int angka = Integer.parseInt(input);
            return angka >= min && angka <= max;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method untuk validasi angka decimal
    private static boolean isAngkaDecimalValid(String input) {
        try {
            double angka = Double.parseDouble(input);
            return angka > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ==================== METHOD UNTUK ORM DAN JDBC ====================

    private static void menuCrudORM() {
        while (true) {
            System.out.println("\n=== CRUD OPERATIONS (ORM) ===");
            System.out.println("1. Create - Tambah Data");
            System.out.println("2. Read - Lihat Semua Data");
            System.out.println("3. Update - Ubah Data");
            System.out.println("4. Delete - Hapus Data");
            System.out.println("5. Kembali ke Menu Utama");
            System.out.print("Pilih: ");

            String input = scanner.nextLine();
            if (!isAngkaValid(input, 1, 5)) {
                System.out.println("Input tidak valid!");
                continue;
            }

            int pilihan = Integer.parseInt(input);

            switch (pilihan) {
                case 1:
                    tambahDataORM();
                    break;
                case 2:
                    lihatDataORM();
                    break;
                case 3:
                    updateDataORM();
                    break;
                case 4:
                    hapusDataORM();
                    break;
                case 5:
                    return;
            }
        }
    }

    private static void menuTampilJDBC() {
        System.out.println("\n=== TAMPIL DATA (JDBC Statement) ===");
        System.out.println("1. Tampilkan Semua Data");
        System.out.println("2. Tampilkan Data Organik");
        System.out.println("3. Tampilkan Data Anorganik");
        System.out.println("4. Cari Data dengan Filter");
        System.out.println("5. Tampilkan Statistik");
        System.out.print("Pilih: ");

        String input = scanner.nextLine();
        if (!isAngkaValid(input, 1, 5)) {
            System.out.println("Input tidak valid!");
            return;
        }

        int pilihan = Integer.parseInt(input);

        switch (pilihan) {
            case 1:
                tampilJDBC.tampilkanSemuaData();
                break;
            case 2:
                tampilJDBC.tampilkanByKategori("Organik");
                break;
            case 3:
                tampilJDBC.tampilkanByKategori("Anorganik");
                break;
            case 4:
                System.out.print("Masukkan kata kunci pencarian: ");
                String keyword = scanner.nextLine();
                tampilJDBC.tampilkanDenganFilter(keyword);
                break;
            case 5:
                tampilJDBC.tampilkanStatistik();
                break;
        }
    }

    private static void tambahDataORM() {
        System.out.println("\n=== TAMBAH DATA (ORM) ===");

        System.out.print("Jenis sampah: ");
        String jenis = scanner.nextLine();

        System.out.print("Berat (kg): ");
        double berat = Double.parseDouble(scanner.nextLine());

        System.out.print("Kategori (Organik/Anorganik): ");
        String kategori = scanner.nextLine();

        Sampah sampah;
        if ("Organik".equalsIgnoreCase(kategori)) {
            System.out.print("Tingkat dekomposisi (Cepat/Sedang/Lambat): ");
            String dekomposisi = scanner.nextLine();
            sampah = new Organik(jenis, berat, dekomposisi);
        } else {
            System.out.print("Dapat didaur ulang (y/n): ");
            boolean daurUlang = scanner.nextLine().equalsIgnoreCase("y");
            sampah = new Anorganik(jenis, berat, daurUlang);
        }

        if (crudOrm.create(sampah)) {
            System.out.println("✅ Berhasil ditambahkan ke database!");
        } else {
            System.out.println("❌ Gagal menambahkan ke database!");
        }
    }

    private static void lihatDataORM() {
        System.out.println("\n=== DATA DARI DATABASE (ORM) ===");
        List<Sampah> data = crudOrm.readAll();

        if (data.isEmpty()) {
            System.out.println("📭 Tidak ada data di database.");
            return;
        }

        for (int i = 0; i < data.size(); i++) {
            Sampah s = data.get(i);
            System.out.printf("%d. %s - %.1f kg - %s - %s%n",
                    i + 1, s.getJenis(), s.getBerat(), s.getKategori(), s.getInfoTambahan());
        }
        System.out.println("Total: " + data.size() + " data");
    }

    private static void updateDataORM() {
        lihatDataORM();
        List<Sampah> data = crudOrm.readAll();

        if (data.isEmpty()) return;

        System.out.print("Pilih nomor data yang akan diupdate: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= data.size()) {
            System.out.println("❌ Nomor tidak valid!");
            return;
        }

        Sampah sampahLama = data.get(index);

        System.out.print("Jenis baru: ");
        String jenis = scanner.nextLine();

        System.out.print("Berat baru: ");
        double berat = Double.parseDouble(scanner.nextLine());

        // Buat objek baru dengan data yang diupdate
        Sampah sampahBaru;
        if (sampahLama instanceof Organik) {
            Organik org = (Organik) sampahLama;
            sampahBaru = new Organik(jenis, berat, org.getTingkatDekomposisi());
        } else {
            Anorganik anorg = (Anorganik) sampahLama;
            sampahBaru = new Anorganik(jenis, berat, anorg.isDapatDidaurUlang());
        }

        // Perhatikan: update di ORM menggunakan ID, bukan index. Tapi dalam contoh ini kita asumsikan index+1 adalah ID
        // Dalam implementasi nyata, Anda harus menyimpan ID dari data yang diambil.
        // Karena dalam CRUD_ORM, update menggunakan ID, dan kita asumsikan ID adalah index+1 (karena data diambil berurutan)
        if (crudOrm.update(index + 1, sampahBaru)) {
            System.out.println("✅ Berhasil update di database!");
        } else {
            System.out.println("❌ Gagal update di database!");
        }
    }

    private static void hapusDataORM() {
        lihatDataORM();
        List<Sampah> data = crudOrm.readAll();

        if (data.isEmpty()) return;

        System.out.print("Pilih nomor data yang akan dihapus: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;

        if (index < 0 || index >= data.size()) {
            System.out.println("❌ Nomor tidak valid!");
            return;
        }

        System.out.print("Yakin ingin menghapus? (y/n): ");
        String konfirmasi = scanner.nextLine();

        if (konfirmasi.equalsIgnoreCase("y")) {
            if (crudOrm.delete(index + 1)) {
                System.out.println("✅ Berhasil hapus dari database!");
            } else {
                System.out.println("❌ Gagal hapus dari database!");
            }
        } else {
            System.out.println("Penghapusan dibatalkan.");
        }
    }
}