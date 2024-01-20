package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentEditPointBinding
import ru.nikita.mapmarks.ui.MapFragment.Companion.LAT_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LONG_KEY


class EditPointFragment : Fragment() {
    private lateinit var binding: FragmentEditPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditPointBinding.inflate(layoutInflater, container, false)


        val latFromMap = arguments?.getString(LAT_KEY)
        val longFromMap = arguments?.getString(LONG_KEY)


        binding.latitudeEditText.setText(latFromMap)
        binding.longitudeEditText.setText(longFromMap)




        binding.saveButton.setOnClickListener {
            findNavController().navigate(R.id.action_editPointFragment_to_mapFragment)
        }




        return binding.root
    }
}