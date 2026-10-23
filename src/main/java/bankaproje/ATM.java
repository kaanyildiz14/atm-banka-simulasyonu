package bankaproje;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

/** Giriş ekranı: uygulamanın başlangıç noktası. */
public class ATM extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTextField HNoText;
	private JPasswordField PKText;

	public static void main(String[] args) {
		Tema.kur();
		EventQueue.invokeLater(() -> {
			try {
				ATM frame = new ATM();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public ATM() {
		try {
			HesapDeposu.VARSAYILAN.kurulum();
		} catch (SQLException hata) {
			JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + hata.getMessage());
		}
		setTitle("MYO Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(480, 600);
		setLocationRelativeTo(null);

		JPanel zemin = new JPanel(new GridBagLayout());
		zemin.setBackground(Tema.ZEMIN);
		setContentPane(zemin);

		JPanel kart = Tema.kart();
		kart.setLayout(new BoxLayout(kart, BoxLayout.Y_AXIS));

		JLabel logo = new JLabel("MYO Bank");
		logo.putClientProperty(FlatClientProperties.STYLE, "font: bold +6");
		logo.setForeground(Tema.LACIVERT);
		satir(kart, logo, 4);
		satir(kart, Tema.aciklama("Hesabınıza giriş yapın"), 24);

		HNoText = new JTextField();
		HNoText.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Örn. 100200");
		satir(kart, Tema.alanEtiketi("Hesap numarası"), 6);
		satir(kart, HNoText, 14);

		PKText = new JPasswordField(); // PIN ekranda görünmesin
		PKText.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "4 haneli PIN");
		PKText.putClientProperty(FlatClientProperties.STYLE, "showRevealButton: true");
		satir(kart, Tema.alanEtiketi("PIN kodu"), 6);
		satir(kart, PKText, 22);

		JButton btnGiris = Tema.anaButon("Giriş yap");
		satir(kart, btnGiris, 10);
		getRootPane().setDefaultButton(btnGiris); // Enter ile giriş

		JButton btnYeniKayit = Tema.baglantiButonu("Hesabınız yok mu? Yeni hesap açın");
		satir(kart, btnYeniKayit, 0);
		kart.setPreferredSize(new Dimension(360, kart.getPreferredSize().height));

		zemin.add(kart);

		btnYeniKayit.addActionListener(e -> {
			YeniKayit yk = new YeniKayit();
			dispose();
			yk.setVisible(true);
		});

		btnGiris.addActionListener(e -> {
			try {
				Optional<Hesap> hesap = HesapDeposu.VARSAYILAN.girisYap(HNoText.getText(),
						String.valueOf(PKText.getPassword()));
				if (hesap.isPresent()) {
					dispose();
					ATManaekran ae = new ATManaekran();
					ae.hesabiGoster(hesap.get());
					ae.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(this, "Hesap numarası veya PIN hatalı", "Giriş yapılamadı",
							JOptionPane.WARNING_MESSAGE);
				}
			} catch (SQLException hata) {
				JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + hata.getMessage());
			}
		});
	}

	/** Kartın içine tam genişlikte bir satır ve altına boşluk ekler. */
	static void satir(JPanel kart, JComponent bilesen, int altBosluk) {
		bilesen.setAlignmentX(Component.LEFT_ALIGNMENT);
		bilesen.setMaximumSize(new Dimension(Integer.MAX_VALUE, bilesen.getPreferredSize().height));
		kart.add(bilesen);
		if (altBosluk > 0) {
			kart.add(Box.createVerticalStrut(altBosluk));
		}
	}

}
