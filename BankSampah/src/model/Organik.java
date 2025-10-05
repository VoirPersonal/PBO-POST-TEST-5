package model;

public class Organik extends Sampah {
    // Encapsulation - private field
    private String tingkatDekomposisi;
    
    public Organik(String jenis, double berat, String tingkatDekomposisi) {
        super(jenis, berat, "Organik");
        this.tingkatDekomposisi = tingkatDekomposisi;
    }
    
    // Getter dan Setter
    public String getTingkatDekomposisi() {
        return tingkatDekomposisi;
    }
    
    public void setTingkatDekomposisi(String tingkatDekomposisi) {
        if (tingkatDekomposisi.equalsIgnoreCase("Cepat") || 
            tingkatDekomposisi.equalsIgnoreCase("Sedang") || 
            tingkatDekomposisi.equalsIgnoreCase("Lambat")) {
            this.tingkatDekomposisi = tingkatDekomposisi;
        } else {
            throw new IllegalArgumentException("Tingkat dekomposisi harus Cepat, Sedang, atau Lambat");
        }
    }
    
    // Override abstract method - polymorphism
    @Override
    public String getInfoTambahan() {
        return "Dekomposisi: " + tingkatDekomposisi;
    }
    
    // Override method untuk memberikan info detail yang berbeda - polymorphism
    @Override
    public String getInfoDetail() {
        return super.getInfoDetail() + String.format(", Tingkat Dekomposisi: %s", tingkatDekomposisi);
    }
    
    // Overloading method - polymorphism
    public String getInfoDetail(boolean includeWaktu) {
        if (includeWaktu) {
            return getInfoDetail() + String.format(", Waktu Dekomposisi: %d hari", getWaktuDekomposisi());
        }
        return getInfoDetail();
    }
    
    // Override method hitungHarga - polymorphism
    @Override
    public double hitungHarga() {
        double hargaDasar = super.hitungHargaDasar();
        // Sampah organik dengan harga berbeda berdasarkan tingkat dekomposisi
        switch (tingkatDekomposisi.toLowerCase()) {
            case "cepat":
                return hargaDasar * 0.8; // 80% dari harga dasar
            case "sedang":
                return hargaDasar * 0.6; // 60% dari harga dasar
            case "lambat":
                return hargaDasar * 0.4; // 40% dari harga dasar
            default:
                return hargaDasar * 0.5;
        }
    }
    
    // Method khusus untuk sampah organik
    public int getWaktuDekomposisi() {
        switch (tingkatDekomposisi.toLowerCase()) {
            case "cepat":
                return 30; // 30 hari
            case "sedang":
                return 60; // 60 hari
            case "lambat":
                return 120; // 120 hari
            default:
                return 90;
        }
    }
    
    // Override method dari interface dengan implementasi khusus
    @Override
    public double hitungHargaDenganPajak(double persenPajak) {
        // Sampah organik mendapat diskon pajak 50%
        double harga = hitungHarga();
        double pajak = (harga * persenPajak / 100) * 0.5; // Diskon 50% untuk pajak
        return harga + pajak;
    }
}