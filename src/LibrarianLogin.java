import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LibrarianLogin implements FocusListener {
    JFrame fr;
    JPanel p;
    private Color lightYellow;
    private JLabel llogin;
    private JLabel lforget;
    private JLabel close;
    private JTextField tID;
    private JPasswordField tpassword;
    private Border border1, border2;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    LibrarianLogin() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 400) / 2, 700, 400);
        fr.setUndecorated(true);

        lightYellow = new Color(250, 255, 107);
        p = new JPanel();
        p.setBounds(0, 0, 700, 400);
        p.setBackground(Color.BLACK);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightYellow, 2, true), "Librarian Login", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightYellow));

        close = new JLabel(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        close.setBounds(663, 17, 32, 32);
        close.setToolTipText("Close");
        p.add(close);
        close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                close.setIcon(new ImageIcon("Images/exit1.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                close.setIcon(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, lightYellow);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.YELLOW);


        JLabel lID = new JLabel("Librarian ID");
        lID.setBounds(175, 150, 165, 22);
        lID.setFont(new Font("Cambria", Font.BOLD, 22));
        lID.setForeground(lightYellow);
        p.add(lID);

        JLabel lLIB = new JLabel("LIB");
        lLIB.setBounds(350, 150, 27, 22);
        lLIB.setFont(new Font("verdana", Font.PLAIN, 16));
        lLIB.setForeground(Color.YELLOW);
        p.add(lLIB);

        tID = new JTextField();
        tID.setBorder(BorderFactory.createCompoundBorder(border1, BorderFactory.createEmptyBorder(1, 30, 1, 1)));
        tID.setBounds(350, 150, 170, 22);
        tID.setFont(new Font("verdana", Font.PLAIN, 16));
        tID.setOpaque(false);
        tID.setForeground(Color.YELLOW);
        tID.setCaretColor(Color.YELLOW);
        tID.addFocusListener(this);
        tID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                char ch = ke.getKeyChar();
                if (ch < '0' || ch > '9') {
                    ke.consume();
                }

                if (tID.getText().length() > 3) {
                    ke.consume();
                }

            }
        });
        p.add(tID);

        JLabel lpassword = new JLabel("Enter Password");
        lpassword.setBounds(175, 200, 165, 22);
        lpassword.setFont(new Font("Cambria", Font.BOLD, 22));
        lpassword.setForeground(lightYellow);
        p.add(lpassword);

        tpassword = new JPasswordField();
        tpassword.setBorder(border1);
        tpassword.setBounds(350, 200, 170, 22);
        tpassword.setFont(new Font("verdana", Font.PLAIN, 16));
        tpassword.setOpaque(false);
        tpassword.setForeground(Color.YELLOW);
        tpassword.setCaretColor(Color.YELLOW);
        tpassword.addFocusListener(this);
        p.add(tpassword);

        Border border3 = BorderFactory.createLineBorder(lightYellow, 4, true);
        Border border4 = BorderFactory.createLineBorder(Color.YELLOW, 4, true);

        llogin = new JLabel("Login");
        llogin.setBounds(152, 300, 165, 40);
        llogin.setFont(new Font("Cambria", Font.BOLD, 25));
        llogin.setHorizontalAlignment(JLabel.CENTER);
        llogin.setBorder(border3);
        llogin.setForeground(Color.white);
        llogin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                llogin.setBorder(border4);
                llogin.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                llogin.setBorder(border3);
                llogin.setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                loginLibrarian();
            }

        });
        p.add(llogin);

        lforget = new JLabel("Forgot Password");
        lforget.setBounds(327, 300, 220, 40);
        lforget.setFont(new Font("Cambria", Font.BOLD, 25));
        lforget.setHorizontalAlignment(JLabel.CENTER);
        lforget.setBorder(border3);
        lforget.setForeground(Color.white);
        lforget.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lforget.setBorder(border4);
                lforget.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lforget.setBorder(border3);
                lforget.setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminForgot(fr);
            }

        });
        p.add(lforget);


        fr.add(p);
        fr.setVisible(true);

    }

    private void loginLibrarian() {
        String ID = tID.getText().trim();
        String pass = new String(tpassword.getPassword());

        if (ID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter librarian ID", "Error", JOptionPane.ERROR_MESSAGE);
            tID.requestFocus();
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Password", "Error", JOptionPane.ERROR_MESSAGE);
            tpassword.requestFocus();
        } else {
            try {
                con = Dao.getConnection();
                assert con != null;
                ps = con.prepareStatement("select * from librarian where id=? and password=?");
                ps.setString(1, "LIB" + ID);
                ps.setString(2, pass);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String un = rs.getString("name");
                    JOptionPane.showMessageDialog(fr, "Welcome, " + un.toUpperCase());
                    new LibrarianDashboard(un);
                    fr.dispose();
                } else {
                    JOptionPane.showMessageDialog(fr, "sorry! invalid username or password");
                }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tID) {
            tID.setBorder(BorderFactory.createCompoundBorder(border2, BorderFactory.createEmptyBorder(1, 30, 1, 1)));
            tID.setForeground(Color.YELLOW);
        } else {
            tpassword.setBorder(border2);
            tpassword.setForeground(Color.YELLOW);

        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tID) {
            tID.setBorder(BorderFactory.createCompoundBorder(border1, BorderFactory.createEmptyBorder(1, 30, 1, 1)));
            tID.setForeground(lightYellow);
        } else {
            tpassword.setBorder(border1);
            tpassword.setForeground(lightYellow);

        }
    }
}
