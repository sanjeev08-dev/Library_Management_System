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

public class AdminLogin implements FocusListener, Runnable {
    JFrame fr;
    private JLabel llogin;
    private JLabel lforget;
    private JLabel lTitle;
    private JLabel lClose;
    private JLabel lSignUp;
    private JTextField tusername;
    private JPasswordField tpassword;
    private Border border1, border2;
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    private boolean flag;

    AdminLogin() {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 400) / 2, 700, 400);
        fr.setUndecorated(true);

        JPanel p = new JPanel();
        p.setBounds(0, 0, 700, 400);
        p.setBackground(Color.black);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.CYAN, 2, true), "Admin Login", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), Color.CYAN));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit3.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(663, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
                fr.dispose();
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

        lTitle = new JLabel("Library Management System");
        lTitle.setForeground(Color.CYAN);
        lTitle.setFont(new Font("Broadway", Font.PLAIN, 30));
        p.add(lTitle);

        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.white);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.cyan);


        JLabel lusername = new JLabel("Enter Admin ID:");
        lusername.setBounds(175, 150, 165, 22);
        lusername.setFont(new Font("Cambria", Font.BOLD, 22));
        lusername.setForeground(Color.white);
        p.add(lusername);

        tusername = new JTextField();
        tusername.setBorder(border1);
        tusername.setBounds(350, 150, 170, 22);
        tusername.setFont(new Font("verdana", Font.PLAIN, 16));
        tusername.setOpaque(false);
        tusername.setForeground(Color.WHITE);
        tusername.setCaretColor(Color.cyan);
        tusername.addFocusListener(this);
        p.add(tusername);

        JLabel lpassword = new JLabel("Enter Password");
        lpassword.setBounds(175, 200, 165, 22);
        lpassword.setFont(new Font("Cambria", Font.BOLD, 22));
        lpassword.setForeground(Color.white);
        p.add(lpassword);

        tpassword = new JPasswordField();
        tpassword.setBorder(border1);
        tpassword.setBounds(350, 200, 170, 22);
        tpassword.setFont(new Font("verdana", Font.PLAIN, 16));
        tpassword.setOpaque(false);
        tpassword.setForeground(Color.WHITE);
        tpassword.setCaretColor(Color.cyan);
        tpassword.addFocusListener(this);
        p.add(tpassword);

        Border border3 = BorderFactory.createLineBorder(Color.white, 4, true);
        Border border4 = BorderFactory.createLineBorder(Color.cyan, 4, true);

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
                llogin.setForeground(Color.cyan);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                llogin.setBorder(border3);
                llogin.setForeground(Color.white);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                login();
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
                lforget.setForeground(Color.cyan);
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

        lSignUp = new JLabel("<html><u>New Signup</u></html>");
        lSignUp.setHorizontalAlignment(JLabel.CENTER);
        lSignUp.setBounds(580, 370, 90, 16);
        lSignUp.setFont(new Font("Cambria", Font.BOLD, 14));
        lSignUp.setForeground(Color.cyan);
        lSignUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminSignUp();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lSignUp.setFont(new Font("Cambria", Font.BOLD, 16));
                lSignUp.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lSignUp.setFont(new Font("Cambria", Font.BOLD, 14));
                lSignUp.setForeground(Color.cyan);
            }
        });
        p.add(lSignUp);


        Thread th = new Thread(this);
        flag = true;
        th.start();
        fr.setVisible(true);
    }

    private void login() {
        String pass = new String(tpassword.getPassword());
        if (tusername.getText().length() == 0) {
            ImageIcon icon = new ImageIcon("images/username.png");
            JOptionPane.showMessageDialog(null, "Username can't be Empty!!", "Error", JOptionPane.INFORMATION_MESSAGE, icon);
            tusername.requestFocus();
        } else if (pass.length() == 0) {
            ImageIcon icon = new ImageIcon("images/password.png");
            JOptionPane.showMessageDialog(null, "Password can't be Empty!!", "Error", JOptionPane.INFORMATION_MESSAGE, icon);
            tpassword.requestFocus();
        } else {
            try {
                con = Dao.getConnection();
                assert con != null;
                ps = con.prepareStatement("select * from admin_login where admin_username=? and admin_password=?");
                ps.setString(1, tusername.getText());
                ps.setString(2, pass);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String un = rs.getString("name");
                    JOptionPane.showMessageDialog(fr, "Welcome, " + un.toUpperCase());
                    new AdminDashboard(un);
                    fr.dispose();
                } else {
                    JOptionPane.showMessageDialog(fr, "sorry! invalid username or password");
                }
                con.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public void focusGained(FocusEvent fe) {
        if (fe.getSource() == tusername) {
            tusername.setBorder(border2);
            tusername.setForeground(Color.cyan);
        } else {
            tpassword.setBorder(border2);
            tpassword.setForeground(Color.cyan);
        }
    }

    public void focusLost(FocusEvent fe) {
        if (fe.getSource() == tusername) {
            tusername.setBorder(border1);
            tusername.setForeground(Color.white);
        } else {
            tpassword.setBorder(border1);
            tpassword.setForeground(Color.white);
        }
    }

    @Override
    public void run() {
        int x = 700;
        while (flag) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (x == -480)
                x = 700;
            else {
                lTitle.setBounds(x, 40, 480, 100);
                x--;
            }
        }
    }
}
