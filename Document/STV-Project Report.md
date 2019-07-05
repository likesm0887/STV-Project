# STV-Project Report

## Test Criteria
### Code Statement Coverage:
- #### Suspension criteria: 
    當達到**40%** 的test cases不通過時，所有測試人員暫停測試，並且集中處理所有沒通過的test cases，確認是App的問題(確認是App錯誤的話就回報Bug)還是test case 撰寫問題。

- #### Resumption criteria: 
    在解決不通過的test cases後，所有測試人員繼續進行接下來的測試。

- #### Approval criteria: 
    所有test cases 必須通過(確認行為無誤)，並且達到程式覆蓋(Code coverage)   <font color=red>**70%**。</font>

## Approach
- 我們在設計測試案例時，主要分為四個大方向:
    - 使用UseCase Diagram
        - 在了解App之後，我們畫出UseCase Diagram，設計測試案例就會以涵蓋UseCase Diagram 為主
    - 使用EdgeCoverage 
        - 我們在參考ACE爬出STD圖，在自行加工成易讀的狀態圖，在此方法我們使用EdgeCoverage，在所有的測試案例將會涵蓋圖中所有的Edge。
    - 使用ISP
        - 在代測程式中，有很多輸入的欄位，如果只有依照前兩個的作法，是無法完整測試程式的行為，所以我們在這個方法中，會依照每一個輸入欄位來輸入vaild值和invaild值和輸入一些極值，來提高程式覆蓋率。 
    - Anomaly測試
### UseCase Diagram
-   在UseCase Diagram中我們選擇不測的有**通知設定**、**分享**、**顯示提醒**，
    ##### 通知設定: 
    -    系統事件是我們在Appium中難以模擬的事件，不容易測試，時間關係所以放棄測試此功能。
    ##### 分享:
    -    分享功能因為會跨到很多App以外，所以我們難以測試他到底有沒有成功傳送到其他app，但是測其他app也不會提高太多的覆蓋率，所以我們選擇放棄
    ##### 顯示提醒:
    -    顯示提醒他需要設定提醒時間，才能觸發此功能，但是腳本中如果等待時間直到出發功能會讓測試的速度大幅降低，所以我們選擇放棄。
