import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageList {

    private CopyOnWriteArrayList<BufferedImage> imgList = 
            new CopyOnWriteArrayList<BufferedImage>();
    private AtomicInteger current; 

    public ImageList(BufferedImage img) {
        super();
        imgList.add(img);
        current = new AtomicInteger();
    }

    public BufferedImage getCurrentImage() {
        return imgList.get(current.get());
    }

    public void addImage(BufferedImage img) {
        imgList.add(img);
        current.incrementAndGet();
    }
    public void resetList(){
        imgList = new CopyOnWriteArrayList<BufferedImage>(
                imgList.subList(0, 0));
        current.set(0);
    }
    public boolean isNext(){
        if(current.get() < imgList.size()-1){
            return true;
        }
        return false;
    }
    public boolean isPrev(){
        if(current.get() > 0){
            return true;
        }
        return false;
    }
    public int setPrev(){
        int val = current.decrementAndGet();
        return val;
    }
    public int setNext(){
        int val = current.incrementAndGet();
        return val;
    }
}
