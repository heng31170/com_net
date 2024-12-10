<template>
    <div>
        <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect"
            background-color="#545c64" text-color="#fff" active-text-color="#ffd04b" style="justify-content: center;">
            <el-menu-item index="1" @click="toCourseAdmin">课程管理</el-menu-item>
            <el-menu-item index="2" @click="toTeacherAdmin">教师管理</el-menu-item>
            <el-menu-item index="3">
                <el-dropdown trigger="click">
                    <span style="color:white">更多操作</span>
                    <i class="el-icon-arrow-down" style="margin-left: 5px;"></i>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item @click.native="unlogin">登出</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </el-menu-item>
        </el-menu>

        <el-main>
            <el-button type="primary" @click="openAddTeacherDialog">添加教师</el-button>
            <!-- 教师数据 -->
            <div class="teacher-container">
                <div v-if="isLoading" class="loading">加载中...</div>
                <div v-else-if="teacherData.length === 0" class="no-data">暂无教师数据</div>
                <div v-else>
                    <div v-for="teacher in teacherData" :key="teacher.teacherId" class="teacher-card">
                        <div class="teacher-avatar" @click="goToTeacherDetail(teacher.teacherId)">
                            <img :src="teacher.avatar" alt="教师头像" class="avatar" />
                        </div>
                        <div class="teacher-info">
                            <h3 class="teacher-name">{{ teacher.name }}</h3>
                            <p class="teacher-bio">{{ teacher.bio }}</p>
                        </div>
                        <!-- 删除按钮 -->
                        <el-button type="danger" @click.stop="deleteTeacher(teacher.teacherId)">删除</el-button>
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
                        <el-input v-model="addTeacher.bio" type="textarea" rows="10"></el-input>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="confirmAddTeacher">确定</el-button>
                        <el-button @click="dialogAddTeacherVisible = false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            <!-- 结束新增教师对话框 -->
        </el-main>
    </div>
</template>

<script>
import axios from 'axios';
import { API_URL } from "@/config";

export default {
    data() {
        return {
            teacherData: [],
            activeIndex2: '2',
            addTeacher: {
                name: "",
                bio: "",
                avatar: "",
            },
            dialogAddTeacherVisible: false,
            isLoading: false,
            selectedFile: null,
        };
    },
    mounted() {
        this.getAllTeachers();  // 获取所有教师数据
    },
    methods: {
        // 处理文件选择
        handleFileChange(file) {
            const reader = new FileReader();
            reader.readAsDataURL(file.raw); // 读取文件为 Data URL
            reader.onload = () => {
                this.addTeacher.avatar = reader.result; // 更新头像
                this.selectedFile = file.raw; // 保存选中的文件
            };
        },
        deleteTeacher(teacherId) {
            axios
                .post(`${API_URL}/teacher/del/${teacherId}`)
                .then(response => {
                    this.$message.success("教师删除成功");
                    this.getAllTeachers(); // 重新获取课程数据
                })
                .catch((error) => {
                    this.$message.error("删除失败: " + (error.response ? error.response.data.message : error.message));
                });
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
            if (!name.trim()) {
                this.$message.error("教师姓名不能为空或仅包含空格");
                return;
            }
            if (!bio.trim()) {
                this.$message.error("教师简介不能为空或仅包含空格");
                return;
            }
            if (!avatar.trim()) {
                this.$message.error("教师头像不能为空或仅包含空格");
                return;
            }
            const formData = new FormData();
            formData.append("json", JSON.stringify({ name, bio, avatar }));
            if (this.selectedFile) {
                formData.append("file", this.selectedFile);
            }
            axios.post(`${API_URL}/teacher/add`, formData, {
                headers: { 'Content-Type': 'multipart/form-data' }
            })
            .then(() => {
                this.getAllTeachers();
                this.$message.success("添加成功!");
                this.dialogAddTeacherVisible = false;
            })
            .catch(error => {
                this.dialogAddTeacherVisible = false;
                this.$message.error("添加失败!");
            });
        },
        getAllTeachers() {
            this.isLoading = true;
            axios.get(`${API_URL}/teachers`)
                .then(response => {
                    this.teacherData = response.data;
                })
                .catch(error => {
                    this.$message.error("查询失败: " + (error.response ? error.response.data.message : error.message));
                })
                .finally(() => {
                    this.isLoading = false;
                });
        },
        goToTeacherDetail(teacherId) {
            // 跳转到教师详情页面
            // this.$router.push({ path: `/teacherDetail/${teacherId}` });
        },
        // 登出
        unlogin() {
            // 清空用户数据
            this.$store.dispatch('updateCurUser', null); // 或者使用适当的 mutation 来重置用户数据
            sessionStorage.removeItem('token');
            this.$router.push('/login').then(() => {
                window.location.reload(); // 刷新页面
            });
        },
        toCourseAdmin() {
            if (this.$route.path !== '/courseAdmin') {
                this.$router.push('/courseAdmin');
            }
        },
        toTeacherAdmin() {
            if (this.$route.path !== '/teacherAdmin') {
                this.$router.push('/teacherAdmin');
            }
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        }
    }
}
</script>

<style>
.el-menu-demo {
    display: flex;
    justify-content: center;
}

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
    display: flex;
    justify-content: center;
    align-items: center;
}

.avatar {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    object-fit: cover;
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
    height: 100px;
    border: 1px dashed #d9d9d9;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #f5f5f5;
}

.avatar-preview {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.avatar-placeholder {
    font-size: 40px;
    color: #999;
}
</style>