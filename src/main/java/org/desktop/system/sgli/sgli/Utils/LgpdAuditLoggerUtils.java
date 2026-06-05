package org.desktop.system.sgli.sgli.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

public class LgpdAuditLogger {

    private static final DateTimeFormatter FMT =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final Logger LOG = Logger.getLogger("lgpd.audit");

    static {
        try {
            Path dir = resolveDir();
            Files.createDirectories(dir);
            FileHandler fh = new FileHandler(dir.resolve("lgpd-audit.log").toString(), true);
            fh.setFormatter(new SimpleFormatter());
            LOG.addHandler(fh);
            LOG.setUseParentHandlers(false);
        } catch (IOException e) {
            Logger.getLogger(LgpdAuditLogger.class.getName())
                  .warning("LGPD audit log setup failed: " + e.getMessage());
        }
    }

    private LgpdAuditLogger() {}

    public static void logCreate(String entity, String maskedIdentifier) {
        log("CREATE", entity, maskedIdentifier);
    }

    public static void logRead(String entity, String maskedIdentifier) {
        log("READ", entity, maskedIdentifier);
    }

    public static void logUpdate(String entity, String maskedIdentifier) {
        log("UPDATE", entity, maskedIdentifier);
    }

    public static void logDelete(String entity, String maskedIdentifier) {
        log("DELETE", entity, maskedIdentifier);
    }

    public static void logExport(String reportName, int recordCount) {
        LOG.info(String.format("[%s] EXPORT | report=%s | records=%d",
            LocalDateTime.now().format(FMT), reportName, recordCount));
    }

    private static void log(String op, String entity, String id) {
        LOG.info(String.format("[%s] %s | entity=%s | id=%s",
            LocalDateTime.now().format(FMT), op, entity, id));
    }

    private static Path resolveDir() {
        String appData = System.getenv("LOCALAPPDATA");
        if (appData != null) return Paths.get(appData, "SGLI");
        return Paths.get(System.getProperty("user.home"), ".sgli");
    }
}
