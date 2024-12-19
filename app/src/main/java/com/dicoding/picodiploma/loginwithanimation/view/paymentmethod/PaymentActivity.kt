package com.dicoding.picodiploma.loginwithanimation.view.paymentmethod

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityPaymentBinding
import com.dicoding.picodiploma.loginwithanimation.view.main.MainActivity

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val channelId = "payment_notifications"
    private val notificationId = 1
    private val requestCodePostNotifications = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val payButton: Button = findViewById(R.id.payButton)
        val paymentOptions: RadioGroup = findViewById(R.id.paymentOptions)

        payButton.setOnClickListener {
            val selectedOption = paymentOptions.checkedRadioButtonId
            if (selectedOption != -1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), requestCodePostNotifications)
                    } else {
                        showNotification("Payment Status", "Menunggu pembayaran...")
                        Handler(Looper.getMainLooper()).postDelayed({
                            showNotification("Payment Status", "Pembayaran berhasil")
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }, 3000)
                    }
                } else {
                    showNotification("Payment Status", "Menunggu pembayaran...")
                    Handler(Looper.getMainLooper()).postDelayed({
                        showNotification("Payment Status", "Pembayaran berhasil")
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }, 3000)
                }
            } else {
                showNotification("Payment Status", "Pilih metode pembayaran terlebih dahulu")
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Payment Notifications"
            val descriptionText = "Notifications for payment status"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification(title: String, message: String) {
        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.baseline_brightness_1_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, builder.build())
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePostNotifications) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                showNotification("Payment Status", "Menunggu pembayaran...")
                Handler(Looper.getMainLooper()).postDelayed({
                    showNotification("Payment Status", "Pembayaran berhasil")
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 3000)
            } else {
                showNotification("Payment Status", "Permission denied")
            }
        }
    }
}