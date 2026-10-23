package bankaproje;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

/**
 * Hesapların SQLite işlemleri. Arayüzden ayrı olduğu için testlerde geçici bir
 * veritabanıyla doğrudan denenebilir.
 */
public class HesapDeposu {

    /** Uygulamanın kullandığı veritabanı: çalıştırıldığı klasördeki banka.db */
    public static final HesapDeposu VARSAYILAN = new HesapDeposu("jdbc:sqlite:banka.db");

    /** Aynı hesap numarasıyla ikinci bir hesap açılmaya çalışıldığında fırlatılır. */
    public static class HesapZatenVarException extends Exception {
        private static final long serialVersionUID = 1L;

        public HesapZatenVarException(String hesapNo) {
            super("Bu hesap numarası zaten kullanılıyor: " + hesapNo);
        }
    }

    private final String dbUrl;

    public HesapDeposu(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    private Connection baglantiAc() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }

    /** Tablo yoksa oluşturur; uygulama ilk açıldığında çağrılır. */
    public void kurulum() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ATMTablo ("
                + "HesapNo TEXT PRIMARY KEY, Adi TEXT NOT NULL, SoyAdi TEXT NOT NULL, "
                + "Bakiye REAL NOT NULL DEFAULT 0 CHECK (Bakiye >= 0), PinKodu TEXT NOT NULL, "
                + "Email TEXT, Sehir TEXT, Adres TEXT)";
        try (Connection conn = baglantiAc(); Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }

    /** Hesap numarası ve PIN eşleşirse hesabı döner. */
    public Optional<Hesap> girisYap(String hesapNo, String pinKodu) throws SQLException {
        String sql = "SELECT * FROM ATMTablo WHERE HesapNo = ? AND PinKodu = ?";
        try (Connection conn = baglantiAc(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hesapNo.trim());
            ps.setString(2, pinKodu.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Hesap(rs.getString("HesapNo"), rs.getString("Adi"), rs.getString("SoyAdi"),
                        rs.getDouble("Bakiye"), rs.getString("Email"), rs.getString("Sehir"),
                        rs.getString("Adres")));
            }
        }
    }

    public void hesapAc(Hesap hesap, String pinKodu) throws SQLException, HesapZatenVarException {
        String sql = "INSERT INTO ATMTablo (HesapNo,Adi,SoyAdi,Bakiye,PinKodu,Email,Sehir,Adres) "
                + "VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = baglantiAc(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hesap.hesapNo().trim());
            ps.setString(2, hesap.adi().trim());
            ps.setString(3, hesap.soyAdi().trim());
            ps.setDouble(4, hesap.bakiye());
            ps.setString(5, pinKodu.trim());
            ps.setString(6, hesap.email().trim());
            ps.setString(7, hesap.sehir().trim());
            ps.setString(8, hesap.adres().trim());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (String.valueOf(e.getMessage()).contains("UNIQUE")) {
                throw new HesapZatenVarException(hesap.hesapNo());
            }
            throw e;
        }
    }
}
