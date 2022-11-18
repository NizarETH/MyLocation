package com.ofppt.mylocation
import android.Manifest;

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        findViewById<View>(R.id.open_map).setOnClickListener( OnClickListener
        {
            Dexter.withContext(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ).withListener(object : MultiplePermissionsListener
            {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    Toast.makeText(this@MainActivity," Permission checked",Toast.LENGTH_LONG).show()

                    Dexter.withContext(this@MainActivity).withPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        .withListener(object : PermissionListener{
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                supportFragmentManager.beginTransaction().replace(R.id.frame, MapFragment(),"MapFragment").addToBackStack("MapFragment").commit()

                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                               p0?.isPermanentlyDenied
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: PermissionRequest?,
                                p1: PermissionToken?
                            ) {
                                p1?.continuePermissionRequest()
                            }

                        }).check()



                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }

            }

            ).check()

        })



    }
}
