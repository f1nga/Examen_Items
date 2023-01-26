package com.example.examenitems.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenitems.R
import com.example.examenitems.adapter.ItemAdapter
import com.example.examenitems.adapter.RecyclerClickListener
import com.example.examenitems.databinding.FragmentShowBinding
import com.example.examenitems.viewmodel.ItemViewModel


class ShowFragment : Fragment() {

    private lateinit var adapter: ItemAdapter
    private val viewModel: ItemViewModel by activityViewModels()
    lateinit var binding: FragmentShowBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_show, container, false
        )
        setHasOptionsMenu(true)

        setRecyclerView()
        observeItems()

        binding.btnAfegir.setOnClickListener() {
            it.findNavController().navigate(R.id.action_showFragment_to_addFragment)
        }

        return binding.root

    }

    private fun setRecyclerView() {
        val itemsRecyclerView = binding.RecyclerView
        itemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        itemsRecyclerView.setHasFixedSize(true)
        adapter = ItemAdapter()

        adapter.setItemListener(object : RecyclerClickListener {
            override fun onItemClick(position: Int) {
                val itemsList = adapter.currentList.toMutableList()
                viewModel.setItem(itemsList[position])

                view?.findNavController()?.navigate(R.id.action_showFragment_to_itemDetailsFragment)

            }
        })

        itemsRecyclerView.adapter = adapter
    }

    private fun observeItems() {
        viewModel.getItems(requireContext())?.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}