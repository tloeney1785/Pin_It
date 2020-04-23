package com.example.snrproject

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.api.load
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.activity_main.*
import android.os.Handler
import android.Manifest
import android.location.LocationListener
import android.location.LocationManager
import android.location.Location
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.provider.Settings

private const val PERMISSION_REQUEST = 10

class MainActivity : AppCompatActivity() {
    lateinit var locationManager: LocationManager
    private var hasGps = false
    private var hasNetwork = false
    private var locationGps: Location? = null
    private var locationNetwork: Location? = null
    //internal var dbHelper = DatabaseHelper(this)
    internal var dbImages = ImageDatabase(this)
    private var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)

    /*
     * onCreate method.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val username = intent.getStringExtra("username")

        // constantly update image preview by checking urlTxt
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                imagePreview.load(urlTxt.text.toString())
                handler.postDelayed(this, 500) // interval time for refresh
            }
        })
        // handle location
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!checkPermission(permissions)) {
                requestPermissions(permissions, PERMISSION_REQUEST)
            }
        }

        val locStr = getLocation()
        handleInserts(username, locStr)
        handleHome(username)
        // handleUpdates()
        // handleDeletes()
        // handleViewing()
        LogoutBtn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() : String {
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (hasGps || hasNetwork) {
            if (hasGps) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationGps = location
                            locationTxt.hint = "Current Location:\nLatitude : " + locationGps!!.latitude + "\nLongitude : " +  locationGps!!.longitude
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String?) {}
                    override fun onProviderDisabled(provider: String?) {}
                })

                val localGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (localGpsLocation != null)
                    locationGps = localGpsLocation
            }
            if (hasNetwork) {
                Log.d("CodeAndroidLocation", "hasGps")
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0F, object : LocationListener {
                    override fun onLocationChanged(location: Location?) {
                        if (location != null) {
                            locationNetwork = location
                        }
                    }
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
                    override fun onProviderEnabled(provider: String?) {}
                    override fun onProviderDisabled(provider: String?) {}
                })

                val localNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                if (localNetworkLocation != null)
                    locationNetwork = localNetworkLocation
            }
            return locationGps!!.latitude.toString() + ", " + locationGps!!.longitude
        } else {
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            return ""
        }
    }

    private fun showToast(text: String){
        Toast.makeText(this@MainActivity, text, Toast.LENGTH_LONG).show()
    }

    /*
     * Alert dialog with data dialog
     */
    private fun showDialog(title : String,Message : String){
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(Message)
        builder.show()
    }

    /*
     * Clear editable text
     */
    private fun clearEditTexts(){
        locationTxt.setText("")
        urlTxt.setText("")
    }

    /*
     * HOME PAGE button clicked
     */
    private fun handleHome(username:String) {
        HomeBtn.setOnClickListener{
            val intent = Intent(this, ListPics::class.java)
            intent.putExtra("username",username)
            startActivity(intent)
        }
    }

    /*
     * ENTER button clicked
     */
    private fun handleInserts(username:String, location:String) {
        insertBtn.setOnClickListener {
            try {
                dbImages.insertData(username,location, urlTxt.text.toString())
                clearEditTexts()
            } catch (e: Exception) {
                e.printStackTrace()
                showToast(e.message.toString())
            }
        }
    }

    private fun checkPermission(permissionArray: Array<String>): Boolean {
        var allSuccess = true
        for (i in permissionArray.indices) {
            if (checkCallingOrSelfPermission(permissionArray[i]) == PackageManager.PERMISSION_DENIED)
                allSuccess = false
        }
        return allSuccess
    }

    /*
     * UPDATE button clicked
     */
    /* private fun handleUpdates() {
         updateBtn.setOnClickListener {
             try {
                 val isUpdate = dbHelper.updateData(
                     idTxt.text.toString(),
                     userTxt.text.toString(),
                     passTxt.text.toString(),
                     locationTxt.text.toString(),
                     urlTxt.text.toString())
                 if (isUpdate)
                     showToast("Data Updated Successfully")
                 else
                     showToast("Data Not Updated")
             } catch (e: Exception){
                 e.printStackTrace()
                 showToast(e.message.toString())
             }
         }
     }



     /*
      * VIEW button clicked
      */
     private fun handleViewing() {
         viewBtn.setOnClickListener(
             View.OnClickListener {
                 val res = dbHelper.allData
                 if (res.isEmpty()) {
                     showDialog("Error", "No Data Found")
                     return@OnClickListener
                 }

                 val buffer = StringBuffer()
                 for (i in res.indices) {
                     buffer.append("ID :" + res[i].userID + "\n")
                     buffer.append("USER :" + res[i].userName + "\n")
                     buffer.append("PASS :" + res[i].userPass + "\n")
                     buffer.append("LOCATION :" + res[i].userLocation + "\n")
                     buffer.append("URL :" + res[i].userURL + "\n\n")
                 }
                 showDialog("Data Listing", buffer.toString())
             }
         )
     }*/
}

