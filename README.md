# Post Test 5 PBO
<br/> **Nama: Fikri Abiyyu Rahman**
<br/> **NIM: 2409116063**
<br/>
<br/> Pada Post Test 5 ini, program Bank Sampah ini saya mengembangkan program dengan mengintegrasikan manajemen database melalui JDBC dan ORM pattern. Implementasi JDBC melalui class TampilDataJDBC memungkinkan koneksi dan pengambilan data dari database MySQL dengan berbagai format tampilan yang fleksibel. Sementara itu, ORM pattern diwujudkan dalam class CRUD_ORM yang berfungsi sebagai jembatan antara model object-oriented dan relational database, melakukan mapping otomatis antara tabel database dengan object Java.

# Implementasi JDBC (Java Database Connectivity)
```java
public class TampilDataJDBC {
    public void tampilkanSemuaData() {
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM sampah")) {
            // Process ResultSet...
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
```
<br/>
<br/>Penerapan JDBC dalam class TampilDataJDBC menunjukkan penggunaan Java Database Connectivity untuk melakukan koneksi dan interaksi dengan database MySQL. JDBC menyediakan API standar yang memungkinkan aplikasi Java terhubung ke berbagai database relational dengan konsisten.

<br/>Dalam implementasi ini, digunakan Statement untuk eksekusi query statis dan PreparedStatement untuk query dinamis yang lebih aman dari SQL injection. Pola try-with-resources memastikan koneksi database ditutup secara otomatis, mencegah kebocoran resource. Hasil query diproses melalui ResultSet yang memungkinkan navigasi melalui baris data, sementara ResultSetMetaData digunakan untuk memeriksa struktur kolom secara dinamis sesuai dengan database yang ada.

# Implementasi ORM (Object-Relational Mapping)
```java
public class CRUD_ORM {
    public boolean create(Sampah sampah) {
        String sql = "INSERT INTO sampah (jenis, berat, kategori, ...) VALUES (?, ?, ?, ...)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, sampah.getJenis());
            stmt.setDouble(2, sampah.getBerat());
            // Mapping object properties to database columns...
        }
    }
    
    private Sampah resultSetToSampah(ResultSet rs) throws SQLException {
        String jenis = rs.getString("jenis");
        double berat = rs.getDouble("berat");
        String kategori = rs.getString("kategori");
        
        if ("Organik".equalsIgnoreCase(kategori)) {
            return new Organik(jenis, berat, rs.getString("tingkat_dekomposisi"));
        } else {
            return new Anorganik(jenis, berat, rs.getBoolean("dapat_didaur_ulang"));
        }
    }
}
```
<br/>Penerapan ORM sederhana dalam class CRUD_ORM berfungsi sebagai mapper antara model object-oriented dan relational database. ORM pattern ini mengatasi perbedaan representasi data dengan melakukan mapping otomatis antara tabel database (sampah) dan object Java (Sampah, Organik, Anorganik).

<br/>Method resultSetToSampah() menunjukkan fase pembacaan ORM, mengonversi data relational dari ResultSet menjadi object Java yang polimorfik. Sebaliknya, method create() dan update() menunjukkan fase penulisan, mengonversi object Java menjadi SQL statement dengan parameter binding yang aman. Implementasi ini juga menangani perbedaan struktur data melalui conditional mapping berdasarkan kolom kategori, serta mengelola nilai NULL dengan tepat menggunakan setNull() dan wasNull().

# Update pada Main Class
```java
public class Main {
    private static SampahService service = new SampahService();
    private static CRUD_ORM crudOrm = new CRUD_ORM();
    private static TampilDataJDBC tampilJDBC = new TampilDataJDBC();
    
    private static void tampilkanMenu() {
        System.out.println("1. Tambah Data Sampah (Memory)");
        System.out.println("2. Lihat Data Sampah (Memory)");
        System.out.println("5. CRUD Database (ORM)");
        System.out.println("6. Tampil Data (JDBC Statement)");
    }
    
    private static void menuCrudORM() {
        System.out.println("=== CRUD OPERATIONS (ORM) ===");
        System.out.println("1. Create - Tambah Data");
        System.out.println("2. Read - Lihat Semua Data");
        System.out.println("3. Update - Ubah Data");
        System.out.println("4. Delete - Hapus Data");
    }
}
```
<br/>Update pada Main class menunjukkan evolusi arsitektur dari pure memory-based menuju hybrid system dengan database persistence. Main class sekarang berfungsi sebagai controller utama yang mengkoordinasi tiga layer berbeda: operasi memory melalui SampahService, operasi ORM melalui CRUD_ORM, dan operasi tampilan JDBC melalui TampilDataJDBC.

