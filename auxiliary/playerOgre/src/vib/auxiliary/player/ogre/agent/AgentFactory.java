/*
 *  This file is part of the auxiliaries of VIB (Virtual Interactive Behaviour).
 */
package vib.auxiliary.player.ogre.agent;

import java.util.HashMap;
import vib.auxiliary.player.ogre.natives.SceneManager;
import vib.auxiliary.player.ogre.natives.SceneNode;
import vib.auxiliary.player.ogre.natives.Quaternion;
import vib.auxiliary.player.ogre.natives.Vector3;
import vib.auxiliary.player.ogre.Ogre;
import vib.auxiliary.player.ogre.agent.autodesk.AutodeskAgent;
import vib.auxiliary.player.ogre.agent.composite.CompositeAgent;
import vib.core.util.xml.XML;
import vib.core.util.xml.XMLParser;
import vib.core.util.xml.XMLTree;

/**
 *
 * @author Andre-Marie Pez
 */
public class AgentFactory {


    /* can not be instanciate */
    private AgentFactory(){}

    private static XMLTree xmlAgentList;
    static {
        XMLParser parser = XML.createParser();
        parser.setValidating(false);
        xmlAgentList = parser.parseFile("./Player/Data/agentList.xml");
    }

    private static void refreshTree() {
        XMLParser parser = XML.createParser();
        parser.setValidating(false);
        XMLTree refreshed = parser.parseFile("./Player/Data/agentList.xml");
        if(refreshed!=null) {
            xmlAgentList = refreshed;
        }
    }

    public static MPEG4Agent instanciate(String id, String agentName, SceneNode parent, SceneManager sceneManager) {
        //refreshTree();

        //search for a definition for agentName
        XMLTree def = findAgentDefinition(agentName);
        if(def==null){//try to load sub-definition
            int dotPos = agentName.lastIndexOf(".");
            while(dotPos>-1 && def==null){
                agentName = agentName.substring(0, dotPos);
                def = findAgentDefinition(agentName);
                dotPos = agentName.lastIndexOf(".");
            }
        }
        if(def==null){//finally, we load Greta
            def = findAgentDefinition("greta");
        }

        if(def!=null){
            if("composite".equalsIgnoreCase(def.getAttribute("type"))){

                XMLTree body = def.findNodeCalled("body");
                String bodyMesh = getMeshName(body);

                XMLTree head = def.findNodeCalled("head");

                XMLTree face = head.findNodeCalled("face");
                String faceMesh = getMeshName(face);
                XMLTree wrinklesMaterial = face.findNodeCalled("wrinkles_material");

                String wrinklesMaterialName = wrinklesMaterial!=null && wrinklesMaterial.hasAttribute("name")?
                        wrinklesMaterial.getAttribute("name") :
                        null;

                CompositeAgent composite = new CompositeAgent(id, parent, sceneManager, bodyMesh, faceMesh, wrinklesMaterialName);

                //face positioning
                composite.setFacePosition(getPosition(face));
                composite.setFaceOrientation(getOrientation(face));
                composite.setFaceScale(getScale(face));
                //face materials
                for(XMLTree child : face.getChildrenElement()){
                    if(child.getName().equalsIgnoreCase("material")){
                        composite.setFaceMaterial(child.getAttribute("name"), getTarget(child));
                    }
                }

                //left eye positioning
                XMLTree leftEye = head.findNodeCalled("eye_left");
                composite.setLeftEyePosition(getPosition(leftEye));
                composite.setLeftEyeOrientation(getOrientation(leftEye));
                composite.setLeftEyeScale(getScale(leftEye));
                //left eye materials
                for(XMLTree child : leftEye.getChildrenElement()){
                    if(child.getName().equalsIgnoreCase("material")){
                        composite.setLeftEyeMaterial(child.getAttribute("name"));
                    }
                }

                //left eye positioning
                XMLTree rightEye = head.findNodeCalled("eye_right");
                composite.setRightEyePosition(getPosition(rightEye));
                composite.setRightEyeOrientation(getOrientation(rightEye));
                composite.setRightEyeScale(getScale(rightEye));
                //left eye materials
                for(XMLTree child : rightEye.getChildrenElement()){
                    if(child.getName().equalsIgnoreCase("material")){
                        composite.setRightEyeMaterial(child.getAttribute("name"));
                    }
                }


                //jaw positioning
                XMLTree jaw = head.findNodeCalled("jaw");
                composite.setJawPosition(getPosition(jaw));
                composite.setJawOrientation(getOrientation(jaw));
                composite.setJawScale(getScale(jaw));

                //head positioning
                composite.setHeadPosition(getPosition(head));
                composite.setHeadOrientation(getOrientation(head));
                composite.setHeadScale(getScale(head));

                //objects
                for(XMLTree child : head.getChildrenElement()){
                    if(child.getName().equalsIgnoreCase("object")){
                        HashMap<String,Integer> materials = new HashMap<String, Integer>();
                        for(XMLTree grandChild : child.getChildrenElement()){
                            if(grandChild.getName().equalsIgnoreCase("material")){
                                materials.put(grandChild.getAttribute("name"), getTarget(grandChild));
                            }
                        }
                        composite.addHeadAccessory(
                                getMeshName(child),
                                getPosition(child),
                                getOrientation(child),
                                getScale(child),
                                materials);
                    }
                }

                //body materials
                for(XMLTree child : body.getChildrenElement()){
                    if(child.getName().equalsIgnoreCase("material")){
                        composite.setBodyMaterial(child.getAttribute("name"), getTarget(child));
                    }
                }

                //global scale
                if(def.hasAttribute("scale")){
                    composite.scale(def.getAttributeNumber("scale"));
                }
                Ogre.updateNodeSync(parent, true, true);
                composite.setVisible(true);
                return composite;
            }
            else if("autodesk".equalsIgnoreCase(def.getAttribute("type"))){
                XMLTree fullBody = def.findNodeCalled("all");
                AutodeskAgent autodesk = new AutodeskAgent(id, parent, sceneManager, fullBody.getAttribute("mesh"));
                if(def.hasAttribute("scale")){
                    autodesk.scale(def.getAttributeNumber("scale"));
                }
                XMLTree wrinkles = def.findNodeCalled("wrinklesmaterial");
                if(wrinkles!=null){
                    autodesk.setWrinklesMaterial(wrinkles.getAttribute("name"), getTarget(wrinkles));
                }
                Ogre.updateNodeSync(parent, true, true);
                autodesk.setVisible(true);
                return autodesk;
            }
        }
        return null;
    }

