package app.company.sportpop.framework.remote

import app.company.sportpop.framework.remote.firebase.ErrorNetwork

data class Response<D>(
    val isSuccessful: Boolean,
    val body: D?,
    val error: ErrorNetwork? = null
)

