package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;

import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class MemberPanel extends JPanel{
	private JTextField keywordTF;
	private JTextField idTF;
	private JTextField nameTF;
	private JTextField phoneTF;
	private JTextField birthdayTF;
	private JTextField withdrawTF;
	private JTable memberListTable;
	private JTable table;
	
	public MemberPanel() {
		setBackground(Color.WHITE);
		setSize(970, 762);
		setLayout(null);
		
		JPanel northPanel = new JPanel();
		northPanel.setBounds(12, 10, 946, 254);
		add(northPanel);
		northPanel.setLayout(null);
		
		JComboBox keyfieldCB = new JComboBox();
		keyfieldCB.setModel(new DefaultComboBoxModel(new String[] {"\uC774\uB984", "ID", "\uC804\uD654\uBC88\uD638"}));
		keyfieldCB.setBounds(20, 20, 80, 20);
		northPanel.add(keyfieldCB);
		
		keywordTF = new JTextField();
		keywordTF.setToolTipText("\uAC80\uC0C9\uC5B4\uB97C \uC785\uB825\uD558\uC138\uC694");
		keywordTF.setBounds(112, 20, 300, 21);
		northPanel.add(keywordTF);
		keywordTF.setColumns(10);
		
		JButton retrieveMemberListBtn = new JButton("\uAC80\uC0C9");
		retrieveMemberListBtn.setBounds(424, 19, 63, 23);
		northPanel.add(retrieveMemberListBtn);
		
		JButton retrieveAllMemberBtn = new JButton("\uC804\uCCB4\uD68C\uC6D0\uC870\uD68C");
		retrieveAllMemberBtn.setBounds(499, 19, 130, 23);
		northPanel.add(retrieveAllMemberBtn);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		idLabel.setBounds(43, 100, 57, 15);
		northPanel.add(idLabel);
		
		JLabel nameLabel = new JLabel("\uC774\uB984");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setBounds(43, 150, 57, 15);
		northPanel.add(nameLabel);
		
		JLabel phoneLabel = new JLabel("\uC804\uD654\uBC88\uD638");
		phoneLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		phoneLabel.setBounds(43, 200, 57, 15);
		northPanel.add(phoneLabel);
		
		JLabel birthdayLabel = new JLabel("\uC0DD\uB144\uC6D4\uC77C");
		birthdayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		birthdayLabel.setBounds(301, 100, 57, 15);
		northPanel.add(birthdayLabel);
		
		JLabel withDrawLabel = new JLabel("\uD0C8\uD1F4\uC5EC\uBD80");
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
		
		JButton btnNewButton = new JButton("\uB4F1\uB85D");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // 등록메소드
			    
			}
		});
		btnNewButton.setBounds(641, 196, 97, 23);
		northPanel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\uCD08\uAE30\uD654");
		btnNewButton_1.setBounds(750, 196, 97, 23);
		northPanel.add(btnNewButton_1);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(12, 274, 946, 478);
		add(centerPanel);
		centerPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 10, 922, 396);
		centerPanel.add(scrollPane);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null},
			},
			new String[] {
				"\uC120\uD0DD", "ID", "\uC774\uB984", "\uC804\uD654\uBC88\uD638", "\uC0DD\uB144\uC6D4\uC77C", "\uD0C8\uD1F4\uC5EC\uBD80", "\uAD00\uB9AC"
			}
		) {
			Class[] columnTypes = new Class[] {
				Boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(121);
		table.getColumnModel().getColumn(2).setPreferredWidth(104);
		table.getColumnModel().getColumn(3).setPreferredWidth(140);
		table.getColumnModel().getColumn(4).setPreferredWidth(113);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(59);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(49);
		
		
		JButton withdrawBtn = new JButton("\uC120\uD0DD\uD68C\uC6D0\uD0C8\uD1F4");
		withdrawBtn.setBounds(808, 428, 126, 23);
		centerPanel.add(withdrawBtn);
		
		
	}
	
	
	// 기능추가
	
	//등록메소드
	public void insertmember() {
	    String name = nameTF.getText();
	    String phoneNum = phoneTF.getText();
	    String birthDay = birthdayTF.getText();
	    
	}
}


