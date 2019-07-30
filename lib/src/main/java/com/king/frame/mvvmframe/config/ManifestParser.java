package com.king.frame.mvvmframe.config;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * @see <a href="https://github.com/bumptech/glide/blob/f7d860412f061e059aa84a42f2563a01ac8c303b/library/src/main/java/com/bumptech/glide/module/ManifestParser.java">Glide</a>
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public final class ManifestParser {
    private static final String CONFIG_MODULE_VALUE = "FrameConfigModule";

    private final Context context;

    public ManifestParser(Context context) {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public List<FrameConfigModule> parse() {
        Timber.d("Loading MVVMFrame modules");
        List<FrameConfigModule> modules = new ArrayList<>();
        try {
            ApplicationInfo appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData == null) {
                    Timber.d( "Got null app info metadata");
                return modules;
            }
            Timber.v("Got app info metadata: " + appInfo.metaData);
            for (String key : appInfo.metaData.keySet()) {
                if (CONFIG_MODULE_VALUE.equals(appInfo.metaData.get(key))) {
                    modules.add(parseModule(key));
                    Timber.d( "Loaded MVVMFrame module: " + key);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException("Unable to find metadata to parse FrameConfigModules", e);
        }
        Timber.d( "Finished loading MVVMFrame modules");

        return modules;
    }

    @SuppressWarnings("deprecation")
    private static FrameConfigModule parseModule(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to find FrameConfigModule implementation", e);
        }

        Object module;
        try {
            module = clazz.newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("Unable to instantiate FrameConfigModule implementation for " + clazz,
                    e);
            // These can't be combined until API minimum is 19.
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to instantiate FrameConfigModule implementation for " + clazz,
                    e);
        }

        if (!(module instanceof FrameConfigModule)) {
            throw new RuntimeException("Expected instanceof FrameConfigModule, but found: " + module);
        }
        return (FrameConfigModule) module;
    }
}
