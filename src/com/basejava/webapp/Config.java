package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import java.io.*;
import java.util.Properties;

public class Config {
    protected static final File PROPS = new File(getHomeDir(), "config/resumes.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;
    private String dbUrl;
    private String dbUser;
    private String dbPassword;

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    private static File getHomeDir() {
        String props = System.getProperty("homeDir");
        File homeDir = new File(props == null ? "." : props);
        if (!homeDir.isDirectory()){
            throw new IllegalStateException(homeDir + " does't directory");
        }
        return homeDir;
    }
}