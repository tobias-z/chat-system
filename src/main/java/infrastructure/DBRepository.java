package infrastructure;

import java.sql.SQLException;
import java.util.List;

public interface DBRepository<T, PrimaryKey> {

    T persist(T entity) throws SQLException;

    T getByPrimaryKey(PrimaryKey primaryKey) throws SQLException;

    List<T> getAll() throws SQLException;

    List<T> createQuery(String query) throws SQLException;

}
