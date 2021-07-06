package org.leogenwp.repository.io;

import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.model.Writer;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.repository.WriterRepository;
import org.leogenwp.CollectionUtils.ConnectDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JavaIOWriterRepository implements WriterRepository {
    private final LabelRepository labelRepository = new JavaIOLabelRepository();
    private final PostRepository postRepository = new JavaIOPostRepository();

    private final String getAll = "select a.id as writer_id ,a.first_name,a.last_name, b.id as post_id ,content,created,updated,post_status,d.id as label_id ,d.description " +
            "from writers a left join posts b " +
            "on a.id = b.writer_id " +
            "left join posts_labels c " +
            "    on c.post_id = b.id " +
            "    left join labels d" +
            "      on c.label_id = d.id";
    private final String getById = "select a.id as writer_id ,a.first_name,a.last_name, b.id as post_id ,content,created,updated,post_status,d.id as label_id ,d.description " +
            "from writers a left join posts b " +
            "on a.id = b.writer_id " +
            "left join posts_labels c " +
            "    on c.post_id = b.id " +
            "    left join labels d" +
            "      on c.label_id = d.id where a.id = %d";

    private final String save = "INSERT INTO writers (first_name,last_name) values('%s','%s')";

    @Override
    public List<Writer> getAll() {
        List<Writer> writers = new ArrayList<>();

        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(getAll)) {
            while (rs.next()) {
                Writer writer = new Writer();
                writer.setPosts(new ArrayList<Post>());
                if (writers.size() > 0) {
                    if ((rs.getInt(1) == writers.get(writers.size() - 1).getId())) {
                        writer = writers.get(writers.size() - 1);
                    }
                }
                writer.setId(rs.getInt(1));
                writer.setFirstName(rs.getString(2));
                writer.setLastName(rs.getString(3));

                if (rs.getInt(4) != 0) {
                    Post post = new Post();
                    if (writer.getPosts().size() > 0) {
                        if ((rs.getInt(4) == writer.getPosts().get(writer.getPosts().size() - 1).getId())) {
                            post = writer.getPosts().get(writer.getPosts().size() - 1);
                        }
                    }
                    post.setId(rs.getInt(4));
                    post.setContent(rs.getString("content"));
                    post.setCreated(rs.getString("created"));
                    post.setUpdated(rs.getString("updated"));
                    if (rs.getString("post_status").equals("ACTIVE")) {
                        post.setPostStatus(PostStatus.ACTIVE);
                    } else if (rs.getString("post_status").equals("UNDER_REVIEW")) {
                        post.setPostStatus(PostStatus.UNDER_REVIEW);
                    } else if (rs.getString("post_status").equals("DELETED")) {
                        post.setPostStatus(PostStatus.DELETED);
                    }
                    if (rs.getInt(9) != 0) {
                        Label label = new Label(rs.getInt(9), rs.getString(10));
                        post.addLabel(label);
                    }
                    if (writer.getPosts().size() == 0) {
                        writer.getPosts().add(post);
                    } else if ((post.getId() != writer.getPosts().get(writer.getPosts().size() - 1).getId())) {
                        writer.getPosts().add(post);
                    }
                }
                if (writers.size() == 0) {
                    writers.add(writer);
                } else if ((writer.getId() != writers.get(writers.size() - 1).getId())) {
                    writers.add(writer);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writers;
    }

    @Override
    public Writer save(Writer writer) {
        try (Connection conn = ConnectDB.getInstance().getConnection()) {
            Statement statement = conn.createStatement();
            String sql = String.format(save, writer.getFirstName(), writer.getLastName());
            statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    writer.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return writer;
    }

    @Override
    public Writer getById(Integer writerId) {
        List<Writer> writers = new ArrayList<>();
        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            String sql = String.format(getById, writerId);
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Writer writer = new Writer();
                writer.setPosts(new ArrayList<Post>());
                if (writers.size() > 0) {
                    if ((rs.getInt(1) == writers.get(writers.size() - 1).getId())) {
                        writer = writers.get(writers.size() - 1);
                    }
                }
                writer.setId(rs.getInt(1));
                writer.setFirstName(rs.getString(2));
                writer.setLastName(rs.getString(3));

                if (rs.getInt(4) != 0) {
                    Post post = new Post();
                    if (writer.getPosts().size() > 0) {
                        if ((rs.getInt(4) == writer.getPosts().get(writer.getPosts().size() - 1).getId())) {
                            post = writer.getPosts().get(writer.getPosts().size() - 1);
                        }
                    }
                    post.setId(rs.getInt(4));
                    post.setContent(rs.getString("content"));
                    post.setCreated(rs.getString("created"));
                    post.setUpdated(rs.getString("updated"));
                    if (rs.getString("post_status").equals("ACTIVE")) {
                        post.setPostStatus(PostStatus.ACTIVE);
                    } else if (rs.getString("post_status").equals("UNDER_REVIEW")) {
                        post.setPostStatus(PostStatus.UNDER_REVIEW);
                    } else if (rs.getString("post_status").equals("DELETED")) {
                        post.setPostStatus(PostStatus.DELETED);
                    }
                    if (rs.getInt(9) != 0) {
                        Label label = new Label(rs.getInt(9), rs.getString(10));
                        post.addLabel(label);
                    }
                    if (writer.getPosts().size() == 0) {
                        writer.getPosts().add(post);
                    } else if ((post.getId() != writer.getPosts().get(writer.getPosts().size() - 1).getId())) {
                        writer.getPosts().add(post);
                    }
                }
                if (writers.size() == 0) {
                    writers.add(writer);
                } else if ((writer.getId() != writers.get(writers.size() - 1).getId())) {
                    writers.add(writer);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writers.get(0);
    }

    @Override
    public Writer update(Writer writer) {
        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            String sql = String.format("UPDATE writers SET first_name = '%s', last_name = '%s' where id = %d"
                    , writer.getFirstName(), writer.getLastName(), writer.getId());
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            String sql = "UPDATE posts SET writer_id = " + writer.getId() + "  WHERE id = ? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            for (Post post : writer.getPosts()) {
                preparedStatement.setInt(1, post.getId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return writer;
    }

    @Override
    public void deleteById(Integer id) {
        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            String sql = "delete from writers where id = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try (Connection conn = ConnectDB.getInstance().getConnection();
             Statement statement = conn.createStatement()) {
            String sql = "delete writer_id from posts where writer_id = " + id;
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

