package me.berkesongur.UrunKayitSistemi.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import me.berkesongur.UrunKayitSistemi.R
import me.berkesongur.UrunKayitSistemi.holders.ProductListHolder
import me.berkesongur.UrunKayitSistemi.models.ProductListItem


class ProductListAdapter(private val ctx: Context) : ListAdapter<ProductListItem, ProductListHolder>(comparator), Filterable {

    companion object {
        private val comparator = object : DiffUtil.ItemCallback<ProductListItem>() {
            override fun areItemsTheSame(
                oldItem: ProductListItem,
                newItem: ProductListItem
            ): Boolean {
                return oldItem.productName == newItem.productName
            }

            override fun areContentsTheSame(
                oldItem: ProductListItem,
                newItem: ProductListItem
            ): Boolean {
                return oldItem.equals(newItem)
            }
        }
    }

    var productListItems: MutableList<ProductListItem> = ArrayList()

    fun setProductListItems(productListItems: List<ProductListItem>): ProductListAdapter {
        this.productListItems = ArrayList(productListItems)
        submitList(productListItems)
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListHolder {
        return ProductListHolder(LayoutInflater.from(this.ctx).inflate(R.layout.card_item_product, parent, false))
    }

    override fun onBindViewHolder(holder: ProductListHolder, position: Int) {
        holder.bind(productListItems[position])
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val filterResults = FilterResults()

                if(p0.toString().isEmpty()) return filterResults

                filterResults.values = ArrayList<ProductListItem>()

                for (i in productListItems) {
                    if(i.productName.lowercase()
                            .trim().startsWith(p0.toString().lowercase().trim())) {
                        val values= filterResults.values as ArrayList<ProductListItem>
                        values.add(i)
                    }
                }
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                productListItems.clear()
                if (p1 != null) {
                    productListItems.addAll(p1.values as Collection<ProductListItem>)
                }
                submitList(productListItems)
            }

        }
    }

    private fun swap(a: MutableList<ProductListItem>, i: Int, j: Int) {
        val t = a[i]
        a[i] = a[j]
        a[j] = t
    }
    private fun partition(a: MutableList<ProductListItem>, start: Int, end: Int, r: Boolean): Int {
        val pivot = a[end].productPrice
        var i = start - 1
        for(j in start until end) {
            if(if(r) a[j].productPrice > pivot else a[j].productPrice < pivot) {
                i++
                swap(a, i, j)
            }
        }
        swap(a, i + 1, end)
        return i + 1
    }
    fun sort(start: Int, end: Int, r: Boolean = false) {
        if(start < end) {
            val pi = partition(productListItems, start, end, r)
            sort(0, pi - 1, r)
            sort(pi + 1, end, r)
        }
    }
}

