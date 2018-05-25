/*
 * This file is part of VIB (Virtual Interactive Behaviour).
 */
package vib.core.intentions;

import vib.core.util.IniManager;
import vib.core.util.Mode;
import vib.core.util.id.ID;
import java.util.List;

/**
 * This class is an implementation of {@code IntentionPerformer} interface.<br>
 * When {@code performIntentions} function is called, the {@code Intentions}
 * received are saved in a file in FML format.
 *
 * @author Andre-Marie Pez
 */
public class FMLFileWriter implements IntentionPerformer {

    private String fmlfolder;

    /**
     * Constructs an {@code FMLFileWriter} where the FML files will be saved in
     * the directory defined in the global {@code IniManager} as
     * "FML_FOLDER".<br> If this parameter is not set (or empty) the files will
     * be saved in the execution path of the program.
     */
    public FMLFileWriter() {
        this(IniManager.getGlobals().getValueString("FML_FOLDER"));
    }

    /**
     * Constructs an {@code FMLFileWriter} where the FML files will be saved in
     * the specified directory.
     *
     * @param directory the directory where the FML files will be saved
     */
    public FMLFileWriter(String directory) {
        //choose the current folder if directory is null or an empty string
        fmlfolder = directory == null || directory.isEmpty() ? "./" : directory;
        //check slashes (linux comtibility)
        fmlfolder = fmlfolder.replaceAll("\\\\", "/");
        //add one at the end if not present
        fmlfolder += fmlfolder.endsWith("/") ? "" : "/";
    }

    /**
     * Saves a list of {@code Intentions} in a FML file.<br> It will be save in
     * the directory defined by the construtor of this {@code FMLFileWriter},
     * and the name of the file will be the {@code requestId} parameter prefixed
     * by "FML-" and suffixed by the XML extention.
     *
     * @param intentions the list of {@code Intention}
     * @param requestId the identifier of the request
     * @param mode blend, replace, append
     */
    @Override
    public void performIntentions(List<Intention> intentions, ID requestId, Mode mode) {
        FMLTranslator.IntentionsToFML(intentions, mode).save(fmlfolder + "FML-" + requestId + ".xml");
    }
}