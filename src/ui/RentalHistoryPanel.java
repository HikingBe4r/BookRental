package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

// 대여반납기록 조회 패널
public class RentalHistoryPanel extends JPanel implements ActionListener {
	private JCheckBox checkRental, checkReturn, checkRenewal;
	private JComboBox<String> combo;
	private JButton selectBtn;
	private JTable rentingTable;
	private DefaultTableModel dm;
	private JTextField conditionTF, startDateTF, endDateTF;
	private Vector<String> condition = new Vector<String>();
	
	private JTable createTable() {
		try {
			String[] columnNames = {"도서ID", "도서명", "저자", "출판사", "장르", "반납예정일", "연장여부"};
			dm = new DefaultTableModel(columnNames, 0) {

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}			
			};
			/*RentalDAO dao = new RentalDAO();
			Vector<Vector<Object>> rowData = dao.selectRentingBooksByMember(memberId);
			for(int i=0; i<rowData.size(); i++) {
				dm.addRow(rowData.elementAt(i));
			}*/
			
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
		
		// 텍스트 필드
		conditionTF = new JTextField(20);
		northPanel.add(conditionTF);
		
		// 체크박스
		checkRental =  new JCheckBox("대여");
		checkReturn =  new JCheckBox("반납");
		checkRenewal =  new JCheckBox("연장");
		northPanel.add(checkRental);
		northPanel.add(checkReturn);
		northPanel.add(checkRenewal);
		
		// 날짜
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");	
		startDateTF = new JTextField(7);
		endDateTF = new JTextField(7);
		endDateTF.setText(sdf.format(d));
		northPanel.add(startDateTF);
		northPanel.add(new JLabel("~"));
		northPanel.add(endDateTF);
		
		// 버튼
		selectBtn = new JButton("조회");
		northPanel.add(selectBtn);
		
		// 대여현황 리스트
		this.rentingTable = createTable();
		this.rentingTable.setRowHeight(20);
		JScrollPane pane = new JScrollPane(rentingTable);
				
		add(pane, BorderLayout.CENTER);
	}
	
	
	private void addEventListener() {
		
	}
		
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		
	}

	public void init() {
		this.setBackground(Color.WHITE);
		addComponent();
		addEventListener();
	}
	
	public RentalHistoryPanel() {
		init();
	}

}
