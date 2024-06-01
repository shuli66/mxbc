<template>
  <div>
    <div>
      <el-input style="width: 200px" placeholder="查询用户名" v-model="searchAdminName"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <el-card style="width: 100%">
      <div slot="header" class="clearfix">
        <span>管理员信息表</span>
      </div>
      <div>
        <el-table :data="admins" >
          <el-table-column label="ID" prop="employeeId"></el-table-column>
          <el-table-column label="admin用户名" prop="userName"></el-table-column>
          <el-table-column label="手机号" prop="phoneNumber"></el-table-column>
          <el-table-column label="管理员类型" prop="employeeType"></el-table-column>
          <el-table-column label="邮箱" prop="email"></el-table-column>
          <el-table-column label="数据更新时间" prop="createdAt" :formatter="formatDate"></el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button type="primary" plain @click="editAdmin(scope.row)">编辑</el-button>
              <el-button type="danger" plain @click="deleteAdmin(scope.row)">删除</el-button>
            </template>
          </el-table-column>
          <el-table-column label="上传头像">
            <template v-slot="scope">
              <el-upload
                  action="http://localhost:8081/file/upload"
                  :headers="{token: loginUser.token}"
                  :on-success="(response, file, fileList) => handleTableFileUpload(scope.row, response, file, fileList)"
                  :show-file-list="false"
              >
                <el-button size="mini" type="primary">点击上传</el-button>
              </el-upload>
            </template>
          </el-table-column>
          <el-table-column label="头像">
            <template v-slot="scope">
              <div style="display: flex;align-items: center">
                <el-image v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 50px;height: 50px;border-radius: 50%" :preview-src-list="[scope.row.avatar]"></el-image>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 新增管理员对话框 -->
    <el-dialog title="新增管理员" :visible.sync="addDialogVisible">
      <el-form :model="newAdmin" :rules="rules" ref="newAdminForm">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
          <el-input v-model="newAdmin.userName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="passwordHash">
          <el-input v-model="newAdmin.passwordHash" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth" prop="phoneNumber">
          <el-input v-model="newAdmin.phoneNumber" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="管理员类型" :label-width="formLabelWidth" prop="employeeType">
          <el-input v-model="newAdmin.employeeType" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="email">
          <el-input v-model="newAdmin.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNewAdmin">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  name: "Admin",
  data() {
    return {
      admins: [],
      loginUser: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      searchAdminName: '',
      addDialogVisible: false,
      newAdmin: {
        userName: '',
        passwordHash:'',
        phoneNumber: '',
        employeeType: '',
        email: ''
      },
      formLabelWidth: '120px',
      rules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' }
        ],
        phoneNumber: [
          { required: true, message: '请输入手机号', trigger: 'blur' }
        ],
        employeeType: [
          { required: true, message: '请输入管理员类型', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' }
        ]
      }
    };
  },
  methods: {
    formatDate(row, column, cellValue) {
      return moment(cellValue).format('YYYY-MM-DD HH:mm:ss');
    },
    handleTableFileUpload(row, response, file, fileList) {
      row.avatar = response.data;
      request.put('/employee/updateEmployee', row).then(res => {
        if (res.code === '200') {
          this.$message.success('上传成功');
        } else {
          this.$message.error(res.msg);
        }
      });
    },
    editAdmin(admin) {
      this.$router.push({ path: '/adminPerson', query: { admin: JSON.stringify(admin) } });
    },
    deleteAdmin(admin) {
      this.$confirm('此操作将永久删除该管理员, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/employee/deleteEmployee/${admin.employeeId}`).then(res => {
          if (res.code === '200') {
            this.$message.success('删除成功');
            this.fetchAdmins();
          } else {
            this.$message.error(res.msg);
          }
        }).catch(() => {
          this.$message.error('删除失败，请稍后重试');
        });
      }).catch(() => {
        this.$message.info('已取消删除');
      });
    },
    load() {
      console.log('查询管理员：', this.searchAdminName);
      const params = {
        searchAdminName: this.searchAdminName
      };
      request.get('/employee/searchEmployee', { params }).then(res => {
        console.log('查询结果：', res);
        this.admins = res.data;
      }).catch(error => {
        console.error('查询失败：', error);
        this.$message.error('查询失败');
      });
    },
    reset() {
      this.searchAdminName = '';
      this.load();
    },
    handleAdd() {
      this.addDialogVisible = true;
    },
    submitNewAdmin() {
      this.$refs.newAdminForm.validate(valid => {
        if (valid) {
          request.post('/employee/insertEmployee', this.newAdmin).
          then(res => {
            if (res.code === '200') {
              this.$message.success('新增管理员成功');
              this.addDialogVisible = false;
              this.fetchAdmins();
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    },
    fetchAdmins() {
      request.get('/employee/getEmployeeAll').then(res => {
        this.admins = res.data;
      }).catch(error => {
        this.$message.error('获取管理员列表失败：' + error);
      });
    }
  },
  mounted() {
    this.fetchAdmins();
  }
};
</script>

<style scoped>
.button-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px; /* 按钮之间的间距 */
}
</style>
