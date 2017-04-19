package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.BookDAO;
import dao.RentalDAO;
import domain.MemberVO;

public class RentalPanel extends JPanel implements ActionListener{
	public JTextField memberIdTF, memberNameTF, phoneTF, rentableBookNumTF, bookSearchConditionTF;
	private JTable booksTable, cartTable;
	private JButton memberSearchBtn, bookSearchBtn, rentBtn, cartBtn;
	private JComboBox combo;
	private Vector<String> condition = new Vector<String>();
	private int bookKeyfield;	// 도서검색 기준
	private String bookKeyword;	// 도서 검색어
	private DefaultTableModel bookDm, cartDm;
	private List<String> rentCart = new ArrayList<String>();
	public static MemberVO member = new MemberVO();
	public void viewAllBooks() {
		for(int i=bookDm.getRowCount()-1; i>=0; i--) {
			bookDm.removeRow(i);
		}
		BookDAO bDAO = new BookDAO();
		bookKeyword = bookSearchConditionTF.getText();
		Vector<Vector<Object>> books = new Vector<Vector<Object>>();
		Vector<Vector<Object>> editedBooks = new Vector<Vector<Object>>();
		try {
			books = bDAO.selectBookList(bookKeyfield, bookKeyword);
			for(int i=0; i<books.size(); i++) {
				Vector<Object> editedbook = new Vector<Object>();
				Vector<Object> book = books.get(i);
				editedbook.addElement(book.elementAt(0));
				editedbook.addElement(book.elementAt(1));
				editedbook.addElement(book.elementAt(2));
				editedbook.addElement(book.elementAt(3));
				editedbook.addElement(book.elementAt(7));
				if(book.elementAt(5).equals("0")) editedbook.addElement("대여 가능");
				else editedbook.addElement("대여 중");
				editedBooks.add(editedbook);
			}
			for(int i=0; i<editedBooks.size(); i++) {
				bookDm.addRow(editedBooks.elementAt(i));
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	

	private void addComponent() {
		setBounds(100, 100, 970, 762);
		setVisible(true);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 948, 75);
		add(panel);
		panel.setLayout(null);

		memberSearchBtn = new JButton("회원검색");
		memberSearchBtn.setFont(new Font("굴림", Font.BOLD, 15));
		memberSearchBtn.setBounds(70, 12, 100, 50);
		panel.add(memberSearchBtn);

		JLabel label = new JLabel("회원ID");
		label.setFont(new Font("굴림", Font.BOLD, 12));
		label.setBounds(195, 24, 45, 20);
		panel.add(label);

		memberIdTF = new JTextField();
		memberIdTF.setEditable(false);
		memberIdTF.setBounds(250, 24, 70, 25);
		panel.add(memberIdTF);
		memberIdTF.setColumns(10);

		JLabel lblNewLabel = new JLabel("회원명");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel.setBounds(330, 24, 45, 20);
		panel.add(lblNewLabel);

		memberNameTF = new JTextField();
		memberNameTF.setEditable(false);
		memberNameTF.setBounds(390, 24, 70, 25);
		panel.add(memberNameTF);
		memberNameTF.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("전화번호");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_1.setBounds(470, 24, 55, 20);
		panel.add(lblNewLabel_1);

		phoneTF = new JTextField();
		phoneTF.setEditable(false);
		phoneTF.setBounds(535, 24, 100, 25);
		panel.add(phoneTF);
		phoneTF.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("대여가능권수");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_2.setBounds(645, 24, 80, 20);
		panel.add(lblNewLabel_2);

		rentableBookNumTF = new JTextField();
		rentableBookNumTF.setEditable(false);
		rentableBookNumTF.setBounds(735, 24, 100, 25);
		panel.add(rentableBookNumTF);
		rentableBookNumTF.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 95, 948, 465);
		add(panel_1);
		panel_1.setLayout(null);

		bookSearchConditionTF = new JTextField();
		bookSearchConditionTF.setBounds(555, 13, 300, 25);
		panel_1.add(bookSearchConditionTF);
		bookSearchConditionTF.setColumns(10);

		bookSearchBtn = new JButton("검색");
		bookSearchBtn.setFont(new Font("굴림", Font.BOLD, 12));
		bookSearchBtn.setBounds(865, 10, 60, 30);
		panel_1.add(bookSearchBtn);

		combo = new JComboBox<String>(condition);
		//combo.setModel(new DefaultComboBoxModel(new String[] { "전체", "제목", "저자", "출판사", "장르" }));
		condition.addElement("전체");
		condition.addElement("제목");
		condition.addElement("저자");
		condition.addElement("출판사");
		condition.addElement("장르");
		combo.setSelectedIndex(0); // 디폴트 : 전체
		bookKeyfield = 1; // 디폴트 : 전체
		combo.setBounds(461, 13, 80, 25);
		panel_1.add(combo);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 48, 924, 407);
		panel_1.add(scrollPane);

		
		//대여하기 목록 조회 테이블
		booksTable = new JTable();
		booksTable.setSurrendersFocusOnKeystroke(true);
		booksTable.setRowHeight(25);
		String[] bookColumnNames = {"도서ID", "제목", "저자", "출판사", "장르", "대여가능상태", ""};
		
		bookDm = new DefaultTableModel(bookColumnNames, 0);
		booksTable.setModel(bookDm);
		
		booksTable.setColumnSelectionAllowed(true);
		booksTable.setCellSelectionEnabled(true);
		scrollPane.setViewportView(booksTable);

		booksTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		booksTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 570, 948, 182);
		add(panel_2);
		panel_2.setLayout(null);

