<template>
    <div class="teacher-detail-container">
        <el-container>
            <el-header>
                <h1 class="teacher-title">{{ teacherDetail.name }}</h1>
            </el-header>
            <el-main>
                <div class="teacher-cover">
                    <img :src="teacherDetail.avatar" alt="教师头像" class="teacher-avatar" />
                </div>
                <div class="teacher-description">
                    <h2>教师简介</h2>
                    <p>{{ teacherDetail.bio }}</p>
                </div>
            </el-main>
        </el-container>
    </div>
</template>

<script>
import { API_URL } from "@/config";
import axios from "axios";

export default {
    data() {
        return {
            teacherId: null, // 教师ID
            teacherDetail: {}, // 教师详情
        };
    },

    methods: {
        // 获取教师详情
        getTeacherDetail() {
            axios.get(`${API_URL}/teacher/${this.teacherId}`)
                .then(response => {
                    this.teacherDetail = response.data;
                })
                .catch(error => {
                    console.error("获取教师详情失败:", error);
                });
        },
    },

    mounted() {
        this.teacherId = sessionStorage.getItem('selectedTeacherId'); // 从sessionStorage获取教师ID

        if (this.teacherId) this.getTeacherDetail(); // 组件加载时获取教师详情
    }
};
</script>

<style scoped>
.teacher-detail-container {
    padding: 20px;
}

.teacher-title {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

.teacher-cover {
    width: 100%; /* 使容器宽度占满 */
    height: 300px; /* 固定高度 */
    overflow: hidden; /* 隐藏溢出部分 */
    display: flex; /* 使用Flexbox居中图片 */
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}

.teacher-avatar {
    width: 150px; /* 设置头像宽度 */
    height: 150px; /* 设置头像高度 */
    border-radius: 50%; /* 圆形 */
    object-fit: cover; /* 保持比例并裁剪 */
    border: 2px solid #f0f0f0; /* 可选边框 */
}

.teacher-description {
    border: 1px solid #eee; /* 添加边框 */
    padding: 15px; /* 添加内边距 */
    margin: 20px auto; /* 添加外边距 */
    border-radius: 4px; /* 圆角 */
    background-color: #f9f9f9; /* 背景颜色 */
    width: 35%;
}
</style>