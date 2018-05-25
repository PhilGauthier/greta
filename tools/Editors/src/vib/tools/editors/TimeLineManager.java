/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.tools.editors;

import vib.core.util.time.Temporizable;
import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author Andre-Marie
 */
public class TimeLineManager<T extends Temporizable> extends javax.swing.JPanel {


    protected TimeLine<T> timeline;
    
    /** Creates new form TimeLineManager */
    public TimeLineManager(TimeLine<T> t) {
        timeline = t;
        timeline.setManager(this);
        init();
        setColor(t.getColor());
    }
    
    public TimeLine getTimeLine(){
        return this.timeline;
    }
    
    public boolean notEmpty(){
        return !this.getTimeLine().getItems().isEmpty();
    }
    
    public void setColor(Color c){
        timeline.setColor(c);
        jTextPane1.setBackground(c);
        this.setBackground(c);
    }
    public void setLabel(String label){
        jTextPane1.setText("\n" + label);
    }
    
    public String getLabel(){
        if (jTextPane1.getText().isEmpty()) {
            return "";
        }
        else {
            return jTextPane1.getText().substring(1);
        }
    }

    public void add(T t){
        timeline.add(new TemporizableContainer<T>(t, getLabel()));
    }
    protected void init(){
        initComponents();
        jTextPane1.setBackground(timeline.getColor());
        StyledDocument doc = jTextPane1.getStyledDocument();

        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(center, jTextPane1.getFont().getFamily());
        StyleConstants.setForeground(center, new Color(0,0,0,220));
        doc.setParagraphAttributes(0, 0, center, true);
    }

    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel1 = timeline;

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jScrollPane1.setBorder(null);

        jTextPane1.setBorder(null);
        jTextPane1.setEditable(false);
        jScrollPane1.setViewportView(jTextPane1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                //.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }

    private javax.swing.JPanel jPanel1;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTextPane jTextPane1;

}