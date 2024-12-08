<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header
                style="font-size: 40px; background-color: rgb(238, 241, 246); display: flex; justify-content: space-between; align-items: center;">
                <span
                    style="display: block; width: 100%; text-align: center; transform: translateX(-50%); margin-left: 50%;">不会写呀这</span>
                <!-- 菜单放置在右侧 -->
                <el-menu :default-active="null" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                    style="margin-right: 10%;" id="delBgc">
                    <el-submenu index="2">
                        <template slot="title">Hello! {{ curUser.name || '游客' }}</template>
                        <el-menu-item index="2-1" @click="toPerson" v-if="curUser.role">个人中心</el-menu-item>
                        <el-menu-item index="2-2" @click="openDialogUpdatePasswdVisible"
                            v-if="curUser.role">修改密码</el-menu-item>
                        <el-menu-item index="2-3" @click="unlogin">{{ curUser.role ? '登出' : '登录' }}</el-menu-item>
                    </el-submenu>
                </el-menu>
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
                            <el-menu-item index="1-5"> <!-- 添加讨论区 -->
                                <router-link to="/exam">考试</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <!-- 表单 搜索课程 -->
                    <el-form :inline="true" :model="searchCourse" class="demo-form-inline">
                        <el-form-item label="课程名称">
                            <el-input v-model="searchCourse.title" placeholder="课程名称"></el-input>
                        </el-form-item>
                        <el-form-item label="类别">
                            <el-select v-model="searchCourse.category" placeholder="课程类别">
                                <el-option label="理论" value="理论"></el-option>
                                <el-option labe l="计算机" value="计算机"></el-option>
                                <el-option label="不限" value=""></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="queCourse">搜索</el-button>
                        </el-form-item>
                        <el-form-item v-if="curUser.role === 'teacher'">
                            <el-button type="primary" @click="openAddCourseDialog">添加课程</el-button>
                        </el-form-item>
                    </el-form>

                    <!-- 课程数据 -->
                    <template>
                        <div class="course-container">
                            <div v-if="CourseData.length === 0" class="no-data">暂无课程数据</div>
                            <div v-else>
                                <div v-for="course in CourseData" :key="course.courseId" class="course-card"
                                    @click="goToCourseDetail(course.courseId)">
                                    <!-- 课程封面 -->
                                    <div class="course-cover">
                                        <img :src="course.avatar" alt="课程封面" />
                                    </div>
                                    <!-- 课程信息 -->
                                    <div class="course-info">
                                        <h3 class="course-title">{{ course.title }}</h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                    <!-- 结束课程数据 -->

                    <!-- 弹出更改密码对话框 -->
                    <el-dialog title="修改密码" :visible.sync="dialogUpdatePasswdVisible">
                        <el-form :model="userPasswd" status-icon :rules="rules" ref="userPasswd" label-width="100px"
                            class="demo-ruleForm">
                            <el-form-item label="原密码" prop="passwd">
                                <el-input type="password" v-model="userPasswd.oldPasswd" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="新密码" prop="passwd">
                                <el-input type="password" v-model="userPasswd.passwd" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码" prop="confirmPasswd">
                                <el-input type="password" v-model="userPasswd.confirmPasswd"
                                    autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="updatePasswd">确定</el-button>
                                <el-button @click="dialogUpdatePasswdVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束更改密码对话框 -->

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
            </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import { API_URL } from "@/config";
import { mapGetters } from "vuex";

