# 移动设备应用行为审计系统

### 运行步骤

1. 运行App.java进行FlowDroid分析
2. 在apk所在文件夹内使用apktool工具，下载地址和使用方法：[Apktool - A tool for reverse engineering 3rd party, closed, binary Android apps. (ibotpeaches.github.io)](https://ibotpeaches.github.io/Apktool/)
3. 运行AuditSystem.java生成审计报告



### 运行须知

1. 基于FlowDroid的性能，只能对小于50MB的apk文件分析
2. 需要修改以下几个地方的文件路径：
   - ReadUtil.java 14行：xml文件
   - ReadUtil.java 14行：dot文件
   - App.java 19行：安卓sdk文件android.jar
   - App.java 21行：apk文件
   - App.java 35行：dot文件输出位置
   - AuditSystem.java 23行：报告输出位置