- 測試腳本
    - ![](https://i.imgur.com/XKcKWzZ.png =400x300)
- UseCase Diagram
    - ![](https://i.imgur.com/oNDMxZS.png =400x400)
#### EditTask  – EachChoice Coverage 
- 在UseCase Diagram中的EditTask，我們採用EachChoice的方式來設計測試案例，因為如果測試案例要涵蓋到所有下拉式選單的選項，會造成測試案例爆炸，所以我們採用EachChioce的方式來縮減測試案例，以下是我們所歸納出來的特性:

- ![](https://i.imgur.com/6GtZyfy.png =600x200)
- 範例圖:
    - ![](https://i.imgur.com/67KB3Zc.png =200x200)
    - ![](https://i.imgur.com/dAC3Njv.png =200x200)
    - ![](https://i.imgur.com/ew3DnZd.png =200x200)
    - ![](https://i.imgur.com/II9Ju4q.png =200x200)
- 測試腳本
- ![](https://i.imgur.com/wyPLhOD.png =500x200)



#### Category  – EachChoice Coverage
- 在UseCase Diagram中的Category，我們採用EachChioce，原因同上，以下是我們所歸納出來的特性:。
- ![](https://i.imgur.com/cPK5rCT.png =400x200)
-  範例圖:

- ![](https://i.imgur.com/ecsQbbm.png =600x400)


- 測試腳本
    ![](https://i.imgur.com/BdpPaQg.png =400x300)


### EdgeCoverage
- 我們使用以下的圖來撰寫EdgeCoverage，總共產生19個測試案例
- 
todo補上STD 連結


- 測試腳本:
    ![](https://i.imgur.com/N2HGMuT.png =400x400)

### ISP
- 針對輸入欄位和數值邊界來做測試
    - 輸入欄位中分為3值種情況
    - C1: Null string
    - C2: Special character: Random Pick unicode  
    - C3: Valid string
- 在數值邊界中挑選最小與最大值與中間值來做測試(舉例:挑選年分)
    - C1: Select 1900 year
    - C2: Select 2019 year
    - C3: Select 2100 year
- 測試腳本
    - ![](https://i.imgur.com/uzrysJW.png)


- 範例圖:
    - 輸入欄位範例:
    - ![](https://i.imgur.com/5zBGB0c.png =250x50)
    - ![](https://i.imgur.com/INEc9u9.png =250x50)
- 數值邊界欄位:
    - ![](https://i.imgur.com/xpIq191.png)

### Anomaly
- 我們針對Anomaly的情況也做了測試，我們的測試方法分為4種
    - 1. Rotation
        - 在執行一行腳本的指令後，將螢幕翻轉90度在翻轉回來，腳本是否能繼續下去
    - 2. Reenter
        - 在執行一行腳本的指令後，離開App回到桌面，再回去App，查看腳本是否能繼續下去
    - 3. Random Input
        - 我們使用亂數Unicode的方式，來產生亂碼，因為我們發現隨機字串造成應用程式崩潰的機率較少。
        - 範例圖:
            - ![](https://i.imgur.com/JH9lShH.png =200x400)
    - 4. Context:Wifi、Bluetooth、PhoneCall…
        不測 :時間不夠再加上此App 沒有用到，所以選擇不測
## Test Results Analyze
 - 我們將每個測試方法分別計算程式涵蓋率，以下是結果
 - Sorted by Statement Coverage: 
    - ![](https://i.imgur.com/KWguofi.png =500x250)
 - Sorted by Branch Coverage: 
    - ![](https://i.imgur.com/LKaZWQK.png =500x250)
 - 我們發現大部分的Statement 與Branch 為正比，不一樣的是CategoryEachChoiceCoverage與EdgeCoverage，在兩個表格中互換位子，我們猜測可能是EdgeCoverage比較少處理邏輯部分，相對的CategoryEachChoiceCoverage，因為要判斷時間的關係，會涵蓋到比較多判斷條件，因此Branch 比較高，而且CategoryEachChoiceCoverage的測試案例幾乎只有在一個畫面中，EdgeCoveage則是走訪過整個APP，但是Branch還是較低。

 
## Pass/Fail 
### Normal
- 我們在沒有開啟Anomaly選項時總共通過了56的測試案例11個失敗
    - ![](https://i.imgur.com/dndJ29b.png)
    - ![](https://i.imgur.com/z9T9W2T.png)
- 我們分析了沒有通過的測試案例整理出以下表格:
    - ![](https://i.imgur.com/c5fWW5h.png =500x300)
#### Crash and Bug
##### Select Year : 
###### 敘述:
 - 1. 設定時間為1900  or 2040 up 
 - 2. 選擇完後畫面出現為1970
    - ![](https://i.imgur.com/WO7dk4N.gif =200x400)
##### Edit Task Title Null   : 
###### 敘述:
 - 在新增Task時，name為 null 會無法新增，但是在編輯畫面時就可以輸入Null
    - ![](https://i.imgur.com/9Ch8lZ4.gif =200x400)
##### ViewTaskCompletePercent  : 
###### 敘述:
 - ViewTaskCompletePercent: 
 - CheckList 全部打勾後在刪掉 Item 會發現完成度還是維持100%
    - ![](https://i.imgur.com/POKn8Mb.gif =200x400)

##### TimeZone   : 
###### 敘述:
- 在完成時間設定為 12:00AM ， 在查看Task時，會被改成8:00AM
    - ![](https://i.imgur.com/i9nRWy7.gif =200x400)

##### EditTasks-CheckList:  : 
###### 敘述:

- 新增item在CheckList裡，再把中間的刪掉，再按next就會Crash
- 錯誤原因：發生這個錯誤主要是ViewGroup等容器移除底下的物件，但卻沒有移除該子查看上面的焦點對焦，導致了該異常的產生
- 我們將這個會造成Crash的Bug也放在此OpenSource的[issues](https://github.com/dmfs/opentasks/issues/804)中，讓作者可以改善

    - ![](https://i.imgur.com/UGdm1o7.gif =200x400)
- error stack:
    - ![](https://i.imgur.com/bXMfx2m.png )
### Anomaly
- 開啟Anomaly 事件偵測，通過了24 個測試案例，失敗了43個，經過了人工篩選後，共篩選出3種Anomaly情況
#### Crash and Anomaly
##### Rotation Back To Top
###### 敘述:
- 旋轉後畫面回到最上方
    - ![](https://i.imgur.com/cdZaJki.gif =300x400)
##### Rotate Calendar Disappear 
###### 敘述:
- 旋轉後選擇日期視窗消失
    - ![](https://i.imgur.com/iBFHLGt.gif =300x400)
##### Rotate PopUp Dialog Disappear
###### 敘述:
- 旋轉之後PopUp視窗消失
    - ![](https://i.imgur.com/BdlAE34.gif =300x400)
##### Rotate Status Dropdown Disappear
###### 敘述:
- 旋轉後下拉式選單消失
    - ![](https://i.imgur.com/VRxc7SV.gif =300x400)
## Difficulties
- 滑動問題 : 某些元件滑動需要速度感才能觸發，但是在appium很難達成
- 系統通知 : Appium無法觸發app的系統事件
- 等待問題 : 在切換app畫面時，Xml 已改變，但是畫面卻還在正在動畫中，導致元件無法觸發，或是元件已經找到，但卻是上一個畫面的元件
- 權限 :在Android 6 版本中有看到要求permission的dialog，但是在android 8 之後就沒有看見。
- 設定 : 同上
- 時間問題 (跨日 ):在app中 某些時段(快要跨日)設定完， 就會容易出現腳本跑不過，或是在assert 時難以確認當下時間。
- 螢幕比例:每個螢幕手機大小不同，導致畫面上出現的東西不一樣，有些需要滑動之後才看的到 (ex : EditTasksActivity)

## Artifacts
- 文件:TCS、STP、SDT、PPT 
- Diagram: UseCase 、StateGraph、ClassDiagram
- TestData
- 框架Source Code & 框架使用說明 
- 腳本
- CI Report
- 影片 : How to wirte and Run a Script , RunTotalScript ,RunTotalScriptWhitAnomalyOperation 
