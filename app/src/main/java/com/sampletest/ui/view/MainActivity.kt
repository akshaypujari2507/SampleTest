package com.sampletest.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.sampletest.R
import com.sampletest.data.remote.api.ApiClient
import com.sampletest.di.Injection
import com.sampletest.ui.viewmodel.MainActivityViewModel
import com.sampletest.utils.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this, Injection.provideMainActivityViewModelFactory(this))
            .get(MainActivityViewModel::class.java)

        initView()
    }

    fun initView() {
        btn1.setOnClickListener {
            buttonOneClicked()
        }

        btn2.setOnClickListener {
            if (Util.isNetworkAvailable(this)) {
                progressBar.visibility = View.VISIBLE
                buttonTwoClicked()
            } else {
                toast("Connection error!")
            }
        }

        btn3.setOnClickListener {
            // call activity
            callListActivity()
        }
    }

    fun buttonOneClicked() {
        if (TextUtils.isEmpty(et_message.text.toString())) {
            toast("Please enter message!")
            return
        }
        progressBar.visibility = View.VISIBLE
        // need to save the message
        viewModel.setMessageLocal(et_message.text.toString(), Util.BUTTON_1)
        progressBar.visibility = View.GONE
        toast("message: ${et_message.text}")
        et_message.setText("")
    }

    fun buttonTwoClicked() {
        viewModel.getMessageRemote(Util.BUTTON_2)?.observe(this, Observer<String> {
            progressBar.visibility = View.GONE
            toast(it)
        })
    }

    private fun callListActivity() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }


    fun callResponse() {
        val api = ApiClient.api
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getResponse().execute().body()
                println("Akshay: $response")
            } catch (e: Exception) {
                println("Akshay Exception: $e")
            }
        }

    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}