<br/>Struktur menu yang diperbarui memisahkan secara jelas antara operasi sementara (memory) dan operasi permanen (database), memberikan user fleksibilitas untuk memilih model penyimpanan sesuai kebutuhan. Method menuCrudORM() dan menuTampilJDBC() mengimplementasikan pola navigasi yang konsisten, menjaga separation of concerns sambil mempertahankan user experience yang intuitif. Validasi input yang robust pada setiap menu mencegah invalid operations dan memastikan data integrity across different storage layers.

# Abstraction
### Interface HargaSampah
```java
public interface HargaSampah {
    double hitungHargaDasar();
    
    default double hitungHargaDenganPajak(double persenPajak) {
        double hargaDasar = hitungHargaDasar();
        return hargaDasar + (hargaDasar * persenPajak / 100);
    }
    
    default double hitungHargaDenganPajak() {
        return hitungHargaDenganPajak(10);
    }
}
```
### Modifikasi Class Sampah yang Mengimplementasikan Interface
```java
public abstract class Sampah implements HargaSampah {
    // Implementasi method dari interface HargaSampah
    @Override
    public double hitungHargaDasar() {
        return berat * 1000;
    }
}
```
Penerapan abstraction terlihat pada pembuatan interface HargaSampah yang mendefinisikan kontrak untuk perhitungan harga sampah. Interface ini mengandung abstract method hitungHargaDasar() yang wajib diimplementasikan oleh class yang menggunakannya. Class Sampah yang sebelumnya hanya abstract class, sekarang juga mengimplementasikan interface HargaSampah, sehingga harus memberikan implementasi konkret untuk method hitungHargaDasar().
<br/>
<br/>Kombinasi abstraction ini menunjukkan penggunaan bersama antara abstract class dan interface dalam satu program, di mana Sampah sebagai abstract class meng-handle struktur dasar sedangkan interface HargaSampah meng-handle perilaku terkait perhitungan harga.

# Polymorphism
### Overloading dalam Interface
```java
default double hitungHargaDenganPajak(double persenPajak) {
    // versi dengan parameter
}

default double hitungHargaDenganPajak() {
    return hitungHargaDenganPajak(10); // versi tanpa parameter
}
```
### Overloading dalam Class Sampah
```java
public String getInfoDetail() {
    // versi tanpa parameter
}

public String getInfoDetail(boolean includeHarga) {
    if (includeHarga) {
        return getInfoDetail() + String.format(", Harga: Rp %.0f", hitungHargaDasar());
    }
    return getInfoDetail();
}
```
### Overloading dalam Class Organik
```java
public String getInfoDetail(boolean includeWaktu) {
    if (includeWaktu) {
        return getInfoDetail() + String.format(", Waktu Dekomposisi: %d hari", getWaktuDekomposisi());
    }
    return getInfoDetail();
}
```
### Overriding Method dari Interface dalam Class Organik
```java
@Override
public double hitungHargaDenganPajak(double persenPajak) {
    // Sampah organik mendapat diskon pajak 50%
    double harga = hitungHarga();
    double pajak = (harga * persenPajak / 100) * 0.5;
    return harga + pajak;
}
```
### Overriding Method dari Interface dalam Class Anorganik
```java
@Override
public double hitungHargaDenganPajak(double persenPajak) {
    // Sampah anorganik yang tidak dapat didaur ulang kena pajak lebih tinggi
    double harga = hitungHarga();
    double multiplier = dapatDidaurUlang ? 1.0 : 1.5;
    return harga + (harga * persenPajak / 100 * multiplier);
}
```
### Overloading dalam SampahService
```java
public void lihatData() {
    lihatData(false); // versi tanpa parameter
}

public void lihatData(boolean denganDetail) {
    // versi dengan parameter boolean
}
```
Penerapan polymorphism melalui overloading terlihat pada beberapa method yang memiliki nama sama tetapi parameter berbeda. Contohnya pada interface HargaSampah dengan method hitungHargaDenganPajak() yang memiliki dua versi, pada class Sampah dengan method getInfoDetail() yang bisa dipanggil dengan atau tanpa parameter boolean, dan pada SampahService dengan method lihatData() yang memiliki dua bentuk pemanggilan.

