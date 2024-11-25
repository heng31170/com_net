package com.zaizi.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaizi.pojo.Emp;
import com.zaizi.service.EmpService;
import com.zaizi.service.PlusEmpServe;
import com.zaizi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class EmpController {
    @Autowired
    private EmpService empService;

    @Autowired
    private PlusEmpServe plusEmpServe;

    // 更新密码
    @PostMapping("/api/passwd/update")
    public ResponseEntity<?> updatePasswd(@RequestBody Emp emp) {
        log.info("修改密码操作:{}",emp);
        // 检查密码是否为空或包含空格
        if (emp.getPasswd() == null || emp.getPasswd().trim().isEmpty() || emp.getPasswd().contains(" ")) {
            return ResponseEntity.badRequest().body(Map.of("status", "failed", "message", "密码不能包含空格或为空"));
        }
        int res = plusEmpServe.updatePasswd(emp);
        if(res != 0) {
            return ResponseEntity.ok("success to update password");
        } else {
            return ResponseEntity.badRequest().body(Map.of("status","failed","message","failed to update password"));
        }
    }

    // 登录
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody Emp emp) {
        log.info("登录操作,账户名:{}",emp.getAccount());
        Emp e = plusEmpServe.login(emp.getAccount(),emp.getPasswd());
        if(e != null) {
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",e.getId());
            claims.put("name",e.getName());
            claims.put("account",e.getAccount());
            String jwt = JwtUtils.generateJwt(claims);
            return ResponseEntity.ok(Map.of("status","success to login","Emp",e,"jwt",jwt));   // 可以返回用户信息或者token等
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登录失败，用户名或密码错误");
    }

    // 获取所有员工
    @GetMapping("/api/emp")
    public ResponseEntity<?> getAllEmp() {
        log.info("获取员工操作");
        List<Emp> empList = empService.getAllEmp();
        return ResponseEntity.ok(Map.of("empList",empList));
    }
    // 获取分页查询员工
    @GetMapping("/api/emp/page")
    public Page<Emp> getEmpByPage(@RequestParam(value = "current") int current,
                                  @RequestParam(value = "size") int size) {
        log.info("分页查询员工,当前页:{},尺寸:{}",current,size);
        Page<Emp> page = plusEmpServe.getEmpPage(current,size);
        log.info("返回数据:{}",page);
        return page;
    }

    // 根据id删除员工
    @PostMapping("/api/emp/delete")
    public ResponseEntity<?> delEmp(@RequestParam("id") Integer id) {
        log.info("删除员工操作,员工id:{}",id);
        try {
            empService.delEmp(id);
            return ResponseEntity.ok("删除成功");
        } catch (Exception e) {
            log.error("删除员工失败,原因是:" + e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("error:","error"+e.getMessage()));
        }
    }
    // 条件查询员工
    @GetMapping("/api/emp/search")
    public ResponseEntity<?> getEmp(@RequestParam(value = "name",required = false) String name,
                                    @RequestParam(value = "gender",required = false) String gender,
                                    @RequestParam(value = "entryDate",required = false)
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate entryDate,
                                    @RequestParam(value = "position") String position) {
        log.info("查询员工,姓名:{},职位:{},性别:{},入职日期:{}",name,position,gender,entryDate);
        try {
            List<Emp> empList = empService.getEmp(name,gender,entryDate,position);
            return ResponseEntity.ok(Map.of("empList",empList));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error","error:"+e.getMessage()));
        }
    }
    // 根据id查询员工
    @GetMapping("/api/emp/{id}")
    public ResponseEntity<?> getEmpById(@PathVariable Integer id) {
        log.info("根据id查询员工,id:{}",id);
        Emp emp = empService.getEmpById(id);
        return ResponseEntity.ok(emp);
    }
    // 编辑员工
    @Value("${file.upload-dir}")
    private String UPLOAD_DIR;
    @Value("${file.serve-dir}")
    private String SERVE_DIR;
    @PostMapping("/api/emp/update")
    public ResponseEntity<?> updateEmp(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @RequestParam("json") String json) {
        try {
            // 解析 JSON 数据
            ObjectMapper objectMapper = new ObjectMapper();
            Emp emp = objectMapper.readValue(json, Emp.class);

            // 如果文件不为空，处理文件上传
            if (file != null && !file.isEmpty()) {
                log.info("接收到文件:{}",file.getOriginalFilename());
                String fileUrl = uploadFile(file,emp); // 上传文件并获取 URL
                emp.setImage(fileUrl); // 设置员工头像 URL
            } else {
                log.warn("没有接受到文件");
            }

            empService.updateEmp(emp); // 更新员工信息
            return ResponseEntity.ok("success to update emp");
        } catch (Exception e) {
            log.error("更新员工信息失败: {}", e.getMessage());
            return ResponseEntity.status(500).body("更新员工信息失败: " + e.getMessage());
        }
    }
    // 新增员工
    @PostMapping("/api/emp/add")
    public ResponseEntity<?> addEmpAndCheck(@RequestBody Emp emp) {
        log.info("新增员工操作,员工:{}",emp);
        try {
            empService.addEmpAndCheckin(emp);
            return ResponseEntity.ok("success to add emp");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("员工添加失败:",e.getMessage()));
        }
    }


    // 文件上传
    private String uploadFile(MultipartFile file,Emp emp) throws Exception {

        // 文件上传逻辑
        String empDir = String.valueOf(emp.getId());
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//        Path filePath = Paths.get(UPLOAD_DIR).resolve(fileName);
        Path filePath = Paths.get(UPLOAD_DIR).resolve(empDir).resolve(fileName);

        // 创建父目录（如果不存在）
        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath);
        return SERVE_DIR + empDir + "/" + fileName; // 返回文件 URL
    }

}

























