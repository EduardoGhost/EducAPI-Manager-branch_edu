package br.ufpb.dcx.apps4society.educapimanager.view.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.adapter.ChallengeAdapter
import br.ufpb.dcx.apps4society.educapimanager.control.service.ChallengesService
import br.ufpb.dcx.apps4society.educapimanager.control.service.response.ChallengePageResponse
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Challenge
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChallengeFragment : Fragment() {

    private lateinit var layManager : LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipyRefreshLayout: SwipyRefreshLayout
    private lateinit var adapter: ChallengeAdapter
    private var mutableList : MutableList<Challenge> = mutableListOf()
    private var PAGE = 0
    private var LAST_PAGE = true
    private var TAG : String = "CreateFragmentFragmentName"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_challenge, container, false)

        swipyRefreshLayout = root.findViewById(R.id.swipyrefreshlayout_challenges)

        recyclerView = root.findViewById(R.id.challenge_recycle_view)
        layManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layManager
        recyclerView.setHasFixedSize(true)

        adapter = ChallengeAdapter(context, mutableList)
        recyclerView.adapter = adapter

        if (PAGE == 0 && mutableList.size == 0){
            getChallengesFromService(PAGE)
        }

        swipyRefreshLayout.setOnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.BOTTOM && !LAST_PAGE){
                PAGE++
                getChallengesFromService(PAGE)
            }else if (direction == SwipyRefreshLayoutDirection.TOP){
                PAGE = 0
                mutableList.clear()
                getChallengesFromService(PAGE)
            }else{
                Toast.makeText(context, "Os desafios disponíveis já estão sendo exibidos!", Toast.LENGTH_LONG).show()
                swipyRefreshLayout.isRefreshing = false
            }
        }

        return root
    }

    fun getChallengesFromService(page: Int){
        val call = RetrofitConfig.getPublicRetrofit().create(ChallengesService::class.java)
            .getChallengesWithPagination(page, null, 10)
        call.enqueue(object: Callback<ChallengePageResponse> {
            override fun onResponse(call: Call<ChallengePageResponse>, response: Response<ChallengePageResponse>) {
                response.body()?.let {

                    LAST_PAGE = it.isLast

                    mutableList.addAll(mutableList.size, it.content)
                    adapter.notifyItemRangeInserted(mutableList.size, mutableList.size)

                    swipyRefreshLayout.isRefreshing = false

                }
            }

            override fun onFailure(call: Call<ChallengePageResponse>, t: Throwable?) {
                swipyRefreshLayout.isRefreshing = false
            }
        })
    }

}