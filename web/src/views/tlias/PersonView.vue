<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header style="
                font-size: 40px;
                background-color: rgb(238, 241, 246);
                display: flex;
                justify-content: space-between;
                align-items: center;
            ">
                <span style="
                    display: block;
                    width: 100%;
                    text-align: center;
                    transform: translateX(-50%);
                    margin-left: 50%;
                ">{{ curEmp.isManager? '拔尖班打卡系统' : '个人打卡系统' }}</span>
                <el-menu :default-active="null" class="el-menu-demo" mode="horizontal" @select="handleSelect">
                    <el-submenu index="2">
                        <template #title>Hello! {{ curEmp.name }}</template>
                        <el-menu-item index="2-1">个人中心</el-menu-item>
                        <el-menu-item index="2-2">修改密码</el-menu-item>
                        <el-menu-item index="2-3" @click="unlogin">登出</el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu :default-openeds="['1', '3']">
                        <el-submenu index="1">
                            <template #title><i class="el-icon-message"></i>系统信息管理</template>
                            <el-menu-item index="1-2">
                                <router-link to="/emp">拔剑班管理</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>

                <!-- 表单 -->
                <el-main style="display: flex; align-items: center; justify-content: center">
                    <el-form :model="curEmp" status-icon ref="curEmpForm" label-width="100px">
                        <el-form-item label="头像" prop="image">
                            <el-upload class="upload-demo" :on-change="handleFileChange" :show-file-list="false"
                                action="">
                                <img v-if="curEmp.image" :src="curEmp.image" class="avatar-preview" />
                                <i v-else class="el-icon-plus avatar-placeholder"></i>
                            </el-upload>
                        </el-form-item>

                        <el-form-item label="账号" prop="account">
                            <el-input v-model="curEmp.account" autocomplete="off" readonly></el-input>
                        </el-form-item>
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="curEmp.name" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="性别" prop="gender">
                            <el-select v-model="curEmp.gender" placeholder="请选择性别">
                                <el-option label="男" value="男"></el-option>
                                <el-option label="女" value="女"></el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="是否管理员" prop="isManager">
                            <el-switch v-model="curEmp.isManager" disabled></el-switch>
                        </el-form-item>
                        <el-form-item label="职位" prop="position">
                            <el-input v-model="curEmp.position" autocomplete="off"></el-input>
                        </el-form-item>
                        <el-form-item label="入职日期" prop="entryDate">
                            <el-date-picker v-model="curEmp.entryDate" type="date"
                                placeholder="选择入职日期"></el-date-picker>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" @click="savePerson" style="margin-left: 10%">保存</el-button>
                            <el-button @click="goBack" type="text" style="float: right">返回</el-button>
                        </el-form-item>
                    </el-form>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
import axios from "axios";
import { API_URL } from "@/config";
import moment from "moment";
import { mapGetters } from "vuex";

export default {
    data() {
        return {
            empId: '',
            selectedFile: null,
            curEmp: {
                account: "",
                name: "",
                gender: "",
                position: "",
                entryDate: "",
                isManager: "",
                image: "",
            },
        };
    },
    computed: {
        ...mapGetters(["getCurEmp"]),
        emp() {
            return this.getCurEmp || {};
        },
    },
    methods: {
        // 处理文件选择
        handleFileChange(file) {
            this.selectedFile = file.raw;
        },
        // 获取当前个人信息
        getPerson() {
            this.empId = this.emp.id;
            axios
                .get(`${API_URL}/api/emp/${this.empId}`)
                .then((response) => {
                    this.curEmp = response.data;
                })
                .catch((error) => {
                    console.error("获取员工信息失败", error);
                })
        },
        // 确认修改保存
        savePerson() {
            const formattedDate = moment(this.curEmp.entryDate).format('YYYY-MM-DD');
            this.curEmp.entryDate = formattedDate;
            const formData = new FormData();
            const { name, image, gender, position, entryDate, isManager } = this.curEmp;
            // 添加json数据
            formData.append("json", JSON.stringify({
                id: this.empId,
                name,
                gender,
                position,
                entryDate,
                image,
                isManager
            }));
            // 添加文件
            if (this.selectedFile) {
                formData.append("file", this.selectedFile);
            }
            axios
                .post(`${API_URL}/api/emp/update`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                .then((response) => {
                    this.getPerson();
                    this.$message.success("保存成功!")
                })
                .catch((error) => {
                    console.error("保存失败:", error);
                })
        },
        // 返回
        goBack() {
            this.$router.go(-1);
        },
        handleSelect() { },
        // 登出
        unlogin() {
            // 清除 token
            sessionStorage.removeItem('token');
            // this.$router.replace('/login');
            this.$router.push('/login').then(() => {
                window.location.reload(); // 刷新页面
            });
        },
    },
    mounted() {
        this.getPerson();
    },
};
</script>

<style scoped>
.avatar-preview {
    width: 80px;
    height: 80px;
    border-radius: 50%;
    object-fit: cover;
    /* 保持头像比例 */
}

.avatar-placeholder {
    font-size: 24px;
    color: #c0c4cc;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    border: 1px dashed #d9d9d9;
    background-color: #f5f7fa;
}

.el-input__inner[readonly] {
    cursor: not-allowed !important;
    /* 禁止光标样式 */
    background-color: #f5f7fa;
    /* 可选：背景色 */
}
</style>