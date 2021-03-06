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
*/package vib.core.animation.mocap;

import java.util.ArrayList;
import vib.core.animation.Frame;

/**
 *
 * @author Jing Huang
 */


public class MotionSequence {
    String _name, _startFrameRef, _endFrameRef;
    ArrayList<Frame> _sequence = new ArrayList<Frame>();
    int _frameNb = 0;

    public MotionSequence(String name, String startFrameRef, String endFrameRef) {
        this._name = name;
        this._startFrameRef = startFrameRef;
        this._endFrameRef = endFrameRef;
    }

    public MotionSequence(String name) {
        this._name = name;
    }

    public String getEndFrameRef() {
        return _endFrameRef;
    }

    public void setEndFrameRef(String endFrameRef) {
        this._endFrameRef = endFrameRef;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public ArrayList<Frame> getSequence() {
        return _sequence;
    }

    public void setSequence(ArrayList<Frame> sequence) {
        this._sequence = sequence;
        _frameNb = sequence.size();
    }

    public String getStartFrameRef() {
        return _startFrameRef;
    }

    public void setStartFrameRef(String startFrameRef) {
        this._startFrameRef = startFrameRef;
    }

    public int getFrameNb() {
        return _frameNb;
    }

}
