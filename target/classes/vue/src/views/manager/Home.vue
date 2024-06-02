<template>
  <div class="home-container">


    <div class="greeting">
      <div class="greeting-message">
        <span>用户信息：</span>
        <span>早上好，{{ loginUser.userName }}，祝你开心每一天！</span>
      </div>
    </div>

    <div class="greeting" style="display: flex">
      <div class="greeting-message">
        <span style="color: red">注：只有“超级管理员”才能查看管理员信息</span>
      </div>
    </div>

    <div class="flex-container" style="display: flex">

      <el-card class="card-item" style="width: 50%;">
        <div slot="header" class="card-header">
          <span>平静的湖面只有呆板的倒映，奔腾的激流才有美丽的浪花！幸福不是靠别人来布施，而是要自己去赢取！生命的意义在不断挑战自己，战胜自己！</span>
        </div>
        <div class="card-content">
          灰暗的时光里握住彼此的手难能可贵！
          <div class="button-group">
            <el-button type="primary">按钮</el-button>
            <el-button type="success">按钮</el-button>
            <el-button type="warning">按钮</el-button>
            <el-button type="danger">按钮</el-button>
            <el-button type="info">按钮</el-button>
          </div>
        </div>
      </el-card>




      <el-card class="card-item" style="width: 50%;">
        <div slot="header" class="card-header">
          <span>文件的上传和下载</span>
        </div>
        <div class="card-content">
          <el-upload
              action="http://localhost:8081/file/upload"
              :headers="{token: loginUser.token}"
              :on-success="handleFileUpload"
          >
            <el-button size="mini" type="primary">单文件上传</el-button>
          </el-upload>
        </div>
        <div class="card-content">
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




      <el-row :gutter="10">

      <!-- 折线图 -->
      <el-col :span="12">
        <el-card>
          <div style="width: 100%; height: 400px" id="line"></div>
        </el-card>
      </el-col>

      <!-- 柱状图 -->
      <el-col :span="12">
        <el-card>
          <div style="width: 100%; height: 400px" id="bar"></div>
        </el-card>
      </el-col>

    </el-row>






    <!-- el-rate 组件 -->
    <div class="rate-container" style="position: fixed;
        bottom: 0px;
        right: 10px;
        padding: 10px; /* 可选：为div添加一些内边距 */">
      <div class="block rate-block" style="margin-left: auto; margin-bottom: 20px;">
        <span class="demonstration">不变色评分</span>
        <el-rate v-model="value1"></el-rate>
      </div>
      <div class="block rate-block" style="margin-left: auto;">
        <span class="demonstration">变色评分</span>
        <el-rate
            v-model="value2"
            :colors="colors">
        </el-rate>
      </div>
    </div>

  </div>
</template>


<script>
import * as echarts from 'echarts';
export default {
  name: "Home",

  mounted() {

    // 折线图数据
    const option = {
      title: {
        text: '订单销售的趋势图',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '金额',
          data: [120, 200, 150, 80, 70, 110, 130], // 示例数据，你可以根据实际情况修改
          type: 'line',
          smooth: true
        },
        {
          name: '库存',
          data: [80, 90, 70, 60, 55, 75, 85], // 示例数据，你可以根据实际情况修改
          type: 'line',
          smooth: true
        }
      ]
    }

    // 柱状图数据
    const option1 = {
      title: {
        text: '订单销售的柱状图',
        left: 'center'
      },
      tooltip: {
        trigger: 'axis'
      },
      legend: {
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: ['水果', '零食', '饮料', '奶制品', '生活用品']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: '金额',
          data: [320, 180, 450, 250, 300], // 示例数据，你可以根据实际情况修改
          type: 'bar',
          smooth: true
        },
        {
          name: '销量',
          data: [100, 150, 200, 180, 220], // 示例数据，你可以根据实际情况修改
          type: 'bar',
          smooth: true
        }
      ]
    }

    // 初始化图表
    let lineDom = document.getElementById('line');
    let lineChart = echarts.init(lineDom);
    lineChart.setOption(option);

    let barDom = document.getElementById('bar');
    let barChart = echarts.init(barDom);
    barChart.setOption(option1);
  },

  data() {
    return {
      loginUser: JSON.parse(localStorage.getItem('honey-user') || '{}'),
      value1: null,
      value2: null,
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']  // 等同于 { 2: '#99A9BF', 4: { value: '#F7BA2A', excluded: true }, 5: '#FF9900' }
    };
  },
  methods: {
    handleFileUpload(response, file, fileList) {
      this.fileList = fileList;
    },
    handleMultipleFileUpload(response, file, fileList) {
      this.urls = fileList.map(v => v.response?.data);
    },
  },
};
</script>

<style scoped>
.home-container {
  padding: 20px;
}

.greeting {
  box-shadow: 0 0 10px rgba(0,0,0,.1);
  padding: 10px 20px;
  border-radius: 5px;
  margin-bottom: 20px;
  font-size: 20px;
}


.card-item {
  box-shadow: 0 0 10px rgba(0,0,0,.1);
  border-radius: 5px;
}


.card-content {
  padding: 20px;
}

.button-group {
  margin-top: 20px;
}

</style>
