package bankaproje;

import java.awt.EventQueue;

import java.sql.SQLException;
import java.util.Optional;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ATM extends JFrame {
	private JTextField HNoText;
	private JPasswordField PKText;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATM frame = new ATM();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private JButton btnYeniKayit;
	/**
	 * Create the frame.
	 */
	public ATM() {
		try {
			HesapDeposu.VARSAYILAN.kurulum();
		} catch (SQLException hata) {
			JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + hata.getMessage());
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(225, 12, 14));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		HNoText = new JTextField();
		HNoText.setBounds(151, 59, 130, 26);
		contentPane.add(HNoText);
		HNoText.setColumns(10);
		
		PKText = new JPasswordField(); // PIN ekranda görünmesin
		PKText.setBounds(151, 97, 130, 26);
		contentPane.add(PKText);
		PKText.setColumns(10);
		
		JButton btnGiris = new JButton("Giri\u015F");
		
		
		btnGiris.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnGiris.setBackground(new Color(128, 128, 0));
		btnGiris.setBounds(151, 135, 130, 29);
		contentPane.add(btnGiris);
		
		JLabel lblNewLabel = new JLabel("Hesap No:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setBounds(79, 63, 63, 17);
		contentPane.add(lblNewLabel);
		
		JLabel lblPinKodu = new JLabel("Pin Kodu:");
		lblPinKodu.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPinKodu.setForeground(Color.WHITE);
		lblPinKodu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPinKodu.setBounds(79, 101, 60, 17);
		contentPane.add(lblPinKodu);
		
		btnYeniKayit = new JButton("Yeni Kayit");
		btnYeniKayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YeniKayit yk = new YeniKayit();
				dispose();
				yk.setVisible(true);
			}
		});
		btnYeniKayit.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnYeniKayit.setBackground(new Color(128, 128, 0));
		btnYeniKayit.setBounds(151, 175, 130, 29);
		contentPane.add(btnYeniKayit);
		
		btnGiris.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					Optional<Hesap> hesap = HesapDeposu.VARSAYILAN.girisYap(HNoText.getText(),
							String.valueOf(PKText.getPassword()));
					if (hesap.isPresent()) {
						dispose();
						JOptionPane.showMessageDialog(null, "Merhaba: Giriş yapıldı");
						ATManaekran ae = new ATManaekran();
						ae.setVisible(true);
						ae.lblAdi.setText(hesap.get().adi());
						ae.lblSoyAdi.setText(hesap.get().soyAdi());
						ae.lblHesapNo.setText(hesap.get().hesapNo());
					} else {
						JOptionPane.showMessageDialog(null, "Hesap numarası veya PIN hatalı");
					}
				} catch (SQLException hata) {
					JOptionPane.showMessageDialog(null, "Veritabanı hatası: " + hata.getMessage());
				}
			}
		});
		
		
		
	}
}
	


