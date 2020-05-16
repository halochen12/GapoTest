package gapo.androidhn.quangt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.utils.LoadingState
import gapo.androidhn.quangt.view.adapter.FeedAdapter
import gapo.androidhn.quangt.viewmodel.PagerViewModel
import kotlinx.android.synthetic.main.fragment_home_pager.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A placeholder fragment containing a simple view.
 */
class HomePagerFragment : Fragment() {

    private val pageViewModel by viewModel<PagerViewModel>()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel.setIndex(arguments?.getInt(ARG_CATEGORY) ?: 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        feedAdapter = FeedAdapter { feed, view ->
            gotoDetail(feed.document_id)
        }
        rvFeeds.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val decorator = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        decorator.setDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.item_divider_simple)!!
        )
        rvFeeds.addItemDecoration(decorator)
        rvFeeds.adapter = feedAdapter

        pageViewModel.loadingState.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    pbLoading.visibility = View.GONE
                    Toast.makeText(context, it.msg, Toast.LENGTH_SHORT).show()
                }
                LoadingState.Status.RUNNING -> pbLoading.visibility = View.VISIBLE
                LoadingState.Status.SUCCESS -> pbLoading.visibility = View.GONE
            }
        })
        pageViewModel.data.observe(viewLifecycleOwner, Observer {
            feedAdapter.update(it)
        })
    }

    private fun gotoDetail(documentId: String) {

    }

    companion object {
        private const val ARG_CATEGORY = "arg_category"

        @JvmStatic
        fun newInstance(category: Int): HomePagerFragment {
            return HomePagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CATEGORY, category)
                }
            }
        }
    }
}