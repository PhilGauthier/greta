/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package vib.core.animation.rbdl;

import vib.core.animation.math.SpatialVector6d;
import vib.core.animation.math.SpatialMatrix6d;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jing Huang
 */
public class SpatialRigidBodyInertiaTest {
    
    public SpatialRigidBodyInertiaTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of multiple method, of class SpatialRigidBodyInertia.
     */
    @Test
    public void testMultiple() {
        System.out.println("multiple");
        SpatialVector6d mv = null;
        SpatialRigidBodyInertia instance = new SpatialRigidBodyInertia();
        SpatialVector6d expResult = null;
        SpatialVector6d result = instance.multiple(mv);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of add method, of class SpatialRigidBodyInertia.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        SpatialRigidBodyInertia srbi = null;
        SpatialRigidBodyInertia instance = new SpatialRigidBodyInertia();
        SpatialRigidBodyInertia expResult = null;
        SpatialRigidBodyInertia result = instance.add(srbi);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createFromMatrix method, of class SpatialRigidBodyInertia.
     */
    @Test
    public void testCreateFromMatrix() {
        System.out.println("createFromMatrix");
        SpatialMatrix6d Ic = null;
        SpatialRigidBodyInertia instance = new SpatialRigidBodyInertia();
        instance.createFromMatrix(Ic);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toMatrix method, of class SpatialRigidBodyInertia.
     */
    @Test
    public void testToMatrix() {
        System.out.println("toMatrix");
        SpatialRigidBodyInertia instance = new SpatialRigidBodyInertia();
        SpatialMatrix6d expResult = null;
        SpatialMatrix6d result = instance.toMatrix();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class SpatialRigidBodyInertia.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        SpatialRigidBodyInertia instance = new SpatialRigidBodyInertia();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}