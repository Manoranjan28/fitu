package org.commonfarm.security.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The PathMapper is used to map file patterns to keys, and find an approriate
 * key for a given file path. The pattern rules are consistent with those defined
 * in the Servlet 2.3 API on the whole. Wildcard patterns are also supported, using
 * any combination of * and ?.
 *
 * <h3>Example</h3>
 *
 * <blockquote><code>
 * PathMapper pm = new PathMapper();<br>
 * <br>
 * pm.put("one","/");<br>
 * pm.put("two","/mydir/*");<br>
 * pm.put("three","*.xml");<br>
 * pm.put("four","/myexactfile.html");<br>
 * pm.put("five","/*\/admin/*.??ml");<br>
 * <br>
 * String result1 = pm.get("/mydir/myfile.xml"); // returns "two";<br>
 * String result2 = pm.get("/mydir/otherdir/admin/myfile.html"); // returns "five";<br>
 * </code></blockquote>
 *
 * @author <a href="mailto:joe@truemesh.com">Joe Walnes</a>
 * @author <a href="mailto:mike@atlassian.com">Mike Cannon-Brookes</a>
 * @author <a href="mailto:hani@formicary.net">Hani Suleiman</a>
 * @version $Revision: 1.1 $
 */
public class PathMapper implements Serializable
{
    private Map mappings = new HashMap();
    private List complexPaths = new ArrayList();
    static String[] DEFAULT_KEYS = {"/", "*", "/*"};

    /** Add a key and appropriate matching pattern. */
    public void put(String key, String pattern) {
        mappings.put(pattern, key);
        if (pattern.indexOf('?') > -1 || (pattern.indexOf("*") > -1 && pattern.length() > 1)) {
            complexPaths.add(pattern);
        }
    }


    /** Retrieve appropriate key by matching patterns with supplied path. */
    public String get(String path) {
        if (path == null) path = "/";
        String mapped = findKey(path, mappings, complexPaths);
        if (mapped == null) return null;
        return (String) mappings.get(mapped);
    }


    /** Retrieve all mappings which match a supplied path. */
    public Collection getAll(String path) {
        if (path == null) path = "/";

        List matches = new ArrayList();

        // find exact keys
        String exactKey = findExactKey(path, mappings);

        if (exactKey != null) matches.add(mappings.get(exactKey));

        // find complex keys
        for (Iterator iterator = findComplexKeys(path, complexPaths).iterator(); iterator.hasNext();) {
            String mapped = (String) iterator.next();
            matches.add(mappings.get(mapped));
        }

        // find default keys
        for (Iterator iterator = findDefaultKeys(mappings).iterator(); iterator.hasNext();) {
            String mapped = (String) iterator.next();
            matches.add(mappings.get(mapped));
        }

        return matches;
    }


    /** Find exact key in mappings. */
    private static String findKey(String path, Map mappings, List keys) {
        String result = findExactKey(path, mappings);
        if (result == null) result = findComplexKey(path, keys);
        if (result == null) result = findDefaultKey(mappings);
        return result;
    }


    /** Check if path matches exact pattern ( /blah/blah.jsp ). */
    private static String findExactKey(String path, Map mappings) {
        if (mappings.containsKey(path)) return path;
        return null;
    }


    /** Find single matching complex key */
    private static String findComplexKey(String path, List complexPaths) {
        int size = complexPaths.size();
        for (int i = 0; i < size; i++) {
            String key = (String) complexPaths.get(i);
            if (match(key, path, false)) {
                return key;
            }
        }
        return null;
    }

    /** Find all matching complex keys */
    private static Collection findComplexKeys(String path, List complexPaths) {
        int size = complexPaths.size();
        List matches = new ArrayList();
        for (int i = 0; i < size; i++) {
            String key = (String) complexPaths.get(i);
            if (match(key, path, false)) {
                matches.add(key);
            }
        }
        return matches;
    }

    /** Look for root pattern ( / ). */
    private static String findDefaultKey(Map mappings) {
        for (int i = 0; i < DEFAULT_KEYS.length; i++) {
            if (mappings.containsKey(DEFAULT_KEYS[i])) return DEFAULT_KEYS[i];
        }
        return null;
    }

    /** Look for root patterns ( / ). */
    private static Collection findDefaultKeys(Map mappings) {
        List matches = new ArrayList();

        for (int i = 0; i < DEFAULT_KEYS.length; i++) {
            if (mappings.containsKey(DEFAULT_KEYS[i])) matches.add(DEFAULT_KEYS[i]);
        }

        return matches;
    }


