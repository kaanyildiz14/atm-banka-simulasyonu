package bankaproje;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.sql.SQLException;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class YeniKayit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfhesapno;
	private JTextField tfad;
	private JTextField tfsoyad;
	private JTextField tfbakiye;
	private JTextField tfpinkodu;
	private JTextField tfemail;
	private JTextField tfsehir;
	private JTextField tfadres;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YeniKayit frame = new YeniKayit();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public YeniKayit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 487, 373);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(45, 21, 200));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Hesap No:      ");
		lblNewLabel.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(105, 18, 113, 28);
		contentPane.add(lblNewLabel);
		
		tfhesapno = new JTextField();
		tfhesapno.setBounds(197, 19, 130, 26);
		contentPane.add(tfhesapno);
		tfhesapno.setColumns(10);
		
		JLabel lblAd = new JLabel("Ad:      ");
		lblAd.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblAd.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAd.setBounds(105, 47, 113, 28);
		contentPane.add(lblAd);
		
		JLabel lblSoyad = new JLabel("Soyad:      ");
		lblSoyad.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblSoyad.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSoyad.setBounds(105, 75, 113, 28);
		contentPane.add(lblSoyad);
		
		JLabel lblBakiye = new JLabel("Bakiye:      ");
		lblBakiye.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblBakiye.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBakiye.setBounds(105, 106, 113, 28);
		contentPane.add(lblBakiye);
		
		JLabel lblPinkodu = new JLabel("Pinkodu:      ");
		lblPinkodu.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblPinkodu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPinkodu.setBounds(105, 137, 113, 28);
		contentPane.add(lblPinkodu);
		
		JLabel lblEmail = new JLabel("Email:      ");
		lblEmail.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(105, 167, 113, 28);
		contentPane.add(lblEmail);
		
		JLabel lblSehir = new JLabel("Sehir:      ");
		lblSehir.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblSehir.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSehir.setBounds(105, 197, 113, 28);
		contentPane.add(lblSehir);
		
		JLabel lblAdres = new JLabel("Adres:      ");
		lblAdres.setFont(new Font("Arial Narrow", Font.BOLD | Font.ITALIC, 16));
		lblAdres.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdres.setBounds(105, 227, 113, 28);
		contentPane.add(lblAdres);
		
		tfad = new JTextField();
		tfad.setColumns(10);
		tfad.setBounds(197, 48, 130, 26);
		contentPane.add(tfad);
		
		tfsoyad = new JTextField();
		tfsoyad.setColumns(10);
		tfsoyad.setBounds(197, 76, 130, 26);
		contentPane.add(tfsoyad);
		
		tfbakiye = new JTextField();
		tfbakiye.setColumns(10);
		tfbakiye.setBounds(197, 107, 130, 26);
		contentPane.add(tfbakiye);
		
		tfpinkodu = new JTextField();
		tfpinkodu.setColumns(10);
		tfpinkodu.setBounds(197, 138, 130, 26);
		contentPane.add(tfpinkodu);
		
		tfemail = new JTextField();
		tfemail.setColumns(10);
		tfemail.setBounds(197, 168, 130, 26);
		contentPane.add(tfemail);
		
		tfsehir = new JTextField();
		tfsehir.setColumns(10);
		tfsehir.setBounds(197, 198, 130, 26);
		contentPane.add(tfsehir);
		
		tfadres = new JTextField();
		tfadres.setColumns(10);
		tfadres.setBounds(197, 228, 130, 26);
		contentPane.add(tfadres);
		
		JButton btnNewButton = new JButton("Kayit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Optional<String> hata = HesapDogrulama.dogrula(tfhesapno.getText(), tfad.getText(), tfsoyad.getText(),
						tfbakiye.getText(), tfpinkodu.getText(), tfemail.getText(), tfsehir.getText(), tfadres.getText());
				if (hata.isPresent()) {
					JOptionPane.showMessageDialog(null, hata.get());
					return;
				}
				Hesap hesap = new Hesap(tfhesapno.getText(), tfad.getText(), tfsoyad.getText(),
						HesapDogrulama.bakiyeOku(tfbakiye.getText()).orElseThrow(), tfemail.getText(),
						tfsehir.getText(), tfadres.getText());
				try {
					HesapDeposu.VARSAYILAN.hesapAc(hesap, tfpinkodu.getText());
					JOptionPane.showMessageDialog(null, "Kayıt Başarılı");
					ATM atm = new ATM();
					dispose();
					atm.setVisible(true);
				} catch (HesapDeposu.HesapZatenVarException hataZatenVar) {
					JOptionPane.showMessageDialog(null, hataZatenVar.getMessage());
				} catch (SQLException hataSql) {
					JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + hataSql.getMessage());
				}
			}
		});
		btnNewButton.setBounds(197, 265, 117, 29);
		contentPane.add(btnNewButton);

	}
}
