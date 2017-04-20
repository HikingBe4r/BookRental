package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener {
	private JButton historyBtn, rentalBtn, memberBtn, returnBtn, bookBtn;
	private JPanel centerPanel;
	private CardLayout card = new CardLayout(0, 0);
	private MemberPanel memberPanel;
	private RentalHistoryPanel historyPanel;
	private RegisterBookPanel regBookPanel;
	private RentalPanel rentalPanel;
	private RetrieveBookPanel retrieveBookPanel;
	private ReturnPanel returnPanel;

	int btnWidth = 180;
	int btnHeight = 130 + 5;

	public void addComponent() {
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0, 0, 200, 762);
		getContentPane().add(leftPanel);
		leftPanel.setLayout(null);

		memberBtn = new JButton("회원관리");
		memberBtn.setBounds(10, 10, btnWidth, btnHeight);
		leftPanel.add(memberBtn);

		rentalBtn = new JButton("대여");
		rentalBtn.setBounds(10, 150 + 10, btnWidth, btnHeight);

		leftPanel.add(rentalBtn);

		returnBtn = new JButton("반납 / 연장");
		returnBtn.setBounds(10, 290 + 15, btnWidth, btnHeight);
		leftPanel.add(returnBtn);

		bookBtn = new JButton("도서관리");
		bookBtn.setBounds(10, 430 + 20, btnWidth, btnHeight);
		leftPanel.add(bookBtn);

		historyBtn = new JButton("대여 / 반납기록");
		historyBtn.setBounds(10, 570 + 25, btnWidth, btnHeight);
		leftPanel.add(historyBtn);

		// JPanel centerPanel = new JPanel();
		centerPanel = new JPanel();
		centerPanel.setBounds(210, 0, 970, 762);
		getContentPane().add(centerPanel);
		centerPanel.setLayout(card);

		historyPanel = new RentalHistoryPanel();
		rentalPanel = new RentalPanel();
		memberPanel = new MemberPanel();
		returnPanel = new ReturnPanel();
		retrieveBookPanel = new RetrieveBookPanel();

		centerPanel.add("member", memberPanel);
		centerPanel.add("history", historyPanel);
		centerPanel.add("rental", rentalPanel);
		centerPanel.add("return", returnPanel);
		centerPanel.add("book", retrieveBookPanel);
	}

	private void addEventListner() {
		historyBtn.addActionListener(this);
		rentalBtn.addActionListener(this);
		memberBtn.addActionListener(this);
		returnBtn.addActionListener(this);
		bookBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() instanceof JButton) {
			// historyBtn, rentalBtn, memberBtn, returnBtn, bookBtn
			if (e.getSource() == historyBtn) {
				historyPanel.retrieveHistorys();
				card.show(centerPanel, "history");
			} else if (e.getSource() == rentalBtn) {
				rentalPanel.viewAllBooks();
				card.show(centerPanel, "rental");
			} else if (e.getSource() == memberBtn) {
				card.show(centerPanel, "member");
			} else if (e.getSource() == returnBtn) {
				card.show(centerPanel, "return");
			} else if (e.getSource() == bookBtn) {
				card.show(centerPanel, "book");
			}
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
}