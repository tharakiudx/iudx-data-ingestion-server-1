package iudx.data.ingestion.server.apiserver.util;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.file.FileSystem;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

public class Configuration {
    private static final Logger LOG = LogManager.getLogger(Configuration.class);
    private static FileSystem fileSystem;
    private static final String CONFIG_PATH = "./configs/config-dev.json";
    private static File file;
    private static Vertx vertx;
    private static JsonObject moduleConfig;
    private static final int moduleIndex = 2;
    /**
     *  Get ApiServerVerticle config to retrieve base path from config-dev
     */

    public static JsonObject getConfiguration() {
        vertx = Vertx.vertx();
        fileSystem = vertx.fileSystem();
        file = new File(CONFIG_PATH);
        moduleConfig = new JsonObject();
        if (file.exists()) {
            Buffer buffer = fileSystem.readFileBlocking(CONFIG_PATH);
            JsonArray jsonArray = buffer.toJsonObject().getJsonArray("modules");
            moduleConfig = jsonArray.getJsonObject(moduleIndex);
            return moduleConfig;
        } else {
            LOG.error("Couldn't read configuration file : " + CONFIG_PATH);
            return null;
        }
    }

    public static String getBasePath()
    {
        JsonObject jsonObject = getConfiguration();
        if (jsonObject != null)
        {
            return jsonObject.getString("ngsildBasePath");
        }
        else
        {
            return null;
        }
    }
}
