package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
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

public class ReturnPanel extends JPanel {
		
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTable table_1;
	private JTable table_2;
	private DefaultTableModel dm;
	private JButton search;
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
		
		textField = new JTextField("");
		textField.setEditable(false);
		textField.setBounds(250, 24, 70, 25);
		panel.add(textField);
		textField.setColumns(10);
				
		JLabel lblNewLabel = new JLabel("회원명");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel.setBounds(330, 24, 45, 20);
		panel.add(lblNewLabel);
		
		textField_1 = new JTextField("");
		textField_1.setEditable(false);
		textField_1.setBounds(390, 24, 70, 25);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("전화번호");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_1.setBounds(470, 24, 55, 20);
		panel.add(lblNewLabel_1);
		
		textField_2 = new JTextField("");
		textField_2.setEditable(false);
		textField_2.setBounds(535, 24, 100, 25);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("대여가능권수");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_2.setBounds(645, 24, 80, 20);
		panel.add(lblNewLabel_2);
		
		textField_3 = new JTextField("");
		textField_3.setEditable(false);
		textField_3.setBounds(735, 24, 100, 25);
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 95, 948, 273);
		add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 924, 253);
		panel_1.add(scrollPane);
		
		
		
		
		// 반납/연장 목록 조회 테이블
		String[] columnNames = {"도서ID", "제목", "저자", "출판사", "장르", "반납예정일", "연장여부", "", ""};
	    dm = new DefaultTableModel(columnNames, 0);
	    Class[] columnTypes = new Class[] {
			Integer.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class};
		
		table = new JTable(dm);
		
		RentalDAO rental = new RentalDAO();
		
		try{
		Vector<Vector<Object>> rowData = rental.selectRentingBooksByMember(textField.getText());
		for(int i = 0; i<rowData.size(); i++){
			dm.addRow(rowData.elementAt(i));
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor(new JCheckBox()));
		table.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer1());
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor1(new JCheckBox()));
		
		scrollPane.setViewportView(table);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 570, 948, 182);
		add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton_2 = new JButton("반납하기");
		btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 40));
		btnNewButton_2.setBounds(724, 10, 210, 165);
		panel_2.add(btnNewButton_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(12, 10, 700, 162);
		panel_2.add(scrollPane_2);
		
		//반납 장바구니 테이블
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {},new String[] {"\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", ""}) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		table_2.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer02());
		table_2.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor02(new JCheckBox()));
		
		
		scrollPane_2.setViewportView(table_2);
				
		JPanel panel_3 = new JPanel();
		panel_3.setLayout(null);
		panel_3.setBounds(10, 378, 948, 182);
		add(panel_3);
		
		JButton button = new JButton("연장하기");
		button.setFont(new Font("굴림", Font.BOLD, 40));
		button.setBounds(724, 10, 210, 165);
		panel_3.add(button);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 10, 700, 162);
		panel_3.add(scrollPane_1);
		
		//연장 장바구니 테이블
		table_1 = new JTable();
		table_1.setModel(new DefaultTableModel(
			new Object[][] {},new String[] {"\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", ""}) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		table_1.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer01());
		table_1.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor01(new JCheckBox()));
		
		scrollPane_1.setViewportView(table_1);
	
	
		
	}
		
	MouseAdapter listener = new MouseAdapter() {

		@Override
		public void mouseClicked(MouseEvent e) {
			Object target = e.getButton();
			if(target == search){
				SearchMemberFrame sm = new SearchMemberFrame();
				sm.setVisible(true);
			}
			
		}
	};
	
	
	
	
	


	// 반납/연장 목록 테이블 - 연장 장바구니 추가 버튼
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
			setText((value == null) ? "연장" : value.toString());
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
			label = (value == null) ? "연장" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 연장하기");
			
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

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "반납" : value.toString());
			return this;
		}
	}
	DefaultTableCellRenderer dcr1 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
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

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(table.getBackground());
			}
			label = (value == null) ? "반납" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 반납하기");
			
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

		public Component getTableCellRendererComponent(JTable table_1, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table_1.getSelectionForeground());
				setBackground(table_1.getSelectionBackground());
			} else {
				setForeground(table_1.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "연장취소" : value.toString());
			return this;
		}
	}
	DefaultTableCellRenderer dcr01 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table_1, Object value, boolean isSelected, boolean hasFocus,
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

		public Component getTableCellEditorComponent(JTable table_1, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table_1.getSelectionForeground());
				button.setBackground(table_1.getSelectionBackground());
			} else {
				button.setForeground(table_1.getForeground());
				button.setBackground(table_1.getBackground());
			}
			label = (value == null) ? "연장취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table_1.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 연장취소");
			
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

		public Component getTableCellRendererComponent(JTable table_2, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				setForeground(table_2.getSelectionForeground());
				setBackground(table_2.getSelectionBackground());
			} else {
				setForeground(table_2.getForeground());
				setBackground(UIManager.getColor("Button.background"));
			}
			setText((value == null) ? "반납취소" : value.toString());
			return this;
		}
	}
	DefaultTableCellRenderer dcr02 = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table_2, Object value, boolean isSelected, boolean hasFocus,
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

		public Component getTableCellEditorComponent(JTable table_2, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table_2.getSelectionForeground());
				button.setBackground(table_2.getSelectionBackground());
			} else {
				button.setForeground(table_2.getForeground());
				button.setBackground(table_2.getBackground());
			}
			label = (value == null) ? "반납취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;
			
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table_2.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 반납취소");
			
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