    private static boolean match(String pattern, String str, boolean isCaseSensitive) {
        char[] patArr = pattern.toCharArray();
        char[] strArr = str.toCharArray();
        int patIdxStart = 0;
        int patIdxEnd = patArr.length - 1;
        int strIdxStart = 0;
        int strIdxEnd = strArr.length - 1;
        char ch;

        boolean containsStar = false;
        for (int i = 0; i < patArr.length; i++) {
            if (patArr[i] == '*') {
                containsStar = true;
                break;
            }
        }

        if (!containsStar) {
            // No '*'s, so we make a shortcut
            if (patIdxEnd != strIdxEnd) {
                return false; // Pattern and string do not have the same size
            }
            for (int i = 0; i <= patIdxEnd; i++) {
                ch = patArr[i];
                if (ch != '?') {
                    if (isCaseSensitive && ch != strArr[i]) {
                        return false;// Character mismatch
                    }
                    if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[i])) {
                        return false; // Character mismatch
                    }
                }
            }
            return true; // String matches against pattern
        }

        if (patIdxEnd == 0) {
            return true; // Pattern contains only '*', which matches anything
        }

        // Process characters before first star
        while ((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?') {
                if (isCaseSensitive && ch != strArr[strIdxStart]) {
                    return false;// Character mismatch
                }
                if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart])) {
                    return false;// Character mismatch
                }
            }
            patIdxStart++;
            strIdxStart++;
        }
        if (strIdxStart > strIdxEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for (int i = patIdxStart; i <= patIdxEnd; i++) {
                if (patArr[i] != '*') {
                    return false;
                }
            }
            return true;
        }

        // Process characters after last star
        while ((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?') {
                if (isCaseSensitive && ch != strArr[strIdxEnd]) {
                    return false;// Character mismatch
                }
                if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxEnd])) {
                    return false;// Character mismatch
                }
            }
            patIdxEnd--;
            strIdxEnd--;
        }
        if (strIdxStart > strIdxEnd) {
            // All characters in the string are used. Check if only '*'s are
            // left in the pattern. If so, we succeeded. Otherwise failure.
            for (int i = patIdxStart; i <= patIdxEnd; i++) {
                if (patArr[i] != '*') {
                    return false;
                }
            }
            return true;
        }

        // process pattern between stars. padIdxStart and patIdxEnd point
        // always to a '*'.
        while (patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
            int patIdxTmp = -1;
            for (int i = patIdxStart + 1; i <= patIdxEnd; i++) {
                if (patArr[i] == '*') {
                    patIdxTmp = i;
                    break;
                }
            }
            if (patIdxTmp == patIdxStart + 1) {
                // Two stars next to each other, skip the first one.
                patIdxStart++;
                continue;
            }
            // Find the pattern between padIdxStart & padIdxTmp in str between
            // strIdxStart & strIdxEnd
            int patLength = (patIdxTmp - patIdxStart - 1);
            int strLength = (strIdxEnd - strIdxStart + 1);
            int foundIdx = -1;
            strLoop:
            for (int i = 0; i <= strLength - patLength; i++) {
                for (int j = 0; j < patLength; j++) {
                    ch = patArr[patIdxStart + j + 1];
                    if (ch != '?') {
                        if (isCaseSensitive && ch != strArr[strIdxStart + i + j]) {
                            continue strLoop;
                        }
                        if (!isCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart + i + j])) {
                            continue strLoop;
                        }
                    }
                }

                foundIdx = strIdxStart + i;
                break;
            }

            if (foundIdx == -1) {
                return false;
            }

            patIdxStart = patIdxTmp;
            strIdxStart = foundIdx + patLength;
        }

        // All characters in the string are used. Check if only '*'s are left
        // in the pattern. If so, we succeeded. Otherwise failure.
        for (int i = patIdxStart; i <= patIdxEnd; i++) {
            if (patArr[i] != '*') {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Mappings:\n");
        for (Iterator iterator = mappings.keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            sb.append(key + "=" + (String) mappings.get(key) + "\n");
        }
        sb.append("Complex Paths:\n");
        for (Iterator iterator = complexPaths.iterator(); iterator.hasNext();) {
            String path = (String) iterator.next();
            sb.append(path + "\n");
        }

        return sb.toString();
    }
}
