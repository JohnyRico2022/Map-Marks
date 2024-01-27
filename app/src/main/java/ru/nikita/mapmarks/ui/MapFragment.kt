package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentMapBinding
import kotlin.random.Random


class MapFragment : Fragment(R.layout.fragment_map) {
    lateinit var binding: FragmentMapBinding
    /*val viewModel: MarksViewModel by viewModels()
    private val startLocation = Point(55.753188, 37.622428)    // стартовая точка
    private var zoomValue = 10.0F                                          // величина приближения
    private lateinit var mapObjectCollection: MapObjectCollection
    private lateinit var placemarkMapObject: PlacemarkMapObject*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        /* binding.mapview.mapWindow.map.addCameraListener(this)
         val map = binding.mapview.mapWindow.map                                 // наша карта
         val marker = createBitmapFromVector(R.drawable.ic_pin_black_svg)        // метка
         val imageProvider = ImageProvider.fromBitmap(marker)
         mapObjectCollection = binding.mapview.mapWindow.map.mapObjects
         placemarkMapObject = mapObjectCollection.addPlacemark()

         //Начальное движенее камеры
         binding.mapview.mapWindow.map.move(
             CameraPosition(startLocation, zoomValue, 0.0F, 100.0F),
             Animation(Animation.Type.SMOOTH, 2F),
             null
         )
 */

        /*val inputListener = object : InputListener {
            override fun onMapTap(map: Map, pin: Point) {
                Toast.makeText(requireContext(), "Tap", Toast.LENGTH_SHORT).show()
            }
            override fun onMapLongTap(map: Map, point: Point) {
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

                }

                findNavController().navigate(
                    R.id.action_mapFragment_to_editPointFragment,
                    Bundle().apply {
                        putString(LAT_KEY, lat.toString())
                        putString(LONG_KEY, long.toString())
                    })
            }
        }

        map.addInputListener(inputListener)

        binding.goToPointsFragment.setOnClickListener {
            findNavController().navigate(R.id.pointsFragment)
        }*/

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            randomButton.setOnClickListener {
                val latRandom = Random.nextDouble(30.000, 35.999)
                val longRandom = Random.nextDouble(60.000, 65.999)
                mapLat.text = latRandom.toString()
                mapLong.text = longRandom.toString()

                Toast.makeText(
                    requireContext(),
                    "long Tap :   $latRandom + $longRandom",
                    Toast.LENGTH_LONG
                ).show()

                findNavController().navigate(
                    R.id.action_mapFragment_to_editPointFragment,
                    Bundle().apply {
                        putString(LAT_KEY, latRandom.toString())
                        putString(LONG_KEY, longRandom.toString())
                    })
            }
        }

        binding.goToPointsFragment.setOnClickListener {
            findNavController().navigate(R.id.pointsFragment)
        }
    }

    /*private fun createBitmapFromVector(art: Int): Bitmap? {
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
    }*/

    companion object {
        const val LAT_KEY = "LAT_KEY"
        const val LONG_KEY = "LONG_KEY"
    }
}
