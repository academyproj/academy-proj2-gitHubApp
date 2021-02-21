package com.example.academy_proj2_githubapp.navigation

import androidx.fragment.app.Fragment
import com.example.academy_proj2_githubapp.NavigationActivity

abstract class BaseFragment : Fragment() {

    protected val navigator: Navigator by lazy { (requireActivity() as NavigationActivity).navigator }
}