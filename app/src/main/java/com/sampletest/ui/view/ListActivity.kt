package com.sampletest.ui.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.sampletest.R
import com.sampletest.data.local.entities.Message
import com.sampletest.di.Injection
import com.sampletest.ui.adapters.MessageAdapter
import com.sampletest.ui.interfaces.OnMessageClicked
import com.sampletest.ui.viewmodel.ListActivityViewModel
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity(), OnMessageClicked {

    private lateinit var viewModel: ListActivityViewModel

    private val GRID_COLUMNS_PORTRAIT = 1
    private val GRID_COLUMNS_LANDSCAPE = 2
    private lateinit var mGridLayoutManager: GridLayoutManager

    lateinit var mAdapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        viewModel = ViewModelProviders.of(this, Injection.provideListActivityViewModelFactory(this))
            .get(ListActivityViewModel::class.java)

        initView()
        initRecyclerView()

        GlobalScope.launch(Dispatchers.Main) {
            fetchMessages()
        }

    }

    fun initView() {

    }

    private fun initRecyclerView() {
        configureRecyclerAdapter(resources.configuration.orientation)

        mAdapter = MessageAdapter(this@ListActivity)
        recyclerView.adapter = mAdapter

    }

    private suspend fun fetchMessages() {

        viewModel.getAllEmployees()?.observe(this, Observer<List<Message>> {
            setUpAdapter(it)
        })
    }


    private fun setUpAdapter(messages: List<Message>) {
        if (messages.size > 0) {
            mAdapter.messages = messages
            recyclerView.visibility = View.VISIBLE
            tv_noRecord.visibility = View.GONE
        } else {
            recyclerView.visibility = View.GONE
            tv_noRecord.visibility = View.VISIBLE
            tv_noRecord.setText(resources.getString(R.string.noRecords))
        }
    }



    private fun configureRecyclerAdapter(orientation: Int) {
        val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT
        mGridLayoutManager = GridLayoutManager(
            this,
            if (isPortrait) GRID_COLUMNS_PORTRAIT else GRID_COLUMNS_LANDSCAPE
        )
        recyclerView.setLayoutManager(mGridLayoutManager)
    }

    fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onMessageClicked(message: Message) {
        toast(message.msg.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.all -> {
                mAdapter.filter.filter("")
                true
            }
            R.id.btn1 -> {
                mAdapter.filter.filter("1")
                true
            }
            R.id.btn2 -> {
                mAdapter.filter.filter("2")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}