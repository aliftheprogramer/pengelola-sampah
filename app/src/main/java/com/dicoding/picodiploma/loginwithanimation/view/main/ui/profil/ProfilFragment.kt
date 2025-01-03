package com.dicoding.picodiploma.loginwithanimation.view.main.ui.profil

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentProfilBinding
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class ProfilFragment : Fragment() {

    private var _binding: FragmentProfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.buttonUbahProfil.setOnClickListener {
            val intent = Intent(activity, UbahProfilActivity::class.java)
            startActivity(intent)
        }

        binding.menuPanduan.setOnClickListener {
            val intent = Intent(activity, PanduanActivity::class.java)
            startActivity(intent)
        }

        binding.menuMediaSosial.setOnClickListener {
            showSocialMediaDialog()
        }
    }

    private fun showSocialMediaDialog() {
        val socialMediaOptions = arrayOf("Instagram", "Facebook", "Twitter")
        val socialMediaLinks = arrayOf(
            "https://www.instagram.com/gogreen_offcial/profilecard/?igsh=ZnVlMGk0amd0ZG91",
            "https://www.facebook.com/yourprofile",
            "https://www.twitter.com/yourprofile"
        )
        val socialMediaIcons = arrayOf(
            R.drawable.img_14,  // Ganti dengan ikon Instagram Anda
            R.drawable.img_15,   // Ganti dengan ikon Facebook Anda
            R.drawable.img_16     // Ganti dengan ikon Twitter Anda
        )

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Media Sosial")

        val adapter = object : ArrayAdapter<String>(requireContext(), R.layout.dialog_social_media_item, socialMediaOptions) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.dialog_social_media_item, parent, false)
                val icon = view.findViewById<ImageView>(R.id.icon)
                val name = view.findViewById<TextView>(R.id.name)
                icon.setImageResource(socialMediaIcons[position])
                name.text = socialMediaOptions[position]
                return view
            }
        }

        builder.setAdapter(adapter) { _, which ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(socialMediaLinks[which]))
            startActivity(intent)
        }
        builder.show()
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