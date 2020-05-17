package gapo.androidhn.quangt.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.utils.ARG_DOCUMENT_ID
import gapo.androidhn.quangt.utils.LoadingState
import gapo.androidhn.quangt.view.adapter.DetailAdapter
import gapo.androidhn.quangt.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModel<DetailViewModel>()
    private lateinit var detailAdapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ivBack.setOnClickListener {
            onBackPressed()
        }
        initRecyclerView()
        swipeRefresh.setOnRefreshListener {
            detailViewModel.getFeedDetail()
        }
        detailViewModel.documentId = intent.getStringExtra(ARG_DOCUMENT_ID)
        detailViewModel.getFeedDetail()
        detailViewModel.data.observe(this, Observer {
            detailAdapter.update(it)
        })
        detailViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    pbLoading.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                    Toast.makeText(this, it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> pbLoading.visibility = View.VISIBLE
                LoadingState.Status.SUCCESS -> {
                    pbLoading.visibility = View.GONE
                    swipeRefresh.isRefreshing = false
                }
            }
        })
    }

    private fun initRecyclerView() {
        detailAdapter = DetailAdapter { item, view ->
        }
        rvItems.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val decorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(this, R.drawable.item_divider_simple)!!
        )
        rvItems.addItemDecoration(decorator)
        rvItems.adapter = detailAdapter
    }
}