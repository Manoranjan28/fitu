package org.commonfarm.util;

import org.w3c.dom.Text;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Some basic XML utility methods used by Seraph.
 */
public class XMLUtil {
    /**
     * With a given parent XML Element, find the text contents of the child element with
     * supplied name.
     */
    public static String getContainedText(Node parent, String childTagName) {
        try {
            Node tag = ((Element) parent).getElementsByTagName(childTagName).item(0);
            String text = ((Text) tag.getFirstChild()).getData();
            return text;
        } catch (Exception e) {
            return null;
        }
    }
}
