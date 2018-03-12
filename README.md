# aliyun-slb-helper
功能：使用aliyun slb sdk完成实例的备份与克隆
<br>
配置：在项目根目录添加配置文件 "ak.json"
{
"accessKeyId": "xxxxx",
"accessKeySecret": "xxxxx"
}
<br>
注意：需要使用jdk1.8以上编译


<br>参数：
<br>export all  导出所有地域所有实例到slbs目录下;
<br>clone local filname 使用导出到本地的slb配置文件克隆一个实例
<br>clone online region loadbalancerid 指定源实例所在地域和实例id，克隆一个新实例；
<br>clone功能忽略原有实例的付费类型，一律生成按量付费实例
