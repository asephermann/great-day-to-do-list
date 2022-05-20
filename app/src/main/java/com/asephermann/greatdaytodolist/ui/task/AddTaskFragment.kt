package com.asephermann.greatdaytodolist.ui.task

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.asephermann.greatdaytodolist.Notification
import com.asephermann.greatdaytodolist.Notification.Companion.channelId
import com.asephermann.greatdaytodolist.Notification.Companion.messageExtra
import com.asephermann.greatdaytodolist.Notification.Companion.notificationId
import com.asephermann.greatdaytodolist.Notification.Companion.titleExtra
import com.asephermann.greatdaytodolist.R
import com.asephermann.greatdaytodolist.data.model.Task
import com.asephermann.greatdaytodolist.ui.category.CategoryViewModel
import com.asephermann.greatdaytodolist.ui.home.HomeFragment
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_task_layout.*
import kotlinx.android.synthetic.main.fragment_add_task.*
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddTaskFragment : Fragment(), View.OnClickListener {

    private val categoryViewModel : CategoryViewModel by viewModels()
    private val addTaskViewModel : AddTaskViewModel by viewModels()

    companion object {
        lateinit var taskList : List<String>
    }

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
        return inflater.inflate(R.layout.fragment_add_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        editTextDate.showSoftInputOnFocus = false
        editTextDate2.showSoftInputOnFocus = false

        editTextDate.setOnClickListener {
            getSelectedDate(editTextDate)
        }
        editTextDate2.setOnClickListener {
            getSelectedDate(editTextDate2)
        }

        buttonBack.setOnClickListener(this)
        buttonSubmitAddTask.setOnClickListener(this)

        initCategory()
        initTask()
        createNotificationChannel()

    }

    private fun initCategory() {
        categoryViewModel.categoriesName.observe(viewLifecycleOwner) {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, it)
            editTextCategory.setAdapter(adapter)
        }
    }

    private fun initTask() {
        addTaskViewModel.openTasks.observe(viewLifecycleOwner) {
            taskList=it
        }
    }

    private fun getSelectedDate(editTextDate: TextInputEditText) {
        val myCalendar: Calendar = Calendar.getInstance()
        val date: DatePickerDialog.OnDateSetListener?

        val myFormat = "MMM-dd-yyyy"

        date = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            editTextDate.setText(sdf.format(myCalendar.time))
        }
        DatePickerDialog(
            requireActivity(), date,
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        ).show()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.buttonBack -> {
                backToHome()
            }


            R.id.buttonSubmitAddTask -> {
                val taskTitle = editTextTitle.text.toString()
                val categoryName = editTextCategory.text.toString()
                val iconId = categoryViewModel.category.value?.iconId
                val desc = editTextDescription.text.toString()
                val dateStart = editTextDate.text.toString()
                val dateEnds = editTextDate2.text.toString()
                if(taskTitle.trim().isNotEmpty() &&
                    categoryName.trim().isNotEmpty() &&
                    desc.trim().isNotEmpty() &&
                    dateStart.trim().isNotEmpty() &&
                    dateEnds.trim().isNotEmpty()
                ) {
                    addTaskViewModel.createTask(
                        Task(
                            title= taskTitle,
                            categoryName = categoryName,
                            iconId = 1,
                            desc = desc,
                            dateStart = dateStart,
                            dateEnds = dateEnds,
                            isDone = false
                        )
                    )
                    Toast.makeText(requireActivity(), "Task added", Toast.LENGTH_SHORT).show()
                    scheduleNotification(dateStart)
                    backToHome()
                }else{
                    Toast.makeText(requireActivity(), "Complete the form.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun scheduleNotification(stringDate :String) {
        var message = taskList.joinToString("\n")

        addTaskViewModel.openTasks.observe(viewLifecycleOwner){
            message = it.joinToString("\n")
        }

        val intent = Intent(requireActivity(), Notification::class.java)
        val title = "Task Reminder"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            requireActivity(),
            notificationId,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime(stringDate)
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
    }

    private fun getTime(stringDate: String): Long {
        val sdf = SimpleDateFormat("MMM-dd-yyyy", Locale.US)
        val calendar = Calendar.getInstance()
        calendar.time = sdf.parse(stringDate)

        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val desc = "Task Reminder"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =  NotificationChannel(channelId, name, importance)
            channel.description = desc
            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun backToHome() {
        requireActivity().window.statusBarColor = ContextCompat.getColor(requireActivity(), R.color.white)
        val mHomeFragment = HomeFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mHomeFragment, HomeFragment::class.java.simpleName)
            commit()
        }
    }
}