<br/>Untuk overriding, terlihat pada class Organik dan Anorganik yang mengoverride method hitungHargaDenganPajak() dari interface dengan implementasi yang berbeda-beda. Class Organik memberikan diskon pajak 50% sebagai insentif untuk sampah organik, sedangkan class Anorganik menerapkan multiplier berbeda dimana sampah yang tidak bisa didaur ulang dikenakan pajak 50% lebih tinggi. Ini menunjukkan polymorphism dimana method yang sama dipanggil pada object yang berbeda akan menghasilkan perilaku yang berbeda sesuai dengan implementasi spesifik masing-masing class.


# Encapsulation (Getter dan Setter)
```java
private String nama;
public String getNama() { return nama; }
public void setNama(String nama) { this.nama = nama; }

```
<br/>Penerapan encapsulation terlihat pada class Sampah, di mana atribut id, nama, dan berat dibuat private dan hanya dapat diakses melalui method getter dan setter, misalnya getNama() dan setNama(String nama). Hal ini memastikan data tidak bisa dimanipulasi secara langsung dari luar class.

# Inheritance (Pewarisan)
```java
// Organik.java
public class Organik extends Sampah {
    public Organik(int id, String nama, double berat) {
        super(id, nama, berat);
    }
}
```
```java
// Anorganik.java
public class Anorganik extends Sampah {
    public Anorganik(int id, String nama, double berat) {
        super(id, nama, berat);
    }
}
```
<br/>Inheritance salah satunya dapat terlihat dari adanya superclass Sampah yang diturunkan ke subclass Organik dan Anorganik. Dengan inheritance ini, Organik dan Anorganik tidak perlu lagi menuliskan ulang atribut id, nama, dan berat, karena sudah diwarisi dari superclass Sampah.

# Overriding
```java
@Override
public String toString() {
    return "Sampah Organik: " + getNama() + " (" + getBerat() + " kg)";
}
```
```java
@Override
public String toString() {
    return "Sampah Anorganik: " + getNama() + " (" + getBerat() + " kg)";
}
```
<br/>Overriding digunakan pada method toString() di subclass, agar informasi sampah tampil sesuai jenisnya. Misalnya di Organik.java dan Anorganik.java. Dengan overriding ini, meskipun kedua subclass menggunakan method toString(), hasil output yang ditampilkan berbeda sesuai jenis sampah.


# Alur Program
Program ini menggunakan perulangan while agar pengguna dapat berinteraksi terus-menerus tanpa perlu menjalankan ulang program setelah setiap operasi.
## Tampilan Menu Utama
<br/><img width="453" height="205" alt="image" src="https://github.com/user-attachments/assets/ef8bf9ac-6e8c-4ebc-b4b7-3c70e40c4aa5" />
<br/>
<br/>Pengguna diberikan kebebasan untuk memilih operasi yang diinginkan dengan memasukkan angka 1 sampai 6. Sistem akan memvalidasi input tersebut untuk memastikan hanya angka yang acceptable yang diproses.

## Mekanisme Penambahan Data (Create)
<br/><img width="708" height="457" alt="image" src="https://github.com/user-attachments/assets/3436da86-9cd3-4c9c-8948-8ef4fb547a37" />
<br><img width="711" height="458" alt="image" src="https://github.com/user-attachments/assets/b82d2e22-0482-4b1a-8fbe-564ed3db1718" />
<br/>
<br/> Disini kita dapat memasukkan sampah berdasarkan tipe sampahnya apakah dia organik ataupun anorganik, setelah memilih tipe sampah sistem akan meminta jenis sampah, berat sampah, dan apakah sampah dapat didaur ulang. Jika bisa didaur ulang maka sistem akan memberikan opsi seberapa cepat sampah dapat di dekomposisi.

## Mekanisme Penampilan Data (Read)
<br/><img width="880" height="404" alt="image" src="https://github.com/user-attachments/assets/b8b6ce08-a9b0-4933-8612-8e413f97790d" />
<br/><img width="565" height="236" alt="image" src="https://github.com/user-attachments/assets/8eef91ed-14d3-4c22-b14f-46c266c13260" />
<br/>
<br/>Saat pengguna memilih opsi menampilkan data, sistem akan mengecek terlebih dahulu apakah ada data yang tersimpan. Bila belum ada, pengguna diberi tahu bahwa belum ada data sampah yang tersedia. Bila ada, program menampilkan daftar semua sampah dalam bentuk tabel yang rapi: tiap baris menunjukkan satu entri data, dengan nomor urut, jenis sampah, dan beratnya. Ini memudahkan pengguna untuk melihat isi data dengan cepat.

