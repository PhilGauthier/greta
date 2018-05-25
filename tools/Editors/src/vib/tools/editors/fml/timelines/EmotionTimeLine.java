/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.tools.editors.fml.timelines;

import vib.tools.editors.TimeLine;
import vib.core.intentions.EmotionIntention;
import vib.core.util.time.TimeMarker;
import java.awt.FontMetrics;
import vib.tools.editors.TemporizableContainer;

/**
 *
 * @author Andre-Marie
 */
public class EmotionTimeLine extends TimeLine<EmotionIntention>{

    @Override
    protected TemporizableContainer<EmotionIntention> instanciateTemporizable(double startTime, double endTime) {
        return new TemporizableContainer<EmotionIntention> (
                new EmotionIntention("emotion_"+System.currentTimeMillis(), IntentionsAviable.EMOTIONS.length==0? "NEUTRAL" : IntentionsAviable.EMOTIONS[0], new TimeMarker("start",startTime), new TimeMarker("end",endTime), EmotionIntention.FELT, 1),
                manager.getLabel());
    }

    @Override
    protected String getDescription(TemporizableContainer<EmotionIntention> temporizableContainer, FontMetrics metrics, int limitSize) {
        return isGoodSize(temporizableContainer.getTemporizable().getType(), metrics, limitSize) ? temporizableContainer.getTemporizable().getType() : null;
    }
}