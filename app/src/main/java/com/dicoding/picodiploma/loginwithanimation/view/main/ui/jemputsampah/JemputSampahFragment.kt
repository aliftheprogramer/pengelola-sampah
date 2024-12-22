package com.dicoding.picodiploma.loginwithanimation.view.main.ui.jemputsampah

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dicoding.picodiploma.loginwithanimation.R

class JemputSampahFragment : Fragment() {

    companion object {
        fun newInstance() = JemputSampahFragment()
    }

    private val viewModel: JemputSampahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_jemput_sampah, container, false)
    }
}