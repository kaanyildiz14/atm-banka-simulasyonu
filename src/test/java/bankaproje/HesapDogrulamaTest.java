package bankaproje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HesapDogrulamaTest {

    private static Optional<String> dogrula(String hesapNo, String bakiye, String pin, String email) {
        return HesapDogrulama.dogrula(hesapNo, "Ayşe", "Yılmaz", bakiye, pin, email, "İzmir", "Bornova");
    }

    @Test
    void gecerliFormHataVermez() {
        assertTrue(dogrula("100200", "1500,50", "1234", "ayse@ornek.com").isEmpty());
    }

    @Test
    void bosAlanKabulEdilmez() {
        assertEquals("Boş bırakılmaz", HesapDogrulama.dogrula("1", "", "x", "1", "1234", "a@b.co", "x", "x").orElseThrow());
    }

    @ParameterizedTest
    @CsvSource({
            "12, 100, 1234, a@b.co, Hesap numarası 4-12 haneli bir sayı olmalı",
            "12ab34, 100, 1234, a@b.co, Hesap numarası 4-12 haneli bir sayı olmalı",
            "100200, 100, 12, a@b.co, PIN kodu 4 haneli bir sayı olmalı",
            "100200, 100, 12a4, a@b.co, PIN kodu 4 haneli bir sayı olmalı",
            "100200, yüz, 1234, a@b.co, Bakiye sıfır ya da pozitif bir sayı olmalı",
            "100200, -5, 1234, a@b.co, Bakiye sıfır ya da pozitif bir sayı olmalı",
            "100200, 100, 1234, eposta-degil, Geçerli bir e-posta adresi girin" })
    void kurallaraUymayanAlanlar(String hesapNo, String bakiye, String pin, String email, String beklenen) {
        assertEquals(beklenen, dogrula(hesapNo, bakiye, pin, email).orElseThrow());
    }

    @Test
    void bakiyeVirgulVeNoktaIleOkunur() {
        assertEquals(1500.5, HesapDogrulama.bakiyeOku("1500,50").orElseThrow());
        assertEquals(1500.5, HesapDogrulama.bakiyeOku(" 1500.50 ").orElseThrow());
        assertTrue(HesapDogrulama.bakiyeOku("NaN").isEmpty());
    }
}
