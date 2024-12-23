package org.example.blog_system.Repository;

import org.example.blog_system.Model.Blog;
import org.example.blog_system.Model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {

    List<Blog> findAllByUser(MyUser myUser);

    Blog findBlogById(Integer id);

    Blog findBlogByTitle(String title);
}
