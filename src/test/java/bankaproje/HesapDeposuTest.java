package bankaproje;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Her test kendi geçici SQLite dosyasıyla, gerçek veritabanı üzerinde çalışır. */
class HesapDeposuTest {

    @TempDir
    Path klasor;

    private HesapDeposu depo;
    private final Hesap ayse = new Hesap("100200", "Ayşe", "Yılmaz", 1500.5, "ayse@ornek.com", "İzmir", "Bornova");

    @BeforeEach
    void kur() throws Exception {
        depo = new HesapDeposu("jdbc:sqlite:" + klasor.resolve("test.db"));
        depo.kurulum();
    }

    @Test
    void acilanHesaplaDogruPinIleGirisYapilir() throws Exception {
        depo.hesapAc(ayse, "1234");
        assertEquals(ayse, depo.girisYap("100200", "1234").orElseThrow());
    }

    @Test
    void yanlisPinVeOlmayanHesapReddedilir() throws Exception {
        depo.hesapAc(ayse, "1234");
        assertTrue(depo.girisYap("100200", "9999").isEmpty());
        assertTrue(depo.girisYap("999999", "1234").isEmpty());
    }

    @Test
    void ayniHesapNumarasiIkinciKezAcilamaz() throws Exception {
        depo.hesapAc(ayse, "1234");
        assertThrows(HesapDeposu.HesapZatenVarException.class, () -> depo.hesapAc(ayse, "5678"));
    }

    @Test
    void sqlInjectionIleGirisYapilamaz() throws Exception {
        depo.hesapAc(ayse, "1234");
        assertTrue(depo.girisYap("100200", "' OR '1'='1").isEmpty());
        assertTrue(depo.girisYap("' OR '1'='1' --", "x").isEmpty());
    }

    @Test
    void kurulumTekrarCalistirilabilir() throws Exception {
        depo.hesapAc(ayse, "1234");
        depo.kurulum();
        assertTrue(depo.girisYap("100200", "1234").isPresent());
    }
}
