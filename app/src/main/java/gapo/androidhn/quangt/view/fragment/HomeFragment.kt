package gapo.androidhn.quangt.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.view.adapter.SectionsPagerAdapter
import gapo.androidhn.quangt.viewmodel.HomeViewModel
import gapo.androidhn.quangt.viewmodel.PagerViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    companion object {
        fun newInstance(): Fragment {
            val fragment = HomeFragment()
            val argument = Bundle()
            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val sectionsPagerAdapter = activity?.let {
            SectionsPagerAdapter(it, childFragmentManager)
        }
        val viewPager: ViewPager? = activity?.findViewById(R.id.view_pager)
        viewPager?.adapter = sectionsPagerAdapter
        val tabs: TabLayout? = activity?.findViewById(R.id.tabs)
        tabs?.setupWithViewPager(viewPager)
    }
}
