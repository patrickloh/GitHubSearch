package tailoredapps.applicationexample.patrickloh.RecyclerView

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import tailoredapps.applicationexample.patrickloh.DetailsActivity
import tailoredapps.applicationexample.patrickloh.Models.RepoModel
import tailoredapps.applicationexample.patrickloh.R

class ViewAdapter(items : MutableList<RepoModel>, ViewContext: Context) : RecyclerView.Adapter<ViewHolder>()
{
    private val repositories = items
    private val context = ViewContext

    // creates the viewholder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    // get and bind item
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = repositories[position]
        holder.bind(currentItem, context)
    }
}

class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
{
    private var repo: RepoModel? = null
    private var context: Context? = null

    // setup clicklistener to the current item
    init {
        view.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(ITEM_KEY, repo)
            view.context.startActivity(intent)
        }
    }

    // bind data to the current item
    fun bind(repo: RepoModel, context: Context)
    {
        this.repo = repo
        this.context = context
        view.tv_title?.text = repo.name
        view.tv_desc?.text = repo.description
        view.tv_date?.text = repo.created_at
    }

    // key for serialization
    companion object {
        private const val ITEM_KEY = "item"
    }
}