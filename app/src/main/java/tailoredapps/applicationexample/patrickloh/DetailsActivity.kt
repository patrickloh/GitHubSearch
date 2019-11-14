package tailoredapps.applicationexample.patrickloh

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import tailoredapps.applicationexample.patrickloh.Models.RepoModel

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        if (!getIntent().hasExtra(ITEM_KEY) || getIntent().getSerializableExtra(ITEM_KEY) !is RepoModel)
        {
            Toast.makeText(this, "Internal error.", Toast.LENGTH_SHORT).show()
        }
        else
        {
            val clickedRepo = getIntent().getSerializableExtra(ITEM_KEY) as RepoModel
            val TitleDetailsView = findViewById<TextView>(R.id.details_name)
            val OwnerDetailsView = findViewById<TextView>(R.id.details_owner)
            val DescDetailsView = findViewById<TextView>(R.id.details_description)
            val DateDetailsView = findViewById<TextView>(R.id.details_date)
            val LanguageDetailsView = findViewById<TextView>(R.id.details_language)
            val ScoreDetailsView = findViewById<TextView>(R.id.details_score)

            val button = findViewById<Button>(R.id.bt_details)
            button.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(clickedRepo.html_url)
                if (intent.resolveActivity(packageManager) != null)
                    startActivity(intent)
            }

            TitleDetailsView.setText(clickedRepo.name)
            OwnerDetailsView.setText(clickedRepo.owner.login)
            DescDetailsView.setText(clickedRepo.description)
            DateDetailsView.setText(clickedRepo.created_at)
            LanguageDetailsView.setText(clickedRepo.language)
            ScoreDetailsView.setText(clickedRepo.score.toString())
        }
    }

    companion object {
        private val ITEM_KEY = "item"
    }
}