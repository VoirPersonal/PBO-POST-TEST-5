// CRUD_ORM.java - DISESUAIKAN DENGAN STRUKTUR DATABASE ANDA
package service;

import model.Sampah;
import model.Organik;
import model.Anorganik;
import model.Database;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CRUD_ORM {
    
    // CREATE - Simpan data sampah ke database
    public boolean create(Sampah sampah) {
        String sql = "INSERT INTO sampah (jenis, berat, kategori, tingkat_dekomposisi, dapat_didaur_ulang) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, sampah.getJenis());
            stmt.setDouble(2, sampah.getBerat());
            stmt.setString(3, sampah.getKategori());
            
            // Set kolom berdasarkan jenis sampah
            if (sampah instanceof Organik) {
                Organik organik = (Organik) sampah;
                stmt.setString(4, organik.getTingkatDekomposisi());
                stmt.setNull(5, Types.BOOLEAN); // null untuk anorganik
            } else if (sampah instanceof Anorganik) {
                Anorganik anorganik = (Anorganik) sampah;
                stmt.setNull(4, Types.VARCHAR); // null untuk organik
                stmt.setBoolean(5, anorganik.isDapatDidaurUlang());
            } else {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.BOOLEAN);
            }
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error create: " + e.getMessage());
            return false;
        }
    }
    
    // READ - Ambil semua data sampah
    public List<Sampah> readAll() {
        List<Sampah> list = new ArrayList<>();
        String sql = "SELECT * FROM sampah";
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Sampah sampah = resultSetToSampah(rs);
                if (sampah != null) {
                    list.add(sampah);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Error readAll: " + e.getMessage());
            System.out.println("Pastikan tabel 'sampah' sudah dibuat dengan struktur yang benar!");
        }
        
        return list;
    }
    
    // READ - Ambil data by ID
    public Sampah readById(int id) {
        String sql = "SELECT * FROM sampah WHERE id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return resultSetToSampah(rs);
            }
            
        } catch (SQLException e) {
            System.out.println("Error readById: " + e.getMessage());
        }
        
        return null;
    }
    
    // UPDATE - Update data sampah
    public boolean update(int id, Sampah sampah) {
        String sql = "UPDATE sampah SET jenis=?, berat=?, kategori=?, tingkat_dekomposisi=?, dapat_didaur_ulang=? WHERE id=?";
        
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, sampah.getJenis());
            stmt.setDouble(2, sampah.getBerat());
            stmt.setString(3, sampah.getKategori());
            
            // Set kolom berdasarkan jenis sampah
            if (sampah instanceof Organik) {
                Organik organik = (Organik) sampah;
                stmt.setString(4, organik.getTingkatDekomposisi());
                stmt.setNull(5, Types.BOOLEAN);
            } else if (sampah instanceof Anorganik) {
                Anorganik anorganik = (Anorganik) sampah;
                stmt.setNull(4, Types.VARCHAR);
                stmt.setBoolean(5, anorganik.isDapatDidaurUlang());
            } else {
                stmt.setNull(4, Types.VARCHAR);
                stmt.setNull(5, Types.BOOLEAN);
            }
            
            stmt.setInt(6, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error update: " + e.getMessage());
            return false;
        }
    }
    
    // DELETE - Hapus data sampah
    public boolean delete(int id) {
        String sql = "DELETE FROM sampah WHERE id = ?";
        
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error delete: " + e.getMessage());
            return false;
        }
    }
    
    // Helper method untuk convert ResultSet ke Object Sampah - DISESUAIKAN
    private Sampah resultSetToSampah(ResultSet rs) throws SQLException {
        try {
            String jenis = rs.getString("jenis");
            double berat = rs.getDouble("berat");
            String kategori = rs.getString("kategori");
            
            if ("Organik".equalsIgnoreCase(kategori)) {
                String tingkatDekomposisi = rs.getString("tingkat_dekomposisi");
                // Handle null value
                if (tingkatDekomposisi == null) {
                    tingkatDekomposisi = "Sedang"; // default value
                }
                return new Organik(jenis, berat, tingkatDekomposisi);
            } else if ("Anorganik".equalsIgnoreCase(kategori)) {
                boolean dapatDidaurUlang = rs.getBoolean("dapat_didaur_ulang");
                // Handle null value - jika kolom NULL, anggap false
                if (rs.wasNull()) {
                    dapatDidaurUlang = false;
                }
                return new Anorganik(jenis, berat, dapatDidaurUlang);
            }
        } catch (SQLException e) {
            System.out.println("Error mapping ResultSet to Sampah: " + e.getMessage());
        }
        
        return null;
    }
    
    // Method untuk mendapatkan jumlah data
    public int getCount() {
        String sql = "SELECT COUNT(*) as total FROM sampah";
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.out.println("Error getCount: " + e.getMessage());
        }
        
        return 0;
    }
    
    // Method untuk mengecek apakah tabel ada
    public boolean isTableExists() {
        String sql = "SELECT 1 FROM sampah LIMIT 1";
        
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}