/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.views.repositorydetails

import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.R


class GitHubRepoDetailsFragment : Fragment(R.layout.fragment_repo_details) {

    /*private val args: GitHubRepoDetailsFragmentArgs by navArgs()

    private var binding: FragmentRepoDetailsBinding? = null
    private val _binding get() = binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentRepoDetailsBinding.bind(view)

        var item = args.item

        _binding.ownerIconView.load(item.ownerIconUrl);
        _binding.nameView.text = item.name;
        _binding.languageView.text = item.language;
        _binding.starsView.text = "${item.stargazersCount} stars";
        _binding.watchersView.text = "${item.watchersCount} watchers";
        _binding.forksView.text = "${item.forksCount} forks";
        _binding.openIssuesView.text = "${item.openIssuesCount} open issues";
    }*/
}
