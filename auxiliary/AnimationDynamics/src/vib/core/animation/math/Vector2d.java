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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vib.core.animation.math;

import org.apache.commons.math3.linear.RealVector;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
/**
 *
 * @author Jing Huang
 * <gabriel.jing.huang@gmail.com or jing.huang@telecom-paristech.fr>
 */
public class Vector2d extends VectorNd<Vector2d> {
    public Vector2d() {
        super(2);
        this.setEntry(0, 0);
        this.setEntry(1, 0);
    }

    public Vector2d(RealVector v) {
        super(2);
        this.setEntry(0, v.getEntry(0));
        this.setEntry(1, v.getEntry(1));
    }

    public Vector2d(double x, double y) {
        super(2);
        this.set(x, y);
    }
    
    public Vector2d(double[] v) {
        super(2);
        this.set(v[0], v[1]);
    }

    public void set(double x, double y) {
        this.setEntry(0, x);
        this.setEntry(1, y);
    }

 
        
    public Vector2d cross(Vector3d v){
        Vector2d c = new Vector2d(getEntry(1) * v.getEntry(2) - getEntry(2) * v.getEntry(1),
                                  getEntry(2) * v.getEntry(0) - getEntry(0) * v.getEntry(2)
        );
        return c;
    }

    @Override
    public Vector2d copyData(RealVector arv) {
        return new Vector2d(arv);
    }
    
    public void normalize(){
        double norm = this.getNorm();
        this.setEntry(0, getEntry(0) / norm);
        this.setEntry(1, getEntry(1) / norm);
    }
    
    public static Vector2d zero(){
        return new Vector2d();
    }
    
}
