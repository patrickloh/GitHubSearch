package tailoredapps.applicationexample.patrickloh

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import tailoredapps.applicationexample.patrickloh.Models.RepoModel

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        if (!intent.hasExtra(ITEM_KEY) || intent.getSerializableExtra(ITEM_KEY) !is RepoModel)
        {
            Toast.makeText(this, "Internal error.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val clickedRepo = intent.getSerializableExtra(ITEM_KEY) as RepoModel
            val titleDetailsView = findViewById<TextView>(R.id.details_name)
            val ownerDetailsView = findViewById<TextView>(R.id.details_owner)
            val descDetailsView = findViewById<TextView>(R.id.details_description)
            val dateDetailsView = findViewById<TextView>(R.id.details_date)
            val languageDetailsView = findViewById<TextView>(R.id.details_language)
            val scoreDetailsView = findViewById<TextView>(R.id.details_score)

            val button = findViewById<Button>(R.id.bt_details)
            button.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(clickedRepo.html_url)
                if (intent.resolveActivity(packageManager) != null)
                    startActivity(intent)
            }

            titleDetailsView.text = clickedRepo.name
            ownerDetailsView.text = clickedRepo.owner.login
            descDetailsView.text = clickedRepo.description
            dateDetailsView.text = clickedRepo.created_at
            languageDetailsView.text = clickedRepo.language
            scoreDetailsView.text = clickedRepo.score.toString()
        }
    }

    companion object {
        private val ITEM_KEY = "item"
    }
}