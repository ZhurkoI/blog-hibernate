package org.zhurko.blog.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.zhurko.blog.model.Post;
import org.zhurko.blog.model.PostStatus;
import org.zhurko.blog.repository.PostRepository;
import org.zhurko.blog.util.HibernateUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HibernatePostRepositoryImpl implements PostRepository {

    @Override
    public Post getById(Long id) {
        Session session = null;
        Transaction transaction = null;
        Post post = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String hql = "FROM Post P LEFT JOIN FETCH P.labels WHERE P.id = :id AND P.postStatus NOT LIKE :status";
        Query<Post> query = session.createQuery(hql, Post.class);
        query.setParameter("id", id);
        query.setParameter("status", PostStatus.DELETED);
        post = query.uniqueResult();
        transaction.commit();
        session.close();

        return post;
    }

    @Override
    public List<Post> getAll() {
        return getAllPostsInternal();
    }

    @Override
    public Post save(Post post) {
        Post savedPost = null;
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Date date = new Date();
        post.setCreated(date);
        post.setUpdated(date);
        post.setPostStatus(PostStatus.UNDER_REVIEW);
        session.persist(post);

        savedPost = session.get(Post.class, post.getId());
        transaction.commit();
        session.close();

        return savedPost;
    }

    @Override
    public Post update(Post editedPost) {
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Post post = session.get(Post.class, editedPost.getId());
        if (post.getPostStatus() != PostStatus.DELETED) {
            post.setContent(editedPost.getContent());
            post.setUpdated(new Date());
            post.setPostStatus(PostStatus.ACTIVE);
            post.setLabels(editedPost.getLabels());
            session.merge(post);
        }

        transaction.commit();
        session.close();

        return this.getById(post.getId());
    }

    @Override
    public void deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Post post = session.get(Post.class, id);
        if (post != null && post.getPostStatus() != PostStatus.DELETED) {
            post.setPostStatus(PostStatus.DELETED);
            post.setUpdated(new Date());
            session.merge(post);
        }
        transaction.commit();
        session.close();
    }

    private List<Post> getAllPostsInternal() {
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        String hql = "FROM Post P LEFT JOIN FETCH P.labels WHERE P.postStatus NOT LIKE :status ORDER BY P.id ASC";
        Query<Post> query = session.createQuery(hql, Post.class);
        query.setParameter("status", PostStatus.DELETED);
        List<Post> posts = query.getResultList();
        transaction.commit();
        session.close();

        if (posts.isEmpty()) {
            return Collections.emptyList();
        } else {
            return posts;
        }
    }
}