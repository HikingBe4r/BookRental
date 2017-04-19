package ui;

import java.awt.Component;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
	private JTextField subjectTF;
	private JTextField writerTF;
	private JTextField publisherTF;
	private JTextField publishDateTF;
	private JTextField isbnTF;
	private JTextField textTF;
	private JTable table;
	private JButton registerBookButton, modifyButton, cancelButton, searchButton, deleteButton, deleteButton2;
	private JComboBox genreBox, categoryBox;
	private String[] index = { "전체", "제목", "저자", "출판사", "장르" };
	private List<String> genre;
	private JScrollPane scrollPane;
	private DefaultTableModel dm, dm2;
	private int row = -1;
	private JSpinner spinner;
	public List<String> isbnList = new ArrayList<String>(); // 도서 삭제 시 isbn리스트
	public List<String> idList = new ArrayList<String>(); // 도서 삭제 시 고유 isbn리스트
	Vector<Vector<Object>> removeLists = new Vector<Vector<Object>>(); // 도서 삭제
																		// 시
																		// 도서리스트

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
		protected JButton registerBookButton;
		private String label;
		private boolean isPushed;

		public ButtonEditor(JCheckBox checkBox) {
			super(checkBox);
			registerBookButton = new JButton();
			registerBookButton.setOpaque(true);
			registerBookButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					fireEditingStopped();
				}
			});
		}

		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			if (isSelected) {
				registerBookButton.setForeground(table.getSelectionForeground());
				registerBookButton.setBackground(table.getSelectionBackground());
			} else {
				registerBookButton.setForeground(table.getForeground());
				registerBookButton.setBackground(table.getBackground());
			}
			label = (value == null) ? "수정" : value.toString();
			registerBookButton.setText(label);
			isPushed = true;
			return registerBookButton;
		}

		public Object getCellEditorValue() {
			if (isPushed) {
				int index = table.getSelectedRow();
				JOptionPane.showMessageDialog(registerBookButton, index + ": Ouch!");
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

			if (target == registerBookButton) { // 도서 등록 화면 이동
				System.out.println("등록 화면 버튼");
			}

			else if (target == modifyButton) { // 수정
				cancelButton.setEnabled(true);
				deleteButton.setVisible(true);
				deleteButton2.setVisible(false);
				dm2.setRowCount(0);
				table.setModel(dm);
				setTable();

				if (isbnList.isEmpty() == false) {
					isbnList = null; // 리스트에 정보가 있으면 지운다
				}
				if (removeLists.isEmpty() == false) {
					removeLists = null; // 리스트에 정보가 있으면 지운다
				}

				String regex = "\\d{4}\\-\\d{2}\\-\\d{2}";
				if (!publishDateTF.getText().matches(regex)) {
					JOptionPane.showMessageDialog(this, "출판일에 올바른 정보를 입력해주십시오.");
					return;
				}

				regex = "\\D";
				if (isbnTF.getText().matches(regex) || isbnTF.getText().length() >= 14
						|| isbnTF.getText().length() <= 12) {
					JOptionPane.showMessageDialog(this, "ISBN에 올바른 정보를 입력해주십시오.");
					return;
				}

				if (subjectTF.getText().length() == 0 || writerTF.getText().length() == 0
						|| publisherTF.getText().length() == 0 || publishDateTF.getText().length() == 0
						|| genreBox.getSelectedIndex() == -1 || isbnTF.getText().length() == 0) {
					JOptionPane.showMessageDialog(this, "정보를 입력하세요.");
					return;
				}

				Vector<Vector<Object>> book = new Vector<Vector<Object>>();
				BookDAO dao = new BookDAO();
				// book_id, title, writer, publisher, isbn, status,
				// publish_date, genre_id
				book = dao.selectBookById((String) dm.getValueAt(row, 5));

				// String bookId, String subject, String writer, String
				// publisher, String publishDate, String isbn, String isRent,
				// int genre1
				BookVO mbook = new BookVO((String) book.get(0).get(0), subjectTF.getText(), writerTF.getText(),
						publisherTF.getText(), publishDateTF.getText(), isbnTF.getText(), "0",
						genreBox.getSelectedIndex() + 1);
				dao.updateBook(mbook);

			}

			else if (target == cancelButton) { // 취소
				modifyButton.setEnabled(true);
				deleteButton.setVisible(true);
				deleteButton2.setVisible(false);
				dm2.setRowCount(0);
				table.setModel(dm);
				setTable();

				if (isbnList.isEmpty() == false) {
					isbnList = null; // 리스트에 정보가 있으면 지운다
				}
				if (removeLists.isEmpty() == false) {
					removeLists = null; // 리스트에 정보가 있으면 지운다
				}

				System.out.println("취소 버튼");

				if (row == -1)
					return;

				Vector<Vector<Object>> book = new Vector<Vector<Object>>();
				BookDAO dao = new BookDAO();
				book = dao.selectBookById((String) dm.getValueAt(row, 5));

				if (book.isEmpty()) {
					subjectTF.setText("");
					writerTF.setText("");
					publisherTF.setText("");
					publishDateTF.setText("");
					isbnTF.setText("");
					genreBox.setSelectedIndex(0);
				} else {
					BookVO mbook = new BookVO((String) book.get(0).get(0), (String) book.get(0).get(1),
							(String) book.get(0).get(2), (String) book.get(0).get(3),
							((String) book.get(0).get(6)).substring(0, 10), (String) book.get(0).get(4), "0",
							genreBox.getSelectedIndex() + 1);

					subjectTF.setText(mbook.getSubject());
					writerTF.setText(mbook.getWriter());
					publisherTF.setText(mbook.getPublisher());
					publishDateTF.setText(mbook.getPublishDate());
					isbnTF.setText(mbook.getIsbn());
					genreBox.setSelectedIndex(mbook.getGenre1() - 1);
				}

			} else if (target == searchButton) { // 검색
				modifyButton.setEnabled(true);
				cancelButton.setEnabled(true);
				deleteButton.setVisible(true);
				deleteButton2.setVisible(false);
				dm2.setRowCount(0);
				table.setModel(dm);
				setTable();

				if (isbnList.isEmpty() == false) {
					isbnList = null; // 리스트에 정보가 있으면 지운다
				}
				if (removeLists.isEmpty() == false) {
					removeLists = null; // 리스트에 정보가 있으면 지운다
				}

				System.out.println("검색 버튼");
				dm.setRowCount(0); // 새로 검색할때마다 0으로

				BookDAO dao = new BookDAO();
				Vector<Vector<Object>> lists = dao.selectBookList((int) categoryBox.getSelectedIndex() + 1,
						textTF.getText());

				if ((int) categoryBox.getSelectedIndex() != 0 && textTF.getText().equals("")) {
					JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.");
					return;
				}

				if (lists.size() == 0) {
					JOptionPane.showMessageDialog(this, "검색된 정보가 없습니다.");
					return;
				}

				for (int i = 0; i < lists.size(); i++) {
					boolean isExist = false;
					for (int j = i + 1; j < lists.size(); j++) {
						// select book_id, title, writer, publisher, isbn,
						// status, publish_date, genre_id
						// 제목 저자 출판사 장르 ISBN
						if (lists.get(i).get(4).equals(lists.get(j).get(4)) && i != j)
							isExist = true;
					}
					if (!isExist) {
						Vector<Object> rowData = new Vector<Object>();
						rowData.add(false);
						rowData.addElement(lists.get(i).get(1));
						rowData.addElement(lists.get(i).get(2));
						rowData.addElement(lists.get(i).get(3));
						rowData.addElement(lists.get(i).get(7));
						rowData.addElement(lists.get(i).get(4));
						dm.addRow(rowData);
					}
				}
			}

			else if (target == deleteButton) { // 삭제 버튼 클릭 -> 삭제하려는 도서 리스트 출력하기
				modifyButton.setEnabled(false);
				cancelButton.setEnabled(false);
				deleteButton.setVisible(false);
				deleteButton2.setVisible(true);

				/*
				 * if(isbnList.isEmpty()==false) { isbnList = null; // 리스트에 정보가
				 * 있으면 지운다 } if(removeLists.isEmpty()==false) { removeLists =
				 * null; // 리스트에 정보가 있으면 지운다 }
				 */

				isbnList = new ArrayList<String>();
				removeLists = new Vector<Vector<Object>>();

				BookDAO dao = new BookDAO();

				for (int i = 0; i < dm.getRowCount(); i++) {
					// System.out.println("i번째: "+ dm.getValueAt(i, 0));
					if ((boolean) dm.getValueAt(i, 0) == true) { // checkBox
																	// check 되어
																	// 있으면
						// isbnList.add((String)dm.getValueAt(i, 1)); // isbn
						Vector<Vector<Object>> removeList = dao.selectBookById((String) dm.getValueAt(i, 5));
						for (int j = 0; j < removeList.size(); j++) {
							if (removeList.get(j).get(5).equals("0"))
								removeLists.add(removeList.get(j)); // 체크한 도서
																	// 리스트를 저장
						}
					}
				}

				table.setModel(dm2);
				setTable();

				List<GenreVO> genres = dao.retrieveGenreList();

				for (int i = 0; i < removeLists.size(); i++) {
					Vector<Object> rowData = new Vector<Object>();
					// select book_id, title, writer, publisher, isbn, status,
					// publish_date, genre_id
					// 제목 저자 출판사 장르 ISBN
					rowData.add(false);
					rowData.addElement(removeLists.get(i).get(1));
					rowData.addElement(removeLists.get(i).get(2));
					rowData.addElement(removeLists.get(i).get(3));
					rowData.addElement(genres.get((int) removeLists.get(i).get(7) - 1).getGenre_name());
					rowData.addElement(removeLists.get(i).get(0));
					dm2.addRow(rowData);
				}

				// for(int i=0 ; i<removeLists.size(); i++)
				// idList.add((String)removeLists.get(i).get(0)); // idList에 삭제할
				// 도서 리스트 저장

				dm.setRowCount(0); // 새로 검색할때마다 0으로

			}

			else if (target == deleteButton2) {
				BookDAO dao = new BookDAO();

				for (int i = 0; i < dm2.getRowCount(); i++) {
					// System.out.println("i번째: "+ dm.getValueAt(i, 0));
					if ((boolean) dm2.getValueAt(i, 0) == true) { // checkBox
																	// check 되어
																	// 있으면
						// isbnList.add((String)dm.getValueAt(i, 1)); // isbn
						// idList = dao.selectBookById((String)dm.getValueAt(i,
						// 5)); // isbn 동일한 도서 리스트를 저장
						idList.add((String) removeLists.get(i).get(0));
					}
				}

				int index = JOptionPane.showConfirmDialog(this, "삭제하시겠습니까?", "삭제", 2);

				if (index == 0)
					dao.deleteBookList(idList); // 선택 도서 삭제

				dm.setRowCount(0);
				deleteButton.setVisible(true);
				deleteButton2.setVisible(false);
				table.setModel(dm);
				setTable();

				modifyButton.setEnabled(true);
				cancelButton.setEnabled(true);

				return;
			}
		}

		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	MouseAdapter listener = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				if (dm.getRowCount() != 0) {
					BookDAO dao = new BookDAO();
					Vector<Vector<Object>> book = new Vector<Vector<Object>>();
					row = table.getSelectedRow();

					book = dao.selectBookById((String) dm.getValueAt(row, 5));

					for (int i = 0; i < book.size(); i++) {
						// book_id, title, writer, publisher, isbn, status,
						// publish_date, genre_id
						subjectTF.setText((String) dm.getValueAt(row, 1));
						publisherTF.setText((String) dm.getValueAt(row, 3));
						writerTF.setText((String) dm.getValueAt(row, 2));
						genreBox.setSelectedIndex((int) book.get(i).get(7) - 1);
						isbnTF.setText((String) dm.getValueAt(row, 5));
						publishDateTF.setText(((String) book.get(i).get(6)).substring(0, 10));
						spinner.setValue(book.size());
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	};

	private void addEventListener() {
		registerBookButton.addActionListener(this);
		modifyButton.addActionListener(this);
		cancelButton.addActionListener(this);
		searchButton.addActionListener(this);
		table.addMouseListener(listener);
		deleteButton.addActionListener(this);
		deleteButton2.addActionListener(this);
	}

	private void addComponent() {
		try {
			setLayout(null);

			JPanel panel_1 = new JPanel();
			panel_1.setBackground(SystemColor.menu);
			panel_1.setBounds(0, 0, 970, 760);
			add(panel_1);
			panel_1.setLayout(null);

			JTextPane subject = new JTextPane();
			subject.setEditable(false);
			subject.setBackground(SystemColor.menu);
			subject.setText("\uC81C\uBAA9");
			subject.setBounds(26, 89, 47, 21);
			panel_1.add(subject);

			JTextPane writer = new JTextPane();
			writer.setEditable(false);
			writer.setBackground(SystemColor.menu);
			writer.setText("\uC800\uC790");
			writer.setBounds(26, 146, 47, 21);
			panel_1.add(writer);

			JTextPane genre_TP = new JTextPane();
			genre_TP.setEditable(false);
			genre_TP.setBackground(SystemColor.menu);
			genre_TP.setText("\uC7A5\uB974");
			genre_TP.setBounds(27, 200, 47, 21);
			panel_1.add(genre_TP);

			genreBox = new JComboBox();
			BookDAO dao = new BookDAO();
			List<GenreVO> genres = dao.retrieveGenreList();
			Vector<String> genre = new Vector<String>();
			for (int i = 0; i < genres.size(); i++)
				genre.add(genres.get(i).getGenre_name());

			subjectTF = new JTextField("");
			subjectTF.setBounds(73, 89, 230, 21);
			panel_1.add(subjectTF);
			subjectTF.setColumns(10);
			genreBox.setModel(new DefaultComboBoxModel(genre));
			genreBox.setBounds(74, 200, 120, 21);
			panel_1.add(genreBox); // 장르 검색해서 출력

			JTextPane quantity = new JTextPane();
			quantity.setEditable(false);
			quantity.setBackground(SystemColor.menu);
			quantity.setText("\uC218\uB7C9");
			quantity.setBounds(718, 89, 47, 21);
			panel_1.add(quantity);

			spinner = new JSpinner();
			spinner.setEnabled(false);
			spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spinner.setBounds(765, 89, 66, 21);
			panel_1.add(spinner);

			JTextPane publisher = new JTextPane();
			publisher.setEditable(false);
			publisher.setBackground(SystemColor.menu);
			publisher.setText("\uCD9C\uD310\uC0AC");
			publisher.setBounds(342, 89, 47, 21);
			panel_1.add(publisher);

			JTextPane publishDate = new JTextPane();
			publishDate.setEditable(false);
			publishDate.setBackground(SystemColor.menu);
			publishDate.setText("\uCD9C\uD310\uC77C");
			publishDate.setBounds(342, 146, 47, 21);
			panel_1.add(publishDate);

			JTextPane isbn = new JTextPane();
			isbn.setEditable(false);
			isbn.setBackground(SystemColor.menu);
			isbn.setText("ISBN");
			isbn.setBounds(342, 200, 47, 21);
			panel_1.add(isbn);

			registerBookButton = new JButton("도서등록");
			registerBookButton.setBounds(830, 30, 90, 40);
			panel_1.add(registerBookButton);

			modifyButton = new JButton("\uC218    \uC815");
			modifyButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			modifyButton.setBounds(718, 190, 90, 40);
			panel_1.add(modifyButton);

			cancelButton = new JButton("\uCDE8    \uC18C");
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			cancelButton.setBounds(830, 190, 90, 40);
			panel_1.add(cancelButton);

			writerTF = new JTextField("");
			writerTF.setColumns(10);
			writerTF.setBounds(73, 146, 230, 21);
			panel_1.add(writerTF);

			publisherTF = new JTextField("");
			publisherTF.setColumns(10);
			publisherTF.setBounds(401, 89, 287, 21);
			panel_1.add(publisherTF);

			publishDateTF = new JTextField("");
			publishDateTF.setColumns(10);
			publishDateTF.setBounds(401, 146, 287, 21);
			panel_1.add(publishDateTF);

			isbnTF = new JTextField("");
			isbnTF.setColumns(10);
			isbnTF.setBounds(401, 200, 287, 21);
			panel_1.add(isbnTF);

			scrollPane = new JScrollPane();
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			scrollPane.setBounds(12, 303, 946, 390);
			panel_1.add(scrollPane);

			dm = new DefaultTableModel(new Object[][] {

			}, new String[] { "no", "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "\uC7A5\uB974",
					"ISBN" }) ; /*
								 * { Class[] columnTypes = new Class[] {
								 * boolean.class, Object.class, Object.class,
								 * Object.class, Object.class, Object.class };
								 * public Class getColumnClass(int columnIndex)
								 * { return columnTypes[columnIndex]; }
								 * boolean[] columnEditables = new boolean[] {
								 * false, false, false, false, false, false,
								 * false }; public boolean isCellEditable(int
								 * row, int column) { return
								 * columnEditables[column]; } }
								 */

			dm2 = new DefaultTableModel(new Object[][] {

			}, new String[] { "no", "\uC81C\uBAA9", "\uC800\uC790", "\uCD9C\uD310\uC0AC", "\uC7A5\uB974", "도서 ID" });

			table = new JTable();
			table.setRowHeight(21);
			table.setModel(dm);
			setTable();

			categoryBox = new JComboBox();
			categoryBox.setModel(new DefaultComboBoxModel(index));
			categoryBox.setBounds(26, 40, 70, 21);
			panel_1.add(categoryBox);

			textTF = new JTextField(""); // 책 검색 필드
			textTF.setColumns(10);
			textTF.setBounds(108, 40, 287, 21);
			panel_1.add(textTF);

			searchButton = new JButton("\uAC80\uC0C9");
			searchButton.setBounds(407, 39, 70, 23);
			panel_1.add(searchButton);

			deleteButton = new JButton("선택 도서 삭제");
			deleteButton.setBounds(834, 715, 124, 35);
			panel_1.add(deleteButton);

			deleteButton2 = new JButton("선택 도서 삭제");
			deleteButton2.setBounds(834, 715, 124, 35);
			panel_1.add(deleteButton2);
			deleteButton2.setVisible(false);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setTable() {
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
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
	}

	public void init() {
		addComponent();
		addEventListener();
	}

	public RetrieveBookPanel() {
		init();
	}
}