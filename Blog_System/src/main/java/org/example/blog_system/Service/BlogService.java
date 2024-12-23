package org.example.blog_system.Service;

import lombok.RequiredArgsConstructor;
import org.example.blog_system.ApiResponse.ApiException;
import org.example.blog_system.Model.Blog;
import org.example.blog_system.Model.MyUser;
import org.example.blog_system.Repository.AuthRepository;
import org.example.blog_system.Repository.BlogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final AuthRepository authRepository;


    //Get blogs by specific user only, not all blogs in DB
    public List<Blog> getAllUserBlogs(Integer userId){

        MyUser myUser = authRepository.findMyUserById(userId);

        if(myUser==null)
            throw new ApiException("User not found!");  //optional

        return blogRepository.findAllByUser(myUser);

    }

    public List<Blog> getAllBlogs(){

        return blogRepository.findAll();
    }

    //no one can add without login--> authPrinciple
    public void addBlog(Integer userId, Blog blog){

        MyUser myUser = authRepository.findMyUserById(userId);

        if(myUser == null)
            throw new ApiException("User not found!");  //optional-- because user already log in

        blog.setUser(myUser);  //associated a blog with one user
        blogRepository.save(blog);

    }



    public void updateBlog(Integer blogId, Blog newBlog, Integer userId){

        Blog oldBlog = blogRepository.findBlogById(blogId);

        if(oldBlog ==null){
            throw new ApiException("This blog not found!");
        }

        if(oldBlog.getUser().getId()!= userId){
            throw new ApiException("This user doesn't have this blog to update it!");
        }


        oldBlog.setTitle(newBlog.getTitle());
        oldBlog.setBody(newBlog.getBody());

        blogRepository.save(oldBlog);

    }


    public void deleteBlog(Integer blogId, Integer userId){

        Blog oldBlog = blogRepository.findBlogById(blogId);
        if(oldBlog==null)
            throw new ApiException("Blog not found");

        if(oldBlog.getUser().getId()!= userId){
            throw new ApiException("This user doesn't have this blog to update it!");
        }

        blogRepository.delete(oldBlog);


    }

    public Blog getBlogById(Integer blogId) {
        return blogRepository.findById(blogId).orElseThrow(() ->
                new ApiException("Blog not found!"));
    }

    public Blog getBlogByTitle(String title) {

        String trimmedTitle = title.trim();
        Blog blog = blogRepository.findBlogByTitle(trimmedTitle);

        if (blog == null)
            throw new ApiException("No blog found with the given title!");


        return blog;
    }



















}
