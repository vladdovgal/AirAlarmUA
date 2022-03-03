package com.alarmua.ui.web

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.alarmua.R
import com.alarmua.databinding.FragmentWebviewBinding
import com.alarmua.databinding.MainFragmentBinding
import com.alarmua.ui.main.MainViewModel
import com.google.android.material.snackbar.Snackbar


class WebViewFragment : Fragment() {

    private val args: WebViewFragmentArgs by navArgs()
    private var binding: FragmentWebviewBinding? = null
    private lateinit var viewModel: WebViewViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[WebViewViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
        binding?.webView?.apply {
            val webSettings: WebSettings = settings
            webSettings.javaScriptEnabled = true
            loadUrl(args.url)
            webViewClient = Browser()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWebviewBinding.inflate(inflater, container, false)
        setUpObservers()
        return binding?.root
    }


    private fun setUpObservers() {
        lifecycleScope.launchWhenResumed {
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                binding?.progressBar?.isVisible = isLoading
            }
        }
    }

    inner class Browser : WebViewClient() {
        init {
            viewModel.loading(true)
        }
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            viewModel.loading(false)
        }
    }

}