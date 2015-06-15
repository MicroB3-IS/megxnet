package net.megx.esa.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageResizer {

	public byte[] resizeImage(byte[] imageBytes, int width, int height) throws IOException{
		BufferedImage inputImage=ImageIO.read(new ByteArrayInputStream(imageBytes));
		int inputWidth=inputImage.getWidth();
		int inputHeight=inputImage.getHeight();
		double s1=(width*1.0)/(inputWidth*1.00);
		double s2=(height*1.0)/(inputHeight*1.00);
		double s=s1<s2?s1:s2;
		int outputWidth=(int)(s*inputWidth);
		int outputHeight=(int)(s*inputHeight);
		BufferedImage outputImage=new BufferedImage(outputWidth,outputHeight,BufferedImage.TYPE_INT_ARGB);
		AffineTransform scaleTransform=new AffineTransform();
		scaleTransform.scale(s,s);
		AffineTransformOp scaleOperation = 
				   new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
		outputImage=scaleOperation.filter(inputImage, null);
		if(outputWidth>width || outputHeight>height){
			int x=outputWidth>width? (outputWidth-width)/2:0;
			int y=outputHeight>height? (outputHeight-height)/2:0;
			outputImage=outputImage.getSubimage(x, y, width,height);
		}
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ImageIO.write(outputImage,"png", baos);
		return baos.toByteArray();
	}
}
