package com.nytimes.mostpopular.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nytimes.mostpopular.R
import com.nytimes.mostpopular.adapters.ArticlesPagedListAdapter
import com.nytimes.mostpopular.db.ArticleEntity
import com.nytimes.mostpopular.utils.NetworkState
import com.nytimes.mostpopular.utils.OnClickListener
import com.nytimes.mostpopular.viewmodel.MostPopulatViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class ListFragment : Fragment() {
    private val viewModel: MostPopulatViewModel by viewModels()

    lateinit var articlesAdapter: ArticlesPagedListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initObserver()

    }

    private fun initRecycler() {
        articlesAdapter = ArticlesPagedListAdapter(object :OnClickListener{

            override fun onArticleCLicked(article: ArticleEntity) {
                var bundle = bundleOf("article" to article)
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment,bundle)
            }
        })

        articlesAdapter.setRetryCallback { viewModel.retry() }
        val layoutManager = LinearLayoutManager(context)



        articles_recycler.adapter = articlesAdapter
        articles_recycler.layoutManager = layoutManager
        articles_recycler.setHasFixedSize(true)

    }

    private fun initObserver() {
        viewModel.articlesPagedList.observe(viewLifecycleOwner, Observer {
            articlesAdapter.submitList(it)
        })

        viewModel.getNetworkState()
            .observe(viewLifecycleOwner, Observer<NetworkState> {
                progress_bar_main.visibility =
                    if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE

                txt_error_main.visibility =
                    if (viewModel.listIsEmpty() && it.message != null) View.VISIBLE else View.GONE

                articlesAdapter.setNetworkState(it)
            })
    }


}