package com.mycompany.pocnew.kompresja;

import com.mycompany.pocnew.shared.LAB;
import com.mycompany.pocnew.POC;
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class KompresjaService {
    private BufferedImage img;
    private POC parent;
    private LAB lab;
    private int quality;

    private int sizeOfChunk = 8;
    private int rowsChunks = 0;
    private int colsChunks = 0;
    private int chunksSize;

    private double[][][] chunksL, chunksA, chunksB;
    private int[][][] chunksIntL, chunksIntA, chunksIntB;

    private int[][] zigzag = makeZigZagArr(sizeOfChunk);

    private int[][] chunksZigL, chunksZigA,chunksZigB;

    private File L = new File("L");
    private File A = new File("A");
    private File B = new File("B");
    private File properties = new File("prop");
    private File zipName = new File("poc.poc");

    public KompresjaService(POC parent) {
        this.parent = parent;
        img = parent.workImage;
        lab = new LAB();
        lab.rgbToLab(img);		
    }
    public KompresjaService(POC parent, File fileToSave) {
        this.parent = parent;
        zipName = fileToSave;
        img = parent.workImage;
        lab = new LAB();
        lab.rgbToLab(img);	
    }
    public void calculate(int quality){
        this.quality = quality;
        engine();
    }
    void engine(){
        separateToChunks();
        calculateDCT();
        quantization();
        calculateZigZagForChunk();
        try {
            compression(properties,  new int[][]{{quality},{img.getHeight()},{img.getWidth()}});
            compression(L, chunksZigL);
            compression(A, chunksZigA);
            compression(B, chunksZigB);
            packedToZip(new File[]{properties, L, A, B}, zipName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void separateToChunks(){
        int height = img.getHeight();
        int width = img.getWidth();

        if(height%sizeOfChunk > 0)
            rowsChunks = height/sizeOfChunk + 1;
        else 
            rowsChunks = height/sizeOfChunk;

        if(width % sizeOfChunk > 0)
            colsChunks = width/sizeOfChunk + 1;
        else colsChunks = width/sizeOfChunk;

        chunksSize = rowsChunks * colsChunks;  

        chunksL = new double[chunksSize][8][8];
        chunksA = new double[chunksSize][8][8];
        chunksB = new double[chunksSize][8][8];
        int count = 0;
        for(int y = 0;y < rowsChunks; y++){
            for(int x = 0;x < colsChunks; x++){
                chunksL[count] = getChunk(lab.getLabL(),y*sizeOfChunk,x*sizeOfChunk);  
                chunksA[count] = getChunk(lab.getLabA(),y*sizeOfChunk,x*sizeOfChunk);
                chunksB[count] = getChunk(lab.getLabB(),y*sizeOfChunk,x*sizeOfChunk);
                count++;
            }
        }
    }

    double[][] getChunk(double[][] arr,int y, int x){
        double[][] chunk8x8 = new double[8][8]; 
        for(int i = y,t = 0;i < y+8; i++,t++){
            for(int j = x,k = 0;j < x+8; j++,k++){
                if(i < arr.length && j < arr[0].length){
                        chunk8x8[t][k] = arr[i][j];
                }
                else chunk8x8[t][k] = 0;
            }
        }
        return chunk8x8;
    }

    void calculateDCT(){	
        DoubleDCT_2D dct = new DoubleDCT_2D(8, 8);
        for(int i = 0;i < chunksL.length; i++){    	
            dct.forward(chunksL[i], true);
            dct.forward(chunksA[i], true);
            dct.forward(chunksB[i], true);
        }		
    }

    void quantization(){
        double[][] QL = {
            {16, 11, 10, 16, 24, 40, 51, 61},
            {12, 12, 14, 19, 26, 58, 60, 55},
            {14, 13, 16, 24, 40, 57, 69, 56},
            {14, 17, 22, 29 ,51 ,87, 80, 62},
            {18, 22, 37, 56, 68, 109, 103, 77},
            {24, 35, 55, 64, 81, 104, 113, 92},
            {49, 64, 78, 87, 103, 121, 120, 101},
            {72, 92, 95, 98, 112, 100, 103, 99}
        };
        double[][] QC = {
            {17, 18, 24, 47, 99, 99, 99, 99},
            {18, 21, 26, 66, 99, 99, 99, 99},
            {24, 26, 56, 99, 99, 99, 99, 99},
            {47, 66, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99},
            {99, 99, 99, 99, 99, 99, 99, 99}
        };

        for(int y = 0;y < QL.length; y++){
            for(int x = 0;x < QL[y].length; x++){
                if(quality > 50){
                    QL[y][x] = (QL[y][x]*(100 - quality))/50;
                    QC[y][x] = (QC[y][x]*(100 - quality))/50;
                    }
                else{
                    QL[y][x] = (QL[y][x]*50)/quality;
                    QC[y][x] = (QC[y][x]*50)/quality;
                }

            }
        }
        chunksIntL = new int[chunksSize][8][8];
        chunksIntA = new int[chunksSize][8][8];
        chunksIntB = new int[chunksSize][8][8];
        for(int i = 0;i < chunksL.length; i++){
            for(int y = 0;y < sizeOfChunk; y++){
                for(int x = 0;x < sizeOfChunk; x++){
                    chunksIntL[i][y][x] = (int) (chunksL[i][y][x]/QL[y][x]);  
                    chunksIntA[i][y][x] = (int) (chunksA[i][y][x]/QC[y][x]);
                    chunksIntB[i][y][x] = (int) (chunksB[i][y][x]/QC[y][x]);
                }
            }
        }
    }

    int[][] makeZigZagArr(int n){
        int[][] result = new int[n][n];
        int i = 0, j = 0;
        int d = -1; // -1 for top-right move, +1 for bottom-left move
        int start = 0, end = n * n - 1;
        do{
            result[i][j] = start++;
            result[n - i - 1][n - j - 1] = end--;

            i += d; j -= d;
            if (i < 0){
                i++; d = -d; // top reached, reverse
            }
            else if (j < 0){
                j++; d = -d; // left reached, reverse
            }
        } while (start < end);
        if (start == end)
            result[i][j] = start;
        return result;
    }

    void calculateZigZagForChunk(){
        int size = chunksIntA.length;
        chunksZigL = new int[size][8*8];
        chunksZigA = new int[size][8*8];
        chunksZigB = new int[size][8*8];
        for(int k = 0;k < chunksZigL.length;k++){
            for(int i = 0;i < zigzag.length; i++){
                for(int y = 0;y < zigzag[0].length; y++){
                    chunksZigL[k][zigzag[i][y]] = chunksIntL[k][i][y];
                    chunksZigA[k][zigzag[i][y]] = chunksIntA[k][i][y];
                    chunksZigB[k][zigzag[i][y]] = chunksIntB[k][i][y];
                }
            }
        }
    }

    void compression(File filename, int[][] arr) throws IOException{		
        BufferedWriter outputWriter = new BufferedWriter(new FileWriter(filename));		
        for(int k = 0;k < arr.length;k++){
            for(int i = 0;i < arr[0].length; i++){
                outputWriter.write(arr[k][i]+" ");		
            }
            outputWriter.newLine();
        }
        outputWriter.flush();  
        outputWriter.close(); 		
    }

    void packedToZip(File[] files, File zipName2) throws IOException{
        FileOutputStream fos = new FileOutputStream(zipName2);
        ZipOutputStream zos = new ZipOutputStream(fos);
        BufferedOutputStream out = new BufferedOutputStream(zos);
        for(File arg: files){
            BufferedReader in = new BufferedReader(new FileReader(arg));
            zos.putNextEntry(new ZipEntry(arg.getName()));
            int c;
            while((c = in.read()) != -1)
                    out.write(c);
            in.close();
            out.flush();
        }
        out.close();
//		L.delete();
//		A.delete();
//		B.delete();
//		properties.delete();
    }
}
