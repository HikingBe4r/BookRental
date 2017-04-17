package ui;

import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.BookDAO;
import domain.BookVO;
import domain.GenreVO;

public class RetrieveBookPanel extends JPanel implements ActionListener {
   private JTextField textField;
   private JTextField textField_1;
   private JTextField textField_2;
   private JTextField textField_3;
   private JTextField textField_4;
   private JTextField textField_5;
   private JTable table;
   private JButton button, button_1, button_2, button_3, button_4;
   private JComboBox comboBox, comboBox_1;
   private String[] index = {"전체", "제목", "저자", "출판사", "장르"};
   private List<String> genre;
   private JScrollPane scrollPane;
   private DefaultTableModel dm;
   private int row = -1;
   private JSpinner spinner;
   
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
			if (isPushed) {
				int index = table.getSelectedRow();
				JOptionPane.showMessageDialog(button, index + ": Ouch!");
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
   
   @Override
	public void actionPerformed(ActionEvent e) {
		   try {
			   Object target = e.getSource();
			   
			   if(target == button) {		// 도서 등록 화면 이동

			   }
			   
			   else if(target == button_1) {		// 수정		1. ISBN 중복 -> ERROR   2. 날짜 오류 -> ERROR
				   
				   Vector<Vector<Object>> book = new Vector<Vector<Object>>();
				   BookDAO dao = new BookDAO();
				   //book_id, title, writer, publisher, isbn, status, publish_date, genre_id
				   book = dao.selectBookById((String)dm.getValueAt(row, 5));
				   
				   //String bookId, String subject, String writer, String publisher, String publishDate, String isbn, String isRent, int genre1
				   BookVO mbook = new BookVO((String)book.get(0).get(0), textField.getText(),
			                  textField_1.getText(), textField_2.getText(), textField_3.getText(), textField_4.getText(), "0", comboBox.getSelectedIndex()+1);
				   dao.updateBook(mbook);
				   
			   }
			   
			   else if(target == button_2) {		// 취소
				   
				   Vector<Vector<Object>> book = new Vector<Vector<Object>>();
				   BookDAO dao = new BookDAO();
				   book = dao.selectBookById((String)dm.getValueAt(row, 5));
				   
				   BookVO mbook = new BookVO((String)book.get(0).get(0), (String)book.get(0).get(1),
						   (String)book.get(0).get(2), (String)book.get(0).get(3), ((String)book.get(0).get(6)).substring(0, 10), (String)book.get(0).get(4), "0", comboBox.getSelectedIndex()+1);
				   
				   textField.setText(mbook.getSubject());
				   textField_1.setText(mbook.getWriter());
				   textField_2.setText(mbook.getPublisher());
				   textField_3.setText(mbook.getPublishDate());
				   textField_4.setText(mbook.getIsbn());
				   comboBox.setSelectedIndex(mbook.getGenre1()-1);
				   
			   }
			   else if(target == button_3) {		// 검색
				   dm.setRowCount(0);		// 새로 검색할때마다 0으로
				   int cnt = 1;
				   BookDAO dao = new BookDAO();
				   Vector<Vector<Object>> lists = dao.selectBookList((int)comboBox_1.getSelectedIndex()+1, textField_5.getText());
				   
				   if((int)comboBox_1.getSelectedIndex() != 0 && textField_5.getText().equals("")) {
					   JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.");
					   return;
				   }
				   
				   if(lists.size() == 0) {
					   JOptionPane.showMessageDialog(this, "검색된 정보가 없습니다.");
					   return;
				   }
				   
				   for(int i=0; i<lists.size();i++) {
					   Vector<Object> rowData = new Vector<Object>();
					   // select book_id, title, writer, publisher, isbn, status, publish_date, genre_id
					   //제목 저자 출판사 장르 ISBN
					   rowData.add(false);
					   rowData.addElement(lists.get(i).get(1));
					   rowData.addElement(lists.get(i).get(2));
					   rowData.addElement(lists.get(i).get(3));
					   rowData.addElement(lists.get(i).get(7));
					   rowData.addElement(lists.get(i).get(4));
					   dm.addRow(rowData);
					   
				   }
			   }
			   else if(target == button_4) {			// 삭제
				   for(int i = 0; i < dm.getRowCount(); i++) {
						System.out.println("i번째: "+ dm.getValueAt(i, 0));
						if((boolean)dm.getValueAt(i, 0) == true) {
							//idList.add((String)dtm.getValueAt(i, 1));
							//deleteBookList(idList);
							//String selectedId = (String) dtm.getValueAt(index, 1);	// 해당 칼럼의 id
						}
					}
			   }
		   }
		   
		   catch(Exception ex) {
			   ex.printStackTrace();
		   }
	}
   
   MouseAdapter listener=new MouseAdapter() {
 		@Override public void mouseClicked(MouseEvent e) {
 			try {
	  			BookDAO dao = new BookDAO();
	  			Vector<Vector<Object>> book = new Vector<Vector<Object>>();
	  			row=table.getSelectedRow();
	  			
	  			book = dao.selectBookById((String)dm.getValueAt(row, 5));
	  			
	  			for(int i=0; i<book.size(); i++) {
	  				//book_id, title, writer, publisher, isbn, status, publish_date, genre_id
	  				textField.setText((String)dm.getValueAt(row, 1));
	  				textField_2.setText((String)dm.getValueAt(row, 3));
	  				textField_1.setText((String)dm.getValueAt(row, 2));
	  				comboBox.setSelectedIndex((int)book.get(i).get(7)-1);
	  				textField_4.setText((String)dm.getValueAt(row, 5));
	  				textField_3.setText(((String)book.get(i).get(6)).substring(0, 10));
	  				spinner.setValue(book.size());
	  			}
	  			
 			}
 			catch (Exception ex) {
 				ex.printStackTrace();
 			}
 		}
   };
	
	private void addEventListener() {
		button.addActionListener(this);
		button_1.addActionListener(this);
		button_2.addActionListener(this);
		button_3.addActionListener(this);
		table.addMouseListener(listener);
	}
	
	private void addComponent() {
		try {
	      setLayout(null);
	      
	      JPanel panel_1 = new JPanel();
	      panel_1.setBackground(SystemColor.menu);
	      panel_1.setBounds(0, 0, 970, 760);
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
	      
	      spinner = new JSpinner();
	      spinner.setEnabled(false);
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
	      
	      button = new JButton("도서등록");
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
	      
	      scrollPane = new JScrollPane();
	      scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	      scrollPane.setBounds(12, 303, 946, 390);
	      panel_1.add(scrollPane);
	      
	      dm = new DefaultTableModel(
	  	      	new Object[][] {
	  	      		
	  	      	},
	  	      	new String[] {
	  	      		"no", "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "\uC7A5\uB974", "ISBN"
	  	      	} 
	  	      ) /*{
	  	      	Class[] columnTypes = new Class[] {
	  	      		boolean.class, Object.class, Object.class, Object.class, Object.class, Object.class
	  	      	};
	  	      	public Class getColumnClass(int columnIndex) {
	  	      		return columnTypes[columnIndex];
	  	      	}
	  	      	boolean[] columnEditables = new boolean[] {
	  	      		false, false, false, false, false, false, false
	  	      	};
	  	      	public boolean isCellEditable(int row, int column) {
	  	      		return columnEditables[column];
	  	      	} }*/
	  	     ;
	      
	      table = new JTable();
	      table.setRowHeight(21);
	      table.setModel(dm);
	      table.getColumnModel().getColumn(0).setResizable(false);
	      table.getColumnModel().getColumn(0).setPreferredWidth(30);
		  table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		  table.getColumnModel().getColumn(0).setCellRenderer(dcr);
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
	      
	      comboBox_1 = new JComboBox();
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
	      
	      button_4 = new JButton("선택 도서 삭제");
	      button_4.setBounds(834, 715, 124, 35);
	      panel_1.add(button_4);
	      
	      
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