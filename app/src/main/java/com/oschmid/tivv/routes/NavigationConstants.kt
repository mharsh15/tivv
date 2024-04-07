package com.oschmid.tivv.routes

/**
 * below is routing constants
 * where routes have params ex: {id}, one has to pass params
 * hence pre is used in compose page, where id is added to the end
 * and non pre route is used navigation/NAvgraph page
 * */

object NavigationConstants  {
    const val DASHBOARD = "dash"
    const val USER_MONTHLY_LEDGER_DASH ="user_monthly_ledger_dash"
    const val USER_MONTHLY_LEDGER_ADD_PRE = "user_monthly_ledger_add/"
    const val USER_MONTHLY_LEDGER_ADD = "user_monthly_ledger_add/{id}"
    const val USER_MONTHLY_LEDGER_LIST = "user_monthly_ledger_list"
    const val USER_INDIVIDUAL_LEDGER_UPDATE_PRE="user_individual_ledger_update/"
    const val USER_INDIVIDUAL_LEDGER_UPDATE = "user_individual_ledger_update/{id}"
    const val USER_INDIVIDUAL_LEDGER_VIEW_PRE = "user_individual_ledger_view/"
    const val USER_INDIVIDUAL_LEDGER_VIEW = "user_individual_ledger_view/{id}"


}