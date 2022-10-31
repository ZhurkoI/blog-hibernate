package org.zhurko.blog;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.zhurko.blog.model.Label;
import org.zhurko.blog.model.Post;
import org.zhurko.blog.model.PostStatus;
import org.zhurko.blog.model.Writer;
import org.zhurko.blog.view.MainView;

import java.sql.Timestamp;
import java.util.Date;

public class Main {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        MainView mainView = new MainView();
        mainView.runMainMenu();
    }

//    public static void main(String[] args) {
//        sessionFactory = new Configuration().configure().buildSessionFactory();
//
//        Main main = new Main();
//
//        System.out.println("Adding labels:");
//        main.addLabel("foo label 1");
//        main.addLabel("foo label 2");
//
//        System.out.println();
//        System.out.println("Adding writers:");
//        main.addWriter("John", "Doe");
//        main.addWriter("igor", "zhurko");
//
//        System.out.println();
//        System.out.println("Adding posts:");
//        main.addPost("Post A");
//        main.addPost("Post B");
//
//        sessionFactory.close();
//    }
//
//    public void addLabel (String name) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = null;
//
//        transaction = session.beginTransaction();
//        Label label = new Label(name);
//        session.persist(label);
//        transaction.commit();
//        session.close();
//    }
//
//    private void addWriter(String firstName, String lastName) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = null;
//
//        transaction = session.beginTransaction();
//        Writer writer = new Writer(firstName, lastName);
//        session.persist(writer);
//        transaction.commit();
//        session.close();
//    }
//
//    private void addPost(String postContent) {
//        Session session = sessionFactory.openSession();
//        Transaction transaction = null;
//        Date date = new Date();
//        Timestamp timestamp = new Timestamp(date.getTime());
//
//        transaction = session.beginTransaction();
//        Post post = new Post(postContent, timestamp, timestamp, PostStatus.UNDER_REVIEW);
//        session.persist(post);
//        transaction.commit();
//        session.close();
//    }
}
