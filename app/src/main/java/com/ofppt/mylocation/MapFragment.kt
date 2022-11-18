package com.ofppt.mylocation

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task


class MapFragment : Fragment()    {



    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var v : View? = null

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @SuppressLint("MissingPermission")
        override fun onLocationResult(locationResult: LocationResult) {
            val location = locationResult.lastLocation
            if (location != null) {

                var latitude = location.latitude.toString()
                var longitude = location.longitude.toString()

                val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?

                mapFragment?.getMapAsync(OnMapReadyCallback { map: GoogleMap ->

                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL)
                    map.setMyLocationEnabled(false)
                    map.getUiSettings().setZoomControlsEnabled(true)
                    map.getUiSettings().setMyLocationButtonEnabled(true)
                    map.getUiSettings().setCompassEnabled(true)
                    map.getUiSettings().setRotateGesturesEnabled(true)
                    map.getUiSettings().setZoomGesturesEnabled(true)

                    val cameraPosition = CameraPosition.Builder()
                        .target(LatLng(latitude.toDouble(), longitude.toDouble())).zoom(16.0f)
                        .build()

                        map.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                cameraPosition
                            )
                        )

                })
            }
        }


    }

    override fun onResume() {
        super.onResume()



    }
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient!!.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()!!
            )

    }

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.map, container, false)


        fusedLocationClient =  LocationServices.getFusedLocationProviderClient(
            requireActivity()
        )
        fusedLocationClient!!.lastLocation.addOnCompleteListener { task: Task<Location?> ->
            val location = task.result
            if (location == null) {
                 requestNewLocationData()
            } else {
                val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?

                mapFragment?.getMapAsync(OnMapReadyCallback { map: GoogleMap ->

                        map.setMapType(GoogleMap.MAP_TYPE_NORMAL)
                        map.setMyLocationEnabled(false)
                        map.getUiSettings().setZoomControlsEnabled(true)
                        map.getUiSettings().setMyLocationButtonEnabled(true)
                        map.getUiSettings().setCompassEnabled(true)
                        map.getUiSettings().setRotateGesturesEnabled(true)
                        map.getUiSettings().setZoomGesturesEnabled(true)

                        val cameraPosition = CameraPosition.Builder()
                            .target(LatLng(location.latitude.toDouble(),location. longitude.toDouble())).zoom(16.0f)
                            .build()

                        map.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                cameraPosition
                            )
                        )

                    map.addMarker(
                        MarkerOptions()
                            .position(LatLng(location.latitude, location.longitude))
                            .title("Marker is here !")
                    )

                })


            }
        }
        return v
    }

}