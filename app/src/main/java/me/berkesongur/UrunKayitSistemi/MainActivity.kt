package me.berkesongur.UrunKayitSistemi

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import me.berkesongur.UrunKayitSistemi.adapters.ProductListAdapter
import me.berkesongur.UrunKayitSistemi.models.ProductListItem

class MainActivity : AppCompatActivity() {

  lateinit var db: FirebaseFirestore
  private lateinit var productListAdapter: ProductListAdapter

  override fun onCreate(state: Bundle?) {
    super.onCreate(state)
    setContentView(R.layout.activity_main)

    FirebaseApp.initializeApp(this);

    db = FirebaseFirestore.getInstance()

    val productList: RecyclerView = findViewById(R.id.productListRV)
    productList.layoutManager = GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false)

    productListAdapter = ProductListAdapter(this)
    productList.adapter = productListAdapter

    val loginBtn = findViewById<Button>(R.id.btn_login)

    loginBtn.setOnClickListener {
      startActivity(Intent(this, LoginActivity::class.java))
      finish()
    }

    findViewById<ImageButton>(R.id.btn_product_sort).setOnClickListener {
      productListAdapter.sort(0, productListAdapter.productListItems.size - 1)
      productListAdapter.submitList(productListAdapter.productListItems)
      productListAdapter.notifyDataSetChanged()
    }

    findViewById<ImageButton>(R.id.btn_product_sort_reverse).setOnClickListener {
      productListAdapter.sort(0, productListAdapter.productListItems.size - 1, true)
      productListAdapter.submitList(productListAdapter.productListItems)
      productListAdapter.notifyDataSetChanged()
    }

    findViewById<ImageButton>(R.id.btn_reload_main).setOnClickListener {
      setProductsList()
      recreate()
    }

    findViewById<ImageButton>(R.id.btn_product_create).setOnClickListener {
      if(Firebase.auth.currentUser != null) {
        startActivity(Intent(this, ProductCreateActivity::class.java))
        finish()
      } else {
        Toast.makeText(this, "Ürün kayıt etmek için giriş yapmalısın", Toast.LENGTH_SHORT).show()
      }
    }

    setProductsList()

    Firebase.auth.currentUser?.let {
      val logoutBtn = findViewById<Button>(R.id.btn_logout)
      val productCreateBtn = findViewById<ImageButton>(R.id.btn_product_create)

      findViewById<TextView>(R.id.user_profile_name).text = it.email

      loginBtn.visibility = View.GONE
      logoutBtn.visibility = View.VISIBLE
      productCreateBtn.visibility = View.VISIBLE

      logoutBtn.setOnClickListener {
        Firebase.auth.signOut()
        recreate()
      }
    }

  }

  override fun onResume() {
    super.onResume()
    setProductsList()
  }

  private fun setProductsList() {
    db.collection("products").get()
      .addOnSuccessListener {
        productListAdapter.setProductListItems(it.toObjects(ProductListItem::class.java))
      }
      .addOnFailureListener {
        Toast.makeText(this, "Error getting data", Toast.LENGTH_LONG).show();
      }
  }

}
