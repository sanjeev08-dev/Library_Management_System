import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Splash implements Runnable {
    private JFrame fr;
    private JProgressBar pb;
    private int progress = 0;

    private Splash() {
        fr = new JFrame("Library Management System");
        fr.setSize(600, 500);
        fr.setLocationRelativeTo(null);
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Image img = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        fr.setIconImage(img);
        fr.setUndecorated(true);
        fr.setLayout(null);

        MyJPanel p1 = new MyJPanel("Images/Splash_back.jpg");
        p1.setBounds(0, 0, 600, 450);
        p1.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 3));
        p1.setLayout(null);


        JLabel ltitle = new JLabel("<html>Library<br> Management <br>System</html>");
        ltitle.setBounds((600 - 595) / 2, (450 - 445) / 2, 595, 445);
        ltitle.setHorizontalAlignment(JLabel.CENTER);
        ltitle.setFont(new Font("Times New Roman", Font.BOLD, 50));
        ltitle.setForeground(Color.BLACK);
        p1.add(ltitle);

        JLabel l1 = new JLabel(new ImageIcon(new ImageIcon("Images/circle.gif").getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
        l1.setBounds(560, 410, 30, 30);
        ltitle.add(l1);

        JPanel p2 = new JPanel();
        p2.setBounds(0, 450, 600, 50);
        p2.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.RED));
        p2.setLayout(null);

        pb = new JProgressBar();
        pb.setValue(progress);
        pb.setBounds(3, 3, 594, 44);
        pb.setStringPainted(true);
        pb.setForeground(new Color(0, 76, 112));
        p2.add(pb);

        fr.add(p1);
        fr.add(p2);
        fr.setVisible(true);

        Thread th = new Thread(this);
        th.start();
    }

    public void run() {
        while (progress <= 100) {
            progress++;
            pb.setValue(progress);
            try {
                Thread.sleep(20);
            } catch (InterruptedException ignored) {

            }
        }

        fr.dispose();
        new Home();
    }

    static class MyJPanel extends JPanel {
        private BufferedImage image;
        private int w, h;

        MyJPanel(String fname) {
            //reads the image
            try {
                image = ImageIO.read(new File(fname));
                w = image.getWidth();
                h = image.getHeight();
            } catch (IOException ioe) {
                System.out.println("Could not read in the pic");
                //System.exit(0);
            }
        }

        public Dimension getPreferredSize() {
            return new Dimension(w, h);
        }

        //this will draw the image
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public static void main(String[] args) {
        new Splash();
    }
}
