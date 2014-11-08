
public class TestFilter {

	public static void main(String[] args) {
        Filter filt = new Filter();
        filt.setImgPath("C:/Users/Andrea/Documents/CSCI5828/JavaImage/imagetool.git/imagefiles/balloons.jpg");
        filt.setDestPath("C:/Users/Andrea/Documents/CSCI5828/JavaImage/imagetool.git/imagefiles/output.jpg");
        
        filt.sharpen();
        
        filt.setDestPath("C:/Users/Andrea/Documents/CSCI5828/JavaImage/imagetool.git/imagefiles/output2.jpg");
        filt.edgeDetect();
	}

}
