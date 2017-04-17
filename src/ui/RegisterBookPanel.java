package ui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
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
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.BookDAO;
import domain.BookVO;
import domain.GenreVO;
import ui.MemberPanel.ButtonEditor;
import ui.MemberPanel.ButtonRenderer;

public class RegisterBookPanel extends JPanel implements ActionListener {
   private JTextField idTF;
   private JTextField pTF;
   private JTextField puTF;
   private JTextField dTF;
   private JTextField ISBNTF;
   private JTable table;
   private JButton updatebutton;
   private JButton button3 = new JButton("\uCD08 \uAE30 \uD654");
   private JSpinner spinner;
   private JComboBox comboBox;
   private JButton btnNewButton_1 = new JButton("\uB3C4\uC11C\uAD00\uB9AC");
   private CardLayout card = new CardLayout();
   private JButton button_4 = new JButton("\uB4F1   \uB85D"), btnNewButton;
   private DefaultTableModel dm;
   private int row = -1;
   private List<BookVO> rowData = new ArrayList<BookVO>();

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

               System.out.println("idTF: " + (String) dm.getValueAt(row, 1));
               
               idTF.setText((String) dm.getValueAt(row, 1));
               puTF.setText((String) dm.getValueAt(row, 3));
               pTF.setText((String) dm.getValueAt(row, 2));
               comboBox.setSelectedItem(dm.getValueAt(row, 5));   // 장르 
               ISBNTF.setText((String) dm.getValueAt(row, 6));      // isbn
               dTF.setText((String) dm.getValueAt(row, 4));         // 출판일
               spinner.setValue(dm.getValueAt(row, 7));            // 수량
               
