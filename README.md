# Test_20200319_intent05 and Test_20200319_intent06
Intent filter practice
 參考 from Android website:

 https://developer.android.com/training/sharing/send 
 
 Sending simple data to other apps

 ....

 When you construct an intent, you must specify the action you want the intent to perform. Android uses the action ACTION_SEND to send data from one activity  to another, even across process boundaries. You need to specify the data and its type. The system automatically identifies the compatible activities that can  receive the data and displays them to the user. In the case of the intent resolver, if only one activity can handle the intent, that activity immediately  starts.
 
 ...

 Using the Android Sharesheet

 For all types of sharing, create an intent and set its action to Intent.ACTION_SEND. In order to display the Android Sharesheet you need to call  Intent.createChooser() , passing it your Intent object. It returns a version of your intent that will always display the Android Sharesheet.

 Sending text content

 The most straightforward and common use of the Android Sharesheet is to send text content from one activity to another. For example, most  browsers can share the URL of the currently-displayed page as text with another app. This is useful for sharing an article or website with  friends via email or social networking. Here's an example of how to do this:

 Intent sendIntent = new Intent();
 sendIntent.setAction(Intent.ACTION_SEND);
 sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
 sendIntent.setType("text/plain");

 Intent shareIntent = Intent.createChooser(sendIntent, null);
 startActivity(shareIntent);


-----------------------------------------------------------------------------------
 setType()補充 from Android website:

 https://developer.android.com/guide/components/intents-filters

 ...

 資料

 URI (Uri 物件) 可參考據以執行的資料和/或該資料的 MIME 類型。 提供的資料類型通常是由意圖的動作控制。例如，如果動作是 ACTION_EDIT，資料就應包含欲編輯文件的 URI。
 建立意圖時，除了意圖的 URI 以外，請務必指定資料類型 (其 MIME 類型)。 例如，能夠顯示影像的 Activity 可能無法播放音訊檔案，即使有類似的 URI 格式。 
 因此，指定資料的 MIME 格式可協助 Android 系統找出最適合接收意圖的元件。 不過 — 尤其是當資料指出資料位在裝置何處且受 ContentProvider 控制讓系統看見資料 MIME  類型的 content: URI 時，有時能夠從 URI 推論出 MIME 類型。

 如果您只想設定資料 URI，請呼叫 setData()。 如要設定 MIME 類型，請呼叫 setType()。您還可以視需要利用 setDataAndType() 明確設定兩者。

 注意：如果您想同時設定 URI 與 MIME 類型，「請勿」呼叫 setData() 和 setType()，原因是這兩者會抵銷彼此的值。
 請務必使用 setDataAndType() 來設定 URI 與 MIME 類型。
-----------------------------------------------------------------------------------
 https://developer.android.com/reference/android/content/Intent
 ...

 setAction(String action)
 Set the general action to be performed.

 https://developer.android.com/guide/components/intents-filters
 ...
 
 您可以透過 setAction() 或 Intent 建構函式來指定意圖的動作。

-----------------------------------------------------------------------------------

 intent05 project的 MainActivity 要呼叫啟動並傳送資料給 intent06 project 的  Myintent Activity 及    ShareData Activity，所以需在intent06 的   AndroidManifest.xml中，在其元素中加入 Intent Filter的元素。

 intent Filter是用來篩選Intent的條件，用來判斷哪個元件是負責特定 Intent，可設定在元件中，代表什麼樣的
 Intent是元件所負責的。

 每個 intent-filter中至少要定義一個 <action> 元素: android.name。
  example: <action android:name="MyIntent" />
	
-----------------------------------------------------------------------------------
	
 因為在intent05 project的 MainActivity 有設定一個intent的action為MyIntent，
 所以MyintentActivity 所對應的的action name設定為 MyIntent。
 沒有傳送資料，所以沒有設定data。 
 category的設定是告訴Android這個activity是可以被獨立啟動的，所以設定 android.intetn.category.DEFAULT

 因為在intent05 project的 MainActivity 有設定一個 intent的 action為 intent.action.SEND，
 所以ShareActivity 所對應的的action設定為 android.intent.action.SEND。
 因為有傳送文字資料所以要設定data ， 設定其 mimeType="text/plain"
 category的設定是告訴Android這個activity是可以被獨立啟動的，所以設定 android.intetn.category.DEFAULT

 
    <activity android:name=".MyintentActivity">
        <intent-filter>
            <action android:name="MyIntent" />
            <category android:name="android.intent.category.DEFAULT" />
        </intent-filter>
    </activity>


     <activity android:name=".ShareActivity">
          <intent-filter>
              <action android:name="android.intent.action.SEND" />
              <data android:mimeType="text/plain" />
              <category android:name="android.intent.category.DEFAULT" />
           </intent-filter>
     </activity>

-----------------------------------------------------------------------------------

 intent05 package 的 MainActivity 設定:

