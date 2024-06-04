package com.juanfra.appubicaciones.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.navigation.NavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.juanfra.appubicaciones.R
import com.juanfra.appubicaciones.data.MapViewModel
import com.juanfra.appubicaciones.databinding.ActivityMainBinding
import com.juanfra.appubicaciones.ui.fragment.BottomSheet

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    val viewModel by viewModels<MapViewModel>{
        MapViewModel.MyViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BottomSheet.viewModel = viewModel

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val fragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragment.getMapAsync(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onMapReady(map: GoogleMap) {

        map.mapType = GoogleMap.MAP_TYPE_HYBRID
        val bottomSheet = BottomSheet()


        map.setOnMapClickListener {
            bottomSheet.show(supportFragmentManager, "BottomSheet")
            viewModel.actualLatLng.value = it
            viewModel.actualFuente.value = null
        }

        map.setOnMarkerClickListener {
            viewModel.actualLatLng.value = it.position
            it.snippet?.toInt()?.let { it1 -> viewModel.obtenerFuentePorId(it1) }
            bottomSheet.show(supportFragmentManager, "BottomSheet")
            true
        }

        viewModel.obtenerTodasFuentes().observe(this){
            it.forEach {
                val marker = MarkerOptions()
                    .title(it.nombre)
                    .snippet(it.id.toString())
                    .position(LatLng(it.lat, it.lng))
                map.addMarker(marker)
            }

        }

    }
}