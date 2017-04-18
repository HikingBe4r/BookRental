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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.RentalDAO;

public class ReturnPanel extends JPanel implements ActionListener{
		
	public JTextField memberidtf;
	public JTextField membernametf;
	public JTextField phonenumbertf;
	public JTextField rentalbooktf;
	private JTable retrievetable;
	private JTable renewalcarttable;
	private JTable returncarttable;
	public DefaultTableModel retrievetabledm;
	private DefaultTableModel renewalcarttabledm;
	private DefaultTableModel returncarttabledm;
	private JButton search;
	private JButton renewalbutton;
	private JButton returnbutton;
	public Vector<Vector<Object>> rowData = new Vector<Vector<Object>>();
	private List<String> returnbooks = new ArrayList<String>();
	private List<String> renewalbooks = new ArrayList<String>();
	private SearchMemberFrame smf = new SearchMemberFrame(this);
	private RentalDAO rental = new RentalDAO();
	
	
	
	public ReturnPanel() {
		setBackground(Color.WHITE);
		
		setBounds(100,100,970,762);
		setVisible(true);
		setLayout(null);
		
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 948, 75);
		add(panel);
		panel.setLayout(null);
		
		search = new JButton("회원검색");
		search.setFont(new Font("굴림", Font.BOLD, 15));
		search.setBounds(70, 12, 100, 50);
		panel.add(search);
		
		JLabel label = new JLabel("회원ID");
		label.setFont(new Font("굴림", Font.BOLD, 12));
		label.setBounds(195, 24, 45, 20);
		panel.add(label);
		
		memberidtf = new JTextField();
		memberidtf.setEditable(false);
		memberidtf.setBounds(250, 24, 70, 25);
		panel.add(memberidtf);
		memberidtf.setColumns(10);
				
		JLabel lblNewLabel = new JLabel("회원명");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel.setBounds(330, 24, 45, 20);
		panel.add(lblNewLabel);
		
		membernametf = new JTextField();
		membernametf.setEditable(false);
		membernametf.setBounds(390, 24, 70, 25);
		panel.add(membernametf);
		membernametf.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("전화번호");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_1.setBounds(470, 24, 55, 20);
		panel.add(lblNewLabel_1);
		
		phonenumbertf = new JTextField();
		phonenumbertf.setEditable(false);
		phonenumbertf.setBounds(535, 24, 100, 25);
		panel.add(phonenumbertf);
		phonenumbertf.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("대여가능권수");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_2.setBounds(645, 24, 80, 20);
		panel.add(lblNewLabel_2);
		
