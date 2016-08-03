package com.ivymobi.abb.pw.network;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by renguangkai on 2016/7/22.
 */
public class CachedFileMap extends HashMap<String, Boolean> {

    private static CachedFileMap cachedFileMap = null;
    private boolean hasUpdated; // todo static?

    public static CachedFileMap getInstance() {
        if (cachedFileMap == null) {
            synchronized (CachedFileMap.class) {
                if (cachedFileMap == null) {
                    cachedFileMap = new CachedFileMap();
                    cachedFileMap.put(CachedFileEnum.BUSINESS_ABB_INTRO.name(), false);
                    cachedFileMap.put(CachedFileEnum.BUSINESS_ABB_POWER_INTRO.name(), false);
                    cachedFileMap.put(CachedFileEnum.BUSINESS_CASE.name(), false);
                    cachedFileMap.put(CachedFileEnum.BUSINESS_LOCAL.name(), false);
                    cachedFileMap.put(CachedFileEnum.ACTIVITY.name(), false);
                    cachedFileMap.put(CachedFileEnum.DOCUMENT_CENTER.name(), false);
                }
            }
        }
        return cachedFileMap;
    }

    public boolean hasUpdated() {
        System.out.println("===============================================");
        for (Map.Entry<String, Boolean> entry : CachedFileMap.getInstance().entrySet()) {
            String key = entry.getKey().toString();
            Boolean value = entry.getValue().booleanValue();
            hasUpdated |= value;    // 任何一处有更新，即视为有更新
            System.out.println("key == " + key + ", value = " + value);
        }
        return hasUpdated;
    }
}
