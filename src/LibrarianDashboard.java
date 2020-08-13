import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LibrarianDashboard implements Runnable, MouseListener {
    JFrame fr;
    private JLabel lAddSTudent, lViewStudent, lAddBook, lRemoveBook, lIssueBook, lIssuedBookList, lViewBook, lRemoveStudent, lLogout;
    private JPanel[] pleft = new JPanel[5];
    private JPanel[] pright = new JPanel[4];
    private Color lightPurpul;
    Connection con = Dao.getConnection();


    LibrarianDashboard(String un) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 600) / 2, 700, 600);
        fr.setUndecorated(true);

        lightPurpul = new Color(220, 150, 255);
        JPanel p = new JPanel();
        p.setBounds(0, 0, 700, 600);
        p.setBackground(Color.BLACK);
        p.setLayout(null);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(lightPurpul, 2, true), "Welcome: " + un, 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), lightPurpul));

        int panelLeftY = 50;
        int panelRightY = 107;

        for (int i = 0; i < pleft.length; i++) {
            pleft[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Dimension arcs = new Dimension(15, 15);
                    int width = getWidth();
                    int height = getHeight();
                    Graphics2D graphics = (Graphics2D) g;
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                    //Draws the rounded opaque panel with borders.
                    graphics.setColor(getBackground());
                    graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint background
                    graphics.setStroke(new BasicStroke(4));
                    graphics.setColor(new Color(78, 0, 186));
                    graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint border

                }
            };
            pleft[i].setBackground(Color.WHITE);
            pleft[i].setLayout(null);
            pleft[i].setOpaque(false);
            p.add(pleft[i]);

            panelLeftY = panelLeftY + 115;

        }

        for (int i = 0; i < pright.length; i++) {
            pright[i] = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Dimension arcs = new Dimension(15, 15);
                    int width = getWidth();
                    int height = getHeight();
                    Graphics2D graphics = (Graphics2D) g;
                    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


                    graphics.setColor(getBackground());
                    graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint background
                    graphics.setStroke(new BasicStroke(4));
                    graphics.setColor(new Color(78, 0, 186));
                    graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);//paint border
                }
            };
            pright[i].setBackground(Color.WHITE);
            pright[i].setLayout(null);
            pright[i].setOpaque(false);
            p.add(pright[i]);

            panelRightY = panelRightY + 115;

        }


        lAddSTudent = new JLabel("Add Student");
        lAddSTudent.setForeground(lightPurpul);
        lAddSTudent.setFont(new Font("Cambria", Font.BOLD, 30));
        lAddSTudent.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lAddSTudent.addMouseListener(this);
        lAddSTudent.setHorizontalAlignment(JLabel.CENTER);
        pleft[0].add(lAddSTudent);

        lViewStudent = new JLabel("View Students");
        lViewStudent.setForeground(lightPurpul);
        lViewStudent.setFont(new Font("Cambria", Font.BOLD, 30));
        lViewStudent.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lViewStudent.addMouseListener(this);
        lViewStudent.setHorizontalAlignment(JLabel.CENTER);
        pright[0].add(lViewStudent);

        lAddBook = new JLabel("Add Book");
        lAddBook.setForeground(lightPurpul);
        lAddBook.setFont(new Font("Cambria", Font.BOLD, 30));
        lAddBook.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lAddBook.addMouseListener(this);
        lAddBook.setHorizontalAlignment(JLabel.CENTER);
        pleft[1].add(lAddBook);

        lViewBook = new JLabel("View All Book");
        lViewBook.setForeground(lightPurpul);
        lViewBook.setFont(new Font("Cambria", Font.BOLD, 30));
        lViewBook.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lViewBook.addMouseListener(this);
        lViewBook.setHorizontalAlignment(JLabel.CENTER);
        pright[1].add(lViewBook);

        lIssueBook = new JLabel("Issue Book");
        lIssueBook.setForeground(lightPurpul);
        lIssueBook.setFont(new Font("Cambria", Font.BOLD, 30));
        lIssueBook.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lIssueBook.addMouseListener(this);
        lIssueBook.setHorizontalAlignment(JLabel.CENTER);
        pleft[2].add(lIssueBook);

        lIssuedBookList = new JLabel("View Issued Book List");
        lIssuedBookList.setForeground(lightPurpul);
        lIssuedBookList.setFont(new Font("Cambria", Font.BOLD, 30));
        lIssuedBookList.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lIssuedBookList.addMouseListener(this);
        lIssuedBookList.setHorizontalAlignment(JLabel.CENTER);
        pright[2].add(lIssuedBookList);

        lRemoveBook = new JLabel("Remove Book");
        lRemoveBook.setForeground(lightPurpul);
        lRemoveBook.setFont(new Font("Cambria", Font.BOLD, 30));
        lRemoveBook.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lRemoveBook.addMouseListener(this);
        lRemoveBook.setHorizontalAlignment(JLabel.CENTER);
        pleft[3].add(lRemoveBook);

        lRemoveStudent = new JLabel("Remove Student");
        lRemoveStudent.setForeground(lightPurpul);
        lRemoveStudent.setFont(new Font("Cambria", Font.BOLD, 30));
        lRemoveStudent.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lRemoveStudent.addMouseListener(this);
        lRemoveStudent.setHorizontalAlignment(JLabel.CENTER);
        pright[3].add(lRemoveStudent);

        lLogout = new JLabel("Logout");
        lLogout.setForeground(lightPurpul);
        lLogout.setFont(new Font("Cambria", Font.BOLD, 30));
        lLogout.setBounds((400 - 350) / 2, (45 - 40) / 2, 350, 40);
        lLogout.addMouseListener(this);
        lLogout.setHorizontalAlignment(JLabel.CENTER);
        pleft[4].add(lLogout);

        fr.add(p);
        Thread th = new Thread(this);
        th.start();
        fr.setVisible(true);
    }


    @Override
    public void run() {
        int b = 750;
        int a = -450;
        while (b != 10) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int panelLeftY = 50;
            int panelRightY = 107;

            for (JPanel jPanel : pleft) {
                jPanel.setBounds(b, panelLeftY, 400, 45);
                panelLeftY = panelLeftY + 115;

            }

            for (JPanel jPanel : pright) {
                jPanel.setBounds(a, panelRightY, 400, 45);
                panelRightY = panelRightY + 115;

            }
            a++;
            b--;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == lAddSTudent) {
            fr.setEnabled(false);
            new AddStudent(fr);
        } else if (e.getSource() == lViewStudent) {
            fr.setEnabled(false);
            try {
                new ViewStudent(fr);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == lAddBook) {
            fr.setEnabled(false);
            new AddBook(fr);
        } else if (e.getSource() == lViewBook) {
            fr.setEnabled(false);
            try {
                new ViewBook(fr);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == lIssueBook) {
            fr.setEnabled(false);
            new IssueBook(fr);
        } else if (e.getSource() == lIssuedBookList) {
            fr.setEnabled(false);
            try {
                new ViewIssuedBooks(fr);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == lRemoveBook) {
            String BID = JOptionPane.showInputDialog(null, "Enter Book ID:", "BID");
            try {
                PreparedStatement BookPS = con.prepareStatement("DELETE FROM book WHERE Bid=?");
                BookPS.setString(1, BID);
                int BookRS = BookPS.executeUpdate();

                PreparedStatement IssuedPS = con.prepareStatement("DELETE FROM issued WHERE BookID=?");
                IssuedPS.setString(1, BID);
                int IssuedRS = IssuedPS.executeUpdate();

                if (BookRS > 0 || IssuedRS > 0) {
                    JOptionPane.showMessageDialog(null, "Success Delete");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == lRemoveStudent) {
            String SID = JOptionPane.showInputDialog(null, "Enter Student ID or Roll No.");
            try {
                PreparedStatement studentIssuedPS = con.prepareStatement("select * from issued where  StudentID = ? or StudentRollNo = ?");
                studentIssuedPS.setString(1, SID);
                studentIssuedPS.setString(2, SID);
                ResultSet studentIssuedRS = studentIssuedPS.executeQuery();
                if (studentIssuedRS.next()) {
                    int choice = JOptionPane.showOptionDialog(null, "You have Issued Book", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                    if (choice == JOptionPane.YES_OPTION) {
                        String student_id = studentIssuedRS.getString(1);
                        String student_name = studentIssuedRS.getString(2);
                        String student_rollNo = studentIssuedRS.getString(3);
                        new BookReturnPage(student_id, student_name, student_rollNo, fr);

                    } else {
                        System.out.println("No");
                    }
                }else{
                    //Delete Student
                    PreparedStatement studentDeletePS = con.prepareStatement("DELETE FROM student WHERE Sid = ? or RollNo = ?");
                    studentDeletePS.setString(1, SID);
                    studentDeletePS.setString(2, SID);
                    int studentDeleteRS = studentDeletePS.executeUpdate();
                    if(studentDeleteRS > 0){
                        JOptionPane.showMessageDialog(null,"Student Delete Successful","Delete Message",JOptionPane.OK_OPTION);
                    }else {
                        JOptionPane.showMessageDialog(null,"Student Delete Failed","Delete Message",JOptionPane.ERROR_MESSAGE);

                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } else if (e.getSource() == lLogout) {
            new LibrarianLogin();
            fr.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == lAddSTudent) {
            lAddSTudent.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lViewStudent) {
            lViewStudent.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lAddBook) {
            lAddBook.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lRemoveBook) {
            lRemoveBook.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lIssueBook) {
            lIssueBook.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lIssuedBookList) {
            lIssuedBookList.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lViewBook) {
            lViewBook.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lRemoveStudent) {
            lRemoveStudent.setForeground(new Color(78, 0, 186));
        } else if (e.getSource() == lLogout) {
            lLogout.setForeground(new Color(78, 0, 186));
        }

    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == lAddSTudent) {
            lAddSTudent.setForeground(lightPurpul);
        } else if (e.getSource() == lViewStudent) {
            lViewStudent.setForeground(lightPurpul);
        } else if (e.getSource() == lAddBook) {
            lAddBook.setForeground(lightPurpul);
        } else if (e.getSource() == lRemoveBook) {
            lRemoveBook.setForeground(lightPurpul);
        } else if (e.getSource() == lIssueBook) {
            lIssueBook.setForeground(lightPurpul);
        } else if (e.getSource() == lIssuedBookList) {
            lIssuedBookList.setForeground(lightPurpul);
        } else if (e.getSource() == lViewBook) {
            lViewBook.setForeground(lightPurpul);
        } else if (e.getSource() == lRemoveStudent) {
            lRemoveStudent.setForeground(lightPurpul);
        } else if (e.getSource() == lLogout) {
            lLogout.setForeground(lightPurpul);
        }


    }
}
