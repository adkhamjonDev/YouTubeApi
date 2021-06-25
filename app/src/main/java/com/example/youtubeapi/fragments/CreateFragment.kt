package com.example.youtubeapi.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.youtubeapi.R
import com.example.youtubeapi.databinding.FragmentCreateBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class CreateFragment : Fragment() {
    private lateinit var binding: FragmentCreateBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentCreateBinding.inflate(inflater, container, false)
        val bottomSheetDialog = BottomSheetDialog(
            requireContext(),
            R.style.BottomSheetDialogThem
        )
        val dialogView = LayoutInflater.from(requireContext()).inflate(
            R.layout.bottom_sheet,
            binding.root,
            false
        )
        bottomSheetDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        bottomSheetDialog.setContentView(dialogView)
        bottomSheetDialog.show()
        return binding.root
    }

}