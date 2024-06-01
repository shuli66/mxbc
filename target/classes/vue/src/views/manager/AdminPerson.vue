<template>
  <div>
    <el-card style="width: 50%">
      <el-form :model="admin" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8081/file/upload"
              :headers="{ token: admin.token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="admin.avatar" :src="admin.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="admin.userName" placeholder="用户名" ></el-input>
        </el-form-item>
        <el-form-item label="电话" prop="phoneNumber">
          <el-input v-model="admin.phoneNumber" placeholder="电话"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="admin.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item label="管理员类型" prop="employeeType">
          <el-input v-model="admin.employeeType" placeholder="管理员类型"></el-input>
        </el-form-item>
        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="update">保存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "AdminPerson",
  data() {
    return {
      employeeId: '', // 管理员ID
      admin: { }, // 管理员信息
    }
  },
  created() {
    if (this.$route.query.admin) {
      this.admin = JSON.parse(this.$route.query.admin);
    } else {
      // 如果没有在路由参数中找到管理员信息，可以根据管理员ID获取管理员信息
      // 确保先设置好管理员ID
      this.employeeId = parseInt(this.$route.query.employeeId);
      this.fetchAdminInfo();
    }
  },

  methods: {
    fetchAdminInfo() {
      // 根据管理员ID获取管理员信息
      request.get(`/employee/getEmployeeById/${this.employeeId}`)
          .then(res => {
            // 将获取到的管理员信息赋值给admin
            this.admin = res.data;
          })
          .catch(error => {
            this.$message.error('获取管理员信息失败：' + error);
          });
    },
    update() {
      // 保存当前的管理员信息到数据库
      request.put('/employee/updateEmployee', this.admin)
          .then(this.handleUpdateSuccess)
          .catch(this.handleUpdateError);
    },
    handleUpdateSuccess(res) {
      if (res.code === '200') {
        // 成功更新
        this.$message.success('保存成功');
        // 保存成功后跳转到指定页面，例如管理员列表页面
        this.$router.push('/admin');
      } else {
        this.handleUpdateError(res);
      }
    },
    handleUpdateError(error) {
      this.$message.error(error.response.data.msg || '保存失败');
      console.error('保存失败:', error);
    },
    handleAvatarSuccess(response, file, fileList) {
      // 把admin的头像属性换成上传的图片的链接
      this.admin.avatar = response.data;
    },
  }
}
</script>

<style scoped>
/deep/.el-form-item__label {
  font-weight: bold;
}
/deep/.el-upload {
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  border-radius: 50%;
}
/deep/.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border-radius: 50%;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
  border-radius: 50%;
}
</style>
