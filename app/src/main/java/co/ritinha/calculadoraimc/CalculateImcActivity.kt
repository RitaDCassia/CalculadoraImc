package co.ritinha.calculadoraimc

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

class CalculateImcActivity : AppCompatActivity() {

    private lateinit var editImcWeight: EditText
    private lateinit var editImcHeight: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_imc)

        editImcWeight = findViewById(R.id.edit_imc_weight)
        editImcHeight = findViewById(R.id.edit_imc_height)

        val btnSend: Button = findViewById(R.id.btn_imc_send)
        btnSend.setOnClickListener {
            // se for diferente de true mostra um Toast e mata a função
            if(!validate()){
                Toast.makeText(this, R.string.fields_messages, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // converter para int
            val weight = editImcWeight.text.toString().toInt()
            val height = editImcHeight.text.toString().toInt()

            // calcula imc
            val result = calculateImc(weight, height)
            // guarda o reaultado em uma string
            val imcResponseId = imcResponse(result)

            // dialog com informações do imc
            AlertDialog.Builder(this)
                // titulo
                .setTitle(getString(R.string.imc_response, result))
                // mensagem
                .setMessage(imcResponseId)
                // btn
                .setPositiveButton(android.R.string.ok
                ) { dialog, which -> dialog.dismiss() }
                .create()
                .show()

            // fechar o teclado quando mostrar o dialog
            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

    // converte numeros para texto
    @StringRes
    private fun imcResponse(imc: Double): Int{
        return when {
            imc < 15.0 -> R.string.imc_severely_low_weight
            imc < 16.0 -> R.string.imc_very_low_weight
            imc < 18.5 -> R.string.imc_low_weight
            imc < 25.0 -> R.string.normal
            imc < 30.0 -> R.string.imc_high_weight
            imc < 35.0 -> R.string.imc_so_high_weight
            imc < 40.0 -> R.string.imc_severely_high_weight
            else -> R.string.imc_extreme_weight
        }
    }

    // calculo do imc
    private fun calculateImc(weight: Int, height: Int): Double{
        // peso / (altura * altura)
        return weight / ((height /100.0) * (height / 100.0))
    }

    private fun validate(): Boolean{
        // verifica se não é vazio e também se não é zero
        return (editImcWeight.text.toString().isNotEmpty()
                && editImcHeight.text.toString().isNotEmpty()
                && !editImcWeight.text.toString().startsWith("0")
                && !editImcHeight.text.toString().startsWith("0"))
    }
}