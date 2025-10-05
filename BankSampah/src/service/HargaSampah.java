package service;

public interface HargaSampah {
    // Abstract method untuk menghitung harga dasar
    double hitungHargaDasar();
    
    // Default method - contoh polymorphism melalui interface
    default double hitungHargaDenganPajak(double persenPajak) {
        double hargaDasar = hitungHargaDasar();
        return hargaDasar + (hargaDasar * persenPajak / 100);
    }
    
    // Overloading dalam interface - polymorphism
    default double hitungHargaDenganPajak() {
        return hitungHargaDenganPajak(10); // Default pajak 10%
    }
}