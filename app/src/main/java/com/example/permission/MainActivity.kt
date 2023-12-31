package com.example.permission

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.permission.databinding.ActivityMainBinding
import java.security.Permission

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (isCallPhonePermissionAvailable()){
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
            binding.btn.isVisible = true
        }else{
            checkPermission()
        }

        binding.btn.setOnClickListener {
            callPhone()
        }
    }

    private fun checkPermission(){
        val list = mutableListOf<String>()

        if (!callPermission()){

            list.add(android.Manifest.permission.CALL_PHONE)
        }
        if (!calendarPermission()){
            list.add(android.Manifest.permission.READ_CALENDAR)
        }
        if (!readContactPermission()){
            list.add(android.Manifest.permission.READ_CONTACTS)
        }
        if (list.isNotEmpty()){
            ActivityCompat.requestPermissions(this,list.toTypedArray(), 100)
        }
    }

    private fun readContactPermission() = ActivityCompat.checkSelfPermission(this,
        android.Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED

    private fun calendarPermission() = ActivityCompat.checkSelfPermission(this,
    android.Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED

    private fun callPermission() = ActivityCompat.checkSelfPermission(this,
    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun isCallPhonePermissionAvailable() = ContextCompat.checkSelfPermission(this,
    android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    private fun callPhone(){
        val phone = binding.editText.text.toString().trim()
        val phoneNumber = Uri.parse("tel:+$phone")
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = phoneNumber
        startActivity(intent)
    }

//    private fun requestCallPhonePermission2(){
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//            android.Manifest.permission.CALL_PHONE)){
//            AlertDialog.Builder(this).apply {
//                setTitle("Permission")
//                setMessage("You should allow permission !!")
//                setPositiveButton("Ok"){di, _->
//                    requestPer()
//                    di.dismiss()
//                }
//            }.create().show()
//        }else{
//            requestPer()
//        }
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                binding.btn.isVisible = true
            }else{
                Toast.makeText(this, "Ruxsat ber", Toast.LENGTH_SHORT).show()
                requestPer()
            }
        }
    }

    private fun requestPer(){
        ActivityCompat.requestPermissions(
            this, arrayOf(android.Manifest.permission.CALL_PHONE),
            100
        )
    }

}