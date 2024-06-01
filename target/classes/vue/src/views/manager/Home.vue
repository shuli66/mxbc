<template>
  <div>
    <div style="box-shadow: 0 0 10px rgba(0,0,0,.1); padding:10px 20px; border-radius: 5px;margin-bottom: 10px;font-size: 20px">
      早上好,{{ loginUser.userName }},祝你开心每一天！
    </div>

    <div style="display: flex" >
      <el-card style="width: 30%;margin-right: 10px">
        <div slot="header" class="clearfix">
          <span>朝如青丝暮成雪....</span>
        </div>
        <div>
          灰暗的时光里握住彼此的手难能可贵!
          <div style="margin-top: 20px">
            <div style="margin: 10px 0"><strong>主题色</strong></div>
            <el-button type="primary">按钮</el-button>
            <el-button type="success">按钮</el-button>
            <el-button type="warning">按钮</el-button>
            <el-button type="danger">按钮</el-button>
            <el-button type="info">按钮</el-button>
          </div>
        </div>
      </el-card>


    </div>


    <div style="display: flex;margin: 10px 0 ">

      <el-card style="width: 50%;margin-right: 10px">
        <div slot="header" class="clearfix">
          <span>文件的上传和下载</span>
        </div>

        <div>

          <el-upload
              action="http://localhost:8081/file/upload"
              :headers="{token: loginUser.token}"
              :on-success="handleFileUpload" >
            <!--                    list-type="fileList"-->

            <el-button size="mini" type="primary">单文件上传</el-button>
          </el-upload>

        </div>

        <div style="margin: 10px 0">
          <el-upload
              action="http://localhost:8081/file/upload"
              :headers="{token: loginUser.token}"
              :on-success="handleMultipleFileUpload"
              multiple
          >
            <el-button size="mini" type="primary">多文件上传</el-button>
          </el-upload>
        </div>


      </el-card>
    </div>
  </div>
</template>

<script>

export default {
  name: "Home",
  data(){
    return{
      loginUser: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      url:''

    }
  },

  created() {
    this.checkLogin();
  },

  methods: {

    checkLogin() {
      if (! this.loginUser.token) {
        this.$router.push('/login');
      }
    },

    handleFileUpload(response, file, fileList) {
      this.fileList=fileList

    },

    handleMultipleFileUpload(response, file, fileList) {
      this.urls = fileList.map(v => v.response?.data)
    },

  },

}
</script>

<style scoped>

</style>