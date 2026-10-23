package bankaproje;

import java.util.Optional;

/** Yeni hesap formunun kuralları. */
public final class HesapDogrulama {

    private HesapDogrulama() {
    }

    /** Hata varsa kullanıcıya gösterilecek mesajı, yoksa boş döner. */
    public static Optional<String> dogrula(String hesapNo, String adi, String soyAdi, String bakiye, String pinKodu,
            String email, String sehir, String adres) {
        for (String alan : new String[] { hesapNo, adi, soyAdi, bakiye, pinKodu, email, sehir, adres }) {
            if (alan == null || alan.isBlank()) {
                return Optional.of("Boş bırakılmaz");
            }
        }
        if (!hesapNo.trim().matches("\\d{4,12}")) {
            return Optional.of("Hesap numarası 4-12 haneli bir sayı olmalı");
        }
        if (!pinKodu.trim().matches("\\d{4}")) {
            return Optional.of("PIN kodu 4 haneli bir sayı olmalı");
        }
        if (bakiyeOku(bakiye).isEmpty()) {
            return Optional.of("Bakiye sıfır ya da pozitif bir sayı olmalı");
        }
        if (!email.trim().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            return Optional.of("Geçerli bir e-posta adresi girin");
        }
        return Optional.empty();
    }

    /** "1500", "1500,50" ve "1500.50" biçimlerini kabul eder; geçersiz ya da negatifse boş döner. */
    public static Optional<Double> bakiyeOku(String metin) {
        try {
            double deger = Double.parseDouble(metin.trim().replace(',', '.'));
            return deger >= 0 && Double.isFinite(deger) ? Optional.of(deger) : Optional.empty();
        } catch (NumberFormatException | NullPointerException e) {
            return Optional.empty();
        }
    }
}
