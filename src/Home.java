import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Home implements Runnable {
    JFrame fr;
    private JLabel ltitle, ladmin, llibrarian, lClose;
    private boolean flag1;
    JPanel p;

    Home() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        fr = new JFrame();
        fr.setTitle("Home");
        Image img = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        fr.setIconImage(img);
        fr.setBounds((dim.width - 700) / 2, (dim.height - 700) / 2, 700, 700);
        fr.setLayout(null);
        fr.setUndecorated(true);

        p = new JPanel();
        p.setBounds(0, 0, 700, 700);
        p.setLayout(null);
        p.setBackground(Color.black);
        p.setBorder(BorderFactory.createLineBorder(new Color(0, 255, 17), 2));
        fr.add(p);

        lClose = new JLabel(new ImageIcon(new ImageIcon("Images/exit1.png").getImage().getScaledInstance(26, 26, Image.SCALE_DEFAULT)));
        lClose.setBounds(658, 20, 32, 32);
        lClose.setToolTipText("Close");
        p.add(lClose);
        lClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
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


        ltitle = new JLabel("<html>Welcome <br>Admin</html>");
        ltitle.setFont(new Font("Magneto", Font.BOLD, 60));
        ltitle.setForeground(Color.GREEN);
        ltitle.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                ltitle.setFont(new Font("Magneto", Font.BOLD, 60));
                ltitle.setForeground(Color.BLUE);

            }

            public void mouseExited(MouseEvent me) {
                ltitle.setFont(new Font("Magneto", Font.BOLD, 60));
                ltitle.setForeground(Color.GREEN);

            }
        });

        ladmin = new JLabel(new ImageIcon(new ImageIcon("Images/admin.png").getImage().getScaledInstance(250, 222, Image.SCALE_DEFAULT)));
        ladmin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ladmin.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                ladmin.setIcon(new ImageIcon(new ImageIcon("Images/admin.png").getImage().getScaledInstance(290, 258, Image.SCALE_DEFAULT)));
            }

            public void mouseExited(MouseEvent me) {
                ladmin.setIcon(new ImageIcon(new ImageIcon("Images/admin.png").getImage().getScaledInstance(250, 222, Image.SCALE_DEFAULT)));
            }

            public void mouseClicked(MouseEvent me) {
                new AdminLogin();
                fr.dispose();
            }
        });


        llibrarian = new JLabel(new ImageIcon(new ImageIcon("Images/lib_icon.png").getImage().getScaledInstance(250, 222, Image.SCALE_DEFAULT)));
        llibrarian.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        llibrarian.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                llibrarian.setIcon(new ImageIcon(new ImageIcon("Images/lib_icon.png").getImage().getScaledInstance(290, 258, Image.SCALE_DEFAULT)));
            }

            public void mouseExited(MouseEvent me) {
                llibrarian.setIcon(new ImageIcon(new ImageIcon("Images/lib_icon.png").getImage().getScaledInstance(250, 222, Image.SCALE_DEFAULT)));
            }

            public void mouseClicked(MouseEvent me) {
                new LibrarianLogin();
                fr.dispose();
            }
        });

        p.add(ltitle);
        p.add(ladmin);
        p.add(llibrarian);
        fr.setVisible(true);

        Thread th = new Thread(this);
        flag1 = true;
        th.start();
    }

    @Override
    public void run() {
        int x = 710;
        int y = -300;
        while (flag1) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException ignored) {
            }
            if (x == 200)
                flag1 = false;
            else {
                ltitle.setBounds(x, 10, 290, 140);
                //noinspection SuspiciousNameCombination
                ladmin.setBounds(y, 180, 290, 258);
                //noinspection SuspiciousNameCombination
                llibrarian.setBounds(y, 420, 290, 258);
                x--;
                y++;
            }
        }
    }

}
