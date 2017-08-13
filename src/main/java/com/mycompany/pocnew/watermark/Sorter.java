package com.mycompany.pocnew.watermark;

public class Sorter {
    private double[][] values;
    private double[][] helpers;

    public void sort(double [][] values) {
        this.values = values;
        int length = values.length;
        this.helpers = new double [length][values[0].length];
        sortLowHigh(0, length - 1);
    }
    private void sortLowHigh(int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            sortLowHigh(low, middle);
            sortLowHigh(middle + 1, high);
            sortLowMiddleHigh(low, middle, high);
        }
    }
    private void sortLowMiddleHigh(int low, int middle, int high) {
        for (int i = low; i <= high; i++)
            helpers[i] = values[i];
        int i = low;
        int j = middle + 1;
        int k = low;
        while(i <= middle && j <= high){
            if (Math.abs(helpers[i][2]) <= Math.abs(helpers[j][2])) {
                values[k] = helpers[i];
                i++;
            } else {
                values[k] = helpers[j];
                j++;
            }
            k++;
        }
        while(i <= middle){
            values[k] = helpers[i];
            k++;
            i++;
        }
    }
}
