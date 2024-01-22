package ru.nikita.mapmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.nikita.mapmarks.R
import ru.nikita.mapmarks.adapter.MarksAdapter
import ru.nikita.mapmarks.adapter.OnInteractionListener
import ru.nikita.mapmarks.databinding.FragmentPointsBinding
import ru.nikita.mapmarks.dto.Marks
import ru.nikita.mapmarks.viewModel.MarksViewModel


class PointsFragment : Fragment() {

    lateinit var binding: FragmentPointsBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPointsBinding.inflate(layoutInflater, container, false)
         val viewModel: MarksViewModel by viewModels()

        val adapter = MarksAdapter(object : OnInteractionListener{

            override fun onEdit(marks: Marks) {
                Toast.makeText(context, "Добавление id: ${marks.id}", Toast.LENGTH_SHORT).show()
            }

            override fun onRemove(marks: Marks) {
                viewModel.removeById(marks.id)
                Toast.makeText(context, "Удаление id: ${marks.id}", Toast.LENGTH_SHORT).show()
            }
        })

        binding.recyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { marks ->
            val newMarks = marks.size > adapter.currentList.size
            adapter.submitList(marks){
                if(newMarks){
                    binding.recyclerView.smoothScrollToPosition(0)
                }
            }
        }


        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.mapFragment)
        }
        return binding.root
    }
}