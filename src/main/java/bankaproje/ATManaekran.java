package bankaproje;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.formdev.flatlaf.FlatClientProperties;

/** Giriş sonrası ana ekran: hesap özeti ve işlem menüsü. */
public class ATManaekran extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final NumberFormat TL = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("tr-TR"));

	private final JLabel lblSelam = new JLabel(" ");
	private final JLabel lblHesapNo = new JLabel(" ");
	private final JLabel lblBakiye = new JLabel(" ");

	public ATManaekran() {
		setTitle("MYO Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(560, 600);
		setLocationRelativeTo(null);

		JPanel kok = new JPanel(new BorderLayout());
		kok.setBackground(Tema.ZEMIN);
		setContentPane(kok);

		// --- Üst bant: selam, hesap no, bakiye ---
		JPanel bant = new JPanel();
		bant.setLayout(new BoxLayout(bant, BoxLayout.Y_AXIS));
		bant.setBackground(Tema.LACIVERT);
		bant.setBorder(BorderFactory.createEmptyBorder(28, 32, 28, 32));
		lblSelam.putClientProperty(FlatClientProperties.STYLE, "font: bold +8");
		lblSelam.setForeground(Color.WHITE);
		lblHesapNo.setForeground(new Color(0xB9C7DD));
		JLabel lblBakiyeBaslik = new JLabel("Kullanılabilir bakiye");
		lblBakiyeBaslik.setForeground(new Color(0xB9C7DD));
		lblBakiye.putClientProperty(FlatClientProperties.STYLE, "font: bold +14");
		lblBakiye.setForeground(Color.WHITE);
		bant.add(lblSelam);
		bant.add(Box.createVerticalStrut(4));
		bant.add(lblHesapNo);
		bant.add(Box.createVerticalStrut(22));
		bant.add(lblBakiyeBaslik);
		bant.add(Box.createVerticalStrut(2));
		bant.add(lblBakiye);
		kok.add(bant, BorderLayout.NORTH);

		// --- İşlem menüsü ---
		JPanel orta = new JPanel(new BorderLayout(0, 14));
		orta.setOpaque(false);
		orta.setBorder(BorderFactory.createEmptyBorder(24, 32, 24, 32));
		JLabel lblIslemler = Tema.baslik("İşlemler", 3);
		orta.add(lblIslemler, BorderLayout.NORTH);

		JPanel izgara = new JPanel(new GridLayout(3, 2, 12, 12));
		izgara.setOpaque(false);
		for (String islem : new String[] { "Para çekme", "Para yatırma", "Havale", "EFT", "QR kod ile işlem",
				"Destek" }) {
			izgara.add(islemKutusu(islem));
		}
		orta.add(izgara, BorderLayout.CENTER);

		JButton btnCikis = new JButton("Çıkış yap");
		btnCikis.addActionListener(e -> {
			dispose();
			ATM atm = new ATM();
			atm.setVisible(true);
		});
		JPanel alt = new JPanel(new BorderLayout());
		alt.setOpaque(false);
		alt.add(btnCikis, BorderLayout.EAST);
		orta.add(alt, BorderLayout.SOUTH);
		kok.add(orta, BorderLayout.CENTER);
	}

	/** Henüz geliştirilmemiş işlemler açıkça "Yakında" olarak gösterilir. */
	private static JButton islemKutusu(String islem) {
		JButton b = new JButton("<html><b>" + islem + "</b><br><span style='color:#8A96A3'>Yakında</span></html>");
		b.setHorizontalAlignment(JButton.LEFT);
		b.setEnabled(false);
		b.setToolTipText("Bu işlem henüz geliştiriliyor");
		b.setPreferredSize(new Dimension(0, 64));
		b.putClientProperty(FlatClientProperties.STYLE, "disabledBackground: #ffffff; margin: 10,16,10,16");
		return b;
	}

	public void hesabiGoster(Hesap hesap) {
		lblSelam.setText("Merhaba, " + hesap.adi() + " " + hesap.soyAdi());
		lblHesapNo.setText("Hesap no " + hesap.hesapNo());
		lblBakiye.setText(TL.format(hesap.bakiye()));
	}
}
