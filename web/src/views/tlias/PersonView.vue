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
                ">个人数据</span>

            </el-header>
            <el-container>

                <!-- 表单 -->
                <el-main style="display: flex; align-items: center; justify-content: center">
                    <el-form :model="curUser" status-icon ref="curUserForm" label-width="100px">

                        <el-form-item label="账号" prop="username">
                            <el-input v-model="curUser.username" autocomplete="off" readonly></el-input>
                        </el-form-item>
                        <el-form-item label="姓名" prop="name">
                            <el-input v-model="curUser.name" autocomplete="off" readonly></el-input>
                        </el-form-item>
                        <el-form-item label="身份" prop="role">
                            <el-input v-model="curUser.role" autocomplete="off" readonly></el-input>
                        </el-form-item>
                        <el-form-item>
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
            userId: '',
            selectedFile: null,
            curUser: {},
        };
    },
    computed: {
    },
    methods: {
        // 获取当前个人信息
        getPerson() {
            axios
                .get(`${API_URL}/user/${this.userId}`)
                .then((response) => {
                    this.curUser = response.data;
                })
                .catch((error) => {
                    console.error("获取员工信息失败", error);
                })
        },


        // 返回
        goBack() {
            window.close();
        },
        handleSelect() { },
    },
    mounted() {
        this.userId = sessionStorage.getItem('selectedPersonId');
        this.getPerson();
    },
};
</script>

<style scoped>


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