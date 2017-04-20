/**
 * 
 */
package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.MemberDAO;
import dao.RentalDAO;
import domain.MemberVO;

/**
 * @author kodica0301
 *
 */
public class SearchMemberFrame extends JFrame {

	private JButton searchBtn, acceptBtn, cancelBtn;
	private JPanel contentPane;
	private JTextField keywordTF;
	private JTable table;
	private JComboBox keyfieldCB;
	private JScrollPane scrollPane;
	private DefaultTableModel dtm;

	private MemberDAO dao = new MemberDAO();
	private MemberVO member = new MemberVO();
	private RentalPanel rentalPanel;
	private ReturnPanel returnPanel;
	private final int BTN_ENABLED = 111111111;
	
	KeyListener kListener = new KeyListener() {

		@Override
		public void keyTyped(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == keywordTF) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchMemberList(keyfieldCB.getSelectedIndex(), keywordTF.getText());
				}
			}
		}
	};	

	public SearchMemberFrame(ReturnPanel returnPanel) {
		this.returnPanel = returnPanel;
		setTitle("\uD68C\uC6D0\uAC80\uC0C9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame)e.getWindow();
				frame.dispose();
				returnPanel.search.setEnabled(true);
			}
			
		});
		setBounds(400, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		keyfieldCB = new JComboBox();
		keyfieldCB.setModel(new DefaultComboBoxModel(new String[] { "\uC774\uB984", "ID", "\uC804\uD654\uBC88\uD638" }));
		keyfieldCB.setBounds(12, 10, 74, 21);
		contentPane.add(keyfieldCB);

		keywordTF = new JTextField();
		keywordTF.setBounds(98, 10, 232, 21);
		contentPane.add(keywordTF);
		keywordTF.setColumns(10);

		searchBtn = new JButton("\uAC80\uC0C9");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.setRowCount(0);
				searchMemberList(keyfieldCB.getSelectedIndex(), keywordTF.getText());
			}
		});
		searchBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		searchBtn.setBounds(342, 9, 64, 23);
		contentPane.add(searchBtn);

		acceptBtn = new JButton("\uC120\uD0DD");
		acceptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(acceptBtn, "선택된 회원이 없습니다.");
					return;
				}
				MemberVO member = selectedMember(table.getSelectedRow());
				returnPanel.memberidtf.setText(member.getId());
				returnPanel.membernametf.setText(member.getName());
				returnPanel.phonenumbertf.setText(member.getPhoneNum());
				RentalDAO dao = new RentalDAO();
				try {
					returnPanel.rentalbooktf.setText("" + dao.rentableBookNum(member.getId())); // 연체자면
																								// 대여가능권수
																								// 0
					

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				returnPanel.search.setEnabled(true);
				setVisible(false);
				try {
					Vector<Vector<Object>> rowData = dao.selectRentingBooksByMember(member.getId());

					for (int i = 0; i < rowData.size(); i++) {
						returnPanel.retrievetabledm.addRow(rowData.elementAt(i));
					}
				} catch (Exception e2) {
				}

			}
		});
		acceptBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		acceptBtn.setBounds(432, 329, 64, 23);
		contentPane.add(acceptBtn);

		cancelBtn = new JButton("\uCDE8\uC18C");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				returnPanel.search.setEnabled(true);	
				setVisible(false);
			}
		});
		cancelBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		cancelBtn.setBounds(508, 329, 64, 23);
		contentPane.add(cancelBtn);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 49, 560, 270);
		contentPane.add(scrollPane);

		dtm = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "\uC774\uB984", "\uC804\uD654\uBC88\uD638", "\uC0DD\uB144\uC6D4\uC77C" });

		table = new JTable(dtm);
		table.setFillsViewportHeight(true);
		table.setBounds(0, 0, 499, 160);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(86);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(142);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(121);
	}

	public SearchMemberFrame(RentalPanel rentalPanel) {
		this.rentalPanel = rentalPanel;
		setTitle("\uD68C\uC6D0\uAC80\uC0C9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JFrame frame = (JFrame)e.getWindow();
				frame.dispose();
				rentalPanel.memberSearchBtn.setEnabled(true);
			}
			
		});
		setBounds(400, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		keyfieldCB = new JComboBox();
		keyfieldCB
				.setModel(new DefaultComboBoxModel(new String[] { "\uC774\uB984", "ID", "\uC804\uD654\uBC88\uD638" }));
		keyfieldCB.setBounds(12, 10, 74, 21);
		contentPane.add(keyfieldCB);

		keywordTF = new JTextField();
		keywordTF.setBounds(98, 10, 232, 21);
		contentPane.add(keywordTF);
		keywordTF.setColumns(10);

		searchBtn = new JButton("\uAC80\uC0C9");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dtm.setRowCount(0);
				searchMemberList(keyfieldCB.getSelectedIndex(), keywordTF.getText());
			}
		});
		searchBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		searchBtn.setBounds(342, 9, 64, 23);
		contentPane.add(searchBtn);

		acceptBtn = new JButton("\uC120\uD0DD");
		acceptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					JOptionPane.showMessageDialog(acceptBtn, "선택된 회원이 없습니다.");
					return;
				}
				MemberVO member = selectedMember(table.getSelectedRow());
				rentalPanel.memberIdTF.setText(member.getId());
				rentalPanel.memberNameTF.setText(member.getName());
				rentalPanel.phoneTF.setText(member.getPhoneNum());
				RentalDAO dao = new RentalDAO();
				try {
					rentalPanel.rentableBookNumTF.setText("" + dao.rentableBookNum(member.getId())); // 연체자면
																									// 대여가능권수
																									// 0

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				rentalPanel.memberSearchBtn.setEnabled(true);
				setVisible(false);
			}
		});
		acceptBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		acceptBtn.setBounds(432, 329, 64, 23);
		contentPane.add(acceptBtn);

		cancelBtn = new JButton("\uCDE8\uC18C");
		cancelBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rentalPanel.memberSearchBtn.setEnabled(true);
				setVisible(false);
			}
		});
		cancelBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		cancelBtn.setBounds(508, 329, 64, 23);
		contentPane.add(cancelBtn);

		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 49, 560, 270);
		contentPane.add(scrollPane);

		dtm = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "\uC774\uB984", "\uC804\uD654\uBC88\uD638", "\uC0DD\uB144\uC6D4\uC77C" });

		table = new JTable(dtm);
		table.setFillsViewportHeight(true);
		table.setBounds(0, 0, 499, 160);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(86);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(142);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(121);
	}

	public MemberVO getMember() {
		return member;
	}

	private boolean searchMemberList(int selectedKeyfield, String keyword) {
		String keyfield = null;// , keyword = keywordTF.getText();

		try {
			if (selectedKeyfield == 0) {
				keyfield = "name";
			} else if (selectedKeyfield == 1) {
				keyfield = "id";
			} else if (selectedKeyfield == 2) {
				keyfield = "phoneNum";
			}
			if (keyword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "검색어를 입력하세요");
			} else {
				Vector<Vector<Object>> rowData = dao.retrieveMemberListByCondition(keyfield, keyword);
				System.out.println(rowData);
				for (Vector<Object> rd : rowData) {
					Vector<Object> member = new Vector<Object>();
					if (rd.elementAt(5).equals("")) {
						member.addElement(rd.elementAt(1));
						member.addElement(rd.elementAt(2));
						member.addElement(rd.elementAt(3));
						member.addElement(rd.elementAt(4));

						dtm.addRow(member);
					}

				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public MemberVO selectedMember(int selectedRow) {
		// MemberVO member = new MemberVO();
		int index = table.getSelectedRow();
		member.setId(table.getValueAt(selectedRow, 0).toString());
		member.setName(table.getValueAt(selectedRow, 1).toString());
		member.setPhone(table.getValueAt(selectedRow, 2).toString());
		member.setBirthDay(table.getValueAt(selectedRow, 3).toString());
		member.setWithdraw("0");

		return member;
	}
	   
	

}