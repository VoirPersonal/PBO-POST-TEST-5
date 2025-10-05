// TampilDataJDBC.java - DISESUAIKAN DENGAN STRUKTUR DATABASE ANDA
package service;

import model.Database;
import java.sql.*;

public class TampilDataJDBC {
    
    // Menampilkan semua data dengan Statement
    public void tampilkanSemuaData() {
        System.out.println("\n=== DATA DARI DATABASE (JDBC Statement) ===");
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sampah")) {
            
            // Header tabel sesuai struktur database
            System.out.printf("%-3s %-20s %-8s %-12s %-20s %-20s%n", 
                "ID", "Jenis", "Berat", "Kategori", "Tingkat Dekomposisi", "Dapat Didaur Ulang");
            System.out.println("-".repeat(90));
            
            int count = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String jenis = rs.getString("jenis");
                double berat = rs.getDouble("berat");
                String kategori = rs.getString("kategori");
                String tingkatDekomposisi = rs.getString("tingkat_dekomposisi");
                Boolean dapatDidaurUlang = rs.getBoolean("dapat_didaur_ulang");
                
                // Handle null values
                String displayTingkat = (tingkatDekomposisi != null) ? tingkatDekomposisi : "-";
                String displayDaurUlang = "-";
                if (dapatDidaurUlang != null) {
                    displayDaurUlang = dapatDidaurUlang ? "Ya" : "Tidak";
                }
                
                System.out.printf("%-3d %-20s %-8.1f %-12s %-20s %-20s%n",
                    id, jenis, berat, kategori, displayTingkat, displayDaurUlang);
                count++;
            }
            
            System.out.println("-".repeat(90));
            System.out.println("Total data: " + count);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Pastikan database sudah di-setup dengan benar!");
        }
    }
    
    // Menampilkan data by kategori
    public void tampilkanByKategori(String kategori) {
        String sql = "SELECT * FROM sampah WHERE kategori = '" + kategori + "'";
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== DATA " + kategori.toUpperCase() + " (JDBC) ===");
            System.out.printf("%-3s %-20s %-8s %-12s %-20s %-20s%n", 
                "ID", "Jenis", "Berat", "Kategori", "Tingkat Dekomposisi", "Dapat Didaur Ulang");
            System.out.println("-".repeat(90));
            
            int count = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String jenis = rs.getString("jenis");
                double berat = rs.getDouble("berat");
                String kategoriDB = rs.getString("kategori");
                String tingkatDekomposisi = rs.getString("tingkat_dekomposisi");
                Boolean dapatDidaurUlang = rs.getBoolean("dapat_didaur_ulang");
                
                String displayTingkat = (tingkatDekomposisi != null) ? tingkatDekomposisi : "-";
                String displayDaurUlang = "-";
                if (dapatDidaurUlang != null) {
                    displayDaurUlang = dapatDidaurUlang ? "Ya" : "Tidak";
                }
                
                System.out.printf("%-3d %-20s %-8.1f %-12s %-20s %-20s%n",
                    id, jenis, berat, kategoriDB, displayTingkat, displayDaurUlang);
                count++;
            }
            
            System.out.println("-".repeat(90));
            System.out.println("Total data " + kategori + ": " + count);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Menampilkan data dengan filter menggunakan PreparedStatement
    public void tampilkanDenganFilter(String keyword) {
        String sql = "SELECT * FROM sampah WHERE jenis LIKE ? OR kategori LIKE ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("\n=== HASIL PENCARIAN: '" + keyword + "' (JDBC) ===");
            System.out.printf("%-3s %-20s %-8s %-12s %-20s %-20s%n", 
                "ID", "Jenis", "Berat", "Kategori", "Tingkat Dekomposisi", "Dapat Didaur Ulang");
            System.out.println("-".repeat(90));
            
            int count = 0;
            while (rs.next()) {
                int id = rs.getInt("id");
                String jenis = rs.getString("jenis");
                double berat = rs.getDouble("berat");
                String kategori = rs.getString("kategori");
                String tingkatDekomposisi = rs.getString("tingkat_dekomposisi");
                Boolean dapatDidaurUlang = rs.getBoolean("dapat_didaur_ulang");
                
                String displayTingkat = (tingkatDekomposisi != null) ? tingkatDekomposisi : "-";
                String displayDaurUlang = "-";
                if (dapatDidaurUlang != null) {
                    displayDaurUlang = dapatDidaurUlang ? "Ya" : "Tidak";
                }
                
                System.out.printf("%-3d %-20s %-8.1f %-12s %-20s %-20s%n",
                    id, jenis, berat, kategori, displayTingkat, displayDaurUlang);
                count++;
            }
            
            System.out.println("-".repeat(90));
            System.out.println("Ditemukan: " + count + " data");
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    // Menampilkan statistik data
    public void tampilkanStatistik() {
        String sql = "SELECT kategori, COUNT(*) as jumlah, SUM(berat) as total_berat FROM sampah GROUP BY kategori";
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\n=== STATISTIK DATABASE (JDBC) ===");
            System.out.printf("%-12s %-8s %-12s%n", 
                "Kategori", "Jumlah", "Total Berat");
            System.out.println("-".repeat(40));
            
            int totalJumlah = 0;
            double totalBerat = 0;
            
            while (rs.next()) {
                String kategori = rs.getString("kategori");
                int jumlah = rs.getInt("jumlah");
                double berat = rs.getDouble("total_berat");
                
                System.out.printf("%-12s %-8d %-12.1f%n",
                    kategori, jumlah, berat);
                
                totalJumlah += jumlah;
                totalBerat += berat;
            }
            
            System.out.println("-".repeat(40));
            System.out.printf("%-12s %-8d %-12.1f%n",
                "TOTAL", totalJumlah, totalBerat);
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}