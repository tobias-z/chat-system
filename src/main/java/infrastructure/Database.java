package infrastructure;

import java.sql.SQLException;
import java.util.List;

public interface Database<T> {

    T persist(T entity) throws SQLException;

    T getById(int id) throws SQLException;

    List<T> getAll() throws SQLException;

    List<T> createQuery(String query) throws SQLException;

}
