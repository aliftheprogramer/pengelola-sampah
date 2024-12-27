package com.dicoding.picodiploma.loginwithanimation.view.main.ui.jemputsampah

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentJemputSampahBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import java.util.Calendar

class JemputSampahFragment : Fragment() {

    private var _binding: FragmentJemputSampahBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JemputSampahViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJemputSampahBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNav = requireActivity().findViewById<View>(R.id.nav_view)
        bottomNav.visibility = View.GONE

        binding.inputTanggal.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                binding.inputTanggal.setText(selectedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        binding.btnCheckout.setOnClickListener {
            val nama = binding.inputNama.text.toString()
            val kategori = binding.spKategori.selectedItem.toString()
            val berat = binding.inputBerat.text.toString().toDoubleOrNull() ?: 0.0
            val tanggal = binding.inputTanggal.text.toString()
            val alamat = binding.inputAlamat.text.toString()
            val catatan = binding.inputTambahan.text.toString()
            val paymentMethod = binding.spPaymentMethod.selectedItem.toString()

            if (nama.isNotEmpty() && kategori.isNotEmpty() && berat > 0 && tanggal.isNotEmpty() && alamat.isNotEmpty()) {
                val jemputSampah = JemputSampah(
                    nama = nama,
                    kategori = kategori,
                    berat = berat,
                    tanggal = tanggal,
                    alamat = alamat,
                    catatan = catatan,
                    new_column = "default_value", // Ensure this field is not null
                    paymentMethod = paymentMethod
                )
                viewModel.saveJemputSampah(jemputSampah)
                Toast.makeText(requireContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(requireContext(), "Mohon isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.navigation_home)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}