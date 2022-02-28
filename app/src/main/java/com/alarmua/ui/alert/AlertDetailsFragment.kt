package com.alarmua.ui.alert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alarmua.databinding.FragmentAlertDetailsBinding


class AlertDetailsFragment : Fragment() {

    private var binding: FragmentAlertDetailsBinding? = null
    private lateinit var viewModel: AlertDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AlertDetailsViewModel::class.java]
        return binding?.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlertDetailsFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlertDetailsFragment().apply {
                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}