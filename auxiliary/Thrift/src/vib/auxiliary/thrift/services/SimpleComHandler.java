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
package vib.auxiliary.thrift.services;

import vib.auxiliary.thrift.gen_java.Message;
import vib.auxiliary.thrift.gen_java.SimpleCom;
import org.apache.thrift.TException;

// Generated code

/**
 *
 * @author Ken Prepin
 */
public class SimpleComHandler implements SimpleCom.Iface{

    private Message message;
    private Receiver receiver;

    public SimpleComHandler(Receiver receiver) {
        message = new Message();
        this.receiver = receiver;
    }

    @Override
    public void send(Message m) throws TException {
        message=m;
        
        if(message.id!=null)
            System.out.println("message received by server:"+message.id+" "+message.type);
         
        receiver.perform(message);
    }

    @Override
    public boolean isStarted() {
       return receiver.isConnected();
    }

}
