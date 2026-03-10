@echo off
chcp 65001 >nul
echo ========================================
echo   QQE2EE - 安装 QQ 到模拟器
echo ========================================
echo.

:: 检查 ADB 是否可用
where adb >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo [警告] 未找到 adb 命令，尝试使用默认路径...
    set ADB_PATH=C:\Users\%USERNAME%\AppData\Local\Android\Sdk\platform-tools\adb.exe
    if not exist "%ADB_PATH%" (
        echo [错误] 也未找到 adb.exe，请手动指定路径！
        echo 默认路径：%ADB_PATH%
        pause
        exit /b 1
    )
) else (
    set ADB_PATH=adb
)

echo [信息] 使用 ADB: %ADB_PATH%
echo.

:: 检查设备连接
echo [1/3] 检查设备连接...
%ADB_PATH% devices
echo.

:: 提示用户输入 QQ APK 路径
echo [2/3] 请输入 QQ APK 文件的完整路径：
echo (例如：C:\Users\%USERNAME%\Downloads\QQ8.9.68.apk)
set /p QQ_APK_PATH="路径："

:: 检查文件是否存在
if not exist "%QQ_APK_PATH%" (
    echo [错误] 文件不存在：%QQ_APK_PATH%
    echo 请检查路径是否正确！
    pause
    exit /b 1
)

echo.
echo [3/3] 开始安装 QQ...
echo 安装包路径：%QQ_APK_PATH%
echo.

:: 执行安装
%ADB_PATH% install "%QQ_APK_PATH%"

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo   ✅ QQ 安装成功！
    echo ========================================
    echo.
    echo 现在可以启动 QQ 了：
    echo %ADB_PATH% shell am start -n com.tencent.mobileqq/com.tencent.mobileqq.activity.SplashActivity
) else (
    echo.
    echo ========================================
    echo   ❌ 安装失败，请检查：
    echo   1. 模拟器是否已启动
    echo   2. APK 文件是否损坏
    echo   3. 存储空间是否充足
    echo ========================================
)

echo.
pause
