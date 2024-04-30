package jp.co.yumemi.android.code_check.views.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.common.initRecyclerView
import jp.co.yumemi.android.code_check.databinding.FragmentFavouritesBinding
import jp.co.yumemi.android.code_check.model.GitHubAccount

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var favoriteRepositoriesAdapter: FavoriteRepositoriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(
            inflater,
            container,
            false
        )
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        initializeFavoriteRepositoriesAdapter()
        observeFavoriteRepositories()


    }

    private fun observeFavoriteRepositories() {
        viewModel.favoriteRepositories.observe(viewLifecycleOwner) { favoriteRepos->
            favoriteRepositoriesAdapter.differ.submitList(favoriteRepos)

        }
    }

    private fun initializeFavoriteRepositoriesAdapter() {
        favoriteRepositoriesAdapter =
            FavoriteRepositoriesAdapter(object : FavoriteRepositoriesAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubAccount) {
                    TODO("Not yet implemented")
                }
            })

        setupFavoriteRepositoriesRecyclerView()
    }

    private fun setupFavoriteRepositoriesRecyclerView() {
        binding.rvSavedRepoList.initRecyclerView(
            LinearLayoutManager(requireContext()),
            favoriteRepositoriesAdapter

        )
    }


}