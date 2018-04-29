import java.awt.Image;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO; 
import imagereader.IImageIO;
import java.awt.Graphics2D;
public class ImplementImageIO implements IImageIO{
	private static final int BYTE_HEAD=14;//bmp文件头0-13位
	private static final int BYTE_INFO=40;//bmp信息部分14-53位
	
	private static final int MULTI_COLOR=24;//彩色，24位
	private static final int GRAY=8;//灰阶，8位
	private static final int FOUR_BYTE=4;//意义不明
	
	private int bitInfo;//28-29 位图图像位数
	
	public Image myRead(String str) throws IOException
	{
		//System.out.println("Show path:"+str);
		FileInputStream inputStream = new FileInputStream(str);
		byte bmpHead[] = new byte[BYTE_HEAD];//bmp文件头
		byte bmpInfo[] = new byte[BYTE_INFO];//bmp文件信息
		byte originData[];
		int RGBdata[];
		
		int offset;//10-13 位图数据偏移量，即起始地址
		int height;//22-25位图的高度
		int width;//18-21 位图的宽度
		int imageSize;//34-37 原始的位图大小

		int zero;//每序列空字节大小
		Image image = null;
		
		try{
			//读取文件头部信息
			inputStream.read(bmpHead,0,BYTE_HEAD);
			inputStream.read(bmpInfo,0,BYTE_INFO);
			
			//offset计算
			offset = byteChangeToInt(bmpHead[13],bmpHead[12],bmpHead[11],bmpHead[10]);
						
			//bitinfo 图像位数计算，一般为24位(彩色)或8位
			bitInfo = (((int)bmpInfo[15] & 0xff)<<8)|((int)bmpInfo[14] & 0xff);
			
			//位图宽度
			width = byteChangeToInt(bmpInfo[7],bmpInfo[6],bmpInfo[5],bmpInfo[4]);
			//位图高度
			
			height = byteChangeToInt(bmpInfo[11],bmpInfo[10],bmpInfo[9],bmpInfo[8]);
			
			//计算原始大小
			imageSize = byteChangeToInt(bmpInfo[23],bmpInfo[22],bmpInfo[21],bmpInfo[20]);
			
			zero = (imageSize/height) - width*3;
			originData = new byte[imageSize];
			
			//获取原始originData
			inputStream.read(originData,0,imageSize);
			RGBdata = new int[height*width];
			//对rgb值进行转换
			//注意：原始存储方式由下到上，由左到右，需要进行转换
			int index = offset - 54;		
			for(int j=0;j<height;j++)
			{
				for(int i=0;i<width;i++)
				{
					int realHeight = height-1-j;
					RGBdata[width*realHeight+i]= byteChangeToInt((byte)255,originData[index+2],originData[index+1],originData[index]);
					index+=3;
				}
				index+=zero;
			}
			image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width,height,RGBdata,0,width));
			inputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return image;
	}
	
	public Image myWrite(Image image, String file) throws IOException {
        try 
		{
            int height = image.getHeight(null);
            int width = image.getWidth(null);
            // 创建图片
            BufferedImage bitImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D bGr = bitImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            // 打开文件
            File newFile= new File(file + ".bmp");
            ImageIO.write(bitImage, "bmp", newFile);
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }

		return image;
	}
	/*
	 * 4字节byte转int，高位在前，低位在后
	 * 因为十分常见，做了个函数
	 * */
	private int byteChangeToInt(byte byte1,byte byte2,byte byte3,byte byte4) {  
        int int1 = ((int)byte1&0xff)<<24;  
        int int2 = ((int)byte2&0xff)<<16;  
        int int3 = ((int)byte3&0xff)<<8;  
        int int4 = (int)byte4&0xff;  
        return int1|int2|int3|int4;  
	}
}