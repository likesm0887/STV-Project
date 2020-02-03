[TOC]

# Appium Keyword Based(AKB)
## Introduction
* AKB是一款基於Appium所開發的Keyword driven框架，使用者能夠自己定義頁面、元件、元件變數等，提高抽象化程度，撰寫腳本時不必知道App元件的xPath，而是使用自己定義的元件進行操作，提高撰寫腳本的便利性，當元件xPath改變時，只需要修改TestData即可，不必變動到所有腳本。
* AKB提供了許多內建的事件(命令)，事件可分為三類：
    * Element manipulation: 對App元件進行操作，如：Click、TypeText、Swipe等
    * Phone operation：對手機環境進行操作，如：LaunchApp、Rotate等
    * Assertion：用來確認執行結果是否如同預期
* 腳本可以Reuse，當腳本之間有相依性時，不必撰寫重複的腳本，可以直接引入
* 能夠方便的客製化指令
* 擁有執行結果報表，包含執行腳本數量、時間、成功或失敗等資訊
* 進階使用能夠產生腳本執行後的Code Coverage

* DEMO
https://www.youtube.com/watch?v=3-zlCvBwULs 
* AKB System Conceptial Diagram
    ![](https://i.imgur.com/NQlR781.png)

## Getting Started ![](https://i.imgur.com/tHVBxR3.png)
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

## App
- 待測App必須經過instrument

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
- 腳本為一文字檔(.txt)，格式如下:
    - 指令分為兩類，元件操作指令及一般指令
        1. ViewName    Event{EVENT_PARAMETER}    Element{ELEMENT_PARAMETER}
            - 分別包含畫面名稱、事件種類、元件
            - 上述之間間格必須為**一個tab或四個空格**
        3. Event{EVENT_PARAMETER}
            - 只有事件種類
    - 事件參數(EVENT_PARAMETER)及元件參數(ELEMENT_PARAMETER)內可以輸入空白或特殊文字
    - 每一行第一個字元如果是#代表該行為註解

- Example:
    ```java=
    TaskList    Click   folder{My tasks}

    DisplayedLists    TypeText{New List}    list_editText
    ```
    ![](https://i.imgur.com/d4HT89F.png)

### Script syntax
- **Element manipulation**
    - **Click element**
        - Description: 點擊UI元件
        - Syntax: *View* **Click** *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            // 點擊元件
            TaskList    Click   float_add_btn
            ```
            
    - **Type text**
        - Description: 輸入文字
        - Syntax: *View* **TypeText**{`some text`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            // 輸入文字"title"
            EditTasks    TypeText{title}    title_editText
            ```
            
    - **Delete text**
        - Description: 刪除TextField內的文字，一次一個字元
        - Syntax: *View* **Delete**{*TIMES*} *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            // 刪除三個字元
            EditTasks    Delete{3}    title_editText
            ```
            
    - **Press progress bar**
        - Description: 點擊元件比例位置
        - Syntax: *View* **PressPercentage**{`percent`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            EditTasks    PressPercentage{80}    percent_complete_bar
            ```
            
    - **Type text randomly**
        - Description: 在文字欄上隨機輸入unicode字串
        - Syntax: *View* **TypeTextRandomly** *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            // 隨機輸入文字
            EditTasks	TypeTextRandomly    description_editText
            ```
            
    - **Swipe element**
        - Description: 滑動元件
        - *Activity* **Swipe**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - `direction`: `UP` / `DOWN` / `RIGHT` / `LEFT` (Case insensitive)
        - Example:
            ```java=
            // 由右到左滑動
            TaskList    Swipe{LEFT}    pager
            ```
            
    - **Scroll to element**
        - Description: 滑動到元件
        - Syntax: *View* **Scroll**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - `direction`: `up` / `down` /`left` / `right`
        - Example:
            ```java=
            // 向下滑動直到找到元件
            EditTasks    Scroll{down}    start_date
            ```

- **Phone operation**
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

- **Assertion**
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

- **Others**
    - **Load script** 
        - Description: 載入其他相依的Script，參數為Script Root的相對路徑
        - Syntax: *LoadScript*{`referred script path`}
        - Example:
        ```java=
        // 載入addTask腳本
        LoadScript{./script/UseCaseCoverage/addTask.txt}
        ```
        
    - **Wait for**
        - Description: 等待時間，以秒為單位
        - Syntax: *WaitFor{`WAITING_TIME`}*
        - Example:
        ```java=
        // 等待10秒
        WaitFor{10}
        ```

- **Command Customize for [Tasks](https://f-droid.org/en/packages/org.dmfs.tasks/) App (Not guarantee accuracy)**
    - **Select the calender year**
        - Description: 滑動到指定年分，並點選年分
        - Syntax: *View*  **SelectCalenderYear**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - Example:
            ```java=
            // 向上滑動直到找到1900年
            EditTasks    SelectCalenderYear{Up}    calendar_pick_year{1900}
            ```
            
    - **Select the calender Month**
        - Description: 滑動到指定月份
        - Syntax: **SelectCalender**{`month`}
        - Example:
            ```java=
            // 選擇6月
            SelectCalendarMonth{6}
            ```
            
    - **Select the time zone**
        - Description: 滑動到指定時區
        - Syntax: *View* **SelectTimeZone**{`direction`} *ELEMENT*{`ELEMENT_PARAMETER`}
        - direction: `up` / `down`
        - Example:
            ```java=
            // 向上滑動timezone視窗直到找到GMT-11:00
            EditTasks   SelectTimeZone{up}   time_zone_options{GMT-11:00}
            ```
    - **Select Tomorrow in calendar**
        - Description: 選擇明天
        - Syntax: **SelectTomorrow**
        - Example:
            ```java=
            // 選擇明天
            SelectTomorrow
            ```
            
    - **Select Someday in calendar**
        - Description: 選擇後天（第3～6天都屬Someday範圍，預設後天）
        - Syntax: **SelectSomeday**
        - Example:
            ```java=
            // 選擇後天（第3～6天都屬Someday範圍，預設後天）
            SelectSomeday
            ```
           
    - **Move the checkItem up**
        - Description: 往上移動 CheckItem
        - Syntax: *View* **MoveUp** *ELEMENT*{`ELEMENT_PARAMETER`}

    - **Move the checkItem down**
        - Description: 往下移動 CheckItem
        - Syntax: *View* **MoveDown** *ELEMENT*{`ELEMENT_PARAMETER`}
    
    - **Assert view**
        - Description: assert當下View名稱
        - Syntax: **AssertView**{`expected View`}
        - Example:
        ```java=
        // 驗證當前畫面為ViewTask
        AssertView{ViewTask}
        ```

## ScriptＭain
- ScriptＭain為一互動式腳本撰寫工具，以AKB為基礎讓使用者能一次輸入一行命令並立刻執行，讓使用者能夠立刻看到執行命令的結果，最後可選擇是否儲存成腳本。

- 操作方式
    1. **Single instruction**: 每次輸入一行命令，AKB會立刻執行，並且每次詢問使用者是否儲存該行命令。
    2. **Batch instruction**: 可連續輸入命令，AKB會立刻執行，直到輸入終止條件後，ScriptMain詢問使用者是否要儲存批次執行之腳本。

    3. **Save script**: 當使用者不想使用此工具，會自動詢問是否要產生新的txt腳本到script folder裡面，如果使用者選擇是就會要求使用者輸入名稱。

- **Demo**
    https://www.youtube.com/watch?v=cTVBHPrcw6Q

- **Coverage** 
    ![](https://i.imgur.com/OyuLgOM.jpg)
