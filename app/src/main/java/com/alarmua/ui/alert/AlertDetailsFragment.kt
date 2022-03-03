package com.alarmua.ui.alert

import android.os.BaseBundle
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.navArgs
import com.alarmua.R
import com.alarmua.databinding.FragmentAlertDetailsBinding
import com.alarmua.service.ALERT_TYPE_AIR_ALARM_OFF
import com.alarmua.service.ALERT_TYPE_AIR_ALARM_ON
import com.alarmua.service.NOTIFICATION_TYPE_ARG
import com.alarmua.ui.base.MyMediaPlayer


class AlertDetailsFragment : Fragment() {

    private var binding: FragmentAlertDetailsBinding? = null
    private lateinit var viewModel: AlertDetailsViewModel
    private val args: AlertDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[AlertDetailsViewModel::class.java]
        MyMediaPlayer.stopNotificationSound()
        val bundle = activity?.intent?.extras?.get(NavController.KEY_DEEP_LINK_EXTRAS) as BaseBundle

        when (bundle.get(NOTIFICATION_TYPE_ARG)) {
            ALERT_TYPE_AIR_ALARM_ON -> {
                setAlertOnViews()
            }
            ALERT_TYPE_AIR_ALARM_OFF -> {
                setAlertOffViews()
            }
            else -> setAlertOnViews()
        }

        return binding?.root
    }

    private fun setAlertOnViews() {
        binding?.apply {
            alertTitle.setText(R.string.air_alarm_on_title)
            rootLayout.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.secondaryDarkColor)
            )
            alertDescription.setText(R.string.air_alarm_on_description)
        }
    }

    private fun setAlertOffViews() {
        binding?.apply {
            alertTitle.setText(R.string.air_alarm_off_title)
            rootLayout.setBackgroundColor(
                ContextCompat.getColor(requireContext(), R.color.alertOffColor)
            )
            alertDescription.setText(R.string.air_alarm_off_description)
        }
    }
}