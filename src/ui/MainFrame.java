package ui;

import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	int btnWidth = 180;
	int btnHeight = 130+5;
	
	
	
	public void addComponent() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 200, 762);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JButton btn1 = new JButton("\uD68C\uC6D0 \uAD00\uB9AC");
		btn1.setBounds(10, 10, btnWidth, btnHeight);
		leftPanel.add(btn1);
		
		JButton button = new JButton("\uB300\uC5EC");
		button.setBounds(10, 150+10, btnWidth, btnHeight);
		leftPanel.add(button);
		
		JButton button_1 = new JButton("\uBC18\uB0A9 / \uC5F0\uC7A5");
		button_1.setBounds(10, 290+15, btnWidth, btnHeight);
		leftPanel.add(button_1);
		
		JButton button_2 = new JButton("\uB3C4\uC11C \uAD00\uB9AC");
		button_2.setBounds(10, 430+20, btnWidth, btnHeight);
		leftPanel.add(button_2);
		
		JButton button_3 = new JButton("\uB300\uC5EC / \uBC18\uB0A9\uAE30\uB85D");
		button_3.setBounds(10, 570+25, btnWidth, btnHeight);
		leftPanel.add(button_3);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(210, 0, 970, 762);
		getContentPane().add(centerPanel);
		centerPanel.setLayout(new CardLayout(0, 0));
		
		MemberPanel mPanel = new MemberPanel();
		centerPanel.add(mPanel);
	}
	
	public void init() {
		getContentPane().setLayout(null);
		setBounds(100, 100, 1200, 800);
		
		addComponent();
	}
	
	public MainFrame() {
		getContentPane().setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		init();		
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}