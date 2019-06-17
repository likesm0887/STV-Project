[TOC]

# Getting Started ![](https://i.imgur.com/tHVBxR3.png)
![](https://i.imgur.com/2nwEw3t.png)

1. 下載 [node.js and npm](https://nodejs.org/en/download/releases/)![](https://i.imgur.com/FA5BxQA.png)
    - 安裝Node.js 8.11.4，程式會自動安裝相對應版本的npm
    - 版本如下 :
        - ![](https://i.imgur.com/QFhpF0R.png)

2. 用npm 下載 appium
    - 在npm裡輸入 npm install -g appium

3. 下載 appium 環境檢查程式
    - 在npm裡輸入 npm install -g appium-doctor

4. 檢查appium是否有下載成功
    - 輸入 appium -v 
    - 得 1.10.1 版

5. 使用 appium-doctor
    - 於 cmd 輸入 appium-doctor
    - 調整環境變數，使appium-doctor全為綠燈
    ![](https://i.imgur.com/MRKkpks.png)

6. 開啟 project run 看看

# Appium Keyword Based(AKB)
![](https://i.imgur.com/NQlR781.png)

## 設定Config
![](https://i.imgur.com/UMwmlwN.png)
- DeviceName, SerialNumber, AndroidVersion：為Android裝置設定，需要對映裝置名稱、序列號碼、和版本
- AppiumPort：為Appium server所監聽的port，如果有衝突可使用別的port
- TestDataPath：為測試資料路徑
- ScriptPath：為腳本存放路徑，如果指定目錄則為執行該目錄下所有腳本
- TestAnomaly：設定為True，代表執行anomaly偵測，包含離開App及重新回到App、裝置旋轉兩次等

## Test Data
- 測試資料為一Excel檔案，紀錄各頁面相關元件資訊，可定義元件名稱、元件xPath，xPath內可使用一個變數，以${VARIABLE}表示變數，AKB將會自動取代成元件參數(Element parameter)，例如：
![](https://i.imgur.com/hqMQywa.png)

- 可以將列合併作為區分該頁面的元件區塊，以及在第三欄之後加上註解說明或者相關圖片，如下圖所示：
![](https://i.imgur.com/81hc3tj.png)

- 每個Sheet為一個自定義的頁面，之後會用於腳本撰寫，如下圖所示將會有四個頁面。
在撰寫腳本時，輸入Activity name並不方便，因此我們讓使用者使用自定義的View name。
![](https://i.imgur.com/IW9WTUI.png)

## Script
- 腳本為一文字檔(.txt)，格式如下，語法間隔必須為四個空格或一個Tab，事件及元件參數可使用帶有空白之文字例如{My tasks}，腳本內可包含註解，每行第一個字元使用#代表該行為說明。
    - ViewName    Event{EVENT_PARAMETER}    Element{ELEMENT_PARAMETER}
    - Event{EVENT_PARAMETER}

![](https://i.imgur.com/d4HT89F.png)

### Script syntax
- Element manipulation
    - **Click element**
        - Description: 點擊UI元件
        - Syntax: *View* **Click** *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Delete text**
        - Description: 刪除TextField內的文字，一次一個字元
        - Syntax: *View* **Delete**{*TIMES*} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Type text**
        - Description: 輸入文字
        - Syntax: *View* **TypeText**{`some text`} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Press progress bar**
        - Description: 點擊元件比例位置
        - Syntax: *View* **PressPercentage**{`percent`} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Type text randomly**
        - Description: 在文字欄上隨機輸入unicode字串
        - Syntax: *View* **TypeTextRandomly** *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Swipe element**
        - Description: 滑動元件
        - *Activity* **Swipe**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - direction: `UP` / `DOWN` / `RIGHT` / `LEFT` (Case insensitive)
    - **Scroll to element**
        - Description: 滑動到元件
        - Syntax: *View* **Scroll**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - direction: `up` / `down` /`left` / `right`
    - **Select the calender year**
        - Description: 滑動到指定年分，並點選年分
        - Syntax: *View*  **SelectCalenderYear**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Select the calender Month**
        - Description: 滑動到指定月份
        - Syntax: **SelectCalender**{`month`}
    - **Select the time zone**
        - Description: 滑動到指定時區
        - Syntax: *View* **SelectTimeZone**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - direction: `up` / `down`
    - **Select Tomorrow in calendar**
        - Description: 選擇明天
        - Syntax: **SelectTomorrow**
    - **Select Someday in calendar**
        - Description: 選擇後天（第3～6天都屬Someday範圍，預設後天）
        - Syntax: **SelectSomeday**
    - **Move the checkItem up**
        - Description: 往上移動 CheckItem
        - Syntax: *View* **MoveUp** *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Move the checkItem down**
        - Description: 往下移動 CheckItem
        - Syntax: *View* **MoveDown** *ELEMENT*{`ELEMENT_PARAMETER`}
    
- Phone operation
    - **Launch app**
        - Description: 啟動App
        - Syntax: **LaunchApp**
    - **Restart**
        - Description: 重新啟動App
        - Syntax: **Restart**
    - **RestartAndClean**
        - Description: 重新啟動App，並且刪除App資料
        - Syntax: **RestartAndClean**
    - **Rotate**
        - Description: 旋轉螢幕
        - Syntax: **Rotate**
    - **PressBackKey**
        - Description: 按下返回鍵，可用於關閉鍵盤
        - Syntax: **PressBackKey**
    - **PressEnter**
        - Description: 按下Enter
        - Syntax: **PressEnter**

- Assertion
    - **Assert view**
        - Description: assert當下View名稱
        - Syntax: **AssertView**{`expected View`}
    - **Assert count**
        - Description: assert元件出現次數(根據XPath)
        - Syntax: *View* **AssertCount**{*EXPECTED_COUNT*} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Assert text**
        - Description: assert元件文字屬性(text)
        - Syntax: *View* **AssertText**{*EXPECTED_TEXT*} *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Assert exist**
        - Description: assert元件是否存在
        - Syntax: *View* **AssertExist** *ELEMENT*{`ELEMENT_PARAMETER`}
    - **Assert text exist**
        - Description: assert文字是否出現在當前畫面
        - Syntax: AssertTextExist{*EXPECTED_TEXT*}

- Others
    - **Load script** 
        - Description: 載入其他相依的Script，參數為Script Root的相對路徑
        - Syntax: *LoadScript*{`referred script path`}
    - **Wait for**
        - Description: 等待時間，以秒為單位
        - Syntax: *WaitFor{`WAITING_TIME`}*

# Script main
- Script main為一腳本撰寫工具，以AKB為基礎讓使用者能一次輸入一行指令並立刻執行，讓使用者能夠立刻看到執行指令的結果，最後可選擇是否儲存成腳本。
