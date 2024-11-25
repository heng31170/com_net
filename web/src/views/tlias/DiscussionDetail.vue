<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-container>
                <el-main>
                    <div class="discussion-container">
                        <h2 class="discussion-title">{{ question.content }} <el-button type="primary"
                                @click="openAddDiscussionDialog" style="float: right;">添加讨论</el-button></h2>
                        <div v-for="(topic, index) in discussions" :key="index" class="topic-item">
                            <div class="topic-info">
                                <span class="topic-title">{{ topic.content }}</span>
                            </div>
                            <div class="topic-footer">
                                <span class="last-reply">回复时间：{{ topic.createdAt }}</span>
                            </div>
                        </div>
                    </div>
                    <!-- 新增讨论对话框 -->
                    <el-dialog title="添加讨论" :visible.sync="dialogAddDiscussionVisible">
                        <el-form ref="form" :model="addDiscussion" label-width="80px">
                            <el-form-item label="讨论">
                                <el-input v-model="addDiscussion.content"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddDiscussion">确认</el-button>
                                <el-button @click="dialogAddDiscussionVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束讨论问题对话框 -->
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import { API_URL } from '@/config';
import axios from 'axios';

export default {
    data() {
        return {
            //
            questionId: null,
            question: {},
            discussions: [
            ],
            dialogAddDiscussionVisible: false,
            addDiscussion: {
                questionId: '',
                content: '',
            },
        };
    },
    methods: {
        // 打开对话框并清空
        openAddDiscussionDialog() {
            this.addDiscussion.content = '';
            this.dialogAddDiscussionVisible = true;
        },
        // 新增讨论
        confirmAddDiscussion() {
            if (!this.addDiscussion.content.trim()) {
                this.message.error("讨论内容不能为空");
            }
            axios.post(`${API_URL}/discussion/add`, {
                content: this.addDiscussion.content, questionId: this.questionId
            })
                .then(response => {
                    this.dialogAddDiscussionVisible = false;
                    this.addDiscussion.content = '';
                    this.getDiscussions();
                    this.$message.success("讨论添加成功");
                })
                .catch((error) => {
                    this.$message.error("添加失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 获取讨论问题
        getQuestion() {
            axios
                .get(`${API_URL}/question/${this.questionId}`)
                .then(response => {
                    this.question = response.data;
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });

        },
        // 获取讨论
        getDiscussions() {
            axios
                .get(`${API_URL}/discussion/${this.questionId}`)
                .then(response => {
                    this.discussions = response.data.sort((a,b)=> {
                        return new Date(b.createdAt) - new Date(a.createdAt);
                    });
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });

        },

        // 跳转页面
        goToDiscussionDetail(questionId) {
            sessionStorage.setItem('selectedQId', questionId);
            window.open(`${window.location.origin}/discussion/${questionId}`, '_blank')
        }
    },
    mounted() {
        this.questionId = sessionStorage.getItem('selectedQId');
        this.getQuestion();
        this.getDiscussions();
    },
};
</script>

<style scoped>
.discussion-container {
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.discussion-title {
    font-size: 24px;
    margin-bottom: 20px;
    text-align: center;
}

.topic-item {
    background-color: #fff;
    border: 1px solid #e0e0e0;
    border-radius: 4px;
    padding: 15px;
    margin-bottom: 10px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    cursor: pointer;
    /* 添加鼠标手型 */
}

.topic-info {
    display: flex;
    flex-direction: column;
}

.topic-title {
    font-size: 18px;
    font-weight: bold;
}

.topic-status {
    color: #007bff;
    /* 蓝色状态 */
}

.topic-footer {
    font-size: 14px;
    color: #666;
}
</style>