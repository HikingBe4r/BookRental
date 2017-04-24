package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.MemberDAO;
import domain.MemberVO;

public class MemberPanel extends JPanel {
	private JPanel northPanel, centerPanel, thisPanel = this;
	private JButton retrieveMemberListBtn, retrieveAllMemberBtn, registerBtn, initBtn, withdrawBtn, updateBtn;
	private JLabel idLabel, nameLabel, phoneLabel, birthdayLabel, withDrawLabel;
	private JScrollPane scrollPane;
	private JComboBox keyfieldCB;
	private JTextField keywordTF, idTF, nameTF, phoneTF, birthdayTF, withdrawTF;
	private JTable table;
	private JCheckBox box;
	private DefaultTableModel dtm;
	private MemberDAO dao = new MemberDAO();
	private ArrayList<String> idList = null;	// 선택한 회원 idList

	// J테이블 안에 버튼, 체크박스
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "수정" : value.toString());
			return this;
		}
	}

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	class ButtonEditor extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "수정" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
		}

		public Object getCellEditorValue() {
			if (isPushed) { // 작은 수정버튼
				int index = table.getSelectedRow();
				
				if(table.getValueAt(index, 5).equals("탈퇴")) {
					JOptionPane.showMessageDialog(thisPanel, "탈퇴한 회원입니다.");
				} else {
					registerBtn.setVisible(false);
					updateBtn.setVisible(true);
					nameTF.setEditable(true);
					phoneTF.setEditable(true);
					birthdayTF.setEditable(true);
				}
			}
			isPushed = false;
			return new String(label);
		}


		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			super.fireEditingStopped();
		}
	}

	private void addActionListener() {
		retrieveMemberListBtn.addActionListener(listener);
		withdrawBtn.addActionListener(listener);
		box.addActionListener(listener);
		table.addMouseListener(mListener);
		retrieveAllMemberBtn.addActionListener(listener);
		registerBtn.addActionListener(listener);
		updateBtn.addActionListener(listener);
		initBtn.addActionListener(listener);
		keywordTF.addKeyListener(kListener);
	}
	
	ActionListener listener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// 버튼
			if (e.getSource() instanceof JButton) {
				if ((JButton) (e.getSource()) == retrieveMemberListBtn) {
					searchMemberList(keyfieldCB.getSelectedIndex(), keywordTF.getText());
				} else if (e.getSource() == withdrawBtn) {
					idList = new ArrayList<String>();
					
					for (int i = 0; i < dtm.getRowCount(); i++) {
						if ((Boolean) dtm.getValueAt(i, 0) == true) {
							idList.add(dtm.getValueAt(i, 1).toString());
							removeMember(idList);
						}
					}
				} else if (e.getSource() == retrieveAllMemberBtn) {
					searchAllmemberList();
				} else if (e.getSource() == registerBtn) {
					registerMemberlist();
				} else if (e.getSource() == updateBtn) {
					updateMemberInfo();
				} else if (e.getSource() == initBtn) {
					resetviewInfo();
				}
			} else if (e.getSource() instanceof JCheckBox) {
				if ((JCheckBox) (e.getSource()) == box) {
					if (box.isSelected()) {
						dtm.setValueAt(true, table.getSelectedRow(), 0);
					} else {
						dtm.setValueAt(false, table.getSelectedRow(), 0);
					}
				}
			}
		}
	};

	MouseAdapter mListener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() instanceof JTable) {
				viewSelectedMember(table.getSelectedRow());
			}
		};

		public void mousePressed(MouseEvent e) {
			if (e.getSource() instanceof JTable) {
				viewSelectedMember(table.getSelectedRow());
			}
		};
	};
	
	KeyListener kListener = new KeyListener() {

		// 사용하지 않음.
		@Override
		public void keyTyped(java.awt.event.KeyEvent e) {}

		@Override
		public void keyReleased(java.awt.event.KeyEvent e) {}

		@Override
		public void keyPressed(java.awt.event.KeyEvent e) {
			if (e.getSource() == keywordTF) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					searchMemberList(keyfieldCB.getSelectedIndex(), keywordTF.getText());
				}
			}
		}
	};

	private void addComponent() {
		northPanel = new JPanel();
		northPanel.setBounds(12, 10, 946, 254);
		add(northPanel);
		northPanel.setLayout(null);

		keyfieldCB = new JComboBox();
		keyfieldCB
				.setModel(new DefaultComboBoxModel(new String[] { "\uC774\uB984", "ID", "\uC804\uD654\uBC88\uD638" }));
		keyfieldCB.setBounds(20, 20, 80, 20);
		northPanel.add(keyfieldCB);

		keywordTF = new JTextField();
		keywordTF.setToolTipText("\uAC80\uC0C9\uC5B4\uB97C \uC785\uB825\uD558\uC138\uC694");
		keywordTF.setBounds(112, 20, 300, 21);
		northPanel.add(keywordTF);
		keywordTF.setColumns(10);

		retrieveMemberListBtn = new JButton("\uAC80\uC0C9");
		retrieveMemberListBtn.setBounds(424, 19, 63, 23);
		northPanel.add(retrieveMemberListBtn);

		retrieveAllMemberBtn = new JButton("\uC804\uCCB4\uD68C\uC6D0\uC870\uD68C");
		retrieveAllMemberBtn.setBounds(499, 19, 130, 23);
		northPanel.add(retrieveAllMemberBtn);

		idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setBounds(43, 100, 57, 15);
		northPanel.add(idLabel);

		nameLabel = new JLabel("\uC774\uB984");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setBounds(43, 150, 57, 15);
		northPanel.add(nameLabel);

		phoneLabel = new JLabel("\uC804\uD654\uBC88\uD638");
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setBounds(43, 200, 57, 15);
		northPanel.add(phoneLabel);

		birthdayLabel = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
		birthdayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		birthdayLabel.setBounds(301, 100, 57, 15);
		northPanel.add(birthdayLabel);

		withDrawLabel = new JLabel("\uD0C8\uD1F4\uC5EC\uBD80");
		withDrawLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		withDrawLabel.setBounds(301, 150, 57, 15);
		northPanel.add(withDrawLabel);

		idTF = new JTextField();
		idTF.setEditable(false);
		idTF.setToolTipText("");
		idTF.setBounds(112, 97, 180, 21);
		northPanel.add(idTF);
		idTF.setColumns(10);

		nameTF = new JTextField();
		nameTF.setToolTipText("\uC774\uB984\uC744 \uC785\uB825\uD558\uC138\uC694 (\uC608: \uD64D\uAE38\uB3D9)");
		nameTF.setBounds(112, 147, 180, 21);
		northPanel.add(nameTF);
		nameTF.setColumns(10);

		phoneTF = new JTextField();
		phoneTF.setToolTipText("\uC804\uD654\uBC88\uD638\uB97C \uC785\uB825\uD558\uC138\uC694.(\uC608: 010-1234-1234)");
		phoneTF.setBounds(112, 197, 180, 21);
		northPanel.add(phoneTF);
		phoneTF.setColumns(10);

		birthdayTF = new JTextField();
		birthdayTF.setToolTipText("\uC0DD\uB144\uC6D4\uC77C\uC744 \uC785\uB825\uD558\uC138\uC694 (YYYY.MM.DD)");
		birthdayTF.setBounds(371, 97, 180, 21);
		northPanel.add(birthdayTF);
		birthdayTF.setColumns(10);

		withdrawTF = new JTextField();
		withdrawTF.setEditable(false);
		withdrawTF.setBounds(371, 147, 180, 21);
		northPanel.add(withdrawTF);
		withdrawTF.setColumns(10);

		registerBtn = new JButton("\uB4F1\uB85D");
		registerBtn.setBounds(641, 196, 97, 23);
		northPanel.add(registerBtn);

		initBtn = new JButton("\uCD08\uAE30\uD654");
		initBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetviewInfo();
			}
		});
		initBtn.setBounds(750, 196, 97, 23);
		northPanel.add(initBtn);

		updateBtn = new JButton("\uC218\uC815");
		updateBtn.setBounds(641, 196, 97, 23);
		northPanel.add(updateBtn);

		centerPanel = new JPanel();
		centerPanel.setBounds(12, 274, 946, 478);
		add(centerPanel);
		centerPanel.setLayout(null);

		// 테이블에 들어갈 체크박스, 버튼 추가
		box = new JCheckBox();
		dtm = new DefaultTableModel(new Object[][] {}, new String[] { "\uC120\uD0DD", "ID", "\uC774\uB984",
				"\uC804\uD654\uBC88\uD638", "\uC0DD\uB144\uC6D4\uC77C", "\uD0C8\uD1F4\uC5EC\uBD80", "\uAD00\uB9AC" }) 
			{
				@Override
	            public boolean isCellEditable(int row, int column) {
	               if (column == 0||column == 6) { 
	                  return true;
	               }
	               return false;
	            }
			};
		table = new JTable(dtm);
		table.setFillsViewportHeight(true);

		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(box));
		table.getColumnModel().getColumn(0).setCellRenderer(dcr);
		table.getColumnModel().getColumn(1).setPreferredWidth(121);
		table.getColumnModel().getColumn(2).setPreferredWidth(104);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(113);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(59);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(49);
		table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		withdrawBtn = new JButton("\uC120\uD0DD\uD68C\uC6D0\uD0C8\uD1F4");
		withdrawBtn.setBounds(808, 428, 126, 23);
		centerPanel.add(withdrawBtn);

		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(12, 10, 922, 396);
		centerPanel.add(scrollPane);
	}

	private void init() {
		setBackground(Color.WHITE);
		setSize(970, 762);
		setLayout(null);

		addComponent();
		addActionListener();
	}

	public MemberPanel() {
		init();
	}
	
	
	// ------------------------------------------------------------------------------------------------
	// 기능부

	// 테이블에 행추가 하기전에. 다 지워주는거
	private void deleteTableRows() {
		dtm.setRowCount(0);
	}

	/**
	 * sub		: 회원 조건검색
	 * param	: selectedKeyfield: 콤보박스 선택, keyword: 검색어
	 * return 	: boolean: true(검색성공), false(검색실패)
	 * dept		: 조건에 맞는 회원 검색메소드
	 */
	private boolean searchMemberList(int selectedKeyfield, String keyword) {
		String keyfield = null;

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
				insertTableRows(rowData);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * sub		: 회원탈퇴
	 * param	: idList: 탈퇴할 회원id 목록
	 * return 	: boolean: true(탈퇴성공), false(탈퇴실패)
	 * dept		: 해당 회원탈퇴 처리
	 */
	private boolean removeMember(ArrayList<String> idList) {

		try {
			if(dao.withdrawMemberList(idList)) {
				JOptionPane.showMessageDialog(this, "선택한 회원이 탈퇴되었습니다.");
				searchAllmemberList();
			} 
			return true;
		} catch (SQLException e) {
			if (e.getErrorCode() == 20001) {
				JOptionPane.showMessageDialog(this, "이미 탈퇴한 회원입니다.");
			} else if (e.getErrorCode() == 20002) {
				JOptionPane.showMessageDialog(this, "대여목록이 있는 회원입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * sub		: 회원 상세조회
	 * param	: index: JTable의 selectedRow
	 * return 	: 
	 * dept		: JTable클릭시 해당 회원정보 가져오기
	 */
	private void viewSelectedMember(int index) {
		idTF.setText(table.getValueAt(index, 1).toString());
		nameTF.setText(table.getValueAt(index, 2).toString());
		phoneTF.setText(table.getValueAt(index, 3).toString());
		birthdayTF.setText(table.getValueAt(index, 4).toString());
		withdrawTF.setText(table.getValueAt(index, 5).toString());
	}

	/**
	 * sub		: 회원 상세조회창 초기화
	 * param	: 
	 * return 	: 
	 * dept		: 상세조회 패널의 TextField 초기화 및 회원등록 준비
	 */
	private void resetviewInfo() {
		idTF.setText(null);
		nameTF.setText(null);
		phoneTF.setText(null);
		birthdayTF.setText(null);
		withdrawTF.setText(null);

		registerBtn.setVisible(true);
		updateBtn.setVisible(false);
		nameTF.setEditable(true);
		phoneTF.setEditable(true);
		birthdayTF.setEditable(true);
	}

	/**
	 * sub		: 전체회원검색
	 * param	: 
	 * return 	: 
	 * dept		: DB에 저장된 전체회원을 검색한다.
	 */
	private void searchAllmemberList() {
		resetviewInfo();
		try {
			nameTF.setEditable(false);
			phoneTF.setEditable(false);
			birthdayTF.setEditable(false);
			Vector<Vector<Object>> rowData = dao.retrieveAllMemberList();
			insertTableRows(rowData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * sub		: 유효성검사
	 * param	: 
	 * return 	: int : 0(정상), 1(전화번호 형식틀림), 2(생일 형식 틀림)
	 * dept		: 회원등록시 전화번호, 생일에 대한 유효성검사 메소드
	 */
	private int checkTF() {
		String phone_ptn = "^01(?:\\d)-(?:\\d{3}|\\d{4})-\\d{4}$"; // 전화번호
		String bday_ptn = "^(18[0-9][0-9]|19[0-9][0-9]|20\\d{2})(\\/|-|.)(0[0-9]|1[0-2])(\\/|-|.)(0[1-9]|[1-2][0-9]|3[0-1])$"; // 생일

		Pattern phone = Pattern.compile(phone_ptn);
		Matcher phoneMatch = phone.matcher(phoneTF.getText());

		Pattern bday = Pattern.compile(bday_ptn);
		Matcher birthdayMatch = bday.matcher(birthdayTF.getText());

		if (!phoneMatch.matches()) {// 전화번호 형식이 틀리고
			return 1;
		}
		if (!birthdayMatch.matches()) {	// 생일 형식이 틀리면
			return 2;
		}
		return 0;
	}

	/**
	 * sub		: 회원등록
	 * param	: name, phone, birthday	// 지금은 없지만 추가하는게 더 좋다.
	 * return 	: 
	 * dept		: 해당 정보로 회원등록 실행
	 */
	private void registerMemberlist() {

		try {
			int index = 1;
			if (checkTF() == 0) {
				index = JOptionPane.showConfirmDialog(this, "등록하시겠습니까?", "등록", 2);
			} else if (checkTF() == 1) {
				JOptionPane.showMessageDialog(this, "전화번호 형식을 확인해주세요.");
			} else if (checkTF() == 2) {
				JOptionPane.showMessageDialog(this, "생년월일 형식을 확인해주세요.");
			}
			if (index == 0) {
				JOptionPane.showMessageDialog(this, "회원이 등록되었습니다.");
				dao.insertMember(new MemberVO(nameTF.getText(), phoneTF.getText(), birthdayTF.getText()));
				searchMemberList(0, nameTF.getText().toString());
				resetviewInfo();
				nameTF.setEditable(false);
				phoneTF.setEditable(false);
				birthdayTF.setEditable(false);

				Vector<Vector<Object>> rowData = dao.retrieveAllMemberList();
				insertTableRows(rowData);
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == 20011) {
				JOptionPane.showMessageDialog(this, "이미 가입된 회원입니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * sub		: 회원정보 수정
	 * param	: name, phone, birthday	// 지금은 없지만 추가하는게 더 좋다.
	 * return 	: 
	 * dept		: 해당 정보로 회원정보 수정 실행
	 */
	private void updateMemberInfo() {

		try {
			int index = 1;
			if (checkTF() == 0) {
				index = JOptionPane.showConfirmDialog(this, "수정하시겠습니까", "수정", 2);
			} else if (checkTF() == 1) {
				JOptionPane.showMessageDialog(this, "전화번호 형식을 확인해주세요.");
			} else if (checkTF() == 2) {
				JOptionPane.showMessageDialog(this, "생년월일 형식을 확인해주세요.");
			}

			if (index == 0) {
				dao.updateMember(new MemberVO(idTF.getText(), nameTF.getText(), phoneTF.getText(), birthdayTF.getText(),
						withdrawTF.getText()));
				deleteTableRows();
				searchMemberList(1, idTF.getText().toString());

				registerBtn.setVisible(true);
				updateBtn.setVisible(false);
				nameTF.setEditable(false);
				phoneTF.setEditable(false);
				birthdayTF.setEditable(false);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * sub		: 테이블 내용 쓰기
	 * param	: Vector<Vector<Object>> rowData: 테이블에 써질 내용
	 * return 	: 
	 * dept		: 테이블을 한번 비운뒤, 테이블에 rowdata를 추가한다.
	 */
	private void insertTableRows(Vector<Vector<Object>> rowData) {
		deleteTableRows();
		for (Vector<Object> rd : rowData) {
			dtm.addRow(rd);
		}
	}

}
