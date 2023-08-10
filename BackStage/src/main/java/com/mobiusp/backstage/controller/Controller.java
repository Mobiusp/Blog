package com.mobiusp.backstage.controller;

import com.mobiusp.backstage.pojo.BlogIndex;
import com.mobiusp.backstage.pojo.Page;
import com.mobiusp.backstage.pojo.Result;
import com.mobiusp.backstage.service.*;
import com.mobiusp.backstage.utils.JWTUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
public class Controller {
    private final LoginService loginService;
    private final PublishService publishService;
    private final BlogIndexService blogIndexService;
    private final CommentService commentService;
    private final FeedbackService feedbackService;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Controller.class);

    @Autowired
    public Controller(LoginService loginService, PublishService publishService, BlogIndexService blogIndexService, CommentService commentService, FeedbackService feedbackService) {
        this.loginService = loginService;
        this.publishService = publishService;
        this.blogIndexService = blogIndexService;
        this.commentService = commentService;
        this.feedbackService = feedbackService;
    }

    @GetMapping("/heartbeat")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result check(HttpServletRequest request, HttpServletResponse response){
        return new Result(200, "OK.",null);
    }

    @PostMapping("/login")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result Login(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        if (!loginService.checkLogin(params)) {
            logger.info(request.getHeader("Origin") + " tried to log in but failed.");
            return new Result(-114514, "The uname or password is incorrect.", null);
        } else logger.info(request.getHeader("Origin") + " logged in successfully.");
        //set-Cookie HttpOnly
        return new Result(200,"Logged in successfully." ,JWTUtil.getToken());
    }

    @PostMapping("/publish")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result Publish(@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        if ( !publishService.publishBlog(params)) {
            logger.warn(request.getHeader("Origin") + " An error occurred while adding a new blog.");
            return new Result(-2, "An error occurred while adding a new blog.", null);
        }
        logger.info(request.getHeader("Origin") + " successfully added a new blog.");
        return new Result(200, "Successfully added a new blog.", null);
    }

    @GetMapping("/getblog/{index}")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result getBlog(@PathVariable("index") String index, HttpServletRequest request, HttpServletResponse response) {
        int ind;
        try {
            ind = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
            return new Result(500, "The param isn't number.", null);
        }
        Page<BlogIndex> blogIndex = blogIndexService.getBlogIndex(ind);
        if (blogIndex == null) {
            return new Result(500, "Exceed the total.",null);
        }
        return new Result(200, "OK.", blogIndex);
    }

    @PostMapping("/update")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result updateBlog (@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        if (! blogIndexService.updateBlog(params)) {
            logger.info(request.getHeader("Origin") + " want to update blog but failed.");
            return new Result(-30, "Update failed.", null);
        }
        logger.info(request.getHeader("Origin") + " updated blog " + params.get("id") + " successfully.");
        return new Result(200, "Update succeeded.", null);
    }

    @PostMapping("/delete")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result deleteBlog (@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        int i;
        try {
            i = Integer.parseInt((String) params.get("id"));
        } catch (NumberFormatException e) {
            logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
            return new Result(500, "The param isn't number.", null);
        }
        if (! blogIndexService.deleteBlog(i)) {
            logger.info(request.getHeader("Origin") + " want to delete blog but failed.");
            return new Result(-31, "Delete failed.", null);
        }
        logger.info(request.getHeader("Origin") + " successfully deleted a blog.");
        return new Result(200, "Delete succeeded.", null);
    }

    @GetMapping("/getcontent/{id}")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result getContent(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) {
        int i;
        try {
            i = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
            return new Result(500, "The param isn't number.", null);
        }
        return new Result(200, "OK.", blogIndexService.getContent(i));
    }

    @GetMapping("/getcomment/{blogid}")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result getComment (@PathVariable("blogid") String blogid, HttpServletRequest request, HttpServletResponse response) {
        int id;
        try {
            id = Integer.parseInt(blogid);
        } catch (NumberFormatException e) {
            logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
            return new Result(500, "The param isn't number.", null);
        }
        logger.info(request.getHeader("Origin") + " getComment.");
        return new Result(200, "OK.", commentService.getComment(id));
    }

    @PostMapping("/deletecomment")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result deleteComment (@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        if ((boolean) params.get("isP")) {
            String []i = ((String) params.get("id")).split("&");
            ArrayList<Integer> te = new ArrayList<>();
            for (String temp : i) {
                try {
                      te.add(Integer.valueOf(temp));
                } catch (NumberFormatException e) {
                    logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
                    return new Result(500, "The param isn't number.", null);
                }
            }
            int []id = te.stream().mapToInt(Integer::intValue).toArray();
            if (! commentService.deleteComment(id)) {
                logger.info(request.getHeader("Origin") + " want to delete comment but failed.");
                return new Result(-31, "Delete failed.", null);
            }
        }else {
            int id;
            try {
                id = Integer.parseInt((String) params.get("id"));
            } catch (NumberFormatException e) {
                logger.warn(request.getHeader("Origin") + " tried to get blogs but sent an error param.");
                return new Result(500, "The param isn't number.", null);
            }
            if (! commentService.deleteComment(id)) {
                logger.info(request.getHeader("Origin") + " want to delete comment but failed.");
                return new Result(-31, "Delete failed.", null);
            }
        }
        logger.info(request.getHeader("Origin") + " successfully deleted a comment.");
        return new Result(200, "Delete succeeded.", null);
    }

    @PostMapping("/newimage")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result newImage (@RequestParam(value = "img") MultipartFile[] file, HttpServletRequest request, HttpServletResponse response) {
        if (file.length == 0) return new Result(-30, "Params error.", null);
        for (MultipartFile i : file) {
            try {
                i.transferTo (new File("/myweb/public/" + i.getOriginalFilename()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Result(200, "OK.", null);
    }

    @GetMapping("/getfeedback")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result getFeedback (HttpServletRequest request, HttpServletResponse response) {
        return new Result(200, "OK.", feedbackService.getFeedback());
    }

    @PostMapping("/changefeedback")
    @CrossOrigin(value = {"https://mobiusp.cc:3939","https://bs.mobiusp.cc"})
    public Result changeFeedback (@RequestBody Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp timestamp = feedbackService.changeFeedback(params);
        String format = df.format(timestamp);
        return new Result(200, "OK.", format);
    }

    @PostMapping("/up")
    @CrossOrigin(value = {"https://mobiusp.cc","https://www.mobiusp.cc"})
    public void up (@RequestBody Map<String, Object> params) {
        if (Objects.equals(params.get("id").getClass(), Integer.class)) {
            DailyViews.up((Integer) params.get("id"));
        }
    }
}
