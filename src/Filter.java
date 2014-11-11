import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import org.opencv.core.Size;
import java.awt.image.*;

import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Filter extends SwingWorker<BufferedImage,BufferedImage>{
    
    private BufferedImage image;
    private String filterT;
    public Filter(BufferedImage img, String type) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        this.image = img;
        this.filterT = type;
    }
    public enum FilterType{
        BLUR, SHARPEN, EDGE, HISTOGRAM 
    }
    
    protected BufferedImage doInBackground () throws IOException {
                
                BufferedImage out;
                FilterType selection = FilterType.valueOf(filterT);
                switch (selection) {
                    case BLUR:
                        out = this.blur();
                        break;
                    case SHARPEN:
                        out = this.sharpen();
                        break;
                    case EDGE:
                        out = this.edge();
                        break;
                    case HISTOGRAM:
                        out = this.histogram();
                        break;
                    default:
                        out = null;
                        break;
                }
                return out;
            }
    
    
    private BufferedImage blur(){
        byte[] resultPixels;
        Mat destination = new Mat();
        BufferedImage result;
        try {
            double width = image.getWidth();
            double height = image.getHeight();
            Mat source = new Mat(new Size(width,height), CvType.CV_8UC3); 
            MatOfByte resultBytes = new MatOfByte();
            byte[] sourcePixels = ((DataBufferByte)
                    image.getRaster().getDataBuffer()).getData();
            source.put(0, 0, sourcePixels);
            destination = new Mat(source.rows(),source.cols(),source.type());
            Imgproc.GaussianBlur(source, destination,new Size(25,25), 0);
            Highgui.imencode(".png", destination, resultBytes);
            resultPixels = resultBytes.toArray();
            } catch (Exception e) {
                System.out.println("Blurring Filter Error " + e.getMessage());
                destination  = null;
                resultPixels = null;            
                }
        InputStream buffer = new ByteArrayInputStream(resultPixels);
        try{result = ImageIO.read(buffer);}
        catch(Exception e){
            System.out.println("Sharpen IO: "+e.getMessage());
            result = null;
            }
        return result;
    }
    
    private BufferedImage sharpen() {
        byte[] resultPixels;
        Mat destination = new Mat();
        BufferedImage result;
        try{
            double width = image.getWidth();
            double height = image.getHeight();
            Mat source = new Mat(new Size(width,height), CvType.CV_8UC3);
            MatOfByte resultBytes = new MatOfByte();
            byte[] sourcePixels = ((DataBufferByte)
                                  image.getRaster().getDataBuffer()).getData();

            source.put(0, 0, sourcePixels);
            destination = new Mat(source.rows(),source.cols(),source.type());
            Imgproc.GaussianBlur(source, destination, new Size(0,0), 10);
            Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);
            Highgui.imencode(".png", destination, resultBytes);
            resultPixels = resultBytes.toArray();
        }catch (Exception e) {
        	System.out.println("Sharpening Filter Error " + e.getMessage());
            destination  = null;
            resultPixels = null;
        }
        InputStream buffer = new ByteArrayInputStream(resultPixels);
        try{result = ImageIO.read(buffer);}
        catch(Exception e){
            System.out.println("Sharpen IO: "+e.getMessage());
            result = null;
            }
        return result;

    }
    
    private BufferedImage edge(){
        byte[] resultPixels;
        Mat destination = new Mat();
        BufferedImage result;
        try{
            double width = image.getWidth();
            double height = image.getHeight();
            Mat source = new Mat(new Size(width,height), CvType.CV_8UC3);
            MatOfByte resultBytes = new MatOfByte();
            byte[] sourcePixels = ((DataBufferByte)
                                  image.getRaster().getDataBuffer()).getData();
            source.put(0, 0, sourcePixels);
    	    destination = new Mat(source.rows(),source.cols(),source.type());
    	    int kernelSize = 3;
    	    Mat kernel = new Mat(kernelSize,kernelSize, CvType.CV_32F){
    	        {
    	        	put(1,2,3);
    	            put(1,1,1);
    	            put(1,2,3);

                    put(-2,-3,-2);
    	            put(0,0,0);
    	            put(2,3,2);
    	          
    	            put(-2,0,2);
    	            put(-3,0,3);
    	            put(-2,0,2);
    	        }
    	    };	      
    	    Imgproc.filter2D(source, destination, -1, kernel);
    	    Core.addWeighted(source, 1.5, destination, -0.5, 0, destination);
            Highgui.imencode(".png", destination, resultBytes);
            resultPixels = resultBytes.toArray();
        }catch (Exception e) {
            System.out.println("Edge Filter Error " + e.getMessage());
            destination  = null;
            resultPixels = null;
        }
        InputStream buffer = new ByteArrayInputStream(resultPixels);
        try{result = ImageIO.read(buffer);}
        catch(Exception e){
            System.out.println("Sharpen IO: "+e.getMessage());
            result = null;
            }
        return result;

    }
        
    
    private BufferedImage histogram(){
        return image;
    }

}//End Filter Class