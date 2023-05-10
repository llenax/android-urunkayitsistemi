package me.berkesongur.UrunKayitSistemi.holders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.berkesongur.UrunKayitSistemi.R
import me.berkesongur.UrunKayitSistemi.models.ProductListItem

class ProductListHolder(private val v: View) : RecyclerView.ViewHolder(v) {
    fun bind(productListItem: ProductListItem) {
        v.findViewById<TextView>(R.id.productItemName).text = productListItem.productName
        v.findViewById<TextView>(R.id.productItemPrice).text = productListItem.productPrice.toString()
        if(productListItem.productImageURL?.isNotEmpty() == true) {
            Picasso.get().load(productListItem.productImageURL).into(v.findViewById<ImageView>(R.id.productItemImage))
        }
    }
}