		rentalbooktf = new JTextField();
		rentalbooktf.setEditable(false);
		rentalbooktf.setBounds(735, 24, 100, 25);
		panel.add(rentalbooktf);
		rentalbooktf.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 95, 948, 273);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 924, 253);
		panel_1.add(scrollPane);
		
		
		
		
		// 반납/연장 목록 조회 테이블
		String[] columnNames = {"도서ID", "제목", "저자", "출판사", "장르", "반납예정일", "연장여부", "", ""};
				
		retrievetabledm = new DefaultTableModel(columnNames, 0);
	    	    
	    retrievetable = new JTable(retrievetabledm);
	    retrievetable.setRowHeight(35);

		retrievetable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		retrievetable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));
		retrievetable.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer1());
		retrievetable.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor1(new JCheckBox()));
		
		scrollPane.setViewportView(retrievetable);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 570, 948, 182);
		add(panel_2);
		panel_2.setLayout(null);
		
		returnbutton = new JButton("반납하기");
		returnbutton.setFont(new Font("굴림", Font.BOLD, 40));
		returnbutton.setBounds(724, 10, 210, 165);
		panel_2.add(returnbutton);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 10, 700, 162);
		panel_2.add(scrollPane_2);
		
		//반납 장바구니 테이블
		//String[] columnNames2 = {"도서ID", "제목", "저자", ""};
		String[] columnNames2 = {"도서ID", "제목", "저자", "출판사", "장르", "반납예정일", "연장여부", ""};
			
		Class[] columnTypes2 = new Class[] {String.class, String.class, String.class, Object.class};
		returncarttabledm = new DefaultTableModel(columnNames2, 0);
		returncarttable = new JTable(returncarttabledm);
		returncarttable.setRowHeight(25);
		
		returncarttable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer02());
		returncarttable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor02(new JCheckBox()));
		
		scrollPane_2.setViewportView(returncarttable);
				
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(10, 378, 948, 182);
		add(panel_3);
		
		renewalbutton = new JButton("연장하기");
		renewalbutton.setFont(new Font("굴림", Font.BOLD, 40));
		renewalbutton.setBounds(724, 10, 210, 165);
		panel_3.add(renewalbutton);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 10, 700, 162);
		panel_3.add(scrollPane_1);
		
		//연장 장바구니 테이블
		//String[] columnNames1 = {"도서ID", "제목", "저자", ""};
		String[] columnNames1 = {"도서ID", "제목", "저자", "출판사", "장르", "반납예정일", "연장여부", ""};
		
		Class[] columnTypes1 = new Class[] {String.class, String.class, String.class, Object.class};
		
		renewalcarttabledm = new DefaultTableModel(columnNames1, 0);
		
		renewalcarttable = new JTable(renewalcarttabledm);
		renewalcarttable.setRowHeight(25);
		renewalcarttable.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer01());
		renewalcarttable.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor01(new JCheckBox()));
		
		scrollPane_1.setViewportView(renewalcarttable);
	
	
		addEventListner();
		
		
	}
			
	private void addEventListner() {
		search.addActionListener(this);
		returnbutton.addActionListener(this);
		renewalbutton.addActionListener(this);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		
			if(target == search){ // 회원 검색
				for(int i=retrievetabledm.getRowCount()-1; i>=0; i--) {
					retrievetabledm.removeRow(i);
				}
				for(int i=returncarttabledm.getRowCount()-1; i>=0; i--) {
					returncarttabledm.removeRow(i);
				}
				for(int i=renewalcarttabledm.getRowCount()-1; i>=0; i--) {
					renewalcarttabledm.removeRow(i);
				}
				renewalbooks.clear();
				returnbooks.clear();

				smf.setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);
				smf.setLocationRelativeTo(null);                                 
				smf.setAlwaysOnTop(true);  
				smf.setVisible(true);
					
			} else if(target == renewalbutton){ // 연장하기 버튼
				if(memberidtf.getText().length() == 0) { 
					JOptionPane.showMessageDialog(renewalbutton, "선택된 회원이 없습니다.");
					return;
				}
				
				if(renewalbooks.size() == 0) {
					JOptionPane.showMessageDialog(renewalbutton, "장바구니가 비어 있습니다");
					return;
				}
				
				RentalDAO rDAO = new RentalDAO();
				try {
					rDAO.renewalBooksFromBasket(renewalbooks);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				renewalbooks.clear();
				for(int i=renewalcarttabledm.getRowCount()-1; i>=0; i--) {
					Vector<Object> rowData1 = new Vector<Object>();
					
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 0));
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 1));
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 2));
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 3));
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 4));
					rowData1.add((String)renewalcarttabledm.getValueAt(i, 5));
					rowData1.add("O");
					retrievetabledm.addRow(rowData1); // 반납/연장 테이블에 행 추가
					
					renewalcarttabledm.removeRow(i);
				}
				Vector<Object> rowData1 = new Vector<Object>();
				JOptionPane.showMessageDialog(renewalbutton, "도서가 정상 연장되었습니다.");		
				
			}else if(target == returnbutton){ // 반납하기 버튼
				if(memberidtf.getText().length() == 0) { 
					JOptionPane.showMessageDialog(returnbutton, "선택된 회원이 없습니다.");
					return;
				}
				for(int i=0; i<returncarttabledm.getRowCount(); i++) {
					returnbooks.add((String)returncarttabledm.getValueAt(i, 0)); // 리시트에 아이디들 담기
				}
				if(returnbooks.size() == 0) {
					JOptionPane.showMessageDialog(returnbutton, "장바구니가 비어 있습니다");
					return;
				}
				
				RentalDAO rDAO = new RentalDAO();
				try {
					rDAO.returnBooksFromBasket(returnbooks);	// 도서 반납
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				returnbooks.clear();
				for(int i=returncarttabledm.getRowCount()-1; i>=0; i--) {
					returncarttabledm.removeRow(i);
				}
				JOptionPane.showMessageDialog(returnbutton, "도서가 정상 반납되었습니다.");
						
				
				
			}

		}
		
	

	
	// 반납/연장 목록 테이블 - 연장 장바구니 추가 버튼
	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable retrievetable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(retrievetable.getSelectionForeground());
				setBackground(retrievetable.getSelectionBackground());
			} else {
				setForeground(retrievetable.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "연장" : value.toString());
			return this;
		}
	}
	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable retrievetable, Object value, boolean isSelected, boolean hasFocus,
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

		public Component getTableCellEditorComponent(JTable retrievetable, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(retrievetable.getSelectionForeground());
				button.setBackground(retrievetable.getSelectionBackground());
			} else {
				button.setForeground(retrievetable.getForeground());
				button.setBackground(retrievetable.getBackground());
			}
			label = (value == null) ? "연장" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			int index = retrievetable.getSelectedRow();
			if (isPushed) { // 연장 장바구니에 추가
				try{							
					if(returnbooks.contains((String)retrievetable.getValueAt(index, 0))) {
						// 이미 반납 장바구니에 들어 있으면
						JOptionPane.showMessageDialog(button, "이미 반납 장바구니에 존재합니다.");
					} else if (renewalbooks.contains((String)retrievetable.getValueAt(index, 0))){
						// 이미 연장 장바구니에 들어 있으면
						JOptionPane.showMessageDialog(button, "이미 연장 장바구니에 존재합니다.");
						
					} else if(((String)retrievetabledm.getValueAt(index, 6)).equals("O")) {
						//이미 연장한 도서
						JOptionPane.showMessageDialog(button, "이미 연장 기록이 있는 도서입니다.");
					} else if(rental.isDelayer(memberidtf.getText())) {
						// 연체 회원
						JOptionPane.showMessageDialog(button, "연체 중인 도서가 있는 회원입니다.");
					} else {
						renewalbooks.add((String)retrievetable.getValueAt(index, 0)); // 장바구니에 도서 ID 추가
						// 연장 장바구니 테이블에 출력
						Vector<Object> rowData1 = new Vector<Object>();
						rowData1.add((String)retrievetable.getValueAt(index, 0));
						rowData1.add((String)retrievetable.getValueAt(index, 1));
						rowData1.add((String)retrievetable.getValueAt(index, 2));
						rowData1.add((String)retrievetable.getValueAt(index, 3));
						rowData1.add((String)retrievetable.getValueAt(index, 4));
						rowData1.add((String)retrievetable.getValueAt(index, 5));
						rowData1.add((String)retrievetable.getValueAt(index, 6));
						renewalcarttabledm.addRow(rowData1);
						
						retrievetabledm.removeRow(index); // 장바구니에 추가한 행 삭제	
						
					}
					
								
				} catch(Exception e){
					 e.printStackTrace();
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
	
	
	
	// 반납/연장 목록 테이블 - 반납 장바구니 추가 버튼
	class ButtonRenderer1 extends JButton implements TableCellRenderer {
		public ButtonRenderer1() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable retrievetable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(retrievetable.getSelectionForeground());
				setBackground(retrievetable.getSelectionBackground());
			} else {
				setForeground(retrievetable.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "반납" : value.toString());
			return this;
		}
	}
	DefaultTableCellRenderer dcr1 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable retrievetable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	class ButtonEditor1 extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;
		
		public ButtonEditor1(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable retrievetable, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(retrievetable.getSelectionForeground());
				button.setBackground(retrievetable.getSelectionBackground());
			} else {
				button.setForeground(retrievetable.getForeground());
				button.setBackground(retrievetable.getBackground());
			}
			label = (value == null) ? "반납" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			int index = retrievetable.getSelectedRow();
			
			if (isPushed) { // 반납 장바구니에 추가
				
				try{							
					if(returnbooks.contains((String)retrievetable.getValueAt(index, 0))) {
						// 이미 반납 장바구니에 들어 있으면
						JOptionPane.showMessageDialog(button, "이미 반납 장바구니에 존재합니다.");
					} else if (renewalbooks.contains((String)retrievetable.getValueAt(index, 0))){
						// 이미 연장 장바구니에 들어 있으면
						JOptionPane.showMessageDialog(button, "이미 연장 장바구니에 존재합니다.");
						
					} else {
						
						//returnbooks.add((String)retrievetable.getValueAt(index, 0)); // 장바구니에 도서 ID 추가
						// 반납 장바구니 테이블에 출력
						Vector<Object> rowData1 = new Vector<Object>();
						rowData1.add((String)retrievetable.getValueAt(index, 0));
						rowData1.add((String)retrievetable.getValueAt(index, 1));
						rowData1.add((String)retrievetable.getValueAt(index, 2));
						rowData1.add((String)retrievetable.getValueAt(index, 3));
						rowData1.add((String)retrievetable.getValueAt(index, 4));
						rowData1.add((String)retrievetable.getValueAt(index, 5));
						rowData1.add((String)retrievetable.getValueAt(index, 6));
						
						retrievetabledm.removeRow(index); // 장바구니에 추가한 행 삭제
						
						returncarttabledm.addRow(rowData1);
						
					}
					
								
				} catch(Exception e){
					 e.printStackTrace();
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
		
	
	
	
	//연장 장바구니 테이블 취소 버튼
	class ButtonRenderer01 extends JButton implements TableCellRenderer {
		public ButtonRenderer01() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable renewalcarttable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(renewalcarttable.getSelectionForeground());
				setBackground(renewalcarttable.getSelectionBackground());
			} else {
				setForeground(renewalcarttable.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "취소" : value.toString());
			return this;
		}
	}
	
	DefaultTableCellRenderer dcr01 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable renewalcarttable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

	class ButtonEditor01 extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;
		
		public ButtonEditor01(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable renewalcarttable, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(renewalcarttable.getSelectionForeground());
				button.setBackground(renewalcarttable.getSelectionBackground());
			} else {
				button.setForeground(renewalcarttable.getForeground());
				button.setBackground(renewalcarttable.getBackground());
			}
			label = (value == null) ? "취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			int index = renewalcarttable.getSelectedRow();
			if (isPushed) {	// 연장 취소버튼		
				Vector<Object> rowData1 = new Vector<Object>();
				
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 0));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 1));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 2));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 3));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 4));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 5));
				rowData1.add((String)renewalcarttabledm.getValueAt(index, 6));
				retrievetabledm.addRow(rowData1); // 반납/연장 테이블에 행 추가
				
				renewalbooks.remove(index); // 장바구니에 담긴 순서와 테이블에 표시되는 순서가 같으므로 인덱스로 삭제 가능
				renewalcarttabledm.removeRow(index); // 해당 행 삭제
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
	
	
	
	
	
	//반납 장바구니 테이블 취소 버튼
	class ButtonRenderer02 extends JButton implements TableCellRenderer {
		public ButtonRenderer02() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable returncarttable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(returncarttable.getSelectionForeground());
				setBackground(returncarttable.getSelectionBackground());
			} else {
				setForeground(returncarttable.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "취소" : value.toString());
			return this;
		}
	}
	
	DefaultTableCellRenderer dcr02 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable rentalcarttable, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};


	class ButtonEditor02 extends DefaultCellEditor {
		protected JButton button;
		private String label;
		private boolean isPushed;
		
		public ButtonEditor02(JCheckBox checkBox) {
			super(checkBox);
			button = new JButton();
			button.setOpaque(true);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable returncarttable, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(returncarttable.getSelectionForeground());
				button.setBackground(returncarttable.getSelectionBackground());
			} else {
				button.setForeground(returncarttable.getForeground());
				button.setBackground(returncarttable.getBackground());
			}
			label = (value == null) ? "취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			int index = returncarttable.getSelectedRow();
			
			if (isPushed) { // 반납 취소버튼
				Vector<Object> rowData1 = new Vector<Object>();
				rowData1.add((String)returncarttabledm.getValueAt(index, 0));
				rowData1.add((String)returncarttabledm.getValueAt(index, 1));
				rowData1.add((String)returncarttabledm.getValueAt(index, 2));
				rowData1.add((String)returncarttabledm.getValueAt(index, 3));
				rowData1.add((String)returncarttabledm.getValueAt(index, 4));
				rowData1.add((String)returncarttabledm.getValueAt(index, 5));
				rowData1.add((String)returncarttabledm.getValueAt(index, 6));
				
				retrievetabledm.addRow(rowData1); // 반납/연장 테이블에 행 추가
				
				//returnbooks.remove(index); // 장바구니에 담긴 순서와 테이블에 표시되는 순서가 같으므로 인덱스로 삭제 가능
				returncarttabledm.removeRow(index);	// 해당 행 삭제
				
				//retrievetabledm.fireTableDataChanged();
				//returncarttabledm.fireTableDataChanged();
						
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


