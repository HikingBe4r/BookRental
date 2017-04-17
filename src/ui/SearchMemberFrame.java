/**
 * 
 */
package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.JProgressBar;
import javax.swing.JSeparator;

/**
 * @author kodica0301
 *
 */
public class SearchMemberFrame extends JFrame {

	private JPanel contentPane;
	private JTextField keywordTF;
	private JTable table;

	/**
	 * Launch the application
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchMemberFrame frame = new SearchMemberFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SearchMemberFrame() {
		setTitle("\uD68C\uC6D0\uAC80\uC0C9");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 200, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox keyfieldCB = new JComboBox();
		keyfieldCB
				.setModel(new DefaultComboBoxModel(new String[] { "\uC774\uB984", "\uC804\uD654\uBC88\uD638", "ID" }));
		keyfieldCB.setBounds(12, 10, 74, 21);
		contentPane.add(keyfieldCB);

		keywordTF = new JTextField();
		keywordTF.setBounds(98, 10, 232, 21);
		contentPane.add(keywordTF);
		keywordTF.setColumns(10);

		JButton searchBtn = new JButton("\uAC80\uC0C9");
		searchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		searchBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		searchBtn.setBounds(342, 9, 64, 23);
		contentPane.add(searchBtn);

		JButton acceptBtn = new JButton("\uC120\uD0DD");
		acceptBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		acceptBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		acceptBtn.setBounds(432, 329, 64, 23);
		contentPane.add(acceptBtn);

		JButton cancelBtn = new JButton("\uCDE8\uC18C");
		cancelBtn.setFont(new Font("굴림", Font.PLAIN, 12));
		cancelBtn.setBounds(508, 329, 64, 23);
		contentPane.add(cancelBtn);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 49, 560, 270);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setBounds(0, 0, 499, 160);
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\uC120\uD0DD", "ID", "\uC774\uB984",
				"\uC804\uD654\uBC88\uD638", "\uC0DD\uB144\uC6D4\uC77C" }));
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.getColumnModel().getColumn(2).setPreferredWidth(86);
		table.getColumnModel().getColumn(3).setResizable(false);
		table.getColumnModel().getColumn(3).setPreferredWidth(142);
		table.getColumnModel().getColumn(4).setResizable(false);
		table.getColumnModel().getColumn(4).setPreferredWidth(121);
	}
}
