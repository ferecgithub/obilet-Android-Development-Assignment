package com.ferechamitebeyli.data.util

interface OnItemClickListener<T> {
    fun onItemClick(position: Int, model: T)
}