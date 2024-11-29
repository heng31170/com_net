<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-container>
                <el-main>
                    <div class="discussion-container">
                        <h2 class="discussion-title">
                            {{ question.content }}
                            <el-button type="primary" @click="exportTable" style="float: right;">导出讨论表</el-button>
                            <el-button type="primary" @click="openAddDiscussionDialog" v-if="curUser.role"
                                style="float: right;margin-right: 1%;">添加讨论</el-button>

                        </h2>

                        <div v-for="(topic, index) in discussions" :key="index" class="topic-item">
                            <div class="topic-user" @click="toPerson(topic.userId)">
                                <span class="username">{{ topic.name }}</span> <!-- 显示用户名 -->
                            </div>
                            <div class="topic-info">
                                <span class="topic-title">{{ topic.content }}</span>
                            </div>
                            <div class="topic-footer">
                                <span class="last-reply">回复时间：{{ new Date(topic.createdAt).toLocaleString() }}</span>
                                <el-button v-if="topic.userId === curUserId" type="danger"
                                    @click.stop="deleteDiscussion(topic.discussionId)" style="margin-left: 10px;">
                                    删除
                                </el-button>
                            </div>
                        </div>
                    </div>

                    <!-- 新增讨论对话框 -->
                    <el-dialog title="添加讨论" :visible.sync="dialogAddDiscussionVisible">
                        <el-form ref="form" :model="addDiscussion" label-width="80px">
                            <el-form-item label="讨论">
                                <el-input v-model="addDiscussion.content" type="textarea" rows="5"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddDiscussion">确认</el-button>
                                <el-button @click="dialogAddDiscussionVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束讨论问题对话框 -->

                    <!-- 删除确认对话框 -->
                    <el-dialog title="确定删除该讨论?" :visible.sync="dialogDelDiscussionVisible">
                        <el-form>
                            <el-form-item>
                                <span>您确定要删除这个讨论吗？</span>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmDelDiscussion">确定</el-button>
                                <el-button @click="dialogDelDiscussionVisible = false">取消</el-button>
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
import * as XLSX from 'xlsx'; // 确保引入 XLSX 库
import moment from 'moment'; // 确保引入 moment.js

export default {
    data() {
        return {
            //
            curUserId: null,
            curUser: {},
            questionId: null,
            question: {},
            discussions: [
            ],
            dialogAddDiscussionVisible: false,
            dialogDelDiscussionVisible: false, // 删除确认对话框可见性
            addDiscussion: {
                questionId: '',
                content: '',
            },
            discussionToDelete: null, // 记录待删除的讨论ID
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
    },
    methods: {
        // 导出表格
        exportTable() {
            // 过滤 discussions，只保留所需字段
            const filteredData = this.discussions.map(topic => ({
                讨论问题: this.question.content, // 添加问题内容
                用户名: topic.name,
                讨论内容: topic.content,
                回复时间: moment(topic.createdAt).format('YYYY-MM-DD HH:mm:ss') // 格式化时间
            }));

            const today = moment().format('YYYY-MM-DD');
            const wb = XLSX.utils.book_new(); // 创建新的工作簿
            const ws = XLSX.utils.json_to_sheet(filteredData); // 将数据转换为工作表
            XLSX.utils.book_append_sheet(wb, ws, '讨论表'); // 添加工作表到工作簿
            XLSX.writeFile(wb, `${today}_讨论表_${this.question.content}.xlsx`); // 导出文件
        },
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
                content: this.addDiscussion.content, questionId: this.questionId, userId: this.curUserId,
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
                    this.discussions = response.data.sort((a, b) => {
                        return new Date(b.createdAt) - new Date(a.createdAt);
                    });
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });

        },
        // 删除讨论对话框
        deleteDiscussion(discussionId) {
            this.discussionToDelete = discussionId;
            this.dialogDelDiscussionVisible = true;
        },
        // 确认删除讨论
        confirmDelDiscussion() {
            axios.post(`${API_URL}/discussion/del/${this.discussionToDelete}`)
                .then(() => {
                    this.discussions = this.discussions.filter(topic => topic.discussionId !== this.discussionToDelete); // 过滤已删除的讨论
                    this.dialogDelDiscussionVisible = false; // 关闭对话框
                    this.discussionToDelete = null; // 清空待删除讨论ID
                    this.$message.success("讨论删除成功!"); // 提示成功
                })
                .catch(error => {
                    console.error("删除讨论失败:", error);
                    this.$message.error("删除讨论失败!");
                });
        },
        // 个人中心
        toPerson(id) {
            sessionStorage.setItem('selectedPersonId', id);
            window.open(`${window.location.origin}/person/${id}`, '_blank')
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
        this.curUserId = this.getCurUser.userId;
        if (this.getCurUser !== null) this.curUser = this.getCurUser;
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