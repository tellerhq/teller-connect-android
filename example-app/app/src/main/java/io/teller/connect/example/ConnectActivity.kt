package io.teller.connect.example

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commitNow
import io.teller.connect.sdk.Config
import io.teller.connect.sdk.ConnectFragment
import io.teller.connect.sdk.ConnectListener
import io.teller.connect.sdk.Error
import io.teller.connect.sdk.Payee
import io.teller.connect.sdk.Payment
import io.teller.connect.sdk.Registration
import java.net.URL

class ConnectActivity : FragmentActivity(), ConnectListener {
    companion object {
        const val EXTRA_CONFIG = "EXTRA_CONFIG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        val config = intent.getParcelableExtra<Config>(EXTRA_CONFIG)!!
        startTellerConnect(config)
    }

    private fun startTellerConnect(
        config: Config
    ) {
        val args = ConnectFragment.buildArgs(config)

        supportFragmentManager.commitNow {
            setReorderingAllowed(true)
            val fragment = ConnectFragment()
            fragment.arguments = args
            add(R.id.connectFragmentContainer, fragment)
        }
    }

    override fun onInit() {
    }

    override fun onExit() {
        finish()
    }

    override fun onSuccess(registration: Registration) {
        finish()
    }

    override fun onSuccess(payment: Payment) {
        finish()
    }

    override fun onSuccess(payee: Payee) {
        finish()
    }

    override fun onEvent(name: String, data: Map<String, Any>) {
    }

    override fun onFailure(error: Error) {
        finish()
    }
}
