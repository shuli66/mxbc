<template>
  <div>
    <div>
      <el-input style="width: 200px; margin: 0 5px" placeholder="查询姓名" v-model="searchName"></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0">
      <el-button type="primary" plain @click="handleAdd">新增</el-button>
    </div>
    <el-card style="width: 100%">
      <div slot="header" class="clearfix">
        <span>用户信息表</span>
      </div>
      <div>
        <el-table :data="users" style="align-items: center;justify-content: center">
          <el-table-column label="ID" prop="userId"></el-table-column>
          <el-table-column label="用户名" prop="userName"></el-table-column>
          <el-table-column label="手机号" prop="phoneNumber"></el-table-column>
          <el-table-column label="用户类型" prop="userType" :formatter="formatUserType"></el-table-column>
          <el-table-column label="邮箱" prop="email"></el-table-column>
          <el-table-column label="数据更新时间" prop="createdAt" :formatter="formatDate"></el-table-column>
          <el-table-column label="操作" style="align-items: center">
            <template slot-scope="scope">
              <div class="button-container">
                <el-button type="primary" plain @click="editUser(scope.row)">编辑</el-button>
                <el-button type="danger" plain @click="deleteUser(scope.row)">删除</el-button>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="上传头像">
            <template v-slot="scope">
              <el-upload
                  action="http://localhost:8081/file/upload"
                  :headers="{ token: loginUser.token }"
                  :on-success="(response, file, fileList) => handleTableFileUpload(scope.row, response, file, fileList)"
                  :show-file-list="false"
              >
                <el-button size="mini" type="primary">点击上传</el-button>
              </el-upload>
            </template>
          </el-table-column>
          <el-table-column label="头像">
            <template v-slot="scope">
              <div style="display: flex; align-items: center">
                <el-image v-if="scope.row.avatar" :src="scope.row.avatar" style="width: 50px; height: 50px; border-radius: 50%" :preview-src-list="[scope.row.avatar]"></el-image>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>

    <!-- 新增用户对话框 -->
    <el-dialog title="新增用户" :visible.sync="addDialogVisible">
      <el-form :model="newUser" ref="newUserForm">
        <el-form-item label="用户名" :label-width="formLabelWidth">
          <el-input v-model="newUser.userName"></el-input>
        </el-form-item>
        <el-form-item label="手机号" :label-width="formLabelWidth">
          <el-input v-model="newUser.phoneNumber"></el-input>
        </el-form-item>
        <el-form-item label="用户类型" :label-width="formLabelWidth">
          <el-input v-model="newUser.userType"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth">
          <el-input v-model="newUser.email"></el-input>
        </el-form-item>
        <el-form-item label="上传头像" :label-width="formLabelWidth">
          <el-upload
              action="http://localhost:8081/file/upload"
              :headers="{ token: loginUser.token }"
              :on-success="handleAvatarUploadSuccess"
              :show-file-list="false"
          >
            <el-button size="mini" type="primary">点击上传</el-button>
          </el-upload>
          <el-image v-if="newUser.avatar" :src="newUser.avatar" style="width: 50px; height: 50px; border-radius: 50%" :preview-src-list="[newUser.avatar]"></el-image>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNewUser">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from "@/utils/request";
import moment from "moment";

export default {
  name: "User",
  data() {
    return {
      users: [],
      loginUser: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      searchName: '',
      addDialogVisible: false,
      newUser: {
        userName: '',
        phoneNumber: '',
        userType: '普通用户', // 默认值
        email: '',
        passwordHash: '123', // 默认密码
        avatar: '' // 头像 URL
      },
      formLabelWidth: '120px'
    };
  },
  created() {
    this.fetchUsers();
  },
  methods: {
    fetchUsers() {
      request.get('/user/getUserAll').then(res => {
        this.users = res.data;
      });
    },
    formatDate(row, column, cellValue) {
      return moment(cellValue).format('YYYY-MM-DD HH:mm:ss');
    },
    formatUserType(row, column, cellValue) {
      return cellValue ? cellValue : '普通用户';
    },
    handleTableFileUpload(row, response, file, fileList) {
      if (response && response.data) {
        row.avatar = response.data;
        request.put('/user/updateUser', row).then(res => {
          if (res.code === '200') {
            this.$message.success('上传成功');
          } else {
            this.$message.error(res.msg);
          }
        });
      } else {
        this.$message.error('上传失败');
      }
    },
    handleAvatarUploadSuccess(response) {
      if (response && response.data) {
        this.newUser.avatar = response.data;
        this.$message.success('头像上传成功');
      } else {
        this.$message.error('头像上传失败');
      }
    },
    editUser(user) {
      this.$router.push({ path: '/person', query: { user: JSON.stringify(user) } });
    },
    deleteUser(user) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        request.delete(`/user/deleteUser/${user.userId}`).then(res => {
          if (res.code === '200') {
            this.$message.success('删除成功');
            this.fetchUsers();
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
      console.log('查询用户：', this.searchName);
      const params = {
        searchName: this.searchName  // 确保传递的参数名为searchName
      };
      request.get('/user/searchUser', { params }).then(res => {
        console.log('查询结果：', res);
        // 更新用户列表数据
        this.users = res.data;
      }).catch(error => {
        console.error('查询失败：', error);
        this.$message.error('查询失败');
      });
    },

    reset() {
      this.searchName = '';
      this.load(); // 重置时默认加载所有数据
    },
    handleAdd() {
      // 初始化newUser
      this.newUser = {
        userName: '',
        passwordHash: '123', // 默认密码123
        phoneNumber: '',
        userType: '普通用户', // 默认值
        email: '',
        avatar: '' // 重置头像
      };
      this.addDialogVisible = true;
    },
    submitNewUser() {
      this.$refs.newUserForm.validate((valid) => {
        if (valid) {
          request.post('/user/insertUser', this.newUser).then(res => {
            if (res.code === '200') {
              this.$message.success('新增用户成功');
              this.addDialogVisible = false;
              this.fetchUsers();
            } else {
              this.$message.error(res.msg);
            }
          });
        }
      });
    }
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
