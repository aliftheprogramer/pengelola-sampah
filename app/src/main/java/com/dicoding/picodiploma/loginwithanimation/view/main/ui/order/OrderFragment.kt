package com.dicoding.picodiploma.loginwithanimation.view.main.ui.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory

class OrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

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

        return view
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }
}