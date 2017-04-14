package ui;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import dao.BookDAO;
import domain.GenreVO;

public class RetrieveBookPanel extends JPanel implements ActionListener {
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   private JTextField textField_5;
   private JTable table;
   private JButton button, button_1, button_2, button_3;
   private JComboBox comboBox;
   private String[] index = {"전체", "제목", "저자", "출판사", "장르"};
   private List<String> genre;
   
   @Override
	public void actionPerformed(ActionEvent e) {
		   try {
			   Object target = e.getSource();
			   
			   if(target == button) {		// 도서 관리
				   
			   }
			   else if(target == button_1) {		// 수정
				  
			   }
			   else if(target == button_2) {		// 취소
				   textField.setText("");
				   textField_1.setText("");
				   textField_2.setText("");
				   textField_3.setText("");
				   textField_4.setText("");
			   }
			   else if(target == button_3) {		// 검색
				   
			   }
		   }
		   catch(Exception ex) {
			   ex.printStackTrace();
		   }
	}
	
	private void addEventListener() {
		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		button_3.addActionListener(this);
	}
	
	private void addComponent() {
		try {
	      setLayout(null);
	      
	      JPanel panel_1 = new JPanel();
	      panel_1.setBackground(SystemColor.menu);
	      panel_1.setBounds(0, 0, 970, 262);
	      add(panel_1);
	      panel_1.setLayout(null);
	      
	      JTextPane textPane = new JTextPane();
	      textPane.setBackground(SystemColor.menu);
	      textPane.setText("\uC81C\uBAA9");
	      textPane.setBounds(26, 89, 47, 21);
	      panel_1.add(textPane);
	      
	      JTextPane textPane_1 = new JTextPane();
	      textPane_1.setBackground(SystemColor.menu);
	      textPane_1.setText("\uC800\uC790");
	      textPane_1.setBounds(26, 146, 47, 21);
	      panel_1.add(textPane_1);
	      
	      JTextPane textPane_2 = new JTextPane();
	      textPane_2.setBackground(SystemColor.menu);
	      textPane_2.setText("\uC7A5\uB974");
	      textPane_2.setBounds(27, 200, 47, 21);
	      panel_1.add(textPane_2);
	      
	      textField = new JTextField("");
	      textField.setBounds(73, 89, 230, 21);
	      panel_1.add(textField);
	      textField.setColumns(10);
	      
	      comboBox = new JComboBox();
	      BookDAO dao = new BookDAO();
	      List<GenreVO> genres = dao.retrieveGenreList();
	      Vector<String> genre = new Vector<String>();
	      for(int i=0; i<genres.size();i++)
	      	genre.add(genres.get(i).getGenre_name());
	      comboBox.setModel(new DefaultComboBoxModel(genre));
	      comboBox.setBounds(74, 200, 120, 21);
	      panel_1.add(comboBox);			// 장르 검색해서 출력
	      
	      JTextPane textPane_3 = new JTextPane();
	      textPane_3.setBackground(SystemColor.menu);
	      textPane_3.setText("\uC218\uB7C9");
	      textPane_3.setBounds(718, 89, 47, 21);
	      panel_1.add(textPane_3);
	      
	      JSpinner spinner = new JSpinner();
	      spinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
	      spinner.setBounds(765, 89, 66, 21);
	      panel_1.add(spinner);
	      
	      JTextPane textPane_4 = new JTextPane();
	      textPane_4.setBackground(SystemColor.menu);
	      textPane_4.setText("\uCD9C\uD310\uC0AC");
	      textPane_4.setBounds(342, 89, 47, 21);
	      panel_1.add(textPane_4);
	      
	      JTextPane textPane_5 = new JTextPane();
	      textPane_5.setBackground(SystemColor.menu);
	      textPane_5.setText("\uCD9C\uD310\uC77C");
	      textPane_5.setBounds(342, 146, 47, 21);
	      panel_1.add(textPane_5);
	      
	      JTextPane txtpnIsbn = new JTextPane();
	      txtpnIsbn.setBackground(SystemColor.menu);
	      txtpnIsbn.setText("ISBN");
	      txtpnIsbn.setBounds(342, 200, 47, 21);
	      panel_1.add(txtpnIsbn);
	      
	      button = new JButton("\uB3C4\uC11C \uAD00\uB9AC");
	      button.setBounds(830, 30, 90, 40);
	      panel_1.add(button);
	      
	      button_1 = new JButton("\uC218    \uC815");
	      button_1.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      	}
	      });
	      button_1.setBounds(718, 190, 90, 40);
	      panel_1.add(button_1);
	      
	      button_2 = new JButton("\uCDE8    \uC18C");
	      button_2.addActionListener(new ActionListener() {
	      	public void actionPerformed(ActionEvent e) {
	      	}
	      });
	      button_2.setBounds(830, 190, 90, 40);
	      panel_1.add(button_2);
	      
	      textField_1 = new JTextField("");
	      textField_1.setColumns(10);
	      textField_1.setBounds(73, 146, 230, 21);
	      panel_1.add(textField_1);
	      
	      textField_2 = new JTextField("");
	      textField_2.setColumns(10);
	      textField_2.setBounds(401, 89, 287, 21);
	      panel_1.add(textField_2);
	      
	      textField_3 = new JTextField("");
	      textField_3.setColumns(10);
	      textField_3.setBounds(401, 146, 287, 21);
	      panel_1.add(textField_3);
	      
	      textField_4 = new JTextField("");
	      textField_4.setColumns(10);
	      textField_4.setBounds(401, 200, 287, 21);
	      panel_1.add(textField_4);
	      
	      JComboBox comboBox_1 = new JComboBox();
	      comboBox_1.setModel(new DefaultComboBoxModel(index));
	      comboBox_1.setBounds(26, 40, 70, 21);
	      panel_1.add(comboBox_1);
	      
	      textField_5 = new JTextField("");		// 책 검색 필드
	      textField_5.setColumns(10);
	      textField_5.setBounds(108, 40, 287, 21);
	      panel_1.add(textField_5);
	      
	      button_3 = new JButton("\uAC80\uC0C9");
	      button_3.setBounds(407, 39, 70, 23);
	      panel_1.add(button_3);
	      
	      JPanel panel_2 = new JPanel();
	      panel_2.setBackground(SystemColor.menu);
	      panel_2.setBounds(0, 262, 970, 500);
	      add(panel_2);
	      panel_2.setLayout(null);
	      
	      JScrollPane scrollPane = new JScrollPane();
	      scrollPane.setBounds(12, 10, 946, 448);
	      panel_2.add(scrollPane);
	      
	      table = new JTable();
	      table.setRowHeight(21);
	      table.setModel(new DefaultTableModel(
	      	new Object[][] {
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      		{null, null, null, null, null, null},
	      	},
	      	new String[] {
	      		"no", "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "\uC7A5\uB974", "ISBN"
	      	}
	      ) {
	      	Class[] columnTypes = new Class[] {
	      		Integer.class, Object.class, Object.class, Object.class, Object.class, Object.class
	      	};
	      	public Class getColumnClass(int columnIndex) {
	      		return columnTypes[columnIndex];
	      	}
	      	boolean[] columnEditables = new boolean[] {
	      		false, false, false, false, false, false
	      	};
	      	public boolean isCellEditable(int row, int column) {
	      		return columnEditables[column];
	      	}
	      });
	      table.getColumnModel().getColumn(0).setResizable(false);
	      table.getColumnModel().getColumn(0).setPreferredWidth(30);
	      table.getColumnModel().getColumn(0).setMinWidth(20);
	      table.getColumnModel().getColumn(1).setResizable(false);
	      table.getColumnModel().getColumn(1).setPreferredWidth(200);
	      table.getColumnModel().getColumn(2).setResizable(false);
	      table.getColumnModel().getColumn(2).setPreferredWidth(130);
	      table.getColumnModel().getColumn(3).setResizable(false);
	      table.getColumnModel().getColumn(3).setPreferredWidth(130);
	      table.getColumnModel().getColumn(4).setResizable(false);
	      table.getColumnModel().getColumn(4).setPreferredWidth(120);
	      table.getColumnModel().getColumn(5).setResizable(false);
	      table.getColumnModel().getColumn(5).setPreferredWidth(200);
	      scrollPane.setViewportView(table);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	      
	   }
	   
		public void init() {
			addComponent();
			addEventListener();
		} 
		
		public RetrieveBookPanel() {
			init();
		} 
   
   
}