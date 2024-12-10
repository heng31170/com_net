<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header
                style="font-size: 40px; background-color: rgb(238, 241, 246); display: flex; justify-content: space-between; align-items: center;">
                <span
                    style="display: block; width: 100%; text-align: center; transform: translateX(-50%); margin-left: 50%;">讨论区</span>
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
                            <el-menu-item index="1-5"> <!-- 添加讨论区 -->
                                <router-link to="/exam">考试</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <div class="discussion-container">
                        <h2 class="discussion-title">
                            讨论区
                            <el-button type="primary" @click="openAddQuestionDialog" style="float: right;" v-if="curUser.role">添加问题</el-button>
                        </h2>

                        <div v-for="(topic, index) in discussionQuestions" :key="index" class="topic-item" @click="goToDiscussionDetail(topic.questionId)">
                            <div class="topic-user" @click="toPerson(topic.userId)">
                                <span class="username">{{ topic.name }}</span> <!-- 显示用户名 -->
                            </div>
                            <div class="topic-info">
                                <span class="topic-title">{{ topic.content }}</span>
                            </div>
                            <div class="topic-footer">
                                <span class="last-reply">问题创建时间：{{ new Date(topic.createdAt).toLocaleString() }}</span>
                                <el-button v-if="topic.userId === curUserId" type="danger"
                                    @click.stop="deleteTopic(topic.questionId)" style="margin-left: 10px;">
                                    删除
                                </el-button>
                            </div>
                        </div>
                    </div>

                    <!-- 新增问题对话框 -->
                    <el-dialog title="添加问题" :visible.sync="dialogAddQuestionVisible">
                        <el-form ref="form" :model="addQuestion" label-width="80px">
                            <el-form-item label="问题">
                                <el-input v-model="addQuestion.content"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddQuestion">确认</el-button>
                                <el-button @click="dialogAddQuestionVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束新增问题对话框 -->

                    <!-- 删除确认对话框 -->
                    <el-dialog title="确定删除该问题?" :visible.sync="dialogDelTopicVisible">
                        <el-form>
                            <el-form-item>
                                <el-button type="primary" @click="confirmDelTopic">确定</el-button>
                                <el-button @click="dialogDelTopicVisible = false">取消</el-button>
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
            dialogDelTopicVisible: false,
            topicToDelete: null, // 记录待删除的问题ID
            curUserId: null,
            curUser: {},
            discussionQuestions: [],
            addQuestion: {
                questionId: '',
                content: '',
            },
            dialogAddQuestionVisible: false // 控制对话框显示
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
    },
    methods: {
        // 打开对话框并清空
        openAddQuestionDialog() {
            this.dialogAddQuestionVisible = true;
            this.addQuestion.content = '';
        },
        // 添加问题的确认逻辑
        confirmAddQuestion() {
            if (!this.addQuestion.content.trim()) {
                this.message.error("问题内容不能为空");
            }
            axios.post(`${API_URL}/question/add`, { content: this.addQuestion.content,userId: this.curUserId })
                .then(response => {
                    this.dialogAddQuestionVisible = false;
                    this.addQuestion.content = '';
                    this.getAllQuestions();
                    this.$message.success("讨论添加成功");
                })
                .catch((error) => {
                    this.$message.error("添加失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 获取全部问题
        getAllQuestions() {
            axios
                .get(`${API_URL}/questions`)
                .then(response => {
                    this.discussionQuestions = response.data.sort((a, b) => {
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
            // window.open(`${window.location.origin}/discussion/${questionId}`, '_blank');
            this.$router.push('/discussion/${questionId}');
        },
        // 删除问题对话框
        deleteTopic(questionId) {
            this.topicToDelete = questionId;
            this.dialogDelTopicVisible = true;
        },
        // 确认删除问题
        confirmDelTopic() {
            axios.post(`${API_URL}/question/del/${this.topicToDelete}`)
                .then(() => {
                    this.discussionQuestions = this.discussionQuestions.filter(topic => topic.questionId !== this.topicToDelete); // 过滤已删除的问题
                    this.dialogDelTopicVisible = false; // 关闭对话框
                    this.topicToDelete = null; // 清空待删除问题ID
                    this.$message.success("问题删除成功!"); // 提示成功
                })
                .catch(error => {
                    console.error("删除问题失败:", error);
                    this.$message.error("删除问题失败!");
                });
        },
        // 个人中心
        toPerson(id) {
            sessionStorage.setItem('selectedPersonId',id);
            window.open(`${window.location.origin}/person/${id}`,'_blank')
        },
    },
    mounted() {
        this.getAllQuestions();
        this.curUserId = this.getCurUser.userId;
        if (this.getCurUser !== null) this.curUser = this.getCurUser;
    },
};
</script>

<style scoped>
.topic-user {
    font-size: 14px;
    font-weight: bold;
    color: #007bff;
    /* 用户名颜色 */
}

.username {
    margin-right: 10px;
    /* 用户名与讨论内容之间的间距 */
}

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
}

.topic-info {
    display: flex;
    flex-direction: column;
}

.topic-title {
    font-size: 18px;
    font-weight: bold;
}

.topic-footer {
    font-size: 14px;
    color: #666;
}
</style>