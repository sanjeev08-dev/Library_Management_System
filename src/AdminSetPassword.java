import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class AdminSetPassword implements FocusListener, KeyListener {
    JFrame fr;
    JPanel p;
    private JLabel lSetPass;
    private JLabel PassStL1;
    private JLabel PassStL2;
    private JLabel checkL;
    private JPasswordField tPass, tCPass;
    private Border border1, border2;
    private Boolean bn1 = false;
    Connection con;
    PreparedStatement ps;

    AdminSetPassword(String name, String username) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 570) / 2, (dim.height - 260) / 2, 570, 260);
        fr.setUndecorated(true);

        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.YELLOW);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(189, 0, 227));

        Border border3 = BorderFactory.createLineBorder(Color.YELLOW, 4, true);
        Border border4 = BorderFactory.createLineBorder(new Color(189, 0, 227), 4, true);

        p = new JPanel();
        p.setBounds(0, 0, 570, 260);
        p.setBackground(new Color(0, 43, 43));
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.YELLOW, 2, true), "Forgot Password", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), Color.YELLOW));
        fr.add(p);

        JLabel lName = new JLabel(name + " please set new Password");
        lName.setBounds(20, 50, 420, 22);
        lName.setForeground(new Color(189, 0, 227));
        lName.setFont(new Font("Cambria", Font.PLAIN, 18));
        p.add(lName);

        JLabel lPass = new JLabel("Enter New Password");
        lPass.setBounds(20, 100, 220, 22);
        lPass.setForeground(Color.YELLOW);
        lPass.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lPass);

        JLabel lCPass = new JLabel("Confirm Password");
        lCPass.setBounds(20, 150, 220, 22);
        lCPass.setForeground(Color.YELLOW);
        lCPass.setFont(new Font("Cambria", Font.BOLD, 22));
        p.add(lCPass);

        tPass = new JPasswordField();
        tPass.setBorder(border1);
        tPass.setBounds(250, 100, 195, 22);
        tPass.setFont(new Font("verdana", Font.PLAIN, 16));
        tPass.setOpaque(false);
        tPass.setForeground(Color.YELLOW);
        tPass.setCaretColor(new Color(189, 0, 227));
        tPass.addFocusListener(this);
        tPass.addKeyListener(this);
        p.add(tPass);

        tCPass = new JPasswordField();
        tCPass.setBorder(border1);
        tCPass.setBounds(250, 150, 195, 22);
        tCPass.setFont(new Font("verdana", Font.PLAIN, 16));
        tCPass.setOpaque(false);
        tCPass.setForeground(Color.YELLOW);
        tCPass.setCaretColor(new Color(189, 0, 227));
        tCPass.addFocusListener(this);
        tCPass.addKeyListener(this);
        p.add(tCPass);

        lSetPass = new JLabel("Set Password");
        lSetPass.setBounds(202, 200, 165, 40);
        lSetPass.setFont(new Font("Cambria", Font.BOLD, 25));
        lSetPass.setHorizontalAlignment(JLabel.CENTER);
        lSetPass.setBorder(border3);
        lSetPass.setForeground(Color.YELLOW);
        lSetPass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setPass(username);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lSetPass.setForeground(new Color(189, 0, 227));
                lSetPass.setBorder(border4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lSetPass.setForeground(Color.YELLOW);
                lSetPass.setBorder(border3);
            }
        });
        p.add(lSetPass);


        PassStL1 = new JLabel();
        PassStL1.setBounds(450, 100, 100, 14);
        PassStL1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        p.add(PassStL1);

        PassStL2 = new JLabel();
        PassStL2.setBounds(450, 119, 100, 3);
        p.add(PassStL2);

        checkL = new JLabel();
        checkL.setBounds(450, 154, 100, 14);
        checkL.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        p.add(checkL);

        fr.setVisible(true);
    }

    private void setPass(String username) {
        String pass = new String(tPass.getPassword());
        String cpass = new String(tCPass.getPassword());
        if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Password");
            tPass.requestFocus();
        } else if (!bn1) {
            JOptionPane.showMessageDialog(fr, "Password strength should be minimum MEDIUM");
            tPass.requestFocus();
        } else if (!pass.equals(cpass)) {
            JOptionPane.showMessageDialog(fr, "Confirm Password should be same as Password");
            tCPass.requestFocus();
        } else {
            try {

                con = Dao.getConnection();
                assert con != null;
                ps = con.prepareStatement("UPDATE admin_login SET admin_password = ? where admin_username = ?");
                ps.setString(1, pass);
                ps.setString(2, username);

                int rs = ps.executeUpdate();
                if (rs > 0) {
                    JOptionPane.showMessageDialog(fr, "Password Updated Successfully");
                    fr.dispose();
                } else {
                    JOptionPane.showMessageDialog(fr, "Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tPass) {
            tPass.setBorder(border2);
            tPass.setForeground(new Color(189, 0, 227));
        } else {
            tCPass.setBorder(border2);
            tCPass.setForeground(new Color(189, 0, 227));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tPass) {
            tPass.setBorder(border1);
            tPass.setForeground(Color.YELLOW);
        } else {
            tCPass.setBorder(border1);
            tCPass.setForeground(Color.YELLOW);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

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
            PassStL2.setIcon(new ImageIcon(new ImageIcon("Images/PassStrength.png").getImage().getScaledInstance(20, 3, Image.SCALE_DEFAULT)));
        } else if (passwordScore <= 4) {
            bn1 = false;
            PassStL1.setForeground(Color.ORANGE);
            PassStL1.setText("Weak");
            PassStL2.setIcon(new ImageIcon(new ImageIcon("Images/PassStrength.png").getImage().getScaledInstance(40, 3, Image.SCALE_DEFAULT)));
        } else if (passwordScore <= 6) {
            bn1 = true;
            PassStL1.setForeground(Color.ORANGE);
            PassStL1.setText("Medium");
            PassStL2.setIcon(new ImageIcon(new ImageIcon("Images/PassStrength.png").getImage().getScaledInstance(60, 3, Image.SCALE_DEFAULT)));
        } else if (passwordScore <= 8) {
            bn1 = true;
            PassStL1.setForeground(Color.GREEN);
            PassStL1.setText("Strong");
            PassStL2.setIcon(new ImageIcon(new ImageIcon("Images/PassStrength.png").getImage().getScaledInstance(80, 3, Image.SCALE_DEFAULT)));
        } else {
            bn1 = true;
            PassStL1.setForeground(Color.GREEN);
            PassStL1.setText("Very Strong");
            PassStL2.setIcon(new ImageIcon(new ImageIcon("Images/PassStrength.png").getImage().getScaledInstance(100, 3, Image.SCALE_DEFAULT)));
        }
        char[] cPass = tCPass.getPassword();
        if (Arrays.equals(pass, cPass)) {
            checkL.setText("Same");
            checkL.setForeground(Color.GREEN);

        } else {
            checkL.setText("Not Same");
            checkL.setForeground(Color.RED);
        }
    }
}
