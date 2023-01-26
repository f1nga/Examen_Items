package com.example.examenitems.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.examenitems.R
import com.example.examenitems.databinding.FragmentItemDetailsBinding
import com.example.examenitems.models.Item
import com.example.examenitems.viewmodel.ItemViewModel

class ItemDetailsFragment : Fragment() {
    private val viewModel: ItemViewModel by activityViewModels()
    lateinit var binding: FragmentItemDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_item_details, container, false
        )
        setHasOptionsMenu(true)

        setDetails()

        binding.btnDelete.setOnClickListener() {
            deleteItemToDB()
        }

        binding.btnUpdate.setOnClickListener() {
            updateItemToDB()
        }


        return binding.root
    }

    private fun setDetails() {
        val itemName = binding.txtNom
        val itemPrice = binding.txtPreu

        itemName.text = viewModel.getItem()?.nom
        itemPrice.text = viewModel.getItem()?.preu.toString()
    }

    private fun deleteItemToDB() {
        val itemName = binding.txtNom.text.toString()
        val itemPrice = binding.txtPreu.text.toString().toInt()

        viewModel.deleteItem(requireContext(), Item(itemName, itemPrice))
    }

    private fun updateItemToDB() {
        val intent = Intent(requireContext(), EditItemActivity::class.java)
        intent.putExtra("item_name", binding.txtNom.text.toString())
        intent.putExtra("item_price", binding.txtPreu.text.toString())
        updateItem.launch(intent)

    }

    val updateItem =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Get the edited note from the AddNoteActivity
                val itemName = result.data?.getStringExtra("item_name")
                val itemPrice = result.data?.getStringExtra("item_price")
                // Update the note in the list
                val newItem = Item(itemName!!, itemPrice!!.toInt())

                val currentItem = Item(viewModel.getItem()?.nom!!, viewModel.getItem()?.preu!!)
                viewModel.updateData(requireContext(), currentItem, newItem)

                viewModel.setItem(newItem)

                setDetails()
            }
        }
}