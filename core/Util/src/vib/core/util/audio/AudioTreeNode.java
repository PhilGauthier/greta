/* This file is part of Greta.
 * Greta is free software: you can redistribute it and / or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.
* 
* Greta is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with Greta.If not, see <http://www.gnu.org/licenses/>.
*//*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.util.audio;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import vib.core.util.Constants;
import vib.core.util.Mode;
import vib.core.util.enums.CompositionType;
import vib.core.util.environment.TreeNode;
import vib.core.util.id.ID;
import vib.core.util.time.Timer;

/**
 *
 * @author Andre-Marie Pez
 */
public class AudioTreeNode extends TreeNode implements AudioPerformer{

    //all audios
    private final LinkedList<Audio> audios;

    public AudioTreeNode() {
        audios = new LinkedList<Audio>();
        /*
        Leaf l = new Leaf();
        l.setSize(0.3f, 0.3f, 0.3f);
        l.setReference("debug.audio");
        addChildNode(l);
        //*/
    }

    public AudioTreeNode(String id){
        this();
        identifier = id;
    }

    public void addAudio(Audio audio, Mode mode) {
        synchronized (audios) {
            if (mode.getCompositionType() == CompositionType.replace) {

                ListIterator<Audio> iter = audios.listIterator();
                while (iter.hasNext()) {
                    Audio presentAudio = iter.next();
                    presentAudio.setPlayingBufferPos(Audio.PLAYING_BUFFER_POSITION_FINISHED);
                }

                audios.clear();
            }

            // search for overlapping cases.
            ListIterator<Audio> iter = audios.listIterator();
            while (iter.hasNext()) {
                Audio presentAudio = iter.next();
                if (presentAudio.getTimeMillis() < audio.getTimeMillis() && presentAudio.getEndMillis() > audio.getTimeMillis()) {
                    //because we cannot blend audios, we force the end of the previous audio.
                    presentAudio.setEndMillis(audio.getTimeMillis());
                    if (presentAudio.getTimeMillis() > presentAudio.getEndMillis()) {
                        //the audio is ended before started, so it is useless.
                        iter.remove();
                    }
                }
            }
            audios.add(audio);
            Collections.sort(audios, Audio.audioComparator);
            cleanEndedAudios();
        }
    }

    private void cleanEndedAudios() {
        while (!audios.isEmpty() && audios.peek().getEndMillis() < Timer.getTimeMillis()) {
            audios.poll().setPlayingBufferPos(Audio.PLAYING_BUFFER_POSITION_FINISHED);
        }
    }

    public Audio getCurrentAudio() {
        cleanEndedAudios();
        if (!audios.isEmpty() && audios.peek().getTimeMillis() < Timer.getTimeMillis() + Constants.FRAME_DURATION_MILLIS) {
            return audios.peek();
        }
        return null;
    }

    @Override
    public void performAudios(List<Audio> audios, ID requestId, Mode mode) {
        Collections.sort(audios,Audio.audioComparator);
        for(Audio audio : audios) {
            addAudio(audio, mode);
        }
    }
}
