import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Size;


public class Filter {
    private String imgpath;
    private String destpath;
    
    public Filter( ) {
        imgpath = "";
        destpath = "";
    }
    
    public void setImgPath( String path ) {
    	imgpath = path;    	
    }
    
    public void setDestPath( String path ) {
    	destpath = path;
    }
    
    public void sharpen() {
        try{
            System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
            Mat source = Highgui.imread(imgpath,Highgui.CV_LOAD_IMAGE_COLOR);
            Mat destination = new Mat(source.rows(),source.cols(),source.type());
            Imgproc.GaussianBlur(source, destination, new Size(0,0), 10);
            Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);
            Highgui.imwrite(destpath, destination);
        }catch (Exception e) {
        	System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void edgeDetect() {
    	try {
            int kernelSize = 3;
    	    System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	    Mat source = Highgui.imread(imgpath,Highgui.CV_LOAD_IMAGE_GRAYSCALE);
    	    Mat destination = new Mat(source.rows(),source.cols(),source.type());
    	    Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F){
    	        {
    	    	    //Use top three for light objects like balloons.jpg
    	        	put(1,2,3);
    	            put(1,1,1);
    	            put(1,2,3);

                    put(-2,-3,-2);
    	            put(0,0,0);
    	            put(2,3,2);
    	          
    	            put(-2,0,2);
    	            put(-3,0,3);
    	            put(-2,0,2);
    	            
    	            //Use below ones for the darker images like boycolor.jpg
    	            /*put(1,2,3);
    	            put(1,1,1);
    	            put(1,2,3);

    	            put(2,3,2);
    	            put(0,0,0);
    	            put(-2,-3,-2);
    	          
    	            put(2,0,-2);
    	            put(3,0,-3);
    	            put(2,0,-2);
    	            */
    	        }
    	    };	      
    	    Imgproc.filter2D(source, destination, -1, kernel);
    	    Highgui.imwrite(destpath, destination);
    	} catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
    	}

    }
}