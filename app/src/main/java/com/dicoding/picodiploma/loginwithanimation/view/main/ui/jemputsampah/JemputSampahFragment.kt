package com.dicoding.picodiploma.loginwithanimation.view.main.ui.jemputsampah

import android.Manifest
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.Notification
import com.dicoding.picodiploma.loginwithanimation.data.database.datasampah.JemputSampah
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentJemputSampahBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class JemputSampahFragment : Fragment() {

    private var _binding: FragmentJemputSampahBinding? = null
    private val binding get() = _binding!!
    private val viewModel: JemputSampahViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }

    companion object {
        private const val REQUEST_CODE_POST_NOTIFICATIONS = 1
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
                    new_column = "default_value",
                    paymentMethod = paymentMethod
                )
                viewModel.saveJemputSampah(jemputSampah)
                Toast.makeText(requireContext(), "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                requestNotificationPermission()
                findNavController().navigate(R.id.navigation_home)
            } else {
                Toast.makeText(requireContext(), "Mohon isi semua data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }

        val toolbar: Toolbar = view.findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_POST_NOTIFICATIONS)
        } else {
            showNotificationsWithDelay()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showNotificationsWithDelay()
            } else {
                Toast.makeText(requireContext(), "Notification permissions are not enabled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showNotification(message: String) {
        val notificationId = 1
        val builder = NotificationCompat.Builder(requireContext(), "default")
            .setSmallIcon(R.drawable.ic_notification_icon)
            .setContentTitle("Notifikasi Jemput Sampah")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            Log.d("JemputSampahFragment", "Notifications are enabled")
            with(NotificationManagerCompat.from(requireContext())) {
                notify(notificationId, builder.build())
                Log.d("JemputSampahFragment", "Notification sent: $message")
            }
        } else {
            Log.d("JemputSampahFragment", "Notification permissions are not enabled")
            Toast.makeText(requireContext(), "Notification permissions are not enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveNotification(title: String, body: String) {
        val dateTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val notification = Notification(id = 0, title = title, body = body, dateTime = dateTime)
        viewModel.saveNotification(notification)
    }

    private fun showNotificationsWithDelay() {
        saveNotification("Sampah sedang dijemput", "Petugas sedang dalam perjalanan untuk menjemput sampah Anda.")
        Handler(Looper.getMainLooper()).postDelayed({
            saveNotification("Petugas telah sampai di lokasi Anda", "Petugas telah tiba di lokasi Anda untuk menjemput sampah.")
        }, 2000)
    }
}