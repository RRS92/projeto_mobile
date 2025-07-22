package com.rebson.projetomobile.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rebson.projetomobile.R
import com.rebson.projetomobile.ui.fragments.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Desativa o modo escuro no app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<MaterialToolbar>(R.id.top_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "" // Remove qualquer título da barra

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> loadFragment(HomeFragment())
                R.id.nav_random -> loadFragment(RandomFragment())
                R.id.nav_search -> loadFragment(SearchFragment())
                R.id.nav_filter_letter -> loadFragment(FilterByLetterFragment())
                R.id.nav_filter_ingredient -> loadFragment(FilterByIngredientFragment())
            }
            true
        }

        // Fragmento inicial
        loadFragment(HomeFragment())

        // Marca o botão "Início" como selecionado
        bottomNav.selectedItemId = R.id.nav_home
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
