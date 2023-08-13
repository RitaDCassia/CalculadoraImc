package co.ritinha.calculadoraimc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var btnComeca: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnComeca = findViewById(R.id.btn_comeca)
        btnComeca.setOnClickListener {
            val intent = Intent(this, CalculateImcActivity::class.java)
            startActivity(intent)
        }
    }
}