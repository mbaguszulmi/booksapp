package com.mbaguszulmi.booksapp.view.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbaguszulmi.booksapp.databinding.ListItemBookBinding
import com.mbaguszulmi.booksapp.model.local.Books


class BookListAdapter(
    private val bookList: MutableList<Books>,
    private val onItemClickListener: (id: String)-> Unit)
    : RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ListItemBookBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Books) {
            binding.tvBookTitle.text = book.title
            binding.tvAuthors.text = book.authors
            binding.tvBookCategories.text = book.categories
            binding.tvBookYear.text = book.getYear()
            binding.tvBookPrice.text = book.formattedPrice

            Glide
                .with(binding.root.context)
                .load(book.thumbnail)
                .centerCrop()
                .into(binding.ivBookCover)


            itemView.setOnClickListener {
                onItemClickListener(book.id)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(bookList: List<Books>) {
        with(this.bookList) {
            clear()
            addAll(bookList)
        }

        notifyDataSetChanged()
        Log.d("BookListAdapter", "Updating...")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(bookList[position])
    }

    override fun getItemCount(): Int = bookList.size
}