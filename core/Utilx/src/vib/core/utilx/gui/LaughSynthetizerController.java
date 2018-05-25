/*
 *  This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.utilx.gui;

import vib.core.util.laugh.Laugh;
import vib.core.util.laugh.LaughSynthetizer;

/**
 *
 * @author Andre-Marie Pez
 */
public class LaughSynthetizerController extends javax.swing.JFrame {

    /** Creates new form TTSController */
    public LaughSynthetizerController() {
        initComponents();
        updateTTSOptions();
    }

    private void updateTTSOptions(){
        Laugh.setLaughSynthetizerOptions(
                doTemporize.isSelected(),
                doAudio.isSelected(),
                doPhonem.isSelected());
    }

    public void setLaughSynthetizer(LaughSynthetizer tts){
        Laugh.setLaughSynthetizer(tts);
    }

    public void setDoTemporize(boolean todo){
        doTemporize.setSelected(todo);
        updateTTSOptions();
    }
    public boolean getDoTemporize(){
        return doTemporize.isSelected();
    }

    public void setDoAudio(boolean todo){
        doAudio.setSelected(todo);
        updateTTSOptions();
    }

    public boolean getDoAudio(){
        return doAudio.isSelected();
    }

    public void setDoPhonemes(boolean todo){
        doPhonem.setSelected(todo);
        updateTTSOptions();
    }

    public boolean getDoPhonemes(){
        return doPhonem.isSelected();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        doTemporize = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.temporize");
        doAudio = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.audio");
        doPhonem = new vib.core.utilx.gui.ToolBox.LocalizedJCheckBox("utilx.tts.phonems");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        doTemporize.setSelected(true);
        doTemporize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doTemporizeActionPerformed(evt);
            }
        });

        doAudio.setSelected(true);
        doAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doAudioActionPerformed(evt);
            }
        });

        doPhonem.setSelected(true);
        doPhonem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                doPhonemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(doTemporize)
                    .addComponent(doAudio)
                    .addComponent(doPhonem))
                .addContainerGap(180, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doTemporize)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doAudio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(doPhonem)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void doTemporizeActionPerformed(java.awt.event.ActionEvent evt) {
        updateTTSOptions();
    }

    private void doAudioActionPerformed(java.awt.event.ActionEvent evt) {
        updateTTSOptions();
    }

    private void doPhonemActionPerformed(java.awt.event.ActionEvent evt) {
        updateTTSOptions();
    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox doAudio;
    private javax.swing.JCheckBox doPhonem;
    private javax.swing.JCheckBox doTemporize;
    // End of variables declaration

}