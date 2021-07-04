package org.leogenwp.repository.io;

import org.leogenwp.model.Label;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.CollectionUtils.ConnectDB;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JavaIOLabelRepository  implements LabelRepository {
    private final String getAll = "select id, description from labels";
    private final String save = "INSERT INTO labels (description) VALUES ('%s')";
    private final String getById = "select id, description from labels where id = %d";
    private final String update = "UPDATE labels SET description = '%s' where id = %d";
    private final String deleteById = "delete from labels where id = %d";

    @Override
    public List<Label> getAll() {
        List<Label> labels = new ArrayList<>();
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(getAll)) {
            while ( rs.next() ) {
                labels.add(new Label(rs.getInt("id"),rs.getString("description")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return labels;
    }

    @Override
    public Label save(Label label) {
        try(Connection conn= ConnectDB.getInstance().getConnection()){
            Statement statement = conn.createStatement();
            String sql = String.format(save,label.getName());
            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                label.setId(rs.getInt(1));
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

        } catch(Exception e){ System.out.println(e);}
        return label;
    }

    @Override
    public Label getById(Integer id) {
       Label label = new Label();
        String sql =String.format(getById,id);
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql)) {
            while ( rs.next() ) {
                label.setId(rs.getInt("id"));
                label.setName(rs.getString("description"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       return label;
    }

    @Override
    public Label update(Label label) {
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format(update,label.getName(),label.getId());
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return label;
    }

    @Override
    public void deleteById(Integer id) {
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format(deleteById,id);
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
