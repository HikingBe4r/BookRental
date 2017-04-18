package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.RentalDAO;

// 대여반납기록 조회 패널
public class RentalHistoryPanel extends JPanel implements ActionListener {
	private JCheckBox checkRental, checkReturn, checkRenewal;
	private JComboBox<String> combo;
	private JButton retrieveBtn;
	private JTable rentingTable;
	private DefaultTableModel dm;
	private JTextField conditionTF, startDateTF, endDateTF;
	private Vector<String> condition = new Vector<String>();
	private String keyword, startDate, endDate;
	boolean[] pattern = new boolean[4];	// String 배열에서 boolean 배열로 바꿈
										// [0] : 도서명 or 회원명, [1]:대여, [2]:반납, [3]:연장
	
	
	
	private JTable createTable() {
		try {
			String[] columnNames = {"대여일련번호", "도서ID", "도서명", "회원ID", "회원명", "연락처", "구분", 
					"대여일", "반납일", "반납예정일"};
			dm = new DefaultTableModel(columnNames, 0) {

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}			
			};
			
			RentalDAO dao = new RentalDAO();
			keyword = conditionTF.getText();		
			startDate = startDateTF.getText();
			if(startDate.length() != 10 || startDate.charAt(4) != '/' || startDate.charAt(7) != '/') {
				// 날짜 포맷 맞는지 확인
				JOptionPane.showMessageDialog(this, "YYYY/MM/DD 형태로 날짜를 입력하시오.");
				return null;
			}
			endDate = endDateTF.getText();
			if(endDate.length() != 10 || endDate.charAt(4) != '/' || endDate.charAt(7) != '/') {
				JOptionPane.showMessageDialog(this, "YYYY/MM/DD 형태로 날짜를 입력하시오.");
				return null;
			}
					
			Vector<Vector<Object>> rowData = dao.selectRentalHistoryList(keyword, pattern, startDate, endDate);
			for(int i=0; i<rowData.size(); i++) {
				dm.addRow(rowData.elementAt(i));
			}
			
			return new JTable(dm);
					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
			
	} // createTable()
	
	
	
	private void addComponent() {
		this.setLayout(new BorderLayout());
		
		// 패널 생성
		JPanel northPanel = new JPanel();
		add(northPanel, BorderLayout.NORTH);
		
		// 콤보박스
		combo = new JComboBox<String>(condition);
		northPanel.add(combo);
		condition.addElement("도서명");
		condition.addElement("회원명");
		combo.setSelectedIndex(0); // 디폴트 : 도서명
		pattern[0] = true; // 도서명 선택
		
		// 텍스트 필드
		conditionTF = new JTextField(30);
		northPanel.add(conditionTF);
		
		// 체크박스
		checkRental =  new JCheckBox("대여");
		checkReturn =  new JCheckBox("반납");
		checkRenewal =  new JCheckBox("연장");
		checkRental.setSelected(true);
		pattern[1] = true;
		checkReturn.setSelected(true);
		pattern[2] = true;
		checkRenewal.setSelected(true);
		pattern[3] = true;
		northPanel.add(checkRental);
		northPanel.add(checkReturn);
		northPanel.add(checkRenewal);
		
		// 날짜
		Calendar calendar = new GregorianCalendar();
		String date = String.format("%1$tY/%1$tm/%1$td", calendar);
		startDateTF = new JTextField(7);
		endDateTF = new JTextField(7);
		endDateTF.setText(date);
		calendar.set(2017, 0, 01);
		date = String.format("%1$tY/%1$tm/%1$td", calendar);
		startDateTF.setText(date);
		northPanel.add(startDateTF);
		northPanel.add(new JLabel("~"));
		northPanel.add(endDateTF);
		
		// 버튼
		retrieveBtn = new JButton("조회");
		northPanel.add(retrieveBtn);
		
		// 대여/반납/연장 기록
		this.rentingTable = createTable();
		this.rentingTable.setRowHeight(20);
		JScrollPane pane = new JScrollPane(rentingTable);
				
		add(pane, BorderLayout.CENTER);
	} // addComponent()
	
	
	private void addEventListener() {
		combo.addActionListener(this);
		checkRental.addActionListener(this);
		checkReturn.addActionListener(this);
		checkRenewal.addActionListener(this);
		retrieveBtn.addActionListener(this);
		
	} // addEventListener()
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object target = e.getSource();
		if(target == combo) {
			if(combo.getSelectedIndex() == 0) pattern[0] = true;
			else pattern[0] = false;
		} else if (target == checkRental) {
			if(checkRental.isSelected()) pattern[1] = true;
			else pattern[1] = false;		
		} else if (target == checkReturn) {
			if(checkReturn.isSelected()) pattern[2] = true;
			else pattern[2] = false;		
		} else if (target == checkRenewal) {
			if(checkRenewal.isSelected()) pattern[3] = true;
			else pattern[3] = false;		
		} else if (target == retrieveBtn) { 
			for(int i=dm.getRowCount()-1; i>=0; i--) {
				dm.removeRow(i);
			}
			RentalDAO dao = new RentalDAO();
			keyword = conditionTF.getText();		
			startDate = startDateTF.getText();
			if(startDate.length() != 10 || startDate.charAt(4) != '/' || startDate.charAt(7) != '/') {
				// 날짜 포맷 맞는지 확인
				JOptionPane.showMessageDialog(this, "YYYY/MM/DD 형태로 날짜를 입력하시오.");
				return;
			}
			endDate = endDateTF.getText();
			if(endDate.length() != 10 || endDate.charAt(4) != '/' || endDate.charAt(7) != '/') {
				JOptionPane.showMessageDialog(this, "YYYY/MM/DD 형태로 날짜를 입력하시오.");
				return;
			}
			try {
				Vector<Vector<Object>> rowData = dao.selectRentalHistoryList(keyword, pattern, startDate, endDate);
				for(int i=0; i<rowData.size(); i++) {
					dm.addRow(rowData.elementAt(i));
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}	
		}
		
		
	} // acctionPerformed()

	private void init() {
		this.setBackground(Color.WHITE);
		addComponent();
		addEventListener();
	} // init()
	
	public RentalHistoryPanel() {
		init();
	} // RentalHistoryPanel()

}
