package gapo.androidhn.quangt.view

import android.os.Bundle
import android.util.SparseArray
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import gapo.androidhn.quangt.R
import gapo.androidhn.quangt.view.fragment.DashboardFragment
import gapo.androidhn.quangt.view.fragment.HomeFragment
import gapo.androidhn.quangt.view.fragment.NotificationsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var savedStateSparseArray =
        SparseArray<Fragment.SavedState>()// taij sao anh laij dungf cais nayf
    private var currentSelectItemId = R.id.navigation_home

    companion object {
        const val SAVED_STATE_CONTAINER_KEY = "ContainerKey"
        const val SAVED_STATE_CURRENT_TAB_KEY = "CurrentTabKey"
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    swapFragments(item.itemId, getString(R.string.title_home))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    swapFragments(item.itemId, getString(R.string.title_dashboard))
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    swapFragments(item.itemId, getString(R.string.title_notifications))
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) {
            savedStateSparseArray =
                savedInstanceState.getSparseParcelableArray(SAVED_STATE_CONTAINER_KEY)!!
            currentSelectItemId = savedInstanceState.getInt(SAVED_STATE_CURRENT_TAB_KEY)
        }
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        swapFragments(currentSelectItemId, getString(R.string.title_home))
    }

    private fun swapFragments(@IdRes actionId: Int, key: String) {
        if (supportFragmentManager.findFragmentByTag(key) == null) {
            savedFragmentState(actionId)
            createFragment(key, actionId)
        }
    }

    private fun createFragment(key: String, actionId: Int) {
        val fragment = when (key) {
            getString(R.string.title_home) -> HomeFragment.newInstance()
            getString(R.string.title_dashboard) -> DashboardFragment.newInstance()
            getString(R.string.title_notifications) -> NotificationsFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
        fragment.setInitialSavedState(savedStateSparseArray[actionId])
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, key)
            .commit()
    }

    private fun savedFragmentState(actionId: Int) {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if (currentFragment != null) {
            savedStateSparseArray.put(
                currentSelectItemId,
                supportFragmentManager.saveFragmentInstanceState(currentFragment)
            )
        }
        currentSelectItemId = actionId
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSparseParcelableArray(SAVED_STATE_CONTAINER_KEY, savedStateSparseArray)
        outState.putInt(SAVED_STATE_CURRENT_TAB_KEY, currentSelectItemId)
    }

    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment != null && fragment.isVisible) {
                with(fragment.childFragmentManager) {
                    if (backStackEntryCount > 0) {
                        popBackStack()
                        return
                    }
                }
            }
        }
        super.onBackPressed()
    }
}
