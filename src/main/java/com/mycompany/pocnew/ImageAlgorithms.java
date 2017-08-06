package com.mycompany.pocnew;

/**
 * Klasa zawiera pomocnicze statyczne funkcje oraz algorytmy do obslugi obrazow
 */
public class ImageAlgorithms
{

    public static int jrgb(int r, int g, int b) { return (r << 16) + (g << 8) + b; }

    public static int jred(int rgb)   { return ((rgb >> 16) & 0xff); }
    public static int jgreen(int rgb) { return ((rgb >> 8) & 0xff); }
    public static int jblue(int rgb)  { return (rgb & 0xff); }

    public static int clamp(int v, int min, int max)
    {
        if (v <= min) return min;
        if (v >= max) return max;
        return v;
    }
    
    // Funkcja zmieniajaca jasnosc koloru rgb o wartosc db
    public static int changeBrightness(int rgb, int db)
    {
        // Rozbior koloru na skladowe R, G, B
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);
        
        // Modyfikacja skladowych razem z przypilnowaniem zakresow 0-255
        r = clamp(r + db, 0, 255);
        g = clamp(g + db, 0, 255);
        b = clamp(b + db, 0, 255);
        
        // Zlozenie skladowych R, G, B w jeden kolor i zwrocenie jego wartosci
        return jrgb(r, g, b);        
    }
    
    public static int changeContrast(int rgb, int dc) {
        int contrast = -dc;
        
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);

        r = clamp((r*(100-contrast)+128*contrast)/100, 0, 255);
        g = clamp((g*(100-contrast)+128*contrast)/100, 0, 255);
        b = clamp((b*(100-contrast)+128*contrast)/100, 0, 255);

       return jrgb(r, g, b);
    }
    
    static int changeGamma(int rgb, int dc) {
        double gamma = (double)dc/10.0;
        
        int r = jred(rgb);
        int g = jgreen(rgb);
        int b = jblue(rgb);
        
        r = (int) (255 * (Math.pow((double) r / (double) 255, (double)gamma)));
        g = (int) (255 * (Math.pow((double) g / (double) 255, (double)gamma)));
        b = (int) (255 * (Math.pow((double) b / (double) 255, (double)gamma)));
        
        return jrgb(r, g, b);
    }
    
    public static int rgb2hsl(int rgb) {return 0;}
    public static int hsl2rgb(int hsl) {return 0;}
    
    // ...

    
}
