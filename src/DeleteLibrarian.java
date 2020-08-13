import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class DeleteLibrarian {
    JFrame fr;
    JFrame pFrame;
    JPanel p;
    private JLabel[] DetailsAL;
    private JLabel DeleteL;
    private JLabel CancelL;

    DeleteLibrarian(JFrame f) throws SQLException {
        pFrame = f;
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame("Delete Librarian");
        Image img = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 555) / 2, (dim.height - 610) / 2, 555, 610);
        fr.setLayout(null);
        fr.setUndecorated(true);

        String id = JOptionPane.showInputDialog(fr, "Enter Librarian ID", "LIB");
        boolean found = checkId(id);
        if (!found) {
            JOptionPane.showMessageDialog(fr, "ID does not exist");
            pFrame.setEnabled(true);
            fr.dispose();

        } else {
            p = new JPanel();
            p.setLayout(null);
            p.setBounds(0, 0, 555, 610);
            p.setBackground(Color.black);
            p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder((new Color(66, 135, 245)), 3, true), "Delete Librarian " + id, 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), (new Color(66, 135, 245))));
            fr.add(p);

            String[] h = {"Librarian ID:", "Name:", "Email ID:", "Contact No:", "Address:"};
            JLabel[] detailsHL = new JLabel[h.length];
            int y = 100;
            for (int i = 0; i < h.length; i++) {
                detailsHL[i] = new JLabel(h[i]);
                detailsHL[i].setBounds(80, y, 185, 30);
                detailsHL[i].setForeground(new Color(66, 135, 245));
                detailsHL[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
                p.add(detailsHL[i]);
                y = y + 60;
            }

            DetailsAL = new JLabel[h.length];
            int z = 100;
            for (int i = 0; i < h.length; i++) {
                DetailsAL[i] = new JLabel();
                DetailsAL[i].setBounds(290, z, 300, 30);
                DetailsAL[i].setForeground(new Color(66, 135, 245));
                DetailsAL[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
                p.add(DetailsAL[i]);
                z = z + 60;
            }

            loaddata(id);

            DeleteL = new JLabel("Delete");
            DeleteL.setBounds(80, 460, 185, 50);
            DeleteL.setForeground(new Color(66, 135, 245));
            DeleteL.setHorizontalAlignment(JLabel.CENTER);
            DeleteL.setBorder(BorderFactory.createLineBorder(new Color(66, 135, 245), 3, true));
            DeleteL.setFont(new Font("Times New Roman", Font.BOLD, 35));
            DeleteL.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    try {
                        deletequary(id);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    DeleteL.setForeground(Color.WHITE);
                    DeleteL.setBorder(BorderFactory.createLineBorder(Color.white, 3, true));

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    DeleteL.setForeground(new Color(66, 135, 245));
                    DeleteL.setBorder(BorderFactory.createLineBorder(new Color(66, 135, 245), 3, true));


                }
            });
            p.add(DeleteL);

            CancelL = new JLabel("Cancel");
            CancelL.setBounds(290, 460, 185, 50);
            CancelL.setForeground(new Color(66, 135, 245));
            CancelL.setHorizontalAlignment(JLabel.CENTER);
            CancelL.setBorder(BorderFactory.createLineBorder(new Color(66, 135, 245), 3, true));
            CancelL.setFont(new Font("Times New Roman", Font.BOLD, 35));
            CancelL.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    pFrame.setEnabled(true);
                    pFrame.requestFocus();
                    fr.dispose();
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    CancelL.setForeground(Color.WHITE);
                    CancelL.setBorder(BorderFactory.createLineBorder(Color.white, 3, true));

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    CancelL.setForeground(new Color(66, 135, 245));
                    CancelL.setBorder(BorderFactory.createLineBorder(new Color(66, 135, 245), 3, true));


                }
            });
            p.add(CancelL);

            fr.setVisible(true);
        }


    }

    private void deletequary(String id) throws SQLException {
        Connection con = Dao.getConnection();
        PreparedStatement ps;
        try {
            assert con != null;
            ps = con.prepareStatement("delete from librarian where id = ?");
            ps.setString(1, id);
            int r = ps.executeUpdate();
            if (r > 0) {
                JOptionPane.showMessageDialog(fr, "Librarian Record delete successfully");
                pFrame.setEnabled(true);
                fr.dispose();
            } else {
                JOptionPane.showMessageDialog(fr, "Librarian Record does not delete");
            }
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loaddata(String id) throws SQLException {
        Connection con = Dao.getConnection();
        PreparedStatement ps;
        try {
            assert con != null;
            ps = con.prepareStatement("select * from librarian where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            rs.next();

            DetailsAL[0].setText(rs.getString(1));
            DetailsAL[1].setText(rs.getString(2));
            DetailsAL[2].setText(rs.getString(3));
            DetailsAL[3].setText(rs.getString(4));
            DetailsAL[4].setText(rs.getString(6));
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private boolean checkId(String id) {
        Connection con = Dao.getConnection();
        PreparedStatement ps;
        try {
            assert con != null;
            ps = con.prepareStatement("select * from librarian where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            //noinspection ThrowablePrintedToSystemOut
            System.out.println(e);
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }


}
