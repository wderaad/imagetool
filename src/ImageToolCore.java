import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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
import javax.swing.JDialog;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.border.LineBorder;

import java.awt.GridLayout;
import java.awt.Component;


public class ImageToolCore {
    
    private ImageList model;
    private JFrame frmIPT;
    private JLabel displayImg;
    private JPanel displayImgB;
    private JComponent newContentPane;
    private JDialog pList;
    private DefaultListModel select;
    
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
        select = null;
        frmIPT = new JFrame();
        frmIPT.getContentPane().setBackground(new Color(119, 136, 153));
        frmIPT.setTitle("Image Processing Toolkit");
        frmIPT.setMinimumSize(new Dimension(300, 450));
        frmIPT.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmIPT.getContentPane().setLayout(
                new MigLayout("", "[135px,grow][grow][1px,grow]", "[22px][grow][87px,grow]"));
        
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
        

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(119, 136, 153));
        frmIPT.getContentPane().add(buttonPanel,
                "cell 0 1,growx,aligny top");

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
        btnFilterList.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //Create and set up the window.
                pList = new JDialog(frmIPT,"Create a process", true);
                newContentPane = new processInput();
                pList.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                newContentPane.setOpaque(true); //content panes must be opaque
                pList.setContentPane(newContentPane); 
                newContentPane.setMinimumSize(
                        new Dimension(
                                newContentPane.getPreferredSize().width,
                                100));
         
                //Display the window.
                pList.pack();
                pList.setLocationRelativeTo(frmIPT);
                pList.setVisible(true);                
            }
        });
        processPanel.add(btnFilterList);
        
        JButton btnEditFilterProcess = new JButton("Edit Filter Process");
        btnEditFilterProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                //Create and set up the window.
                pList = new JDialog(frmIPT,"Create a process", true);
                if(select == null){
                    newContentPane = new processInput();
                }else{
                    newContentPane = new processInput(select);
                }
                pList.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                newContentPane.setOpaque(true); //content panes must be opaque
                pList.setContentPane(newContentPane); 
                newContentPane.setMinimumSize(
                        new Dimension(
                                newContentPane.getPreferredSize().width,
                                100));
         
                //Display the window.
                pList.pack();
                pList.setLocationRelativeTo(frmIPT);
                pList.setVisible(true);                
            }
        });
        processPanel.add(btnEditFilterProcess);
        
        JButton btnRunFilterProcess = new JButton("Run Filter Process");
        btnRunFilterProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
           
                if(select == null || select.isEmpty()){
                    JOptionPane.showMessageDialog(frmIPT,
                            "Filter Process Required", "Create Filter Process", 
                            JOptionPane.INFORMATION_MESSAGE);
                }else{
                    if(!select.isEmpty()){
                        Object temp[] = select.toArray();
                        String it;
                        Queue<String> fProcess = new LinkedList<String>();
                        for(int i = 0; i < temp.length; i++){    
                            if(temp[i].toString() == "Equalize"){
                                it = "HISTOGRAM";
                            }else{
                                it = temp[i].toString().toUpperCase();
                            }
                            fProcess.add(it);
                        }
                        final Queue<String> tempProcess = fProcess;
                        final BufferedImage interm = model.getCurrentImage();

                        SwingWorker<BufferedImage,BufferedImage> worker = new
                                SwingWorker<BufferedImage,BufferedImage>(){
                            protected BufferedImage doInBackground()
                                    throws Exception{
                                BufferedImage current = interm;
                                while(!tempProcess.isEmpty()){
                                    Filter filter = new 
                                            Filter(current,tempProcess.poll());
                                    filter.execute();
                                    current = filter.get();
                                    publish(current);
                                }
                                return current;
                            }
                            protected void process(List<BufferedImage> chunks){
                                for (BufferedImage iter : chunks){
                                    model.addImage(iter);
                                    updateImage();
                                }
                            }
                        };
                        worker.execute();
                    }
                }
            }
        });
        processPanel.add(btnRunFilterProcess);
        
        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(new TitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null), "Apply Image Filter:",
                TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        filterPanel.setBackground(new Color(119, 136, 153));
        filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));
        
        final JButton btnSharpen = new JButton("Sharpen");
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
        
        final JButton btnEqualize = new JButton("Equalize");
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
        
        final JButton btnBlurImage = new JButton("Blur");
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
        
        final JButton btnBrightenImage = new JButton("Brighten");
        btnBrightenImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "BRIGHTEN"){
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
        
        final JButton btnDarkenImage = new JButton("Darken");
        btnDarkenImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "DARKEN"){
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
        
        final JButton btnEdge = new JButton("Detect Edges");
        btnEdge.setEnabled(false);
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
        
        JButton btnGray = new JButton("Grayscale");
        btnGray.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                btnDarkenImage.setEnabled(false);
                btnBrightenImage.setEnabled(false);
                btnBlurImage.setEnabled(false);
                btnEqualize.setEnabled(false);
                btnSharpen.setEnabled(false);
                BufferedImage interm = model.getCurrentImage();
                Filter filt = new Filter(interm, "GRAYSCALE"){
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
                btnEdge.setEnabled(true);
            }
        });
        filterPanel.add(btnGray);
        filterPanel.add(btnEdge);

        JButton btnReset = new JButton("Reset");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.resetList();
                updateImage();
                btnDarkenImage.setEnabled(true);
                btnBrightenImage.setEnabled(true);
                btnBlurImage.setEnabled(true);
                btnEqualize.setEnabled(true);
                btnSharpen.setEnabled(true);
                btnEdge.setEnabled(false);
            }
        });
        toolBar.add(btnReset);

        
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.anchor = GridBagConstraints.NORTHWEST;
        gbc_panel.gridx = 1;
        gbc_panel.gridy = 0;
        buttonPanel.add(filterPanel, gbc_panel);
        initializeExample();
        
        displayImgB = new JPanel();
        frmIPT.getContentPane().add(displayImgB, "cell 1 1");
        displayImgB.setAlignmentY(Component.TOP_ALIGNMENT);
        displayImgB.setAlignmentX(Component.LEFT_ALIGNMENT);
        displayImgB.setForeground(new Color(0, 0, 0));
        displayImgB.setBackground(new Color(119, 136, 153));
        displayImgB.setBorder(new TitledBorder(new EtchedBorder(
                EtchedBorder.LOWERED, null, null), "Current Image",
                TitledBorder.CENTER, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        displayImgB.setLayout(new MigLayout("", "[1px]", "[87px]"));
        
        
        displayImg = new JLabel();
        displayImg.setIcon(new ImageIcon(model.getCurrentImage()));
        
        displayImgB.add(displayImg, "cell 0 0,alignx right,aligny center");
        
        JPanel navPanel = new JPanel();
        frmIPT.getContentPane().add(navPanel, "cell 1 2,alignx center,aligny top");
        navPanel.setBackground(new Color(119, 136, 153));
        navPanel.setBorder(null);
        navPanel.setLayout(new GridBagLayout());
        
        JButton btnPrevious = new JButton("Previous");
        btnPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(model.isPrev()){
                    model.setPrev();
                    updateImage();
                }
            }
        });
        GridBagConstraints gbc_btnPrevious = new GridBagConstraints();
        gbc_btnPrevious.anchor = GridBagConstraints.CENTER;
        gbc_btnPrevious.insets = new Insets(0, 0, 0, 5);
        gbc_btnPrevious.gridx = 0;
        gbc_btnPrevious.gridy = 0;
        navPanel.add(btnPrevious, gbc_btnPrevious);
        
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(model.isNext()){
                    model.setNext();
                    updateImage();
                }
            }
        });
        GridBagConstraints gbc_btnNext = new GridBagConstraints();
        gbc_btnNext.anchor = GridBagConstraints.CENTER;
        gbc_btnNext.gridx = 1;
        gbc_btnNext.gridy = 0;
        navPanel.add(btnNext, gbc_btnNext);
        frmIPT.pack();
        frmIPT.setLocationRelativeTo(null);

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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void initializeExample(){
        BufferedImage img = null;
        try {
            File sourceImage = new File("imagefiles/lake.jpg");
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

    public class processInput extends JPanel 
    implements ListSelectionListener {
        private JList list;
        private JList process;
        private DefaultListModel listModel;
        private DefaultListModel processModel;

        private static final String addString = "Add";
        private static final String deleteString = "Delete";
        private static final String doneString = "Done";


        private JButton addButton;
        private JButton deleteButton;
        private JButton doneButton;

        public processInput(DefaultListModel in) {
            this();
            while(!in.isEmpty()){
                processModel.addElement(in.remove(0));                
            }
            deleteButton.setEnabled(true);
        }

        public processInput() {
            super(new BorderLayout());

            //Create and populate the list model.
            listModel = new DefaultListModel();
            processModel = new DefaultListModel();

            listModel.addElement("Sharpen");
            listModel.addElement("Equalize");
            listModel.addElement("Blur");
            listModel.addElement("Brighten");
            listModel.addElement("Darken");
            listModel.addElement("Grayscale");
            listModel.addElement("Edge");

            processModel.addListDataListener(new MyListDataListener());

            //Create the list and put it in a scroll pane.
            list = new JList(listModel);
            list.setSelectionMode(
                    ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            list.setSelectedIndex(0);
            list.addListSelectionListener(this);
            JScrollPane listScrollPane = new JScrollPane(list);

            process = new JList(processModel);
            process.setSelectionMode(
                    ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            process.setSelectedIndex(0);
            process.addListSelectionListener(this);
            JScrollPane processScrollPane = new JScrollPane(process);

            addButton = new JButton(addString);
            addButton.setActionCommand(addString);
            addButton.addActionListener(new AddButtonListener());

            deleteButton = new JButton(deleteString);
            deleteButton.setActionCommand(deleteString);
            deleteButton.addActionListener(
                    new DeleteButtonListener());

            doneButton = new JButton(doneString);
            doneButton.setActionCommand(doneString);
            doneButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    select = processModel;
                    pList.dispose();
                }
            });

            JPanel buttonPane = new JPanel();
            buttonPane.add(addButton);
            buttonPane.add(deleteButton);
            buttonPane.add(doneButton);
            deleteButton.setEnabled(false);

            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                    listScrollPane, processScrollPane);
            splitPane.setResizeWeight(0.5);

            add(buttonPane, BorderLayout.PAGE_START);
            add(splitPane, BorderLayout.CENTER);
        }
        
        class MyListDataListener implements ListDataListener {
            public void contentsChanged(ListDataEvent e) {

            }
            public void intervalAdded(ListDataEvent e) {

            }
            public void intervalRemoved(ListDataEvent e) {

            }
        }
        class DeleteButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {


                ListSelectionModel lsm = process.getSelectionModel();
                int firstSelected = lsm.getMinSelectionIndex();
                int lastSelected = lsm.getMaxSelectionIndex();
                processModel.removeRange(firstSelected, lastSelected);

                int size = processModel.size();

                if (size == 0) {
                    //List is empty: disable delete
                    deleteButton.setEnabled(false);
                } else {
                    deleteButton.setEnabled(true);

                }
            }
        }


        /** A listener shared by the text field and add button. */
        class AddButtonListener implements ActionListener {
            public void actionPerformed(ActionEvent e) { 
                ListSelectionModel lsm = list.getSelectionModel();
                int firstSelected = lsm.getMinSelectionIndex();
                processModel.addElement(listModel.get(firstSelected));

                int size = processModel.size();

                if (size == 0) {
                    //List is empty: disable delete
                    deleteButton.setEnabled(false);
                } else {
                    deleteButton.setEnabled(true);
                }
            }
        }
        //Listener method for list selection changes.
        public void valueChanged(ListSelectionEvent e) {
            if (e.getValueIsAdjusting() == false) {

            }
        }
        
    }
}
