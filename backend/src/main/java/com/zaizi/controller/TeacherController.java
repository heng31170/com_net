package com.zaizi.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaizi.pojo.Teacher;
import com.zaizi.service.TeacherService;
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

@Slf4j
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    // 获取全部教师
    @GetMapping("/teachers")
    public List<Teacher> getAllTeachers() {
        return teacherService.getAllTeachers();
    }
    // 根据id获取教师
    @GetMapping("/teacher/{teacherId}")
    public Teacher getTeacherById(@PathVariable Integer teacherId) {
        return teacherService.getTeacherById(teacherId);
    }
    // 添加教师
    // 添加课程
    @PostMapping("/teacher/add")
    public ResponseEntity<?> addTeacher(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam("json") String json) {
        try {
            log.info("添加课程");
            // 解析 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            Teacher teacher = objectMapper.readValue(json, Teacher.class);

            // 如果文件不为空，处理文件上传
            if (file != null && !file.isEmpty()) {
                log.info("接收到文件:{}",file.getOriginalFilename());
                String fileUrl = uploadFile(file,teacher); // 上传文件并获取 URL
                teacher.setAvatar(fileUrl); // 设置员工头像 URL
            } else {
                log.warn("没有接受到文件");
            }

            teacherService.addTeacher(teacher); // 更新员工信息
            return ResponseEntity.ok("success to add teacher");
        } catch (Exception e) {
            log.error("添加教师失败: {}", e.getMessage());
            return ResponseEntity.status(500).body("添加教师失败: " + e.getMessage());
        }
    }
    // 文件上传
    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;
    @Value("${file.serve-dir}")
    private String SERVE_DIR;
    private String uploadFile(MultipartFile file,Teacher teacher) throws Exception {

        // 文件上传逻辑
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);

        // 创建父目录（如果不存在）
        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath);
        return SERVE_DIR +  fileName; // 返回文件 URL
    }
}
