/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.environmentmanager.core.io.bap;

import vib.auxiliary.environmentmanager.core.IEnvironmentServer;
import vib.auxiliary.environmentmanager.core.mvc.IEnvironmentManagerCore;
import vib.core.animation.mpeg4.MPEG4Animatable;

/**
 *
 * @author Brice Donval
 */
public abstract class AbstractBAPSender implements IBAPSender {

    private final IEnvironmentManagerCore environmentServer;

    private final MPEG4Animatable mpegAnimatable;

    /* ---------------------------------------------------------------------- */

    public AbstractBAPSender(IEnvironmentServer environmentServer, MPEG4Animatable mpegAnimatable) {
        this.environmentServer = (IEnvironmentManagerCore) environmentServer;
        this.mpegAnimatable = mpegAnimatable;
    }

    /* ---------------------------------------------------------------------- */

    @Override
    public final IEnvironmentServer getEnvironmentServer() {
        return environmentServer;
    }

    @Override
    public final MPEG4Animatable getMPEG4Animatable() {
        return mpegAnimatable;
    }

    /* ---------------------------------------------------------------------- */

    @Override
    public final void hasConnected() {
        environmentServer.bapSenderHasConnected(getPort());
    }

    @Override
    public final void hasDisconnected() {
        environmentServer.bapSenderHasDisconnected(getPort());
    }

    /* -------------------------------------------------- */

    @Override
    public final void destroy() {
        onDestroy();
        environmentServer.bapSenderHasDisappeared();
    }

}