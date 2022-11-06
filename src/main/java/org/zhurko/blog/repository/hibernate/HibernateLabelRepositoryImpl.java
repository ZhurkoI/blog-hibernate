package org.zhurko.blog.repository.hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.zhurko.blog.model.Label;
import org.zhurko.blog.repository.LabelRepository;
import org.zhurko.blog.util.HibernateUtil;

import java.util.Collections;
import java.util.List;

public class HibernateLabelRepositoryImpl implements LabelRepository {

    @Override
    public Label save(Label label) {
        Label ifExistentLabel = findByName(label.getName());
        if (ifExistentLabel != null) {
            return ifExistentLabel;
        }

        Label savedLabel = null;
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.persist(label);

        savedLabel = session.get(Label.class, label.getId());
        transaction.commit();
        session.close();

        return savedLabel;
    }

    @Override
    public Label getById(Long id) {
        Label label = null;
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        label = session.get(Label.class, id);
        transaction.commit();
        session.close();

        return label;
    }

    @Override
    public List<Label> getAll() {
        return getAllLabelsInternal();
    }

    @Override
    public Label update(Label label) {
        Label updatedLabel = null;

        Label ifExistentLabel = findByName(label.getName());
        if (ifExistentLabel != null) {
            System.out.printf("Label with name = '%s' already exists. Nothing was updated.%n", ifExistentLabel.getName());
            return ifExistentLabel;
        }

        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        updatedLabel = session.merge(label);

        transaction.commit();
        session.close();

        return updatedLabel;
    }

    @Override
    public void deleteById(Long id) {
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Label label = session.get(Label.class, id);
        if (label != null) {
            session.remove(label);
        }
        transaction.commit();
        session.close();
    }

    private List<Label> getAllLabelsInternal() {
        List<Label> labels = null;
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        labels = session.createQuery("FROM Label", Label.class).getResultList();
        transaction.commit();
        session.close();

        if (labels.isEmpty()) {
            return Collections.emptyList();
        } else {
            return labels;
        }
    }

    @Override
    public Label findByName(String name) {
        Label label = null;
        Session session = null;
        Transaction transaction = null;

        session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Label> cr = cb.createQuery(Label.class);
        Root<Label> root = cr.from(Label.class);
        cr.select(root).where(cb.equal(root.get("name"), name));

        Query<Label> query = session.createQuery(cr);
        List<Label> results = query.getResultList();

        if (!results.isEmpty()) {
            label = results.get(0);
        }

        transaction.commit();
        session.close();

        return label;
    }
}
