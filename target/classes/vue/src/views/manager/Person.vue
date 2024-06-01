<template>
  <div>

    <el-card style="width: 50%">
      <el-form :model="user" label-width="80px" style="padding-right: 20px">
        <div style="margin: 15px; text-align: center">
          <el-upload
              class="avatar-uploader"
              action="http://localhost:8081/file/upload"
              :headers="{ token: adminUser.token }"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
          >
            <img v-if="user.avatar" :src="user.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </div>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="user.userName" placeholder="用户名" disabled></el-input>
        </el-form-item>

        <el-form-item label="电话" prop="phoneNumber">
          <el-input v-model="user.phoneNumber" placeholder="电话"></el-input>
        </el-form-item>

        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" placeholder="邮箱"></el-input>
        </el-form-item>

        <el-form-item label="用户类型" prop="userType">
          <el-input type="textarea" v-model="user.userType" placeholder="用户类型"></el-input>
        </el-form-item>

        <div style="text-align: center; margin-bottom: 20px">
          <el-button type="primary" @click="updateUser" :loading="loading">保 存</el-button>
        </div>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: "Person",
  data() {
    return {
      adminUser: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      user: {},
      loading: false,

    };
  },
  created() {
    if (this.$route.query.user) {
      this.user = JSON.parse(this.$route.query.user);
    }
  },
  methods: {
    handleAvatarSuccess(response, file, fileList) {
      if (response && response.data) {
        this.user.avatar = response.data;
      } else {
        this.$message.error('上传失败，未收到有效的响应');
      }
    },
    updateUser() {
      this.loading = true;
      // 发送更新请求
      request.put('/user/updateUser', this.user)
          .then(res => {
            if (res.code === '200') {
              this.$message.success('更新成功');
              // 更新成功后重定向回 User 页面
              this.$router.push('/user');
            } else {
              this.$message.error(res.msg);
            }
          })
          .catch(error => {
            this.$message.error('更新失败，请稍后重试');
            console.error(error);
          })
          .finally(() => {
            this.loading = false;
          });
    }
  }
};
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
