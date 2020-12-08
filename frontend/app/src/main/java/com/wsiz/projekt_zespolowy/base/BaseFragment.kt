package com.wsiz.projekt_zespolowy.base

import androidx.fragment.app.Fragment
import com.wsiz.projekt_zespolowy.activity.main.MainActivity

abstract class BaseFragment : Fragment() {

    fun mainActivity() = activity as MainActivity
}