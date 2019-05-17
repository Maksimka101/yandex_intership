package com.maksimka101.git_repo.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.maksimka101.git_repo.R
import com.maksimka101.git_repo.extensions.inflate
import com.maksimka101.git_repo.models.GitRepository
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class RecyclerAdapter(private val repositoryArr: ArrayList<GitRepository>, private val context: Context) : RecyclerView.Adapter<RecyclerAdapter.ItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val inflatedView = parent.inflate(R.layout.recyclerview_item, false)
        return ItemHolder(inflatedView, context)
    }

    override fun getItemCount() = repositoryArr.size

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bindText(repositoryArr[position])
    }

    class ItemHolder(v: View, val context: Context) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private val view = v
        var url: String? = null

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (url != null) {
                val uri = Uri.parse(url)
                val linkIntent = Intent(Intent.ACTION_WEB_SEARCH, uri)
                context.startActivity(linkIntent)
            } else
                println("No url!!!")

        }

        // TODO
        fun bindText(repository: GitRepository) {
            url = repository.url
            view.language.text = "Language: ${repository.language}"
            view.itemName.text = repository.fullName
            view.score.text = "Score: ${repository.score}"
            view.itemAbout.text = repository.description
            view.isPrivate.text = if (repository.isPrivate!!) "private" else "isn't private"
        }
    }
}