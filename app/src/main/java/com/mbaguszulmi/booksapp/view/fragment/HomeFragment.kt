package com.mbaguszulmi.booksapp.view.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaguszulmi.booksapp.databinding.FragmentHomeBinding
import com.mbaguszulmi.booksapp.model.local.Books
import com.mbaguszulmi.booksapp.view.adapter.BookListAdapter
import com.mbaguszulmi.booksapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        mainViewModel.searchBook()
    }

    private fun initView() {
        Log.d("HomeFragment", "initView called")
        val adapter = BookListAdapter(mainViewModel.booksValue as MutableList<Books>) {
            // TODO: navigate to details activity with id
        }
        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMain.adapter = adapter

        mainViewModel.books.observe(requireActivity()) {
            Log.d("HomeFragment", "Updating From Fragment... ${it.size}")
            adapter.updateData(it)
        }

        mainViewModel.isLoading.observe(requireActivity()) {

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment HomeFragment.
         */
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}