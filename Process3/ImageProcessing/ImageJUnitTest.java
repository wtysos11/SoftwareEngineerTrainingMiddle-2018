import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.FileInputStream;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.ImageProducer;
import javax.imageio.ImageIO;

public class ImageJUnitTest {

	private final String BaseDir = "C:\\Users\\wty\\Documents\\bmptest\\";
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRed1() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "1.bmp");
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelR(originImage);
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\1_red_goal.bmp"));
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testGreen1() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "1.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelG(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\1_green_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testBlue1() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "1.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelB(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\1_blue_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testGray1() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "1.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showGray(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\1_gray_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	@Test
	public void testRed2() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "2.bmp");
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelR(originImage);
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\2_red_goal.bmp"));
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testGreen2() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "2.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelG(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\2_green_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testBlue2() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "2.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showChanelB(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\2_blue_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
	
	@Test
	public void testGray2() throws IOException{
		ImplementImageIO implementImage = new ImplementImageIO();
		Image originImage = implementImage.myRead(BaseDir + "2.bmp");//relative to file
		ImplementImageProcessor imageProcessor = new ImplementImageProcessor();
		Image processedImage = imageProcessor.showGray(originImage);//relative to color
		
		int w = processedImage.getWidth(null);
		int d = processedImage.getHeight(null);
		BufferedImage testImage = new BufferedImage(w,d,BufferedImage.TYPE_INT_BGR);
        Graphics2D bGr = testImage.createGraphics();
        bGr.drawImage(processedImage, 0, 0, null);
        bGr.dispose();
		
		BufferedImage goalImage = ImageIO.read(new File(BaseDir+"goal\\2_gray_goal.bmp"));//relative to answer
		
		assertEquals(goalImage.getWidth(null), testImage.getWidth(null));
		assertEquals(goalImage.getHeight(null), testImage.getHeight(null));
		
		for (int i = 0; i < goalImage.getWidth(null); i++)
		{
			for (int j = 0; j < goalImage.getHeight(null); j++)
			{
				assertEquals(testImage.getRGB(i, j), goalImage.getRGB(i, j));
			}
				
		}
	}
}
