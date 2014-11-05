import java.awt.BorderLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
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
        Image image1 = addImage();
        ImageList test = new ImageList(image1);
        //Image image2 = addImage();
        //test.addImage(image2);
        JFrame frame = new JFrame();
        JLabel img = new JLabel(new ImageIcon(test.getCurrentImage()));
        frame.getContentPane().add(img,BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
    public static Image addImage(){
        Image image = null;
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
