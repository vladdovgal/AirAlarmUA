package com.alarmua.ui.locations

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.alarmua.R
import com.alarmua.databinding.FragmentLocationsListBinding
import com.alarmua.service.CURRENT_LOCATION_ID_KEY
import com.alarmua.service.CURRENT_LOCATION_SHARED_PREFS_FILE_NAME
import com.alarmua.ui.locations.list.LocationsListAdapter
import com.google.android.material.snackbar.Snackbar


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
            viewModel.shortMessage.observe(viewLifecycleOwner) { message ->
                if (message.isNotEmpty()) {
                    binding?.apply {
                        Snackbar.make(root, message, Snackbar.LENGTH_LONG)
                            .setAction(R.string.dismiss) { /*dismiss*/ }
                            .show()
                    }
                }
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.navigationDestination.observe(viewLifecycleOwner) { destination ->
                if (destination != null) {
                    navigateTo(destination)
                }
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
                val selectedLocationId = viewModel.locations.value?.find { it.isSelected }?.id ?: "NULL"
                val lastLocationId = updateCurrentLocationSharedPrefs(selectedLocationId)
                viewModel.saveLocation(
                    lastLocationId = lastLocationId,
                    selectedLocationId = selectedLocationId,
                )
            }
        }
    }

    private fun navigateTo(destination: NavDirections) {
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(destination)
    }

    private fun updateCurrentLocationSharedPrefs(selectedLocationId: String): String? {
        return activity?.let {
            val prefs = it.getSharedPreferences(CURRENT_LOCATION_SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
            val lastLocationId = prefs.getString(CURRENT_LOCATION_ID_KEY, null) // get last saved location
            prefs.edit().putString(CURRENT_LOCATION_ID_KEY, selectedLocationId).apply() // save new locationId
            return@let lastLocationId
        }
    }
}