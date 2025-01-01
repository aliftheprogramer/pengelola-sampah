package com.dicoding.picodiploma.loginwithanimation.view.main.ui.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private lateinit var newsPagerAdapter: NewsPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack()
        }

        binding.jemputSampah.setOnClickListener {
            findNavController().navigate(R.id.navigation_jemput_sampah)
        }

        binding.cvKategori.setOnClickListener {
            findNavController().navigate(R.id.navigation_jenis_sampah)
        }

        // Inisialisasi ViewPager2
        viewPager = binding.viewPager

        val newsList = listOf(
            NewsItem(R.drawable.ic_logo, "Sampah terkendali, lingkungan sehat!", "Aplikasi GoGreen adalah solusi untuk menyelesaikan masalah sosial tentang kebersihan lingkungan."),
            NewsItem(R.drawable.img_12, "Community", "Bergabunglah dengan komunitas GoGreen dan jadilah bagian dari solusi."),
            NewsItem(R.drawable.img_13, "GoGreen", "Tukarkan GG Point jadi saldo e-wallet dan tukarkan hadiah menarik lainnya."),
        )

        newsPagerAdapter = NewsPagerAdapter(newsList)
        viewPager.adapter = newsPagerAdapter
    }

    override fun onResume() {
        super.onResume()
        val bottomNav = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNav.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}