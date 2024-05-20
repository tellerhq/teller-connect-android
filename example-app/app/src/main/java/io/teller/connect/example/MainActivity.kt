package io.teller.connect.example

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import io.teller.connect.sdk.*
import timber.log.Timber

class MainActivity : FragmentActivity(), ConnectListener {

    companion object {
        val config = Config(
            appId = "YOUR-APPLICATION-ID",
            environment = Environment.SANDBOX,
            selectAccount = SelectAccount.SINGLE,
            products = listOf(Product.IDENTITY),
            debug = false
        )
    }

    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentContainer = findViewById(R.id.fragmentContainer)
        findViewById<View>(R.id.connectFragmentButton).setOnClickListener {
            startTellerConnectFragment(config)
        }

        findViewById<View>(R.id.connectActivityButton).setOnClickListener {
            startTellerConnectActivity(config)
        }
    }

    private fun showSuccess(registration: Registration?) {
        Timber.i("token: ${registration?.accessToken}")
        val message =
            "\uD83D\uDCB8 Success! \uD83D\uDCB8\naccessToken: ${registration?.accessToken}"
        Snackbar.make(fragmentContainer, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showSuccess(payment: Payment?) {
        val message =
            "\uD83D\uDCB8 Success! \uD83D\uDCB8\npayment: ${payment?.id}"
        Snackbar.make(fragmentContainer, message, Snackbar.LENGTH_LONG).show()
    }

    private fun showSuccess(payee: Payee?) {
        val message =
            "\uD83D\uDCB8 Success! \uD83D\uDCB8\npayee: ${payee?.id}"
        Snackbar.make(fragmentContainer, message, Snackbar.LENGTH_LONG).show()
    }

    private fun handleError(error: Error?) {
        if (error != null) {
            Snackbar.make(fragmentContainer, "Error: ${error.message}", Snackbar.LENGTH_LONG)
                .show()
        } else {
            Snackbar.make(fragmentContainer, "Failure", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun startTellerConnectFragment(config: Config) {
        val args = ConnectFragment.buildArgs(config)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack("TellerConnect")
            add<ConnectFragment>(R.id.fragmentContainer, args = args)
        }
    }

    private fun startTellerConnectActivity(config: Config) {
        val intent = Intent(this, ConnectActivity::class.java)
        intent.putExtra(ConnectActivity.EXTRA_CONFIG, config)
        startActivity(intent)
    }

    override fun onInit() {
        Timber.d("Initialized Teller Connect")
    }

    override fun onExit() {
        handleError(null)
        removeTellerConnectFragment()
    }

    override fun onSuccess(registration: Registration) {
        showSuccess(registration)
        removeTellerConnectFragment()
    }

    override fun onSuccess(payment: Payment) {
        showSuccess(payment)
        removeTellerConnectFragment()
    }

    override fun onSuccess(payee: Payee) {
        showSuccess(payee)
        removeTellerConnectFragment()
    }

    override fun onEvent(name: String, data: Map<String, Any>) {
        Timber.d("$name: $data")
    }

    override fun onFailure(error: Error) {
        handleError(error)
        removeTellerConnectFragment()
    }

    private fun removeTellerConnectFragment() {
        supportFragmentManager.popBackStack()
    }

    @Deprecated("Deprecated by Android")
    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            removeTellerConnectFragment()
        } else {
            super.onBackPressed()
        }
    }
}
