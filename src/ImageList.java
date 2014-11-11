import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageList {

    private List<BufferedImage> imgList = new ArrayList<BufferedImage>();
    private int current; 

    public ImageList(BufferedImage img) {
        super();
        this.imgList.add(img);
        current = 0;
    }

    public BufferedImage getCurrentImage() {
        return this.imgList.get(current);
    }

    public void addImage(BufferedImage img) {
        this.imgList.add(img);
        this.current++;
    }
    
    public void resetList(){
        this.imgList = this.imgList.subList(0, 0);
        this.current = 0;
    }

}
