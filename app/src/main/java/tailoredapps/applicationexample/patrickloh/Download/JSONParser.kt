package tailoredapps.applicationexample.patrickloh.Download

import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import okhttp3.*
import okhttp3.internal.wait
import tailoredapps.applicationexample.patrickloh.Models.RepoModel
import java.io.IOException
import java.io.Serializable

class JSONParser
{
    var repositories: MutableList<RepoModel> = ArrayList()

    fun fetchJson(repo: String): MutableList<RepoModel>
    {
        val repository_search_url = "https://api.github.com/search/repositories?q=" + repo + "&sort=stars&order=desc"

        val request = Request.Builder().url(repository_search_url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                val gson = GsonBuilder().create()
                val jsonResponse =  gson.fromJson(body, JSONResponse::class.java)
                repositories = jsonResponse.items

            }
        })
        while (repositories.size == 0)
        {
            if(repositories.size != 0)
                break
        }
        return repositories
    }
}

class JSONResponse(val items: MutableList<RepoModel>)
class Owner(val login: String, val avatar_url: String, val url: String, val type: String) : Serializable