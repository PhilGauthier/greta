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
package vib.auxiliary.BvhMocap;

//import vib.core.signals.EventPerformer;

/**
 *
 * @author Nesrine Fourati
 * @author Radoslaw Niewiadomski
 */


public class SynchroBvhReader extends BvhReader //implements EventPerformer
{

    public SynchroBvhReader() {
        this("");
    }

    public SynchroBvhReader(String fname) {
        filename = fname;
        dictionary = new Dictionary();
        dictionary.Initialize();
    }

//   // @Override
//    public void load(String bvhfilename) {
//        filename = bvhfilename;
//    }
//
//    public void performEvent(String message) {   //filename = message;
//        process();
//    }
}
