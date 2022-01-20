package com.mbaguszulmi.booksapp.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.mbaguszulmi.booksapp.R
import com.mbaguszulmi.booksapp.databinding.ActivityDetailBinding
import com.mbaguszulmi.booksapp.viewmodel.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_BOOK_ID = "__extra_book_id__"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var bookId: String

    private val detailsViewModel by viewModels<DetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bookId = intent.getStringExtra(EXTRA_BOOK_ID) ?: ""
        if (bookId.isEmpty()) {
            Toast.makeText(this, getString(R.string.missing_book_id), Toast.LENGTH_LONG).show()
            finish()
        }

        detailsViewModel.fetchDetails(bookId, this)

        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initView() {
        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

        detailsViewModel.isLoading.observe(this) {
            binding.pbLoadDetails.isVisible = it
        }

        detailsViewModel.details.observe(this) {
            it?.let {
                binding.tvTitle.text = it.title
                binding.tvBookAuthors.text = it.authors
                binding.tvPublisher.text = it.publisher
                binding.tvPrice.text = it.formattedPrice

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    binding.tvDescription.text = Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    //noinspection deprecation
                    binding.tvDescription.text = Html.fromHtml(it.description)
                }
                binding.tvPublishedDate.text =  if (it.formattedPublishedDate.isNotEmpty())
                    getString(R.string.book_published_date, it.formattedPublishedDate) else ""
                binding.tvCategories.text = it.categories

                binding.btnInfo.setOnClickListener {_ ->
                    val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.infoLink))
                    startActivity(urlIntent)
                }

                Glide.with(this)
                    .load(it.thumbnail)
                    .centerCrop()
                    .into(binding.ivCover)
            }
        }

        detailsViewModel.errorMessage.observe(this) {
            if (it != null) {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
}