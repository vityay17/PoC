package com.mycompany.pocnew.shared;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class LAB {
    double[][] labL;
    double[][] labA;
    double[][] labB;

    public double[][] getLabL() {
        return labL;
    }

    public double[][] getLabA() {
        return labA;
    }

    public double[][] getLabB() {
        return labB;
    }

    public void rgbToLab(BufferedImage img){
        int height = img.getHeight();
        int width = img.getWidth();
        labL = new double[height][width];
        labA = new double[height][width];
        labB = new double[height][width];
        for(int y = 0;y < height; y++){
            for(int x = 0;x < width; ++x){
                Color color = new Color(img.getRGB(x, y));
                int r = color.getRed();
                int g = color.getGreen();
                int b = color.getBlue();
                double[] XYZ = ImageAlgorithms.RGBtoXYZ(r, g, b);
                double[] LAB = XYZtoLAB(XYZ[0], XYZ[1], XYZ[2]);
                labL[y][x] = LAB[0];
                labA[y][x] = LAB[1];
                labB[y][x] = LAB[2];
            }
         }
    }
        
    double[] XYZtoLAB(double X, double Y, double Z){
        double ref_X =  95.047;
        double ref_Y = 100.000;
        double ref_Z = 108.883;
        double var_X = X / ref_X;          //ref_X =  95.047   Observer= 2°, Illuminant= D65
        double var_Y = Y / ref_Y;          //ref_Y = 100.000
        double var_Z = Z / ref_Z;          //ref_Z = 108.883
        
        if(var_X > 0.008856)
            var_X = Math.pow(var_X, (double)1/3);
        else
            var_X = ( 7.787 * var_X ) + ( (double)16 / 116 );

        if(var_Y > 0.008856)
            var_Y = Math.pow(var_Y, (double)1/3);
        else 
            var_Y = ( 7.787 * var_Y ) + ( (double)16 / 116 );

        if(var_Z > 0.008856)
            var_Z = Math.pow(var_Z, (double)1/3);
        else 
            var_Z = ( 7.787 * var_Z ) + ( (double)16 / 116 );

        double CIEL = ( 116 * var_Y ) - 16;
        double CIEa = 500 * ( var_X - var_Y );
        double CIEb = 200 * ( var_Y - var_Z );
        
        return new double[]{CIEL, CIEa,CIEb};
    }
	
    double[] LABtoXYZ(double CIEL, double CIEa, double CIEb){
        double var_Y = ( CIEL + 16 ) / 116;
        double var_X = CIEa / 500 + var_Y;
        double var_Z = var_Y - CIEb / 200;

        if (Math.pow(var_Y, 3) > 0.008856 )
            var_Y = Math.pow(var_Y, 3);
        else
            var_Y = ( var_Y - (double)16 / 116 ) / 7.787;

        if( Math.pow(var_X, 3) > 0.008856 )
            var_X = Math.pow(var_X, 3);
        else
            var_X = ( var_X - (double)16 / 116 ) / 7.787;
        
        if ( Math.pow(var_Z, 3) > 0.008856 )
            var_Z = Math.pow(var_Z, 3);
        else
            var_Z = ( var_Z - (double)16 / 116 ) / 7.787;

        double ref_X =  95.047;
        double ref_Y = 100.000;
        double ref_Z = 108.883;

        double X = ref_X * var_X;     //ref_X =  95.047     Observer= 2°, Illuminant= D65
        double Y = ref_Y * var_Y;     //ref_Y = 100.000
        double Z = ref_Z * var_Z;     //ref_Z = 108.883
        return new double[]{X, Y, Z};
    }
	
    public BufferedImage labToRGB(double[][] labL, double[][] labA, double[][] labB){
        int height = labL.length;
        int width = labL[0].length;
        BufferedImage imgTemp = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB );
        for(int y = 0;y < height; y++){
            for(int x = 0;x < width; ++x){
                double[] XYZ = LABtoXYZ(labL[y][x], labA[y][x], labB[y][x]);
                int[] rgb = ImageAlgorithms.XYZtoRGB(XYZ[0], XYZ[1], XYZ[2]);
                int r = ImageAlgorithms.clamp(rgb[0], 0, 255);
                int g = ImageAlgorithms.clamp(rgb[1], 0, 255);
                int b = ImageAlgorithms.clamp(rgb[2], 0, 255);
                imgTemp.setRGB(x, y, new Color(r,g,b).getRGB());
            }
        }
        return imgTemp;
    }
  
}
