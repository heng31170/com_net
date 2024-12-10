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
            <el-button type="primary" @click="openAddCourseDialog">添加课程</el-button>
            <!-- 课程数据 -->
            <div class="course-container">
                <div v-if="CourseData.length === 0" class="no-data">暂无课程数据</div>
                <div v-else>
                    <div v-for="course in CourseData" :key="course.courseId" class="course-card">
                        <!-- 课程封面 -->
                        <div class="course-cover" @click="goToCourseDetail(course.courseId)">
                            <img :src="course.avatar" alt="课程封面" />
                        </div>
                        <!-- 课程信息 -->
                        <div class="course-info">
                            <h3 class="course-title">{{ course.title }}</h3>
                        </div>
                        <!-- 删除按钮 -->
                        <el-button type="danger" @click.stop="deleteCourse(course.courseId)">删除</el-button>
                    </div>
                </div>
            </div>
            <!-- 结束课程数据 -->
            <!-- 新增课程对话框 -->
            <el-dialog title="新增课程" :visible.sync="dialogAddCourseVisible">
                <el-form ref="form" :model="addCourse" label-width="80px">
                    <el-form-item label="课程名称">
                        <el-input v-model="addCourse.title"></el-input>
                    </el-form-item>
                    <el-form-item label="课程封面" prop="avatar">
                        <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false">
                            <div class="avatar-preview-container">
                                <img v-if="addCourse.avatar" :src="addCourse.avatar" class="avatar-preview" />
                                <i v-else class="el-icon-plus avatar-placeholder"></i>
                            </div>
                        </el-upload>
                    </el-form-item>
                    <el-form-item label="课程简介">
                        <el-input v-model="addCourse.description" type="textarea" rows="10"></el-input>
                    </el-form-item>
                    <el-form-item label="课程类别">
                        <el-select v-model="addCourse.category" placeholder="请选择课程类别">
                            <el-option label="理论" value="理论"></el-option>
                            <el-option label="计算机" value="计算机"></el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button type="primary" @click="confirmAddCourse">确定</el-button>
                        <el-button @click="dialogAddCourseVisible = false">取消</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            <!-- 结束新增课程对话框 -->
        </el-main>
    </div>
</template>

<script>
import axios from 'axios';
import { API_URL } from "@/config";

export default {
    data() {
        return {
            CourseData: [],
            activeIndex2: '1', // 默认选中项
            // 新增课程
            addCourse: {
                title: "",
                description: "",
                category: "",
                avatar: "",
            },
            dialogAddCourseVisible: false, // 新增课程对话框可见性
            selectedFile: null,
        };
    },
    mounted() {
        this.getAllCourses();  // 获取所有课程数据
    },
    methods: {
        // 处理文件选择
        handleFileChange(file) {
            const reader = new FileReader();
            reader.readAsDataURL(file.raw); // 读取文件为 Data URL
            reader.onload = () => {
                this.addCourse.avatar = reader.result; // 更新头像
                this.selectedFile = file.raw; // 保存选中的文件
            };
        },
        // 打开新增课程对话框
        openAddCourseDialog() {
            this.dialogAddCourseVisible = true;
            // 重置 addCourse 数据
            this.addCourse = {
                title: "",
                description: "",
                category: "",
                avatar: "",
            };
            this.selectedFile = null; // 清空已选择的文件
        },
        // 确认添加课程
        confirmAddCourse() {
            const { title, description, category, avatar } = this.addCourse;
            // 检查每个字段是否为空或仅包含空格
            if (!title.trim()) {
                this.$message.error("课程名称不能为空或仅包含空格");
                return;
            }
            if (!description.trim()) {
                this.$message.error("课程简介不能为空或仅包含空格");
                return;
            }
            if (!category.trim()) {
                this.$message.error("课程类别不能为空或仅包含空格");
                return;
            }
            if (!avatar.trim()) {
                this.$message.error("课程封面不能为空或仅包含空格");
                return;
            }
            const formData = new FormData();

            formData.append("json", JSON.stringify({
                title,
                description,
                category,
                avatar
            }));
            // 添加文件
            if (this.selectedFile) {
                formData.append("file", this.selectedFile);
            }
            axios
                .post(`${API_URL}/course/add`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then((response) => {
                    this.getAllCourses();
                    this.$message.success("添加成功!")
                    this.dialogAddCourseVisible = false;
                })
                .catch((error) => {
                    this.dialogAddCourseVisible = false;
                    this.$message.error("添加失败!");
                })
        },
        getAllCourses() {
            axios
                .get(`${API_URL}/courses`)
                .then(response => {
                    this.CourseData = response.data;
                })
                .catch((error) => {
                    this.$message.error("查询失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        deleteCourse(courseId) {
            axios
                .post(`${API_URL}/course/del/${courseId}`)
                .then(response => {
                    this.$message.success("课程删除成功");
                    this.getAllCourses(); // 重新获取课程数据
                })
                .catch((error) => {
                    this.$message.error("删除失败: " + (error.response ? error.response.data.message : error.message));
                });
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

.el-menu-item {
    margin: 0 15px;
}

.course-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    /* 设置间距 */
    justify-content: center;
    /* 居中对齐 */
    padding: 20px;
    /* 添加内边距 */
}

.course-card {
    float: left;
    margin-left: 50px;
    margin-top: 30px;
    width: 260px;
    /* 每个卡片的固定宽度 */
    border: 1px solid #f0f0f0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;
    display: flex;
    flex-direction: column;
    /* 垂直排列 */
}

.course-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.course-cover img {
    width: 100%;
    height: 150px;
    /* 固定高度 */
    object-fit: cover;
    /* 保持图片比例 */
}

.course-info {
    padding: 16px;
    text-align: center;
}

.course-title {
    font-size: 16px;
    font-weight: bold;
    margin: 0 0 8px;
    color: #333;
}

/* 响应式调整 */
@media (max-width: 1200px) {
    .course-card {
        width: 220px;
        /* 中等屏幕时缩小卡片宽度 */
    }
}

@media (max-width: 768px) {
    .course-card {
        width: 180px;
        /* 小屏幕时进一步缩小卡片宽度 */
    }
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