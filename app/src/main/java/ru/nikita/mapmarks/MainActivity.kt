package ru.nikita.mapmarks

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.CameraUpdateReason
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import ru.nikita.mapmarks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), CameraListener {
    private lateinit var binding: ActivityMainBinding
    private val startLocation2 = Point(60.054842, 30.328168) // стартовая точка
    private var zoomValue = 16.0F// величина приближения
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setApiKey(savedInstanceState)
        MapKitFactory.initialize(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mapview.mapWindow.map.addCameraListener(this)
        val map = binding.mapview.mapWindow.map           // наша карта
        val marker = createBitmapFromVector(R.drawable.ic_pin_black_svg) // метка
        val imageProvider = ImageProvider.fromBitmap(marker)
        mapObjectCollection = binding.mapview.mapWindow.map.mapObjects
        placemarkMapObject = mapObjectCollection.addPlacemark()
        requestLocationPermission()
        moveToStartLocation()

        val inputListener = object : InputListener {
            override fun onMapTap(map: Map, pin: Point) {             // одинарное нажатие на карту
                Toast.makeText(applicationContext, "Tap", Toast.LENGTH_SHORT).show()
            }

            override fun onMapLongTap(map: Map, point: Point) { // долгое нажатие на карту
                placemarkMapObject.geometry = point
                Toast.makeText(
                    applicationContext,
                    "long Tap :   ${point.latitude} + ${point.longitude}",
                    Toast.LENGTH_SHORT
                ).show()

                val lat = point.latitude
                val long = point.longitude

                map.mapObjects.addPlacemark().apply {
                    geometry = Point(lat, long)
                    setIcon(imageProvider)
                    setText("new point")
                }
            }
        }

        map.addInputListener(inputListener)
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey") ?: false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY)
        }
    }

    private fun requestLocationPermission() {    //разрешение на определение местоположения
        if (
            (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) ||
            (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        )
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        return
    }

    private fun moveToStartLocation() {     //Начальное движенее камеры
        binding.mapview.mapWindow.map.move(
            CameraPosition(startLocation2, zoomValue, 0.0F, 100.0F),
            Animation(Animation.Type.SMOOTH, 7F),
            null
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(this, art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        ) ?: return null
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    companion object {
        const val MAPKIT_API_KEY = "c439084a-3fdb-4fe3-903f-e0d2ee642d43"
    }

    override fun onCameraPositionChanged(
        map: Map,
        cameraPosition: CameraPosition,
        cameraUpdateReason: CameraUpdateReason,
        finished: Boolean
    ) {
        zoomValue = cameraPosition.zoom
    }
}


