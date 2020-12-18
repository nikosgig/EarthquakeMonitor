package com.steliosgig.earthquakemonitor.features.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.steliosgig.earthquakemonitor.R
import com.steliosgig.earthquakemonitor.features.earthquakemap.MapsActivity
import kotlinx.android.synthetic.main.activity_maps.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        bottom_nav.selectedItemId = R.id.navigation_settings
        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_map -> {
                    startActivity(Intent(this, MapsActivity::class.java))
                    overridePendingTransition(0, 0)
                    finish()
                }
            }
            true
        }
    }
}