import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class AddBook implements KeyListener, FocusListener {
    JFrame pFrame, fr;
    private Color lightYellow;
    private Border border1, border2;
    JPanel p;
    private JLabel lClose;
    private JLabel lSave;
    private JLabel lReset;
    private JTextField[] textField;
    private JTextField tID;

    AddBook(JFrame f) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        pFrame = f;
        fr = new JFrame("Add Student");
        fr.setBounds((dim.width - 450) / 2, (dim.height - 600) / 2, 450, 600);
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setUndecorated(true);

        lightYellow = new Color(210, 255, 128);
        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, lightYellow);

        p = new JPanel();
        p.setBounds(0, 0, 450, 600);
        p.setBackground(Color.BLACK);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightYellow, 2, true), "Add Book", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightYellow));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/wrong.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(410, 17, 32, 32);
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

        int labelY = 75;
        String[] labelString = {"Book ID", "Book Name", "Book Auther", "Book Publisher", "Quantity"};
        JLabel[] label = new JLabel[labelString.length];
        for (int i = 0; i < labelString.length; i++) {
            label[i] = new JLabel(labelString[i]);
            label[i].setBounds(30, labelY, 185, 30);
            label[i].setForeground(lightYellow);
            label[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
            p.add(label[i]);

            labelY = labelY + 50;
        }

        JButton button = new JButton(new ImageIcon("Images/seachIcon.png"));
        button.setBounds(375, 70, 35, 35);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        p.add(button);
        button.addActionListener(e -> generateRandomNu());

        tID = new JTextField();
        tID.setBounds(200, 75, 150, 30);
        tID.setEditable(false);
        tID.setForeground(lightYellow);
        tID.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tID.setOpaque(false);
        tID.setBorder(border2);
        p.add(tID);

        int textFieldY = 125;
        textField = new JTextField[labelString.length - 1];
        for (int i = 0; i < labelString.length - 1; i++) {
            textField[i] = new JTextField();
            textField[i].setBounds(200, textFieldY, 185, 30);
            textField[i].setCaretColor(Color.RED);
            textField[i].setForeground(lightYellow);
            textField[i].setFont(new Font("Times New Roman", Font.PLAIN, 18));
            textField[i].setOpaque(false);
            textField[i].setBorder(border2);
            textField[i].addKeyListener(this);
            textField[i].addFocusListener(this);
            p.add(textField[i]);

            textFieldY = textFieldY + 50;
        }

        lSave = new JLabel("Submit Book");
        lSave.setBounds(30, 350, 150, 32);
        lSave.setToolTipText("Click to Save Book Data");
        lSave.setBorder(BorderFactory.createLineBorder(lightYellow, 2, true));
        lSave.setForeground(lightYellow);
        lSave.setHorizontalAlignment(JLabel.CENTER);
        lSave.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                saveBookData();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lSave.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
                lSave.setForeground(Color.RED);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                lSave.setBorder(BorderFactory.createLineBorder(lightYellow, 2, true));
                lSave.setForeground(lightYellow);

            }
        });
        p.add(lSave);

        lReset = new JLabel("Reset");
        lReset.setBounds(200, 350, 150, 32);
        lReset.setToolTipText("Click to Reset Entered Data");
        lReset.setBorder(BorderFactory.createLineBorder(lightYellow, 2, true));
        lReset.setForeground(lightYellow);
        lReset.setHorizontalAlignment(JLabel.CENTER);
        lReset.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tID.setText("");
                textField[0].setText("");
                textField[1].setText("");
                textField[2].setText("");
                textField[3].setText("");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lReset.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
                lReset.setForeground(Color.RED);

            }

            @Override
            public void mouseExited(MouseEvent e) {
                lReset.setBorder(BorderFactory.createLineBorder(lightYellow, 2, true));
                lReset.setForeground(lightYellow);

            }
        });
        p.add(lReset);

        fr.setVisible(true);
    }

    private void saveBookData() {
        String bookID = tID.getText().trim();
        String bookName = textField[0].getText().trim();
        String bookAuthor = textField[1].getText().trim();
        String bookPublisher = textField[2].getText().trim();

        if (bookID.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book ID is mandatory!!", "Error", JOptionPane.ERROR_MESSAGE);
            generateRandomNu();
        } else if (bookName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book Name is mandatory!!", "Error", JOptionPane.ERROR_MESSAGE);
            textField[0].requestFocus();
        } else if (bookAuthor.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book Author is mandatory!!", "Error", JOptionPane.ERROR_MESSAGE);
            textField[1].requestFocus();
        } else if (bookPublisher.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book Publisher is mandatory!!", "Error", JOptionPane.ERROR_MESSAGE);
            textField[2].requestFocus();
        } else if (textField[3].getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Book Quantity is mandatory!!", "Error", JOptionPane.ERROR_MESSAGE);
            textField[3].requestFocus();
        } else {
            int bookQuantity = Integer.parseInt(textField[3].getText().trim());
            if (bookQuantity <= 0) {
                JOptionPane.showMessageDialog(null, "Enter valid book quantity!!", "Error", JOptionPane.ERROR_MESSAGE);
                textField[3].requestFocus();
            } else {
                try {

                    Connection con = Dao.getConnection();
                    assert con != null;
                    PreparedStatement ps = con.prepareStatement("insert into book values (?,?,?,?,?)");
                    ps.setString(1, bookID);
                    ps.setString(2, bookName);
                    ps.setString(3, bookAuthor);
                    ps.setString(4, bookPublisher);
                    ps.setInt(5, bookQuantity);
                    int rs = ps.executeUpdate();
                    if (rs > 0) {
                        JOptionPane.showMessageDialog(fr, "Book added Successfully,\nPlease Note Down Book ID: " + bookID);
                        tID.setText("");
                        textField[0].setText("");
                        textField[1].setText("");
                        textField[2].setText("");
                        textField[3].setText("");
                    } else {
                        JOptionPane.showMessageDialog(fr, "Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (HeadlessException | SQLException e) {
                    System.out.println(e);
                }

            }
        }
    }

    private void generateRandomNu() {
        Random rand = new Random();
        int n = rand.nextInt(9000) + 1000;
        String bID = "BID" + n;
        tID.setText(bID);

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == textField[3]) {
            char ch = e.getKeyChar();
            if (ch < '0' || ch > '9') {
                e.consume();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == textField[0] || e.getSource() == textField[1] || e.getSource() == textField[2] || e.getSource() == textField[3]) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border1);
            tf.setForeground(Color.red);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == textField[0] || e.getSource() == textField[1] || e.getSource() == textField[2] || e.getSource() == textField[3]) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border2);
            tf.setForeground(lightYellow);
        }
    }
}
