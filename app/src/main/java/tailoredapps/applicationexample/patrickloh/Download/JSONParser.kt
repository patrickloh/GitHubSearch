package tailoredapps.applicationexample.patrickloh.Download

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.*
import tailoredapps.applicationexample.patrickloh.Models.RepoModel
import java.io.IOException
import java.io.Serializable
import java.time.LocalTime

class JSONParser
{
    var repositories: MutableList<RepoModel> = ArrayList()
    private val TAG = "JSONParser"

    fun fetchJson(repo: String): MutableList<RepoModel>
    {
        val repositorySearchURL = "https://api.github.com/search/repositories?q=" + repo + "&sort=stars&order=desc"
        var isEmpty = false

        val request = Request.Builder().url(repositorySearchURL).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "Failed to execute request")
            }

            // build the response and add the items to an arraylist
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val jsonResponse =  gson.fromJson(body, JSONResponse::class.java)

                if(body?.count() == 55) {
                    isEmpty = true
                }

                Log.d(TAG, "Successfully downloaded from the url")
                repositories = jsonResponse.items
            }
        })

        // wait until download is finished (not a fancy way)
        while (repositories.size == 0)
        {
            if(repositories.size != 0 || isEmpty)
                break
        }
        return repositories
    }
}

// classes for building the Model
class JSONResponse(val items: MutableList<RepoModel>)
class Owner(val login: String, val avatar_url: String, val url: String, val type: String) : Serializable