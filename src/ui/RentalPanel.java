package ui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import ui.ReturnPanel.ButtonEditor;
import ui.ReturnPanel.ButtonRenderer;

import javax.swing.JScrollPane;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.beans.VetoableChangeListener;
import java.rmi.server.SocketSecurityException;
import java.beans.PropertyChangeEvent;
import javax.swing.DebugGraphics;
import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;

import java.awt.Component;
import java.awt.Font;
import java.awt.Color;

public class RentalPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;
	private JTable table_1;
	private JButton btn;

	public RentalPanel() {
		setBackground(Color.WHITE);

		setBounds(100, 100, 970, 762);
		setVisible(true);
		setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 948, 75);
		add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("회원검색");
		btnNewButton.setFont(new Font("굴림", Font.BOLD, 15));
		btnNewButton.setBounds(70, 12, 100, 50);
		panel.add(btnNewButton);

		JLabel label = new JLabel("회원ID");
		label.setFont(new Font("굴림", Font.BOLD, 12));
		label.setBounds(195, 24, 45, 20);
		panel.add(label);

		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(250, 24, 70, 25);
		panel.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel = new JLabel("회원명");
		lblNewLabel.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel.setBounds(330, 24, 45, 20);
		panel.add(lblNewLabel);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(390, 24, 70, 25);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("전화번호");
		lblNewLabel_1.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_1.setBounds(470, 24, 55, 20);
		panel.add(lblNewLabel_1);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		textField_2.setBounds(535, 24, 100, 25);
		panel.add(textField_2);
		textField_2.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("대여가능권수");
		lblNewLabel_2.setFont(new Font("굴림", Font.BOLD, 12));
		lblNewLabel_2.setBounds(645, 24, 80, 20);
		panel.add(lblNewLabel_2);

		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setBounds(735, 24, 100, 25);
		panel.add(textField_3);
		textField_3.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 95, 948, 465);
		add(panel_1);
		panel_1.setLayout(null);

		textField_4 = new JTextField();
		textField_4.setBounds(555, 13, 300, 25);
		panel_1.add(textField_4);
		textField_4.setColumns(10);

		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setFont(new Font("굴림", Font.BOLD, 12));
		btnNewButton_1.setBounds(865, 10, 60, 30);
		panel_1.add(btnNewButton_1);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "전체", "제목", "저자", "출판사", "장르" }));
		comboBox.setBounds(461, 13, 80, 25);
		panel_1.add(comboBox);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 48, 924, 407);
		panel_1.add(scrollPane);

		
		//대여하기 목록 조회 테이블
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] { { "1", "해리포터", "일길동","" , null, null, null }, },
				new String[] { "No", "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "\uC7A5\uB974",
						"\uB300\uC5EC\uAC00\uB2A5\uC0C1\uD0DC", "" }));
		table.setColumnSelectionAllowed(true);
		table.setCellSelectionEnabled(true);
		scrollPane.setViewportView(table);

		table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 570, 948, 182);
		add(panel_2);
		panel_2.setLayout(null);

		JButton btnNewButton_2 = new JButton("대여하기");
		btnNewButton_2.setFont(new Font("굴림", Font.BOLD, 40));
		btnNewButton_2.setBounds(724, 10, 210, 165);
		panel_2.add(btnNewButton_2);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 10, 700, 162);
		panel_2.add(scrollPane_1);

		
		//대여하기 장바구니 테이블
		table_1 = new JTable();
		table_1.setSurrendersFocusOnKeystroke(true);
		table_1.setRowHeight(25);
		table_1.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null }, },
				new String[] { "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "" }) {
			Class[] columnTypes = new Class[] { String.class, String.class, String.class, Object.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		
		
		scrollPane_1.setViewportView(table_1);

		table_1.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer1());
		table_1.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor1(new JCheckBox()));
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
				int index = table.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 대여하기");
				
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

		public Component getTableCellEditorComponent(JTable table_1, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				button.setForeground(table_1.getSelectionForeground());
				button.setBackground(table_1.getSelectionBackground());
			} else {
				button.setForeground(table_1.getForeground());
				button.setBackground(table_1.getBackground());
			}
			label = (value == null) ? "대여취소" : value.toString();
			button.setText(label);
			isPushed = true;
			return button;

		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table_1.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": 대여취소");
				
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
