package com.example.examenitems.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenitems.R
import com.example.examenitems.adapter.ItemAdapter
import com.example.examenitems.adapter.RecyclerClickListener
import com.example.examenitems.databinding.FragmentShowBinding
import com.example.examenitems.viewmodel.ItemViewModel
import androidx.fragment.app.activityViewModels
import com.example.examenitems.models.Item


class ShowFragment : Fragment() {

    private lateinit var adapter: ItemAdapter
    private val viewModel: ItemViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentShowBinding>(
            inflater,
            R.layout.fragment_show, container, false
        )
        setHasOptionsMenu(true)

        viewModel.deleteItem(requireContext(), Item("Lampara", 10))

        //viewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        setRecyclerView(binding)
        observeNotes()

        binding.btnAfegir.setOnClickListener() {
            it.findNavController().navigate(R.id.action_showFragment_to_addFragment)

        }

        return binding.root

    }

    private fun setRecyclerView(binding: FragmentShowBinding) {
        val notesRecyclerview = binding.RecyclerView
        notesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        notesRecyclerview.setHasFixedSize(true)
        adapter = ItemAdapter()

        adapter.setItemListener(object : RecyclerClickListener {

            override fun onItemClick(position: Int) {
                val itemsList = adapter.currentList.toMutableList()
                viewModel.setItem(itemsList[position])

                view?.findNavController()?.navigate(R.id.action_showFragment_to_itemDetailsFragment)

            }
        })

        notesRecyclerview.adapter = adapter
    }

    private fun observeNotes() {
        viewModel.getItems(requireContext())?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}