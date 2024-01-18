package ru.nikita.mapmarks.ui

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
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
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentMapBinding


class MapFragment : Fragment(), CameraListener {
    lateinit var binding: FragmentMapBinding
    private val startLocation2 = Point(60.054842, 30.328168) // стартовая точка
    private var zoomValue = 16.0F// величина приближения
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.mapview.mapWindow.map.addCameraListener(this)
        val map = binding.mapview.mapWindow.map           // наша карта
        val marker = createBitmapFromVector(R.drawable.ic_pin_black_svg) // метка
        val imageProvider = ImageProvider.fromBitmap(marker)
        mapObjectCollection = binding.mapview.mapWindow.map.mapObjects
        placemarkMapObject = mapObjectCollection.addPlacemark()

        val inputListener = object : InputListener {
            override fun onMapTap(map: Map, pin: Point) {             // одинарное нажатие на карту
                Toast.makeText(requireContext(), "Tap", Toast.LENGTH_SHORT).show()
            }

            override fun onMapLongTap(map: Map, point: Point) { // долгое нажатие на карту
                placemarkMapObject.geometry = point
                Toast.makeText(
                    requireContext(),
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

        //Начальное движенее камеры
        binding.mapview.mapWindow.map.move(
            CameraPosition(startLocation2, zoomValue, 0.0F, 100.0F),
            Animation(Animation.Type.SMOOTH, 7F),
            null
        )

        binding.goToPointsFragment.setOnClickListener {
            findNavController().navigate(R.id.pointsFragment)
        }

        return binding.root
    }
    // END onCreateView


    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), art) ?: return null
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
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