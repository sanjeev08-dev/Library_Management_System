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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ViewSelectedStudent implements KeyListener, FocusListener {
    JFrame fr, pFrame;
    JPanel p;
    private JLabel lClose;
    private JLabel lPhoto;
    private JLabel lUpdate;
    private JTextField tName, tFName;
    private JComboBox<String> cCourse;
    private JComboBox<String> cBranch;
    private JComboBox<Integer> cSemester;
    private Border border1, border2;
    private String path;
    Connection con;
    private Color lightGreen;
    private Image original, scaled;
    private boolean isPhotoLoad = false;

    ViewSelectedStudent(String id, JFrame f) {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        con = Dao.getConnection();

        pFrame = f;

        fr = new JFrame();
        fr.setBounds((dim.width - 450) / 2, (dim.height - 600) / 2, 450, 600);
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setUndecorated(true);
        lightGreen = new Color(161, 252, 3);

        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED);
        border2 = BorderFactory.createMatteBorder(0, 0, 1, 1, lightGreen);

        p = new JPanel();
        p.setBounds(0, 0, 450, 600);
        p.setBackground(Color.BLACK);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightGreen, 2, true), "Student", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightGreen));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/wrong.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(410, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pFrame.setEnabled(true);
                pFrame.requestFocus();
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
        String[] labelString = {"Student ID", "Name", "Father's Name", "Course", "Branch", "Semester"};
        JLabel[] label = new JLabel[labelString.length];
        for (int i = 0; i < labelString.length; i++) {
            label[i] = new JLabel(labelString[i]);
            label[i].setBounds(30, labelY, 185, 30);
            label[i].setForeground(Color.RED);
            label[i].setFont(new Font("Times New Roman", Font.BOLD, 18));
            p.add(label[i]);

            labelY = labelY + 50;
        }

        JLabel lID = new JLabel(id);
        lID.setBounds(230, 75, 185, 30);
        lID.setForeground(Color.RED);
        lID.setFont(new Font("Times New Roman", Font.PLAIN, 24));
        p.add(lID);

        tName = new JTextField();
        tName.setBounds(230, 125, 185, 30);
        tName.setCaretColor(lightGreen);
        tName.setForeground(Color.RED);
        tName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tName.setOpaque(false);
        tName.setBorder(border1);
        tName.addKeyListener(this);
        tName.addFocusListener(this);
        p.add(tName);

        tFName = new JTextField();
        tFName.setBounds(230, 175, 185, 30);
        tFName.setCaretColor(lightGreen);
        tFName.setForeground(Color.RED);
        tFName.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        tFName.setOpaque(false);
        tFName.setBorder(border1);
        tFName.addKeyListener(this);
        tFName.addFocusListener(this);
        p.add(tFName);

        cCourse = new JComboBox<>();
        String[] courses = {"Select Course", "B.Tech.", "M.Tech.", "B.C.A.", "M.C.A.", "H.M."};
        for (String cours : courses) {
            cCourse.addItem(cours);
        }
        cCourse.setBounds(230, 225, 185, 30);
        cCourse.setForeground(Color.RED);
        cCourse.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cCourse.setBackground(Color.BLACK);
        cCourse.setBorder(border1);
        cCourse.setOpaque(false);
        cCourse.setMaximumRowCount(3);
        cCourse.addFocusListener(this);
        p.add(cCourse);

        cBranch = new JComboBox<>();
        cBranch.setBounds(230, 275, 185, 30);
        cBranch.setForeground(Color.RED);
        cBranch.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cBranch.setBackground(Color.BLACK);
        cBranch.setOpaque(false);
        cBranch.setBorder(border1);
        cBranch.setMaximumRowCount(3);
        cBranch.addFocusListener(this);
        p.add(cBranch);


        cSemester = new JComboBox<>();
        cSemester.setBounds(230, 325, 185, 30);
        cSemester.setForeground(Color.RED);
        cSemester.setOpaque(false);
        cSemester.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        cSemester.setBackground(Color.BLACK);
        cSemester.setBorder(border1);
        cSemester.setMaximumRowCount(3);
        cSemester.addFocusListener(this);
        p.add(cSemester);

        cCourse.addActionListener((ActionEvent e) -> {
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
        lPhoto.setBounds(30, 375, 160, 200);
        lPhoto.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
        lPhoto.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lPhoto.setBorder(BorderFactory.createLineBorder(lightGreen, 4));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lPhoto.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    FileDialog fd = new FileDialog(fr, "Select Photo", FileDialog.LOAD);
                    fd.setVisible(true);
                    path = fd.getDirectory() + fd.getFile();
                    if (path.equals("nullnull")) {
                        lPhoto.setIcon(new ImageIcon(new ImageIcon("Student Images/" + id + ".jpg").getImage().getScaledInstance(160, 200, Image.SCALE_DEFAULT)));
                    } else {
                        original = Toolkit.getDefaultToolkit().getImage(path);
                        scaled = original.getScaledInstance(160, 200, Image.SCALE_DEFAULT);
                        lPhoto.setIcon(new ImageIcon(scaled));
                        isPhotoLoad = true;
                    }
                } catch (Exception e1) {
                    System.out.println("Error" + e1);
                }

            }
        });
        p.add(lPhoto);

        loadStudentData(id);

        lUpdate = new JLabel("Update Details");
        lUpdate.setBounds(240, 520, 150, 50);
        lUpdate.setHorizontalAlignment(JLabel.CENTER);
        lUpdate.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lUpdate.setForeground(Color.RED);
        lUpdate.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
        lUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                updateDetails(id);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lUpdate.setFont(new Font("Times New Roman", Font.BOLD, 20));
                lUpdate.setForeground(lightGreen);
                lUpdate.setBorder(BorderFactory.createLineBorder(lightGreen, 2, true));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lUpdate.setFont(new Font("Times New Roman", Font.BOLD, 18));
                lUpdate.setForeground(Color.RED);
                lUpdate.setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
            }
        });

        p.add(lUpdate);

        fr.add(p);
        fr.setVisible(true);


    }

    private void updateDetails(String id) {
        int result = JOptionPane.showConfirmDialog(null, "Do you want to update details!!", "Confirm Message", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == 0) {
            String studentName = tName.getText().trim();
            String studentFName = tFName.getText().trim();
            String studentCourse = Objects.requireNonNull(cCourse.getSelectedItem()).toString().trim();
            String studentBranch = Objects.requireNonNull(cBranch.getSelectedItem()).toString().trim();
            String studentSemester = Objects.requireNonNull(cSemester.getSelectedItem()).toString().trim();
            try {
                PreparedStatement updatePS = con.prepareStatement("update student SET name  = ? ,  Fname   = ? ,  Course  = ? ,  Branch = ? ,  Semester = ? where Sid = ?");
                updatePS.setString(1, studentName);
                updatePS.setString(2, studentFName);
                updatePS.setString(3, studentCourse);
                updatePS.setString(4, studentBranch);
                updatePS.setString(5, studentSemester);
                updatePS.setString(6, id);

                int updateDetailsRS = updatePS.executeUpdate();
                if (updateDetailsRS > 0) {
                    if (isPhotoLoad) {
                        BufferedImage image = null;

                        // READ IMAGE
                        try {

                            File input_file = new File(path);
                            image = new BufferedImage(160, 200, BufferedImage.TYPE_INT_ARGB);
                            image = ImageIO.read(input_file);


                        } catch (IOException e) {
                            System.out.println("Error: " + e);
                        }

                        try {
                            File output_file = new File("Student Images//" + id + ".jpg");
                            ImageIO.write(image, "jpg", output_file);
                        } catch (IOException ignored) {
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Update Successfully...");
                    pFrame.setEnabled(true);
                    pFrame.requestFocus();
                    fr.dispose();

                } else {
                    JOptionPane.showMessageDialog(null, "Update Unsuccessful...");
                }
            } catch (SQLException e) {
                System.out.println("Error" + e);
            }
        }
    }

    private void loadStudentData(String id) {
        try {
            PreparedStatement loadPS = con.prepareStatement("select * from student where Sid = ?");
            loadPS.setString(1, id);
            ResultSet loadRS = loadPS.executeQuery();
            if (loadRS.next()) {
                tName.setText(loadRS.getString(2));
                tFName.setText(loadRS.getString(3));
                cCourse.setSelectedItem(loadRS.getObject(5));
                cBranch.setSelectedItem(loadRS.getObject(6));
                cSemester.setSelectedItem(loadRS.getObject(7));

                lPhoto.setIcon(new ImageIcon(new ImageIcon("Student Images/" + id + ".jpg").getImage().getScaledInstance(160, 200, Image.SCALE_DEFAULT)));
            }
        } catch (SQLException e) {
            System.out.println("Error" + e);
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
        if (e.getSource() == tName || e.getSource() == tFName) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border2);
            tf.setForeground(lightGreen);
        } else if (e.getSource() == cCourse || e.getSource() == cBranch || e.getSource() == cSemester) {
            JComboBox c = (JComboBox) e.getSource();
            c.setBorder(border2);
            c.setOpaque(false);
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == tName || e.getSource() == tFName) {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(border1);
            tf.setForeground(Color.red);
        } else if (e.getSource() == cCourse || e.getSource() == cBranch || e.getSource() == cSemester) {
            JComboBox c = (JComboBox) e.getSource();
            c.setBorder(border1);
            c.setOpaque(false);
        }


    }
}
