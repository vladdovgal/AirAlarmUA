package com.alarmua.ui.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alarmua.databinding.FragmentLocationsListBinding
import com.alarmua.ui.locations.list.LocationsListAdapter

class LocationsListFragment : Fragment() {

    private var binding: FragmentLocationsListBinding? = null
    private lateinit var viewModel: LocationsListViewModel
    lateinit var listAdapter: LocationsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLocationsListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[LocationsListViewModel::class.java]
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setObservers()
    }

    private fun setObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.locations.observe(viewLifecycleOwner) { items ->
                listAdapter.submitList(items)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.isSaveButtonEnabled.observe(viewLifecycleOwner) { enabled ->
                binding?.saveButton?.isVisible = enabled
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding?.blockingProgress?.isVisible = isLoading
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.toastMessage.observe(viewLifecycleOwner) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initView() {
        listAdapter = LocationsListAdapter { selectedItemId ->
            viewModel.selectItem(selectedItemId)
        }
        binding?.apply {
            locationsList.adapter = listAdapter
            saveButton.setOnClickListener {
                viewModel.saveLocation(
                    viewModel.locations.value?.find { it.isSelected }?.id ?: "NULL"
                )
            }
        }
    }
}