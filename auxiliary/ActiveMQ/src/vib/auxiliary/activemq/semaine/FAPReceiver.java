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
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.activemq.semaine;

import vib.auxiliary.activemq.TextReceiver;
import vib.auxiliary.activemq.WhiteBoard;
import vib.core.animation.mpeg4.fap.FAPFrame;
import vib.core.animation.mpeg4.fap.FAPFrameEmitter;
import vib.core.animation.mpeg4.fap.FAPFrameEmitterImpl;
import vib.core.animation.mpeg4.fap.FAPFramePerformer;
import vib.core.animation.mpeg4.fap.FAPParser;
import vib.core.util.id.IDProvider;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andre-Marie Pez
 */
public class FAPReceiver extends TextReceiver implements FAPFrameEmitter {

    private FAPFrameEmitterImpl fapEmitter = new FAPFrameEmitterImpl();
    private FAPParser parser = new FAPParser();

    public FAPReceiver() {
        this(WhiteBoard.DEFAULT_ACTIVEMQ_HOST,
             WhiteBoard.DEFAULT_ACTIVEMQ_PORT,
             "vib.FAP");
    }

    public FAPReceiver(String host, String port, String topic) {
        super(host, port, topic);
    }

    @Override
    protected void onMessage(String content, Map<String, Object> properties) {

        List<FAPFrame> frames = parser.readFromString(content.toString(), false, false);

        if (!frames.isEmpty()) {
            String id = "ActiveMQ_FAP_Receiver";
            if (properties.containsKey("content-id")) {
                id = properties.get("content-id").toString();
            } else {
                if (properties.containsKey("id")) {
                    id = properties.get("id").toString();
                }
            }
            fapEmitter.sendFAPFrames(IDProvider.createID(id), frames);
        }
    }

    @Override
    public void addFAPFramePerformer(FAPFramePerformer fapp) {
        fapEmitter.addFAPFramePerformer(fapp);
    }

    @Override
    public void removeFAPFramePerformer(FAPFramePerformer fapp) {
        fapEmitter.removeFAPFramePerformer(fapp);
    }

}
