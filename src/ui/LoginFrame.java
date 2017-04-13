package ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField textField;
    private JTextField textField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    LoginFrame frame = new LoginFrame();
		    frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame
     */
    public LoginFrame() {
	setBounds(100,100,400,300);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setBackground(Color.BLACK);
    	setAlwaysOnTop(true);
    	getContentPane().setBackground(Color.GRAY);
    	getContentPane().setEnabled(false);
    	getContentPane().setForeground(Color.PINK);
    	setTitle("Login");
	getContentPane().setLayout(null);

	textField = new JTextField();
	textField.setBounds(222, 124, 116, 21);
	getContentPane().add(textField);
	textField.setColumns(10);

	textField_1 = new JTextField();
	textField_1.setBounds(222, 73, 116, 21);
	getContentPane().add(textField_1);
	textField_1.setColumns(10);

	JLabel lblNewLabel = new JLabel("User Name");
	lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 12));
	lblNewLabel.setBounds(148, 78, 91, 15);
	getContentPane().add(lblNewLabel);
	
	JLabel lblPassword = new JLabel("Password");
	lblPassword.setFont(new Font("Consolas", Font.PLAIN, 12));
	lblPassword.setBounds(148, 129, 56, 15);
	getContentPane().add(lblPassword);
	
	JButton btnLogin = new JButton("Login");
	Image img = new ImageIcon(this.getClass().getResource ("/Ok.png")).getImage();
	btnLogin.setIcon(new ImageIcon(img));
	
	btnLogin.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		}
	});
	btnLogin.setBounds(148, 176, 106, 38);
	getContentPane().add(btnLogin);
	
	JLabel lblNewLabel_1 = new JLabel("");
	Image img1 = new ImageIcon(this.getClass().getResource ("/login.png")).getImage();
	lblNewLabel_1.setIcon(new ImageIcon(img1));
	
	lblNewLabel_1.setBounds(12, 42, 116, 172);
	getContentPane().add(lblNewLabel_1);
	
	
	
	
	

    }
}
