STV Project
===

## AUT
- [Tasks](https://f-droid.org/en/packages/org.dmfs.tasks/)

## Get Application's Element Id
1. UIAutomatorViewer: $ANDROID_HOME/tools/bin/uiautomatorviewer
2. Turn on [developer options](https://developer.android.com/studio/debug/dev-options)
3. [turtorial](https://www.guru99.com/uiautomatorviewer-tutorial.html)

## Resource
* [Sample](https://github.com/appium/java-client/tree/171fcca4a546ccf1ab3c2afb1010b01510f59689/src/test/java/io/appium/java_client)
* [Selector Turtorial](http://www.automationtestinghub.com/appium-tutorial/)
* [W3C XPath](https://www.w3.org/TR/xpath/all/#dt-string-value)

## Issue
- [android.support.v7.app.ActionBar$Tab](https://github.com/appium/appium/issues/8102)

## 提醒
- 各個頁面的`Show completed tasks`是獨立的
- task有修改時才能`Save`

## Test Case
- Task
    - 左右滑動task，未完成時Complete / Edit，完成後Delete / unComplete
    - 修改時區，開始時間要顯示該時區和換算成+8的時間
    - Status一開始為`needs action`，勾選check item後應該要變成`in process`，check list全部完成後則變成`done`，且`percent complete`會跟著變化

- Tasks
- Tasks due
    - 今天：Today
    - 第二天：Tomorrow
    - 未來的一星期內：Next days
    - 未來的一星期或以後：Someday
- Tasks starting
    - 今天以前：Already started
    - 今天：Today
    - 第二天：Tomorrow
    - 未來的一星期內：Someday
    - 未來的一個星期或以後：Later
- Task priority
- Task progress
    - Percent complete
        - 0%: Nothing accomplished
        - 5-40%: Way to go
        - 45-60%: Halfway there
        - 65-95%: Almost done
        - 100%: Done
- Search
    - 只能找到還沒完成的task
    - 刪除歷史紀錄
- Displayed Lists




## Test Case Design
### UseCaseCoverage
- __addList__
    - 輸入:
        - 新增一個List叫做test list，選擇顏色15
- __addTask__
    - 輸入:
        - 新增一個Task叫做Add Task，並且選擇status_options_cancelled ,
        - 並在location_editText輸入an address
- __deleteList__
    - 輸入:
        - 刪掉第一個List
- __deleteTask__
    - 輸入:
        - 載入addTask，新增一個task，並且刪掉
- __editList__
    - 輸入:
        - 編輯第1個List，更改名子為update list，並且選擇顏色12
- __editTask__
    - 輸入:
        - 載入addTask，更改名子為update task，並且更改status為status_options_in_process

### Input Space Patition
- __saveWithEarlyCompleteDate__
    - 輸入:
        - 1900/12/31 12:00 AM
    - 期待輸出:
        - 1900/12/31 12:00 AM
- __saveWithFutureCompleteDate__
    - 輸入:
        - 2100/12/31 12:00 AM
    - 期待輸出:
        - 2100/12/31 12:00 AM
- __saveWithSettingCompleteTime__
    - 輸入:
        - 分成三個 block 依序分為 Hour, Minute, Time slot, 接著使用 each choice 選擇當作輸入值
            - Hour: 12, 3, 6, 9
            - Minute: 00, 15, 30, 45
            - Time Slot: AM, PM
        - 根據上面我們選擇 12:00 AM, 3:15 PM, 6:30 AM, 9:45 PM
        - 因為我們寫 assertion 需要整個字串相同，所以設定年月日設定都是相同的
    - 期待輸出:
        - Mon, December 31, 2018, 12:00 AM
        - Mon, December 31, 2018, 3:15 PM
        - Mon, December 31, 2018, 6:30 AM
        - Mon, December 31, 2018, 9:45 PM
- __saveWithRandomTextDescription__
    - 輸入:
        - 參考[此網站](https://www.theverge.com/2018/2/15/17015654/apple-iphone-crash-ios-11-bug-imessage)，我們使用 RandomStringUtils 來產生亂數 Unicode 字碼
    - 期待輸出:
        - 輸出為亂數 Unicode
- __saveWithEarlyDueDate__
    - 輸入:
        - 1900/12/31 12:00 AM
    - 期待輸出:
        - 1900/12/31 12:00 AM
- __saveWithFutureDueDate__
    - 輸入:
        - 2100/12/31 12:00 AM
    - 期待輸出:
        - 2100/12/31 12:00 AM
- __saveWithFutureDueDateAndCheckTaskListView__
    - 輸入:
        - 2000/12/31
    - 期待輸出:
        - 此階段要期待各分頁都要出現 Sun, Dec 31, 2000
- __saveWithSettingDueTime__
    - 輸入:
        - 分成三個 block 依序分為 Hour, Minute, Time slot, 接著使用 each choice 選擇當作輸入值
            - Hour: 12, 3, 6, 9
            - Minute: 00, 15, 30, 45
            - Time Slot: AM, PM
        - 根據上面我們選擇 12:00 AM, 3:15 PM, 6:30 AM, 9:45 PM
        - 因為我們寫 assertion 需要整個字串相同，所以設定年月日設定都是相同的
    - 期待輸出:
        - Mon, December 31, 2018, 12:00 AM
        - Mon, December 31, 2018, 3:15 PM
        - Mon, December 31, 2018, 6:30 AM
        - Mon, December 31, 2018, 9:45 PM
- __editList_forNullName__
    - 輸入:
        - 給予 folder name 空值
    - 期待輸出:
        - 需要跳出一個提醒的 toast
- __editList_forRandomly__
    - 輸入:
        - 給予 folder name 隨機 Unicode
    - 期待輸出:
        - 可以正常的儲存，並不會 crash
- __editList_forSpace__
    - 輸入:
        - 給予 folder name 空白值
    - 期待輸出:
        - 需要跳出一個提醒的 toast

- __saveWithNumbericLocation__
    - 輸入:
        - 給予 location 一個負值
    - 期待輸出:
        - 可以正常儲存
- __saveWithRandomTextLocation__
    - 輸入:
        - 給予 location 一串隨機 Unicode
    - 期待輸出:
        - 需要跳出一個提醒的 toast
- __saveWithEarlyStartDate__
    - 輸入:
        - 1900/12/31 12:00 AM
    - 期待輸出:
        - 1900/12/31 12:00 AM
- __saveWithFutureStartDate__
    - 輸入:
        - 2100/12/31 12:00 AM
    - 期待輸出:
        - 2100/12/31 12:00 AM
- __saveWithThisYearStartDate__
    - 輸入:
        - 2019/06/20 12:00 AM
    - 期待輸出:
        - Thu, June 20, 2019, 12:00 AM
- __saveWithSettingStartTime__
    - 輸入:
        - 分成三個 block 依序分為 Hour, Minute, Time slot, 接著使用 each choice 選擇當作輸入值
            - Hour: 12, 3, 6, 9
            - Minute: 00, 15, 30, 45
            - Time Slot: AM, PM
        - 根據上面我們選擇 12:00 AM, 3:15 PM, 6:30 AM, 9:45 PM
        - 因為我們寫 assertion 需要整個字串相同，所以設定年月日設定都是相同的
    - 期待輸出:
        - Mon, December 31, 2018, 12:00 AM
        - Mon, December 31, 2018, 3:15 PM
        - Mon, December 31, 2018, 6:30 AM
        - Mon, December 31, 2018, 9:45 PM
- __saveWithNoTaskName__
    - 輸入:
        - 給予 task name 空值
    - 期待輸出:
        - 需要跳出一個提醒的 toast
- __saveWithNoTaskNameAfterSaveTask__
    - 輸入:
        - 先儲存一個有名字的 Task，之後再把 task name 清除
    - 期待輸出:
        - 需要跳出一個提醒的 toast
- __saveWithTaskName__
    - 輸入:
        - 給予 task name 名字
    - 期待輸出:
        - 可以正常被存取，並跳轉到 ViewTask 的頁面
- __saveWithBottomCountryInTimeZone__
    - 輸入:
        - 選擇最底部時區 (GMT+14:00) Line Islands Time
    - 期待輸出:
        - 存取後，再回來看時區應是一樣
- __saveWithTopCountryInTimeZone__
    - 輸入:
        - 選擇最上面時區 (GMT-11:00) Midway Island
    - 期待輸出:
        - 存取後，再回來看時區應是一樣
- __saveWithRandomTextURL__
    - 輸入:
        - +-*/asd/.554
    - 期待輸出:
        - 可以正常存取，且可以點擊URL
### CategoryEachChoiceCoverage
- __block1__
    - 輸入：
        - 選擇due date的日期為今天
        - 選擇start date的日期為上個月10號
        - 選擇priority為high
        - 選擇進度條的進度為80% (almost done)
    - 期待輸出：
        - 存取後，能夠在每個tab裡相對應的folder找得到該task
- __block2__
    - 輸入：
        - 選擇due date的日期為明天
        - 選擇start date的日期為今天
        - 選擇priority為medium
        - 選擇進度條的進度為50% (halfway there)
    - 期待輸出：
        - 存取後，能夠在每個tab裡相對應的folder找得到該task
- __block3__
    - 輸入：
        - 選擇due date的日期為後天(Next days)
        - 選擇start date的日期為明天
        - 選擇priority為low
        - 選擇進度條的進度為40% (way to go)
    - 期待輸出：
        - 存取後，能夠在每個tab裡相對應的folder找得到該task
- __block4__
    - 輸入：
        - 選擇due date的日期為下個月20號(Someday)
        - 選擇start date的日期為後天(Someday)
        - 選擇priority為none
        - 選擇進度條的進度為0% (nothing accomplished)
    - 期待輸出：
        - 存取後，能夠在每個tab裡相對應的folder找得到該task
- __block5__
    - 輸入：
        - 選擇start date的日期為下個月20號(Later)
        - 選擇進度條的進度為100% (done)
    - 期待輸出：
        - 存取後，能夠在每個tab裡相對應的folder找得到該task
- __block6__
    - 輸入：
        
    - 期待輸出：
        
- __somedayStartCategory__
    - 輸入：
        - 選擇start date的日期為後天(Someday)
    - 期待輸出：
        - 存取後，能夠在start tab的Someday folder裡找得到該task
- __tomorrowStartCategory__
    - 輸入：
        - 選擇start date的日期為明天
    - 期待輸出：
        - 存取後，能夠在start tab的Tomorrow folder裡找得到該task

### EditTaskEachChoiceCoverage
- __editTaskEachChoiceSelection1__
    - 輸入
        - Folder下拉式選單選擇 My tasks
        - Status下拉式選單選擇 needs action
        - Start date選擇今天的日期，然後在timezone下拉式選單選擇最上面時區 (GMT-11:00) Midway Island
        - Priority下拉式選單選擇 none
        - Privacy下拉式選單選擇 not specified
    - 期待輸出：
        - 存取後，在view task頁面找得到 My tasks 和 needs action
- __editTaskEachChoiceSelection2__
    - 輸入
        - Folder下拉式選單選擇 New List(預先新增一個New List)
        - Status下拉式選單選擇 in process
        - Start date選擇今天的日期，然後在timezone下拉式選單選擇時區 (GMT+00:00) GMT
        - Priority下拉式選單選擇 low
        - Privacy下拉式選單選擇 public
    - 期待輸出：
        - 存取後，在view task頁面找得到 New List，in process，low 和 public
- __editTaskEachChoiceSelection3__
    - 輸入
        - Status下拉式選單選擇 done
        - Start date選擇今天的日期，然後在timezone下拉式選單選擇最下面時區 (GMT+14:00) Line Islands Time
        - Priority下拉式選單選擇 medium
        - Privacy下拉式選單選擇 private
    - 期待輸出：
        - 存取後，在view task頁面找得到 done，medium 和 private
- __editTaskEachChoiceSelection4__
    - 輸入
        - Status下拉式選單選擇 cancelled
        - Priority下拉式選單選擇 high
        - Privacy下拉式選單選擇 confidential
    - 期待輸出：
        - 存取後，在view task頁面找得到 cancelled，high 和 confidential

## Bug List
1. viewTask - >task->item 全打勾 到100% 在全砍掉item%數會錯誤
    砍掉item 清空時 newValue is empty()成立 所以不會改變%數
    ![](https://i.imgur.com/82msFXO.png)

3. 開始時間日期不能超過 2040 超過會回到1970
4. 新增 task 時 title 可以空的，但是編輯時卻可以讓title是null.
5. EditTask ->CheckList 中間刪掉案返回 會crash   
    
    - 錯誤原因：發生這個錯誤主要是ListView控件或者其它的ViewGroup等容器控件因為滑動而暫時移除子視圖，但卻沒有移除該子查看上面的焦點對焦，所以在ListView的滑動返回到原來的位置的時候沒有恢復成原來的景觀，導致了該異常的產生;
    - ![](https://i.imgur.com/X0eIF7i.png)
    


