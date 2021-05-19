package domain.persistence;

import domain.user.User;
import domain.user.UserPersistence;
import infrastructure.DBRepository;
import java.sql.SQLException;

public class PersistenceFactory<T, X> {

    private final PersistMethod method;
    private final DBRepository<T, X> db;
    public PersistenceFactory(PersistMethod method, DBRepository<T, X> db) {
        this.method = method;
        this.db = db;
    }

    private Persistence<T> getPersistenceInstance(T entity) {
        switch (method) {
            case USER:
                return (Persistence<T>) new UserPersistence((User) entity, (DBRepository<User, Integer>) db);
            default:
                throw new IllegalStateException("Unexpected value: " + method);
        }
    }

    public T validateAndCommit(T entity) throws SQLException {
        Persistence<T> persistence = getPersistenceInstance(entity);
        if (!persistence.isValid()) {
            throw new InvalidPropertiesOnEntity("Invalid properties for persist with method: " + method.name());
        }
        return persistence.commit();
    }

}

class InvalidPropertiesOnEntity extends RuntimeException {
    private static final long serialVersionUID = -8943565199221117865L;

    public InvalidPropertiesOnEntity(String message) {
        super(message);
    }
}
