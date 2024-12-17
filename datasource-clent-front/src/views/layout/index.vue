<template>
  <div>
    <el-container>
      <el-header>
        <el-dropdown split-button="true" type="primary" style="float: left; padding-left: -200px">
          选择一个数据源
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item>dev-mysql</el-dropdown-item>
            <el-dropdown-item>dev-pg</el-dropdown-item>
            <el-dropdown-item>dev-sqlserver</el-dropdown-item>
            <el-dropdown-item>dev-sqlite</el-dropdown-item>
            <el-dropdown-item>dev-hana</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
      <el-container>
        <el-aside width="auto">
          <NavMenu/>
        </el-aside>
        <el-container>
          <el-main>Main</el-main>
          <el-footer>Footer</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>
<script setup lang="ts">
import NavMenu from "@/views/layout/navmenu.vue";
</script>

<style>
.el-header, .el-footer {
  background-color: #B3C0D1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  width: auto;
}

.el-main {
  background-color: #E9EEF3;
  color: #333;
  text-align: center;
  line-height: 160px;
}

body > .el-container {
  margin-bottom: 40px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}
</style>
