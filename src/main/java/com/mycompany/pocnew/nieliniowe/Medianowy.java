package com.mycompany.pocnew.nieliniowe;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class Medianowy extends Nieliniowy{
    
    @Override
    protected int engineForPixel(BufferedImage imgTemp, int X, int Y, int[][] maska, int color){
        int t = (maska.length-1)/2; 
        int s = t;
        ArrayList<Integer> arrayValuesPixels = new ArrayList<>();
        int med = 0;
        for(int i = -s;i <= s; i++){
            for(int j = -t,k = 0;j <= t;k++, j++){
                int clr = 0;
                if(color == 1)
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getRed();
                else if (color == 2)
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getGreen();
                else
                    clr = new Color(imgTemp.getRGB(X + i, Y + j)).getBlue();
                if(maska[j+t][i+t] == 1)
                    arrayValuesPixels.add(clr);	        	
            }
        }
        Collections.sort(arrayValuesPixels);
        med = arrayValuesPixels.get(arrayValuesPixels.size()/2);
        return ImageAlgorithms.clamp(med, 0, 255);
    }    
}
