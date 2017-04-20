package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.BookDAO;
import domain.BookVO;
import domain.GenreVO;

public class RegisterBookPanel extends JPanel implements ActionListener {
	private JTextField idTF;
	private JTextField pTF;
	private JTextField puTF;
	private JTextField dTF;
	private JTextField ISBNTF;
	private JTable table;
	private JButton updatebutton;
	private JButton resetbutton = new JButton("초 기 화");
	private JSpinner spinner;
	private JComboBox comboBox;
	private JButton manageButton = new JButton("도서 관리");
	private CardLayout card = new CardLayout();
	private JButton enterbutton = new JButton("등     록"), choiceButton;
	private DefaultTableModel dm;
	private int row = -1;
	private List<BookVO> rowData = new ArrayList<BookVO>();
	private String regex = "\\D";

	DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox box = new JCheckBox();
			box.setSelected(((Boolean) value).booleanValue());
			box.setHorizontalAlignment(JLabel.CENTER);
			return box;
		}
	};

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
				try {
					row = table.getSelectedRow();

					idTF.setText((String) dm.getValueAt(row, 1));
					puTF.setText((String) dm.getValueAt(row, 3));
					pTF.setText((String) dm.getValueAt(row, 2));
					comboBox.setSelectedItem(dm.getValueAt(row, 5)); // 장르
					ISBNTF.setText((String) dm.getValueAt(row, 6)); // isbn
					dTF.setText((String) dm.getValueAt(row, 4)); // 출판일
					spinner.setValue(dm.getValueAt(row, 7)); // 수량

					enterbutton.setVisible(false);
					updatebutton.setVisible(true);

				} catch (Exception ex) {
					ex.printStackTrace();
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

		DefaultTableCellRenderer dcr = new DefaultTableCellRenderer() {
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JCheckBox box = new JCheckBox();
				box.setSelected(((Boolean) value).booleanValue());
				box.setHorizontalAlignment(JLabel.CENTER);
				return box;
			}
		};

	}

	private void addComponent() {
		setLayout(card);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(0, 0, 970, 262);
		add(panel_1);
		panel_1.setLayout(null);

		JTextPane titlePane = new JTextPane();
		titlePane.setBounds(26, 58, 47, 21);
		titlePane.setText("제목");
		panel_1.add(titlePane);

		JTextPane writerPane = new JTextPane();
		writerPane.setBounds(26, 120, 47, 21);
		writerPane.setText("저자");
		panel_1.add(writerPane);

		JTextPane genrePane = new JTextPane();
		genrePane.setBounds(26, 185, 47, 21);
		genrePane.setText("장르");
		panel_1.add(genrePane);

		comboBox = new JComboBox();
		comboBox.setBounds(73, 185, 120, 21);
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "소설", "시/에세이", "인문", "가정/육아", "요리", "건강", "취미/실용/스포츠",
				"경제/경영", "자기계발", "정치/사회", "역사/문화", "종교", "예술/대중문화" }));
		comboBox.setToolTipText("");
		panel_1.add(comboBox);

		JTextPane quntityPane = new JTextPane();
		quntityPane.setBounds(247, 185, 47, 21);
		quntityPane.setText("수량");
		panel_1.add(quntityPane);

		spinner = new JSpinner();
		spinner.setBounds(294, 185, 66, 21);
		spinner.setModel(new SpinnerNumberModel(1, 1, 999, 1));
		panel_1.add(spinner);

		JTextPane publishPane = new JTextPane();
		publishPane.setBounds(418, 58, 47, 21);
		publishPane.setText("출판사");
		panel_1.add(publishPane);

		JTextPane datePane = new JTextPane();
		datePane.setBounds(418, 120, 47, 21);
		datePane.setText("출판일");
		panel_1.add(datePane);

		JTextPane txtpnIsbn = new JTextPane();
		txtpnIsbn.setBounds(418, 185, 47, 21);
		txtpnIsbn.setText("ISBN");
		panel_1.add(txtpnIsbn);

		idTF = new JTextField("");
		idTF.setBounds(73, 58, 287, 21);
		panel_1.add(idTF);
		idTF.setColumns(10);

		pTF = new JTextField("");
		pTF.setBounds(73, 120, 287, 21);
		pTF.setColumns(10);
		panel_1.add(pTF);

		puTF = new JTextField("");
		puTF.setBounds(477, 58, 287, 21);
		puTF.setColumns(10);
		panel_1.add(puTF);

		dTF = new JTextField("");
		dTF.setBounds(477, 120, 287, 21);
		dTF.setColumns(10);
		panel_1.add(dTF);

		ISBNTF = new JTextField("");
		ISBNTF.setBounds(477, 185, 287, 21);
		ISBNTF.setColumns(10);
		panel_1.add(ISBNTF);

		manageButton.setBounds(830, 30, 90, 40);
		panel_1.add(manageButton);

		resetbutton.setBounds(830, 110, 90, 40);
		panel_1.add(resetbutton);

		enterbutton.setBounds(830, 190, 90, 40);
		panel_1.add(enterbutton);
		enterbutton.setVisible(true);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 285, 946, 397);
		panel_1.add(scrollPane);

		table = new JTable();

		table.setRowHeight(21);
		dm = new DefaultTableModel(new String[] { "no", "    제                    목  ", " 저  자  ", " 출 판 사 ", " 출 판 일 ",
				" 장 르 ", "   I  S  B  N   ", " 수 량 ", " 관 리 " }, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if (column == 0 || column == 8) {
					return true;
				}
				return false;
			}
		};
		table.setModel(dm);

		JCheckBox box = new JCheckBox();
		box.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					row = table.getSelectedRow();
					if (box.isSelected()) {

						dm.setValueAt(true, row, 0);

						String title = (String) dm.getValueAt(row, 1);
						String writer = (String) dm.getValueAt(row, 2);
						String publisher = (String) dm.getValueAt(row, 3);
						String publishDate = (String) dm.getValueAt(row, 4);
						String genreName = (String) dm.getValueAt(row, 5);
						int genreId = 0;
						String isbn = (String) dm.getValueAt(row, 6);
						int quantity = (Integer) dm.getValueAt(row, 7);

						BookDAO dao = new BookDAO();
						List<GenreVO> genres = dao.retrieveGenreList();

						for (int i = 0; i < genres.size(); i++)
							if (genres.get(i).getGenre_name().equals(genreName))
								genreId = genres.get(i).getGenre_id();

						for (int i = 0; i < quantity; i++) {
							BookVO book = new BookVO();
							book.setSubject(title);
							book.setWriter(writer);
							book.setPublisher(publisher);
							book.setPublishDate(publishDate);
							book.setGenre1(genreId);
							book.setIsbn(isbn);

							rowData.add(book);
						}
					} else {
						if (row != -1) {
							dm.setValueAt(false, row, 0);
							rowData.remove(row);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

		table.getColumn("no").setCellEditor(new DefaultCellEditor(box));
		table.getColumn("no").setCellRenderer(dcr);
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(41);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(124);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(47);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(67);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(59);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(5).setPreferredWidth(64);
		table.getColumnModel().getColumn(5).setResizable(false);
		table.getColumnModel().getColumn(6).setPreferredWidth(123);
		table.getColumnModel().getColumn(6).setResizable(false);
		table.getColumnModel().getColumn(7).setPreferredWidth(56);
		table.getColumnModel().getColumn(7).setResizable(false);
		table.getColumnModel().getColumn(8).setPreferredWidth(52);
		table.getColumnModel().getColumn(8).setResizable(false);
		table.getColumnModel().getColumn(8).setCellRenderer(new ButtonRenderer());
		table.getColumnModel().getColumn(8).setCellEditor(new ButtonEditor(new JCheckBox()));

		scrollPane.setViewportView(table);

		choiceButton = new JButton("\uC120 \uD0DD \uB3C4 \uC11C \uB4F1 \uB85D");
		choiceButton.setBounds(817, 705, 141, 40);
		panel_1.add(choiceButton);

		updatebutton = new JButton("수     정");
		updatebutton.setBounds(830, 190, 90, 40);
		panel_1.add(updatebutton);
		updatebutton.setVisible(false);

	}

	private void addEventListener() {
		resetbutton.addActionListener(this);
		choiceButton.addActionListener(this);
		manageButton.addActionListener(this);
		enterbutton.addActionListener(this);
		updatebutton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			Object target = e.getSource();
			if (target == resetbutton) {
				idTF.setText("");
				pTF.setText("");
				puTF.setText("");
				dTF.setText("");
				ISBNTF.setText("");
				comboBox.setSelectedIndex(0);
				spinner.setValue(1);
			} else if (target == manageButton) {
				this.setVisible(false);

			} else if (target == enterbutton) { // 등록

				String regex = "^(18[0-9][0-9]|19[0-9][0-9]|20\\d{2})\\/(0[0-9]|1[0-2])\\/(0[1-9]|[1-2][0-9]|3[0-1])$";
				if (!dTF.getText().matches(regex)) {
					JOptionPane.showMessageDialog(this, "출판일에 올바른 정보를 입력해주십시오.");
					return;
				}

				regex = "\\D";
				if (ISBNTF.getText().matches(regex) || ISBNTF.getText().length() >= 14
						|| ISBNTF.getText().length() <= 12) {
					JOptionPane.showMessageDialog(this, "ISBN에 올바른 정보를 입력해주십시오.");
					return;
				}

				if (idTF.getText().length() == 0 || pTF.getText().length() == 0 || puTF.getText().length() == 0
						|| dTF.getText().length() == 0 || comboBox.getSelectedIndex() == -1
						|| ISBNTF.getText().length() == 0 || spinner.getValue() == null) {
					JOptionPane.showMessageDialog(this, "정보를 입력하세요.");
					return;
				}

				for (int i = 0; i < dm.getRowCount(); i++) {
					if (ISBNTF.getText().equals(dm.getValueAt(i, 6))) {
						JOptionPane.showMessageDialog(this, "이미 등록되었습니다.");
						return;
					}
				}

				Vector<Object> rowData2 = new Vector<Object>();
				rowData2.addElement(false);
				rowData2.addElement(idTF.getText());
				rowData2.addElement(pTF.getText());
				rowData2.addElement(puTF.getText());
				rowData2.addElement(dTF.getText());
				rowData2.addElement(comboBox.getSelectedItem());
				rowData2.addElement(ISBNTF.getText());
				rowData2.addElement(spinner.getValue());
				rowData2.addElement("수정");
				dm.addRow(rowData2);

			} else if (target == choiceButton) {

				int index = 1;
				if (dm.getRowCount() == 0) {
					JOptionPane.showMessageDialog(this, "등록할 도서가 없습니다.");
				} else {
					index = JOptionPane.showConfirmDialog(this, "등록하시겠습니까?", "등록", 2);
				}

				if (index == JOptionPane.OK_OPTION) {
					BookDAO dao = new BookDAO();
					List<Integer> selectList = new ArrayList<Integer>();

					for (int i = 0; i < dm.getRowCount(); i++) {
						if ((Boolean) dm.getValueAt(i, 0) == true) {
							selectList.add(i);
						}
					}
					if (selectList.size() == 0) {
						JOptionPane.showMessageDialog(this, "선택된 도서가 없습니다.");
						return;
					}
					dao.insertBook(rowData);
					JOptionPane.showMessageDialog(this, "등록완료되었습니다.");

					for (int i = selectList.size() - 1; i >= 0; i--) {
						dm.removeRow(selectList.get(i));
					}

					rowData.clear();

					idTF.setText("");
					pTF.setText("");
					puTF.setText("");
					dTF.setText("");
					ISBNTF.setText("");
					comboBox.setSelectedIndex(0);
					spinner.setValue(1);

				} else if (index == JOptionPane.CANCEL_OPTION) {
					JOptionPane.showMessageDialog(this, "취소되었습니다.");

				}

			} else if (target == updatebutton) { // 큰수정 버튼

				String regex = "^(18[0-9][0-9]|19[0-9][0-9]|20\\d{2})\\/(0[0-9]|1[0-2])\\/(0[1-9]|[1-2][0-9]|3[0-1])$";
				if (!dTF.getText().matches(regex)) {
					JOptionPane.showMessageDialog(this, "출판일에 올바른 정보를 입력해주십시오.");
					return;
				}

				if (ISBNTF.getText().equals(regex) || ISBNTF.getText().length() >= 14
						|| ISBNTF.getText().length() <= 12) {
					JOptionPane.showMessageDialog(this, "ISBN에 올바른 정보를 입력해주십시오.");
				} else {

					int index = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정", 2);

					if (index == 0) {
						BookDAO dao = new BookDAO();
						JOptionPane.showMessageDialog(this, "수정완료되었습니다.");

						table.setValueAt(idTF.getText(), table.getSelectedRow(), 1);
						table.setValueAt(pTF.getText(), table.getSelectedRow(), 2);
						table.setValueAt(puTF.getText(), table.getSelectedRow(), 3);
						table.setValueAt(dTF.getText(), table.getSelectedRow(), 4);
						table.setValueAt(comboBox.getSelectedItem(), table.getSelectedRow(), 5);
						table.setValueAt(ISBNTF.getText(), table.getSelectedRow(), 6);
						table.setValueAt(spinner.getValue(), table.getSelectedRow(), 7);

						idTF.setText("");
						pTF.setText("");
						puTF.setText("");
						dTF.setText("");
						ISBNTF.setText("");
						comboBox.setSelectedIndex(0);
						spinner.setValue(1);

						enterbutton.setVisible(true);
						updatebutton.setVisible(false);

						return;
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void init() {
		addComponent();
		addEventListener();

	}

	public RegisterBookPanel() {
		init();
	}
}