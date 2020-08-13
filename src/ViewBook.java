import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

class ViewBook {
    JFrame fr, pFrame;
    private Color lightYellow;
    JPanel p;
    JLabel lClose;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;


    ViewBook(JFrame f) throws SQLException {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        pFrame = f;
        fr = new JFrame("Add Student");
        fr.setBounds((dim.width - 900) / 2, (dim.height - 600) / 2, 900, 600);
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setUndecorated(true);

        lightYellow = new Color(232, 228, 0);

        p = new JPanel();
        p.setBounds(0, 0, 900, 600);
        p.setBackground(new Color(77, 77, 77));
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightYellow, 2, true), "All Books", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightYellow));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/wrong.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(860, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pFrame.setEnabled(true);
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lClose.setIcon(new ImageIcon("Images/wrong.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/wrong.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        fr.setVisible(true);
        loadBooks();
    }

    private void loadBooks() throws SQLException {
        con = Dao.getConnection();
        assert con != null;
        ps = con.prepareStatement("select * from book order by BookName");
        rs = ps.executeQuery();

        Vector<String> heading = new Vector<>();
        heading.add("Book ID");
        heading.add("Book Name");
        heading.add("Book Author Name");
        heading.add("Book Publisher Name");
        heading.add("Quantity");

        Vector<Vector<Object>> row = new Vector<>();

        while (rs.next()) {
            Vector<Object> data = new Vector<>();
            data.add(rs.getObject(1));
            data.add(rs.getObject(2));
            data.add(rs.getObject(3));
            data.add(rs.getObject(4));
            data.add(rs.getObject(5));
            row.add(data);
        }

        JTable tab = new JTable(row, heading);
        tab.setBackground(new Color(77, 77, 77));
        tab.setForeground(lightYellow);
        tab.setFont(new Font("Calibri", Font.BOLD, 18));
        tab.setGridColor(Color.cyan);
        tab.setSelectionBackground(lightYellow);
        tab.setSelectionForeground(new Color(77, 77, 77));
        tab.setRowHeight(25);

        TableColumnModel columnModel = tab.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(100);

        JTableHeader th = tab.getTableHeader();
        th.setPreferredSize(new Dimension(100, 40));
        th.setColumnModel(columnModel);
        th.setBackground(new Color(77, 77, 77));
        th.setForeground(Color.CYAN);
        th.setBorder(BorderFactory.createLineBorder(Color.cyan));
        th.setFont(new Font("Calibri", Font.BOLD, 20));
        JScrollPane jsp = new JScrollPane(tab);
        jsp.setBounds((fr.getWidth() - 800) / 2, (fr.getHeight() - 440) / 2, 800, 440);
        jsp.getViewport().setBackground(new Color(77, 77, 77));
        jsp.setBorder(BorderFactory.createEmptyBorder());

        p.add(jsp);

    }

}
