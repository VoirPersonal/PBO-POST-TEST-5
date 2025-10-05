package model;

public class Anorganik extends Sampah {
    // Encapsulation - private field
    private boolean dapatDidaurUlang;
    
    public Anorganik(String jenis, double berat, boolean dapatDidaurUlang) {
        super(jenis, berat, "Anorganik");
        this.dapatDidaurUlang = dapatDidaurUlang;
    }
    
    // Getter dan Setter
    public boolean isDapatDidaurUlang() {
        return dapatDidaurUlang;
    }
    
    public void setDapatDidaurUlang(boolean dapatDidaurUlang) {
        this.dapatDidaurUlang = dapatDidaurUlang;
    }
    
    // Override abstract method - polymorphism
    @Override
    public String getInfoTambahan() {
        return "Daur Ulang: " + (dapatDidaurUlang ? "Ya" : "Tidak");
    }
    
    // Override method untuk memberikan info detail yang berbeda - polymorphism
    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + String.format(", Dapat Didaur Ulang: %s", 
                                                    dapatDidaurUlang ? "Ya" : "Tidak");
    }
    
    // Overloading method - polymorphism
    public String getInfoDetail(boolean includeEstimasi) {
        if (includeEstimasi) {
            return getInfoDetail() + String.format(", Estimasi Terurai: %d tahun", getEstimasiWaktuTerurai());
        }
        return getInfoDetail();
    }
    
    // Override method hitungHarga - polymorphism
    @Override
    public double hitungHarga() {
        double hargaDasar = super.hitungHargaDasar();
        if (dapatDidaurUlang) {
            return hargaDasar * 1.5; // 150% dari harga dasar jika dapat didaur ulang
        } else {
            return hargaDasar * 0.3; // 30% dari harga dasar jika tidak dapat didaur ulang
        }
    }
    
    // Method khusus untuk sampah anorganik
    public String getStatusLingkungan() {
        if (dapatDidaurUlang) {
            return "Ramah Lingkungan - Dapat Didaur Ulang";
        } else {
            return "Perlu Perhatian Khusus - Sulit Terurai";
        }
    }
    
    // Method untuk menghitung estimasi waktu terurai (dalam tahun)
    public int getEstimasiWaktuTerurai() {
        if (dapatDidaurUlang) {
            if (getJenis().toLowerCase().contains("kertas")) {
                return 1;
            } else if (getJenis().toLowerCase().contains("plastik")) {
                return 50;
            } else if (getJenis().toLowerCase().contains("logam")) {
                return 100;
            } else {
                return 25;
            }
        } else {
            return 500;
        }
    }
    
    // Override method dari interface dengan implementasi khusus
    @Override
    public double hitungHargaDenganPajak(double persenPajak) {
        // Sampah anorganik yang tidak dapat didaur ulang kena pajak lebih tinggi
        double harga = hitungHarga();
        double multiplier = dapatDidaurUlang ? 1.0 : 1.5; // 50% lebih tinggi untuk yang tidak bisa didaur ulang
        return harga + (harga * persenPajak / 100 * multiplier);
    }
}