package com.example.examenitems.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.examenitems.R
import com.example.examenitems.databinding.FragmentAddBinding
import com.example.examenitems.databinding.FragmentShowBinding
import com.example.examenitems.models.Item
import com.example.examenitems.viewmodel.ItemViewModel

class AddFragment : Fragment() {

    lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: ItemViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add, container, false
        )
        setHasOptionsMenu(true)

        viewModel = ViewModelProvider(this)[ItemViewModel::class.java]

        binding.btnInsertar.setOnClickListener() {
            addItemToDB()
        }

        return binding.root
    }

    private fun addItemToDB() {
        val itemName = binding.inputNomText.text.toString()
        val itemPrice = binding.inputPreuText.text.toString().toInt()

        viewModel.insertItem(requireContext(), Item(itemName, itemPrice))
    }

}