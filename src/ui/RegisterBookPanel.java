package ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class RegisterBookPanel extends JPanel implements ActionListener{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTable table;
	private JButton button3 = new JButton("\uCD08 \uAE30 \uD654");
	private JSpinner spinner;
	private JComboBox comboBox;
	
	
	private void addComponent(){
		setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 970, 262);
		add(panel_1);
		panel_1.setLayout(null);
		
		JTextPane textPane = new JTextPane();
		textPane.setText("\uC81C\uBAA9");
		textPane.setBounds(26, 58, 47, 21);
		panel_1.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("\uC800\uC790");
		textPane_1.setBounds(26, 120, 47, 21);
		panel_1.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("\uC7A5\uB974");
		textPane_2.setBounds(26, 185, 47, 21);
		panel_1.add(textPane_2);
		
		textField = new JTextField("");
		textField.setBounds(73, 58, 287, 21);
		panel_1.add(textField);
		textField.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"\uC18C\uC124", "\uC2DC/\uC5D0\uC138\uC774", "\uC778\uBB38", "\uAC00\uC815/\uC721\uC544", "\uC694\uB9AC", "\uAC74\uAC15", "\uCDE8\uBBF8/\uC2E4\uC6A9/\uC2A4\uD3EC\uCE20", "\uACBD\uC81C/\uACBD\uC601", "\uC790\uAE30\uACC4\uBC1C", "\uC815\uCE58/\uC0AC\uD68C", "\uC5ED\uC0AC/\uBB38\uD654", "\uC885\uAD50", "\uC608\uC220/\uB300\uC911\uBB38\uD654"}));
		comboBox.setToolTipText("");
		comboBox.setBounds(73, 185, 120, 21);
		panel_1.add(comboBox);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("\uC218\uB7C9");
		textPane_3.setBounds(247, 185, 47, 21);
		panel_1.add(textPane_3);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner.setBounds(294, 185, 66, 21);
		panel_1.add(spinner);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("\uCD9C\uD310\uC0AC");
		textPane_4.setBounds(418, 58, 47, 21);
		panel_1.add(textPane_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setText("\uCD9C\uD310\uC77C");
		textPane_5.setBounds(418, 120, 47, 21);
		panel_1.add(textPane_5);
		
		JTextPane txtpnIsbn = new JTextPane();
		txtpnIsbn.setText("ISBN");
		txtpnIsbn.setBounds(418, 185, 47, 21);
		panel_1.add(txtpnIsbn);
		
		textField_1 = new JTextField("");
		textField_1.setColumns(10);
		textField_1.setBounds(73, 120, 287, 21);
		panel_1.add(textField_1);
		
		textField_2 = new JTextField("");
		textField_2.setColumns(10);
		textField_2.setBounds(477, 58, 287, 21);
		panel_1.add(textField_2);
		
		textField_3 = new JTextField("");
		textField_3.setColumns(10);
		textField_3.setBounds(477, 120, 287, 21);
		panel_1.add(textField_3);
		
		textField_4 = new JTextField("");
		textField_4.setColumns(10);
		textField_4.setBounds(477, 185, 287, 21);
		panel_1.add(textField_4);
		
		JButton btnNewButton_1 = new JButton("\uB3C4\uC11C\uAD00\uB9AC");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1.setBounds(830, 30, 90, 40);
		panel_1.add(btnNewButton_1);
		
				//초기화
		button3.setBounds(830, 110, 90, 40);
		panel_1.add(button3);
		
		 		
		JButton button_4 = new JButton("\uB4F1   \uB85D");
		button_4.setBounds(830, 190, 90, 40);
		panel_1.add(button_4);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setBounds(0, 262, 970, 500);
		add(panel_2);
		panel_2.setLayout(null);
		
		JButton btnNewButton = new JButton("\uC120\uD0DD \uB3C4\uC11C \uB4F1\uB85D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnNewButton.setBounds(845, 443, 113, 35);
		panel_2.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 22, 946, 397);
		panel_2.add(scrollPane);
		
		table = new JTable();
		table.setRowHeight(21);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null},
			},
			new String[] {
				"  no", "                  \uC81C                \uBAA9                     ", "      \uC800  \uC790", "  \uCD9C  \uD310  \uC0AC ", "  \uC7A5  \uB974  ", "           I  S  B  N     ", "     \uC218   \uB7C9   ", "     \uAD00   \uB9AC"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(38);
		table.getColumnModel().getColumn(1).setPreferredWidth(259);
		table.getColumnModel().getColumn(2).setPreferredWidth(86);
		table.getColumnModel().getColumn(3).setPreferredWidth(81);
		table.getColumnModel().getColumn(4).setPreferredWidth(65);
		table.getColumnModel().getColumn(5).setPreferredWidth(139);
		table.getColumnModel().getColumn(6).setPreferredWidth(80);
		table.getColumnModel().getColumn(7).setPreferredWidth(87);
		scrollPane.setViewportView(table);
		
				
	}
	
	private void addEventListener() {
		button3.addActionListener(this);
	} 
	
	public void actionPerformed(ActionEvent e){
		Object target = e.getSource();
		if(target == button3){
			textField.setText("");
			textField_1.setText("");
			textField_2.setText("");
			textField_3.setText("");
			textField_4.setText("");
			comboBox.setSelectedIndex(0);
			spinner.setValue(0);
		}
		
	}
	
	public void init() {
		addComponent();
		addEventListener();
	} // init()
	
	public RegisterBookPanel() {
		init();
	} // RentalHistoryPanel()
	
	
	
}
