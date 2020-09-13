package censorchip;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class censorchip {
    private JButton selectPicBtn;
    private JPanel parent;
    private JLabel picDisplay;
    private JButton addChipBtn;
    private JButton remChipBtn;
    private JButton autoAddBtn;
    private static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("chips");
        frame.add(new censorchip().parent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public censorchip() {
        selectPicBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new File("C:\\Users\\raicc\\Desktop"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "JPG & PNG Images", "jpg", "png");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(parent);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    String path = file.getAbsolutePath();
                    picDisplay.setIcon(ResizeImage(path));
                } else if (returnVal == JFileChooser.CANCEL_OPTION) {
                    System.out.println("No File Selected.");
                }
            }
        });

        addChipBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.add(new TestPane(),0);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public ImageIcon ResizeImage(String ImagePath){
        ImageIcon MyImage = new ImageIcon(ImagePath);
        Image img = MyImage.getImage();
        Integer h = img.getHeight(null);
        Integer w = img.getWidth(null);
        Image newImg = img.getScaledInstance(picDisplay.getWidth(), (int) Math.round((picDisplay.getWidth() * (h.doubleValue()/w.doubleValue()))), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImg);
        return image;
    }

    public class TestPane extends JLayeredPane {

        public TestPane() {
            try {
                BufferedImage img = ImageIO.read(new File("C:\\Users\\raicc\\Desktop\\ONE_CHIP.png"));
                JLabel label = new JLabel(new ImageIcon(img));
                label.setSize(label.getPreferredSize());
                label.setLocation(0, 0);
                MouseHandler mh  = new MouseHandler();
                label.addMouseListener(mh);
                label.addMouseMotionListener(mh);
                add(label);
            } catch (IOException exp) {
                exp.printStackTrace();
            }

        }



        @Override
        public Dimension getPreferredSize() {
            return new Dimension(780, 580);
        }

        public class MouseHandler extends MouseAdapter {

            private Point offset;

            @Override
            public void mousePressed(MouseEvent e) {
                JLabel label = (JLabel) e.getComponent();
                moveToFront(label);
                offset = e.getPoint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getPoint().x - offset.x;
                int y = e.getPoint().y - offset.y;
                Component component = e.getComponent();
                Point location = component.getLocation();
                location.x += x;
                location.y += y;
                component.setLocation(location);
            }

        }

    }


}