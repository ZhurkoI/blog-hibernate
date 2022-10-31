package org.zhurko.blog.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.zhurko.blog.model.Post;
import org.zhurko.blog.model.Writer;
import org.zhurko.blog.repository.WriterRepository;
import org.zhurko.blog.util.HibernateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// todo: тест -удалить writer c приасайненными post
public class HibernateWriterRepositoryImpl implements WriterRepository {

//    private static final String SAVE_WRITER = "INSERT INTO writers (first_name, last_name) VALUES (?, ?)";
//    private static final String GET_ALL_WRITERS = "SELECT * FROM writers ORDER BY id ASC";
//    private static final String GET_POSTS_CREATED_BY_WRITER = "SELECT * FROM posts " +
//            "WHERE writer_id = ? AND post_status NOT LIKE 'DELETED'";
//    private static final String GET_WRITER_BY_ID = "SELECT * FROM writers WHERE id = ?";
//    private static final String UPDATE_WRITER = "UPDATE writers SET first_name = ?, last_name = ? WHERE id = ?";
    private static final String ADD_POST_TO_WRITER = "UPDATE posts SET writer_id = ? WHERE id = ?";
    private static final String REMOVE_POST_FROM_WRITER = "UPDATE posts SET writer_id = NULL WHERE id = ?";
//    private static final String DELETE_WRITER_BY_ID = "DELETE FROM writers WHERE id = ?";

    @Override
    public Writer getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        Writer writer = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        String hql = "FROM Writer W LEFT JOIN FETCH W.posts WHERE W.id = :id ORDER BY W.id ASC";
        Query<Writer> query = session.createQuery(hql, Writer.class);
        query.setParameter("id", id);
        writer = query.uniqueResult();
        transaction.commit();
        session.close();

        return writer;
    }

    @Override
    public List<Writer> getAll() {
        return getAllWritersInternal();
    }

    @Override
    public Writer save(Writer writer) {
        Session session = null;
        Transaction transaction = null;
        Writer savedWriter = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.persist(writer);
        savedWriter = session.get(Writer.class, writer.getId());
        transaction.commit();
        session.close();

        return savedWriter;
    }

    @Override
    public Writer update(Writer editedWriter) {
//        Set<Post> existentPostsOfWriters = getPostsCreatedByWriter(writer);
//
//        try (PreparedStatement updateWriter = JdbcUtils.getPreparedStatement(UPDATE_WRITER);
//             PreparedStatement addPost = JdbcUtils.getPreparedStatement(ADD_POST_TO_WRITER);
//             PreparedStatement removePost = JdbcUtils.getPreparedStatement(REMOVE_POST_FROM_WRITER)) {
//
//            JdbcUtils.getConnection().setAutoCommit(false);
//
//            updateWriter.setString(1, writer.getFirstName());
//            updateWriter.setString(2, writer.getLastName());
//            updateWriter.setLong(3, writer.getId());
//            updateWriter.executeUpdate();
//
//            if (writer.getPosts().size() > existentPostsOfWriters.size()) {
//                writer.getPosts().removeAll(existentPostsOfWriters);
//                Iterator<Post> iterator = writer.getPosts().iterator();
//                Post newPost = iterator.next();
//                addPost.setLong(1, writer.getId());
//                addPost.setLong(2, newPost.getId());
//                addPost.executeUpdate();
//            } else if (writer.getPosts().size() < existentPostsOfWriters.size()) {
//                existentPostsOfWriters.removeAll(writer.getPosts());
//                Post extraPost = existentPostsOfWriters.iterator().next();
//                removePost.setLong(1, extraPost.getId());
//                removePost.executeUpdate();
//            }
//            JdbcUtils.getConnection().commit();
//            updatedWriter = getById(writer.getId());
//        } catch (SQLException ex) {
//            System.out.println("Cannot save data to DB.");
//        }
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, editedWriter.getId());
        writer.setFirstName(editedWriter.getFirstName());
        writer.setLastName(editedWriter.getLastName());
        writer.setPosts(editedWriter.getPosts());
        session.merge(writer);
        transaction.commit();
        session.close();

        return this.getById(writer.getId());
    }

    @Override
    public void deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Writer writer = session.get(Writer.class, id);
        if (writer != null) {
            session.remove(writer);
        }
        transaction.commit();
        session.close();
    }

    private List<Writer> getAllWritersInternal() {
        Session session = null;
        Transaction transaction = null;
        List<Writer> writers;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String hql = "FROM Writer W LEFT JOIN FETCH W.posts ORDER BY W.id ASC";
        Query<Writer> query = session.createQuery(hql, Writer.class);
        writers = query.getResultList();
        transaction.commit();
        session.close();

        if (writers.isEmpty()) {
            return Collections.emptyList();
        } else {
            return writers;
        }
    }

    ///////////////////  DELETE  //////////////////
//    public Writer addPostToWriter (Writer editedWriter) {
//        Session session = null;
//        Transaction transaction = null;
//
//        session = HibernateUtil.getSessionFactory().openSession();
//        transaction = session.beginTransaction();
//
//        Writer writer = session.get(Writer.class, editedWriter.getId());
////        writer.addPost(editedWriter.getPost());
//        session.merge(writer);
//        transaction.commit();
//        session.close();
//
//        return this.getById(writer.getId());
//
//    }

}
