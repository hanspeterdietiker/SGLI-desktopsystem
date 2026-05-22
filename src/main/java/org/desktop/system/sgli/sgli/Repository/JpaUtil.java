package org.desktop.system.sgli.sgli.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public final class JpaUtil {
    private static final String PERSISTENCE_UNIT_NAME = "sgli-persistence-unit";
    private static final String APP_DIRECTORY_NAME = "SGLI";
    private static final String DATABASE_FILE_NAME = "sgli.db";

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = createEntityManagerFactory();

    private JpaUtil() {
        throw new UnsupportedOperationException("Classe JPA utilitaria nao deve ser instanciada");
    }

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }

    public static void initialize() {
        ENTITY_MANAGER_FACTORY.createEntityManager().close();
    }

    public static void close() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    public static Path getDatabasePath() {
        return getApplicationDirectory().resolve(DATABASE_FILE_NAME);
    }

    private static EntityManagerFactory createEntityManagerFactory() {
        createDatabaseDirectory();

        Map<String, String> properties = Map.of(
                "jakarta.persistence.jdbc.url", "jdbc:sqlite:" + getDatabasePath()
        );

        return Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
    }

    private static void createDatabaseDirectory() {
        try {
            Files.createDirectories(getApplicationDirectory());
        } catch (IOException e) {
            throw new IllegalStateException("Erro ao criar diretorio local do banco de dados", e);
        }
    }

    private static Path getApplicationDirectory() {
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null && !localAppData.isBlank()) {
            return Path.of(localAppData, APP_DIRECTORY_NAME);
        }

        return Path.of(System.getProperty("user.home"), "." + APP_DIRECTORY_NAME.toLowerCase());
    }
}
