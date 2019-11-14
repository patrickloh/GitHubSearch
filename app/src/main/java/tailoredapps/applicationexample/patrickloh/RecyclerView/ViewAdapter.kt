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
    val repositories = items
    val context = ViewContext

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val clickedItem = repositories[position]
        holder?.bind(clickedItem, context)
    }
}

class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)
{
    private var repo: RepoModel? = null
    private var context: Context? = null

    init {
        view.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(ITEM_KEY, repo)
            view.context.startActivity(intent)
        }
    }

    fun bind(repo: RepoModel, context: Context)
    {
        this.repo = repo
        this.context = context
        view.tv_title?.text = repo.name
        view.tv_desc?.text = repo.description
        view.tv_date?.text = repo.created_at
    }

    companion object {
        private val ITEM_KEY = "item"
    }
}