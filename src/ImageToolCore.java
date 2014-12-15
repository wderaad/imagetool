import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractAction;
import javax.swing.Action;


public class ImageToolCore {
    
    private ImageList model;
    private JFrame frmIPT;
    private JLabel displayImg;
    private JPanel displayImgB;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ImageToolCore window = new ImageToolCore();
                    window.frmIPT.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ImageToolCore() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmIPT = new JFrame();
        frmIPT.getContentPane().setBackground(new Color(119, 136, 153));
        frmIPT.setTitle("Image Processing Toolkit");
        frmIPT.setMinimumSize(new Dimension(300, 450));
        frmIPT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmIPT.getContentPane().setLayout(
                new MigLayout("", "[135px,grow][1px]", "[22px][grow][87px]"));
        
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(new Color(112, 128, 144));
        frmIPT.getContentPane().add(toolBar, "cell 0 0,alignx left,aligny top");
        
        JButton btnOpen = new JButton("Open");
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openImage();
                frmIPT.pack();
            }
        });
        toolBar.add(btnOpen);
        
        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });
        toolBar.add(btnSave);
        
        JButton btnClose = new JButton("Close");
        toolBar.add(btnClose);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(119, 136, 153));
        frmIPT.getContentPane().add(buttonPanel,
                "cell 0 1,growx,aligny top");
        GridBagLayout gbl_bPanel = new GridBagLayout();
        gbl_bPanel.columnWidths = new int[]{22, 199, 0};
        gbl_bPanel.rowHeights = new int[]{202, 0};
        gbl_bPanel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        gbl_bPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        JPanel processPanel = new JPanel();
        processPanel.setBackground(new Color(119, 136, 153));
        processPanel.setBorder(new TitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null), "Manage Filtering Process:",
                TitledBorder.CENTER, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        GridBagConstraints gbc_processPanel = new GridBagConstraints();
        gbc_processPanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_processPanel.insets = new Insets(0, 0, 0, 5);
        gbc_processPanel.gridx = 0;
        gbc_processPanel.gridy = 0;
        buttonPanel.add(processPanel, gbc_processPanel);
        processPanel.setLayout(new BoxLayout(processPanel, BoxLayout.Y_AXIS));
        
        JButton btnFilterList = new JButton("Create Filter Process");
        processPanel.add(btnFilterList);
        
        JButton btnEditFilterProcess = new JButton("Edit Filter Process");
        processPanel.add(btnEditFilterProcess);
        
        JButton btnRunFilterProcess = new JButton("Run Filter Process");
        processPanel.add(btnRunFilterProcess);
        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null), "Apply Image Filter:",
                TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        filterPanel.setBackground(new Color(119, 136, 153));
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        
        JButton btnSharpen = new JButton("Sharpen");
        btnSharpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "SHARPEN"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnSharpen);
        
        JButton btnEqualize = new JButton("Equalize");
        btnEqualize.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "HISTOGRAM"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnEqualize);
        
        JButton btnBlurImage = new JButton("Blur");
        btnBlurImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "BLUR"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnBlurImage);
        
        JButton btnBrightenImage = new JButton("Brighten");
        btnBrightenImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "BRIGHT"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnBrightenImage);
        
        JButton btnDarkenImage = new JButton("Darken");
        btnDarkenImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "DARK"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnDarkenImage);
        
        JButton btnGray = new JButton("Grayscale");
        btnGray.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "GRAY"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnGray);
        
        JButton btnEdge = new JButton("Detect Edges");
        btnEdge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "EDGE"){
                    protected void done(){
                        try {
                            BufferedImage res = get();
                            model.addImage(res);
                            updateImage();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                filt.execute();
            }
        });
        filterPanel.add(btnEdge);
        
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTHWEST;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        buttonPanel.add(filterPanel, gbc_panel);
        initializeExample();
        
        displayImgB = new JPanel();
        displayImgB.setForeground(new Color(0, 0, 0));
        displayImgB.setBackground(new Color(119, 136, 153));
        displayImgB.setBorder(new TitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null), "Example Image",
                TitledBorder.CENTER, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        frmIPT.getContentPane().add(
                displayImgB, "cell 1 1,grow");
        displayImgB.setLayout(new MigLayout("", "[1px]", "[87px]"));
        
        
        displayImg = new JLabel();
        displayImg.setIcon(new ImageIcon(model.getCurrentImage()));
        
        displayImgB.add(displayImg, "cell 0 0,alignx right,aligny center");
        frmIPT.pack();
    }
    
    private void openImage(){
        BufferedImage image = null;
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new java.io.File("./imagefiles"));
        FileFilter filter = new FileNameExtensionFilter(
                "Image","jpg","jpeg","JPG","JPEG","png","PNG");
        fc.setFileFilter(filter);
        int ret = fc.showOpenDialog(frmIPT);
        if (ret == JFileChooser.APPROVE_OPTION) {
            try {
                File sourceImage = new File(fc.getSelectedFile().getPath());
                image = ImageIO.read(sourceImage); 
                initializeModel(image);
                updateImage();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    private void saveImage(){
        BufferedImage imSave = model.getCurrentImage();
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File("./imagefiles"));
        fc.setSelectedFile(new File("NewImage.png"));
        int ret = fc.showSaveDialog(frmIPT);
        if (ret == JFileChooser.APPROVE_OPTION) {
            try{
                ImageIO.write(imSave, "png", fc.getSelectedFile());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void initializeExample(){
        BufferedImage img = null;
        try {
            File sourceImage = new File("imagefiles/river.jpg");
            img = ImageIO.read(sourceImage);   
        }catch (IOException e){
            System.out.println("Error occured"+e);
        }
        initializeModel(img);
    }
    
    private void initializeModel(BufferedImage img){
        model = new ImageList(img);

    }
    
    private void updateImage(){
        displayImg.setIcon(new ImageIcon(model.getCurrentImage()));
        displayImgB.setVisible(true);
    }
}
