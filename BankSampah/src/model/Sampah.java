package model;

import service.HargaSampah;

public abstract class Sampah implements HargaSampah {
    // Encapsulation - private fields
    private String jenis;
    private double berat;
    private String kategori;
    
    // Constructor
    public Sampah(String jenis, double berat, String kategori) {
        this.jenis = jenis;
        this.berat = berat;
        this.kategori = kategori;
    }
        
    // Getter methods
    public String getJenis() {
        return jenis;
    }
    
    public double getBerat() {
        return berat;
    }
    
    public String getKategori() {
        return kategori;
    }
    
    // Setter methods
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
    
    public void setBerat(double berat) {
        if (berat > 0) {
            this.berat = berat;
        } else {
            throw new IllegalArgumentException("Berat harus lebih dari 0");
        }
    }
    
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
    
    // Implementasi method dari interface HargaSampah
    @Override
    public double hitungHargaDasar() {
        return berat * 1000; // Rp 1000 per kg sebagai harga dasar
    }
    
    // Abstract method untuk polymorphism
    public abstract String getInfoTambahan();
    
    // Method yang dapat di-override - polymorphism
    public String getInfoDetail() {
        return String.format("Jenis: %s, Berat: %.1f kg, Kategori: %s", 
                           jenis, berat, kategori);
    }
    
    // Overloading method - polymorphism
    public String getInfoDetail(boolean includeHarga) {
        if (includeHarga) {
            return getInfoDetail() + String.format(", Harga: Rp %.0f", hitungHargaDasar());
        }
        return getInfoDetail();
    }
    
    // Method untuk menghitung harga (dapat di-override) - polymorphism
    public double hitungHarga() {
        return hitungHargaDasar(); // Menggunakan implementasi dasar
    }
    
    @Override
    public String toString() {
        return String.format("%-20s %-10.1f %-15s %-20s", 
                           jenis, berat, kategori, getInfoTambahan());
    }
}