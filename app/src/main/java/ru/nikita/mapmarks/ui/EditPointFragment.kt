package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentEditPointBinding
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.ui.MapFragment.Companion.ID_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LAT_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LONG_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.TITLE_KEY
import ru.nikita.mapmarks.viewModel.MarksViewModel

class EditPointFragment : Fragment() {
    private lateinit var binding: FragmentEditPointBinding
    private var nextId = 0L
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPointBinding.inflate(layoutInflater, container, false)
        val viewModel: MarksViewModel by activityViewModels()

        // получение данных
        val lat = arguments?.getString(LAT_KEY)
        val long = arguments?.getString(LONG_KEY)
        val title = arguments?.getString(TITLE_KEY)
        val currentId = arguments?.getString(ID_KEY)


        // вписывание данных в TextView
        binding.latitudeValue.text = lat
        binding.longitudeValue.text = long
        binding.titleEditText.setText(title)

        binding.saveButton.setOnClickListener {
            // получение данных с TextView
            val setNewPointTitle = binding.titleEditText.text.toString()
            val setNewPointLat = (binding.latitudeValue.text as String?)!!.toDouble()
            val setNewPointLong = (binding.longitudeValue.text as String?)!!.toDouble()
            val newId = currentId!!.toLong()

            if (newId == 0L) {
                viewModel.save(
                    Marks(
                        id = nextId++,
                        title = setNewPointTitle,
                        latitude = setNewPointLat,
                        longitude = setNewPointLong
                    ))
            } else {
                viewModel.editById(
                    currentId.toLong(),
                    title = setNewPointTitle
                )
            }

            findNavController().navigate(R.id.action_editPointFragment_to_mapFragment)
            }

        return binding.root
        }
    }

