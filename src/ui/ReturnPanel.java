package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
import domain.MemberVO;

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
	
	Vector<Object> returnbooks;
	Vector<Object> renewalbooks;
	SearchMemberFrame smf = new SearchMemberFrame(this);
	RentalDAO rental = new RentalDAO();
	MemberVO member = new MemberVO();
	
	
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
		

		try{
		 
			smf.rowData = rental.selectRentingBooksByMember(memberidtf.getText());
			
			for(int i = 0; i<smf.rowData.size(); i++){
				retrievetabledm.addRow(smf.rowData.elementAt(i));
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
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
		String[] columnNames2 = {"\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", ""};
			
		Class[] columnTypes2 = new Class[] {String.class, String.class, String.class, Object.class};
		returncarttabledm = new DefaultTableModel(columnNames2, 0);
		returncarttable = new JTable(returncarttabledm);
		
		returncarttable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer02());
		returncarttable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor02(new JCheckBox()));
		
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
		String[] columnNames1 = {"\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", ""};
		
		Class[] columnTypes1 = new Class[] {String.class, String.class, String.class, Object.class};
		
		renewalcarttabledm = new DefaultTableModel(columnNames1, 0);
		
		renewalcarttable = new JTable(renewalcarttabledm);
		
		renewalcarttable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer01());
		renewalcarttable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor01(new JCheckBox()));
		
		scrollPane_1.setViewportView(renewalcarttable);
	
	
		addEventListner();
		
		
	}
			
	private void addEventListner() {
		search.addActionListener(this);
		returnbutton.addActionListener(this);
		renewalbutton.addActionListener(this);
	}
	
	//여기부터다아아아아아..............
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		
			if(target == search){
				smf.setDefaultCloseOperation(MainFrame.DISPOSE_ON_CLOSE);
				smf.setLocationRelativeTo(null);                                 
				smf.setAlwaysOnTop(true);  
				smf.setVisible(true);
					
			} else if(target == renewalbutton){
				try{
					Vector<Object> renewal = new Vector<Object>();
					if(renewalbooks == null){
						JOptionPane.showMessageDialog(this, "연장 장바구니에 책 목록이 없습니다.");
					}
					for(int i = 0; i<renewalbooks.size(); i++){
						renewalbooks.get(i);
						renewal.addElement(renewalbooks.get(i));
					}
					rental.renewalBooksFromBasket(renewal);
										
				}catch(Exception e2){
					e2.printStackTrace();
				}
	
				
			}else if(target == returnbutton){
				try {
					if(returnbooks == null){
						JOptionPane.showMessageDialog(this, "반납 장바구니에 책 목록이 없습니다.");
					}
					rental.returnBooksFromBasket(renewalbooks);	
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				
				
				
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
			if (isPushed) {
				try{
										
					int index = retrievetable.getSelectedRow();
					Vector<Object> renewalb = new Vector<Object>();
					smf.rowData = rental.selectRentingBooksByMember(memberidtf.getText());
					renewalb.addElement(smf.rowData.get(index).elementAt(1));
					renewalb.addElement(smf.rowData.get(index).elementAt(2));
					renewalb.addElement(smf.rowData.get(index).elementAt(3));
															
						
					renewalcarttabledm.addRow(renewalb);
					
					}catch(Exception e){
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
			super.fireEditingStopped();
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
			if (isPushed) {
				
				try{
					
					int index = retrievetable.getSelectedRow();
					Vector<Object> returnb = new Vector<Object>();
					smf.rowData = rental.selectRentingBooksByMember(memberidtf.getText());
					returnb.addElement(smf.rowData.get(index).elementAt(1));
					returnb.addElement(smf.rowData.get(index).elementAt(2));
					returnb.addElement(smf.rowData.get(index).elementAt(3));
					
					
					
					
					returncarttabledm.addRow(returnb);
						
						
									
					}
				catch(Exception e){
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
			super.fireEditingStopped();
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
			setText((value == null) ? "연장취소" : value.toString());
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
			label = (value == null) ? "연장취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
								
				try{
					
					int index = renewalcarttable.getSelectedRow();
					System.out.println(renewalcarttable.getSelectedRow());
					renewalcarttabledm.removeRow(index);
					
	
					
					}
				catch(Exception e){
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
			super.fireEditingStopped();
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
			setText((value == null) ? "반납취소" : value.toString());
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
			label = (value == null) ? "반납취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				
				try{
					
					int index = returncarttable.getSelectedRow();
					System.out.println(index);
					returncarttabledm.removeRow(index);
	
					
					}
				catch(Exception e){
					// e.printStackTrace();
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
	
	
	
}


