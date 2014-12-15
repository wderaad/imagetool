import java.awt.BorderLayout;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
/*
 * This is a Test class which will not go into production
 * Basically a helper class to begin adding a filter and ImageList class
 */



public class Test {

    /**
     * @param args
     */

    public static void main(String[] args) {
        BufferedImage image = addImage();
        ImageList test = new ImageList(image);
        //Image image2 = addImage();
        //test.addImage(image2);
        Filter filt1 = new Filter(image, "BLUR");
        Filter filt2 = new Filter(image, "SHARPEN");
        Filter filt3 = new Filter(image, "EDGE");
        Filter filt4 = new Filter(image, "HISTOGRAM");
        Filter filt5 = new Filter(image, "GRAY");
        Filter filt6 = new Filter(image, "BRIGHT");
        Filter filt7 = new Filter(image, "DARK");
        filt1.execute();
        filt2.execute();
        filt3.execute();
        filt4.execute();
        filt5.execute();
        filt6.execute();
        filt7.execute();
        BufferedImage image1;
        BufferedImage image2;
        BufferedImage image3;
        BufferedImage image4;
        BufferedImage image5;
        BufferedImage image6;
        BufferedImage image7;

        try {
            image1 = filt1.get();
            image2 = filt2.get();
            image3 = filt3.get();
            image4 = filt4.get();
            image5 = filt5.get();
            image6 = filt6.get();
            image7 = filt7.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            image = null;
            image1 = null;
            image2 = null;
            image3 = null;
            image4 = null;
            image5 = null;
            image6 = null;
            image7 = null;

        }
        JFrame frame = new JFrame();
        JLabel img = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame.getContentPane().add(img,BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Original");
        frame.setVisible(true);
        
        test.addImage(image1);
        JFrame frame1 = new JFrame();
        JLabel img1 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame1.getContentPane().add(img1,BorderLayout.CENTER);
        frame1.pack();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setTitle("Blur");
        frame1.setVisible(true);
        
        test.addImage(image2);
        JFrame frame2 = new JFrame();
        JLabel img2 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame2.getContentPane().add(img2,BorderLayout.CENTER);
        frame2.pack();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setTitle("Sharpen");
        frame2.setVisible(true);
        
        test.addImage(image3);
        JFrame frame3 = new JFrame();
        JLabel img3 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame3.getContentPane().add(img3,BorderLayout.CENTER);
        frame3.pack();
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setTitle("Edge");
        frame3.setVisible(true);

        test.addImage(image4);
        JFrame frame4 = new JFrame();
        JLabel img4 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame4.getContentPane().add(img4,BorderLayout.CENTER);
        frame4.pack();
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.setTitle("Histogram");
        frame4.setVisible(true);
        
        test.addImage(image5);
        JFrame frame5 = new JFrame();
        JLabel img5 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame5.getContentPane().add(img5,BorderLayout.CENTER);
        frame5.pack();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.setTitle("Gray");
        frame5.setVisible(true);

        test.addImage(image6);
        JFrame frame6 = new JFrame();
        JLabel img6 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame6.getContentPane().add(img6,BorderLayout.CENTER);
        frame6.pack();
        frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame6.setTitle("Brightness");
        frame6.setVisible(true);
        
        test.addImage(image7);
        JFrame frame7 = new JFrame();
        JLabel img7 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame7.getContentPane().add(img7,BorderLayout.CENTER);
        frame7.pack();
        frame7.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame7.setTitle("Darken");
        frame7.setVisible(true);
    }
    public static BufferedImage addImage(){
        BufferedImage image = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("./imagefiles"));
        FileFilter filter = new FileNameExtensionFilter("Image","jpg","jpeg");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser);
        while(returnVal != JFileChooser.APPROVE_OPTION){
        }
        try {
            File sourceImage = new File(chooser.getSelectedFile().getPath());
            image = ImageIO.read(sourceImage);   
        }catch (IOException e){
            System.out.println("Error occured"+e);
        }
        return image;
    }

}