    private static XMLTree findAgentDefinition(String agentName){
        if(agentName!=null){
            for(XMLTree child : xmlAgentList.getChildrenElement()){
                if("agent".equalsIgnoreCase(child.getName()) && agentName.equalsIgnoreCase(child.getAttribute("name"))){
                    return child;
                }
            }
        }
        return null;
    }

    private static String getMeshName(XMLTree tree){
        return tree.getAttribute("mesh");
    }
    private static Vector3 getPosition(XMLTree tree){
        return getVector(tree.findNodeCalled("position"), 0, 0, 0);
    }
    private static Vector3 getScale(XMLTree tree){
        return getVector(tree.findNodeCalled("scale"), 1, 1, 1);
    }
    private static Quaternion getOrientation(XMLTree tree){
        return getQuaternion(tree.findNodeCalled("rotation"));
    }

    private static int getTarget(XMLTree tree){
        double target = valueOf(tree, "target", Double.NaN);
        return  (target==Double.NaN? 0 : (target<0? -1 : (int)(target+1)));
    }

    private static Vector3 getVector(XMLTree tree, double x, double y, double z){
        return new Vector3(valueOf(tree,"x",x), valueOf(tree,"y",y), valueOf(tree,"z",z));
    }

    private static Quaternion getQuaternion(XMLTree tree){
        Vector3 axe = getVector(tree,1,0,0);
        double angle = valueOf(tree,"angle",0);
        if(tree!=null && tree.hasAttribute("angle_unit") && tree.getAttribute("angle_unit").equalsIgnoreCase("degree")){
            angle = Math.toRadians(angle);
        }
        return new Quaternion(angle, axe);
    }

    private static double valueOf(XMLTree tree, String attributeName, double defaultValue){
        return tree!=null && tree.hasAttribute(attributeName) ? tree.getAttributeNumber(attributeName) : defaultValue;
    }

}