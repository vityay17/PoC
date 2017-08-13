package com.mycompany.pocnew.nieliniowe;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import java.awt.Color;
import java.awt.image.BufferedImage;

public abstract class Nieliniowy {
    
    public BufferedImage run(int[][] maska, BufferedImage img){
        return runEngine(maska, img);
    }
	
    public BufferedImage runEngine(int[][] maska, BufferedImage imgTemp){
        int radius = (maska.length - 1)/2;
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
    
    int engineForPixel(BufferedImage imgTemp, int X, int Y, int[][] maska, int color){
        return 0;
};
}
