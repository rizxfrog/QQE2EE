# QQE2EE 测试环境配置指南

## 📱 推荐：Android 模拟器测试方案

### 一、创建模拟器

1. **打开 Device Manager**
   ```
   Android Studio → Tools → Device Manager
   ```

2. **创建新设备**
   - 点击 `Create Device`
   - 硬件选择：**Pixel 6** 或 **Pixel 7**（推荐）
   - 系统镜像：选择 **API 33 (Android 13)** 或更高，**必须带 Google Play 图标**
   - 完成创建

3. **启动模拟器**
   - 双击设备列表中的模拟器
   - 等待系统完全启动（约 30-60 秒）

---

### 二、安装 QQ

#### 方法 A：拖拽安装（最简单）
1. 从 [QQ 官网](https://im.qq.com/mobileqq/) 下载 APK
2. 将 APK 文件直接拖拽到模拟器窗口
3. 自动安装完成

#### 方法 B：ADB 命令安装
```bash
# 在项目根目录打开终端
adb install C:\Users\你的用户名\Downloads\QQ8.9.68.apk

# 验证安装
adb shell pm list packages | findstr qq
# 应该输出：package:com.tencent.mobileqq
```

---

### 三、配置测试账号

1. **登录 QQ**
   - 在模拟器中打开 QQ
   - 使用小号登录（建议准备两个测试账号）

2. **创建测试环境**
   - 创建一个测试群
   - 或者添加另一个小号为好友
   - 用于测试消息收发

---

### 四、运行 QQE2EE

#### 方式 1：Android Studio 直接运行
```
点击 Run 按钮（Shift + F10）
→ 选择模拟器作为目标设备
→ 应用自动安装并启动
```

#### 方式 2：命令行运行
```bash
./gradlew installDebug
```

---

### 五、授予权限

1. **无障碍权限**
   ```
   打开 QQE2EE → 点击猫爪按钮
   → 跳转到无障碍设置页面
   → 找到 "QQE2EE" 并开启
   → 返回应用，猫爪变深色表示成功
   ```

2. **悬浮窗权限**（如果需要）
   ```
   设置 → 应用 → QQE2EE → 权限 → 悬浮窗 → 允许
   ```

---

### 六、开始测试

#### ✅ 加密发送测试
1. 打开 QQ → 进入测试群/好友聊天
2. 确认输入框旁的发送按钮有**浅蓝色遮罩**
3. 输入文字："这是一条测试消息"
4. **长按发送按钮**（标准模式）或**直接点击**（沉浸模式）
5. 观察发送的内容是否变成"猫语"密文

#### ✅ 解密测试
**标准模式**：
- 点击收到的密文消息
- 弹出解密窗口显示明文

**沉浸模式**：
- 密文消息自动显示解密按钮
- 无需点击即可看到明文

#### ✅ 文件传输测试
1. **双击输入框** → 拉起附件发送界面
2. 选择图片/文件（<10MB）
3. 等待上传进度完成
4. 发送加密的文件链接

---

### 七、调试技巧

#### 1. 查看日志
```bash
# 过滤 QQE2EE 的日志
adb logcat | findstr "NCBaseHandler"

# 或者使用 Android Studio 的 Logcat 面板
# Filter: NCBaseHandler
```

#### 2. 关键日志标签
- `NCBaseHandler` - 核心处理器日志
- `QQE2EEApp.TAG` - 应用全局日志
- `CryptoManager` - 加密相关日志

#### 3. Layout Inspector（重要！）
用于分析 QQ 界面的无障碍节点：
```
Android Studio → Tools → Layout Inspector
→ 选择 com.tencent.mobileqq 进程
→ 查看 UI 树结构
→ 找到输入框、发送按钮的 resource-id
```

#### 4. 截图/录屏
```bash
# 截图
adb shell screencap -p /sdcard/screenshot.png
adb pull /sdcard/screenshot.png

# 录屏（按 Ctrl+C 停止）
adb shell screenrecord /sdcard/test.mp4
adb pull /sdcard/test.mp4
```

---

### 八、常见问题

#### ❓ 模拟器无法启动
```
解决方案：
1. 确保启用了 VT-x/AMD-V（BIOS 设置）
2. 关闭其他虚拟机软件（VMware、VirtualBox）
3. 尝试使用 Cold Boot Now 强制重启
```

#### ❓ QQ 安装失败
```
解决方案：
1. 检查 APK 是否完整下载
2. 尝试不同版本的 QQ（推荐 8.9.68）
3. 清除模拟器数据后重试
```

#### ❓ 无障碍服务无法启动
```
解决方案：
1. 确保在系统设置中已开启权限
2. 重启模拟器
3. 重新安装应用
```

#### ❓ 找不到发送按钮节点
```
解决方案：
1. 使用 Layout Inspector 查看正确的 ID
2. 更新 app/src/main/java/me/wjz/QQE2EE/service/handler/QQHandler.kt 中的配置
3. 或使用应用的"扫描功能"自动适配
```

---

### 九、快速测试脚本

项目根目录提供了 `start-test-env.bat` 脚本：

```batch
start-test-env.bat
```

自动完成：
1. 启动模拟器
2. 等待系统就绪
3. 启动 QQ
4. 显示设备连接状态

---

## 📊 测试清单

完成以下测试项以确保功能正常：

- [ ] 无障碍服务成功启动
- [ ] 发送按钮遮罩正常显示
- [ ] 标准模式：长按发送按钮 → 发送密文
- [ ] 标准模式：点击密文 → 弹出解密窗口
- [ ] 沉浸模式：点击发送按钮 → 直接发送密文
- [ ] 沉浸模式：密文自动解密显示
- [ ] 双击输入框 → 拉起附件界面
- [ ] 选择图片 → 加密上传成功
- [ ] 发送文件链接 → 接收方可以解密下载
- [ ] 切换密钥 → 加解密使用新密钥
- [ ] 自定义适配其他聊天应用

---

## 🔗 相关资源

- [Android 无障碍服务文档](https://developer.android.com/guide/topics/ui/accessibility/service)
- [QQ NT 架构分析](https://github.com/NapNeko/NapCatQQ)
- [Layout Inspector 使用教程](https://developer.android.com/studio/debug/layout-inspector)

---

**⚠️ 重要提示**：
- 本工具仅供学习交流使用
- 请勿用于非法用途
- 测试时建议使用小号，避免主账号风险
