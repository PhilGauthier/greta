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
package vib.auxiliary.player.ogre;

import vib.auxiliary.player.ogre.natives.Viewport;
import vib.auxiliary.player.ogre.natives.PixelBox;
import vib.auxiliary.player.ogre.capture.Capturable;

/**
 *
 * @author Andre-Marie Pez
 */
public class OgreRenderTexture implements Capturable {

    private int currentbufSize = 48;
    private int captureHeight = 4;
    private int captureWidth = 4;
    private vib.auxiliary.player.ogre.natives.HardwarePixelBuffer buffer = null;
    private PixelBox pib = null;
    private Viewport viewport;
    private vib.auxiliary.player.ogre.natives.RenderTexture renderTexture;
    private Camera camera;
    private int textureHeight = 0;
    private int textureWidth = 0;

    public OgreRenderTexture(Camera cam){
        camera = cam;
    }

    public void setCamera(Camera cam){
        camera = cam;
    }

    public void setSize(int width, int height){
        captureWidth = width;
        captureHeight = height;
    }
    private int round4(int value) {
        return Math.max(4, (value / 4) * 4);
    }

    @Override
    public void prepareCapture() {
        if(isSizeChanged()){
            textureWidth = round4(captureWidth);
            textureHeight = round4(captureHeight);
            currentbufSize = textureWidth * textureHeight * 3;
            Ogre.callSync(new OgreThread.Callback() {
                @Override
                public void run() {
                    if(renderTexture!=null){
                        renderTexture.setActive(false);
                        renderTexture.setAutoUpdated(false);
                        vib.auxiliary.player.ogre.natives.TextureManager.getSingleton().remove("VIBRenderTexture-"+this.toString());
                    }
                    vib.auxiliary.player.ogre.natives.Texture texture = vib.auxiliary.player.ogre.natives.TextureManager.getSingleton().createRenderTexture(
                            "VIBRenderTexture-"+this.toString(),
                            textureWidth,
                            textureHeight
                    );
                    buffer = texture.getBuffer(0, 0);
                    renderTexture = buffer.getRenderTarget(0);
                }
            });
        }
        camera.getOgreCamera().setAspectRatio(((float)captureWidth)/((float)captureHeight));
        Ogre.callSync(new OgreThread.Callback() {
            @Override
            public void run() {
                renderTexture.removeAllViewports();
                renderTexture.addViewport(camera.getOgreCamera());

//                viewport = renderTexture.getViewport(0);
//                viewport.setClearEveryFrame(true, FrameBufferType.FBT_COLOUR.getValue() | FrameBufferType.FBT_DEPTH.getValue());
                camera.applyBackgroundColor();
//                viewport.setOverlaysEnabled(false);
                renderTexture.setActive(true);
                renderTexture.setAutoUpdated(true);
            }
        });
    }

    @Override
    public int getCaptureWidth() {
        return captureWidth;
    }

    @Override
    public int getCaptureHeight() {
        return captureHeight;
    }

    @Override
    public boolean isSizeChanged() {
        return (textureWidth != round4(captureWidth))
                || (textureHeight != round4(captureHeight));
    }

    @Override
    public byte[] getCaptureData() {
        Ogre.callSync(new CaptureMe());
        return this.pib.getByteBuffer();
    }

    @Override
    public Camera getCamera() {
        return camera;
    }

    private class CaptureMe extends OgreThread.Callback {
        @Override
        public void run() {
            pib = new PixelBox(captureWidth, captureHeight);
            buffer.blitToMemory(pib);
        }
    };
}
