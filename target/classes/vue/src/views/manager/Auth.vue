<template>
  <div class="auth-container">
    <div class="auth-message">
      <h1>无权访问</h1>
      <p>您当前的权限类型为不够！</p>
      <router-link to="/">返回首页</router-link>
    </div>
  </div>
</template>

<script>
export default {
  name: "Auth",
  data() {
    return {


    };
  },
};
</script>

<style scoped>
.auth-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-image: url("~@/assets/wuquan.jpg");
  background-size: cover;
  background-position: center;
  text-align: center;
  color: #fff; /* 设置文本颜色为白色，使其在深色背景上清晰可见 */
}

.auth-message {
  background-color: rgba(0, 0, 0, 0.6); /* 半透明背景，以提高可读性 */
  padding: 20px;
  border-radius: 10px;
}

.auth-message h1 {
  font-size: 2.5em;
  margin-bottom: 20px;
}

.auth-message p {
  font-size: 1.2em;
  margin-bottom: 20px;
}

.auth-message a {
  color: #00c1d4;
  text-decoration: none;
  font-size: 1.1em;
}

.auth-message a:hover {
  text-decoration: underline;
}
</style>
