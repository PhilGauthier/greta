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
 *  This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.animation.mpeg4.fap;

import vib.core.util.id.ID;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class is a basic implementation of {@code FAPFrameEmitter}.<br/>
 * It provides some methods to send {@code FAPFrames} to all {@code FAPFramePerfomers} added.
 * @author Andre-Marie Pez
 */
public class FAPFrameEmitterImpl implements FAPFrameEmitter{

    private ArrayList<FAPFramePerformer> performers = new ArrayList<FAPFramePerformer>();

    @Override
    public void addFAPFramePerformer(FAPFramePerformer performer) {
        if (performer != null) {
            performers.add(performer);
        }
    }

    @Override
    public void removeFAPFramePerformer(FAPFramePerformer performer) {
        if (performer != null) {
            performers.remove(performer);
        }
    }

    public void sendFAPFrames(ID requestId, FAPFrame... frames){
        sendFAPFrames(requestId, Arrays.asList(frames));
    }


    public void sendFAPFrames(ID requestId, List<FAPFrame> frames){
        for(FAPFramePerformer performer : performers){
            performer.performFAPFrames(frames, requestId);
        }
    }

    public void sendFAPFrame(ID requestId, FAPFrame frame){
        sendFAPFrames(requestId, frame);
    }

}
