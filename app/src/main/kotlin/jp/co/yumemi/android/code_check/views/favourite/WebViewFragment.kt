package jp.co.yumemi.android.code_check.views.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentWebViewBinding

/**
 * A Fragment that displays a web view.
 * This Fragment is responsible for displaying a web page.
 */
@AndroidEntryPoint
class WebViewFragment : Fragment() {

    // Data binding for the fragment layout
    private lateinit var binding: FragmentWebViewBinding

    // Arguments passed to the fragment using Safe Args
    private val args: WebViewFragmentArgs by navArgs()

    // ViewModel for managing favorites data
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWebViewBinding.inflate(
            inflater,
            container,
            false
        )
        // Initialize ViewModel using ViewModelProvider
        viewModel = ViewModelProvider(this)[FavoritesViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the target repository from the arguments
        val webRepository = args.targetRepository

        // Set up the WebView with the HTML URL of the repository owner
        setupWebView(webRepository.owner?.htmlUrl)

    }

    /**
     * Sets up the WebView with the specified HTML URL.
     * @param htmlUrl The HTML URL to load in the WebView.
     */
    private fun setupWebView(htmlUrl: String?) {
        binding.webView.apply {
            webViewClient = WebViewClient()
            htmlUrl?.let {
                loadUrl(it)
            }
        }
    }
}