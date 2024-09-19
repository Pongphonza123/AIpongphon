package pongphol.android.ai

import android.os.Bundle
import android.os.StrictMode
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val sendBtn = findViewById<Button>(R.id.sendBtn)

        val editText_gender = findViewById<EditText>(R.id.editText_gender)
        val edittext_Hemoglobin = findViewById<EditText>(R.id.edittext_Hemoglobin)
        val edittext_MCH = findViewById<EditText>(R.id.edittext_MCH)
        val edittext_MCHC = findViewById<EditText>(R.id.edittext_MCHC)
        val edittext_MCV = findViewById<EditText>(R.id.edittext_MCV)

        val resultTextview = findViewById<TextView>(R.id.resultTextview)

        sendBtn.setOnClickListener {
            if (editText_gender.text.toString().isEmpty() || edittext_Hemoglobin.text.toString().isEmpty()
                || edittext_MCH.text.toString().isEmpty() || edittext_MCHC.text.toString().isEmpty() ||
                edittext_MCV.text.toString().isEmpty()) {
                Toast.makeText(this, "กรุณากรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val url = getString(R.string.url)

            val okHttpClient = OkHttpClient()
            val fromBody : RequestBody = FormBody.Builder()
                .add("gender", editText_gender.text.toString())
                .add("emoglobin", edittext_Hemoglobin.text.toString())
                .add("mch", edittext_MCH.text.toString())
                .add("mchc", edittext_MCHC.text.toString())
                .add("mcv", edittext_MCV.text.toString())
                .build()
            val request : Request = Request.Builder()
                .url(url)
                .post(fromBody)
                .build()
            val response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                val obj = JSONObject(response.body!!.string())
                resultTextview.text = obj["result"].toString()
            }
        }

    }
}