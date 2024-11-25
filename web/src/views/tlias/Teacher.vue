<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header
                style="font-size: 40px; background-color: rgb(238, 241, 246); display: flex; justify-content: space-between; align-items: center;">
                <span
                    style="display: block; width: 100%; text-align: center; transform: translateX(-50%); margin-left: 50%;">不会写呀这</span>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu :default-openeds="['1', '3']">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>课程管理</template>
                            <el-menu-item index="1-2">
                                <router-link to="/course">主页</router-link>
                            </el-menu-item>
                            <el-menu-item index="1-3"> <!-- 添加教师管理 -->
                                <router-link to="/teacher">教师</router-link>
                            </el-menu-item>
                            <el-menu-item index="1-4"> <!-- 添加讨论区 -->
                                <router-link to="/discussion">讨论区</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <!-- 表单 搜索教师 -->
                    <el-form :inline="true" :model="searchTeacher" class="demo-form-inline">
                        <el-form-item style="float: right;">
                            <el-button type="primary" @click="openAddTeacherDialog">添加教师</el-button>
                        </el-form-item>
                    </el-form>

                    <!-- 教师数据 -->
                    <div class="teacher-container">
                        <div v-if="isLoading" class="loading">加载中...</div>
                        <div v-else-if="teacherData.length === 0" class="no-data">暂无教师数据</div>
                        <div v-else>
                            <div v-for="teacher in teacherData" :key="teacher.teacherId" class="teacher-card"
                                @click="goToTeacherDetail(teacher.teacherId)">
                                <!-- 教师头像 -->
                                <div class="teacher-avatar">
                                    <img :src="teacher.avatar" alt="教师头像" class="avatar" />
                                </div>
                                <!-- 教师信息 -->
                                <div class="teacher-info">
                                    <h3 class="teacher-name">{{ teacher.name }}</h3>
                                    <p class="teacher-bio">{{ teacher.bio }}</p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 结束教师数据 -->

                    <!-- 新增教师对话框 -->
                    <el-dialog title="添加教师" :visible.sync="dialogAddTeacherVisible">
                        <el-form ref="form" :model="addTeacher" label-width="80px">
                            <el-form-item label="教师姓名">
                                <el-input v-model="addTeacher.name"></el-input>
                            </el-form-item>
                            <el-form-item label="教师头像" prop="avatar">
                                <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false">
                                    <div class="avatar-preview-container">
                                        <img v-if="addTeacher.avatar" :src="addTeacher.avatar" class="avatar-preview" />
                                        <i v-else class="el-icon-plus avatar-placeholder"></i>
                                    </div>
                                </el-upload>
                            </el-form-item>
                            <el-form-item label="教师简介">
                                <el-input v-model="addTeacher.bio"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddTeacher">确定</el-button>
                                <el-button @click="dialogAddTeacherVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束新增教师对话框 -->

                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import { API_URL } from "@/config";

export default {
    data() {
        return {
            // 教师数据
            teacherData: [
            ],
            teacherId: '',
            curTeacher: {
                teacherId: "",
                name: "",
                bio: "",
                userId: "",
                avatar: "",
            },

            // 新增教师
            addTeacher: {
                name: "",
                bio: "",
                avatar: "",
            },
            selectedFile: null,
            dialogAddTeacherVisible: false, // 新增教师对话框可见性
            isLoading: false, // 加载状态
        };
    },

    methods: {
        // 处理文件选择
        handleFileChange(file) {
            // this.selectedFile = file.raw;
            const reader = new FileReader();
            reader.readAsDataURL(file.raw); // 读取文件为 Data URL
            reader.onload = () => {
                this.addTeacher.avatar = reader.result; // 更新头像
                this.selectedFile = file.raw; // 保存选中的文件
            };
        },
        // 打开新增教师对话框
        openAddTeacherDialog() {
            this.dialogAddTeacherVisible = true;
            // 重置 addTeacher 数据
            this.addTeacher = {
                name: "",
                bio: "",
                avatar: "",
            };
            this.selectedFile = null; // 清空已选择的文件
        },
        // 确认添加教师
        confirmAddTeacher() {
            const { name, bio, avatar } = this.addTeacher;
            // 检查每个字段是否为空或仅包含空格
            if (!name.trim()) {
                this.$message.error("姓名不能为空或仅包含空格");
                return;
            }
            if (!bio.trim()) {
                this.$message.error("简介不能为空或仅包含空格");
                return;
            }
            if (!avatar.trim()) {
                this.$message.error("头像不能为空或仅包含空格");
                return;
            }
            const formData = new FormData();
            formData.append("json", JSON.stringify({
                name,
                bio,
                avatar
            }));
            // 添加文件
            if (this.selectedFile) {
                formData.append("file", this.selectedFile);
            }
            axios
                .post(`${API_URL}/teacher/add`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then((response) => {
                    this.getAllTeachers();
                    this.$message.success("添加成功!");
                    this.dialogAddTeacherVisible = false;
                })
                .catch((error) => {
                    console.error("添加失败:", error);
                });
        },
        // 获取所有教师
        getAllTeachers() {
            this.isLoading = true; // 设置加载状态
            axios
                .get(`${API_URL}/teachers`)
                .then(response => {
                    this.teacherData = response.data;
                })
                .catch((error) => {
                    this.$message.error("查询失败: " + (error.response ? error.response.data.message : error.message));
                })
                .finally(() => {
                    this.isLoading = false; // 关闭加载状态
                });
        },
        // 跳转到教师详情页
        goToTeacherDetail(teacherId) {
            sessionStorage.setItem('selectedTeacherId', teacherId);
            window.open(`${window.location.origin}/teacher/${teacherId}`, '_blank');
        },
    },

    mounted() {
        this.getAllTeachers();  // 获取所有教师数据
    },
};
</script>

<style scoped>
.teacher-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: flex-start;
}

.teacher-card {
    float: left;
    margin-left: 50px;
    margin-top: 30px;
    width: 260px;
    border: 1px solid #f0f0f0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;
}

.teacher-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.teacher-avatar {
    width: 100%;
    height: 150px;
    /* 固定高度 */
    display: flex;
    justify-content: center;
    align-items: center;
}

.avatar {
    width: 100px;
    /* 设置宽度 */
    height: 100px;
    /* 设置高度 */
    border-radius: 50%;
    /* 圆形 */
    object-fit: cover;
    /* 保持比例 */
    border: 2px solid #f0f0f0;
    /* 可选边框 */
}

.teacher-info {
    padding: 16px;
    text-align: center;
}

.teacher-name {
    font-size: 16px;
    font-weight: bold;
    margin: 0 0 8px;
    color: #333;
}

.teacher-bio {
    font-size: 14px;
    color: #666;
}

.avatar-preview-container {
    width: 200px;
    /* 设置宽度 */
    height: 100px;
    /* 设置高度 */
    border: 1px dashed #d9d9d9;
    /* 边框样式 */
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f5f5f5;
    /* 背景颜色 */
}

.avatar-preview {
    width: 100%;
    /* 图片宽度100% */
    height: 100%;
    /* 图片高度100% */
    object-fit: cover;
    /* 保持图片比例 */
}

.avatar-placeholder {
    font-size: 40px;
    /* 占位符的字体大小 */
    color: #999;
    /* 颜色 */
}
</style>