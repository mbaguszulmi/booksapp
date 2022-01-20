package com.mbaguszulmi.booksapp.view.activity

import android.app.SearchManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.mbaguszulmi.booksapp.R
import com.mbaguszulmi.booksapp.databinding.ActivityMainBinding
import com.mbaguszulmi.booksapp.view.fragment.FavoriteFragment
import com.mbaguszulmi.booksapp.view.fragment.HomeFragment
import com.mbaguszulmi.booksapp.view.fragment.SettingsFragment
import com.mbaguszulmi.booksapp.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var searchItem: MenuItem? = null

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        val homeFragment = HomeFragment.newInstance()
        val favoriteFragment = FavoriteFragment.newInstance()
        val settingsFragment = SettingsFragment.newInstance()

        changeFragment(homeFragment, R.id.mi_home)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mi_home -> changeFragment(homeFragment, it.itemId)
                R.id.mi_favorite -> changeFragment(favoriteFragment, it.itemId)
                R.id.mi_settings -> changeFragment(settingsFragment, it.itemId)
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        searchItem = menu?.findItem(R.id.app_bar_search)!!
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Find Books"

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                mainViewModel.setQuery(q)
                mainViewModel.searchBook()

                return true
            }

            override fun onQueryTextChange(q: String?): Boolean {
                return false
            }
        })

        searchItem?.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                mainViewModel.setQuery(null)
                mainViewModel.searchBook()
                return true
            }


        })

        return true
    }

    private fun changeFragment(fragment: Fragment, id: Int): Boolean {
        mainViewModel.menuId = id

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFcm, fragment)
            commit()
        }

        supportActionBar?.title = when(id) {
            R.id.mi_home -> "Home"
            R.id.mi_settings -> "Settings"
            R.id.mi_favorite -> {
                "Favorites"
            }
            else -> getString(R.string.app_name)
        }

        searchItem?.isVisible = R.id.mi_settings != id

        return true
    }
}
