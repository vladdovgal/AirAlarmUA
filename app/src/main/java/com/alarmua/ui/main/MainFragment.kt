package com.alarmua.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.alarmua.R
import com.alarmua.databinding.MainFragmentBinding
import com.alarmua.service.CURRENT_LOCATION_ID_KEY
import com.alarmua.service.CURRENT_LOCATION_SHARED_PREFS_FILE_NAME
import com.alarmua.ui.main.list.DashboardLinksAdapter
import com.google.android.material.snackbar.Snackbar

private const val TELEGRAM_BASE_LINK = "https://t.me"

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
        setUpObservers()
        initViews()
        return binding?.root
    }

    private fun setUpObservers() {
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
    }

    private fun initViews() {
        val sharedPreferences = activity?.getSharedPreferences(CURRENT_LOCATION_SHARED_PREFS_FILE_NAME, Context.MODE_PRIVATE)
        val lastLocationId = sharedPreferences?.getString(CURRENT_LOCATION_ID_KEY, null)
        listAdapter = DashboardLinksAdapter { url ->
            if (url.contains(TELEGRAM_BASE_LINK)) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            } else {
                navigateToWebViewFragment(url)
            }

        }
        listAdapter.submitList(
            viewModel.getDashboardLinks()
        )
        binding?.apply {
            itemChangeLocation.apply {
                linkTitle.setText(R.string.change_location)
                root.setOnClickListener {
                    navigateToSelectLocationFragment()
                }
            }
            recyclerView.apply {
                adapter = listAdapter
            }
            btnUnsubscribe.apply {
                text = String.format(
                    getString(R.string.unsubscribe_location),
                    lastLocationId,
                )
                setOnClickListener {
                    lastLocationId?.let {
                        viewModel.unsubscribeFromLocationUpdates(it)
                        sharedPreferences.edit()?.clear()?.apply()
                        navigateToSelectLocationFragment()
                    }
                }
            }
        }
    }


    private fun navigateToWebViewFragment(url: String) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToWebViewFragment(url)
        )
    }

    private fun navigateToSelectLocationFragment() {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToLocationsListFragment()
        )
    }
}