package com.mobiusp.server.controller;

import com.mobiusp.server.pojo.*;
import com.mobiusp.server.service.BlogService;
import com.mobiusp.server.service.FeedbackService;
import com.mobiusp.server.service.IndexService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class Controller {
    private final IndexService indexService;
    private final BlogService blogService;
    private final FeedbackService feedbackService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(IndexService indexService, BlogService blogService, FeedbackService feedbackService) {
        this.indexService = indexService;
        this.blogService = blogService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/first")
    @RequestMapping("/first")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result indexGetBlogs(HttpServletRequest request){
        return new Result(200, "OK.", indexService.getBlogs());
    }

    @GetMapping("/blogcontent/{blogid}")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result getBlogContent(@PathVariable("blogid") String blogid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(blogid.length() != 5) return new Result(404, "Blog id error.", null);
        try {
            return new Result(200, "OK.", blogService.getBlogContent(Integer.valueOf(blogid)));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/blogtitle/{blogid}")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result getBlogTitle(@PathVariable("blogid") String blogid, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(blogid.length() != 5) return new Result(404, "Blog id error.", null);
        try {
            return new Result(200, "OK.", blogService.getBlogTitle(Integer.valueOf(blogid)));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/blogcomment/{blogid}")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result getComment(@PathVariable("blogid") String blogid, HttpServletRequest request, HttpServletResponse response){
        if(blogid.length() != 5) return new Result(404, "Blog id error.", null);
        try {
            return new Result(200, "OK.", blogService.getComment(Integer.valueOf(blogid)));
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/newpcomment")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result newParentComment(@RequestBody Map<String, Object> params,HttpServletRequest request){
        if (blogService.newParentComment(params)) {
            return new Result(200, "OK.",null);
        }
        return new Result(-1, "Failed.",null);
    }

    @PostMapping("/newccomment")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result newChildComment(@RequestBody Map<String, Object> params,HttpServletRequest request){
        if (blogService.newChildComment(params)) {
            return new Result(200, "OK.", null);
        }
        return new Result(-1, "Failed.", null);
    }

    @GetMapping("/getblogpage/{ind}")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result getBlogPage (@PathVariable("ind") String ind, HttpServletRequest request, HttpServletResponse response) {
        int index;
        try {
            index = Integer.parseInt(ind);
        } catch (NumberFormatException e) {
            return new Result(500, "Param is not number.", null);
        }
        Page<Blog> blogPage = blogService.getBlogPage(index);
        if (blogPage != null) {
            return new Result(200, "OK.", blogPage);
        }
        return new Result(404, "Exceed range.",null);
    }

    @PostMapping("/searchblog")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result searchBlog (@RequestBody Map<String, Object> params,HttpServletRequest request) {
        if (params.get("key") == null || params.get("index") == null) return new Result(500, "Params error.", null);
        int ind = (int) params.get("index");
        Page<Blog> data = blogService.searchBlog((String) params.get("key"), ind);
        if (data == null) return new Result(404, "Exceed range.", null);
        return new Result(200, "OK.", data);
    }

    @PostMapping("/newfeedback")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public  Result newFeedback (@RequestBody Map<String, Object> params,HttpServletRequest request) {
        if (!feedbackService.newFeedback(params)) return new Result(500, "New Feedback error.", null);
        return new Result(200, "OK.", null);
    }

    @PostMapping("/newfeedbackimg")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public Result newFeedbackImg (@RequestParam(value = "img") MultipartFile[] file, HttpServletRequest request) {
        if (file.length > 3) return new Result(500, "Too many img.", null);
        if (file.length == 0) return new Result(500, "Params error.", null);
        for (MultipartFile i : file) {
            try {
                byte[] bytes = i.getBytes();
                FileOutputStream fos = new FileOutputStream("/backstage/public/" + i.getOriginalFilename());
                fos.write(bytes);
                fos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Result(200, "OK.", null);
    }
}
