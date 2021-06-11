package br.ufpb.dcx.apps4society.educapimanager.view.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.view.NavDrawerActivity
import br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge.CreateChallengeActivity
import br.ufpb.dcx.apps4society.educapimanager.view.ui.context.CreateContextActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment(){

    private var TAG : String = "HomeFragment"
    private lateinit var tabMenuAdapter : TabMenuAdapter
    private lateinit var viewPagerTabMenu : ViewPager
    private lateinit var tabLayoutTabMenu : TabLayout
    private lateinit var fab : FloatingActionButton

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        fab = root.findViewById(R.id.fab)

        tabMenuAdapter = TabMenuAdapter(
            childFragmentManager,
            FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        );

        fab.setOnClickListener{ view ->
            val intent = Intent(context, CreateContextActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    fun fillTabMenu(view: View) {
        viewPagerTabMenu = view.findViewById(R.id.vpTabMenu)
        viewPagerTabMenu.adapter = tabMenuAdapter
        tabLayoutTabMenu = view.findViewById(R.id.tlTabMenu)
        tabLayoutTabMenu.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                if (p0?.position == 0){
                    fab.setOnClickListener{ view ->
                        val intent = Intent(context, CreateContextActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    fab.setOnClickListener{ view ->
                        val intent = Intent(context, CreateChallengeActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillTabMenu(view)
    }
}