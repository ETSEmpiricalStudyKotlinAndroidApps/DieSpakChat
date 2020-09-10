package me.sungbin.spakchat.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import me.sungbin.spakchat.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    companion object {
        lateinit var fabAction: ExtendedFloatingActionButton
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.fm_container)
        fabAction = fab_action
        supportActionBar?.hide()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_navigation, menu)
        sbb_navigation.setupWithNavController(menu!!, navController)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        navController.navigateUp()
        return true
    }

}