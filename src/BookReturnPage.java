import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

class BookReturnPage {
    JFrame fr, pFrame;
    Dimension dim;
    private JPanel pTab;
    private JLabel lClose;
    private JLabel lBid;
    private String BookID;
    private String Student_ID;
    Connection con;
    ResultSet rs;
    private JTable tab;
    private JButton returnButton;
    private int selectedRowIndex;

    BookReturnPage(String student_id, String student_name, String student_rollNo, JFrame f) {
        pFrame = f;

        Student_ID = student_id;

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr = new JFrame();
        fr.setBounds((dim.width - 1000) / 2, (dim.height - 600) / 2, 1000, 600);
        fr.setUndecorated(true);

        JPanel p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 1000, 600);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, true), "Return Book", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), Color.YELLOW));

        pTab = new JPanel();
        pTab.setBackground(Color.BLACK);
        pTab.setBounds(2, 70, 996, 470);
        pTab.setLayout(null);
        pTab.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.YELLOW));
        p.add(pTab);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(960, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pFrame.setEnabled(true);
                pFrame.requestFocus();
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        JLabel lSid = new JLabel("Student ID : " + Student_ID);
        lSid.setBounds(20, 25, 200, 32);
        lSid.setFont(new Font("Arial", Font.BOLD, 20));
        lSid.setForeground(Color.YELLOW);
        p.add(lSid);

        JLabel lSName = new JLabel("Student Name : " + student_name);
        lSName.setBounds(296, 25, 300, 32);
        lSName.setFont(new Font("Arial", Font.BOLD, 20));
        lSName.setForeground(Color.YELLOW);
        p.add(lSName);

        JLabel lSRoll = new JLabel("Student Roll No. : " + student_rollNo);
        lSRoll.setBounds(652, 25, 300, 32);
        lSRoll.setFont(new Font("Arial", Font.BOLD, 20));
        lSRoll.setForeground(Color.YELLOW);
        p.add(lSRoll);

        lBid = new JLabel("Book ID : " + BookID);
        lBid.setBounds(20, 555, 300, 32);
        lBid.setFont(new Font("Arial", Font.BOLD, 20));
        lBid.setForeground(Color.YELLOW);
        p.add(lBid);

        returnButton = new JButton("Return Book");
        returnButton.setBounds(735, 549, 250, 40);
        returnButton.setFont(new Font("Arial", Font.BOLD, 20));
        returnButton.setForeground(Color.YELLOW);
        returnButton.setBackground(Color.GRAY);
        returnButton.setEnabled(false);
        returnButton.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, false));
        p.add(returnButton);
        returnButton.addActionListener(e -> returnBook());


        fr.add(p);
        fr.setVisible(true);

        loadStudentIssuedData();

    }

    private void returnBook() {
        System.out.println(BookID);
        System.out.println(Student_ID);
        try {
            con = Dao.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("delete from issued where BookID = ? and StudentID = ?");
            ps.setString(1, BookID);
            ps.setString(2, Student_ID);
            int r = ps.executeUpdate();
            if (r > 0) {
                JOptionPane.showMessageDialog(null, "Book Return Successfully");
                DefaultTableModel model = (DefaultTableModel) tab.getModel();
                model.removeRow(selectedRowIndex);

                PreparedStatement getBookDetailsPS = con.prepareStatement("select * from book where Bid = ?");
                getBookDetailsPS.setString(1, BookID);
                ResultSet getBookRS = getBookDetailsPS.executeQuery();
                if (getBookRS.next()) {
                    int bookQuantity = getBookRS.getInt(5);
                    bookQuantity = bookQuantity + 1;

                    PreparedStatement updateBookTableps = con.prepareStatement("UPDATE book SET BookQuantity = ? WHERE Bid = ?");
                    updateBookTableps.setInt(1, bookQuantity);
                    updateBookTableps.setString(2, BookID);
                    int updateQuantityRS = updateBookTableps.executeUpdate();
                    if (updateQuantityRS > 0) {
                        System.out.println("Success");
                    } else {
                        System.out.println("Failed");
                    }

                }


            } else {
                System.out.println("Failed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadStudentIssuedData() {
        try {
            con = Dao.getConnection();
            assert con != null;
            PreparedStatement ps = con.prepareStatement("select * from issued where  StudentID = ? order by BookName");
            ps.setString(1, Student_ID);
            rs = ps.executeQuery();

            Vector<String> heading = new Vector<>();
            heading.add("Book Name");
            heading.add("Book ID");
            heading.add("Issued Date");

            Vector<Vector<Object>> row = new Vector<>();

            while (rs.next()) {
                Vector<Object> data = new Vector<>();
                data.add(rs.getObject(5));
                data.add(rs.getObject(4));
                data.add(rs.getObject(6));
                row.add(data);
            }


            tab = new JTable(row, heading);
            tab.setBackground(Color.black);
            tab.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tab.setForeground(Color.YELLOW);
            tab.setFont(new Font("Calibri", Font.BOLD, 15));
            tab.setGridColor(Color.YELLOW);
            tab.setSelectionBackground(Color.YELLOW);
            tab.setSelectionForeground(Color.black);
            tab.setRowHeight(30);
            tab.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selectedRowIndex = tab.getSelectedRow();
                    BookID = (String) tab.getModel().getValueAt(selectedRowIndex, 1);
                    lBid.setText("Book ID : " + BookID);
                    returnButton.setEnabled(true);
                }
            });
            JTableHeader th = tab.getTableHeader();
            th.setPreferredSize(new Dimension(100, 40));
            th.setBackground(Color.BLACK);
            th.setForeground(Color.YELLOW);
            th.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
            th.setFont(new Font("Arial", Font.BOLD, 25));
            JScrollPane jsp = new JScrollPane(tab);
            jsp.setBounds(50, 50, 896, 370);
            jsp.getViewport().setBackground(Color.BLACK);
            jsp.setBorder(BorderFactory.createEmptyBorder());

            pTab.add(jsp);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }


    }
}
