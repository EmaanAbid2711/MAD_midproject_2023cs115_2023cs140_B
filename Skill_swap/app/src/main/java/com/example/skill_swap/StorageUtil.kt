package com.example.skill_swap

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object StorageUtil {

    private const val USER_KEY = "users"
    private const val REQUEST_KEY = "requests"

    // ================= USERS =================

    fun getUsers(context: Context): ArrayList<User> {
        val prefs = context.getSharedPreferences("SkillSwap", Context.MODE_PRIVATE)
        val json = prefs.getString(USER_KEY, "[]")

        val list = ArrayList<User>()
        val array = JSONArray(json)

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)

            list.add(
                User(
                    obj.getString("name"),
                    obj.getString("email"),
                    obj.getString("password"),
                    obj.optString("skillHave"),
                    obj.optString("skillWant"),
                    obj.optString("level")
                )
            )
        }
        return list
    }

    // ✅ FIXED: Added missing function (this caused your error)
    fun saveUser(context: Context, user: User) {
        val list = getUsers(context)
        list.add(user)
        updateUsers(context, list)
    }

    fun updateUsers(context: Context, list: ArrayList<User>) {
        val prefs = context.getSharedPreferences("SkillSwap", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val array = JSONArray()

        for (u in list) {
            val obj = JSONObject()
            obj.put("name", u.name)
            obj.put("email", u.email)
            obj.put("password", u.password)
            obj.put("skillHave", u.skillHave)
            obj.put("skillWant", u.skillWant)
            obj.put("level", u.level)

            array.put(obj)
        }

        editor.putString(USER_KEY, array.toString())
        editor.apply()
    }

    // ================= REQUESTS =================

    fun getRequests(context: Context): ArrayList<Request> {
        val prefs = context.getSharedPreferences("SkillSwap", Context.MODE_PRIVATE)
        val json = prefs.getString(REQUEST_KEY, "[]")

        val list = ArrayList<Request>()
        val array = JSONArray(json)

        for (i in 0 until array.length()) {
            val obj = array.getJSONObject(i)

            val sender = obj.getString("sender")
            val receiver = obj.getString("receiver")
            val skill = obj.getString("skill")
            val status = obj.getString("status")

            // ✅ Prevent crash if old data doesn't have timeSlot
            val timeSlot = if (obj.has("timeSlot"))
                obj.getString("timeSlot")
            else
                "Not Selected"

            list.add(Request(sender, receiver, skill, status, timeSlot))
        }

        return list
    }

    fun saveRequest(context: Context, request: Request) {
        val list = getRequests(context)
        list.add(request)
        updateRequests(context, list)
    }

    fun updateRequests(context: Context, list: ArrayList<Request>) {
        val prefs = context.getSharedPreferences("SkillSwap", Context.MODE_PRIVATE)
        val editor = prefs.edit()

        val array = JSONArray()

        for (r in list) {
            val obj = JSONObject()
            obj.put("sender", r.sender)
            obj.put("receiver", r.receiver)
            obj.put("skill", r.skill)
            obj.put("status", r.status)

            // ✅ IMPORTANT: always save timeSlot
            obj.put("timeSlot", r.timeSlot)

            array.put(obj)
        }

        editor.putString(REQUEST_KEY, array.toString())
        editor.apply()
    }
}