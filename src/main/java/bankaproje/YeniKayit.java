package bankaproje;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

/** Yeni hesap açma formu. */
public class YeniKayit extends JFrame {

	private static final long serialVersionUID = 1L;

	private final JTextField tfhesapno = alan("4-12 haneli");
	private final JTextField tfad = alan(null);
	private final JTextField tfsoyad = alan(null);
	private final JTextField tfbakiye = alan("Örn. 1500,00");
	private final JTextField tfpinkodu = alan("4 haneli");
	private final JTextField tfemail = alan("ad@ornek.com");
	private final JTextField tfsehir = alan(null);
	private final JTextField tfadres = alan(null);

	public YeniKayit() {
		setTitle("MYO Bank · Yeni hesap");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(620, 600);
		setLocationRelativeTo(null);

		JPanel zemin = new JPanel(new GridBagLayout());
		zemin.setBackground(Tema.ZEMIN);
		setContentPane(zemin);

		JPanel kart = Tema.kart();
		kart.setLayout(new BorderLayout(0, 20));

		JPanel ust = new JPanel(new BorderLayout(0, 4));
		ust.setOpaque(false);
		ust.add(Tema.baslik("Yeni hesap açın", 6), BorderLayout.NORTH);
		ust.add(Tema.aciklama("Bilgilerinizi girin; hesabınızla hemen giriş yapabilirsiniz."), BorderLayout.SOUTH);
		kart.add(ust, BorderLayout.NORTH);

		// İki sütunlu form: her satırda iki alan
		JPanel form = new JPanel(new GridBagLayout());
		form.setOpaque(false);
		String[] etiketler = { "Hesap numarası", "PIN kodu", "Ad", "Soyad", "Açılış bakiyesi (TL)", "E-posta", "Şehir",
				"Adres" };
		JTextField[] alanlar = { tfhesapno, tfpinkodu, tfad, tfsoyad, tfbakiye, tfemail, tfsehir, tfadres };
		GridBagConstraints g = new GridBagConstraints();
		g.fill = GridBagConstraints.HORIZONTAL;
		g.weightx = 1;
		for (int i = 0; i < alanlar.length; i++) {
			g.gridx = i % 2;
			g.gridy = i / 2;
			g.insets = new Insets(0, g.gridx == 0 ? 0 : 8, 14, g.gridx == 0 ? 8 : 0);
			form.add(etiketli(etiketler[i], alanlar[i]), g);
		}
		JPanel formSarici = new JPanel(new BorderLayout());
		formSarici.setOpaque(false);
		formSarici.add(form, BorderLayout.NORTH); // alanlar başlığın hemen altından başlasın
		kart.add(formSarici, BorderLayout.CENTER);

		JButton btnVazgec = new JButton("Vazgeç");
		JButton btnKayit = Tema.anaButon("Hesap aç");
		getRootPane().setDefaultButton(btnKayit);
		JPanel butonlar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		butonlar.setOpaque(false);
		butonlar.add(btnVazgec);
		butonlar.add(btnKayit);
		kart.add(butonlar, BorderLayout.SOUTH);
		kart.setPreferredSize(new Dimension(520, kart.getPreferredSize().height));
		zemin.add(kart);

		btnVazgec.addActionListener(e -> girisEkraninaDon());
		btnKayit.addActionListener(e -> {
			Optional<String> hata = HesapDogrulama.dogrula(tfhesapno.getText(), tfad.getText(), tfsoyad.getText(),
					tfbakiye.getText(), tfpinkodu.getText(), tfemail.getText(), tfsehir.getText(), tfadres.getText());
			if (hata.isPresent()) {
				JOptionPane.showMessageDialog(this, hata.get(), "Eksik ya da hatalı bilgi",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			Hesap hesap = new Hesap(tfhesapno.getText(), tfad.getText(), tfsoyad.getText(),
					HesapDogrulama.bakiyeOku(tfbakiye.getText()).orElseThrow(), tfemail.getText(),
					tfsehir.getText(), tfadres.getText());
			try {
				HesapDeposu.VARSAYILAN.hesapAc(hesap, tfpinkodu.getText());
				JOptionPane.showMessageDialog(this, "Hesabınız açıldı. Şimdi giriş yapabilirsiniz.");
				girisEkraninaDon();
			} catch (HesapDeposu.HesapZatenVarException hataZatenVar) {
				JOptionPane.showMessageDialog(this, hataZatenVar.getMessage());
			} catch (SQLException hataSql) {
				JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + hataSql.getMessage());
			}
		});
	}

	private void girisEkraninaDon() {
		ATM atm = new ATM();
		dispose();
		atm.setVisible(true);
	}

	private static JTextField alan(String ipucu) {
		JTextField alan = new JTextField();
		if (ipucu != null) {
			alan.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, ipucu);
		}
		return alan;
	}

	private static JComponent etiketli(String etiket, JComponent alan) {
		JPanel p = new JPanel(new BorderLayout(0, 6));
		p.setOpaque(false);
		p.setBorder(BorderFactory.createEmptyBorder());
		p.add(Tema.alanEtiketi(etiket), BorderLayout.NORTH);
		p.add(alan, BorderLayout.CENTER);
		return p;
	}
}
