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
package vib.core.animation.performer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import vib.core.animation.mpeg4.bap.BAPFrame;
import vib.core.animation.mpeg4.bap.BAPFramesEmitter;
import vib.core.animation.mpeg4.bap.BAPFramesPerformer;
import vib.core.animation.mpeg4.bap.BAPType;
import vib.core.util.id.ID;
import vib.core.util.time.Timer;

/**
 *
 * @author Brian Ravenet
 */
public class BodyAnimationArmNoise implements BAPFramesEmitter, BAPFramesPerformer {

    private final List<BAPFramesPerformer> bapFramesPerformers = new ArrayList<BAPFramesPerformer>();
    private double leftShoulderTimer = 0;
    private double rightShoulderTimer = 0;
    private double leftElbowFlexTimer = 0;
    private double rightElbowFlexTimer = 0;
    private double leftElbowTwistTimer = 0;
    private double rightElbowTwistTimer = 0;

    public BodyAnimationArmNoise() {

    }

    @Override
    public void addBAPFramesPerformer(BAPFramesPerformer bapFramesPerformer) {
        if (bapFramesPerformer != null) {
            bapFramesPerformers.add(bapFramesPerformer);
        }
    }

    @Override
    public void removeBAPFramesPerformer(BAPFramesPerformer bapFramesPerformer) {
        if (bapFramesPerformer != null) {
            bapFramesPerformers.remove(bapFramesPerformer);
        }
    }

    @Override
    public void performBAPFrames(List<BAPFrame> list, ID id) {
        //
        Random r = new Random();
        for (BAPFrame bf : list) {
            leftShoulderTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.l_shoulder_flexion, (int) (bf.getValue(BAPType.l_shoulder_flexion) * (1 + Math.sin(leftShoulderTimer) / 40)));
            rightShoulderTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.r_shoulder_flexion, (int) (bf.getValue(BAPType.r_shoulder_flexion) * (1 + Math.sin(rightShoulderTimer) / 40)));
            leftElbowFlexTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.l_elbow_flexion, (int) (bf.getValue(BAPType.l_elbow_flexion) * (1 + Math.sin(leftElbowFlexTimer) / 40)));
            rightElbowFlexTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.r_elbow_flexion, (int) (bf.getValue(BAPType.r_elbow_flexion) * (1 + Math.sin(rightElbowFlexTimer) / 40)));
            leftElbowTwistTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.l_elbow_twisting, (int) (bf.getValue(BAPType.l_elbow_twisting) * (1 + Math.sin(leftElbowTwistTimer) / 40)));
            rightElbowTwistTimer += r.nextDouble() / 10;
            bf.setValue(BAPType.r_elbow_twisting, (int) (bf.getValue(BAPType.r_elbow_twisting) * (1 + Math.sin(rightElbowTwistTimer) / 40)));

            // leftShoulderFlex = leftShoulderFlex * ((Math.sin(leftElbowFlex)+1)/2);
        }

        for (BAPFramesPerformer bfp : bapFramesPerformers) {
            bfp.performBAPFrames(list, id);
        }//To change body of generated methods, choose Tools | Templates.
    }

    // Function to linearly interpolate between a0 and a1
    // Weight w should be in the range [0.0, 1.0]
    private double lerp(double a0, double a1, double w) {
        return (1.0 - w) * a0 + w * a1;
    }

}
