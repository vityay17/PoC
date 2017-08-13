package com.mycompany.pocnew.gauss;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import com.mycompany.pocnew.POC;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class GaussService {
    
    POC parent = null;

    public GaussService(POC parent) {
        this.parent = parent;
    }
    
    public BufferedImage runEngine(int[][] maska, BufferedImage imgTemp){	    
        int radius = (maska.length-1)/2;
        BufferedImage imgWithMirroredSides = ImageAlgorithms.createImageWithMirroredSides(imgTemp,radius);
            for(int y = 0;y < imgTemp.getHeight(); y++){
                for(int x = 0;x < imgTemp.getWidth(); ++x){
                        int r = engineForPixel(imgWithMirroredSides, x + radius, y + radius, maska, 1);//1 means Red
                        int g = engineForPixel(imgWithMirroredSides, x + radius, y + radius, maska, 2);//2 means Green
                        int b = engineForPixel(imgWithMirroredSides, x + radius, y + radius, maska, 3);//3 means Blue
                        imgTemp.setRGB(x,y,new Color(r,g,b).getRGB());
                }
            }
            return imgTemp;
    }
    
    /*
    * @param int color - 1 if red, 2 if green or 3 if Blue
    */
    private int engineForPixel(BufferedImage imgTemp, int X, int Y, int[][] maska, int color){
        int t = (maska.length - 1)/2; 
        int s = t;
        int sumMaska = 0;
        for(int i = 0;i < maska.length; i++){
            for(int j = 0;j < maska.length; j++){
                sumMaska += maska[i][j];
            }
        }
        if(sumMaska == 0) 
            sumMaska = 1;
        int tempResult = 0;
        for(int i = -s;i <= s; i++){
            for(int j = -t;j <= t; j++){
                int clr = 0;
                if(color == 1) 			
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getRed();
                else if (color == 2) 	
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getGreen();
                else 					
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getBlue();
                tempResult += maska[t+i][t+j] * clr;
            }
        }
        return ImageAlgorithms.clamp(tempResult/sumMaska, 0, 255);
    }
    
    public BufferedImage calculateUnsharpMask(BufferedImage imgWithGauss, int afi){
        int nHeight = imgWithGauss.getHeight();
        int nWidth = imgWithGauss.getWidth();
        int[][] gmask = new int[nWidth][nHeight];
        
        //calculate difference between original value color and Gauss value
        //and set it to array[x][y]
        for(int y = 0;y < nHeight; y++){
            for(int x = 0;x < nWidth; ++x){
                Color color = new Color(parent.originalImage.getRGB(x, y));
                int red = color.getRed();;
                int green = color.getGreen();
                int blue = color.getBlue();
                int GreyOrig = (red+green+blue)/3;

                color = new Color(imgWithGauss.getRGB(x, y));
                red = color.getRed();;
                green = color.getGreen();
                blue = color.getBlue();
                int GreyGauss = (red+green+blue)/3;

                gmask[x][y] = GreyOrig - GreyGauss;
            }
        }
        for(int y = 0;y < nHeight; y++){
            for(int x = 0;x < nWidth; ++x){
                Color color = new Color(parent.originalImage.getRGB(x, y));
                int gm = afi * gmask[x][y] ;

                int red = color.getRed() + gm;
                int green = color.getGreen() + gm;
                int blue = color.getBlue() + gm;

                red = ImageAlgorithms.clamp(red,0,255);
                green = ImageAlgorithms.clamp(green,0,255);
                blue = ImageAlgorithms.clamp(blue,0,255);

                imgWithGauss.setRGB(x,y,new Color(red,green,blue).getRGB());
            }
        }
		
        return imgWithGauss;
    }
}
