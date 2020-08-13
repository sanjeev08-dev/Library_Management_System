import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminForgot implements FocusListener {
    private JLabel lSubmit;
    private JLabel lCancel;
    JFrame fr, pFrame;
    private JTextField tName, tUserName;
    JPanel p;
    private Border border1, border2;
    private Color greenColor;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    AdminForgot(JFrame frame) {
        pFrame = frame;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 385) / 2, (dim.height - 210) / 2, 385, 210);
        fr.setUndecorated(true);

        greenColor = new Color(0, 255, 4);
        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, greenColor);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.YELLOW);

        Border border3 = BorderFactory.createLineBorder(greenColor, 4, true);
        Border border4 = BorderFactory.createLineBorder(Color.YELLOW, 4, true);

        p = new JPanel();
        p.setBounds(0, 0, 385, 210);
        p.setBackground(new Color(56, 50, 0));
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(greenColor, 2, true), "Forgot Password", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), greenColor));
        fr.add(p);

        JLabel lName = new JLabel("Name");
        lName.setBounds(20, 50, 100, 22);
        lName.setForeground(greenColor);
        lName.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lName);

        JLabel lUserName = new JLabel("Username");
        lUserName.setBounds(20, 100, 104, 22);
        lUserName.setForeground(greenColor);
        lUserName.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lUserName);

        tName = new JTextField();
        tName.setBorder(border1);
        tName.setBounds(170, 50, 195, 22);
        tName.setFont(new Font("verdana", Font.PLAIN, 16));
        tName.setOpaque(false);
        tName.setForeground(greenColor);
        tName.setCaretColor(Color.YELLOW);
        tName.addFocusListener(this);
        p.add(tName);

        tUserName = new JTextField();
        tUserName.setBorder(border1);
        tUserName.setBounds(170, 100, 195, 22);
        tUserName.setFont(new Font("verdana", Font.PLAIN, 16));
        tUserName.setOpaque(false);
        tUserName.setForeground(Color.YELLOW);
        tUserName.setCaretColor(Color.YELLOW);
        tUserName.addFocusListener(this);
        p.add(tUserName);

        lSubmit = new JLabel("Submit");
        lSubmit.setBounds(20, 150, 165, 40);
        lSubmit.setFont(new Font("Cambria", Font.BOLD, 25));
        lSubmit.setHorizontalAlignment(JLabel.CENTER);
        lSubmit.setBorder(border3);
        lSubmit.setForeground(greenColor);
        lSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                searchAdmin();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lSubmit.setForeground(Color.YELLOW);
                lSubmit.setBorder(border4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lSubmit.setForeground(greenColor);
                lSubmit.setBorder(border3);
            }
        });
        p.add(lSubmit);

        lCancel = new JLabel("Cancel");
        lCancel.setBounds(200, 150, 165, 40);
        lCancel.setFont(new Font("Cambria", Font.BOLD, 25));
        lCancel.setHorizontalAlignment(JLabel.CENTER);
        lCancel.setBorder(border3);
        lCancel.setForeground(greenColor);
        lCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pFrame.setEnabled(true);
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lCancel.setForeground(Color.YELLOW);
                lCancel.setBorder(border4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lCancel.setForeground(greenColor);
                lCancel.setBorder(border3);
            }
        });
        p.add(lCancel);

        fr.setVisible(true);
    }

    private void searchAdmin() {
        String name = tName.getText().intern();
        String username = tUserName.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name field can't be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
            tName.requestFocus();
        } else if (username.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Username field can't be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
            tUserName.requestFocus();

        } else {
            try {
                con = Dao.getConnection();
                assert con != null;
                ps = con.prepareStatement("select * from admin_login where name=? and admin_username=?");

                ps.setString(1, name);
                ps.setString(2, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    new AdminSetPassword(name, username);
                    tName.setText("");
                    tUserName.setText("");
                    fr.dispose();
                } else {
                    JOptionPane.showMessageDialog(fr, "sorry! Couldn't find any such account");
                }
                con.close();
            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
            }

        }
    }


    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tName) {
            tName.setBorder(border2);
            tName.setForeground(Color.YELLOW);
        } else {
            tUserName.setBorder(border2);
            tUserName.setForeground(Color.YELLOW);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tName) {
            tName.setBorder(border1);
            tName.setForeground(greenColor);
        } else {
            tUserName.setBorder(border1);
            tUserName.setForeground(greenColor);
        }

    }
}
