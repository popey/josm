// License: GPL. For details, see LICENSE file.
package org.openstreetmap.josm.data.preferences;

import static org.openstreetmap.josm.tools.I18n.tr;

import java.io.File;

import javax.swing.JOptionPane;

import org.openstreetmap.josm.Main;
import org.openstreetmap.josm.spi.preferences.Config;
import org.openstreetmap.josm.spi.preferences.IBaseDirectories;
import org.openstreetmap.josm.tools.Logging;

/**
 * Class provides base directory locations for JOSM.
 * @since 13021
 */
public final class JosmBaseDirectories implements IBaseDirectories {

    private JosmBaseDirectories() {
        // hide constructor
    }

    private static class InstanceHolder {
        static final JosmBaseDirectories INSTANCE = new JosmBaseDirectories();
    }

    public static JosmBaseDirectories getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Internal storage for the preference directory.
     */
    private File preferencesDir;

    /**
     * Internal storage for the cache directory.
     */
    private File cacheDir;

    /**
     * Internal storage for the user data directory.
     */
    private File userdataDir;

    @Override
    public File getPreferencesDirectory(boolean createIfMissing) {
        if (preferencesDir == null) {
            String path;
            path = System.getProperty("josm.pref");
            if (path != null) {
                preferencesDir = new File(path).getAbsoluteFile();
            } else {
                path = System.getProperty("josm.home");
                if (path != null) {
                    preferencesDir = new File(path).getAbsoluteFile();
                } else {
                    preferencesDir = Main.platform.getDefaultPrefDirectory();
                }
            }
        }
        if (createIfMissing && !preferencesDir.exists() && !preferencesDir.mkdirs()) {
            Logging.warn(tr("Failed to create missing preferences directory: {0}", preferencesDir.getAbsoluteFile()));
            JOptionPane.showMessageDialog(
                    Main.parent,
                    tr("<html>Failed to create missing preferences directory: {0}</html>", preferencesDir.getAbsoluteFile()),
                    tr("Error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return preferencesDir;
    }

    @Override
    public File getUserDataDirectory(boolean createIfMissing) {
        if (userdataDir == null) {
            String path;
            path = System.getProperty("josm.userdata");
            if (path != null) {
                userdataDir = new File(path).getAbsoluteFile();
            } else {
                path = System.getProperty("josm.home");
                if (path != null) {
                    userdataDir = new File(path).getAbsoluteFile();
                } else {
                    userdataDir = Main.platform.getDefaultUserDataDirectory();
                }
            }
        }
        if (createIfMissing && !userdataDir.exists() && !userdataDir.mkdirs()) {
            Logging.warn(tr("Failed to create missing user data directory: {0}", userdataDir.getAbsoluteFile()));
            JOptionPane.showMessageDialog(
                    Main.parent,
                    tr("<html>Failed to create missing user data directory: {0}</html>", userdataDir.getAbsoluteFile()),
                    tr("Error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return userdataDir;
    }

    @Override
    public File getCacheDirectory(boolean createIfMissing) {
        if (cacheDir == null) {
            String path = System.getProperty("josm.cache");
            if (path != null) {
                cacheDir = new File(path).getAbsoluteFile();
            } else {
                path = System.getProperty("josm.home");
                if (path != null) {
                    cacheDir = new File(path, "cache");
                } else {
                    path = Config.getPref().get("cache.folder", null);
                    if (path != null) {
                        cacheDir = new File(path).getAbsoluteFile();
                    } else {
                        cacheDir = Main.platform.getDefaultCacheDirectory();
                    }
                }
            }
        }
        if (createIfMissing && !cacheDir.exists() && !cacheDir.mkdirs()) {
            Logging.warn(tr("Failed to create missing cache directory: {0}", cacheDir.getAbsoluteFile()));
            JOptionPane.showMessageDialog(
                    Main.parent,
                    tr("<html>Failed to create missing cache directory: {0}</html>", cacheDir.getAbsoluteFile()),
                    tr("Error"),
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return cacheDir;
    }
}
