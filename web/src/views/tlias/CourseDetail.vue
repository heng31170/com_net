<template>
    <div class="course-detail-container">
        <el-container>
            <el-header>
                <h1 class="course-title">{{ courseDetail.title }}</h1>
            </el-header>
            <el-main>
                <div class="course-cover">
                    <img :src="courseDetail.avatar" alt="课程封面" />
                </div>
                <el-button type="primary" class="play-button" @click="playCourse">开始学习</el-button>
                <div class="course-description">
                    <h3>课程类别:{{ courseDetail.category }}</h3>
                    <h2>课程简介</h2>
                    <p>{{ courseDetail.description }}</p>
                </div>

                <div class="comment-section">
                    
                    <div class="comment-input-container">
                        <h2 style="margin-right: 5%;">评论区</h2><el-input type="textarea" v-model="newComment" placeholder="添加评论..." class="comment-input"
                            @keyup.enter.native="addComment" :rows="3">
                        </el-input>
                        <el-button type="primary" @click="addComment">发布</el-button>
                    </div>
                    <div class="comments">
                        <div v-for="(comment) in courseComments" :key="comment.comment_id" class="comment">
                            <p>{{ comment.content }}</p>
                            <small class="timestamp">{{ new Date(comment.createdAt).toLocaleString() }}</small>
                            <el-button v-if="comment.user_id === currentUserId" type="danger"
                                @click="deleteComment(comment.comment_id)">
                                删除
                            </el-button>
                        </div>
                    </div>
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
            courseId: null, // 当前课程ID
            courseDetail: {},
            newComment: '',
            courseComments: [],
            currentUserId: null // 当前用户ID
        };
    },

    methods: {
        // 获取课程详情
        getCourseDetail() {
            axios.get(`${API_URL}/course/${this.courseId}`)
                .then(response => {
                    this.courseDetail = response.data;
                    this.getCourseComments(); // 获取评论
                })
                .catch(error => {
                    console.error("获取课程详情失败:", error);
                });
        },

        // 获取课程评论
        getCourseComments() {
            axios.get(`${API_URL}/comment/${this.courseId}`)
                .then(response => {
                    this.courseComments = response.data; // 假设数据格式正确
                    // 按照创建时间排序，最新的在前
                    this.courseComments.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                })
                .catch(error => {
                    console.error("获取课程评论失败:", error);
                });
        },

        // 播放课程
        playCourse() {
            window.open("http://www.bilibili.com", "_blank");
        },

        // 添加评论
        addComment() {
            if (this.newComment.trim() !== '') {
                const newCommentData = {
                    content: this.newComment,
                    userId: this.currentUserId,
                    courseId: this.courseId // 使用 this.courseId
                };

                // POST请求添加评论
                axios.post(`${API_URL}/comment/add`, newCommentData)
                    .then(() => {
                        this.getCourseComments(); // 重新加载评论
                        this.newComment = ''; // 清空输入框
                        this.$message.success("评论添加成功!");
                    })
                    .catch(error => {
                        console.error("添加评论失败:", error);
                    });
            }
        },

        // 删除评论
        deleteComment(commentId) {
            axios.delete(`${API_URL}/course_comments/${commentId}`)
                .then(() => {
                    this.courseComments = this.courseComments.filter(comment => comment.comment_id !== commentId);
                })
                .catch(error => {
                    console.error("删除评论失败:", error);
                });
        },

        // 获取当前用户ID
        fetchCurrentUserId() {
            this.currentUserId = sessionStorage.getItem('currentUserId'); // 示例：从sessionStorage获取
        }
    },

    mounted() {
        this.courseId = sessionStorage.getItem('selectedCourseId'); // 将课程ID存储在组件的 data 中

        if (this.courseId) this.getCourseDetail(); // 组件加载时获取课程详情
        this.fetchCurrentUserId(); // 获取当前用户ID
    }
};
</script>

<style scoped>
.course-detail-container {
    padding: 20px;
}

.course-title {
    text-align: center;
    font-size: 24px;
    margin-bottom: 20px;
}

.course-cover {
    width: 100%; /* 使容器宽度占满 */
    height: 300px; /* 固定高度 */
    overflow: hidden; /* 隐藏溢出部分 */
    display: flex; /* 使用Flexbox居中图片 */
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
}

.course-cover img {
    width: auto; /* 自动宽度 */
    height: 100%; /* 占满容器高度 */
    object-fit: cover; /* 保持比例并裁剪 */
}

.course-description {
    border: 1px solid #eee; /* 添加边框 */
    padding: 15px; /* 添加内边距 */
    margin: 20px auto; /* 添加外边距 */
    border-radius: 4px; /* 圆角 */
    background-color: #f9f9f9; /* 背景颜色 */
    width: 35%;
}

.play-button {
    display: block;
    margin: 20px auto;
}

.comment-section {
    margin-top: 30px;
}

.comment-input-container {
    display: flex;
    justify-content: center;
    margin-bottom: 15px;
}

.comment-input {
    flex: 1;
    max-width: 400px;
    margin-right: 10px;
}

.comments {
    margin-top: 20px;
}

.comment {
    border: 1px solid #eee;
    padding: 10px;
    margin: 10px auto;
    border-radius: 4px;
    max-width: 600px; /* 设置最大宽度 */
    width: 50%; /* 设置宽度为 90% */
}

.timestamp {
    display: block;
    font-size: 12px;
    color: #999;
    margin-top: 5px;
}

.el-button {
    margin-top: 10px; /* 增加删除按钮的上边距 */
}
</style>