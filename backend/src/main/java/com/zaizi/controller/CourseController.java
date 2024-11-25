package com.zaizi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaizi.pojo.Course;
import com.zaizi.service.CourseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@Slf4j
public class CourseController {
    @Autowired
    private CourseService courseService;
    // 获取全部课程
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }
    // 条件查询课程
    @GetMapping("/course/search")
    public List<Course> getCourses(@RequestParam(value = "title",required = false) String title,
                                   @RequestParam(value = "category",required = false) String category) {
        return courseService.getCourses(title, category);
    };
    // 根据id查询课程
    @GetMapping("/course/{courseId}")
    public Course getCourseById(@PathVariable Integer courseId) {
        return courseService.getCourseById(courseId);
    }
    // 添加课程
    @PostMapping("/course/add")
    public ResponseEntity<?> addCourse(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam("json") String json) {
        try {
            log.info("添加课程");
            // 解析 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            Course course = objectMapper.readValue(json, Course.class);

            // 如果文件不为空，处理文件上传
            if (file != null && !file.isEmpty()) {
                log.info("接收到文件:{}",file.getOriginalFilename());
                String fileUrl = uploadFile(file,course); // 上传文件并获取 URL
                course.setAvatar(fileUrl); // 设置员工头像 URL
            } else {
                log.warn("没有接受到文件");
            }

            courseService.addCourse(course); // 更新员工信息
            return ResponseEntity.ok("success to add course");
        } catch (Exception e) {
            log.error("添加课程失败: {}", e.getMessage());
            return ResponseEntity.status(500).body("添加课程失败: " + e.getMessage());
        }
    }
    // 文件上传
    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;
    @Value("${file.serve-dir}")
    private String SERVE_DIR;
    private String uploadFile(MultipartFile file,Course course) throws Exception {

        // 文件上传逻辑
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

        // 创建父目录（如果不存在）
        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath);
        return SERVE_DIR +  fileName; // 返回文件 URL
    }
}























