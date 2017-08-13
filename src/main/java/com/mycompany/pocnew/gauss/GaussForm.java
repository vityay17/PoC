package com.mycompany.pocnew.gauss;

import com.mycompany.pocnew.shared.ImageAlgorithms;
import com.mycompany.pocnew.POC;
import com.mycompany.pocnew.nieliniowe.Maximum;
import com.mycompany.pocnew.nieliniowe.Medianowy;
import com.mycompany.pocnew.nieliniowe.Minimum;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class GaussForm extends javax.swing.JFrame{

    POC parent = null;

    // W konstruktorze dla ulatwienia podaje obiekt POC - zeby miec dostep do obrazow oryginalnego i roboczego
    // To jest rozwiazanie malo eleganckie i przy rozbudowie powinno zostac zastapione odpowiednimi interfejsami
    public GaussForm(POC parent){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.parent = parent;
        initComponents();
        generateTextFields();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();
        jTextFieldRadiusMask = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldFiGauss = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldAGauss = new javax.swing.JTextField();
        jButtonGauss = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        jButtonUnsharp = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldAfiUnsharp = new javax.swing.JTextField();
        jButtonMinimum = new javax.swing.JButton();
        jButtonMaximum = new javax.swing.JButton();
        jButtonMedianowy = new javax.swing.JButton();
        jButtonPopulateMaskWith1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Brightness");
        setMinimumSize(new java.awt.Dimension(300, 100));

        cancelButton.setText("Anuluj");
        cancelButton.setPreferredSize(new java.awt.Dimension(125, 25));
        cancelButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cancelButtonMouseClicked(evt);
            }
        });
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        okButton.setText("OK");
        okButton.setPreferredSize(new java.awt.Dimension(125, 25));
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        jTextFieldRadiusMask.setText("3");
        jTextFieldRadiusMask.setToolTipText("");
        jTextFieldRadiusMask.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldRadiusMaskKeyReleased(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("r:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("fi:");

        jTextFieldFiGauss.setText("5");
        jTextFieldFiGauss.setToolTipText("");
        jTextFieldFiGauss.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldFiGaussKeyReleased(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("a:");

        jTextFieldAGauss.setText("10");
        jTextFieldAGauss.setToolTipText("");
        jTextFieldAGauss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldAGaussActionPerformed(evt);
            }
        });
        jTextFieldAGauss.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldAGaussKeyReleased(evt);
            }
        });

        jButtonGauss.setText("Gauss");
        jButtonGauss.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGaussActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 756, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 494, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(panel);

        jButtonUnsharp.setText("Unsharp");
        jButtonUnsharp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonUnsharpActionPerformed(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("afi:");

        jTextFieldAfiUnsharp.setText("5");
        jTextFieldAfiUnsharp.setToolTipText("");
        jTextFieldAfiUnsharp.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldAfiUnsharpKeyReleased(evt);
            }
        });

        jButtonMinimum.setText("Minimum");
        jButtonMinimum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMinimumActionPerformed(evt);
            }
        });

        jButtonMaximum.setText("Maximum");
        jButtonMaximum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMaximumActionPerformed(evt);
            }
        });

        jButtonMedianowy.setText("Medianowy");
        jButtonMedianowy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMedianowyActionPerformed(evt);
            }
        });

        jButtonPopulateMaskWith1.setText("Populate mask with 1");
        jButtonPopulateMaskWith1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPopulateMaskWith1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonGauss, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldAGauss))
                            .addComponent(jButtonUnsharp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldAfiUnsharp, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonMinimum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonMaximum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonMedianowy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonPopulateMaskWith1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldRadiusMask))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jTextFieldFiGauss)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldRadiusMask, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldFiGauss, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldAGauss, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonGauss)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldAfiUnsharp, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonUnsharp)
                        .addGap(17, 17, 17)
                        .addComponent(jButtonPopulateMaskWith1)
                        .addGap(18, 18, 18)
                        .addComponent(jButtonMinimum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonMaximum)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonMedianowy)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_cancelButtonMouseClicked
    {//GEN-HEADEREND:event_cancelButtonMouseClicked
        parent.revertImage();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_cancelButtonMouseClicked

    private void okButtonMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_okButtonMouseClicked
    {//GEN-HEADEREND:event_okButtonMouseClicked
        parent.updateImage();
        setVisible(false);
        dispose();
    }//GEN-LAST:event_okButtonMouseClicked

    private void jTextFieldAGaussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldAGaussActionPerformed
        generateTextFields();
    }//GEN-LAST:event_jTextFieldAGaussActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_okButtonActionPerformed

    private void jButtonGaussActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGaussActionPerformed
        runGauss();
    }//GEN-LAST:event_jButtonGaussActionPerformed

    private void jTextFieldRadiusMaskKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRadiusMaskKeyReleased
        generateTextFields();
    }//GEN-LAST:event_jTextFieldRadiusMaskKeyReleased

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jTextFieldFiGaussKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldFiGaussKeyReleased
        generateTextFields();
    }//GEN-LAST:event_jTextFieldFiGaussKeyReleased

    private void jTextFieldAGaussKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAGaussKeyReleased
        generateTextFields();
    }//GEN-LAST:event_jTextFieldAGaussKeyReleased

    private void jButtonUnsharpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonUnsharpActionPerformed
        runUnsharp();
    }//GEN-LAST:event_jButtonUnsharpActionPerformed

    private void jTextFieldAfiUnsharpKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldAfiUnsharpKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldAfiUnsharpKeyReleased

    private void jButtonMinimumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMinimumActionPerformed
        runMinimum();
    }//GEN-LAST:event_jButtonMinimumActionPerformed

    private void jButtonMaximumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMaximumActionPerformed
        runMaximum();
    }//GEN-LAST:event_jButtonMaximumActionPerformed

    private void jButtonMedianowyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMedianowyActionPerformed
        runMedianowy();
    }//GEN-LAST:event_jButtonMedianowyActionPerformed

    private void jButtonPopulateMaskWith1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPopulateMaskWith1ActionPerformed
        for(int i = 0; i < textFieldGrid.length; i++)
            for(int j = 0; j < textFieldGrid.length; j++)
                textFieldGrid[i][j].setText("1");
    }//GEN-LAST:event_jButtonPopulateMaskWith1ActionPerformed
    
    void generateTextFields(){
        try{
            panel.removeAll();
            int radius = Integer.valueOf(jTextFieldRadiusMask.getText());
            int size = radius * 2 + 1;
            textFieldGrid = new JTextField[size][size];
            panel.setLayout(new GridLayout(size, size));
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    JTextField tx = new JTextField();
                    tx.setColumns(5);
                    textFieldGrid[i][j] = tx;
                    panel.add(tx);
                }
            }
            calculateGaussElements();
            panel.repaint();
            validate();
        } catch(NumberFormatException e){
        }
    }
    
    public int[][] populateGausMask(int r, int fi, int a){
        int t = r; 
        int s = t;

        for(int i = -s; i <= s; i++){
            for(int j = -t; j <= t; j++){
                if(i == 0 && j == 0){
                    textFieldGrid[i + r][j + r].setText(String.valueOf(a));
                }else{
                    double temp1 = -(Math.pow(i, 2)+Math.pow(j, 2));
                    double temp2 = 2 * Math.pow(fi,2);
                    int result = (int)(a * Math.pow(Math.E, temp1 / temp2));
                    textFieldGrid[i + r][j + r].setText(String.valueOf(result));
                }
            }
        }
        int[][] maska = getArrayFromTextFields();				
        return maska;
    }
    
    private int[][] getArrayFromTextFields(){
        int[][] maska = new int[textFieldGrid.length][textFieldGrid.length];
        for(int i = 0; i < textFieldGrid.length; i++){
            for(int j = 0; j < textFieldGrid[i].length; j++){
                try{
                    maska[i][j] = Integer.valueOf(textFieldGrid[i][j].getText());
                } catch(NumberFormatException e){
                }
            }
        }
        return maska;
    }
    
    private void calculateGaussElements(){
        try{
            int r = Integer.valueOf(jTextFieldRadiusMask.getText());
            int fi = Integer.valueOf(jTextFieldFiGauss.getText());
            int a = Integer.valueOf(jTextFieldAGauss.getText());

            int t = r; 
            int s = t;

            for(int i = -s; i <= s; i++){
                for(int j = -t; j <= t; j++){
                    if(i == 0 && j == 0){
                        textFieldGrid[i + r][j + r].setText(String.valueOf(a));
                    }else{
                        double temp1 = -(Math.pow(i, 2) + Math.pow(j, 2));
                        double temp2 = 2 * Math.pow(fi, 2);
                        int result = (int)(a * Math.pow(Math.E, temp1/temp2));
                        textFieldGrid[i+r][j+r].setText(String.valueOf(result));
                    }
                }
            }
        } catch(NumberFormatException e){
        }
    }
      
    void runGauss(){
        int r = Integer.valueOf(jTextFieldRadiusMask.getText());
        int fi = Integer.valueOf(jTextFieldFiGauss.getText());
        int a = Integer.valueOf(jTextFieldAGauss.getText());

        int[][] maskaGauss = populateGausMask(r, fi, a);
        parent.workImage = new GaussService(parent).runEngine(maskaGauss, parent.workImage);        
        parent.repaint();
    }
    
    void runUnsharp(){
        int r = Integer.valueOf(jTextFieldRadiusMask.getText());
        int fi = Integer.valueOf(jTextFieldFiGauss.getText());
        int a = Integer.valueOf(jTextFieldAGauss.getText());
        int afi = Integer.valueOf(jTextFieldAfiUnsharp.getText());

        int[][] maskaGauss = populateGausMask(r, fi,  a);
        BufferedImage img = new GaussService(parent).runEngine(maskaGauss, parent.workImage);
        parent.workImage = new GaussService(parent).calculateUnsharpMask(img, afi);
        parent.repaint();
    }
    
    void runMinimum(){
        int[][] maska = getArrayFromTextFields();
        new Minimum().run(maska, parent.workImage);
        parent.repaint();
    }
    
    void runMaximum(){
        int[][] maska = getArrayFromTextFields();
        new Maximum().run(maska, parent.workImage);
        parent.repaint();
    }
    
    void runMedianowy(){
        int[][] maska = getArrayFromTextFields();
        new Medianowy().run(maska, parent.workImage);
        parent.repaint();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButtonGauss;
    private javax.swing.JButton jButtonMaximum;
    private javax.swing.JButton jButtonMedianowy;
    private javax.swing.JButton jButtonMinimum;
    private javax.swing.JButton jButtonPopulateMaskWith1;
    private javax.swing.JButton jButtonUnsharp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldAGauss;
    private javax.swing.JTextField jTextFieldAfiUnsharp;
    private javax.swing.JTextField jTextFieldFiGauss;
    private javax.swing.JTextField jTextFieldRadiusMask;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel panel;
    // End of variables declaration//GEN-END:variables
    private JTextField[][] textFieldGrid;
}