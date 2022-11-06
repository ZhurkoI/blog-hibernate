package org.zhurko.blog.repository.hibernate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.zhurko.blog.model.Writer;
import org.zhurko.blog.repository.WriterRepository;
import org.zhurko.blog.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class HibernateWriterRepositoryImpl implements WriterRepository {

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
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();

        Writer writer = session.get(Writer.class, editedWriter.getId());
        writer.setFirstName(editedWriter.getFirstName());
        writer.setLastName(editedWriter.getLastName());
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
}
