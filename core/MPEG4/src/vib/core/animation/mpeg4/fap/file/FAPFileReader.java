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
package vib.core.animation.mpeg4.fap.file;

import vib.core.animation.mpeg4.fap.FAPFrame;
import vib.core.animation.mpeg4.fap.FAPFrameEmitterImpl;
import vib.core.animation.mpeg4.fap.FAPParser;
import vib.core.util.id.ID;
import vib.core.util.id.IDProvider;
import java.io.File;
import java.util.List;

/**
 *
 * @author Andre-Marie Pez
 */
public class FAPFileReader extends FAPFrameEmitterImpl {

    private FAPParser parser = new FAPParser();

    public void load(String bapfilename) {
        List<FAPFrame> fap_animation = parser.readFromFile(bapfilename, true);

        if (!fap_animation.isEmpty()) {
            String base = (new File(bapfilename)).getName().replaceAll("\\.fap$", "");
            ID id = IDProvider.createID(base);
            //send to all BAPPerformer added
            sendFAPFrames(id, fap_animation);
        }
    }

    public java.io.FileFilter getFileFilter() {
        return new java.io.FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".fap");
            }
        };
    }
}
