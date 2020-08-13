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

class ViewStudent {
    JFrame fr, pFrame;
    JPanel p;
    JLabel lClose;
    private Color lightGreen;
    private JTable tab;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Dimension dim;

    ViewStudent(JFrame f) throws SQLException {

        pFrame = f;

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr = new JFrame();
        fr.setBounds((dim.width - 800) / 2, (dim.height - 600) / 2, 800, 600);
        fr.setUndecorated(true);

        lightGreen = new Color(50, 168, 82);
        p = new JPanel();
        p.setBackground(Color.BLACK);
        p.setBounds(0, 0, 800, 700);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGreen, 2, true), "View Students", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightGreen));

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit2.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
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
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit2.png").getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit2.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        fr.add(p);
        fr.setVisible(true);
        loadData();
    }

    private void loadData() throws SQLException {
        con = Dao.getConnection();
        assert con != null;
        ps = con.prepareStatement("select * from student order by name");
        rs = ps.executeQuery();

        Vector<String> heading = new Vector<>();
        heading.add("Student ID");
        heading.add("Name");
        heading.add("Father Name");
        heading.add("Roll No.");
        heading.add("Course");
        heading.add("Branch");

        Vector<Vector<Object>> row = new Vector<>();

        while (rs.next()) {
            Vector<Object> data = new Vector<>();
            data.add(rs.getObject(1));
            data.add(rs.getObject(2));
            data.add(rs.getObject(3));
            data.add(rs.getObject(4));
            data.add(rs.getObject(5));
            data.add(rs.getObject(6));
            row.add(data);
        }
        tab = new JTable(row, heading);
        tab.setBackground(Color.black);
        tab.setForeground(lightGreen);
        tab.setGridColor(Color.cyan);
        tab.getSelectionModel().addListSelectionListener(event -> {
            String id = (String) tab.getValueAt(tab.getSelectedRow(), 0);
            fr.setEnabled(false);
            new ViewSelectedStudent(id, fr);
        });
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
        jsp.setBounds((fr.getWidth() - 540) / 2, (fr.getHeight() - 440) / 2, 540, 440);
        jsp.getViewport().setBackground(Color.BLACK);
        jsp.setBorder(BorderFactory.createEmptyBorder());

        p.add(jsp);

    }


}
