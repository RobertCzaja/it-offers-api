package pl.api.itoffers.shared.persistence.hibernate;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;

public class CustomStringArrayType implements UserType<String[]> {
    @Override
    public int getSqlType() {
        return Types.ARRAY;
    }

    @Override
    public Class returnedClass() {
        return String[].class;
    }

    @Override
    public boolean equals(String[] strings, String[] j1) {
        return false; // TODO ?
    }

    @Override
    public int hashCode(String[] strings) {
        return 0; // TODO ?
    }

    @Override
    public String[] nullSafeGet(ResultSet resultSet, int position, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws SQLException {
        Array array = resultSet.getArray(position);
        return array != null ? (String[]) array.getArray() : null;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, String[] value, int index, SharedSessionContractImplementor session) throws SQLException {
        if (preparedStatement != null) {
            if (value != null) {
                Array array = session.getJdbcConnectionAccess().obtainConnection()
                        .createArrayOf("text", value);
                preparedStatement.setArray(index, array);
            } else {
                preparedStatement.setNull(index, Types.ARRAY);
            }
        }
    }

    @Override
    public String[] deepCopy(String[] strings) {
        return new String[0]; // TODO ?
    }

    @Override
    public boolean isMutable() {
        return false; // TODO ?
    }

    @Override
    public Serializable disassemble(String[] strings) {
        return null; // TODO ?
    }

    @Override
    public String[] assemble(Serializable serializable, Object o) {
        return new String[0]; // TODO ?
    }
}
