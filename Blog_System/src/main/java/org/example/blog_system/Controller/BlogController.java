package org.example.blog_system.Controller;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.blog_system.ApiResponse.ApiResponse;
import org.example.blog_system.Model.Blog;
import org.example.blog_system.Model.MyUser;
import org.example.blog_system.Service.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/blog")
public class BlogController {

    private final BlogService blogService;

    //@AuthenticationPrincipal --> (user id, username, password)
    @GetMapping("/get/user-blogs")
    public ResponseEntity getAllUserBlogs(@AuthenticationPrincipal MyUser myUser){

        return ResponseEntity.status(200).body(blogService.getAllUserBlogs(myUser.getId()));

    }

    @GetMapping("/get/all-blogs")

    public ResponseEntity getAllBlogs(){
        return ResponseEntity.status(200).body(blogService.getAllBlogs());

    }

    @PostMapping("/add-blog")
    public ResponseEntity addBlog(@AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Blog blog){

        blogService.addBlog(myUser.getId(), blog);
        return ResponseEntity.status(200).body(new ApiResponse("Blog added successfully!"));
    }

    @PutMapping("/update/blog/{blogId}")
    public ResponseEntity updateBlog(@PathVariable Integer blogId, @AuthenticationPrincipal MyUser myUser, @RequestBody @Valid Blog blog){
        blogService.updateBlog(blogId, blog, myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Blog updated successfully!"));

    }

    @DeleteMapping("/delete/blog/{blogId}")
    public ResponseEntity deleteBlog(@PathVariable Integer blogId, @AuthenticationPrincipal MyUser myUser){

        blogService.deleteBlog(blogId, myUser.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Blog deleted successfully !"));
    }


    @GetMapping("/get/blog-byId/{blogId}")
    public ResponseEntity getBlogById(@PathVariable Integer blogId) {
        Blog blog = blogService.getBlogById(blogId);
        return ResponseEntity.status(200).body(blog);
    }

    @GetMapping("/get/blog-byTitle/{title}")
    public ResponseEntity getBlogByTitle(@PathVariable String title) {
        Blog blog = blogService.getBlogByTitle(title);
        return ResponseEntity.ok(blog);
    }









}