## Mekanisme Pembaruan Data (Update)
<br/><img width="834" height="516" alt="image" src="https://github.com/user-attachments/assets/be097aca-18a3-4bcb-a423-20c272d34e07" />
<br/>
<br/>Untuk memperbarui data, pengguna akan melihat daftar data yang sudah tersimpan agar dapat menentukan dengan jelas mana yang ingin diubah. Setelah memilih nomor data, program menampilkan data terpilih sebagai konfirmasi, kemudian meminta input baru untuk jenis dan berat. Jika pengguna memasukkan nilai sama dengan sebelumnya, itu juga diperbolehkan. Setelah diubah, program menyimpan perubahan dan menampilkan kembali daftar data terkini.

## Mekanisme Penghapusan Data (Delete)
<br/><img width="931" height="896" alt="image" src="https://github.com/user-attachments/assets/d44de242-d61a-43c5-bd1b-1ff496783e4f" />
<br/>
<br/>Opsi menghapus data juga dilakukan secara berhati-hati. Program memunculkan daftar data, pengguna memilih data mana yang akan dihapus, dan sebelum benar-benar dihapus, program menampilkan data tersebut lagi sebagai konfirmasi. Pengguna dapat membatalkan jika ternyata memilih entri yang salah. Bila konfirmasi diterima, data dihapus dan daftar data diperbarui sehingga entri yang dihapus sudah tidak muncul lagi.

## Mekanisme Keluar dari Program
<br/><img width="884" height="389" alt="image" src="https://github.com/user-attachments/assets/5931b035-b076-4694-8a69-3bb0da66b7d7" />
<br/>
<br/>Fitur keluar dari program memungkinkan pengguna mengakhiri interaksi. Setelah memilih opsi keluar, program berhenti berjalan.

## Nilai Tambah
### MVC (Model View Controller)
<br/><img width="293" height="203" alt="image" src="https://github.com/user-attachments/assets/769cf5f1-a28e-4c10-8456-7355c313e411" />
<br/>
<br/>Struktur program ini dibuat dengan konsep MVC (Model–View–Controller) sederhana. Di bagian model ada class Sampah, Organik, dan Anorganik yang dipakai untuk menyimpan data. Lalu di bagian service ada SampahService yang ngurus semua logika CRUD, mulai dari nambah, ubah, hapus, sampai validasi dan search. Terakhir ada main, yaitu class PT1 yang jadi tempat menu ditampilkan ke pengguna. Dengan pembagian kayak gini, program jadi lebih teratur karena tiap bagian punya fungsi masing-masing.
### Validasi input
<br/><img width="476" height="433" alt="image" src="https://github.com/user-attachments/assets/63c0496f-5a2c-4585-95ed-93fa79af2059" />
<br/>
<br/>Program ini juga sudah diberi pengecekan input biar data yang masuk nggak sembarangan. Contohnya kalau pengguna salah masukin pilihan menu, sistem bakal kasih pesan kalau input nggak valid dan minta coba lagi. Hal yang sama juga berlaku saat masukin data sampah, misalnya berat harus berupa angka. Jadi kalau ada kesalahan, program nggak langsung error, tapi ngarahin pengguna untuk ngulang input. Hasilnya, data yang tersimpan lebih rapi dan sesuai aturan.
###  Fitur search
<br/><img width="697" height="772" alt="image" src="https://github.com/user-attachments/assets/345f470f-88f7-48af-a4a0-bbf997eb4fd0" />
<br/>
<br/>Selain fitur CRUD, program ini juga dilengkapi dengan fitur pencarian. Ketika pengguna memilih menu search, sistem akan meminta kata kunci yang ingin dicari, biasanya berupa nama atau jenis sampah. Program kemudian akan menelusuri daftar data yang tersimpan dan menampilkan hasil apabila data yang dicari ditemukan. Jika tidak ada data yang sesuai, sistem akan memberikan pesan bahwa data tidak ditemukan. Dengan adanya fitur ini, pengguna dapat lebih mudah menemukan data tertentu tanpa harus menelusuri seluruh daftar secara manual.