export default {
    data() {
        return {
            // 课程数据
            CourseData: [],
            CourseId: '',
            curUser: {},

            // 修改密码
            userPasswd: {
                oldPasswd: '',
                passwd: '',
                confirmPasswd: '',
            },

            // 条件查询
            searchCourse: {
                title: "",
                category: "",
            },

            // 新增课程
            addCourse: {
                title: "",
                description: "",
                category: "",
                avatar: "",
            },
            selectedFile: null,
            dialogUpdatePasswdVisible: false, // 修改密码对话框可见性
            dialogAddCourseVisible: false, // 新增课程对话框可见性
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
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
                    console.error("添加失败:", error);
                })
        },
        // 获取所有课程
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
        // 跳转到课程详情页
        goToCourseDetail(courseId) {
            // 存储 courseId 到 sessionStorage
            sessionStorage.setItem('selectedCourseId', courseId);
            // window.open(`${window.location.origin}/course/${courseId}`, '_blank')
            this.$router.push('/course/${courseId}');
        },
        // 弹出更新密码对话框
        openDialogUpdatePasswdVisible() {
            this.dialogUpdatePasswdVisible = true;
            this.resetPasswdForm();
        }, 
        // 更新密码
        updatePasswd() {
            if (!this.userPasswd.oldPasswd || !this.userPasswd.passwd || !this.userPasswd.confirmPasswd) {
                this.$message.error("原密码、新密码和确认密码不能为空");
                return;
            }
            if (this.userPasswd.passwd !== this.userPasswd.confirmPasswd) {
                this.$message.error("新密码和确认密码不一致");
                return;
            }
            if (this.userPasswd.passwd.includes(" ")) {
                this.$message.error("新密码不能包含空格");
                return;
            }
            axios
                .post(`${API_URL}/passwd/update`, {
                    userId: this.curUser.userId,
                    oldPasswd: this.userPasswd.oldPasswd,
                    passwd: this.userPasswd.passwd,
                })
                .then((response) => {
                    this.dialogUpdatePasswdVisible = false; // 关闭对话框
                    this.resetPasswdForm(); // 重置表单
                    this.unlogin();
                    this.$message.success("密码更新成功,请重新登录!");
                })
                .catch(error => {
                    this.$message.error("密码更新失败");
                });
        },

        // 重置密码表单
        resetPasswdForm() {
            this.userPasswd.oldPasswd = '';
            this.userPasswd.passwd = '';
            this.userPasswd.confirmPasswd = '';
        },

        handleSelect() { },

        // 个人中心
        toPerson() {
            // this.$router.push('/person'); // 个人中心路由
            sessionStorage.setItem('selectedPersonId', this.curUser.userId);
            window.open(`${window.location.origin}/person/${this.curUser.userId}`, '_blank')
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

        // 条件查询课程
        queCourse() {
            const { title, category } = this.searchCourse;
            const params = {
                title,
                category
            };
            axios
                .get(`${API_URL}/course/search`, { params })
                .then((response) => {
                    this.CourseData = response.data;
                    this.$message.success("课程信息查询成功!");
                })
                .catch((error) => {
                    //this.$message.error("查询失败: " + (error.response ? error.response.data.message : error.message));
                });
        },

    },

    mounted() {
        this.getAllCourses();  // 获取所有课程数据
        if (this.getCurUser !== null) this.curUser = this.getCurUser;
        console.log(this.curUser);

    },
};
</script>

<style scoped>
.course-container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    justify-content: flex-start;
}

.course-card {
    float: left;
    margin-left: 50px;
    margin-top: 30px;
    width: 260px;
    /* 每个卡片固定宽度 */
    border: 1px solid #f0f0f0;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;
}

.course-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.course-cover img {
    width: 100%;
    /* 图片宽度自适应卡片 */
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
        /* 缩小卡片宽度 */
    }
}

@media (max-width: 768px) {
    .course-card {
        width: 180px;
        /* 更小屏幕时进一步缩小卡片宽度 */
    }
}

.course-meta {
    font-size: 14px;
    color: #666;
    margin: 0 0 12px;
}

.start-button {
    display: inline-block;
    padding: 6px 12px;
    font-size: 14px;
    color: #fff;
    background-color: #409eff;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

.start-button:hover {
    background-color: #66b1ff;
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
<!-- <style>
.avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-top: 10px;
}

#delBgc {
    background-color: transparent;
}
</style> -->