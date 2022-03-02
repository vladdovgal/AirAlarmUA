package com.alarmua.ui.success

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.alarmua.R
import com.alarmua.databinding.FragmentAlertDetailsBinding
import com.alarmua.databinding.FragmentSuccessActionBinding
import com.alarmua.ui.alert.AlertDetailsViewModel

class SuccessActionFragment : Fragment() {

    private var binding: FragmentSuccessActionBinding? = null
    private lateinit var viewModel: AlertDetailsViewModel
    private val args: SuccessActionFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSuccessActionBinding.inflate(inflater, container, false)
        binding?.apply {
            txtDescription.text = String.format(
                getString(R.string.subscription_description),
                args.locationName
            )
            okButton.setOnClickListener {
                findNavController().navigate(
                    SuccessActionFragmentDirections.actionSuccessFragmentToMainFragment()
                )
            }
        }
        viewModel = ViewModelProvider(this)[AlertDetailsViewModel::class.java]
        return binding?.root
    }
}