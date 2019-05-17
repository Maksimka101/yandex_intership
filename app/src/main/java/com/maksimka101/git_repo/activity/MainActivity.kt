package com.maksimka101.git_repo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.maksimka101.git_repo.R
import com.maksimka101.git_repo.adapters.RecyclerAdapter
import com.maksimka101.git_repo.handler.OnRepositoryLoad
import com.maksimka101.git_repo.handler.RepositoryHandler
import com.maksimka101.git_repo.models.GitRepository
import com.maksimka101.git_repo.models.RequestHead
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var repositoryHandler: RepositoryHandler
    private lateinit var linearLayoutManager : LinearLayoutManager
    private lateinit var adapter: RecyclerAdapter
    private var repositoryArr = ArrayList<GitRepository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        adapter = RecyclerAdapter(repositoryArr, this)
        recyclerView.adapter = adapter

        repositoryHandler = RepositoryHandler()

        search.setOnClickListener {
            getRepository(repositoryNameFolder.text.toString(), repositoryLanguageField.text.toString())
        }

        getRepository(getString(R.string.default_repository), getString(R.string.default_language))
    }

    private fun getRepository(name: String, language: String) {
        repositoryHandler.getRepository(name, language, object : OnRepositoryLoad {
            override fun onGetRepository(repository: ArrayList<GitRepository>, requestHead: RequestHead) {
                runOnUiThread {
                    repositoryArr.clear()
                    repositoryArr.addAll(repository)
                    adapter.notifyDataSetChanged()
                    if (repositoryArr.size == 0)
                        noResults.visibility = View.VISIBLE
                    else
                        noResults.visibility = View.GONE
                }
            }
        })
    }
}
