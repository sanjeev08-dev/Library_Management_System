import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class AdminDashboard implements Runnable {
    String name;
    JFrame fr;
    JPanel p;
    private JLabel lTitle, lClose, lAddLIb, LSearchLib, LDeleteLIb, LLogout;
    private static Boolean flag1 = true;
    Thread th;

    AdminDashboard(String un) {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        name = un;
        fr = new JFrame("Admin Dashboard");
        Image img = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 700) / 2, 700, 700);
        fr.setLayout(null);
        fr.setUndecorated(true);

        p = new JPanel();
        p.setBounds(0, 0, 700, 700);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(255, 251, 0), 3, true), "Login By:" + un, 0, 0, (new Font("Times New Roman", Font.BOLD, 26)), (new Color(255, 251, 0))));
        p.setBackground(new Color(43, 43, 43));
        fr.add(p);

        lTitle = new JLabel("Library Management System");
        lTitle.setForeground(new Color(255, 251, 0));
        lTitle.setFont(new Font("Broadway", Font.PLAIN, 30));
        p.setLayout(null);
        p.add(lTitle);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(658, 20, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new Home();
                fr.dispose();
                flag1 = false;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lClose.setIcon(new ImageIcon("Images/exit1.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lClose.setIcon(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
            }
        });

        lAddLIb = new JLabel(new ImageIcon(new ImageIcon("Images/AddLib.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        lAddLIb.setToolTipText("<html><body><b><font color = yellow size = 4>Add Librarian</font></b></body></html>");
        p.add(lAddLIb);
        lAddLIb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AddLibrarian(fr);
                fr.setEnabled(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                lAddLIb.setIcon(new ImageIcon("Images/AddLib.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lAddLIb.setIcon(new ImageIcon(new ImageIcon("Images/AddLib.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            }
        });

        LDeleteLIb = new JLabel(new ImageIcon(new ImageIcon("Images/DelLib.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
        LDeleteLIb.setToolTipText("<html><body><b><font color = yellow size = 4>Delete Librarian</font></b></body></html>");
        p.add(LDeleteLIb);
        LDeleteLIb.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseClicked(MouseEvent e) {
                try {
                    new DeleteLibrarian(fr);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LDeleteLIb.setIcon(new ImageIcon("Images/DelLib.png"));
            }


            @Override
            public void mouseExited(MouseEvent e) {
                LDeleteLIb.setIcon(new ImageIcon(new ImageIcon("Images/DelLib.png").getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT)));
            }
        });

        LSearchLib = new JLabel(new ImageIcon(new ImageIcon("Images/SearchLib.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));
        LSearchLib.setToolTipText("<html><body><b><font color = yellow size = 4>Search Librarian</font></b></body></html>");
        p.add(LSearchLib);
        LSearchLib.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    new SearchLibrarian(fr);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                fr.setEnabled(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LSearchLib.setIcon(new ImageIcon("Images/SearchLib.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                LSearchLib.setIcon(new ImageIcon(new ImageIcon("Images/SearchLib.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));
            }
        });

        LLogout = new JLabel(new ImageIcon(new ImageIcon("Images/AdminLogout.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));
        LLogout.setToolTipText("<html><body><b><font color = yellow size = 4>Logout!!</font></b></body></html>");
        p.add(LLogout);
        LLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new AdminLogin();
                fr.dispose();
                flag1 = false;

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                LLogout.setIcon(new ImageIcon("Images/AdminLogout.png"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                LLogout.setIcon(new ImageIcon(new ImageIcon("Images/AdminLogout.png").getImage().getScaledInstance(180, 180, Image.SCALE_DEFAULT)));
            }
        });


        th = new Thread(this);
        flag1 = true;
        th.start();
        fr.setVisible(true);

    }


    @Override
    public void run() {
        int b = 750;
        int a = -200;
        while (b != 400) {
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LSearchLib.setBounds(80, b, 256, 256);
            LLogout.setBounds(320, b, 256, 256);
            b--;

            lAddLIb.setBounds(80, a, 256, 256);
            LDeleteLIb.setBounds(320, a, 256, 256);
            a++;
        }
        int x = 700;
        while (flag1) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            if (x == -480) {
                x = 700;

            } else {
                lTitle.setBounds(x, 40, 480, 100);
                x--;
            }
        }
    }
}
