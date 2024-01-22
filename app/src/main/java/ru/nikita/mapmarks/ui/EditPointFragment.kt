package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentEditPointBinding
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.ui.MapFragment.Companion.LAT_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LONG_KEY
import ru.nikita.mapmarks.viewModel.MarksViewModel

class EditPointFragment : Fragment() {
    private lateinit var binding: FragmentEditPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPointBinding.inflate(layoutInflater, container, false)
        val viewModel: MarksViewModel by activityViewModels()

        // получение данных с карты
        val latFromMap = arguments?.getString(LAT_KEY)
        val longFromMap = arguments?.getString(LONG_KEY)

        // вписывание данных в TextView
        binding.latitudeValue.text = latFromMap
        binding.longitudeValue.text = longFromMap


        binding.saveButton.setOnClickListener {
            // получение данных с TextView
            val setNewPointTitle = binding.titleEditText.text.toString()
            val setNewPointLat = (binding.latitudeValue.text as String?)!!.toDouble()
            val setNewPointLong = (binding.longitudeValue.text as String?)!!.toDouble()

            // отправляем во viewModel
            viewModel.save(
                Marks(
                    id,
                    title = setNewPointTitle,
                    latitude = setNewPointLat,
                    longitude = setNewPointLong
                ))

                // возвращаемся на карту
            findNavController().navigate(R.id.action_editPointFragment_to_mapFragment)
        }

        return binding.root
    }

}