--建立editTextShareData按鈕事件。
--建立buttonSend按鈕事件及監聽器方法，並直接以匿名類別的建立方式建立 setOnClickListener(new View.OnClickListener() 。
--@Override public void onClick(View v)方法。
  --建立 String ShareData物件並取得使用者輸入的資料轉成字串。
  --建立 String action 物件並存放命令參數 Intetn.ACTION_SEND。 
  --建立 Intent 的物件，並以 Intent() 建構子的方式把 action 放進去: Intent intent = new Intent(action)。
  --用 putExtra() 方法將資料放入Intent物件，key為 Intent.EXTRA_TEXT， value為ShareData: intent.putExtra(Intent.EXTRA_TEXT, shareData);
  --須設定data的mimeType，用 setType() 方法設定: intent.setType("text/plain")。
  --啟動intent: startActivity(intent)。

	editTextShareData = (EditText) findViewById(R.id.editText_sharedata);

        buttonSend = (Button) findViewById(R.id.button_sendto);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String shareData = editTextShareData.getText().toString(); // 取得使用者輸入的資料轉成字串

                String action = Intent.ACTION_SEND;

                Intent intent = new Intent(action);

                intent.putExtra(Intent.EXTRA_TEXT, shareData);

                intent.setType("text/plain"); // 符合剛剛的 manifests.xml的設定

                startActivity(intent);

                // startActivity(Intent.createChooser(intent, "Share Data")); // 可以建立選擇器 老師說還是用傳統的就好 bug較少

            }
        });


--建立editTextMyIntent EditText事件。
--建立buttonMyIntent按鈕事件及監聽器方法，並直接以匿名類別的建立方式建立 setOnClickListener(new View.OnClickListener() 。
--@Override public void onClick(View v)方法。
  --建立 String myData物件並取得使用者輸入的資料轉成字串。
  --建立 Intent 的物件: Intent intent = new Intent();
  --用 setAction() 方法，參數為"MyIntent"，設定 action name。
  --用 putExtra() 方法將資料放入Intent物件，key為 "data"， value為myData: intent.putExtra("data", myData)。
  --為了控制 MyIntent activity 所顯示的圖片，所以用 putExtra() 方法將資料放入Intent物件，key為 "number"，value為 1: intent.putExtra("number", 1)。
  --啟動intent: startActivity(intent)。
 

                editTextMyIntent = (EditText) findViewById(R.id.editText_mydata);

                buttonMyIntent = (Button) findViewById(R.id.button_myintent);

                buttonMyIntent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String myData = editTextMyIntent.getText().toString(); // 取得使用者輸入的資料轉成字串

                        Intent intent = new Intent();

                        intent.setAction("MyIntent");

                        intent.putExtra("data", myData);

                        intent.putExtra("number", 1); // 設定索引值為1的那一張圖

                        startActivity(intent);

                    }
                });

-----------------------------------------------------------------------------------

 intent06 package 的 ShareActivity 設定:

--設定 Activity 的標題: setTitle("Share Data")。
--建立 textViewData TextView事件: textViewData = (TextView) findViewById(R.id.textView_sharedata);
--清空字串: textViewData = (TextView) findViewById(R.id.textView_sharedata)。
--宣告此class的屬性: Intent intent。
--接收Intent: intent = getIntent()。
--用 intent.getStringExtra() 方法取得傳來的資料: String data = intent.getStringExtra(Intent.EXTRA_TEXT)。
--於 textViewData 物件上顯示傳來的資料: textViewData.setText(data)。
 

        setTitle("Share Data");

        textViewData = (TextView) findViewById(R.id.textView_sharedata);
        textViewData.setText("");

        intent = getIntent();

        String data = intent.getStringExtra(Intent.EXTRA_TEXT);

        textViewData.setText(data);
-----------------------------------------------------------------------------------

 intent06 package 的 MyintentActivity 設定:

--設定 Activity 的標題: setTitle("My intent")。
--建立 textViewData TextView事件: textViewData = (TextView) findViewById(R.id.textView_myIntent)。
--清空字串: textViewData.setText("");
--建立 imageViewPic ImageView事件。
--宣告此class的屬性: Intent intent。
--接收Intent: intent = getIntent()。
--用 intent.getStringExtra() 方法取得傳來的資料，對應的key為data: String data = intent.getStringExtra("data")。
--於 textViewData 物件上顯示傳來的資料: textViewData.setText(data)。
--建立 int 變數接收傳來的int型態資料: int number = intent.getIntExtra("number", 0);
--宣告一個屬性 int[] array 用來利用索引值去改變imageView物件所顯示的圖: private int[] picArray = {R.drawable.img_1, R.drawable.img_2};
--用 imageView 物件取得 R資源中圖的方法，參數為該array索引值的內容，去改變顯示的圖:  imageViewPic.setImageResource(picArray[number]);

        setTitle("My intent");

        textViewData = (TextView) findViewById(R.id.textView_myIntent);
        textViewData.setText("");

        imageViewPic = (ImageView) findViewById(R.id.imageView_id);

        intent = getIntent();

        String data = intent.getStringExtra("data");

        textViewData.setText(data);

        int number = intent.getIntExtra("number", 0);

        imageViewPic.setImageResource(picArray[number]);
-----------------------------------------------------------------------------------



