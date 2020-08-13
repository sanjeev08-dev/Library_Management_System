import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;
import java.util.Date;

public class IssueBook implements Runnable, FocusListener {
    Dimension dim;
    JFrame fr, pFrame;
    private JLabel lLeft, lRight;
    private String[] studentDetailsString = {"Name :", "Father Name :", "Roll No. :", "Course :", "Semester :"};
    private String[] bookDetailsString = {"Book Name :", "Author Name :", "Publisher Name :"};
    JPanel p;
    private JLabel lClose;
    private JTextField sTSearch;
    private JTextField bTSearch;
    private JTextField[] studentDetailsTextField;
    private JTextField[] bookDetailsTextField;
    private String sSearch = "Enter Student ID or Roll No";
    private String bSearch = "Enter Book ID";
    Border border1;
    private int bookQuantity;
    private String studentID, studentName, studentRollNo, bookID, bookName;
    private boolean isStudentDataFound, isBookDataFound;
    Connection con;

    IssueBook(JFrame f) {
        pFrame = f;

        dim = Toolkit.getDefaultToolkit().getScreenSize();
        fr = new JFrame();
        fr.setBounds((dim.width - 800) / 2, (dim.height - 600) / 2, 800, 600);
        fr.setUndecorated(true);

        con = Dao.getConnection();

        Color red = new Color(252, 9, 0);
        border1 = BorderFactory.createMatteBorder(0, 0, 1, 1, Color.RED);

        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                Color color1 = Color.BLACK;
                Color color2 = Color.blue;
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        p.setBounds(0, 0, 800, 700);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(red, 2, true), "Issue Book", 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), red));

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(760, 17, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                pFrame.setEnabled(true);
                pFrame.requestFocus();
                fr.dispose();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(32, 32, Image.SCALE_DEFAULT)));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        lLeft = new JLabel();
        lLeft.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 0, 10), 2, true), "Student Details", 0, 0, (new Font("Times New Roman", Font.BOLD, 20)), new Color(255, 0, 1)));
        lLeft.setBackground(new Color(242, 255, 0));
        lLeft.setOpaque(true);
        p.add(lLeft);

        sTSearch = new JTextField(sSearch);
        sTSearch.setBounds(30, 50, 250, 40);
        sTSearch.setBorder(border1);
        sTSearch.setHorizontalAlignment(JTextField.CENTER);
        sTSearch.setBackground(Color.YELLOW);
        sTSearch.setCaretColor(Color.RED);
        sTSearch.setFont(new Font("Times New Roman", Font.BOLD, 20));
        sTSearch.setForeground(Color.RED);
        sTSearch.addFocusListener(this);
        lLeft.add(sTSearch);

        JButton sIDSearchButton = new JButton(new ImageIcon("Images/seachIcon.png"));
        sIDSearchButton.setBounds(300, 54, 35, 35);
        sIDSearchButton.setBorderPainted(false);
        sIDSearchButton.setContentAreaFilled(false);
        sIDSearchButton.setFocusPainted(false);
        sIDSearchButton.setOpaque(false);
        lLeft.add(sIDSearchButton);
        sIDSearchButton.addActionListener(e -> loadStudentDetails());

        int studentLabelY = 150;
        JLabel[] studentDetailsLabel = new JLabel[studentDetailsString.length];
        int iLabel;
        for (iLabel = 0; iLabel < studentDetailsString.length; iLabel++) {
            studentDetailsLabel[iLabel] = new JLabel(studentDetailsString[iLabel]);
            studentDetailsLabel[iLabel].setBounds(30, studentLabelY, 130, 30);
            studentDetailsLabel[iLabel].setForeground(Color.RED);
            studentDetailsLabel[iLabel].setFont(new Font("Times New Roman", Font.BOLD, 20));
            lLeft.add(studentDetailsLabel[iLabel]);

            studentLabelY = studentLabelY + 50;
        }

        int studentTextFieldY = 150;
        studentDetailsTextField = new JTextField[studentDetailsString.length];
        int iText;
        for (iText = 0; iText < studentDetailsString.length; iText++) {
            studentDetailsTextField[iText] = new JTextField();
            studentDetailsTextField[iText].setBounds(170, studentTextFieldY, 150, 30);
            studentDetailsTextField[iText].setForeground(Color.RED);
            studentDetailsTextField[iText].setOpaque(false);
            studentDetailsTextField[iText].setBorder(border1);
            studentDetailsTextField[iText].setEditable(false);
            studentDetailsTextField[iText].setFont(new Font("Times New Roman", Font.BOLD, 20));
            lLeft.add(studentDetailsTextField[iText]);

            studentTextFieldY = studentTextFieldY + 50;
        }

        lRight = new JLabel();
        lRight.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 0, 10), 2, true), "Book Details", 0, 0, (new Font("Times New Roman", Font.BOLD, 20)), new Color(255, 0, 1)));
        lRight.setBackground(new Color(242, 255, 0));
        lRight.setOpaque(true);
        p.add(lRight);

        bTSearch = new JTextField(bSearch);
        bTSearch.setBounds(30, 50, 250, 40);
        bTSearch.setBorder(border1);
        bTSearch.setHorizontalAlignment(JTextField.CENTER);
        bTSearch.setBackground(Color.YELLOW);
        bTSearch.setCaretColor(Color.RED);
        bTSearch.setFont(new Font("Times New Roman", Font.BOLD, 20));
        bTSearch.setForeground(Color.RED);
        bTSearch.addFocusListener(this);
        lRight.add(bTSearch);

        JButton bIDSearchButton = new JButton(new ImageIcon("Images/seachIcon.png"));
        bIDSearchButton.setBounds(300, 54, 35, 35);
        bIDSearchButton.setBorderPainted(false);
        bIDSearchButton.setContentAreaFilled(false);
        bIDSearchButton.setFocusPainted(false);
        bIDSearchButton.setOpaque(false);
        lRight.add(bIDSearchButton);
        bIDSearchButton.addActionListener(e -> loadBookDetails());

        int bookLabelY = 150;
        JLabel[] bookDetailsLabel = new JLabel[bookDetailsString.length];
        int jLabel;
        for (jLabel = 0; jLabel < bookDetailsString.length; jLabel++) {
            bookDetailsLabel[jLabel] = new JLabel(bookDetailsString[jLabel]);
            bookDetailsLabel[jLabel].setBounds(30, bookLabelY, 150, 30);
            bookDetailsLabel[jLabel].setForeground(Color.RED);
            bookDetailsLabel[jLabel].setFont(new Font("Times New Roman", Font.BOLD, 20));
            lRight.add(bookDetailsLabel[jLabel]);

            bookLabelY = bookLabelY + 50;
        }

        int bookTextFieldY = 150;
        bookDetailsTextField = new JTextField[bookDetailsString.length];
        int jText;
        for (jText = 0; jText < bookDetailsString.length; jText++) {
            bookDetailsTextField[jText] = new JTextField();
            bookDetailsTextField[jText].setBounds(190, bookTextFieldY, 150, 30);
            bookDetailsTextField[jText].setForeground(Color.RED);
            bookDetailsTextField[jText].setOpaque(false);
            bookDetailsTextField[jText].setBorder(border1);
            bookDetailsTextField[jText].setEditable(false);
            bookDetailsTextField[jText].setFont(new Font("Times New Roman", Font.BOLD, 20));
            lRight.add(bookDetailsTextField[jText]);

            bookTextFieldY = bookTextFieldY + 50;
        }

        JButton issueButton = new JButton("Issue Book");
        issueButton.setBounds((800 - 150) / 2, 555, 150, 40);
        issueButton.setFont(new Font("Times New Roman", Font.BOLD, 20));
        issueButton.setBackground(Color.RED);
        fr.getRootPane().setDefaultButton(issueButton);
        issueButton.setForeground(Color.YELLOW);
        issueButton.setFocusPainted(false);
        issueButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.RED, 2, true), BorderFactory.createStrokeBorder(new BasicStroke(2), Color.YELLOW)));
        issueButton.addActionListener(e -> issueBook());
        p.add(issueButton);

        fr.add(p);
        Thread th = new Thread(this);
        th.start();

        fr.setVisible(true);
    }

    private void issueBook() {

        if (!isStudentDataFound) {
            JOptionPane.showMessageDialog(fr, "Load Student Details");
        } else if (!isBookDataFound) {
            JOptionPane.showMessageDialog(fr, "Load Book Details");
        } else {
            try {
                //noinspection StatementWithEmptyBody
                if (!hasBookIssued(studentID, bookID)) {
                } else {

                    assert con != null;
                    Timestamp date = new Timestamp(new Date().getTime());
                    PreparedStatement updateIssuedTableps = con.prepareStatement("insert into issued values (?,?,?,?,?,?)");
                    updateIssuedTableps.setString(1, studentID);
                    updateIssuedTableps.setString(2, studentName);
                    updateIssuedTableps.setString(3, studentRollNo);
                    updateIssuedTableps.setString(4, bookID);
                    updateIssuedTableps.setString(5, bookName);
                    updateIssuedTableps.setTimestamp(6, date);
                    int updateIssuedTablers = updateIssuedTableps.executeUpdate();
                    if (updateIssuedTablers > 0) {
                        JOptionPane.showMessageDialog(fr, "Book Issued Successfully");
                        bookQuantity = bookQuantity - 1;
                        sTSearch.setText("");
                        bTSearch.setText("");
                        for (int z1 = 0; z1 < studentDetailsString.length; z1++) {
                            studentDetailsTextField[z1].setText("");
                        }

                        for (int z2 = 0; z2 < bookDetailsString.length; z2++) {
                            bookDetailsTextField[z2].setText("");
                        }
                        isBookDataFound = false;
                        isStudentDataFound = false;

                        PreparedStatement updateBookTableps = con.prepareStatement("UPDATE book SET BookQuantity = ? WHERE Bid = ?");
                        updateBookTableps.setInt(1, bookQuantity);
                        updateBookTableps.setString(2, bookID);
                        int updateQuantityRS = updateBookTableps.executeUpdate();
                        if (updateQuantityRS > 0) {
                            System.out.println("Success");
                        } else {
                            System.out.println("Failed");
                        }
                    } else {
                        JOptionPane.showMessageDialog(fr, "Book issued Failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
            }
        }


    }

    private boolean hasBookIssued(String studentID, String bookID) {
        try {
            PreparedStatement checkIssuedPS = con.prepareStatement("Select * from issued where  StudentID = ? AND BookID = ?");
            checkIssuedPS.setString(1, studentID);
            checkIssuedPS.setString(2, bookID);
            ResultSet checkIssuedRS = checkIssuedPS.executeQuery();
            if (checkIssuedRS.next()) {
                JOptionPane.showMessageDialog(null, "Book is already assign", "Assigned notice", JOptionPane.ERROR_MESSAGE);
                return false;
            } else {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void loadBookDetails() {
        String bookIDs = bTSearch.getText().trim().toUpperCase();
        if (bookIDs.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Book ID", "Error", JOptionPane.WARNING_MESSAGE);
            isBookDataFound = false;
            bTSearch.requestFocus();
        } else if (bookIDs.equals(sSearch.trim().toUpperCase())) {
            JOptionPane.showMessageDialog(null, "Please Enter Book ID", "Error", JOptionPane.WARNING_MESSAGE);
            isBookDataFound = false;
            bTSearch.requestFocus();
        } else {
            try {
                PreparedStatement loadBookDetailsps = con.prepareStatement("select * from book where Bid = ?");
                loadBookDetailsps.setString(1, bookIDs);
                ResultSet loadBookDetailsRs = loadBookDetailsps.executeQuery();

                if (loadBookDetailsRs.next()) {
                    bookID = loadBookDetailsRs.getString(1);
                    bookName = loadBookDetailsRs.getString(2);
                    bookQuantity = loadBookDetailsRs.getInt(5);

                    if (bookQuantity < 1) {
                        JOptionPane.showMessageDialog(fr, "Insufficient Book Quantity");
                        isBookDataFound = false;
                    } else {
                        bookDetailsTextField[0].setText(loadBookDetailsRs.getString(2));
                        bookDetailsTextField[1].setText(loadBookDetailsRs.getString(3));
                        bookDetailsTextField[2].setText(loadBookDetailsRs.getString(4));
                        isBookDataFound = true;
                    }

                } else {
                    JOptionPane.showMessageDialog(fr, "Sorry!! Book does not Found!!");
                    isBookDataFound = false;
                }

            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
                isBookDataFound = false;
            }

        }
    }

    private void loadStudentDetails() {
        String studentIDRoll = sTSearch.getText().trim().toUpperCase();
        if (studentIDRoll.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID or Roll No.", "Error", JOptionPane.WARNING_MESSAGE);
            isStudentDataFound = false;
            sTSearch.requestFocus();
        } else if (studentIDRoll.equals(sSearch.trim().toUpperCase())) {
            JOptionPane.showMessageDialog(null, "Please Enter Student ID or Roll No.", "Error", JOptionPane.WARNING_MESSAGE);
            isStudentDataFound = false;
            sTSearch.requestFocus();
        } else {
            try {
                PreparedStatement loadStudentDetailsPs = con.prepareStatement("select * from student where Sid = ? or RollNo=?");
                loadStudentDetailsPs.setString(1, studentIDRoll);
                loadStudentDetailsPs.setString(2, studentIDRoll);
                ResultSet loadStudentDetailsRs = loadStudentDetailsPs.executeQuery();

                if (loadStudentDetailsRs.next()) {
                    studentID = loadStudentDetailsRs.getString(1);
                    studentName = loadStudentDetailsRs.getString(2);
                    studentRollNo = loadStudentDetailsRs.getString(4);

                    studentDetailsTextField[0].setText(loadStudentDetailsRs.getString(2));
                    studentDetailsTextField[1].setText(loadStudentDetailsRs.getString(3));
                    studentDetailsTextField[2].setText(loadStudentDetailsRs.getString(4));
                    studentDetailsTextField[3].setText(loadStudentDetailsRs.getString(5));
                    studentDetailsTextField[4].setText(loadStudentDetailsRs.getString(7));

                    isStudentDataFound = true;

                } else {
                    JOptionPane.showMessageDialog(fr, "Sorry!! Student does not Found");
                    isStudentDataFound = false;
                }

            } catch (HeadlessException | SQLException e) {
                System.out.println(e);
                isStudentDataFound = false;
            }

        }
    }


    @Override
    public void run() {
        int left = -400;
        int right = 810;
        while (left != 10) {
            try {
                Thread.sleep(7);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            lLeft.setBounds(left, 50, 390, 500);
            lRight.setBounds(right, 50, 390, 500);
            right--;
            left++;
        }

    }

    @Override
    public void focusGained(FocusEvent e) {
        if (e.getSource() == sTSearch) {
            if (sTSearch.getText().trim().equals(sSearch)) {
                sTSearch.setText("");
            }
        } else if (e.getSource() == bTSearch) {
            if (bTSearch.getText().trim().equals(bSearch)) {
                bTSearch.setText("");
            }
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (e.getSource() == sTSearch) {
            if (sTSearch.getText().trim().equals("")) {
                sTSearch.setText(sSearch);
            }
        } else if (e.getSource() == bTSearch) {
            if (bTSearch.getText().trim().equals("")) {
                bTSearch.setText(bSearch);
            }
        }
    }
}
