<template>
    <div>
        <el-container style="height: 700px; border: 1px solid #eee">
            <el-header
                style="font-size: 40px; background-color: rgb(238, 241, 246); display: flex; justify-content: space-between; align-items: center;">
                <span
                    style="display: block; width: 100%; text-align: center; transform: translateX(-50%); margin-left: 50%;">个人打卡系统</span>
                <!-- 菜单放置在右侧 -->
                <el-menu :default-active="null" class="el-menu-demo" mode="horizontal" @select="handleSelect"
                    style="margin-right: 10%;" id="delBgc">
                    <el-submenu index="2">
                        <template slot="title">Hello! {{ curEmp.name }} <img :src="curEmp.image"
                                style="width: 40%; height: auto; border-radius: 50%;"></template>
                        <el-menu-item index="2-1" @click="toPerson">个人中心</el-menu-item>
                        <el-menu-item index="2-2" @click="dialogUpdatePasswdVisible = true">修改密码</el-menu-item>
                        <el-menu-item index="2-3" @click="unlogin">登出</el-menu-item>
                    </el-submenu>
                </el-menu>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu :default-openeds="['1', '3']">
                        <el-submenu index="1">
                            <template slot="title"><i class="el-icon-message"></i>系统信息管理</template>

                            <!-- <el-menu-item index="1-1">
                            <router-link to="/login">登录管理</router-link>
                            </el-menu-item> -->
                            <el-menu-item index="1-2">
                                <router-link to="/cemp">打卡管理</router-link>
                            </el-menu-item>
                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>

                    <!-- 表格  插入数据？  -->
                    <el-table :data="[curEmp]">
                        <el-table-column prop="name" label="姓名" width="140">
                        </el-table-column>
                        <el-table-column prop="image" label="头像" width="140">
                            <template slot-scope="scope">
                                <img :src="scope.row.image" alt="头像"
                                    style="width: 40%; height: 40%; border-radius: 50%;" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="gender" label="性别" width="140"></el-table-column>
                        <el-table-column prop="position" label="职位" width="140"></el-table-column>
                        <el-table-column prop="entryDate" label="入职日期" width="180"></el-table-column>
                        <el-table-column prop="lastOperationTime" label="最后操作时间" width="230"></el-table-column>
                        <el-table-column prop="action" label="操作" width="230">
                            <template slot-scope="scope">
                                <el-button v-if="!isCheck[scope.row.id]" type="primary" size="mini"
                                    @click="getIdAndCheckDialog(scope.row.id)">
                                    打卡
                                </el-button>
                                <el-button v-else type="success" size="mini"
                                    @click="getIdAndUnCheckDialog(scope.row.id)">
                                    已打卡
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <!-- 结束表格   插入数据 -->

                    <!-- 弹出更改密码对话框 -->
                    <el-dialog title="修改密码" :visible.sync="dialogUpdatePasswdVisible">
                        <el-form :model="EmpPasswd" status-icon :rules="rules" ref="EmpPasswd" label-width="100px"
                            class="demo-ruleForm">

                            <el-form-item label="原密码" prop="passwd">
                                <el-input type="password" v-model="EmpPasswd.originPasswd"
                                    autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="新密码" prop="passwd">
                                <el-input type="password" v-model="EmpPasswd.passwd" autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item label="确认密码" prop="confirmPasswd">
                                <el-input type="password" v-model="EmpPasswd.confirmPasswd"
                                    autocomplete="off"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-button type="primary" @click="updatePasswd">确定</el-button>
                                <el-button @click="dialogUpdatePasswdVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束更改密码对话框 -->


                    <!-- 弹出对话框,是否签到  -->
                    <el-dialog title="确定签到?" :visible.sync="dialogCheckVisible">
                        <el-form>
                            <el-form-item>
                                <el-button type="primary" @click="confirmCheck">确定</el-button>
                                <el-button @click="dialogCheckVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束签到对话框 -->
                    <!-- 弹出对话框,是否取消签到  -->
                    <el-dialog title="确定取消签到?" :visible.sync="dialogUnCheckVisible">
                        <el-form>
                            <el-form-item>
                                <el-button type="primary" @click="confirmUnCheck">确定</el-button>
                                <el-button @click="dialogUnCheckVisible = false">取消</el-button>
                            </el-form-item>
                        </el-form>
                    </el-dialog>
                    <!-- 结束取消签到对话框 -->




                    <br /><br />
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>



