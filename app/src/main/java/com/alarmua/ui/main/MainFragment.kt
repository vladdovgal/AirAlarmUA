package com.alarmua.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.alarmua.databinding.MainFragmentBinding
import com.alarmua.ui.main.list.DashboardLinksAdapter

class MainFragment : Fragment() {

    private var binding: MainFragmentBinding? = null
    private lateinit var viewModel: MainViewModel
    lateinit var listAdapter: DashboardLinksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        initViews()
        return binding?.root
    }

    private fun initViews() {
        listAdapter = DashboardLinksAdapter() {
            // navigate to WebView screen
        }
        listAdapter.submitList(
            viewModel.getDashboardLinks()
        )
        binding?.apply {
            recyclerView.adapter = listAdapter
            btnSelectLocation.setOnClickListener {
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToLocationsListFragment()
                )
            }
        }
    }
}