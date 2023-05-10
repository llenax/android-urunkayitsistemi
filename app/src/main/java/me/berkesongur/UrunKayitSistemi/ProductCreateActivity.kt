package me.berkesongur.UrunKayitSistemi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class ProductCreateActivity : AppCompatActivity() {
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_create)

        val db = FirebaseFirestore.getInstance()

        findViewById<Button>(R.id.btn_go_back_main).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        findViewById<Button>(R.id.btn_create_product).setOnClickListener {
            val productName = findViewById<EditText>(R.id.product_name)
            val productPrice = findViewById<EditText>(R.id.product_price)
            val productImageURL = findViewById<EditText>(R.id.product_image_url)

            Firebase.auth.currentUser.let{
                db.collection("products").add(hashMapOf(
                    "productName" to productName.text.toString(),
                    "productPrice" to productPrice.text.toString().toInt(),
                    "productImageURL" to productImageURL.text.toString()
                ))
                    .addOnSuccessListener {
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Ürün kayıt edilemedi", Toast.LENGTH_SHORT).show()
                    }
            }

        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(this.currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


}