package com.kishynskaya.myapplication

import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kishynskaya.myapplication.databinding.FragmentFirstBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class FirstFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var viewModel: ListMovieModelView
    private lateinit var binding: FragmentFirstBinding

    private val handler: Handler = Handler()
    private var runnable: Runnable? = null

    private val movieAdapter = MovieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = ViewModelProvider(requireActivity()).get(ListMovieModelView::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                Toast.makeText(context, "First Fragment item", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_list, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search movie"
        searchView.setOnQueryTextListener(this)
        searchView.isIconified = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.movies.collectLatest {
                movieAdapter.submitData(it)
            }
            viewModel.queryMovies.observe(requireActivity(), {
                lifecycleScope.launch {
                    movieAdapter.submitData(it)
                }
            })
        }
        binding.movieRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
//        view.findViewById<Button>(R.id.button_first).setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        Toast.makeText(requireActivity(), "Query Inserted", Toast.LENGTH_SHORT).show();
        return false

    }

    override fun onQueryTextChange(query: String?): Boolean {
        runnable?.let {
            handler.removeCallbacks(it)
        }
        runnable = Runnable { onDelayerQueryTextChange(query) }
        handler.postDelayed(runnable!!, 700)
        return true
    }

    private fun onDelayerQueryTextChange(query: String?) {
        query?.let {
            runBlocking {
                viewModel.search(query)
            }
        }
        Toast.makeText(requireActivity(), "QueryText", Toast.LENGTH_SHORT).show();
    }
}
