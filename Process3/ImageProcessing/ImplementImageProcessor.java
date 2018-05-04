import imagereader.IImageProcessor;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.RGBImageFilter;
import java.awt.image.ImageProducer;

public class ImplementImageProcessor implements IImageProcessor {

	//rgb值构成 ff + r + g + b
    class RedImageFilter extends RGBImageFilter {
        public final int filterRGB(final int x, final int y, final int rgb) {
            return rgb & 0xffff0000;
        }
    }

	public Image showChanelR(Image image) {
		RedImageFilter redFilter = new RedImageFilter();
		ImageProducer ip = new FilteredImageSource(image.getSource(), redFilter);
        return Toolkit.getDefaultToolkit().createImage(ip);
	}

    class GreenImageFilter extends RGBImageFilter {
        public final int filterRGB(final int x, final int y, final int rgb) {
            return rgb & 0xff00ff00;
        }
    }

	public Image showChanelG(Image image) {
		GreenImageFilter greenFilter = new GreenImageFilter();
		ImageProducer ip = new FilteredImageSource(image.getSource(), greenFilter);
        return Toolkit.getDefaultToolkit().createImage(ip);	
    }

    class BlueImageFilter extends RGBImageFilter {
        public final int filterRGB(final int x, final int y, final int rgb) {
            return rgb & 0xff0000ff;
        }
    }

	public Image showChanelB(Image image) {
		BlueImageFilter blueFilter = new BlueImageFilter();
		ImageProducer ip = new FilteredImageSource(image.getSource(), blueFilter);
        return Toolkit.getDefaultToolkit().createImage(ip);		
    }

    class GrayImageFilter extends RGBImageFilter {
        public int filterRGB(int x, int y, int rgb) {
            int red = (rgb & 0x00ff0000) >> 16;
            int green = (rgb & 0x0000ff00) >> 8;
            int blue = (rgb & 0x000000ff);
            int gray = (int)(0.299 * red + 0.587 * green + 0.114 * blue);
            return (rgb & 0xff000000)+(gray << 16)+(gray << 8) + gray;
        }
    }

	public Image showGray(Image image) {
		GrayImageFilter grayFilter = new GrayImageFilter();
		ImageProducer ip = new FilteredImageSource(image.getSource(), grayFilter);
        return Toolkit.getDefaultToolkit().createImage(ip);	
	}
}
