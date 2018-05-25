/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vib.mgik.core.animation;

import java.util.ArrayList;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealVector;
import vib.core.animation.math.Vector3d;
import vib.core.animation.math.VectorNd;

/**
 *
 * @author Jing Huang
 * <gabriel.jing.huang@gmail.com or jing.huang@telecom-paristech.fr>
 */
public class JacobianCov extends IKSolver {

    RealMatrix m_cov;
    RealMatrix m_invcov;
    RealVector m_mu;
    RealVector last_state;
    
    double m_dampling1 = 0.01;
    double m_dampling2 = 0.01;
    public JacobianCov(int maxTries, double targetThreshold, double dampling1, double dampling2) {
        super(maxTries, targetThreshold);
        m_dampling1 = dampling1;
        m_dampling2 = dampling2;
    }

    String getIKSolverName() {
        return "JacobianCov";
    }

    @Override
    public void solveOneStep(Skeleton chain, ArrayList<Vector3d> targets) {
        chain.update();
        int tries = 0;
        RealMatrix jacobian = chain.m_jacobian;
        ArrayRealVector distance = new ArrayRealVector(jacobian.getRowDimension());

        for (int ei = 0; ei < chain.m_endeffectors.size(); ++ei) {
            int endeffectorIdx = chain.m_endeffectors.get(ei);

            Vector3d endpos = chain.m_joint_globalPositions.get(endeffectorIdx);
            Vector3d dis = (targets.get(ei).substract(endpos));
            distance.setEntry(ei * 3 + 0, dis.getEntry(0));
            distance.setEntry(ei * 3 + 1, dis.getEntry(1));
            distance.setEntry(ei * 3 + 2, dis.getEntry(2));
            if (m_activeRoot) {
                for (int j = 0; j < 3; ++j) {
                    Skeleton.Dim dim = chain.m_dims.get(j);
                    Vector3d axis = chain.m_dim_axis.get(dim.m_idx);
                    int lastDim = dim.m_lastIdx;
                    if (lastDim >= 0) {
                        axis = chain.m_dim_globalOrientations.get(lastDim).multiple(axis);
                    }
                    Vector3d axisXYZgradient = axis;
                    jacobian.setEntry(ei * 3 + 0, j, axisXYZgradient.getEntry(0));
                    jacobian.setEntry(ei * 3 + 1, j, axisXYZgradient.getEntry(1));
                    jacobian.setEntry(ei * 3 + 2, j, axisXYZgradient.getEntry(2));
                }
            }

            for (int j = chain.m_startDim4IK; j < chain.m_dims.size(); ++j) {
                if (chain.m_jacobian.getEntry(ei * 3 + 0, j) < 0.1) {
                    continue;
                }

                Skeleton.Dim dim = chain.m_dims.get(j);
                Vector3d jointPos = chain.m_dim_globalPositions.get(dim.m_idx);
                Vector3d boneVector = endpos.substract(jointPos);
                if (boneVector.getNorm() == 0) {
                    continue;
                }
                //boneVector.normalize();
                Vector3d axis = chain.m_dim_axis.get(dim.m_idx);
                int lastDim = dim.m_lastIdx;
                if (lastDim >= 0) {
                    axis = chain.m_dim_globalOrientations.get(lastDim).multiple(axis);
                }
                Vector3d axisXYZgradient = axis.cross(boneVector);

                jacobian.setEntry(ei * 3 + 0, j, axisXYZgradient.getEntry(0));
                jacobian.setEntry(ei * 3 + 1, j, axisXYZgradient.getEntry(1));
                jacobian.setEntry(ei * 3 + 2, j, axisXYZgradient.getEntry(2));
            }
        }

        RealMatrix jacobianTranspose = jacobian.transpose();
        RealMatrix jtj = jacobian.multiply(jacobianTranspose);
        RealMatrix lamdaI = MatrixUtils.createRealIdentityMatrix(jtj.getRowDimension());
        
        double dampling = m_dampling2 * Math.pow(distance.getNorm(), 2);
	RealMatrix a =  MatrixUtils.inverse(jtj.scalarMultiply(2).add(lamdaI.scalarMultiply(m_dampling1)).add( m_invcov.scalarMultiply(dampling)));
	RealVector b = jacobianTranspose.operate(distance).mapMultiply(2).add(m_invcov.operate(m_mu.subtract(last_state)).mapMultiply(dampling));
	RealVector dR = a.operate(b);
        
        
        if (m_activeRoot) {
            for (int i = 0; i < 3; ++i) {
                chain.m_dim_values.set(i, castPiRange(chain.m_dim_values.get(i) + dR.getEntry(i)));
            }
        }
        for (int i = chain.m_startDim4IK; i < chain.m_dims.size(); ++i) {
            chain.m_dim_values.set(i, castPiRange(chain.m_dim_values.get(i) + dR.getEntry(i)));
        }
        chain.update();
    }

}