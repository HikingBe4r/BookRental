package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dao.AdminDAO;
import domain.AdminVO;

public class LoginFrame extends JFrame {
	private JTextField idTF;
	private AdminDAO dao = new AdminDAO();
	private JPasswordField passwordTF;

	private void KeyEventListener() {
		

	}
	
	private void addListener() {
		idTF.addKeyListener(kListener);
		passwordTF.addKeyListener(kListener);
	}
	
	KeyListener kListener = new KeyListener() {
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() instanceof JTextField) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					confirmLogin(idTF.getText().toString(), passwordTF.getText().toString());
				}
			}
		}
	};

	public void addComponent() {
		setBounds(100, 100, 400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.BLACK);
		setAlwaysOnTop(true);
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setEnabled(false);
		getContentPane().setForeground(Color.PINK);
		setTitle("Login");
		getContentPane().setLayout(null);

		idTF = new JTextField();
		idTF.setBounds(222, 73, 116, 21);
		getContentPane().add(idTF);
		idTF.setColumns(10);

		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblNewLabel.setBounds(148, 78, 91, 15);
		getContentPane().add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Consolas", Font.PLAIN, 12));
		lblPassword.setBounds(148, 129, 56, 15);
		getContentPane().add(lblPassword);

		JButton loginBtn = new JButton("Login");
		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmLogin(idTF.getText().toString(), passwordTF.getText().toString());
			}
		});
		Image img = new ImageIcon(this.getClass().getResource("/Ok.png")).getImage();
		loginBtn.setIcon(new ImageIcon(img));

		loginBtn.setBounds(148, 176, 106, 38);
		getContentPane().add(loginBtn);

		JLabel lblNewLabel_1 = new JLabel("");
		Image img1 = new ImageIcon(this.getClass().getResource("/login.png")).getImage();
		lblNewLabel_1.setIcon(new ImageIcon(img1));

		lblNewLabel_1.setBounds(12, 42, 116, 172);
		getContentPane().add(lblNewLabel_1);

		passwordTF = new JPasswordField();
		passwordTF.setBounds(222, 124, 116, 21);
		getContentPane().add(passwordTF);
	}

	public void init() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		addComponent();
		addListener();

		setVisible(true);
	}

	public LoginFrame() {
		init();

	}

	public static void main(String[] args) {
		new LoginFrame();
	}

	private void confirmLogin(String id, String password) {
		try {
			if (dao.loginAdmin(new AdminVO(id, password))) {
				JOptionPane.showMessageDialog(this, "로그인 성공");
				new MainFrame();
				this.setVisible(false);
			} else {
				JOptionPane.showMessageDialog(this, "로그인 실패");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
