import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AdminSignUp implements FocusListener, KeyListener {

    JFrame fr;
    JPanel p;
    private JLabel lSetPass;
    private JLabel PassStL1;
    private JLabel lCancel;
    private JPasswordField tPass;
    private Border border1, border2;
    private JTextField tName, tUserName;
    private Boolean bn1 = false;
    Connection con;
    PreparedStatement ps;


    AdminSignUp() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 570) / 2, (dim.height - 260) / 2, 570, 260);
        fr.setUndecorated(true);

        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(252, 186, 3));
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(189, 0, 227));

        Border border3 = BorderFactory.createLineBorder(new Color(252, 186, 3), 4, true);
        Border border4 = BorderFactory.createLineBorder(new Color(189, 0, 227), 4, true);

        p = new JPanel();
        p.setBounds(0, 0, 570, 260);
        p.setBackground(new Color(0, 0, 0));
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(252, 186, 3), 2, true), "SignUp", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), new Color(252, 186, 3)));
        fr.add(p);

        JLabel lUserName = new JLabel("Username :");
        lUserName.setBounds(20, 50, 420, 22);
        lUserName.setForeground(new Color(252, 186, 3));
        lUserName.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lUserName);

        JLabel lName = new JLabel("Name :");
        lName.setBounds(20, 100, 220, 22);
        lName.setForeground(new Color(252, 186, 3));
        lName.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lName);

        JLabel lPass = new JLabel("Password :");
        lPass.setBounds(20, 150, 220, 22);
        lPass.setForeground(new Color(252, 186, 3));
        lPass.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lPass);


        tUserName = new JTextField();
        tUserName.setBorder(border1);
        tUserName.setBounds(250, 50, 195, 22);
        tUserName.setFont(new Font("verdana", Font.PLAIN, 16));
        tUserName.setOpaque(false);
        tUserName.setToolTipText("Only small alphabets, numeric and '@' is allowed");
        tUserName.setForeground(new Color(252, 186, 3));
        tUserName.setCaretColor(new Color(189, 0, 227));
        tUserName.addFocusListener(this);
        tUserName.addKeyListener(this);
        p.add(tUserName);

        tName = new JTextField();
        tName.setBorder(border1);
        tName.setBounds(250, 100, 195, 22);
        tName.setFont(new Font("verdana", Font.PLAIN, 16));
        tName.setOpaque(false);
        tName.setForeground(new Color(252, 186, 3));
        tName.setCaretColor(new Color(189, 0, 227));
        tName.addFocusListener(this);
        tName.addKeyListener(this);
        p.add(tName);

        tPass = new JPasswordField();
        tPass.setBorder(border1);
        tPass.setBounds(250, 150, 195, 22);
        tPass.setFont(new Font("verdana", Font.PLAIN, 16));
        tPass.setOpaque(false);
        tPass.setForeground(new Color(252, 186, 3));
        tPass.setCaretColor(new Color(189, 0, 227));
        tPass.addFocusListener(this);
        tPass.addKeyListener(this);
        p.add(tPass);

        lSetPass = new JLabel("Sign Up");
        lSetPass.setBounds(50, 200, 165, 40);
        lSetPass.setFont(new Font("Cambria", Font.BOLD, 25));
        lSetPass.setHorizontalAlignment(JLabel.CENTER);
        lSetPass.setBorder(border3);
        lSetPass.setForeground(new Color(252, 186, 3));
        lSetPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                signup();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lSetPass.setForeground(new Color(189, 0, 227));
                lSetPass.setBorder(border4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lSetPass.setForeground(new Color(252, 186, 3));
                lSetPass.setBorder(border3);
            }
        });
        p.add(lSetPass);

        lCancel = new JLabel("Cancel");
        lCancel.setBounds(250, 200, 165, 40);
        lCancel.setFont(new Font("Cambria", Font.BOLD, 25));
        lCancel.setHorizontalAlignment(JLabel.CENTER);
        lCancel.setBorder(border3);
        lCancel.setForeground(new Color(252, 186, 3));
        lCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lCancel.setForeground(new Color(189, 0, 227));
                lCancel.setBorder(border4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lCancel.setForeground(new Color(252, 186, 3));
                lCancel.setBorder(border3);
            }
        });
        p.add(lCancel);


        PassStL1 = new JLabel();
        PassStL1.setBounds(450, 150, 100, 14);
        PassStL1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        p.add(PassStL1);

        fr.setVisible(true);

    }

    private void signup() {
        String username = tUserName.getText().trim();
        String name = tName.getText().trim();
        String pass = new String(tPass.getPassword());
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Username");
            tUserName.requestFocus();
        } else if (name.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Name");
            tName.requestFocus();
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Password");
            tPass.requestFocus();
        } else if (!bn1) {
            JOptionPane.showMessageDialog(fr, "Password strength should be minimum MEDIUM");
            tPass.requestFocus();
        } else {
            PreparedStatement ps;
            try {
                con = Dao.getConnection();
                assert con != null;
                ps = con.prepareStatement("insert into admin_login values (?,?,?)");
                ps.setString(1, username);
                ps.setString(2, pass);
                ps.setString(3, name);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    JOptionPane.showMessageDialog(fr, "Successfully Signup...Please login.");
                    fr.dispose();

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void focusGained(FocusEvent e) {

        if (e.getSource() == tName || e.getSource() == tUserName) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border2);
            tf.setForeground(new Color(189, 0, 227));
        } else if (e.getSource() == tPass) {
            tPass.setBorder(border2);
            tPass.setForeground(new Color(189, 0, 227));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tName || e.getSource() == tUserName) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border1);
            tf.setForeground(new Color(252, 186, 3));
        } else if (e.getSource() == tPass) {
            tPass.setBorder(border1);
            tPass.setForeground(new Color(252, 186, 3));
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == tName) {
            char ch = e.getKeyChar();
            if (!(ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122 || ch == KeyEvent.VK_SPACE)) {
                e.consume();
            }
        } else if (e.getSource() == tUserName) {
            char ch = e.getKeyChar();
            if (!(ch >= 97 && ch <= 122 || ch == '@' || ch > '0' && ch < '9')) {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        char[] pass = tPass.getPassword();
        int passwordScore = 0;

        if (pass.length >= 10)
            passwordScore += 2;
        else
            passwordScore += 1;

        if (new String(pass).matches("(?=.*[0-9]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[a-z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[A-Z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[~!@#$%^&*()_-]).*"))
            passwordScore += 2;

        if (passwordScore <= 2) {
            bn1 = false;
            PassStL1.setText("Very Weak");
            PassStL1.setForeground(Color.RED);
        } else if (passwordScore <= 4) {
            bn1 = false;
            PassStL1.setForeground(Color.ORANGE);
            PassStL1.setText("Weak");
        } else if (passwordScore <= 6) {
            bn1 = true;
            PassStL1.setForeground(Color.ORANGE);
            PassStL1.setText("Medium");
        } else if (passwordScore <= 8) {
            bn1 = true;
            PassStL1.setForeground(Color.GREEN);
            PassStL1.setText("Strong");
        } else {
            bn1 = true;
            PassStL1.setForeground(Color.GREEN);
            PassStL1.setText("Very Strong");
        }
    }

}
