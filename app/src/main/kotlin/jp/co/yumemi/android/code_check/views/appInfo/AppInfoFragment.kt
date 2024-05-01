package jp.co.yumemi.android.code_check.views.appInfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.databinding.FragmentAppInfoBinding

/**
 * A fragment to display information about the app.
 * This fragment includes details.
 */
class AppInfoFragment : Fragment() {

    // Data binding for the fragment layout
    private lateinit var binding: FragmentAppInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppInfoBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
}