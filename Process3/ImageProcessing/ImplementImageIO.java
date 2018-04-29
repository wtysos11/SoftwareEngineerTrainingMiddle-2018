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
	private static final int BYTE_HEAD=14;//bmp�ļ�ͷ0-13λ
	private static final int BYTE_INFO=40;//bmp��Ϣ����14-53λ
	
	private static final int MULTI_COLOR=24;//��ɫ��24λ
	private static final int GRAY=8;//�ҽף�8λ
	private static final int FOUR_BYTE=4;//���岻��
	
	private int bitInfo;//28-29 λͼͼ��λ��
	
	public Image myRead(String str) throws IOException
	{
		//System.out.println("Show path:"+str);
		FileInputStream inputStream = new FileInputStream(str);
		byte bmpHead[] = new byte[BYTE_HEAD];//bmp�ļ�ͷ
		byte bmpInfo[] = new byte[BYTE_INFO];//bmp�ļ���Ϣ
		byte originData[];
		int RGBdata[];
		
		int offset;//10-13 λͼ����ƫ����������ʼ��ַ
		int height;//22-25λͼ�ĸ߶�
		int width;//18-21 λͼ�Ŀ��
		int imageSize;//34-37 ԭʼ��λͼ��С

		int zero;//ÿ���п��ֽڴ�С
		Image image = null;
		
		try{
			//��ȡ�ļ�ͷ����Ϣ
			inputStream.read(bmpHead,0,BYTE_HEAD);
			inputStream.read(bmpInfo,0,BYTE_INFO);
			
			//offset����
			offset = byteChangeToInt(bmpHead[13],bmpHead[12],bmpHead[11],bmpHead[10]);
						
			//bitinfo ͼ��λ�����㣬һ��Ϊ24λ(��ɫ)��8λ
			bitInfo = (((int)bmpInfo[15] & 0xff)<<8)|((int)bmpInfo[14] & 0xff);
			
			//λͼ���
			width = byteChangeToInt(bmpInfo[7],bmpInfo[6],bmpInfo[5],bmpInfo[4]);
			//λͼ�߶�
			
			height = byteChangeToInt(bmpInfo[11],bmpInfo[10],bmpInfo[9],bmpInfo[8]);
			
			//����ԭʼ��С
			imageSize = byteChangeToInt(bmpInfo[23],bmpInfo[22],bmpInfo[21],bmpInfo[20]);
			
			zero = (imageSize/height) - width*3;
			originData = new byte[imageSize];
			
			//��ȡԭʼoriginData
			inputStream.read(originData,0,imageSize);
			RGBdata = new int[height*width];
			//��rgbֵ����ת��
			//ע�⣺ԭʼ�洢��ʽ���µ��ϣ������ң���Ҫ����ת��
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
            // ����ͼƬ
            BufferedImage bitImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            Graphics2D bGr = bitImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            // ���ļ�
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
	 * 4�ֽ�byteתint����λ��ǰ����λ�ں�
	 * ��Ϊʮ�ֳ��������˸�����
	 * */
	private int byteChangeToInt(byte byte1,byte byte2,byte byte3,byte byte4) {  
        int int1 = ((int)byte1&0xff)<<24;  
        int int2 = ((int)byte2&0xff)<<16;  
        int int3 = ((int)byte3&0xff)<<8;  
        int int4 = (int)byte4&0xff;  
        return int1|int2|int3|int4;  
	}
}