package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import ui.RentalHistoryPanel;
import ui.RentalPanel;

public class MainFrame extends JFrame implements ActionListener{
	private JButton hButton, rentalBtn;
	private JPanel centerPanel;
	private CardLayout card = new CardLayout(0, 0);
	int btnWidth = 180;
	int btnHeight = 130+5;
	
	
	
	public void addComponent() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 200, 762);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JButton btn1 = new JButton("회원관리");
		btn1.setBounds(10, 10, btnWidth, btnHeight);
		leftPanel.add(btn1);
		
		rentalBtn = new JButton("대여");
		rentalBtn.setBounds(10, 150+10, btnWidth, btnHeight);
		
		leftPanel.add(rentalBtn);
		
		JButton button_1 = new JButton("반납 / 연장");
		button_1.setBounds(10, 290+15, btnWidth, btnHeight);
		leftPanel.add(button_1);
		
		JButton button_2 = new JButton("도서관리");
		button_2.setBounds(10, 430+20, btnWidth, btnHeight);
		leftPanel.add(button_2);
				
		hButton = new JButton("대여 / 반납기록");
		hButton.setBounds(10, 570+25, btnWidth, btnHeight);
		leftPanel.add(hButton);
		
		
		//JPanel centerPanel = new JPanel();
		centerPanel = new JPanel();
		centerPanel.setBounds(210, 0, 970, 762);
		getContentPane().add(centerPanel);
		centerPanel.setLayout(card);
		
		MemberPanel mPanel = new MemberPanel();
		centerPanel.add(mPanel);
		
		RentalHistoryPanel hPanel = new RentalHistoryPanel();
		RentalPanel rentalPanel = new RentalPanel();
		centerPanel.add("h", hPanel);	
		centerPanel.add("rental", rentalPanel);
	}
	private void addEventListner() {
		hButton.addActionListener(this);
		rentalBtn.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if(target == hButton) {
			card.show(centerPanel, "h");
		} else if (target == rentalBtn) {
			card.show(centerPanel, "rental");
		}
		
	}
	public void init() {
		getContentPane().setLayout(null);
		setBounds(100, 100, 1200, 800);
		
		addComponent();
		addEventListner();
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