package tailoredapps.applicationexample.patrickloh

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import tailoredapps.applicationexample.patrickloh.Download.JSONParser
import tailoredapps.applicationexample.patrickloh.RecyclerView.ViewAdapter
import tailoredapps.applicationexample.patrickloh.Models.RepoModel

class MainActivity : AppCompatActivity() {

    // Initializing an empty ArrayList to be filled with repos
    var repositories: MutableList<RepoModel> = ArrayList()
    val dummy: MutableList<RepoModel> = ArrayList()

    var page = 1
    var scrollPage = 0
    var isLoading = false
    var limit = 10

    lateinit var adapter: ViewAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var downloader: JSONParser
    lateinit var search: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creates a vertical Layout Manager
        layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager

        progressbar.visibility = View.INVISIBLE

        rv_list.addOnScrollListener(object : RecyclerView.OnScrollListener()
        {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                //if (dy > 0) {
                    val visibleItemCount = layoutManager.childCount
                    val pastVisibleItem = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val total = adapter.itemCount

                    if (!isLoading)
                    {
                        if ((visibleItemCount + pastVisibleItem) >= total) {
                            if(total%30 == 0)
                            {
                                scrollPage = 0
                                page++
                                val searchResult = search.text.toString() + "&page=" + page.toString()
                                AsyncTaskHandler().execute(searchResult)
                            }
                            else
                            {
                                scrollPage++
                                getPage()
                            }
                        }
                    }
                //}
                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    inner class AsyncTaskHandler:AsyncTask<String, Void, MutableList<RepoModel>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            isLoading = true
            progressbar.visibility = View.VISIBLE
        }

        override fun onPostExecute(result: MutableList<RepoModel>?) {
            super.onPostExecute(result)

            repositories = downloader.repositories
            getPage()
        }

        override fun doInBackground(vararg params: String?): MutableList<RepoModel> {
            downloader = JSONParser()
            val items = downloader.fetchJson(params[0].toString())
            return items
        }
    }

    fun doSearch(view: View) {
        search = findViewById(R.id.search)

        if (search.text.toString() != "") {
            val searchResult = search.text.toString() + "&page=" + page.toString()
            AsyncTaskHandler().execute(searchResult)
        }
        else {
            Toast.makeText(this, "Can't search for no repository", Toast.LENGTH_SHORT).show()
        }
    }

    fun getPage() {
        isLoading = true
        progressbar.visibility = View.VISIBLE

        val start = ((scrollPage) * limit) //+ 1
        val end = ((scrollPage +1) * limit) -1

        if(end<=repositories.size) {
            for(i in start..end)
            {
                if (repositories.size != 0)
                    dummy.add(repositories[i])
                else
                    Toast.makeText(this, "Something went wrong while downloading", Toast.LENGTH_SHORT).show()
            }
        }

        Handler().postDelayed({
            if(::adapter.isInitialized)
            {
                adapter.notifyDataSetChanged()
            }
            else
            {
                // Access the RecyclerView Adapter and load the data into it
                adapter = ViewAdapter(dummy, this)
                rv_list.adapter = adapter
            }

            isLoading = false
            progressbar.visibility = View.GONE

        }, 100)
    }
}
