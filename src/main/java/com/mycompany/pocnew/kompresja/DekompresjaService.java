package com.mycompany.pocnew.kompresja;

import com.mycompany.pocnew.shared.LAB;
import edu.emory.mathcs.jtransforms.dct.DoubleDCT_2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class DekompresjaService {
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
    int quality;

    double[][] labL, labA, labB;
    double[][][] chunksDoubleL, chunksDoubleA, chunksDoubleB;

    int[][] sourceL, sourceA, sourceB;

    int sizeOfChunk = 8;
    LAB labClass;

    int height;
    int width;

    public DekompresjaService(JLabel jLabelImage){		
        try {
            getFilesFromZip(new File("poc.poc"));
            engine(jLabelImage);
        } catch (Exception e) {
        }			
    }

    public DekompresjaService(File fileToSave, JLabel jLabelImage) {			
        try {
            getFilesFromZip(fileToSave);
            engine(jLabelImage);
        } catch (Exception e) {
        }		
    }
    void engine(JLabel jLabelImage){
        try { 
            decodingZigZag();
            deQuantization();
            deCalculateDCT();
            deSeparateTo8x8blocks();
            labClass = new LAB();
            jLabelImage.setIcon(new ImageIcon(labClass.labToRGB(labL, labA, labB)));
        } catch (Exception e) {
                e.printStackTrace();
        }		
    }

    void getFilesFromZip(File fileToSave) throws Exception{
        ZipFile zf = new ZipFile(fileToSave);
        @SuppressWarnings("unchecked")
        Enumeration<ZipEntry> e = (Enumeration<ZipEntry>) zf.entries();
        while(e.hasMoreElements()){
            ZipEntry ze = e.nextElement();
            String filename = ze.getName();
            switch(filename){
            case "L": sourceL = create2DIntMatrixFromFile(filename);break;
            case "A": sourceA = create2DIntMatrixFromFile(filename);break;
            case "B": sourceB = create2DIntMatrixFromFile(filename);break;
            case "prop": getPropertiesFromFile(filename);break;
            }			
        }			
    }

    private void getPropertiesFromFile(String filename) throws Exception {
        int[][] temp = create2DIntMatrixFromFile(filename);
        quality = temp[0][0];
        height = temp[1][0];
        width = temp[2][0];

    }

    public int[][] create2DIntMatrixFromFile(String filename) throws Exception {
        BufferedReader buffer = new BufferedReader(new FileReader(filename));
        ArrayList<int[]> arr = new ArrayList<int[]>();
        Stream<String> str = buffer.lines();
        Object[] arrObject = str.toArray();
        int[] matrix = null;
        int size = 0;
        for(int i = 0; i < arrObject.length;i++){
            String[] vals = ((String) arrObject[i]).trim().split("\\s+");
            size = vals.length;
            matrix = new int[size];
            for (int col = 0; col < size; col++)
                matrix[col] = Integer.parseInt(vals[col]);
            arr.add(matrix);
        }
        int [][] finalArr = new int[arr.size()][matrix.length];
        for(int i = 0; i < finalArr.length;i++)
            finalArr[i] = arr.get(i);
        
        return finalArr;
    }

    void decodingZigZag(){
        int[][] zigzag = makeZigZagArr(sizeOfChunk);
        int size = sourceL.length;
        chunksDoubleL = new double[size][sizeOfChunk][sizeOfChunk];
        chunksDoubleA = new double[size][sizeOfChunk][sizeOfChunk];
        chunksDoubleB = new double[size][sizeOfChunk][sizeOfChunk];		 
        for(int k = 0;k < chunksDoubleL.length;k++){
            for(int i = 0;i < chunksDoubleL[k].length; i++){
                for(int y = 0;y < chunksDoubleL[k][i].length; y++){
                    int point = zigzag[i][y];
                    chunksDoubleL[k][i][y] = sourceL[k][point];
                    chunksDoubleA[k][i][y] = sourceA[k][point];
                    chunksDoubleB[k][i][y] = sourceB[k][point];
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

    void deQuantization(){		
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

        for(int i = 0;i < chunksDoubleL.length; i++){
            for(int y = 0;y < chunksDoubleL[i].length; y++){
                for(int x = 0;x < chunksDoubleL[i][y].length; x++){
                    chunksDoubleL[i][y][x] = chunksDoubleL[i][y][x]*QL[y][x];  
                    chunksDoubleA[i][y][x] = chunksDoubleA[i][y][x]*QC[y][x];
                    chunksDoubleB[i][y][x] = chunksDoubleB[i][y][x]*QC[y][x];
                }
            }
        }
    }
    void deCalculateDCT(){	
        DoubleDCT_2D dct = new DoubleDCT_2D(sizeOfChunk, sizeOfChunk);
        for(int i = 0;i < chunksDoubleL.length; i++){    	
            dct.inverse(chunksDoubleL[i], true);
            dct.inverse(chunksDoubleA[i], true);
            dct.inverse(chunksDoubleB[i], true);
        }
    }

    void deSeparateTo8x8blocks(){
        int rowsChunks, colsChunks;
        if(height%sizeOfChunk > 0)
            rowsChunks = height/sizeOfChunk + 1;
        else rowsChunks = height/sizeOfChunk;

        if(width % sizeOfChunk > 0)
            colsChunks = width/sizeOfChunk + 1;
        else colsChunks = width/sizeOfChunk;

        labL = new double[height][width];
        labA = new double[height][width];
        labB = new double[height][width];

        int count = 0;
        for(int y = 0;y < rowsChunks; y++){
            for(int x = 0;x < colsChunks; x++){
                putChunk(labL, y*sizeOfChunk, x*sizeOfChunk, chunksDoubleL[count]);
                putChunk(labA, y*sizeOfChunk, x*sizeOfChunk, chunksDoubleA[count]);
                putChunk(labB, y*sizeOfChunk, x*sizeOfChunk, chunksDoubleB[count]);
                count++;
            }
        }
    }

    void putChunk(double[][]dest,int x, int y, double[][] source ){
        for(int i = x,t = 0;i < x+8; i++,t++){
            for(int j = y,k = 0;j < y+8; j++,k++){
                if(i < dest.length && j < dest[0].length){
                    dest[i][j] = source[t][k];
                }
            }
        }
    }
}
