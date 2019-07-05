Crawler - APP Code Coverage
===
###### tags: `Crawler`

Instrument 方法
---
Step
1. 使用AndroidStudio開啟APP專案
2. 於"src"資料夾下新增"androidTest"資料夾
3. 找出MainActivity所在的位置，依據他的資料夾路徑在androidTest下建立出一模一樣的路徑/[xxxTest].java
4. 在step3所新增之Test中寫入
```java
    @RunWith(AndroidJUnit4.class)
    public class [xxxTest] {

        private ActivityTestRule<MainActivity> activityTestRule;
        private boolean testStop = false;

        @Test
        public void testInitPrint() throws InterruptedException {
            activityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);
            activityTestRule.launchActivity(null);
            IntentFilter intentFilter = new IntentFilter("test");
            BroadcastReceiver brocastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    testStop = true;
                }
            };
            Activity activity = activityTestRule.getActivity();
            activity.registerReceiver(brocastReceiver, intentFilter);
            while (!activityTestRule.getActivity().isFinishing() && !testStop) {
            }
            activity.unregisterReceiver(brocastReceiver);
        }
    }
```
5. 在 [APKName]/app/build.gradle
    a. 最上面加入 `apply plugin: 'jacoco'` 
    b. 在androd>defaultConfig中加入
    `testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"`
    c. 在androd>buildTypes中加入 `debug { testCoverageEnabled = true }`
    d. 於 `android{}` 後加入下面程式碼
    ```
    jacoco{
        toolVersion = "0.7.5+"
    }

    def coverageSourceDirs = [
            '../app/src/main/java'
    ]

    task jacocoTestReport(type: JacocoReport) {
        group = "Reporting"
        description = "Generate Jacoco coverage reports after running tests."
        reports {
            xml.enabled = true
            html.enabled = true
        }
        classDirectories = fileTree(
                dir: './build/intermediates/classes/debug',
                excludes: ['**/R*.class',
                           '**/*$InjectAdapter.class',
                           '**/*$ModuleAdapter.class',
                           '**/*$ViewInjector*.class'
                ]
        )

        sourceDirectories = files(coverageSourceDirs)
        additionalSourceDirs = files(coverageSourceDirs)
        executionData = fileTree(
                dir: "$buildDir/outputs/code-coverage/connected"
        )

    }
    ```
    e. 在dependencies下加
    ```
    androidTestCompile 'com.android.support.test:rules:0.5'
    androidTestCompile('com.android.support.test:runner:0.5') {
        // Necessary if your app targets Marshmallow (since the test runner
        // hasn't moved to Marshmallow yet)
        exclude group: 'com.android.support', module: 'support-annotations'
    }
    ```
6. 執行[xxxTest]
7. 從手機結束app，測試就結束

Instrument Test 作法
---
1. 使用無限迴圈使Test可以持續執行
2. 終止條件為APP Finishing(Ex.用"Back Key"關閉APP)或是對手機Broadcast "Any Content"

手動取得 Coverage
---
**以下指令使用命令提示字元(不要用powershell)**
1. 以adb指令啟動Instrumented App 
    `adb shell am instrument -w -e coverage true     [PackageName].test/android.support.test.runner.AndroidJUnitRunner`
2. 手動操作應用程式，關閉App即終止測試
3. 以adb指令取得 coverage.ec 檔案
`adb exec-out run-as [PackageName] cat /data/data/[PackageName]/files/coverage.ec > ./coverage.ec`
3. 將coverage.ec放到應用程式原始碼專案目錄下 `/app/build/outputs/code-coverage/connected` 下 (需自行新建資料夾)
4. 於應用程式原始碼專案目錄下執行cmd `gradle jacocoTestReport`，將 coverage.ec 轉成html報表 (報表路徑 /app/build/reports/jacoco/jacocoTestReport)
    > 遇到`Unable to read execution data file...`問題時，檢查/app/build.gradle中`jacoco{ toolVersion = "0.7.5+" }`
    > 第一次執行jacocoTestReport時間可能會較長，需要NDK
    > 如果遇到打開報表都是空的代表那個專案沒有run 過 instructment
