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
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentMapBinding
import ru.nikita.mapmarks.viewModel.MarksViewModel


class MapFragment : Fragment(), CameraListener {
    lateinit var binding: FragmentMapBinding
    private val viewModel: MarksViewModel by viewModels()
    private var zoomValue = 8.0F
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placeMarkMapObject: PlacemarkMapObject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        binding.mapview.mapWindow.map.addCameraListener(this)
        val map = binding.mapview.mapWindow.map                                 // наша карта
        val marker = createBitmapFromVector(R.drawable.ic_pin_black_svg)        // метка
        val imageProvider = ImageProvider.fromBitmap(marker)
        mapObjectCollection = binding.mapview.mapWindow.map.mapObjects
        placeMarkMapObject = mapObjectCollection.addPlacemark()


        val inputListener = object : InputListener {

            override fun onMapTap(map: Map, pin: Point) {
                Toast.makeText(requireContext(), R.string.tap_on_map, Toast.LENGTH_SHORT).show()
            }

            override fun onMapLongTap(map: Map, point: Point) {
                placeMarkMapObject.geometry = point
                val idMark = 0L

                findNavController().navigate(
                    R.id.action_mapFragment_to_editPointFragment,
                    Bundle().apply {
                        putString(LAT_KEY, point.latitude.toString())
                        putString(LONG_KEY, point.longitude.toString())
                        putString(ID_KEY, idMark.toString())
                    })
            }
        }

        map.addInputListener(inputListener)

        val pinsCollection = map.mapObjects.addCollection()
        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.places.collectLatest { places ->
                pinsCollection.clear()
                places.forEach { point ->
                    pinsCollection.addPlacemark().apply {
                        geometry = Point(point.latitude, point.longitude)
                        setIcon(imageProvider)
                        setText(point.title)
                    }
                }

            }
        }

        binding.goToPointsFragment.setOnClickListener {
            findNavController().navigate(R.id.pointsFragment)
        }

        cameraMove()

        return binding.root
    }


    private fun cameraMove() {

        val latFromBD = arguments?.getString(LAT_KEY)?.toDouble() ?: 55.753188
        val longFromBD = arguments?.getString(LONG_KEY)?.toDouble() ?: 37.622428

        binding.mapview.mapWindow.map.move(
            CameraPosition(Point(latFromBD, longFromBD), zoomValue, 0.0F, 100.0F),
            Animation(Animation.Type.SMOOTH, 1F),
            null
        )
    }

    private fun createBitmapFromVector(art: Int): Bitmap? {
        val drawable = ContextCompat.getDrawable(requireContext(), art) ?: return null
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
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
        super.onStop()
        binding.mapview.onStop()
        MapKitFactory.getInstance().onStop()
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

    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LONG_KEY = "LONG_KEY"
        const val TITLE_KEY = "TITLE_KEY"
        const val ID_KEY = "ID_KEY"
    }
}
