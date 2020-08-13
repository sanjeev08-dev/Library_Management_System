import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

class ViewIssuedBooks {
    JFrame fr, pFrame;
    Dimension dim;
    private Color goldanColor;
    JPanel p;
    JLabel lClose;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    private JTable tab;

    ViewIssuedBooks(JFrame f) throws SQLException {
        pFrame = f;

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr = new JFrame();
        fr.setBounds((dim.width - 800) / 2, (dim.height - 600) / 2, 800, 600);
        fr.setUndecorated(true);

        goldanColor = new Color(255, 213, 0);
        p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 800, 700);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(goldanColor, 2, true), "Issued Books", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), goldanColor));

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(760, 17, 32, 32);
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

        fr.add(p);
        fr.setVisible(true);
        loadData();
    }

    private void loadData() throws SQLException {
        con = Dao.getConnection();
        assert con != null;
        ps = con.prepareStatement("select * from issued order by BookName");
        rs = ps.executeQuery();

        Vector<String> heading = new Vector<>();
        heading.add("Book Name");
        heading.add("Book ID");
        heading.add("Student ID");
        heading.add("Student Name");
        heading.add("Roll No.");
        heading.add("Issued Date");

        Vector<Vector<Object>> row = new Vector<>();

        while (rs.next()) {
            Vector<Object> data = new Vector<>();
            data.add(rs.getObject(4));
            data.add(rs.getObject(5));
            data.add(rs.getObject(1));
            data.add(rs.getObject(2));
            data.add(rs.getObject(3));
            data.add(rs.getObject(6));
            row.add(data);
        }
        tab = new JTable(row, heading);
        tab.setBackground(Color.black);
        tab.setForeground(goldanColor);
        tab.setGridColor(Color.cyan);
        tab.setSelectionBackground(Color.cyan);
        tab.setSelectionForeground(Color.black);
        tab.setRowHeight(25);
        tab.getSelectionModel().addListSelectionListener(event -> {
            String student_id = (String) tab.getValueAt(tab.getSelectedRow(), 2);
            String student_name = (String) tab.getValueAt(tab.getSelectedRow(), 3);
            String student_rollNo = (String) tab.getValueAt(tab.getSelectedRow(), 4);
            new BookReturnPage(student_id, student_name, student_rollNo, fr);
            pFrame.setEnabled(true);
            fr.dispose();
        });
        JTableHeader th = tab.getTableHeader();
        th.setPreferredSize(new Dimension(100, 40));
        th.setBackground(Color.black);
        th.setForeground(Color.CYAN);
        th.setBorder(BorderFactory.createLineBorder(Color.cyan));
        th.setFont(new Font("Calibri", Font.BOLD, 15));
        JScrollPane jsp = new JScrollPane(tab);
        jsp.setBounds((fr.getWidth() - 540) / 2, (fr.getHeight() - 440) / 2, 540, 440);
        jsp.getViewport().setBackground(Color.BLACK);
        jsp.setBorder(BorderFactory.createEmptyBorder());

        p.add(jsp);

    }

}
