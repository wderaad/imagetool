import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

public class ImageList {

    private List<Image> imgList = new ArrayList<Image>();
    private int current; 

    public ImageList(Image img) {
        super();
        this.imgList.add(img);
        current = 0;
    }

    public Image getCurrentImage() {
        return this.imgList.get(current);
    }

    public void addImage(Image img) {
        this.imgList.add(img);
        this.current++;
    }
    
    public void resetList(){
        this.imgList = this.imgList.subList(0, 0);
        this.current = 0;
    }

}
