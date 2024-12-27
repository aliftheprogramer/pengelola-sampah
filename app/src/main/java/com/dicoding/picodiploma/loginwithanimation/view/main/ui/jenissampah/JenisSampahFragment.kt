// JenisSampahFragment.kt
package com.dicoding.picodiploma.loginwithanimation.view.main.ui.jenissampah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentJenisSampahBinding

class JenisSampahFragment : Fragment() {

    private var _binding: FragmentJenisSampahBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJenisSampahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}