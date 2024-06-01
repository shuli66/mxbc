<template>

  <div>




    <el-row>
      <el-col :span="6" >
        <div style="padding: 20px; border: #333333 1px solid ;text-align: center">
          <img src="@/assets/logo.png" alt="" style="width: 80%">
          <div style="text-align: center">这是个logo</div>
          <div style="text-align: center;color: red">$ 999.99</div>
        </div>

      </el-col>
      <el-col :span="6">

        <div style="padding: 20px; border: #333333 1px solid ;text-align: center">
          <img src="@/assets/logo.png" alt="" style="width: 80%">
          <div style="text-align: center">这是个logo</div>
          <div style="text-align: center;color: red">$ 999.99</div>
        </div>
      </el-col>

      <el-col :span="6">

        <div style="padding: 20px; border: #333333 1px solid ;text-align: center">
          <img src="@/assets/logo.png" alt="" style="width: 80%">
          <div style="text-align: center">这是个logo</div>
          <div style="text-align: center;color: red">$ 999.99</div>
        </div>
      </el-col>

      <el-col :span="6">
        <div style="padding: 20px; border: #333333 1px solid ;text-align: center">
          <img src="@/assets/logo.png" alt="" style="width: 80%">
          <div style="text-align: center">这是个logo</div>
          <div style="text-align: center;color: red">$ 999.99</div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">

        <div style="padding: 1%">
          <el-button >默认按钮</el-button>
          <el-button type="primary">主要按钮</el-button>
          <el-button type="danger">危险按钮</el-button>
          <el-button type="warning">警告按钮</el-button>
          <el-button type="info">信息按钮</el-button>
          <el-button type="success">成功按钮</el-button>
        </div>

        <div style="padding: 1%">
          <el-col :span="24">
            <el-button plain>默认按钮</el-button>
            <el-button type="primary" plain round>主要按钮</el-button>
            <el-button type="danger" plain round>危险按钮</el-button>
            <el-button type="warning" plain>警告按钮</el-button>
            <el-button type="info" plain>信息按钮</el-button>
            <el-button type="success" plain>成功按钮</el-button>
          </el-col>
        </div>

        <div style="padding: 1% ;margin-top: 1%">
          <el-button circle icon="el-icon-edit" type="primary"></el-button>
          <el-button circle icon="el-icon-delete" type="danger"></el-button>

        </div>

      </el-col>
    </el-row>

    <el-row>
      <el-rol>
        <el-input style="width: 200px" v-model="value" placeholder="请输入内容"></el-input>
        <el-input type="textarea" style="width: 200px" v-model="value1" placeholder="请输入内容"></el-input>
        <el-input show-password style="width: 200px" v-model="password" placeholder="请输入密码"></el-input>
        <el-input clearable prefix-icon="el-icon-search" style="width: 200px" v-model="value2" placeholder="请输入内容"></el-input>
        <el-input suffix-icon="el-icon-user" style="width: 200px" v-model="value2" placeholder="请输入内容"></el-input>
        <el-button>搜索</el-button>
      </el-rol>
    </el-row>

    <el-row style="margin: 20px 0">
      <el-autocomplete style="width: 200px" placeholder="请输入内容" :fetch-suggestions="querySearch" :trigger-on-focus="false" v-model="value3"></el-autocomplete>
    </el-row>

  <el-row>
    <el-select>
      <el-option value="苹果" ></el-option>
      <el-option value="香蕉"></el-option>
      <el-option value="橘子"></el-option>
    </el-select>

    <el-select v-model="select1" multiple>
      <el-option v-for="item in fruits" :key="item.id" :label="item.name" :value="item.name"></el-option>
    </el-select>

    <el-select v-model="select2" @change="changeUser" filterable>
      <el-option v-for="item in users" :key="item.ID" :label="item.label" :value="item.ID"></el-option>
    </el-select>

  </el-row>

   <el-row>
     <el-radio-group v-model="radio" @change="selectRadio">
       <el-radio  label="男"></el-radio>
       <el-radio  label="女"></el-radio>
     </el-radio-group>

     <el-checkbox-group v-model="checkList" @change="selectCheck">
        <el-checkbox label="选项1"></el-checkbox>
        <el-checkbox label="选项2"></el-checkbox>
     </el-checkbox-group>

     <el-date-picker v-model="date" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" @change="changeDate"></el-date-picker>
     <el-date-picker v-model="datetime" type="datetime" placeholder="选择日期时间"></el-date-picker>

   </el-row>


    <el-table
        :data="tableData"
        border
        :header-cell-style="{ background: 'aliceblue' }"
    >
      <el-table-column label="序号" prop="id" align="center"></el-table-column>
      <el-table-column label="地址" prop="address" align="center"></el-table-column>
      <el-table-column label="名称" prop="name" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="primary" @click="edit(scope.row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>



  </div>
</template>

<script>
export default {
  name: "element",
  data() {
    return {
      tableData:[
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},
          {name:"wsy",address:"sdj",id:"5"},

      ],
      value: '',
      value1: '',
      value2:'',
      value3: '',
      password:'',
      select1:null,
      select2:null,
      select3:null,
      radio:'',
      checkList:[],
      date:'',
      datetime:'',
      coffees:[{value:'1星巴克咖啡'},{value:'2瑞幸咖啡' },{value:'3库迪咖啡'}],
      fruits:[{name:'苹果',id:1},
        {name:'香蕉',id: 2},
        {name:'橘子',id:3},
        {name: 'wsy',id:4}],
      users:[{label:'wjh',ID:15555},
          { label:'wsy',ID:254454545},
            {label:'ll',ID:3785655}]

    }
  },
  methods:{
    edit(row){
      // alert(row.name)

      this.$message.success(row.name)
    },
    querySearch(query,cb) {
       let result = query ? this.coffees.filter(coffee => coffee.value.includes(query)) : this.coffees
       cb(result)
    },
    changeUser(){
      console.log(this.select2)
    },
    selectRadio(){
      alert(this.radio)
    },
    selectCheck(){
      console.log(this.checkList)
    },
    changeDate(){
      console.log(this.date)
    }
  }
}
</script>

<style scoped>

</style>