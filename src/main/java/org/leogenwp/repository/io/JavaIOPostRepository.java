package org.leogenwp.repository.io;

import org.leogenwp.model.Label;
import org.leogenwp.model.Post;
import org.leogenwp.model.PostStatus;
import org.leogenwp.repository.LabelRepository;
import org.leogenwp.repository.PostRepository;
import org.leogenwp.CollectionUtils.ConnectDB;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JavaIOPostRepository implements PostRepository {
    private final LabelRepository labelRepository = new JavaIOLabelRepository();
    private final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String getAll = "SELECT b.id as post_id ,content,created,updated,post_status,c.id as label_id ,c.description " +
              "                    FROM posts_labels a right join  posts b" +
              "                    on a.post_id = b.id" +
              "                    left join labels c" +
              "                    on a.label_id = c.id";
    private final String save = "INSERT INTO posts (content,created,updated,post_status) VALUES('%s','%s','%s','%s')";
    private final String getById = "SELECT b.id as post_id ,content,created,updated,post_status,c.id as label_id ,c.description " +
            "                    FROM posts_labels a right join  posts b" +
            "                    on a.post_id = b.id" +
            "                    left join labels c" +
            "                    on a.label_id = c.id where b.id = %d";
    private final String update = "UPDATE posts SET content ='%s',updated = '%s' ,post_status = '%s' where id = %d";
    private final String deleteById = "delete from labels where id = %d";

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()
            ) {
            ResultSet rs = statement.executeQuery(getAll);
            while ( rs.next() ) {
                Post post = new Post();
                post.setLabels(new ArrayList<Label>());
               if (posts.size() > 0) {
                   if ( (rs.getInt(1)==posts.get(posts.size()-1).getId())) {
                       post = posts.get(posts.size()-1);
                   }
               }
                post.setId(rs.getInt(1));
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
                if(rs.getInt(6)!=0){
                    Label label = new Label(rs.getInt(6),rs.getString(7));
                    post.addLabel(label);
                }
                if(posts.size() == 0) {
                    posts.add(post);
                } else if ( (post.getId() !=posts.get(posts.size()-1).getId())) {
                    posts.add(post);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post save(Post post) {
        Date now = new Date();
        String strDate = sdfDate.format(now);
        post.setCreated(strDate);
        post.setUpdated(strDate);
        post.setPostStatus(PostStatus.ACTIVE);

        try(Connection conn= ConnectDB.getInstance().getConnection()){
            Statement statement = conn.createStatement();
            String sql = String.format(save,post.getContent(),post.getCreated(),post.getUpdated(),post.getPostStatus());
            statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch(Exception e){ System.out.println(e);}
        return post;
    }

    @Override
    public Post getById(Integer postId) {
        Post post = new Post();
        post.setLabels(new ArrayList<Label>());
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()
        ) {
            String sql =String.format(getById,postId);
            ResultSet rs = statement.executeQuery(sql);
            while ( rs.next() ) {
                post.setId(rs.getInt(1));
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
                if(rs.getInt(6)!=0){
                    Label label = new Label(rs.getInt(6),rs.getString(7));
                    post.addLabel(label);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post post) {
        Date now = new Date();
        String strDate = sdfDate.format(now);
        post.setUpdated(strDate);

        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql =String.format(update,post.getContent(),post.getUpdated(),post.getPostStatus(),post.getId());
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            statement.executeUpdate("delete from posts_labels where post_id = " + post.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        for(Label label : post.getLabels()) {
            try (Connection conn= ConnectDB.getInstance().getConnection();
                 Statement statement = conn.createStatement()) {
                statement.executeUpdate("INSERT INTO posts_labels (post_id,label_id) " + "VALUES (" + post.getId() + ", " + label.getId() + ")");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return post;
    }

    @Override
    public void deleteById (Integer id){
        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format("delete from posts_labels  where post_id = %d", id);
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try(Connection conn= ConnectDB.getInstance().getConnection();
            Statement statement = conn.createStatement()) {
            String sql = String.format("delete from posts where id = %d",id);
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