		rentBtn = new JButton("대여하기");
		rentBtn.setFont(new Font("굴림", Font.BOLD, 40));
		rentBtn.setBounds(724, 10, 210, 165);
		panel_2.add(rentBtn);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 10, 700, 162);
		panel_2.add(scrollPane_1);

		
		//대여하기 장바구니 테이블
		cartTable = new JTable();
		cartTable.setSurrendersFocusOnKeystroke(true);
		cartTable.setRowHeight(25);
		String[] cartColumnNames = {"제목","저자","출판사", ""};
		
		cartDm = new DefaultTableModel(cartColumnNames, 0);
		cartTable.setModel(cartDm);
				
		scrollPane_1.setViewportView(cartTable);

		cartTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer1());
		cartTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor1(new JCheckBox()));
	}
	
	private void addEventListener() {
		memberSearchBtn.addActionListener(this);
		bookSearchBtn.addActionListener(this);
		combo.addActionListener(this);
		rentBtn.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if(target == memberSearchBtn) { // 회원 검색 버튼
			for(int i=cartDm.getRowCount()-1; i>=0; i--) {
				cartDm.removeRow(i);
			}
			SearchMemberFrame frame = new SearchMemberFrame(this);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(null);                                 
            frame.setAlwaysOnTop(true);                                      
            frame.setVisible(true);     
			
		} else if(target == combo) { // 도서검색 콤보박스
			bookKeyfield = combo.getSelectedIndex() + 1;
		} else if(target == rentBtn) { // 대여하기 버튼
			if(memberIdTF.getText().length() == 0) {
				JOptionPane.showMessageDialog(rentBtn, "선택된 회원이 없습니다.");
				return;
			}
			
			if(rentCart.size() == 0) {
				JOptionPane.showMessageDialog(rentBtn, "장바구니가 비어 있습니다");
				return;
			}
			
			RentalDAO rDAO = new RentalDAO();
			try {
				rDAO.rentalBooksFromBasket(memberIdTF.getText(), rentCart);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			Integer changed = Integer.parseInt(rentableBookNumTF.getText()) - rentCart.size();
			rentableBookNumTF.setText(changed.toString());
			// 바구니 비우기
			rentCart.clear();
			for(int i=cartDm.getRowCount()-1; i>=0; i--) {
				cartDm.removeRow(i);
			}
			// 도서 목록 상태 최신화
			viewAllBooks();
			JOptionPane.showMessageDialog(rentBtn, "도서가 정상 대여되었습니다.");
			
				
			
		} else if(target == bookSearchBtn) { // 도서 검색 버튼		
			viewAllBooks();
		}	
	}
	
	private void init() {
		setBackground(Color.WHITE);
		addComponent();
		addEventListener();
		viewAllBooks();
		
	}

	public RentalPanel() {
		init();	
	}

	//대여하기 목록 조회 테이블 - 대여장바구니 추가 버튼
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
			setText((value == null) ? "대여하기" : value.toString());
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
			label = (value == null) ? "대여하기" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;

		}

		public Object getCellEditorValue() {
			if (isPushed) {
				RentalDAO rDAO = new RentalDAO();
				String memberId = memberIdTF.getText();
				String penaltyDate = rDAO.overduePenalty(memberId);
				int rentableBookNum = Integer.parseInt(rentableBookNumTF.getText());
				
				int index = booksTable.getSelectedRow();
				
				if(!booksTable.getValueAt(index, 5).equals("대여 가능")) { // 도서가 대여 불가능 상태일 때
					JOptionPane.showMessageDialog(button, "대여가 불가능한 도서입니다.");				
				} else if(memberId.length() == 0) {
					JOptionPane.showMessageDialog(button, "회원을 선택하여 주십시오.");
				} else if(rDAO.hasOverdue(memberId)) { // 연체도서가 있을때
					JOptionPane.showMessageDialog(button, "연체 중인 도서가 있는 회원입니다.");
				} else if(penaltyDate.length() != 0) {
					JOptionPane.showMessageDialog(button, penaltyDate + "까지 대여가 불가능한 회원입니다.");
				} else if(rentableBookNum == 0) { // 대여가능권수가 0일 때 
					JOptionPane.showMessageDialog(button, "대여가능권수가 0입니다.");
				} else if(rentableBookNum == rentCart.size()) { // 장바구니에 대여가능권수보다 책을 많이 담았을 때,
					JOptionPane.showMessageDialog(button, "도서를 더 이상 장바구니에 담을 수 없습니다.");
				} else {
					if(rentCart.contains((String)booksTable.getValueAt(index, 0))){ // 장바구니에 이미 있을 때
						JOptionPane.showMessageDialog(button, "이미 장바구니에 담긴 도서입니다.");
						return null;
					}
					rentCart.add((String)booksTable.getValueAt(index, 0)); // 장바구니에 도서 아이디 추가
									
					Vector<Object> temp = new Vector<Object>();
					temp.addElement(bookDm.getValueAt(booksTable.getSelectedRow(), 1));			
					temp.addElement(bookDm.getValueAt(booksTable.getSelectedRow(), 2));
					temp.addElement(bookDm.getValueAt(booksTable.getSelectedRow(), 3));
					cartDm.addRow(temp);				
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
			try {
				super.fireEditingStopped();
			} catch (Exception e) {
			}
			
		}
	}

	
	
	//대여하기 장바구니 테이블 - 취소하기 버튼
	class ButtonRenderer1 extends JButton implements TableCellRenderer {
		public ButtonRenderer1() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table_1, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table_1.getSelectionForeground());
				setBackground(table_1.getSelectionBackground());
			} else {
				setForeground(table_1.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "대여취소" : value.toString());
			return this;
		}
	}

	DefaultTableCellRenderer dcr1 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table_1, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	class ButtonEditor1 extends DefaultCellEditor {
		//protected JButton button;
		private String label;
		private boolean isPushed;

		public ButtonEditor1(JCheckBox checkBox) {
			super(checkBox);
			cartBtn = new JButton();
			cartBtn.setOpaque(true);
			cartBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}
		

		public Component getTableCellEditorComponent(JTable table_1, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				cartBtn.setForeground(table_1.getSelectionForeground());
				cartBtn.setBackground(table_1.getSelectionBackground());
			} else {
				cartBtn.setForeground(table_1.getForeground());
				cartBtn.setBackground(table_1.getBackground());
			}
			label = (value == null) ? "대여취소" : value.toString();
			cartBtn.setText(label);
			isPushed = true;
			return cartBtn;

		}

		public Object getCellEditorValue() {	
			int index = cartTable.getSelectedRow();	
			
			 if (isPushed) {					
				rentCart.remove(index); // 어차피 리스트에 담긴 순서와 테이블에 보여지는 순서는 같으므로 index로 삭제 가능			
				cartDm.removeRow(index); 
			}
			isPushed = false;
			return new String(label);
		}

		public boolean stopCellEditing() {
			isPushed = false;
			return super.stopCellEditing();
		}

		protected void fireEditingStopped() {
			try {
				super.fireEditingStopped();
			} catch (Exception e) {
			}
			
		}
	}

}
