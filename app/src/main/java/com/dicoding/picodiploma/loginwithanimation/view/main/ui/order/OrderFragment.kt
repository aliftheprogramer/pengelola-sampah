package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentOrderBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {

    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: OrderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Setup RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = OrderAdapter(emptyList()) { order ->
            viewModel.deleteOrder(order)
        }
        binding.recyclerView.adapter = adapter

        // Observe orders from ViewModel
        viewModel.allOrders.observe(viewLifecycleOwner) { orders ->
            orders?.let {
                adapter.updateOrders(it)
            }
        }

        // Retrieve email and password from Firebase Authentication and UserPreferences
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email ?: ""

        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("GGPointsPrefs", Context.MODE_PRIVATE)

        val ggPoints = sharedPreferences.getInt("gg_points", 0)
        binding.textBalance.text = "GG points: $ggPoints"

        val userPreferences = UserPreference.getInstance(requireContext().dataStore)

        lifecycleScope.launch {
            val password = userPreferences.getPassword().first() ?: ""

            // Display GG points using ViewModel or SharedPreferences fallback
            viewModel.getUser(email, password).observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    binding.textBalance.text = "GG points: ${ggPoints}"
                } else {
                    val fallbackPoints = sharedPreferences.getInt("gg_points", 0)
                    binding.textBalance.text = "GG points: $fallbackPoints"
                    if (fallbackPoints == 0) {
                        Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
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
