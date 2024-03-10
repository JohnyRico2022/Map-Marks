package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.adapter.MarksAdapter
import ru.nikita.mapmarks.adapter.OnInteractionListener
import ru.nikita.mapmarks.databinding.FragmentPointsBinding
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.ui.MapFragment.Companion.ID_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LAT_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.LONG_KEY
import ru.nikita.mapmarks.ui.MapFragment.Companion.TITLE_KEY
import ru.nikita.mapmarks.viewModel.MarksViewModel


class PointsFragment : Fragment() {

    lateinit var binding: FragmentPointsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsBinding.inflate(layoutInflater, container, false)
        val viewModel: MarksViewModel by viewModels()

        val adapter = MarksAdapter(object : OnInteractionListener {

            override fun onEdit(marks: Marks) {

                findNavController().navigate(R.id.editPointFragment,
                    Bundle().apply {
                        putString(LAT_KEY, marks.latitude.toString())
                        putString(LONG_KEY, marks.longitude.toString())
                        putString(TITLE_KEY, marks.title)
                        putString(ID_KEY, marks.id.toString())
                    })
            }

            override fun onRemove(marks: Marks) {
                viewModel.removeById(marks.id)
                Toast.makeText(context, "Вы удалили метку: ${marks.title}", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onItemClicked(marks: Marks) {

                findNavController().navigate(R.id.mapFragment,
                    Bundle().apply {
                        putString(LAT_KEY, marks.latitude.toString())
                        putString(LONG_KEY, marks.longitude.toString())
                        putString(TITLE_KEY, marks.title)
                    })
            }
        })

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            binding.recyclerView.adapter = adapter

            viewModel.places.collectLatest { plases ->
                adapter.submitList(plases)
            }
        }

        binding.backButton.setOnClickListener {
            findNavController().popBackStack(R.id.mapFragment, false)
        }
        return binding.root
    }
}
