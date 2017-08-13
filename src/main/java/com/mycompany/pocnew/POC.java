package com.mycompany.pocnew;

import com.mycompany.pocnew.canny.CannyForm;
import com.mycompany.pocnew.kompresja.KompresjaForm;
import com.mycompany.pocnew.gauss.GaussForm;
import com.mycompany.pocnew.watermark.WatermarkForm;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Glowna klasa integrujaca wszystki formatki kontrolne i wyswietlajaca obraz
 */
public class POC extends javax.swing.JFrame
{
    // Oryginalny obraz, z ktorego beda brane dane do przetwarzania
    public BufferedImage originalImage = null;
    
    // Obraz roboczy, na ktorym są przeprowadzane wszystkie operacje przetwarzania.
    // Tylko ten obraz jest wyświetlany na formatce glownej
    public BufferedImage workImage = null;
    
    // Formatka kontrolna z parametrami konkretnej operacji
    GaussForm gaussForm = null;
    KompresjaForm kompresjaForm = null;
    CannyForm cannyForm = null;
    WatermarkForm watermarkForm = null;
    // Komponenty do wyświetlania obrazu na formatce glownej
    JLabel imageLabel = null;
    ImageIcon imageIcon = null;

    public POC(){
        setTitle("POC - Imie Nazwisko");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);        
        initComponents();
//        readImage("lena.bmp");
//        repaint();
    }
    
    // Metoda przywracająca oryginalny obraz do obrazu roboczego
    public void revertImage(){
        try{
            originalImage.copyData(workImage.getRaster());
            imageIcon.setImage(workImage);
            imageLabel.setIcon(imageIcon);
            imageLabel.repaint();
        } catch (Exception e) {}
    }

    // Metoda aktualizujaca oryginalny obraz danymi z obrazu roboczego
    public void updateImage(){
        try{
            workImage.copyData(originalImage.getRaster());
            imageIcon.setImage(workImage);
            imageLabel.setIcon(imageIcon);
        } catch (Exception e) {}
    }

    // Metoda wczytujaca obraz z pliku i tworzaca na jego podstawie obraz oryginalny i jego kopie robocza
    public void readImage(String fn){
        try{
            // Wczytanie obrazu z pliku
            BufferedImage loadImage = ImageIO.read(new File(fn));
            
            // Oryginalny obraz tworzony na podstawie wczytanego z ewentualna konwersja obrazu do formatu 32 bit RGB
            originalImage = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            originalImage.getGraphics().drawImage(loadImage, 0, 0, null);            
            
            // Roboczy obraz tworzony jako kopia oryginalnego
            workImage = new BufferedImage(loadImage.getWidth(), loadImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            originalImage.copyData(workImage.getRaster());

            // Wyswietlenie roboczego obrazu na formatce
            imageIcon = new ImageIcon(workImage);
            imageLabel.setIcon(imageIcon);
            
        } catch (Exception e){
            System.out.println("Image read error: " + e.getMessage());
        }
    }

    private void initComponents(){
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        
        imageLabel = new JLabel();                
        add(new JScrollPane(imageLabel));

        JMenuItem mitem = new JMenuItem("Open");

        mitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION)
                {
                    File file = fc.getSelectedFile();
                    String fname = file.getPath();
                    readImage(fname);
//                    readImage("lena.bmp");
                    repaint();
                }
            }
        });
        menu.add(mitem);

        menu.addSeparator();

        mitem = new JMenuItem("Exit");
        mitem.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                System.exit(0);
            }
        });
        menu.add(mitem);

        menu = new JMenu("Colors");
        menuBar.add(menu);

        mitem = new JMenuItem("Rozmycie Gaussa i Filtry Nieliniowe");
        mitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                gaussForm = new GaussForm(POC.this);
                gaussForm.pack();
                gaussForm.setVisible(true);
            }
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("Filtr Canny'ego");
        mitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                cannyForm = new CannyForm(POC.this);
                cannyForm.pack();
                cannyForm.setVisible(true);
            }
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("Kompresja stratna obrazu");
        mitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                kompresjaForm = new KompresjaForm(POC.this);
                kompresjaForm.pack();
                kompresjaForm.setVisible(true);
            }
        });
        menu.add(mitem);
        
        mitem = new JMenuItem("Znakowanie wodne");
        mitem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                watermarkForm = new WatermarkForm(POC.this);
                watermarkForm.pack();
                watermarkForm.setVisible(true);
            }
        });
        menu.add(mitem);
    }

    public static void main(String[] args){
        POC main = new POC();
        main.setVisible(true);
        main.setSize(800, 600);
        
        //main.setExtendedState(Frame.MAXIMIZED_BOTH);               
    }
    
}
