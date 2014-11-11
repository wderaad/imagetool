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
        filt1.execute();
        Filter filt2 = new Filter(image, "SHARPEN");
        filt1.execute();
        Filter filt3 = new Filter(image, "EDGE");
        filt1.execute();
        filt2.execute();
        filt3.execute();
        BufferedImage image1;
        BufferedImage image2;
        BufferedImage image3;

        try {
            image1 = filt1.get();
            image2 = filt2.get();
            image3 = filt3.get();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            image = null;
            image1 = null;
            image2 = null;
            image3 = null;

        }
        JFrame frame = new JFrame();
        JLabel img = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame.getContentPane().add(img,BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        test.addImage(image1);
        JFrame frame1 = new JFrame();
        JLabel img1 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame1.getContentPane().add(img1,BorderLayout.CENTER);
        frame1.pack();
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setVisible(true);
        
        test.addImage(image2);
        JFrame frame2 = new JFrame();
        JLabel img2 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame2.getContentPane().add(img2,BorderLayout.CENTER);
        frame2.pack();
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setVisible(true);
        
        test.addImage(image3);
        JFrame frame3 = new JFrame();
        JLabel img3 = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame3.getContentPane().add(img3,BorderLayout.CENTER);
        frame3.pack();
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setVisible(true);
        

        
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
