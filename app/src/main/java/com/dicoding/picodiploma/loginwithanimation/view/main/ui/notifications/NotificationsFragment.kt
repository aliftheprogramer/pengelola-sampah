package com.dicoding.picodiploma.loginwithanimation.view.main.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentNotificationsBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.customview.SpacingItemDecoration

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory.getInstance(requireContext())
        val notificationsViewModel = ViewModelProvider(this, factory).get(NotificationsViewModel::class.java)
        notificationsViewModel.loadNotifications()

        notificationsViewModel.notifications.observe(viewLifecycleOwner, { notifications ->
            val adapter = NotificationsAdapter(notifications)
            binding.recyclerViewNotifications.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerViewNotifications.adapter = adapter
            binding.recyclerViewNotifications.addItemDecoration(SpacingItemDecoration(10))
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }
}