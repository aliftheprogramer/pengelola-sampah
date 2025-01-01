package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = OrderAdapter(emptyList()) { order ->
            viewModel.deleteOrder(order)
        }
        recyclerView.adapter = adapter

        viewModel.allOrders.observe(viewLifecycleOwner, Observer { orders ->
            orders?.let {
                adapter.updateOrders(it)
            }
        })

        // Retrieve email and password from Firebase Authentication and UserPreferences
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email ?: ""


        val userPreferences = UserPreference.getInstance(requireContext().dataStore)
        lifecycleScope.launch {
            val password = userPreferences.getPassword().first() ?: ""

            // Display GG points
            val textBalance: TextView = view.findViewById(R.id.text_balance)
            viewModel.getUser(email, password).observe(viewLifecycleOwner, Observer { user ->
                if (user != null) {
                    textBalance.text = "GG points: ${user.ggPoints}"
                } else {
                    textBalance.text = "GG points: 0"
                    Toast.makeText(requireContext(), "User data not found", Toast.LENGTH_SHORT).show()
                }
            })
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }
}