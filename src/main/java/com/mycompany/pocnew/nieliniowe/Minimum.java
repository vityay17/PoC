package com.mycompany.pocnew.nieliniowe;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Minimum extends Nieliniowy{

    /*
     * @param int color - 1 if red, 2 if green or 3 if Blue
     */
    @Override
    int engineForPixel(BufferedImage imgTemp, int X, int Y, int[][] maska, int color){
        int t = (maska.length - 1)/2; 
        int s = t;
        int min = 255;
        for(int i = -s; i <= s; i++){
            for(int j = -t;j <= t; j++){
                int clr = 0;
                if(color == 1)
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getRed();
                else if (color == 2)
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getGreen();
                else		
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getBlue();
                if(maska[j+t][i+t] == 1)
                    if(min>=clr)
                        min = clr;
            }
        }
        return ImageAlgorithms.clamp(min,0,255);
    }
}
