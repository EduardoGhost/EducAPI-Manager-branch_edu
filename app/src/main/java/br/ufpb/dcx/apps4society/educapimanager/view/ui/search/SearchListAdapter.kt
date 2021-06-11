package br.ufpb.dcx.apps4society.educapimanager.view.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.ufpb.dcx.apps4society.educapimanager.R
import br.ufpb.dcx.apps4society.educapimanager.control.facade.CreateObjectFacade
import com.bumptech.glide.Glide
import com.bumptech.glide.load.EncodeStrategy
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.api.services.customsearch.model.Result

class SearchListAdapter (private var resultado : List<Result>, context:Context,fragment: Fragment,tag:String) : RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {

    private var fragmentContext : Context = context
    private var TAG : String = tag
    private var fragmento : Fragment = fragment

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.search_card_grid, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return resultado.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            loadImage(resultado[position].pagemap["cse_image"]?.get(0)?.get("src").toString(),holder.img)
        }
        catch(e :NullPointerException){
            Toast.makeText(fragmentContext,"Imagem Invalida, Tente escolher Outra",Toast.LENGTH_SHORT).show();
        }
        holder.itemView.setOnClickListener {
            if(TAG == "challenge") {
                CreateObjectFacade.instance.tempChallenge.imageUrl = resultado[position].pagemap["cse_image"]?.get(0)?.get("src").toString();
            }else {
                CreateObjectFacade.instance.tempContext.imageUrl = resultado[position].pagemap["cse_image"]?.get(0)?.get("src").toString();
            }
            fragmento.fragmentManager?.popBackStack()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img : ImageView = itemView.findViewById(R.id.grid_image)
    }

    private fun loadImage(imageUrl: String?, themeImageLeft: ImageView) {
        val diskCacheStrategy = object : DiskCacheStrategy() {
            override fun isDataCacheable(dataSource: com.bumptech.glide.load.DataSource?): Boolean {
                return true
            }

            override fun isResourceCacheable(isFromAlternateCacheKey: Boolean, dataSource: com.bumptech.glide.load.DataSource?, encodeStrategy: EncodeStrategy?): Boolean {
                return true
            }

            override fun decodeCachedResource(): Boolean {
                return true
            }

            override fun decodeCachedData(): Boolean {
                return true
            }

        }
        var erroImg : Int
        try {
            erroImg = Integer.parseInt(imageUrl.toString())
        } catch (e: NumberFormatException) {
            erroImg = R.drawable.no_image
        }

        Glide.with(fragmentContext)
            .load(imageUrl)
            .error(erroImg)
            .diskCacheStrategy(diskCacheStrategy)
            .into(themeImageLeft)
    }


}