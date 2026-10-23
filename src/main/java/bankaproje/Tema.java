package bankaproje;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.inter.FlatInterFont;

/** MYO Bank'ın görünümü: lacivert vurgu, beyaz kartlar, Inter yazı tipi. Tüm ekranlar buradan beslenir. */
public final class Tema {

    public static final Color LACIVERT = new Color(0x13315C);
    public static final Color ZEMIN = new Color(0xF2F5F9);
    public static final Color CIZGI = new Color(0xDDE3EA);
    public static final Color SOLUK_YAZI = new Color(0x5A6878);

    private Tema() {
    }

    public static void kur() {
        FlatInterFont.install();
        FlatLaf.setPreferredFontFamily(FlatInterFont.FAMILY);
        FlatLaf.setGlobalExtraDefaults(Map.of("@accentColor", "#1D4ED8"));
        FlatLightLaf.setup();
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 12);
        UIManager.put("TextField.margin", new Insets(8, 12, 8, 12));
        UIManager.put("PasswordField.margin", new Insets(8, 12, 8, 12));
        UIManager.put("Button.margin", new Insets(9, 16, 9, 16));
    }

    /** Beyaz, ince çerçeveli içerik kartı. */
    public static JPanel kart() {
        JPanel kart = new JPanel();
        kart.setBackground(Color.WHITE);
        kart.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(CIZGI),
                BorderFactory.createEmptyBorder(28, 28, 28, 28)));
        return kart;
    }

    /** Ekranın ana eylemi: dolgulu, lacivert-mavi. */
    public static JButton anaButon(String yazi) {
        JButton b = new JButton(yazi);
        // Enter tuşuyla çalışan "varsayılan buton" olduğunda FlatLaf ayrı (default.*) renkler kullanır
        b.putClientProperty(FlatClientProperties.STYLE, "background: $Component.accentColor; foreground: #ffffff; "
                + "default.background: $Component.accentColor; default.foreground: #ffffff; "
                + "font: bold; borderWidth: 0; default.borderWidth: 0");
        return b;
    }

    /** İkincil eylem: çerçevesiz, bağlantı gibi. */
    public static JButton baglantiButonu(String yazi) {
        JButton b = new JButton(yazi);
        b.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        b.setForeground(new Color(0x1D4ED8));
        return b;
    }

    public static JLabel baslik(String yazi, int buyume) {
        JLabel l = new JLabel(yazi);
        l.putClientProperty(FlatClientProperties.STYLE, "font: bold +" + buyume);
        return l;
    }

    public static JLabel aciklama(String yazi) {
        JLabel l = new JLabel(yazi);
        l.setForeground(SOLUK_YAZI);
        return l;
    }

    public static JLabel alanEtiketi(String yazi) {
        JLabel l = new JLabel(yazi);
        l.setForeground(SOLUK_YAZI);
        l.setFont(l.getFont().deriveFont(Font.BOLD));
        return l;
    }
}
