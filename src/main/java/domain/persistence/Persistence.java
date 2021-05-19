package domain.persistence;

import java.sql.SQLException;

public interface Persistence<T> {

    boolean isValid();

    T commit() throws SQLException;

}
