package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentEditPointBinding


class EditPointFragment : Fragment() {
    private lateinit var binding: FragmentEditPointBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditPointBinding.inflate(layoutInflater, container, false)





        binding.saveButton.setOnClickListener {
            findNavController().navigate(R.id.action_editPointFragment_to_mapFragment)
        }



        binding.root
        return inflater.inflate(R.layout.fragment_edit_point, container, false)
    }
}