<script>
import axios from "axios";
import { API_URL } from "@/config";
import moment from 'moment';
import { mapGetters } from "vuex";
export default {
    data() {
        return {
            tableData: [],
            // 当前员工
            curEmp: {
                account: "",
                name: "",
                gender: "",
                position: "",
                entryDate: "",
                isManager: "",
                image: "",
                lastOperationTime: "",
            },
            // 修改密码
            EmpPasswd: {
                originPasswd: '',
                passwd: '',
                confirmPasswd: '',
            },
            // 条件查询
            searchEmp: {
                name: "",
                gender: "",
                entryDate: "",
                position: "",
            },
            // 分页信息
            pagination: {
                currentPage: 1,
                pageSize: 10,
                total: 0
            },
            // 编辑员工
            changeEmp: {
                name: "",
                image: null,
                gender: "",
                position: "",
                entryDate: "",
            },
            // 新增员工
            AddEmp: {
                account: "",
                name: "",
                gender: "",
                position: "",
                entryDate: "",
            },
            dialogDelVisible: false,
            dialogCheckVisible: false,
            dialogUnCheckVisible: false,
            dialogChangeEmpVisible: false,
            dialogAddEmpVisible: false,
            dialogUpdatePasswdVisible: false,
            empId: null,
            isCheck: {},
            selectedFile: null,
        };
    },
    computed: {
        ...mapGetters(['getCurEmp']),
        emp() {
            return this.getCurEmp || {};
        }
    },
    methods: {

        // 更新密码
        updatePasswd() {
            // 验证密码输入
            if (!this.EmpPasswd.originPasswd || !this.EmpPasswd.passwd || !this.EmpPasswd.confirmPasswd) {
                this.$message.error("原密码、新密码和确认密码不能为空");
                return;
            }

            if (this.EmpPasswd.passwd !== this.EmpPasswd.confirmPasswd) {
                this.$message.error("新密码和确认密码不一致");
                return;
            }
            // 检查新密码是否包含空格
            if (this.EmpPasswd.passwd.includes(" ")) {
                this.$message.error("新密码不能包含空格");
                return;
            }
            // 发送更新请求
            axios
                .post(`${API_URL}/api/passwd/update`, {
                    id: this.emp.id, // 传递员工 ID
                    originPasswd: this.EmpPasswd.originPasswd,
                    passwd: this.EmpPasswd.passwd,
                })
                .then(response => {
                    if (response.data) {
                        this.$message.success("密码更新成功,请重新登录!");
                        this.dialogUpdatePasswdVisible = false; // 关闭对话框
                        this.resetPasswdForm(); // 重置表单
                        this.unlogin();
                    }
                })
                .catch(error => {
                    if (error.response && error.response.data) {
                        this.$message.error(error.response.data.message || "密码更新失败");
                    } else {
                        this.$message.error("密码更新失败");
                    }
                });
        },

        // 重置密码表单
        resetPasswdForm() {
            this.EmpPasswd.originPasswd = '';
            this.EmpPasswd.passwd = '';
            this.EmpPasswd.confirmPasswd = '';
        },
        handleSelect() {
        },
        // 个人中心
        toPerson() {
            this.$router.push('/person');
        },
        // 登出
        unlogin() {
            // 清除 token
            sessionStorage.removeItem('token');
            this.$router.push('/login').then(() => {
                window.location.reload(); // 刷新页面
            });

        },


        // 处理文件选择
        handleFileChange(file) {
            this.selectedFile = file.raw;
        },


        // 上传前的处理
        beforeUpload(file) {
            const isImage = file.type.startsWith('image/');
            if (!isImage) {
                this.$message.error('上传头像只能是图片!');
            }
            return false;
        },




        // 签到对话框
        getIdAndCheckDialog(id) {
            this.empId = id;
            this.dialogCheckVisible = true;
        },
        // 确认签到并关闭对话框
        confirmCheck() {
            this.addCheck(this.empId);
            this.dialogCheckVisible = false;
            this.$message.success("签到成功!")
        },
        // 取消签到对话框
        getIdAndUnCheckDialog(id) {
            this.empId = id;
            this.dialogUnCheckVisible = true;
        },
        // 确认取消签到并关闭对话框
        confirmUnCheck() {
            this.delCheck(this.empId);
            this.dialogUnCheckVisible = false;
            this.$message.success("取消签到成功!")
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



        // 获取打卡状态
        getAllCheck() {
            axios
                .get(`${API_URL}/api/checkins`, {
                })
                .then((response) => {
                    response.data.isCheck.forEach((item) => {
                        this.$set(this.isCheck, item.empId, item.isCheck);
                    });
                })
                .catch((error) => {
                    console.error("获取签到信息失败:", error);
                    console.log(
                        "无法获取签到信息" +
                        (error.response ? error.response.data : error.message)
                    );
                });
        },
        // 打卡
        addCheck(id) {
            axios
                .post(`${API_URL}/api/addcheck?id=${id}`)
                .then(() => {
                    this.getAllCheck();
                })
                .catch((error) => {
                    alert("打卡失败!原因是：" + error);
                });
        },
        // 取消打卡
        delCheck(id) {
            axios
                .post(`${API_URL}/api/delcheck?id=${id}`)
                .then(() => {
                    this.getAllCheck();
                })
                .catch((error) => {
                    alert("取消打卡失败!原因是：" + error);
                });
        },
    },
    mounted() {
        const token = sessionStorage.getItem('token'); // 获取存储的token
        if (!token) { // 如果token为空，跳转到登录页面并刷新
            this.$router.replace('/login').then(() => {
                window.location.reload(); // 刷新页面
            });
        } else { // 如果token存在，继续获取用户信息和员工数据
            this.getPerson();
            // this.getEmp();
            this.getAllCheck();
        }
    },
};
</script>




<style>
.avatar {
    width: 50px;
    height: 50px;
    border-radius: 50%;
    margin-top: 10px;
}

#delBgc {
    background-color: transparent;
}
</style>