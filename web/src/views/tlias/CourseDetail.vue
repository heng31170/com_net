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
                        <h2 style="margin-right: 5%;">评论区</h2><el-input type="textarea" v-model="newComment"
                            placeholder="添加评论..." class="comment-input" @keyup.enter.native="addComment" :rows="3">
                        </el-input>
                        <el-button type="primary" @click="addComment">发布</el-button>
                    </div>
                    <div class="comments">
                        <div v-for="(comment) in courseComments" :key="comment.commentId" class="comment">
                            <div class="user-box" @click="toPerson(comment.userId)">
                                <span class="username">{{ comment.name }}</span> <!-- 显示用户名 -->
                            </div>
                            <p>{{ comment.content }}</p>
                            <small class="timestamp">{{ new Date(comment.createdAt).toLocaleString() }}</small>
                            <el-button v-if="comment.userId === currentUserId" type="danger"
                                @click="deleteComment(comment.commentId)">
                                删除
                            </el-button>
                        </div>
                    </div>
                </div>
                <!-- 弹出对话框,是否删除  -->
                <el-dialog title="确定删除该评论?" :visible.sync="dialogDelCommentVisible">
                    <el-form>
                        <el-form-item>
                            <el-button type="primary" @click="confirmDelComment">确定</el-button>
                            <el-button @click="dialogDelCommentVisible = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
                <!-- 结束删除对话框  -->
            </el-main>
        </el-container>
    </div>
</template>

<script>
import { API_URL } from "@/config";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
    data() {
        return {
            courseId: null, // 当前课程ID
            courseDetail: {},
            newComment: '',
            courseComments: [],
            currentUserId: null,
            dialogDelCommentVisible: false, // 删除评论对话框可见性
            commentToDelete: null,  // 记录待删除的评论ID
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
    },

    methods: {
        // 删除评论对话框
        deleteComment(commentId) {
            this.commentToDelete = commentId;
            this.dialogDelCommentVisible = true;
        },
        // 确认删除评论
        confirmDelComment() {
            axios.post(`${API_URL}/comment/del/${this.commentToDelete}`)
                .then(() => {
                    this.courseComments = this.courseComments.filter(comment => comment.commentId !== this.commentToDelete);  // 过滤删除的评论
                    this.dialogDelCommentVisible = false; // 关闭对话框
                    this.commentToDelete = null; // 清空待删除评论ID
                    this.$message.success("评论删除成功!"); // 提示成功
                })
                .catch(error => {
                    console.error("删除评论失败:", error);
                    this.$message.error("删除评论失败!");
                });
        },
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
                    this.courseComments = response.data;
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
                    .then(response => {
                        console.log("评论添加成功:", response);
                        this.getCourseComments(); // 重新加载评论
                        this.newComment = ''; // 清空输入框
                        this.$message.success("评论添加成功!");
                    })
                    .catch(error => {
                        console.error("添加评论失败:", error);
                        if (error.response) {
                            if (error.response.status === 401) {
                                this.$message.error("未登录，请登录后再试");
                            } else {
                                this.$message.error("添加评论失败: " + error.response.data);
                            }
                        } else {
                            this.$message.error("添加评论失败: " + error.message);
                        }
                    });
            }
        },
        // 个人中心
        toPerson(id) {
            sessionStorage.setItem('selectedPersonId', id);
            window.open(`${window.location.origin}/person/${id}`, '_blank')
        },

        // 获取当前用户ID
        fetchCurrentUserId() {
            this.currentUserId = this.getCurUser.userId; // 示例：从sessionStorage获取
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
    width: 100%;
    /* 使容器宽度占满 */
    height: 300px;
    /* 固定高度 */
    overflow: hidden;
    /* 隐藏溢出部分 */
    display: flex;
    /* 使用Flexbox居中图片 */
    justify-content: center;
    /* 水平居中 */
    align-items: center;
    /* 垂直居中 */
}

.course-cover img {
    width: auto;
    /* 自动宽度 */
    height: 100%;
    /* 占满容器高度 */
    object-fit: cover;
    /* 保持比例并裁剪 */
}

.course-description {
    border: 1px solid #eee;
    /* 添加边框 */
    padding: 15px;
    /* 添加内边距 */
    margin: 20px auto;
    /* 添加外边距 */
    border-radius: 4px;
    /* 圆角 */
    background-color: #f9f9f9;
    /* 背景颜色 */
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
    max-width: 600px;
    /* 设置最大宽度 */
    width: 50%;
    /* 设置宽度为 90% */
}

.user-box {
    cursor: pointer;
    float: left;
    background-color: #f0f0f0;
    /* 背景色 */
    border: 1px solid #ccc;
    /* 边框 */
    border-radius: 3px;
    /* 圆角 */
    padding: 5px 10px;
    /* 内边距 */
    margin-bottom: 5px;
    /* 下部外边距 */
    /* display: inline-block; 使其成为行内块元素 */
}

.username {
    font-weight: bold;
    /* 加粗用户名 */
    color: #333;
    /* 字体颜色 */
}

.timestamp {
    display: block;
    font-size: 12px;
    color: #999;
    margin-top: 5px;
}

.el-button {
    margin-top: 10px;
    /* 增加删除按钮的上边距 */
}
</style>