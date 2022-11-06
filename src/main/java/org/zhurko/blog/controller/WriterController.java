package org.zhurko.blog.controller;

import org.zhurko.blog.model.Post;
import org.zhurko.blog.model.Writer;
import org.zhurko.blog.repository.hibernate.HibernatePostRepositoryImpl;
import org.zhurko.blog.repository.hibernate.HibernateWriterRepositoryImpl;
import org.zhurko.blog.service.PostService;
import org.zhurko.blog.service.WriterService;

import java.util.List;

public class WriterController {

    private static final String BLANK_INPUT_ERROR_MESSAGE = "Name cannot be zero-length or contain only spaces.";

    private final WriterService writerService = new WriterService(new HibernateWriterRepositoryImpl());
    private final PostService postService = new PostService(new HibernatePostRepositoryImpl());

    public List<Writer> getAll() {
        return writerService.getAll();
    }

    public Writer getWriterById(Long id) {
        return writerService.getById(id);
    }

    public Writer save(String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            System.out.println(BLANK_INPUT_ERROR_MESSAGE);
            return null;
        }

        return writerService.save(new Writer(firstName, lastName));
    }

    public void deleteById(Long id) {
        writerService.deleteById(id);
    }

    public Writer updateWriter(Long id, String firstName, String lastName) {
        if (firstName.isBlank() || lastName.isBlank()) {
            System.out.println(BLANK_INPUT_ERROR_MESSAGE);
            return null;
        }

        Writer writer = writerService.getById(id);
        writer.setFirstName(firstName);
        writer.setLastName(lastName);
        return writerService.update(writer);
    }

    public Writer addPostToWriter(Long writerId, Long postId) {
        Writer writer = writerService.getById(writerId);
        Post post = postService.getById(postId);
        writer.addPost(post);
        postService.save(post);
        return writerService.getById(writerId);
    }

    public Writer removePostFromWriter(Long writerId, Long postId) {
        Writer writer = writerService.getById(writerId);
        Post post = postService.getById(postId);
        writer.removePost(post);
        postService.save(post);
        return writerService.update(writer);
    }
}
