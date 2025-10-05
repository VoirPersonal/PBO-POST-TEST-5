package service;

import model.Sampah;
import model.Organik;
import model.Anorganik;
import java.util.ArrayList;

public class SampahService {
    // Encapsulation - private field
    private ArrayList<Sampah> dataSampah = new ArrayList<>();
    
    // Getter untuk akses data (read-only)
    public ArrayList<Sampah> getDataSampah() {
        return new ArrayList<>(dataSampah); // Return copy untuk menjaga encapsulation
    }
    
    // Method untuk mendapatkan jumlah data
    public int getJumlahData() {
        return dataSampah.size();
    }
    
    // Method untuk cek apakah data kosong
    public boolean isDataKosong() {
        return dataSampah.isEmpty();
    }
    
    // Method overloading - polymorphism
    public void tambahSampah(Sampah sampah) {
        dataSampah.add(sampah);
        System.out.println("Data sampah berhasil ditambahkan!");
    }
    
    public void tambahOrganik(String jenis, double berat, String tingkatDekomposisi) {
        try {
            tambahSampah(new Organik(jenis, berat, tingkatDekomposisi));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void tambahAnorganik(String jenis, double berat, boolean dapatDidaurUlang) {
        try {
            tambahSampah(new Anorganik(jenis, berat, dapatDidaurUlang));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Method overloading untuk lihatData - polymorphism
    public void lihatData() {
        lihatData(false); // Default tanpa detail
    }
    
    public void lihatData(boolean denganDetail) {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data sampah.");
            return;
        }
        
        System.out.println("\nDATA SAMPAH" + (denganDetail ? " (DETAIL)" : ""));
        System.out.println("=================================================================");
        
        if (denganDetail) {
            System.out.printf("%-5s %-20s %-10s %-15s %-20s %-15s %-15s%n", 
                             "No.", "Jenis", "Berat", "Kategori", "Info Tambahan", "Harga Dasar", "Harga+Pajak");
            System.out.println("-----------------------------------------------------------------");
            
            double totalHargaDasar = 0;
            double totalHargaPajak = 0;
            
            for (int i = 0; i < dataSampah.size(); i++) {
                Sampah s = dataSampah.get(i);
                double hargaDasar = s.hitungHargaDasar();
                double hargaPajak = s.hitungHargaDenganPajak();
                totalHargaDasar += hargaDasar;
                totalHargaPajak += hargaPajak;
                
                System.out.printf("%-5d %-20s %-10.1f %-15s %-20s %-15.0f %-15.0f%n", 
                                 i + 1, s.getJenis(), s.getBerat(), s.getKategori(), 
                                 s.getInfoTambahan(), hargaDasar, hargaPajak);
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.printf("Total Harga Dasar: Rp %.0f%n", totalHargaDasar);
            System.out.printf("Total Harga + Pajak: Rp %.0f%n", totalHargaPajak);
        } else {
            System.out.printf("%-5s %-20s %-10s %-15s %-20s %-15s%n", 
                             "No.", "Jenis", "Berat", "Kategori", "Info Tambahan", "Harga (Rp)");
            System.out.println("-----------------------------------------------------------------");
            
            double totalHarga = 0;
            for (int i = 0; i < dataSampah.size(); i++) {
                Sampah s = dataSampah.get(i);
                double harga = s.hitungHarga();
                totalHarga += harga;
                
                System.out.printf("%-5d %-20s %-10.1f %-15s %-20s %-15.0f%n", 
                                 i + 1, s.getJenis(), s.getBerat(), s.getKategori(), 
                                 s.getInfoTambahan(), harga);
            }
            System.out.println("-----------------------------------------------------------------");
            System.out.printf("Total Estimasi Nilai: Rp %.0f%n", totalHarga);
        }
    }
    
    public void updateData(int index, String jenis, double berat) {
        if (index >= 0 && index < dataSampah.size()) {
            try {
                Sampah s = dataSampah.get(index);
                s.setJenis(jenis);
                s.setBerat(berat);
                System.out.println("Data berhasil diupdate!");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    public void hapusData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            dataSampah.remove(index);
            System.out.println("Data berhasil dihapus!");
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    // Method untuk mendapatkan data berdasarkan index
    public Sampah getData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            return dataSampah.get(index);
        }
        return null;
    }
    
    // Fitur search yang diperbaiki
    public void cariData(String kataKunci) {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data sampah.");
            return;
        }
        
        ArrayList<Sampah> hasilPencarian = new ArrayList<>();
        
        for (Sampah s : dataSampah) {
            if (s.getJenis().toLowerCase().contains(kataKunci.toLowerCase()) || 
                s.getKategori().toLowerCase().contains(kataKunci.toLowerCase())) {
                hasilPencarian.add(s);
            }
        }
        
        if (hasilPencarian.isEmpty()) {
            System.out.println("Tidak ditemukan data dengan kata kunci '" + kataKunci + "'");
            return;
        }
        
        System.out.println("\nHASIL PENCARIAN: '" + kataKunci + "'");
        System.out.println("=================================================================");
        System.out.printf("%-5s %-20s %-10s %-15s %-20s %-15s%n", 
                         "No.", "Jenis", "Berat", "Kategori", "Info Tambahan", "Harga (Rp)");
        System.out.println("-----------------------------------------------------------------");
        
        for (int i = 0; i < hasilPencarian.size(); i++) {
            Sampah s = hasilPencarian.get(i);
            System.out.printf("%-5d %-20s %-10.1f %-15s %-20s %-15.0f%n", 
                             i + 1, s.getJenis(), s.getBerat(), s.getKategori(), 
                             s.getInfoTambahan(), s.hitungHarga());
        }
    }
    
    // Method tambahan untuk menampilkan detail lengkap
    public void lihatDetailData(int index) {
        if (index >= 0 && index < dataSampah.size()) {
            Sampah s = dataSampah.get(index);
            System.out.println("\n=== DETAIL DATA SAMPAH ===");
            System.out.println(s.getInfoDetail(true)); // Menggunakan overloading method
            System.out.printf("Estimasi Harga Dasar: Rp %.0f%n", s.hitungHargaDasar());
            System.out.printf("Estimasi Harga Final: Rp %.0f%n", s.hitungHarga());
            System.out.printf("Estimasi Harga dengan Pajak: Rp %.0f%n", s.hitungHargaDenganPajak());
            
            if (s instanceof Organik) {
                Organik organik = (Organik) s;
                System.out.printf("Estimasi Waktu Dekomposisi: %d hari%n", 
                                organik.getWaktuDekomposisi());
            } else if (s instanceof Anorganik) {
                Anorganik anorganik = (Anorganik) s;
                System.out.println("Status Lingkungan: " + anorganik.getStatusLingkungan());
                System.out.printf("Estimasi Waktu Terurai: %d tahun%n", 
                                anorganik.getEstimasiWaktuTerurai());
            }
        } else {
            System.out.println("Index tidak valid!");
        }
    }
    
    // Method untuk menghitung statistik
    public void tampilkanStatistik() {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data untuk ditampilkan statistiknya.");
            return;
        }
        
        int jumlahOrganik = 0;
        int jumlahAnorganik = 0;
        double beratOrganik = 0;
        double beratAnorganik = 0;
        double totalNilai = 0;
        double totalNilaiDasar = 0;
        double totalPajak = 0;
        
        for (Sampah s : dataSampah) {
            totalNilai += s.hitungHarga();
            totalNilaiDasar += s.hitungHargaDasar();
            totalPajak += s.hitungHargaDenganPajak() - s.hitungHarga();
            
            if (s instanceof Organik) {
                jumlahOrganik++;
                beratOrganik += s.getBerat();
            } else if (s instanceof Anorganik) {
                jumlahAnorganik++;
                beratAnorganik += s.getBerat();
            }
        }
        
        System.out.println("\n=== STATISTIK BANK SAMPAH ===");
        System.out.println("Total Data: " + dataSampah.size());
        System.out.println("Sampah Organik: " + jumlahOrganik + " item (" + beratOrganik + " kg)");
        System.out.println("Sampah Anorganik: " + jumlahAnorganik + " item (" + beratAnorganik + " kg)");
        System.out.println("Total Berat: " + (beratOrganik + beratAnorganik) + " kg");
        System.out.printf("Total Estimasi Nilai Dasar: Rp %.0f%n", totalNilaiDasar);
        System.out.printf("Total Estimasi Nilai Final: Rp %.0f%n", totalNilai);
        System.out.printf("Total Pajak: Rp %.0f%n", totalPajak);
        System.out.printf("Total Nilai + Pajak: Rp %.0f%n", (totalNilai + totalPajak));
    }
    
    // Method baru untuk demonstrasi polymorphism
    public void demonstrasiPolymorphism() {
        if (dataSampah.isEmpty()) {
            System.out.println("Belum ada data untuk demonstrasi.");
            return;
        }
        
        System.out.println("\n=== DEMONSTRASI POLYMORPHISM ===");
        System.out.println("Menunjukkan konsep Overriding dan Overloading:");
        System.out.println("=" .repeat(60));
        
        for (int i = 0; i < dataSampah.size(); i++) {
            Sampah s = dataSampah.get(i);
            System.out.println("\n" + (i + 1) + ". " + s.getJenis() + " (" + s.getKategori() + "):");
            System.out.println("-".repeat(40));
            
            // Demonstrasi Overriding
            System.out.println("1. Overriding Methods:");
            System.out.println("   • getInfoTambahan(): " + s.getInfoTambahan());
            System.out.println("   • getInfoDetail(): " + s.getInfoDetail());
            System.out.printf("   • hitungHargaDasar(): Rp %.0f%n", s.hitungHargaDasar());
            System.out.printf("   • hitungHarga(): Rp %.0f%n", s.hitungHarga());
            
            // Demonstrasi Overloading
            System.out.println("2. Overloading Methods:");
            System.out.println("   • getInfoDetail(false): " + s.getInfoDetail(false));
            System.out.println("   • getInfoDetail(true): " + s.getInfoDetail(true));
            System.out.printf("   • hitungHargaDenganPajak(): Rp %.0f%n", s.hitungHargaDenganPajak());
            System.out.printf("   • hitungHargaDenganPajak(15): Rp %.0f%n", s.hitungHargaDenganPajak(15));
            
            // Demonstrasi polymorphism dengan instanceof
            if (s instanceof Organik) {
                Organik organik = (Organik) s;
                System.out.println("3. Method Khusus Organik:");
                System.out.println("   • getInfoDetail(true): " + organik.getInfoDetail(true));
                System.out.println("   • getWaktuDekomposisi(): " + organik.getWaktuDekomposisi() + " hari");
            } else if (s instanceof Anorganik) {
                Anorganik anorganik = (Anorganik) s;
                System.out.println("3. Method Khusus Anorganik:");
                System.out.println("   • getInfoDetail(true): " + anorganik.getInfoDetail(true));
                System.out.println("   • getEstimasiWaktuTerurai(): " + anorganik.getEstimasiWaktuTerurai() + " tahun");
                System.out.println("   • getStatusLingkungan(): " + anorganik.getStatusLingkungan());
            }
            
            if (i < dataSampah.size() - 1) {
                System.out.println("\n" + "=" .repeat(60));
            }
        }
    }
}