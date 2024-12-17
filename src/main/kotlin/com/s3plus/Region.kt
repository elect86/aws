package com.s3plus

enum class Region(val description: String) {
    usｰeastｰ2("US East (Ohio)"),
    usｰeastｰ1("US East (N. Virginia)"),
    usｰwestｰ1("US West (N. California)"),
    usｰwestｰ2("US West (Oregon)"),
    afｰsouthｰ1("Africa (Cape Town)"),
    afｰeastｰ1("Asia Pacific (Hong Kong)");

    val region
        get() = name.replace('ｰ', '-')
}