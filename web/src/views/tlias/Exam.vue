<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header
                style="font-size: 40px; background-color: rgb(238, 241, 246); display: flex; justify-content: space-between; align-items: center;">
                <span
                    style="display: block; width: 100%; text-align: center; transform: translateX(-50%); margin-left: 50%;">考试区</span>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu :default-openeds="['1', '3']">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>课程管理</template>
                            <el-menu-item index="1-2">
                                <router-link to="/course">主页</router-link>
                            </el-menu-item>
                            <el-menu-item index="1-3">
                                <router-link to="/teacher">教师</router-link>
                            </el-menu-item>
                            <el-menu-item index="1-4">
                                <router-link to="/discussion">讨论区</router-link>
                            </el-menu-item>
                            <el-menu-item index="1-5">
                                <router-link to="/exam">考试</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <div class="exam-container">
                        <h2 class="exam-title">
                            考试管理
                            <el-button type="primary" @click="openAddExamDialog" style="float: right;"
                                v-if="curUser.role === 'teacher'">添加考试</el-button>
                        </h2>

                        <div v-for="(exam, index) in exams" :key="index" class="exam-item"
                            @click="goToExamDetail(exam.examId)">
                            <div class="exam-user" @click="toPerson(exam.userId)">
                                <span class="username">{{ exam.name }}</span>
                            </div>
                            <div class="exam-info">
                                <span class="exam-title">{{ exam.title }}</span>
                            </div>
                            <div class="exam-footer">
                                <span class="last-reply">考试创建时间：{{ new Date(exam.createdAt).toLocaleString() }}</span>
                                <el-button v-if="exam.userId === curUserId" type="danger"
                                    @click.stop="deleteExam(exam.examId)" style="margin-left: 10px;">
                                    删除
                                </el-button>
                                <el-button type="primary" @click.stop="downloadExam(exam.examId)">下载试卷</el-button> 
                            </div>
                        </div>
                    </div>

                    <!-- 新增考试对话框 -->
                    <el-dialog title="添加考试" :visible.sync="dialogAddExamVisible">
                        <el-form ref="form" :model="addExam" label-width="80px">
                            <el-form-item label="标题">
                                <el-input v-model="addExam.title"></el-input>
                            </el-form-item>
                            <el-form-item label="内容">
                                <el-input type="textarea" v-model="addExam.text" :rows="10"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddExam">确认</el-button>
                                <el-button @click="dialogAddExamVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束新增考试对话框 -->

                    <!-- 删除确认对话框 -->
                    <el-dialog title="确定删除该考试?" :visible.sync="dialogDelExamVisible">
                        <el-form>
                            <el-form-item>
                                <el-button type="primary" @click="confirmDelExam">确定</el-button>
                                <el-button @click="dialogDelExamVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束删除确认对话框 -->
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import { API_URL } from '@/config';
import axios from 'axios';
import { mapGetters } from "vuex";

export default {
    data() {
        return {
            dialogDelExamVisible: false,
            examToDelete: null, // 记录待删除的考试ID
            curUserId: null,
            curUser: {},
            exams: [],
            addExam: {
                examId: '',
                title: '',
                text: '',
            },
            dialogAddExamVisible: false // 控制对话框显示
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
    },
    methods: {
        // 下载考试
        downloadExam(examId) {
            const exam = this.exams.find(e => e.examId === examId);
            if (!exam) {
                this.$message.error("考试信息未找到");
                return;
            }

            const content = `
            <html xmlns:w="urn:schemas-microsoft-com:office:word">
            <head>
                <meta charset="utf-8">
                <style>
                    body { font-family: Arial, sans-serif; }
                    h1 { font-size: 24px; }
                    p { font-size: 16px; }
                </style>
            </head>
            <body>
                <h1>${exam.title}</h1>
                <p>${exam.text}</p>
            </body>
            </html>
        `;

            const blob = new Blob([content], { type: 'application/msword' });
            const url = URL.createObjectURL(blob);
            // 获取当前日期并格式化
            const createdAt = new Date(exam.createdAt); // 确保是日期对象
            const formattedDate = createdAt.toISOString().split('T')[0]; // 格式化为 YYYY-MM-DD
            const link = document.createElement('a');
            link.href = url;
            link.download = `${formattedDate}_${exam.title}.doc`;

            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            URL.revokeObjectURL(url);
        },
        // 打开对话框并清空
        openAddExamDialog() {
            this.dialogAddExamVisible = true;
            this.addExam.title = '';
            this.addExam.text = '';
        },
        // 添加考试的确认逻辑
        confirmAddExam() {
            if (!this.addExam.title.trim() || !this.addExam.text.trim()) {
                this.$message.error("标题和内容不能为空");
                return;
            }
            axios.post(`${API_URL}/exam/add`, { title: this.addExam.title, text: this.addExam.text, userId: this.curUserId })
                .then(response => {
                    this.dialogAddExamVisible = false;
                    this.addExam.title = '';
                    this.addExam.text = '';
                    this.getAllExams();
                    this.$message.success("考试添加成功");
                })
                .catch((error) => {
                    this.$message.error("添加失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 获取全部考试
        getAllExams() {
            axios
                .get(`${API_URL}/exams`)
                .then(response => {
                    this.exams = response.data.sort((a, b) => {
                        return new Date(b.createdAt) - new Date(a.createdAt);
                    });
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 跳转页面
        goToExamDetail(examId) {
            sessionStorage.setItem('selectedExamId', examId);
            this.$router.push(`/exam/${examId}`);
        },
        // 删除考试对话框
        deleteExam(examId) {
            this.examToDelete = examId;
            this.dialogDelExamVisible = true;
        },
        // 确认删除考试
        confirmDelExam() {
            axios.post(`${API_URL}/exam/del/${this.examToDelete}`)
                .then(() => {
                    this.exams = this.exams.filter(exam => exam.examId !== this.examToDelete); // 过滤已删除的考试
                    this.dialogDelExamVisible = false; // 关闭对话框
                    this.examToDelete = null; // 清空待删除考试ID
                    this.$message.success("考试删除成功!"); // 提示成功
                })
                .catch(error => {
                    console.error("删除考试失败:", error);
                    this.$message.error("删除考试失败!");
                });
        },
        // 个人中心
        toPerson(id) {
            sessionStorage.setItem('selectedPersonId', id);
            window.open(`${window.location.origin}/person/${id}`, 'blank');
        },
    },
    mounted() {
        this.getAllExams();
        this.curUserId = this.getCurUser.userId;
        if (this.getCurUser !== null) this.curUser = this.getCurUser;
    },
};
</script>

<style scoped>
.exam-user {
    font-size: 14px;
    font-weight: bold;
    color: #007bff;
    /* 用户名颜色 */
}

.username {
    margin-right: 10px;
    /* 用户名与考试内容之间的间距 */
}

.exam-container {
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.exam-title {
    font-size: 24px;
    margin-bottom: 20px;
    text-align: center;
}

.exam-item {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 4px;
    padding: 15px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: pointer;
}

.exam-info {
    display: flex;
    flex-direction: column;
}

.exam-title {
    font-size: 18px;
    font-weight: bold;
}

.exam-footer {
    font-size: 14px;
    color: #666;
}
</style>