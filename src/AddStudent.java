import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Random;

public class AddStudent implements FocusListener, KeyListener {
    JFrame fr, pFrame;
    private String sID;
    private JLabel lSubmit;
    private JLabel lReset;
    private JLabel lPhoto;
    JLabel lClose;
    private JTextField tID, tName, tFName, tRollNo;
    private JComboBox<String> cCourse;
    private JComboBox<String> cBranch;
    private JComboBox<Integer> cSemester;
    private Border border1, border2;
    JPanel p;
    private String path;
    private Image original, scaled;
    private boolean bn1;

    AddStudent(JFrame f) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        pFrame = f;
        fr = new JFrame("Add Student");
        fr.setBounds((dim.width - 450) / 2, (dim.height - 650) / 2, 450, 650);
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setUndecorated(true);

        Color lightRed = new Color(255, 51, 51);
        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.CYAN);

        p = new JPanel();
        p.setBounds(0, 0, 450, 650);
        p.setBackground(Color.BLACK);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightRed, 2, true), "Add Student", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightRed));
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
        String[] labelString = {"Student ID", "Name", "Father's Name", "Roll No.", "Course", "Branch", "Semester"};
        JLabel[] label = new JLabel[labelString.length];
        for (int i = 0; i < labelString.length; i++) {
            label[i] = new JLabel(labelString[i]);
            label[i].setBounds(30, labelY, 185, 30);
            label[i].setForeground(Color.RED);
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
        tID.setBounds(220, 75, 150, 30);
        tID.setEditable(false);
        tID.setForeground(Color.RED);
        tID.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tID.setOpaque(false);
        tID.setBorder(border1);
        p.add(tID);

        tName = new JTextField();
        tName.setBounds(220, 125, 200, 30);
        tName.setCaretColor(Color.CYAN);
        tName.setForeground(Color.RED);
        tName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tName.setOpaque(false);
        tName.setBorder(border1);
        tName.addKeyListener(this);
        tName.addFocusListener(this);
        p.add(tName);

        tFName = new JTextField();
        tFName.setBounds(220, 175, 200, 30);
        tFName.setCaretColor(Color.CYAN);
        tFName.setForeground(Color.RED);
        tFName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tFName.setOpaque(false);
        tFName.setBorder(border1);
        tFName.addKeyListener(this);
        tFName.addFocusListener(this);
        p.add(tFName);

        tRollNo = new JTextField();
        tRollNo.setBounds(220, 225, 200, 30);
        tRollNo.setCaretColor(Color.CYAN);
        tRollNo.setForeground(Color.RED);
        tRollNo.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tRollNo.setOpaque(false);
        tRollNo.setBorder(border1);
        tRollNo.addKeyListener(this);
        tRollNo.addFocusListener(this);
        p.add(tRollNo);

        cCourse = new JComboBox<>();
        String[] courses = {"Select Course", "B.Tech.", "M.Tech.", "B.C.A.", "M.C.A.", "H.M."};
        for (String cours : courses) {
            cCourse.addItem(cours);
        }
        cCourse.setBounds(220, 275, 200, 30);
        cCourse.setForeground(Color.RED);
        cCourse.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cCourse.setBackground(Color.BLACK);
        cCourse.setBorder(border1);
        cCourse.setOpaque(false);
        cCourse.setMaximumRowCount(3);
        cCourse.addFocusListener(this);
        p.add(cCourse);

        cBranch = new JComboBox<>();
        cBranch.setBounds(220, 325, 200, 30);
        cBranch.setForeground(Color.RED);
        cBranch.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cBranch.setBackground(Color.BLACK);
        cBranch.setOpaque(false);
        cBranch.setBorder(border1);
        cBranch.setMaximumRowCount(3);
        cBranch.addFocusListener(this);
        p.add(cBranch);


        cSemester = new JComboBox<>();
        cSemester.setBounds(220, 375, 200, 30);
        cSemester.setForeground(Color.RED);
        cSemester.setOpaque(false);
        cSemester.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cSemester.setBackground(Color.BLACK);
        cSemester.setBorder(border1);
        cSemester.setMaximumRowCount(3);
        cSemester.addFocusListener(this);
        p.add(cSemester);

        cCourse.addActionListener(e -> {
            String course = Objects.requireNonNull(cCourse.getSelectedItem()).toString();
            if (course.equalsIgnoreCase("B.Tech.") || course.equalsIgnoreCase("M.Tech.")) {
                cBranch.removeAllItems();
                cBranch.addItem("I.T.");
                cBranch.addItem("C.S.E.");
                cBranch.addItem("C.E.");
                cBranch.addItem("M.E.");
                cBranch.addItem("E.E.");
                cSemester.removeAllItems();
                for (int i = 1; i <= 8; i++)
                    cSemester.addItem(i);
            } else if (course.equalsIgnoreCase("H.M.")) {
                cBranch.removeAllItems();
                cBranch.addItem("Hotel Management");
                cSemester.removeAllItems();
                for (int i = 1; i <= 8; i++)
                    cSemester.addItem(i);
            } else if (course.equalsIgnoreCase("B.C.A.") || course.equalsIgnoreCase("M.C.A.")) {
                cBranch.removeAllItems();
                cBranch.addItem("Computer Science");
                cSemester.removeAllItems();
                for (int i = 1; i <= 6; i++)
                    cSemester.addItem(i);
            }

        });

        lPhoto = new JLabel();
        lPhoto.setBounds(30, 425, 160, 200);
        lPhoto.setIcon(new ImageIcon("images/userPic.png"));
        lPhoto.setBorder(BorderFactory.createLineBorder(Color.RED, 5, false));
        lPhoto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lPhoto.setToolTipText("Click to browse photo");
        p.add(lPhoto);

        lPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                FileDialog fd = new FileDialog(fr, "Select Photo", FileDialog.LOAD);
                fd.setVisible(true);
                path = fd.getDirectory() + fd.getFile();
                if (path.equals("nullnull")) {
                    lPhoto.setIcon(new ImageIcon("images/userPic.png"));
                    bn1 = false;
                } else {
                    bn1 = true;
                    original = Toolkit.getDefaultToolkit().getImage(path);
                    scaled = original.getScaledInstance(160, 200, Image.SCALE_DEFAULT);
                    lPhoto.setIcon(new ImageIcon(scaled));
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lPhoto.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5, false));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lPhoto.setBorder(BorderFactory.createLineBorder(Color.RED, 5, false));
            }
        });

        lSubmit = new JLabel("Submit");
        lSubmit.setBounds(220, 500, 200, 50);
        lSubmit.setBorder(BorderFactory.createLineBorder(Color.RED, 5, true));
        lSubmit.setForeground(Color.RED);
        lSubmit.setFont(new Font("Times New Roman", Font.BOLD, 35));
        lSubmit.setHorizontalAlignment(JLabel.CENTER);
        lSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addStudent();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                lSubmit.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5, true));
                lSubmit.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                lSubmit.setBorder(BorderFactory.createLineBorder(Color.RED, 5, true));
                lSubmit.setForeground(Color.RED);
            }
        });
        p.add(lSubmit);

        lReset = new JLabel("Reset");
        lReset.setBounds(220, 575, 200, 50);
        lReset.setBorder(BorderFactory.createLineBorder(Color.RED, 5, true));
        lReset.setFont(new Font("Times New Roman", Font.BOLD, 35));
        lReset.setForeground(Color.RED);
        lReset.setHorizontalAlignment(JLabel.CENTER);
        lReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                resetAllData();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                lReset.setBorder(BorderFactory.createLineBorder(Color.CYAN, 5, true));
                lReset.setForeground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                lReset.setBorder(BorderFactory.createLineBorder(Color.RED, 5, true));
                lReset.setForeground(Color.RED);
            }
        });
        p.add(lReset);

        fr.setVisible(true);
    }

    private void generateRandomNu() {
        Random rand = new Random();
        int n = rand.nextInt(9000) + 1000;
        sID = "SID" + n;
        tID.setText(sID);
    }

    private void resetAllData() {

        tID.setText("");

        tName.setText("");
        tFName.setText("");
        tRollNo.setText("");
        cCourse.setSelectedIndex(0);
        lPhoto.setIcon(new ImageIcon("images/userPic.png"));
        bn1 = false;


    }

    private void addStudent() {
        String name = tName.getText().trim();
        String fName = tFName.getText().trim();
        String RollNo = tRollNo.getText().trim();
        String course = Objects.requireNonNull(cCourse.getSelectedItem()).toString().trim();

        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Name field can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            tName.requestFocus();
        } else if (fName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Father's Name field can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            tFName.requestFocus();
        } else if (RollNo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Roll No field can not be empty", "Error", JOptionPane.ERROR_MESSAGE);
            tRollNo.requestFocus();
        } else if (!bn1) {
            JOptionPane.showMessageDialog(null, "Upload Student pic", "Error", JOptionPane.ERROR_MESSAGE);
            lPhoto.requestFocus();
        } else if (course.equalsIgnoreCase("Select Course")) {
            JOptionPane.showMessageDialog(null, "Select a course", "Error", JOptionPane.ERROR_MESSAGE);
            cCourse.requestFocus();
        } else {
            try {

                Connection con = Dao.getConnection();
                assert con != null;
                PreparedStatement ps = con.prepareStatement("insert into student values (?,?,?,?,?,?,?)");
                ps.setString(1, sID);
                ps.setString(2, name);
                ps.setString(3, fName);
                ps.setString(4, RollNo);
                ps.setString(5, cCourse.getSelectedItem().toString().trim());
                ps.setString(6, Objects.requireNonNull(cBranch.getSelectedItem()).toString().trim());
                ps.setString(7, Objects.requireNonNull(cSemester.getSelectedItem()).toString().trim());
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    saveStudentPic(sID);
                    JOptionPane.showMessageDialog(fr, "Student added Successfully,\nPlease Note Down Student ID: " + sID);
                    tID.setText("");

                    tName.setText("");
                    tFName.setText("");
                    tRollNo.setText("");
                    cCourse.setSelectedIndex(0);
                    lPhoto.setIcon(new ImageIcon("images/userPic.png"));
                    bn1 = false;
                } else {
                    JOptionPane.showMessageDialog(fr, "Unsuccessful", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
            }

        }
    }

    private void saveStudentPic(String sID) {
        BufferedImage image = null;

        // READ IMAGE
        try {
            File input_file = new File(path);
            System.out.println(input_file);
            image = new BufferedImage(160, 200, BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(input_file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        try {
            File dir = new File("Student Images");
            dir.mkdir();
            File output_file = new File(dir,sID + ".jpg");
            ImageIO.write(image, "jpg", output_file);
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == tName || e.getSource() == tFName || e.getSource() == tRollNo) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border2);
            tf.setForeground(Color.cyan);
        } else if (e.getSource() == cCourse || e.getSource() == cBranch || e.getSource() == cSemester) {
            JComboBox c = (JComboBox) e.getSource();
            c.setBorder(border2);
            c.setOpaque(false);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tName || e.getSource() == tFName || e.getSource() == tRollNo) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border1);
            tf.setForeground(Color.red);
        } else if (e.getSource() == cCourse || e.getSource() == cBranch || e.getSource() == cSemester) {
            JComboBox c = (JComboBox) e.getSource();
            c.setBorder(border1);
            c.setOpaque(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == tName) {
            char ch = e.getKeyChar();
            if (!(ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122 || ch == KeyEvent.VK_SPACE)) {
                e.consume();
            }
        } else if (e.getSource() == tFName) {
            char ch = e.getKeyChar();
            if (!(ch >= 65 && ch <= 90 || ch >= 97 && ch <= 122 || ch == KeyEvent.VK_SPACE)) {
                e.consume();
            }
        } else if (e.getSource() == tRollNo) {
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
}
