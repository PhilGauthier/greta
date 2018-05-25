/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.thrift;

import java.util.ArrayList;
import java.util.List;
import vib.auxiliary.thrift.gen_java.Message;
import vib.auxiliary.thrift.services.Receiver;
import vib.core.signals.BMLTranslator;
import vib.core.signals.Signal;
import vib.core.signals.SignalEmitter;
import vib.core.signals.SignalPerformer;
import vib.core.util.Mode;
import vib.core.util.id.ID;
import vib.core.util.id.IDProvider;
import vib.core.util.xml.XML;
import vib.core.util.xml.XMLParser;
import vib.core.util.xml.XMLTree;

/**
 *
 * @author Brice Donval
 */
public class BMLReceiver extends Receiver implements SignalEmitter {

    public BMLReceiver() {
        super();
        performers = new ArrayList<SignalPerformer>();
    }

    public BMLReceiver(int port) {
        super(port);
        performers = new ArrayList<SignalPerformer>();
    }

    private final ArrayList<SignalPerformer> performers;
    private final XMLParser bmlparser = XML.createParser();

    @Override
    public void perform(Message m) {
        XMLTree bml = bmlparser.parseBuffer(m.getString_content());
        List<Signal> signals = BMLTranslator.BMLToSignals(bml);
        Mode mode = BMLTranslator.getDefaultBMLMode();
        if (bml.hasAttribute("composition")) {
            mode.setCompositionType(bml.getAttribute("composition"));
        }
        if (bml.hasAttribute("reaction_type")) {
            mode.setReactionType(bml.getAttribute("reaction_type"));
        }
        if (bml.hasAttribute("reaction_duration")) {
            mode.setReactionDuration(bml.getAttribute("reaction_duration"));
        }
        if (bml.hasAttribute("social_attitude")) {
            mode.setSocialAttitude(bml.getAttribute("social_attitude"));
        }
        //send to all SignalPerformer added
        ID id = IDProvider.createID(m.getId());
        for (SignalPerformer performer : performers) {
            performer.performSignals(signals, id, mode);
        }
    }

    @Override
    public void addSignalPerformer(SignalPerformer performer) {
        performers.add(performer);
    }

    @Override
    public void removeSignalPerformer(SignalPerformer performer) {
        performers.remove(performer);
    }

}