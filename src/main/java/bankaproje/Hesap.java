package bankaproje;

/** Bir banka hesabı. PIN kodu bu kayıtta tutulmaz; yalnızca giriş kontrolünde kullanılır. */
public record Hesap(String hesapNo, String adi, String soyAdi, double bakiye, String email, String sehir,
        String adres) {
}
