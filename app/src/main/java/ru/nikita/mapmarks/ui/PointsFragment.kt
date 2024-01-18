package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.databinding.FragmentPointsBinding


class PointsFragment : Fragment() {

    lateinit var binding: FragmentPointsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsBinding.inflate(layoutInflater, container, false)







        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }
        return binding.root
    }
}