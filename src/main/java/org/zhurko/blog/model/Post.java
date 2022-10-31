package org.zhurko.blog.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "updated", nullable = false)
    private Date updated;

    @ManyToMany (fetch = FetchType.LAZY)    // TODO: у некоторых есть (cascade = { CascadeType.ALL })   ИЛИ  (cascade = {CascadeType.PERSIST}).  Что это?
    @JoinTable(name = "post_label",
            joinColumns = {@JoinColumn(name = "post_id")},
            inverseJoinColumns = {@JoinColumn(name = "label_id")}
    )
    private Set<Label> labels = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="writer_id")
    private Writer writer;

    @Column(name = "post_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    public Post() {
    }

    public Post(String content) {
        this.content = content;
    }

    public Post(Long id, String content, Date created, Date updated, Set<Label> labels, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.postStatus = postStatus;
    }

    public Post(Long id, String content, Date created, Date updated, PostStatus postStatus) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postStatus = postStatus;
    }

    public Post(String content, Date created, Date updated, PostStatus postStatus) {
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.postStatus = postStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Set<Label> getLabels() {
        return labels;
    }

    public void setLabels(Set<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(PostStatus postStatus) {
        this.postStatus = postStatus;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(content, post.content)
                && Objects.equals(created, post.created) && Objects.equals(updated, post.updated)
                && Objects.equals(labels, post.labels) && postStatus == post.postStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels, postStatus);
    }
}
