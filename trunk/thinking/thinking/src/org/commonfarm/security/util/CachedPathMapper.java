package org.commonfarm.security.util;

import java.util.Map;
import java.util.Collections;
import java.util.Collection;


/**
 * Caches the results of the {@link PathMapper}
 */
public class CachedPathMapper extends PathMapper {
    private Map cacheMap;
    private Map cacheAllMap;

    /**
     * Creates a CachedPathMapper object that will use cacheMap to cache the
     * results of the {@link #get(String)} calls and cacheAllMap to cache the
     * results of the {@link #getAll(String)} class.
     * @param cacheMap
     * @param cacheAllMap
     */
    public CachedPathMapper(Map cacheMap, Map cacheAllMap) {
        // Synchronize access to the map for muti-threaded access
        this.cacheMap = Collections.synchronizedMap(cacheMap);
        this.cacheAllMap = Collections.synchronizedMap(cacheAllMap);
    }

    public String get(String path) {
        // Check the cache
        if (cacheMap.containsKey(path)) {
            // The result for this path is cached, return the value
            return (String) cacheMap.get(path);
        }
        // Get the result from PathMapper
        final String result = super.get(path);
        // Cache the result
        cacheMap.put(path, result);
        return result;
    }

    public Collection getAll(String path) {
        // Check the cache
        if (cacheAllMap.containsKey(path)) {
            // The result for this key is cached, return the value
            return (Collection) cacheAllMap.get(path);
        }
        // Get the result from PathMapper
        final Collection result = super.getAll(path);
        // Cache the result
        cacheAllMap.put(path, result);
        return result;
    }

    public void put(String key, String pattern) {
        // Check if we have the entry in the caches
        if (cacheMap.containsKey(key)) {
            cacheMap.remove(key);
        }
        if (cacheAllMap.containsKey(key)) {
            cacheAllMap.remove(key);
        }
        // Let PathMapper update the patterns
        super.put(key, pattern);
    }
}
