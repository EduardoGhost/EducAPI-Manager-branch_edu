package br.ufpb.dcx.apps4society.educapimanager.view.ui.context

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.adapter.ContextAdapter
import br.ufpb.dcx.apps4society.educapimanager.control.service.response.ContextPageResponse
import br.ufpb.dcx.apps4society.educapimanager.helper.RetrofitConfig
import br.ufpb.dcx.apps4society.educapimanager.model.bean.Context
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayout
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContextFragment : Fragment() {

    private lateinit var layManager : LinearLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipyRefreshLayout: SwipyRefreshLayout
    private lateinit var adapter: ContextAdapter
    private var mutableList : MutableList<Context> = mutableListOf()
    private var TAG : String = "CreateContextFragmentName"
    private var PAGE = 0
    private var LAST_PAGE = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_context, container, false)

        swipyRefreshLayout = root.findViewById(R.id.swipyrefreshlayout)

        recyclerView = root.findViewById(R.id.context_recycle_view)
        layManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layManager
        recyclerView.setHasFixedSize(true)

        adapter = ContextAdapter(context, mutableList)
        recyclerView.adapter = adapter

        if (PAGE == 0 && mutableList.size == 0){
            getContextsFromService(PAGE)
        }

        swipyRefreshLayout.setOnRefreshListener { direction ->
            if (direction == SwipyRefreshLayoutDirection.BOTTOM && !LAST_PAGE){
                PAGE++
                getContextsFromService(PAGE)
            }else if (direction == SwipyRefreshLayoutDirection.TOP){
                PAGE = 0
                mutableList.clear()
                getContextsFromService(PAGE)
            }else{
                Toast.makeText(context, "Os contextos disponíveis já estão sendo exibidos!", Toast.LENGTH_LONG).show()
                swipyRefreshLayout.isRefreshing = false
            }
        }

        return root
    }

    fun getContextsFromService(page: Int){
        val call = RetrofitConfig.contextsService().getWithPagination(null, null, page, 5)
        call.enqueue(object: Callback<ContextPageResponse> {
            override fun onResponse(call: Call<ContextPageResponse>, response: Response<ContextPageResponse>) {
                response.body()?.let {
                    LAST_PAGE = it.isLast

                    mutableList.addAll(mutableList.size, it.content)
                    adapter.notifyItemRangeInserted(mutableList.size, mutableList.size)

                    swipyRefreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ContextPageResponse>, t: Throwable?) {
                swipyRefreshLayout.isRefreshing = false

            }
        })
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

}
