package com.asephermann.greatdaytodolist.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.Category
import com.asephermann.greatdaytodolist.data.model.CategoryIcon
import com.asephermann.greatdaytodolist.ui.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_category_layout.*
import kotlinx.android.synthetic.main.fragment_add_category.*
import kotlinx.android.synthetic.main.grid_item_layout.*

@AndroidEntryPoint
class AddCategoryFragment : Fragment(), View.OnClickListener {

    private val addCategoryViewModel : AddCategoryViewModel by viewModels()
    private lateinit var rvIcons: RecyclerView
    private val list = ArrayList<CategoryIcon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if(shouldInterceptBackPress()){
                    requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
                    val mHomeFragment = HomeFragment()
                    val mFragmentManager = parentFragmentManager
                    mFragmentManager.beginTransaction().apply {
                        replace(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
                        commit()
                    }
                }else{
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }
    fun shouldInterceptBackPress() = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonBack.setOnClickListener(this)
        buttonSubmitAddCategory.setOnClickListener(this)

        rvIcons = requireActivity().findViewById(R.id.gridView)
        rvIcons.setHasFixedSize(true)

        list.addAll(listIcons)
        showRecyclerGrid()
    }
    private val listIcons: ArrayList<CategoryIcon>
        get() {
            val dataCategory = resources.getStringArray(R.array.data_category)
            val dataIcon = resources.obtainTypedArray(R.array.data_icon)
            val listCategory = ArrayList<CategoryIcon>()
            for (i in dataCategory.indices) {
                val icon = CategoryIcon(dataIcon.getResourceId(i, -1))
                listCategory.add(icon)
            }
            return listCategory
        }
    private fun showRecyclerGrid() {
        rvIcons.layoutManager = GridLayoutManager(requireActivity(),3)
        val chooseIconAdapter = ChooseIconAdapter(list)
        rvIcons.adapter = chooseIconAdapter
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonBack -> {
                backToHome()
            }

            R.id.buttonSubmitAddCategory -> {
                val selectedIcon = textIconId.text.toString().toInt()
                val categoryName = editTextCategoryName.text.toString()
                if(categoryName.trim().isNotEmpty() && selectedIcon!=0) {
                    addCategoryViewModel.createCategory(
                        Category(name = categoryName, iconId = selectedIcon)
                    )
                    Toast.makeText(requireActivity(), "Category added", Toast.LENGTH_SHORT).show()

                    backToHome()
                }else{
                    Toast.makeText(requireActivity(), "Fill the category name.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun backToHome(){
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
        val mHomeFragment = HomeFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
            commit()
        }
    }
}