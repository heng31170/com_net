<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-container>
                <el-main>
                    <div class="exam-container">
                        <h2 class="exam-title">
                            {{ exam.title }}
                            <el-button type="primary" @click="openAddExamAnsDialog" v-if="curUser.role"
                                style="float: right;margin-right: 1%;">提交考试</el-button>
                        </h2>

                        <div v-for="(ans, index) in examAns" :key="index" class="ans-item">
                            <div class="ans-user" @click="toPerson(ans.userId)">
                                <span class="username">{{ ans.name }}</span> <!-- 显示用户名 -->
                            </div>
                            <div class="ans-info">
                                <span class="ans-title">{{ ans.name + exam.title + '的卷子&nbsp;&nbsp;&nbsp;得分：' + ans.score
                                    }}</span>
                            </div>
                            <div class="ans-footer">
                                <span class="last-reply">提交时间：{{ new Date(ans.createdAt).toLocaleString() }}</span>
                                <el-button v-if="ans.userId === curUserId" type="danger"
                                    @click.stop="deleteExamAns(ans.answerId)" style="margin-left: 10px;">
                                    删除
                                </el-button>
                                <el-button type="primary" @click.stop="downloadAns(ans.answerId)" v-if="curUser.role === 'teacher' || ans.userId === curUserId">下载卷子</el-button>
                                <el-button type="primary" @click.stop="openAddScoreDialog(ans.answerId)"
                                    v-if="curUser.role === 'teacher'">评分</el-button>
                            </div>
                        </div>
                    </div>

                    <!-- 新增答案对话框 -->
                    <el-dialog title="提交考试" :visible.sync="dialogAddExamAnsVisible">
                        <el-form ref="form" :model="addExamAns" label-width="80px">
                            <el-form-item label="答案">
                                <el-input v-model="addExamAns.content" type="textarea" rows="10"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddExamAns">确认</el-button>
                                <el-button @click="dialogAddExamAnsVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束答案对话框 -->

                    <!-- 评分对话框 -->
                    <el-dialog title="添加评分" :visible.sync="dialogAddScoreVisible">
                        <el-form ref="form" :model="addScore" label-width="80px">
                            <el-form-item label="评分">
                                <el-input v-model.number="addScore.score" type="number" min="0" max="100"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmAddScore">确认</el-button>
                                <el-button @click="dialogAddScoreVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束评分对话框 -->

                    <!-- 删除确认对话框 -->
                    <el-dialog title="确定删除该答案?" :visible.sync="dialogDelExamAnsVisible">
                        <el-form>
                            <el-form-item>
                                <span>您确定要删除这个答案吗？</span>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="confirmDelExamAns">确定</el-button>
                                <el-button @click="dialogDelExamAnsVisible = false">取消</el-button>
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
            curUserId: null,
            curUser: {},
            examId: null,
            exam: {},
            examAns: [],
            dialogAddExamAnsVisible: false,
            dialogAddScoreVisible: false,
            dialogDelExamAnsVisible: false, // 删除确认对话框可见性
            addExamAns: {
                examId: '',
                content: '',
            },
            addScore: {
                examId: '',
                score: '',
                answerId: null, // 用于关联评分的答案ID
            },
            examAnsToDelete: null, // 记录待删除的答案ID
        };
    },
    computed: {
        ...mapGetters(['getCurUser']),
    },
    methods: {
        // 打开评分对话框
        openAddScoreDialog(answerId) {
            this.addScore.answerId = answerId; // 记录当前评分的答案ID
            this.addScore.score = ''; // 清空评分
            this.dialogAddScoreVisible = true;
        },
        // 确认评分
        confirmAddScore() {
            const scoreValue = Number(this.addScore.score); // 确保将 score 转换为数字

            // 检查 scoreValue 是否有效
            if (isNaN(scoreValue) || scoreValue < 0 || scoreValue > 100) {
                this.$message.error("评分必须在 0 到 100 之间");
                return;
            }

            // 发送请求
            axios.post(`${API_URL}/exam/ans/score`, {
                answerId: this.addScore.answerId,
                score: scoreValue,
            })
                .then(response => {
                    this.dialogAddScoreVisible = false;
                    this.addScore.score = ''; // 重置评分
                    this.getExamAns(); // 刷新答案列表
                    this.$message.success("评分添加成功");
                })
                .catch((error) => {
                    this.$message.error("评分失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 下载卷子
        downloadAns(answerId) {
            const ans = this.examAns.find(a => a.answerId === answerId);
            if (!ans) {
                this.$message.error("答案信息未找到");
                return;
            }

            const content = `
        <html xmlns:w="urn:schemas-microsoft-com:office:word">
        <head>
            <meta charset="utf-8">
            <style>
                body { font-family: Arial, sans-serif; }
                h1 { font-size: 24px; }
                p { font-size: 16px; }
            </style>
        </head>
        <body>
            <h1>${this.exam.title} 的卷子</h1>
            <p>${ans.content}</p>
        </body>
        </html>
    `;

            const blob = new Blob([content], { type: 'application/msword' });
            const url = URL.createObjectURL(blob);

            // 获取当前日期并格式化
            const createdAt = new Date(ans.createdAt);
            const formattedDate = createdAt.toISOString().split('T')[0]; // 格式化为 YYYY-MM-DD

            const link = document.createElement('a');
            link.href = url;
            link.download = `${formattedDate}_${ans.name}_${this.exam.title}.doc`; // 使用答案的用户名和考试标题

            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            URL.revokeObjectURL(url);
        },
        // 打开对话框并清空
        openAddExamAnsDialog() {
            this.addExamAns.content = '';
            this.dialogAddExamAnsVisible = true;
        },
        // 新增答案
        confirmAddExamAns() {
            if (!this.addExamAns.content.trim()) {
                this.$message.error("答案内容不能为空");
                return;
            }
            axios.post(`${API_URL}/exam/ans/add`, {
                content: this.addExamAns.content, examId: this.examId, userId: this.curUserId,
            })
                .then(response => {
                    this.dialogAddExamAnsVisible = false;
                    this.addExamAns.content = '';
                    this.getExamAns();
                    this.$message.success("答案添加成功");
                })
                .catch((error) => {
                    this.$message.error("添加失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 获取考试
        getExam() {
            axios
                .get(`${API_URL}/exam/${this.examId}`)
                .then(response => {
                    this.exam = response.data;
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 获取答案
        getExamAns() {
            axios
                .get(`${API_URL}/exam/ans/${this.examId}`)
                .then(response => {
                    this.examAns = response.data.sort((a, b) => {
                        return new Date(b.createdAt) - new Date(a.createdAt);
                    });
                })
                .catch((error) => {
                    this.$message.error("获取失败: " + (error.response ? error.response.data.message : error.message));
                });
        },
        // 删除答案对话框
        deleteExamAns(answerId) {
            this.examAnsToDelete = answerId;
            this.dialogDelExamAnsVisible = true;
        },
        // 确认删除答案
        confirmDelExamAns() {
            axios.post(`${API_URL}/exam/ans/del/${this.examAnsToDelete}`)
                .then(() => {
                    this.examAns = this.examAns.filter(ans => ans.answerId !== this.examAnsToDelete); // 过滤已删除的答案
                    this.dialogDelExamAnsVisible = false; // 关闭对话框
                    this.examAnsToDelete = null; // 清空待删除答案ID
                    this.$message.success("答案删除成功!"); // 提示成功
                })
                .catch(error => {
                    console.error("删除答案失败:", error);
                    this.$message.error("删除答案失败!");
                });
        },
        // 个人中心
        toPerson(id) {
            sessionStorage.setItem('selectedPersonId', id);
            window.open(`${window.location.origin}/person/${id}`, '_blank');
        },
    },
    mounted() {
        this.examId = sessionStorage.getItem('selectedExamId');
        this.getExam();
        this.getExamAns();
        this.curUserId = this.getCurUser.userId;
        if (this.getCurUser !== null) this.curUser = this.getCurUser;
    },
};
</script>

<style scoped>
.exam-container {
    padding: 20px;
    background-color: #f9f9f9;
    border-radius: 8px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.exam-title {
    font-size: 24px;
    margin-bottom: 20px;
    text-align: center;
}

.ans-user {
    font-size: 14px;
    font-weight: bold;
    color: #007bff;
    /* 用户名颜色 */
}

.username {
    margin-right: 10px;
    /* 用户名与答案内容之间的间距 */
}

.ans-item {
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

.ans-info {
    display: flex;
    flex-direction: column;
}

.ans-title {
    font-size: 18px;
    font-weight: bold;
}

.ans-footer {
    font-size: 14px;
    color: #666;
}
</style>