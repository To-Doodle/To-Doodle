package ca.uwaterloo.cs.todoodle

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ca.uwaterloo.cs.todoodle.databinding.HomeActivityBinding

class HomeActivity : AppCompatActivity() {

    internal lateinit var binding: HomeActivityBinding

    private val examplesAdapter = HomeOptionsAdapter {
        val fragment = Example5Fragment()
        supportFragmentManager.beginTransaction()
            .run {
                return@run setCustomAnimations(
                        androidx.appcompat.R.anim.abc_slide_in_top,
                        androidx.constraintlayout.widget.R.anim.abc_fade_out,
                        androidx.appcompat.R.anim.abc_fade_in,
                        androidx.constraintlayout.widget.R.anim.abc_slide_out_bottom
                    )
                return@run setCustomAnimations(
                    androidx.appcompat.R.anim.abc_shrink_fade_out_from_bottom,
                    androidx.appcompat.R.anim.abc_fade_out
                )
            }
            .replace(R.id.homeContainer, fragment, fragment.javaClass.simpleName)
            .addToBackStack(fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.homeToolbar)
        binding.examplesRv.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = examplesAdapter
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> onBackPressed().let { true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
