package resources;

import java.io.Serializable;
import javax.annotation.sql.DataSourceDefinition;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataSourceDefinition(name = "java:app/jdbc/orderViewer",
        className = "org.apache.derby.jdbc.ClientDataSource",
        serverName = "127.0.0.1",
        portNumber = 1527,
        databaseName = "orderViewer",
        user = "app",
        password = "app"
)
@Singleton
public class DataRepositoryConfig implements Serializable {

    @PersistenceContext
    private EntityManager entityManager;

    public DataRepositoryConfig() {
    }

    @Produces
    @DataRepository
    public EntityManager produceEntityManager() {
        return entityManager;
    }

}
