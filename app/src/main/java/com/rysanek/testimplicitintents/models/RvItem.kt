package com.rysanek.testimplicitintents.models

import com.rysanek.testimplicitintents.ui.fragments.BaseFragment
import kotlin.reflect.KClass

data class RvItem(val iconId: Int, val itemActionText: String, val fragment: KClass<out BaseFragment>)