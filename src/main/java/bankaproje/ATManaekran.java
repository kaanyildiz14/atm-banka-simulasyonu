package bankaproje;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ATManaekran extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ATManaekran frame = new ATManaekran();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	JLabel lblAdi , lblSoyAdi , lblHesapNo;
	/**
	 * Create the frame.
	 */
	public ATManaekran() {
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setTitle("MYO Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(550, 250, 500, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(101, 121, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Para Çekme");
		btnNewButton.setBackground(new Color(231, 239, 245));
		btnNewButton.setForeground(new Color(28, 57, 255));
		btnNewButton.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnNewButton.setBounds(37, 151, 143, 46);
		contentPane.add(btnNewButton);
		
		JButton btnParaYatrma = new JButton("Para yatırma");
		btnParaYatrma.setForeground(new Color(28, 57, 255));
		btnParaYatrma.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnParaYatrma.setBackground(new Color(231, 239, 245));
		btnParaYatrma.setBounds(37, 209, 143, 46);
		contentPane.add(btnParaYatrma);
		
		JButton btnHavale = new JButton("Havale");
		btnHavale.setForeground(new Color(28, 57, 255));
		btnHavale.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnHavale.setBackground(new Color(231, 239, 245));
		btnHavale.setBounds(37, 267, 143, 46);
		contentPane.add(btnHavale);
		
		JButton btnEft = new JButton("EFT");
		btnEft.setForeground(new Color(28, 57, 255));
		btnEft.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnEft.setBackground(new Color(231, 239, 245));
		btnEft.setBounds(317, 151, 143, 46);
		contentPane.add(btnEft);
		
		JButton btnQrKodIle = new JButton("QR kod ile işlem ");
		btnQrKodIle.setForeground(new Color(28, 57, 255));
		btnQrKodIle.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnQrKodIle.setBackground(new Color(231, 239, 245));
		btnQrKodIle.setBounds(317, 209, 143, 46);
		contentPane.add(btnQrKodIle);
		
		JButton btnDestek = new JButton("Destek");
		btnDestek.setForeground(new Color(28, 57, 255));
		btnDestek.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnDestek.setBackground(new Color(231, 239, 245));
		btnDestek.setBounds(317, 267, 143, 46);
		contentPane.add(btnDestek);
		
		JLabel lblNewLabel = new JLabel("Hoş Geldiniz");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(new Color(239, 244, 241));
		lblNewLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		lblNewLabel.setBounds(37, 100, 124, 39);
		contentPane.add(lblNewLabel);
		
		JButton btnk = new JButton("Çıkış");
		btnk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				ATM atm = new ATM();
				atm.setVisible(true);
			}
		});
		btnk.setForeground(new Color(28, 57, 255));
		btnk.setFont(new Font("Arial Narrow", Font.PLAIN, 13));
		btnk.setBackground(new Color(231, 239, 245));
		btnk.setBounds(144, 325, 186, 46);
		contentPane.add(btnk);
		
		lblHesapNo = new JLabel("");
		lblHesapNo.setHorizontalAlignment(SwingConstants.CENTER);
		lblHesapNo.setForeground(new Color(239, 244, 241));
		lblHesapNo.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		lblHesapNo.setBounds(179, 6, 124, 39);
		contentPane.add(lblHesapNo);
		
		lblSoyAdi = new JLabel("");
		lblSoyAdi.setHorizontalAlignment(SwingConstants.CENTER);
		lblSoyAdi.setForeground(new Color(239, 244, 241));
		lblSoyAdi.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		lblSoyAdi.setBounds(317, 100, 124, 39);
		contentPane.add(lblSoyAdi);
		
		lblAdi = new JLabel("");
		lblAdi.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdi.setForeground(new Color(239, 244, 241));
		lblAdi.setFont(new Font("Arial Narrow", Font.PLAIN, 24));
		lblAdi.setBounds(179, 100, 124, 39);
		contentPane.add(lblAdi);

	}
}
