import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

class SearchLibrarian {
    JFrame fr, pFrame;
    JPanel p;
    JLabel lClose;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    SearchLibrarian(JFrame f) throws SQLException {
        pFrame = f;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame("Search Librarian");
        fr.setBounds((dim.width - 700) / 2, (dim.height - 600) / 2, 700, 600);
        fr.setUndecorated(true);
        fr.setLayout(null);

        Border border1 = new CompoundBorder(BorderFactory.createLineBorder(new Color(0, 255, 250), 3, true), BorderFactory.createDashedBorder(new Color(0, 255, 250), 3, 3, 2, true));

        p = new JPanel();
        p.setBounds(0, 0, 700, 600);
        p.setLayout(null);
        p.setBackground(Color.black);
        p.setBorder(BorderFactory.createTitledBorder(border1, "All Librarian", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), new Color(0, 255, 250)));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(663, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.dispose();
                pFrame.setEnabled(true);
                pFrame.requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lClose.setIcon(new ImageIcon("Images/exit3.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });


        fr.setVisible(true);
        loaddata();
    }

    private void loaddata() throws SQLException {
        con = Dao.getConnection();
        assert con != null;
        ps = con.prepareStatement("select * from librarian");
        rs = ps.executeQuery();

        Vector<String> heading = new Vector<>();
        heading.add("Librarian ID");
        heading.add("Name");
        heading.add("Email ID");
        heading.add("Contact Number");
        heading.add("Address");

        Vector<Vector<Object>> row = new Vector<>();

        while (rs.next()) {
            Vector<Object> data = new Vector<>();
            data.add(rs.getObject(1));
            data.add(rs.getObject(2));
            data.add(rs.getObject(3));
            data.add(rs.getObject(4));
            data.add(rs.getObject(6));
            row.add(data);
        }
        JTable tab = new JTable(row, heading);
        tab.setBackground(Color.black);
        tab.setForeground(Color.CYAN);
        tab.setGridColor(Color.cyan);
        tab.setSelectionBackground(Color.cyan);
        tab.setSelectionForeground(Color.black);
        tab.setRowHeight(25);
        JTableHeader th = tab.getTableHeader();
        th.setPreferredSize(new Dimension(100, 40));
        th.setBackground(Color.black);
        th.setForeground(Color.CYAN);
        th.setBorder(BorderFactory.createLineBorder(Color.cyan));
        th.setFont(new Font("Calibri", Font.BOLD, 15));
        JScrollPane jsp = new JScrollPane(tab);
        jsp.setBounds(80, 100, 540, 440);
        jsp.getViewport().setBackground(Color.BLACK);
        jsp.setBorder(BorderFactory.createEmptyBorder());

        p.add(jsp);
    }

}

