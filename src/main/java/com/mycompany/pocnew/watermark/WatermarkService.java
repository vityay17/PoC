package com.mycompany.pocnew.watermark;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import com.mycompany.pocnew.shared.GUIElements;
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WatermarkService {
    BufferedImage image;
    BufferedImage realisImage;
    double[][] arrayWMR;
    double[][] arrayWMG;
    double[][] arrayWMB;

    public WatermarkService(BufferedImage image) {
        this.image = image;
    }
    
    BufferedImage generateWaterMarkImage(int K){
        double[] gaussArray = generateGaussElements(K);
        DoubleDCT_2D dct = new DoubleDCT_2D(image.getHeight(), image.getWidth());
        double[][] imageDataR = new double[image.getHeight()][image.getWidth()];
        double[][] imageDataG = new double[image.getHeight()][image.getWidth()];
        double[][] imageDataB = new double[image.getHeight()][image.getWidth()];
        
        Color color;
        float red, green, blue;
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                color = new Color(image.getRGB(y, x));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();

                imageDataR[x][y] = red;
                imageDataG[x][y] = green;
                imageDataB[x][y] = blue;
            }
        }
//DCT manipulation
        dct.forward(imageDataR, true);
        dct.forward(imageDataG, true);
        dct.forward(imageDataB, true);
        arrayWMR = new double[K][4];
        arrayWMG = new double[K][4];
        arrayWMB = new double[K][4];
        waterMarkHelper(imageDataR, gaussArray, 0.1, arrayWMR);
        waterMarkHelper(imageDataG, gaussArray, 0.1, arrayWMG);
        waterMarkHelper(imageDataB, gaussArray, 0.1, arrayWMB);
        dct.inverse(imageDataR,true);
        dct.inverse(imageDataG,true);
        dct.inverse(imageDataB,true);
        double RmaxR = 0;
        double RmaxG = 0;
        double RmaxB = 0;
        //otrzymywanie maksymalnych pikseli
        for(int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                if (RmaxR < imageDataR[x][y])
                    RmaxR = imageDataR[x][y];
                if (RmaxG < imageDataG[x][y])
                    RmaxG = imageDataG[x][y];
                if (RmaxB < imageDataB[x][y])
                    RmaxB = imageDataB[x][y];
            }
        }
        realisImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for(int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                imageDataR[x][y] = (imageDataR[x][y]*255/RmaxR);
                imageDataG[x][y] = (imageDataG[x][y]*255/RmaxG);
                imageDataB[x][y] = (imageDataB[x][y]*255/RmaxB);
                color = new Color(
                    ImageAlgorithms.clamp((int)imageDataR[x][y],0,255),
                    ImageAlgorithms.clamp((int)imageDataG[x][y],0,255),
                    ImageAlgorithms.clamp((int)imageDataB[x][y],0,255));
                realisImage.setRGB(y, x, color.getRGB());
            }
        }
        return realisImage;
    }
    
    public double[] generateGaussElements(int K){
        double[] gaussArray = new double[K];
        for (int x = 0; x < K; x++)
            gaussArray[x] = new Random().nextGaussian();
        return gaussArray;
    }
    
    public void waterMarkHelper(double[][] imageData, double[] gaussArray, double alfa, double[][] arrayWM) {
        int height = imageData.length, 
            weight = imageData[0].length; 
        int K = gaussArray.length;
        double[][] sorted = new double[height * weight][4];
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < weight; y++) {
                sorted[x*weight + y][0] = x;              
                sorted[x*weight + y][1] = y;
                sorted[x*weight + y][2] = imageData[x][y];
                sorted[x*weight + y][3] = 0; 
            }
        }
        new Sorter().sort(sorted);

        int s = sorted.length - K;
        for (int i = 0; i < arrayWM.length; i++) {
            arrayWM [i][0] = sorted[s + i][0] ;
            arrayWM [i][1] = sorted[s + i][1] ;
            arrayWM [i][2] = sorted[s + i][2] ;
            arrayWM [i][3] = gaussArray[i];
            imageData[(int)arrayWM[i][0] ] [ (int)arrayWM[i][1] ] =  arrayWM[i][2] * (1 + alfa*arrayWM[i][3]);
        }
    }
    
    public String compareWaterMark() {
        if(arrayWMR == null){
            GUIElements.showMessage("Musicz najpierw wykonać operacje Znakowania wodnego.");
            return null;
        }
        DoubleDCT_2D dct = new DoubleDCT_2D(image.getHeight(), image.getWidth());
        double[][] dataR = new double[image.getHeight()][image.getWidth()];
        double[][] dataG = new double[image.getHeight()][image.getWidth()];
        double[][] dataB = new double[image.getHeight()][image.getWidth()];
        Color color;
        float red, green, blue;
        for (int x = 0; x < image.getHeight(); x++) {
            for (int y = 0; y < image.getWidth(); y++) {
                color = new Color(image.getRGB(y,x));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                dataR[x][y] = red;
                dataG[x][y] = green;
                dataB[x][y] = blue;
            }
        }
//DCT
        dct.forward(dataR, true);
        dct.forward(dataG, true);
        dct.forward(dataB, true);
        return "Correlation: Red = " + compareEngine(dataR, arrayWMR) + "; Green = " + compareEngine(dataG, arrayWMG)+"; Blue = " + compareEngine(dataB, arrayWMB);
    }
    
    public double compareEngine(double[][] imageData, double[][] arrayWatermark){
        double correlation = 0;
        double[] wprym = new double[arrayWatermark.length];
        double middle = 0;
        double wprymMiddle = 0;
        for (int i = 0; i < arrayWatermark.length; i++) {
            wprym[i] = (imageData[(int)arrayWatermark[i][0]][(int)arrayWatermark[i][0]]- arrayWatermark[i][2])/arrayWatermark[i][2];
            middle = middle + arrayWatermark[i][3];
            wprymMiddle = wprymMiddle + wprym[i];
        }
        middle = middle + arrayWatermark.length;
        wprymMiddle = wprymMiddle + arrayWatermark.length;
        double A = 0, B = 0, C = 0, D = 0, E = 0;
        for (int i = 0; i < arrayWatermark.length; i++) {
            A = wprym[i] - wprymMiddle;
            B = arrayWatermark[i][3] - middle;
            C = C +(A*B);
            D = D +(A*A);
            E = E +(B*B);
        }
        correlation = C/Math.sqrt(D*E);
        return correlation;
    }
    
    public void openWaterMark(){
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("Watermark file", "wtm");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
            DataInputStream dis = null;
            File file = fc.getSelectedFile();
            try{
                dis = new DataInputStream(new FileInputStream(file));
                int size = (int)dis.readDouble();
                arrayWMR = new double [size][4];
                arrayWMG = new double [size][4];
                arrayWMB = new double [size][4];
                for (int i = 0; i < size; i++) {
                    arrayWMR[i][0]=dis.readDouble();
                    arrayWMR[i][1]=dis.readDouble();
                    arrayWMR[i][2]=dis.readDouble();
                    arrayWMR[i][3]=dis.readDouble();
                    arrayWMG[i][0]=dis.readDouble();
                    arrayWMG[i][1]=dis.readDouble();
                    arrayWMG[i][2]=dis.readDouble();
                    arrayWMG[i][3]=dis.readDouble();
                    arrayWMB[i][0]=dis.readDouble();
                    arrayWMB[i][1]=dis.readDouble();
                    arrayWMB[i][2]=dis.readDouble();
                    arrayWMB[i][3]=dis.readDouble();
                }
            }catch (IOException e){
                GUIElements.showMessage("Nie powiodło się wczytać pliku: " + e.getLocalizedMessage());
            }
        }
    }
    
    public void saveWaterMark() {
        if(realisImage == null){
            GUIElements.showMessage("Musicz najpierw wykonać operacje Znakowania wodnego.");
            return;
        }
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("JPEG file", "jpg", "jpeg");
        fc.addChoosableFileFilter(filter);
        fc.setFileFilter(filter);
        if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
            try{
                File file = fc.getSelectedFile();
                ImageIO.write(realisImage, "jpg", file);
                DataOutputStream dos = new DataOutputStream(new FileOutputStream(file + ".wtm"));
                dos.writeDouble(arrayWMR.length);
                for (int i = 0; i < arrayWMR.length; i++) {
                    dos.writeDouble(arrayWMR[i][0]);
                    dos.writeDouble(arrayWMR[i][1]);
                    dos.writeDouble(arrayWMR[i][2]);
                    dos.writeDouble(arrayWMR[i][3]);
                    dos.writeDouble(arrayWMG[i][0]);
                    dos.writeDouble(arrayWMG[i][1]);
                    dos.writeDouble(arrayWMG[i][2]);
                    dos.writeDouble(arrayWMG[i][3]);
                    dos.writeDouble(arrayWMB[i][0]);
                    dos.writeDouble(arrayWMB[i][1]);
                    dos.writeDouble(arrayWMB[i][2]);
                    dos.writeDouble(arrayWMB[i][3]);
                }
            }catch (Exception e){
                GUIElements.showMessage("Nie powiodło się zapisać pliku: " + e.getLocalizedMessage());
            }
        }
    }
    
}
