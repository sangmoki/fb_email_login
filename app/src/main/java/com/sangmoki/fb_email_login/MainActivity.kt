package com.sangmoki.fb_email_login

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase 사용자 현재 로그인 확인
        auth = FirebaseAuth.getInstance()

        // 회원가입 버튼
        val joinBtn = findViewById<Button>(R.id.joinBtn)
        // 로그인 버튼
        val loginBtn = findViewById<Button>(R.id.loginBtn)
        // 로그아웃 버튼        
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        // 이메일과 패스워드 정보
        val email = findViewById<EditText>(R.id.emailArea)
        val password = findViewById<EditText>(R.id.passwordArea)
        
        // 회원가입 버튼 클릭 이벤트        
        joinBtn.setOnClickListener {

            // 인증객체에 email과 password를 넘겨 신규 계정을 생성한다.
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->

                    // 성공했을 경우 회원가입
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "회원가입이 완료되었습니다.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    // 실패했을 경우 실패 메시지 출력
                    } else {
                        Toast.makeText(
                            baseContext,
                            "회원가입에 실패하였습니다.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
        
        loginBtn.setOnClickListener {
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext,
                            "로그인 성공",
                            Toast.LENGTH_SHORT,
                        ).show()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "로그인 실패",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }

        // 로그아웃 버튼 클릭 시 발생 이벤트
        logoutBtn.setOnClickListener {
            auth.signOut()
            Toast.makeText(
                baseContext,
                "로그아웃 완료",
                Toast.LENGTH_SHORT,
            ).show()
        }


    }
}