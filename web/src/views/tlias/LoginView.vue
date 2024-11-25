<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header style="font-size: 40px; background-color: rgb(238, 241, 246)">登录</el-header>
            <el-main style="display: flex; align-items: center; justify-content: center;">
                <el-form :model="loginForm" status-icon :rules="rules" ref="loginForm" label-width="100px"
                    class="demo-ruleForm">
                    <el-form-item label="账号" prop="username">
                        <el-input v-model="loginForm.username" autocomplete="off" @keyup.enter.native="submitLogin">
                            <template #append>
                                <el-button v-if="loginForm.username" icon="el-icon-close" @click="clearUsername"
                                    size="small" class="clear-button">
                                </el-button>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item label="密码" prop="passwd">
                        <el-input v-model="loginForm.passwd" :type="passwordFieldType" autocomplete="off" @keyup.enter.native="submitLogin">
                            <template #append>
                                <el-button icon="el-icon-view" @click="togglePasswordVisibility" size="small"
                                    class="toggle-password-button">
                                </el-button>
                                <el-button v-if="loginForm.passwd" icon="el-icon-close" @click="clearPassword"
                                    size="small" class="clear-button">
                                </el-button>
                            </template>
                        </el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-checkbox v-model="rememberMe" style="float: left;"> 保存密码</el-checkbox>
                        <el-button type="primary" @click="submitLogin">登录</el-button>
                        <el-button @click="goToRegister" type="text" style="margin-left: 20%;">注册</el-button>
                    </el-form-item>
                </el-form>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import { API_URL } from "@/config";

export default {
    data() {
        return {
            loginForm: {
                username: '',
                passwd: ''
            },
            rememberMe: false,
            passwordFieldType: 'password',
            rules: {
                username: [
                    { required: true, message: '请输入账号', trigger: 'blur' }
                ],
                passwd: [
                    { required: true, message: '请输入密码', trigger: 'blur' }
                ]
            }
        };
    },
    created() {
        const savedUsername = sessionStorage.getItem('savedUsername');
        const savedPassword = sessionStorage.getItem('savedPassword');
        const savedRememberMe = sessionStorage.getItem('rememberMe') === 'true';

        if (savedUsername) {
            this.loginForm.username = savedUsername;
        }
        if (savedPassword) {
            this.loginForm.passwd = savedPassword;
        }
        this.rememberMe = savedRememberMe;
    },
    methods: {
        submitLogin() {
            axios.post(`${API_URL}/login`, this.loginForm)
                .then(response => {
                    this.$message.success("登录成功!");
                    this.$store.dispatch('updateCurUser', response.data.user);
                    sessionStorage.setItem('token', response.data.jwt);  //                     

                    if (this.rememberMe) {
                        sessionStorage.setItem('savedUsername', this.loginForm.username);
                        sessionStorage.setItem('savedPassword', this.loginForm.passwd);
                        sessionStorage.setItem('rememberMe', 'true');
                    } else {
                        sessionStorage.removeItem('savedUsername');
                        sessionStorage.removeItem('savedPassword');
                        sessionStorage.setItem('rememberMe', 'false');
                    }

                    this.$router.push('/course').then(() => {
                        window.location.reload(); // 刷新页面
                    });
                })
                .catch(error => {
                    this.$message.error('登录失败!账号或密码错误');
                });
        },
        goToRegister() {
            this.$router.push('/register');
        },
        togglePasswordVisibility() {
            this.passwordFieldType = this.passwordFieldType === 'password' ? 'text' : 'password';
        },
        clearUsername() {
            this.loginForm.username = '';
        },
        clearPassword() {
            this.loginForm.passwd = '';
        }
    }
}
</script>

<style scoped>
.demo-ruleForm {
    width: 25%;
}

.toggle-password-button {
    border: none;
    background: transparent;
    cursor: pointer;
}

.clear-button {
    border: none;
    background: transparent;
    cursor: pointer;
}
</style>