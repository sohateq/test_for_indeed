package com.akameko.testforindeed.view.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.akameko.testforindeed.R
import com.akameko.testforindeed.barcodegooglesample.BarcodeCaptureActivity
import com.akameko.testforindeed.view.main.MainActivity
import com.an.biometric.BiometricCallback
import com.an.biometric.BiometricManager.BiometricBuilder
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode


class LoginActivity : AppCompatActivity(), BiometricCallback {

    private lateinit var editTextLogin: EditText
    private lateinit var editTextPass: EditText

    private lateinit var buttonSignIn: Button
    private lateinit var buttonSignUp: Button
    private lateinit var buttonFingerprint: Button

    private lateinit var sharedPreferences: SharedPreferences



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initKeystore()
        initViews()

    }

    private fun initKeystore() {
        //keystore (EncryptedSharedPreferences)
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        val masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)

        sharedPreferences = EncryptedSharedPreferences
                .create(
                        "not_passwords",
                        masterKeyAlias,
                        this,
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                )
    }

    private fun initViews() {
        editTextLogin = findViewById(R.id.edit_text_login)
        editTextPass = findViewById(R.id.edit_text_password)


        buttonSignIn = findViewById(R.id.button_login)
        buttonSignIn.setOnClickListener {
            val correctPass = sharedPreferences.getString(editTextLogin.text.toString(), null).toString()
            if (editTextPass.text.toString() == correctPass) {
                //Toast.makeText(this, "Lucky", Toast.LENGTH_LONG).show()
                launchMainActivity()
            } else {
                //Toast.makeText(this, "Неверный пароль или логин!", Toast.LENGTH_LONG).show()
                showStatus("Неверный пароль или логин!")
            }
        }

        buttonSignUp = findViewById(R.id.button_sign_up)
        buttonSignUp.setOnClickListener {
            // launch barcode activity.
            val intent = Intent(this, BarcodeCaptureActivity::class.java)
            intent.putExtra(BarcodeCaptureActivity.AutoFocus, true)
            startActivityForResult(intent, 0)
        }

        buttonFingerprint = findViewById(R.id.button_fingerprint)
        buttonFingerprint.setOnClickListener {
            displayBiometricPrompt()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode: Barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject)
                    signUp(barcode)
                            //Toast.makeText(this, "Sign up succesful", Toast.LENGTH_LONG).show()

                    Log.d("123", "Barcode read: " + barcode.displayValue)

                } else {
                    showStatus("Регистрация не удалась")
                    //Toast.makeText(this, "Sign up fail", Toast.LENGTH_LONG).show()
                    Log.d("123", "No barcode captured, intent data is null")
                }
            } else {
                showStatus("Регистрация не удалась")
                        //Toast.makeText(this, "Sign up fail", Toast.LENGTH_LONG).show()
                Log.d("123", "No barcode captured, result code is not successful")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun signUp(barcode: Barcode) {
        val base64 = String(Base64.decode(barcode.displayValue, Base64.DEFAULT))

        var s = base64
        s = s.replace("super://secure/page?login=", "")
        s = s.replace("&password=", " ")
        val login = s.split(" ").toTypedArray()[0]
        val pass = s.split(" ").toTypedArray()[1]

        //сохранение в keystore (EncryptedSharedPreferences)
        val sharedPrefsEditor = sharedPreferences.edit()
        sharedPrefsEditor.putString(login, pass).apply()
        showStatus("Sign up succesful, you can log in")
    }

    private fun launchMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showStatus(status: String){
        val textViewStatus: TextView = findViewById(R.id.text_view_status)
        if(textViewStatus.visibility == View.INVISIBLE){
            textViewStatus.visibility = View.VISIBLE
        }
        textViewStatus.text = status
    }


    private fun displayBiometricPrompt() {
        //var mBiometricManager: BiometricManager? = null

        val mBiometricManager = BiometricBuilder(this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build()


        //start authentication
        mBiometricManager.authenticate(this)
    }

    override fun onSdkVersionNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(applicationContext, getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
        //        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(applicationContext, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()

    }

    override fun onAuthenticationSuccessful() {
        //enter
        Toast.makeText(applicationContext, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
        launchMainActivity()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
        //        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        //        Toast.makeText(getApplicationContext(), errString, Toast.LENGTH_LONG).show();
    }
}
