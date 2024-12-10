<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">注册</el-header>
            <el-main style="display: flex; align-items: center; justify-content: center">
                <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="100px"
                    class="demo-ruleForm">
                    <el-form-item label="账号" prop="username">
                        <el-input v-model="registerForm.username" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="passwd">
                        <el-input type="password" v-model="registerForm.passwd" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" prop="confirmPasswd">
                        <el-input type="password" v-model="registerForm.confirmPasswd" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="registerForm.name" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="身份" prop="role">
                        <el-select v-model="registerForm.role" placeholder="请选择身份">
                            <el-option label="教师" value="teacher"></el-option>
                            <el-option label="学生" value="student"></el-option>
                            <el-option label="管理员" value="admin"></el-option>
                        </el-select>
                    </el-form-item>

                    <!-- 验证码部分 -->
                    <el-form-item label="验证码" prop="captcha">
                        <div style="display: flex; align-items: center">
                            <el-input v-model="registerForm.captcha" autocomplete="off" style="width: 60%"></el-input>
                            <span style="margin-left: 10px; font-size: 20px; font-weight: bold">{{ captcha }}</span>
                            <el-button @click="generateCaptcha" type="text" style="margin-left: 10px">换一换</el-button>
                        </div>
                    </el-form-item>

                    <el-form-item>
                        <el-button type="primary" @click="submitRegister()" style="margin-left: 10%">注册</el-button>
                        <el-button @click="goToLogin" type="text" style="float: right">返回登录</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import { API_URL } from "@/config";
import moment from "moment";

export default {
    data() {
        return {
            registerForm: {
                username: "",
                passwd: "",
                confirmPasswd: "",
                name: "",
                role: "",
            },
            captcha: "",
            captchaValue: "",
            rules: {
                username: [{ required: true, message: "请输入账号", trigger: "blur" }],
                passwd: [{ required: true, message: "请输入密码", trigger: "blur" }],
                confirmPasswd: [
                    { required: true, message: "请确认密码", trigger: "blur" },
                    { validator: this.validatePasswd, trigger: "blur" },
                ],
                name: [{ required: true, message: "请输入姓名", trigger: "blur" }],
                gender: [{ required: true, message: "请选择身份", trigger: "change" }],
                captcha: [
                    { required: true, message: '请输入验证码', trigger: 'blur' },
                    { validator: this.validateCaptcha, trigger: 'blur' }
                ],
            },
        };
    },

    mounted() {
        this.generateCaptcha(); // 随机生成验证码
    },

    methods: {
        // 密码验证
        validatePasswd(rule, value, callback) {
            if (value !== this.registerForm.passwd) {
                callback(new Error("两次输入的密码不一致"));
            } else {
                callback();
            }
        },
        // 验证码验证
        validateCaptcha(rule, value, callback) {
            if (value !== this.captchaValue) {
                callback(new Error("验证码不正确"));
            } else {
                callback();
            }
        },
        generateCaptcha() {
            // 生成一个随机的6位数字验证码
            this.captchaValue = Math.random().toString().slice(-6);
            this.captcha = this.captchaValue; // 显示生成的验证码
        },
        submitRegister() {
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    axios
                        .post(`${API_URL}/register`, this.registerForm)
                        .then((response) => {
                            this.$message.success("注册成功!");
                            this.goToLogin();
                        })
                        .catch((error) => {
                            this.$message.error("注册失败，请重试");
                        });
                } else {
                    this.$message.error("表单输入有误，请检查");
                }
            });
        },
        goToLogin() {
            this.$router.push("/login");
        },
    },
};
</script>

<style scoped>
.demo-ruleForm {
    width: 30%;
}
</style>