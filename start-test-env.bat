@echo off
echo ========================================
echo   QQE2EE 测试环境启动脚本
echo ========================================
echo.

:: 1. 启动模拟器（替换为你的 AVD 名称）
echo [1/3] 启动 Android 模拟器...
start "" "C:\Users\%USERNAME%\AppData\Local\Android\Sdk\emulator\emulator.exe" -avd Pixel_6_API_33

:: 等待模拟器启动（约 30 秒）
echo 等待模拟器启动完成...
timeout /t 30 /nobreak

:: 2. 检查设备连接
echo [2/3] 检查设备连接...
adb devices

:: 3. 启动 QQ（确保已安装）
echo [3/3] 启动 QQ...
adb shell am start -n com.tencent.mobileqq/com.tencent.mobileqq.activity.SplashActivity

echo.
echo ========================================
echo   测试环境准备完成！
echo   现在可以在 Android Studio 中运行 QQE2EE
echo ========================================
pause