               button_4.setVisible(false);
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
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
               int row, int column) {
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

      JTextPane textPane = new JTextPane();
      textPane.setBounds(26, 58, 47, 21);
      textPane.setText("\uC81C\uBAA9");
      panel_1.add(textPane);

      JTextPane textPane_1 = new JTextPane();
      textPane_1.setBounds(26, 120, 47, 21);
      textPane_1.setText("\uC800\uC790");
      panel_1.add(textPane_1);

      JTextPane textPane_2 = new JTextPane();
      textPane_2.setBounds(26, 185, 47, 21);
      textPane_2.setText("\uC7A5\uB974");
      panel_1.add(textPane_2);

      comboBox = new JComboBox();
      comboBox.setBounds(73, 185, 120, 21);
      comboBox.setModel(new DefaultComboBoxModel(
            new String[] { "\uC18C\uC124", "\uC2DC/\uC5D0\uC138\uC774", "\uC778\uBB38", "\uAC00\uC815/\uC721\uC544",
                  "\uC694\uB9AC", "\uAC74\uAC15", "\uCDE8\uBBF8/\uC2E4\uC6A9/\uC2A4\uD3EC\uCE20",
                  "\uACBD\uC81C/\uACBD\uC601", "\uC790\uAE30\uACC4\uBC1C", "\uC815\uCE58/\uC0AC\uD68C",
                  "\uC5ED\uC0AC/\uBB38\uD654", "\uC885\uAD50", "\uC608\uC220/\uB300\uC911\uBB38\uD654" }));
      comboBox.setToolTipText("");
      panel_1.add(comboBox);

      JTextPane textPane_3 = new JTextPane();
      textPane_3.setBounds(247, 185, 47, 21);
      textPane_3.setText("\uC218\uB7C9");
      panel_1.add(textPane_3);

      spinner = new JSpinner();
      spinner.setBounds(294, 185, 66, 21);
      spinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
      panel_1.add(spinner);

      JTextPane textPane_4 = new JTextPane();
      textPane_4.setBounds(418, 58, 47, 21);
      textPane_4.setText("\uCD9C\uD310\uC0AC");
      panel_1.add(textPane_4);

      JTextPane textPane_5 = new JTextPane();
      textPane_5.setBounds(418, 120, 47, 21);
      textPane_5.setText("\uCD9C\uD310\uC77C");
      panel_1.add(textPane_5);

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

      /* JButton btnNewButton_1 = new JButton("\uB3C4\uC11C\uAD00\uB9AC"); */
      btnNewButton_1.setBounds(830, 30, 90, 40);
      /*
       * btnNewButton_1.addActionListener(new ActionListener() { public void
       * actionPerformed(ActionEvent e) { } });
       */
      panel_1.add(btnNewButton_1);

      button3.setBounds(830, 110, 90, 40);
      panel_1.add(button3);
      

   
      button_4.setBounds(830, 190, 90, 40);
      panel_1.add(button_4);
      button_4.setVisible(true);
      /* updatebutton.setVisible(false); */

      JScrollPane scrollPane = new JScrollPane();
      scrollPane.setBounds(12, 285, 946, 397);
      panel_1.add(scrollPane);

      table = new JTable();
   /*   table.addMouseListener(new MouseAdapter() {

         @Override
         public void mouseClicked(MouseEvent e) {
            System.out.println("mouse call");
            row = table.getSelectedRow();
         }      
      });
      */
      
      table.setRowHeight(21);
      dm = new DefaultTableModel(new String[] { "no", "제목 ", "\uC800  \uC790", " \uCD9C \uD310 \uC0AC",
            "\uCD9C \uD310 \uC77C", "\uC7A5  \uB974", "    I  S  B  N   ", "\uC218 \uB7C9", "\uAD00 \uB9AC" }, 0);
      table.setModel(dm);
      
      JCheckBox box = new JCheckBox();
      box.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed(ActionEvent e) {
            try {
            row = table.getSelectedRow();
            
            if (box.isSelected()) {
               System.out.println(row);               
               
               String title = (String)dm.getValueAt(row, 1);               
               String writer = (String)dm.getValueAt(row, 2);
               String publisher = (String)dm.getValueAt(row, 3);
               String publishDate = (String)dm.getValueAt(row, 4);
               String genreName = (String)dm.getValueAt(row, 5); 
               int genreId=0;
               String isbn = (String)dm.getValueAt(row, 6);
               int quantity = (Integer)dm.getValueAt(row, 7);
               
               BookDAO dao = new BookDAO();
               List<GenreVO> genres = dao.retrieveGenreList();
               
               for(int i = 0; i<genres.size();i++)
                  if(genres.get(i).getGenre_name().equals(genreName))
                     genreId = genres.get(i).getGenre_id();
               
               BookVO book = new BookVO();
               book.setSubject(title);
               book.setWriter(writer);
               book.setPublisher(publisher);
               book.setPublishDate(publishDate);
               book.setGenre1(genreId);
               book.setIsbn(isbn);
               book.setQuantity(quantity);
               
               rowData.add(book);
            } else {
               if(row != -1) {
                  rowData.remove(row);
               }
            }
         }
         catch(Exception ex) {
            ex.printStackTrace();
         }
         }
      });

      table.getColumn("no").setCellEditor(new DefaultCellEditor(box));
      table.getColumn("no").setCellRenderer(dcr);

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

      btnNewButton = new JButton("\uC120\uD0DD \uB3C4\uC11C \uB4F1\uB85D");
      btnNewButton.setBounds(817, 705, 141, 40);
      panel_1.add(btnNewButton);

      updatebutton = new JButton("\uC218   \uC815");
      updatebutton.setBounds(830, 190, 90, 40);
      panel_1.add(updatebutton);
      updatebutton.setVisible(false);

      add("second", new RetrieveBookPanel());

   }

   private void addEventListener() {
      button3.addActionListener(this);
      btnNewButton.addActionListener(this);
      btnNewButton_1.addActionListener(this);
      button_4.addActionListener(this);
      updatebutton.addActionListener(this);
   }

   public void actionPerformed(ActionEvent e) {
      try {
         Object target = e.getSource();
         if (target == button3) {
            idTF.setText("");
            pTF.setText("");
            puTF.setText("");
            dTF.setText("");
            ISBNTF.setText("");
            comboBox.setSelectedIndex(0);
            spinner.setValue(0);
         } else if (target == btnNewButton_1) {
            card.show(this, "second");

         } else if (target == button_4) {  //등록
            if (idTF.getText().length() == 0 || pTF.getText().length() == 0 || puTF.getText().length() == 0
                  || dTF.getText().length() == 0 || comboBox.getSelectedIndex() == -1
                  || ISBNTF.getText().length() == 0 || spinner.getValue() == null) {
               JOptionPane.showMessageDialog(this, "정보를 입력하세요.");
               return;
            } 
            
            for(int i = 0; i < dm.getRowCount(); i++) {
               if(ISBNTF.getText().equals(dm.getValueAt(i, 6))) {
                  JOptionPane.showMessageDialog(this, "이미 등록되었습니다.");
                  return;
               }
            }
            System.out.println("call");
            
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

         } else if (target == btnNewButton) {
            
               int index = JOptionPane.showConfirmDialog(this,  "등록하시겠습니까?", "등록", 2);
               
               if(index == 0) {
                  BookDAO dao = new BookDAO();
                  dao.insertBook(rowData);
                  JOptionPane.showMessageDialog(this, "등록완료되었습니다.");
                  dm.setRowCount(0);
               }
               
            
            
         } else if(target == updatebutton) {
               button_4.setVisible(true);
               updatebutton.setVisible(false);
               
               // 수정하는거 만들어.
               // 등록하던거를 그냥 여기서만 setValueAt? 하면될껄?
               //int rowIndex = table.getSelectedRow();
               
               table.setValueAt(idTF.getText(), table.getSelectedRow(), 1);
               table.setValueAt(pTF.getText(), table.getSelectedRow(), 2);
               table.setValueAt(puTF.getText(), table.getSelectedRow(), 3);
               table.setValueAt(dTF.getText(), table.getSelectedRow(), 4);
               table.setValueAt(comboBox.getSelectedItem(), table.getSelectedRow(), 5);
               table.setValueAt(ISBNTF.getText(), table.getSelectedRow(), 6);
               table.setValueAt(spinner.getValue(), table.getSelectedRow(), 7);
               
               /*
               idTF.setText((String) dm.getValueAt(row, 1));
               puTF.setText((String) dm.getValueAt(row, 3));
               pTF.setText((String) dm.getValueAt(row, 2));
               comboBox.setSelectedItem(dm.getValueAt(row, 5));   // 장르 
               ISBNTF.setText((String) dm.getValueAt(row, 6));      // isbn
               dTF.setText((String) dm.getValueAt(row, 4));         // 출판일
               spinner.setValue(dm.getValueAt(row, 7));            // 수량
*/         }

      } catch (Exception ex) {
         ex.printStackTrace();
      }

   }

   public void init() {
      addComponent();
      addEventListener();
      // setVisible(true);
   } // init()

   public RegisterBookPanel() {
      init();
   } // RentalHistoryPanel()
}