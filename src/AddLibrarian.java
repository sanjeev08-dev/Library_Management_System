import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddLibrarian implements FocusListener, KeyListener {
    JFrame pFrame, fr;
    JPanel p;
    private JLabel PassStL1;
    private JLabel PassStL2;
    private JLabel EmailCheckL;
    private JLabel checkL;
    private JLabel SubmitL;
    private JLabel CancelL;
    private JLabel LibID;
    private JTextField[] TextField;
    private JPasswordField PassT, CPassT;
    private JTextArea AddressT;
    private Border border2, border3;
    private Boolean bn1 = false;
    private String id;

    AddLibrarian(JFrame f) {
        pFrame = f;

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame("Add Librarian");
        Image img = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 700) / 2, 700, 700);
        fr.setLayout(null);
        fr.setUndecorated(true);

        Border border1 = new CompoundBorder(BorderFactory.createLineBorder(Color.RED, 3, true), BorderFactory.createDashedBorder(Color.ORANGE, 3, 3, 2, true));

        p = new JPanel();
        p.setBounds(0, 0, 700, 700);
        p.setBackground(Color.black);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(border1, "Add Librarian", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), Color.RED));
        fr.add(p);

        Random rand = new Random();
        int n = rand.nextInt(9000) + 1000;

        id = "LIB" + n;

        LibID = new JLabel(id);
        LibID.setBounds(290, 105, 100, 20);
        LibID.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        LibID.setForeground(Color.RED);
        p.add(LibID);

        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED);
        border3 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.ORANGE);

        String[] detailsS = {"Librarian ID:", "Name:", "Email ID:", "Contact No:", "Password:", "Confirm Password:", "Address:"};
        JLabel[] detailsL = new JLabel[detailsS.length];
        int y = 100;
        for (int i = 0; i < detailsS.length; i++) {
            detailsL[i] = new JLabel(detailsS[i]);
            detailsL[i].setBounds(80, y, 185, 30);
            detailsL[i].setForeground(Color.RED);
            detailsL[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
            p.add(detailsL[i]);
            y = y + 60;
        }

        TextField = new JTextField[3];
        int z = 160;
        for (int i = 0; i < 3; i++) {
            TextField[i] = new JTextField();
            TextField[i].setBounds(290, z, 200, 30);
            TextField[i].setCaretColor(Color.ORANGE);
            TextField[i].setForeground(Color.RED);
            TextField[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            TextField[i].setOpaque(false);
            TextField[i].setBorder(border2);
            TextField[i].addFocusListener(this);
            p.add(TextField[i]);
            z = z + 60;
        }

        TextField[0].addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                {
                    char ch = e.getKeyChar();
                    if (!(ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122 || ch == KeyEvent.VK_SPACE)) {
                        e.consume();
                    }
                }

            }
        });

        TextField[2].addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();
                if (ch < '0' || ch > '9') {
                    e.consume();
                }

                if (TextField[2].getText().length() > 9) {
                    e.consume();
                }
            }
        });

        PassT = new JPasswordField();
        PassT.setBounds(290, 340, 200, 30);
        PassT.setCaretColor(Color.ORANGE);
        PassT.setForeground(Color.RED);
        PassT.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        PassT.setOpaque(false);
        PassT.setBorder(border2);
        PassT.addFocusListener(this);
        PassT.addKeyListener(this);
        p.add(PassT);

        CPassT = new JPasswordField();
        CPassT.setBounds(290, 400, 200, 30);
        CPassT.setCaretColor(Color.ORANGE);
        CPassT.setForeground(Color.RED);
        CPassT.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        CPassT.setOpaque(false);
        CPassT.setBorder(border2);
        CPassT.addFocusListener(this);
        CPassT.addKeyListener(this);
        p.add(CPassT);

        AddressT = new JTextArea();
        AddressT.setBounds(290, 460, 200, 67);
        AddressT.setCaretColor(Color.ORANGE);
        AddressT.setForeground(Color.RED);
        AddressT.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        AddressT.setOpaque(false);
        AddressT.setLineWrap(true);
        AddressT.setWrapStyleWord(true);
        AddressT.setBorder(border2);
        AddressT.addFocusListener(this);
        p.add(AddressT);

        EmailCheckL = new JLabel();
        EmailCheckL.setBounds(500, 220, 24, 24);
        p.add(EmailCheckL);


        PassStL1 = new JLabel();
        PassStL1.setBounds(500, 344, 100, 14);
        PassStL1.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        p.add(PassStL1);

        PassStL2 = new JLabel();
        PassStL2.setBounds(500, 363, 100, 3);
        p.add(PassStL2);

        checkL = new JLabel();
        checkL.setBounds(500, 404, 100, 14);
        checkL.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        p.add(checkL);

        SubmitL = new JLabel("Submit");
        SubmitL.setBounds(80, 600, 150, 40);
        SubmitL.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        SubmitL.setForeground(Color.RED);
        SubmitL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        SubmitL.setHorizontalAlignment(JLabel.CENTER);
        p.add(SubmitL);
        SubmitL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Submit();

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                SubmitL.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4, true));
                SubmitL.setForeground(Color.ORANGE);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                SubmitL.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
                SubmitL.setForeground(Color.RED);

            }
        });

        CancelL = new JLabel("Cancel");
        CancelL.setBounds(340, 600, 150, 40);
        CancelL.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
        CancelL.setForeground(Color.RED);
        CancelL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        CancelL.setHorizontalAlignment(JLabel.CENTER);
        p.add(CancelL);
        CancelL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pFrame.setEnabled(true);
                pFrame.requestFocus();
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                CancelL.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 4, true));
                CancelL.setForeground(Color.ORANGE);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                CancelL.setBorder(BorderFactory.createLineBorder(Color.RED, 4, true));
                CancelL.setForeground(Color.RED);

            }
        });

        fr.setVisible(true);

    }

    private void Submit() {
        String pass = new String(PassT.getPassword());
        String cpass = new String(CPassT.getPassword());
        if (TextField[0].getText().length() == 0) {
            JOptionPane.showMessageDialog(fr, "Enter Librarian Name");
            TextField[0].requestFocus();
        } else if (TextField[1].getText().length() == 0) {
            JOptionPane.showMessageDialog(fr, "Enter Email ID");
            TextField[1].requestFocus();
        } else if (!ValidateEmail()) {
            JOptionPane.showMessageDialog(fr, "Enter Valid Email ID");
            TextField[1].requestFocus();
        } else if (TextField[2].getText().length() == 0) {
            JOptionPane.showMessageDialog(fr, "Enter Contact Number");
            TextField[2].requestFocus();
        } else if (TextField[2].getText().length() < 10) {
            JOptionPane.showMessageDialog(fr, "Contact number should have 10 digits");
            TextField[2].requestFocus();
        } else if (pass.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Password");
            PassT.requestFocus();
        } else if (!bn1) {
            JOptionPane.showMessageDialog(fr, "Password strength should be minimum MEDIUM");
            PassT.requestFocus();
        } else if (!pass.equals(cpass)) {
            JOptionPane.showMessageDialog(fr, "Confirm Password should be same as Password");
            CPassT.requestFocus();
        } else if (AddressT.getText().isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Please Enter the Address");
            AddressT.requestFocus();
        } else {
            try {

                Connection con = Dao.getConnection();
                assert con != null;
                PreparedStatement ps = con.prepareStatement("insert into librarian values (?,?,?,?,?,?)");
                ps.setString(1, id);
                ps.setString(2, TextField[0].getText());
                ps.setString(3, TextField[1].getText());
                ps.setString(4, TextField[2].getText());
                ps.setString(5, pass);
                ps.setString(6, AddressT.getText());
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    JOptionPane.showMessageDialog(fr, "Librarian added Successfully,\nPlease Note Down Librarian ID: " + id);

                    LibID.setText("");
                    TextField[0].setText("");
                    TextField[1].setText("");
                    TextField[2].setText("");
                    AddressT.setText("");
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
        if (e.getSource() == AddressT) {
            AddressT.setBorder(border3);
            AddressT.setForeground(Color.ORANGE);
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border3);
            tf.setForeground(Color.ORANGE);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == AddressT) {
            AddressT.setBorder(border2);
            AddressT.setForeground(Color.RED);
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border2);
            tf.setForeground(Color.RED);

            if (tf == TextField[1]) {
                ValidateEmail();
            }
        }
    }

    private Boolean ValidateEmail() {
        String regex = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        String email = TextField[1].getText();

        Pattern ptr = Pattern.compile(regex);
        Matcher mth = ptr.matcher(email);
        if (mth.matches()) {
            EmailCheckL.setIcon(new ImageIcon("Images/right.png"));
            EmailCheckL.setToolTipText("Correct email address");
            EmailCheckL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return true;

        } else {
            EmailCheckL.setIcon(new ImageIcon("Images/wrong.png"));
            EmailCheckL.setToolTipText("<html>" + "Please Enter a valid Email Address" + "<br>" + "eg. - xyz123@gmail.com" + "</html>");
            EmailCheckL.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            return false;
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
        char[] pass = PassT.getPassword();
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
        char[] cPass = CPassT.getPassword();
        if (Arrays.equals(pass, cPass)) {
            checkL.setText("Same");
            checkL.setForeground(Color.GREEN);

        } else {
            checkL.setText("Not Same");
            checkL.setForeground(Color.RED);
        }
    }
}
