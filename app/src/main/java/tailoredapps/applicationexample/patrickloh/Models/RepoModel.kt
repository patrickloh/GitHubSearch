package tailoredapps.applicationexample.patrickloh.Models

import tailoredapps.applicationexample.patrickloh.Download.Owner
import java.io.Serializable

class RepoModel(repoName: String, ownerClass: Owner, ownerURL: String, repoURL: String, desc: String, date: String, codeLanguage: String, rating: Double) : Serializable {
    val name: String = repoName
    val owner: Owner = ownerClass
    val url: String = ownerURL
    val html_url: String = repoURL
    val description: String = desc
    val created_at: String = date
    val language: String = codeLanguage
    val score: Double = rating
}

