# STV-Project CI 架設
## Plugin
- Git  
    - 必裝套件
- GitHub
    - 可以讓jenkins clone github的專案
- HTML Publish plugin
    - 將codecoverage的 Report顯示在jenkins
- JDK Parameter
    - 必裝套件
- LDAP
    - 設定權限，讓成員可以用實驗室的帳號登入
- Config File Provider
    - 此套件可以讓jenkins執行中，抽換掉project中的config檔案

## Slave設定
- Slave中要裝好AKB環境，並且在jenkins的節點管理中設定git maven JDK的路徑
- ![](https://i.imgur.com/ONWYXno.png)
- ![](https://i.imgur.com/4xs8WZQ.png)
- **每開一個Job 都要Build 和 instrument一次project中的app**，不然會無法打開或是無法build codecoverage
- 模擬器要常駐開啟
## Job設定

- ![](https://i.imgur.com/DxnsOiw.png)

- ![](https://i.imgur.com/RWxcBX5.png)

### 設定config
- ![](https://i.imgur.com/zJpG1yq.png)
- 新增一個Simple XML file 
    - ![](https://i.imgur.com/q1rIdUh.png)

- Name 是需要替換檔案的黨名
- Comment是註解
- Content是config內容
    - ![](https://i.imgur.com/rBDzuek.png)
- 接著到Job選擇config 並且 輸入Target(要替換檔案的資料夾)
- ![](https://i.imgur.com/VFOWhsS.png)
### 建置
- ![](https://i.imgur.com/47CyRLL.png)
- clean package  先清理Clean Project
- exec:java -Dexec.mainClass="src.main.java.main" 執行main
- Dmaven.test.skip=true 忽略單元測試

### Build codecoverage
- 使用Window批次指令
- 以下路徑接為絕對路徑
``` java

SET testAppName=opentasks-master  \\待測App的名稱
SET projectPath=D:\Public\jenkins\workspace\%JOB_NAME% \\Job資料夾
SET Path=D:\Public\jenkins\workspace\%JOB_NAME%\opentasks-master \\待測App的路經
SET logPath=%Path%codeCoverage \\存放codeCoverage.ec的檔案路徑

//如果outputs\code-coverage\connected不存在就建立(jacoco需要資料夾)
if not exist %Path%\opentasks\build\outputs\code-coverage\connected mkdir %Path%\opentasks\build\outputs\code-coverage\connected

//如果connected裡面有舊資料就砍掉
if  exist %Path%\opentasks\build\outputs\code-coverage\connected del /F /S %Path%\opentasks\build\outputs\code-coverage\connected\*.ec

//複製AKB抓下來的codecoverage.ec檔案到待測資料夾的connected中
C:\Windows\System32\xcopy.exe  %projectPath%\CodeCoverage   %Path%\opentasks\build\outputs\code-coverage\connected
//回到待策應用程式資料夾 執行gradle jacocoTestReport
D:
cd %projectPath%\%testAppName%
C:\gradle-5.3-all\gradle-5.3\bin\gradle jacocoTestReport
echo [Create done] ------------------------------------------------------------
```
### 建置後

- 輸入HTML的路徑
- ![](https://i.imgur.com/TZQdLcU.png)





